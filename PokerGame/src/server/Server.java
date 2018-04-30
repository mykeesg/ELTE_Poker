/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Player;
import model.PokerGame;

/**
 *
 * @author iron2414
 */
public class Server {

    private static final int PORT = 444;
    private static final int MIN_PLAYER = 2;
    private static final int MIN_BET = 100;
    private List<Player> playerList;
    private final ServerSocket SRVSOCK;

    public Server() throws IOException {
        playerList = new ArrayList<>();
        SRVSOCK = new ServerSocket(PORT);
        System.out.println("Server socket is listening");
    }

    private Socket addPlayer() throws IOException {
        System.out.println("Waiting for player...");
        Socket socket = SRVSOCK.accept();
        //TODO communicate with client about name
        Scanner input = new Scanner(socket.getInputStream());
        String name = input.nextLine();
        Player player = new Player(name, null, null, socket);
        this.playerList.add(player);
        System.out.println("Player connected: "+ name);

        return socket;
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            while (true) {
                while (server.getPlayerList().size() < MIN_PLAYER) {
                    server.addPlayer();
                }
                PokerGame game = new PokerGame(server.getPlayerList(), MIN_BET);
                System.out.println("Game starting");
                while(!game.finished()) {
                    //TODO do Poker with the players
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Getter Setter
    public List<Player> getPlayerList() {
        return this.playerList;
    }

}
