/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cllient;

import cllient.frontend.ClientFrontend;
import cllient.frontend.model.GameState;
import cllient.frontend.model.PlayerState;
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
//        socket = new Socket(HOST,PORT);
//        input = new Scanner(socket.getInputStream());
//        output = new PrintWriter(socket.getOutputStream());
//        output.println(name);
//        output.flush();
//        System.out.println("Socket connected");
    }

    public static void main(String[] args) {
        try {
            Client client = new Client("Test name");

            new Thread(() -> {
                ClientFrontend clientFrontend = new ClientFrontend();

                clientFrontend.getRaise().addEventListener((sender, amount) -> {
                    JOptionPane.showMessageDialog(null, "Raised: " + amount);
                });

                clientFrontend.updateState(new GameState(
                        new PlayerState(
                                "Me",
                                1,
                                new Card[]{
                                        new Card(Suit.HEARTS, Rank.FOUR)
                                },
                                false,
                                false,
                                false),
                        new PlayerState[]{},
                        new Card[]{},
                        1
                ));
            }).start();

        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
