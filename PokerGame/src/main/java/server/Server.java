/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.io.IOException;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import model.AbstractPokerGame;
import model.Card;
import model.GameAction;
import model.Player;
import model.PokerGame;
import network.GameState;
import network.PlayerAction;
import network.PlayerState;
import utils.Logger;

/**
 *
 * @author iron2414
 */
public class Server {

    private static final int PORT = 4444;
    private static final int MIN_PLAYER = 2;
    private static final int MIN_BET = 100;
    private static List<Player> playerList;
    private static int currentPlayerIndex;
    private static boolean messageRecieved;
    private static PlayerAction action;
    private static AbstractPokerGame game;

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.run();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Server() throws IOException {
        playerList = new ArrayList<>();
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            System.out.println("SERVER START");
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerInitializer());

            bootstrap.bind(PORT).sync().channel(); //.closeFuture().sync();
            System.out.println("SERVER IS RUNNING");
            //TODO optimalization

            while (playerList.size() < MIN_PLAYER) {
                Thread.sleep(2000);
            }
            Logger.LOGGING = true;
            game = new PokerGame(playerList, MIN_BET);
            System.out.println("GAME START");
            do {
                game.newRound();
                currentPlayerIndex = game.getCurrentPlayerID();
                System.out.println("currentPlayer: " + currentPlayerIndex + "-" + playerList.get(currentPlayerIndex).getName());
                refreshStates(game.isRoundOver());

                while (!game.isRoundOver()) {
                    System.out.println("------");
                    System.out.println("Current turn is on: " + playerList.get(game.getCurrentPlayerID()).getName());
                    System.out.println("\t his/her hand: " + Arrays.toString(playerList.get(game.getCurrentPlayerID()).getHand()));
                    while (!messageRecieved) {
                        Thread.sleep(1000);
                    }

                    System.out.println("message received: " + action.getAction() + " " + action.getRaiseAmount());
                    GameAction act = GameAction.get(action.getAction());
                    int money = 0;

                    if (act == GameAction.RAISE) {
                        money = action.getRaiseAmount();
                    }

                    try {
                        game.takeAction(game.getCurrentPlayerID(), act, money);
                    } catch (IllegalArgumentException iae) {
                        System.err.println(iae.getMessage());
                    }
                    currentPlayerIndex = game.getCurrentPlayerID();
                    messageRecieved = false;
                    action = null;

                    refreshStates(game.isRoundOver());

                    //so the logs won't collide
                    Thread.sleep(100);
                }
                //System.out.println("Round over winnner: "+ game.getWinner().getName());
                Thread.sleep(4000);
            } while (!game.isGameOver());

        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private void refreshStates(boolean allCardVisible) {
        int i = 0;
        for (Player player : playerList) {
            GameState state = getStateForPlayer(i, allCardVisible);
            ServerHandler.refreshStates(i, state);
            i++;
        }
    }

    //TODO tableCards, pot,maxRaise, canFold, can Call, canRaise
    public static GameState getStateForPlayer(int playerIndex, boolean allCardVisible) {
        GameState state = null;
        PlayerState currentPlayer = null;
        ArrayList<PlayerState> opponents = new ArrayList<>();

        int i = 0;
        for (Player player : playerList) {
            if (i == playerIndex) {
                currentPlayer = new PlayerState(
                        player.getName(),
                        player.getMoney(),
                        player.getHand(),
                        Server.game.getDealerID() == i,
                        Server.game.getBigBlindID() == i,
                        Server.game.getSmallBlindID() == i);

            } else {
                opponents.add(new PlayerState(
                        player.getName(),
                        player.getMoney(),
                        (allCardVisible ? player.getHand() : new Card[]{}),
                        Server.game.getDealerID() == i,
                        Server.game.getBigBlindID() == i,
                        Server.game.getSmallBlindID() == i));
            }
            i++;
        }

        String winnerName = game.isRoundOver() ? game.getWinner().getName() : "";
        String currentPlayerName = playerList.get(game.getCurrentPlayerID()).getName();
        state = new GameState(currentPlayer, opponents,
                Server.game.getTableCards(),
                Server.game.getPot(),
                1000,
                true,
                true,
                true,
                winnerName,
                currentPlayerName
        );
        return state;
    }

    public static List<Player> getPlayerList() {
        return playerList;
    }

    public static int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public static void setMessageRecieved(boolean messageRecieved) {
        Server.messageRecieved = messageRecieved;
    }

    public static void setAction(PlayerAction action) {
        Server.action = action;
    }
}
