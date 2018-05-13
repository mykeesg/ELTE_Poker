/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import client.frontend.ClientFrontend;
import client.frontend.model.GameState;
import client.frontend.model.PlayerState;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.io.BufferedReader;
import model.Card;
import model.Rank;
import model.Suit;
import javax.swing.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import static network.Communication.getSocketString;
import network.*;

/**
 * @author iron2414
 */
public class Client {

    private static final int PORT = 444;
    private static final String HOST = "localhost";
    private String name;
    private Channel channel;

    public Client(String name) throws IOException {
        this.name = name;
    }

    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            System.out.println("Client init");
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientInitializer());
            channel = bootstrap.connect(HOST, PORT).sync().channel();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

//            //TODO ez csak debug
//            while (channel.isActive()) {
//                System.out.println("Write something");
//                int b = Integer.parseInt(in.readLine());
//                PokerAction a = new PokerAction(b,3000);
//                channel.writeAndFlush(getSocketString(a));
//            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            group.shutdownGracefully();
        }
    }

    /**
     * Bedobás.
     */
    private void onFold() {
        PokerAction a = new PokerAction(0);
        channel.writeAndFlush(getSocketString(a));

    }

    /**
     * Check vagy call, az előző tét megadása.
     */
    private void onCall() {
        PokerAction a = new PokerAction(1);
        channel.writeAndFlush(getSocketString(a));

    }

    /**
     * Emelés.
     */
    private void onRaise(int amount) {
        PokerAction a = new PokerAction(2,amount);
        channel.writeAndFlush(getSocketString(a));
    }

    /**
     * Kilépés.
     */
    private void onQuit() {
       channel.close();
    }
    
    private void onChangeName(String name) {
        PlayerName a = new PlayerName(name);
        channel.writeAndFlush(getSocketString(a));
    }

    public static void main(String[] args) {
        try {
            //ClientFrontend clientFrontend = new ClientFrontend();

            //String name = clientFrontend.logIn();
            Client client = new Client("Teszt");

            new Thread(() -> {
                client.run();
            }).start();

//            new Thread(() -> {
//                // eseménykezelők beállítása
//                clientFrontend.getFold().addEventListener(sender -> client.onFold());
//                clientFrontend.getCall().addEventListener(sender -> client.onCall());
//                clientFrontend.getRaise().addEventListener((sender, amount) -> client.onRaise(amount));
//                clientFrontend.getQuit().addEventListener(sender -> client.onQuit());
//
//                clientFrontend.startGame();
//
//                clientFrontend.updateState(new GameState(
//                        new PlayerState(
//                                "Miki",
//                                249,
//                                new Card[]{
//                                        new Card(Suit.DIAMONDS, Rank.QUEEN),
//                                        new Card(Suit.DIAMONDS, Rank.KING),
//                                },
//                                false,
//                                false,
//                                false),
//                        new PlayerState[]{
//                                new PlayerState("Peti", 380, new Card[]{}, true, false, false),
//                                new PlayerState("Jani", 249, new Card[]{}, false, true, false),
//                                new PlayerState("Józsi", 55, new Card[]{}, false, false, false),
//                                new PlayerState("Feri", 5, new Card[]{}, false, false, true),
//                        },
//                        new Card[]{
//                                new Card(Suit.DIAMONDS, Rank.QUEEN),
//                                new Card(Suit.DIAMONDS, Rank.KING),
//                                new Card(Suit.DIAMONDS, Rank.TEN),
//                                new Card(Suit.DIAMONDS, Rank.SIX),
//                                new Card(Suit.DIAMONDS, Rank.FIVE),
//                        },
//                        156,
//                        249,
//                        true,
//                        true,
//                        true
//                ));
//            }).start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
