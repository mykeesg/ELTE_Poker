/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author mykee
 */
public class Player {

    private final String name;
    private int money;
    private Card[] hand;
    private final String username;
    private final String password;
    private Socket socket;
    public Scanner input;
    public PrintWriter output;

    public Player(String name, String username, String password, Socket socket) throws IOException {
        this.name = name;
        this.username = username;
        this.password = password;
        this.socket = socket;
        this.input = new Scanner(socket.getInputStream());
        this.output = new PrintWriter(socket.getOutputStream());
        money = 0;
    }

    public void setHand(Card[] hand) {
        this.hand = hand;
    }

    public Card[] getHand() {
        //deep copy
        return new Card[]{new Card(hand[0]), new Card(hand[1])};
    }

    @Override
    public String toString() {
        return "Player{" + "name=" + name + '}';
    }

}
