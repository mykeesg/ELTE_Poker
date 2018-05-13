/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.AbstractPokerGame;
import model.GameAction;
import model.Player;
import model.PokerGame;
import network.PlayerAction;
import network.PokerState;

/**
 *
 * @author iron2414
 */
public class Server {

    private static final int PORT = 444;
    private static final int MIN_PLAYER = 2;
    private static final int MIN_BET = 100;
    private static List<Player> playerList;
    private static int currentPlayerIndex;
    private static boolean messageRecieved;
    private static PlayerAction action;

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
            
//            System.out.println("SERVER START");
//            ServerBootstrap bootstrap = new ServerBootstrap()
//                    .group(bossGroup, workerGroup)
//                    .channel(NioServerSocketChannel.class)
//                    .childHandler(new ServerInitializer());
//
//            System.out.println(100);
//            ChannelFuture f0=bootstrap.bind(PORT);
//            System.out.println(110);
//            f0=f0.sync();
//            System.out.println(120);
//            Channel c0=f0.channel();
//            System.out.println(130);
//            f0=c0.closeFuture();
//            System.out.println(135);
//            c0.close();
//            System.out.println(140);
//            f0.sync();
//            System.out.println("SERVER IS RUNNING");

            System.out.println("SERVER START");
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerInitializer());

            bootstrap.bind(PORT).sync().channel(); //.closeFuture().sync();
            System.out.println("SERVER IS RUNNING");
            //TODO optimalization

            while (true) {
                while (playerList.size() < MIN_PLAYER) {
                    System.out.println(playerList.size());
                    Thread.sleep(2000);
                }
                System.out.println("GAME START");

                AbstractPokerGame game = new PokerGame(playerList, MIN_BET);
                game.newRound();
                System.out.println("ciklus start");
                currentPlayerIndex = 0;

                while (!game.isGameOver()) {
                    System.out.println("------");
                    System.out.println("Current turn is on: " + playerList.get(game.getCurrentPlayerID()).getName());
                    System.out.println("\t his/her hand: " + Arrays.toString(playerList.get(game.getCurrentPlayerID()).getHand()));
                    while (!messageRecieved) {
                        Thread.sleep(1000);
                    }

                    GameAction act = GameAction.get(action.getAction());
                    int money = 0;

                    if (act == GameAction.RAISE) {
                        System.out.println("Enter the amount to raise: ");
                        money = action.getRaiseAmount();
                    }

                    game.takeAction(game.getCurrentPlayerID(), act, money);
                    currentPlayerIndex = game.getCurrentPlayerID();

                    refreshStates();

                    //so the logs won't collide
                    Thread.sleep(100);
                }
            }

        } catch (Throwable e) {
            System.out.println("itt");
            System.out.println(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private void refreshStates() {
        ServerHandler.refreshStates();
    }

    public static PokerState getState(int playerIndex) {
        //TODO implements
        PokerState state = null;
        return state;
    }

    public static List<Player> getPlayerList() {
        return playerList;
    }

}
