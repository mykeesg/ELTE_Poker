/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import client.frontend.ClientFrontend;
import client.frontend.model.GameState;
import client.frontend.model.PlayerState;
import model.Card;
import model.Rank;
import model.Suit;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author iron2414
 */
public class Client {
    private static final int PORT = 444;
    private static final String HOST = "localhost";
    private Socket socket;
    private Scanner input;
    private PrintWriter output;

    public Client(String name) throws IOException {
        socket = new Socket(HOST,PORT);
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream());
        output.println(name);
        output.flush();
        System.out.println("Socket connected");
    }

    public static void main(String[] args) {
        try {
            ClientFrontend clientFrontend = new ClientFrontend();

            String name = clientFrontend.logIn();

            Client client = new Client(name);

            new Thread(() -> {
                clientFrontend.startGame();

                clientFrontend.getRaise().addEventListener((sender, amount) -> {
                    JOptionPane.showMessageDialog(null, "Raised: " + amount);
                });

                clientFrontend.updateState(new GameState(
                        new PlayerState(
                                "Miki",
                                249,
                                new Card[]{
                                        new Card(Suit.DIAMONDS, Rank.QUEEN),
                                        new Card(Suit.DIAMONDS, Rank.KING),
                                },
                                false,
                                false,
                                false),
                        new PlayerState[]{
                                new PlayerState("Peti", 380, new Card[]{}, true, false, false),
                                new PlayerState("Jani", 249, new Card[]{}, false, true, false),
                                new PlayerState("Józsi", 55, new Card[]{}, false, false, false),
                                new PlayerState("Feri", 5, new Card[]{}, false, false, true),
                        },
                        new Card[]{
                                new Card(Suit.DIAMONDS, Rank.QUEEN),
                                new Card(Suit.DIAMONDS, Rank.KING),
                                new Card(Suit.DIAMONDS, Rank.TEN),
                                new Card(Suit.DIAMONDS, Rank.SIX),
                                new Card(Suit.DIAMONDS, Rank.FIVE),
                        },
                        156,
                        249,
                        true,
                        true,
                        true
                ));
            }).start();

        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
