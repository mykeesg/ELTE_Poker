/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import client.frontend.ClientFrontend;
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
import java.util.ArrayList;
import java.util.Scanner;
import static network.Communication.getSocketString;
import network.*;

/**
 * @author iron2414
 */
public class Client {

    private static final int PORT = 4444;
    private static final String HOST = "localhost";
    public static String name;
    private Channel channel;
    private static GameState state;
    private static ClientFrontend clientFrontend;

    public Client(String name) throws IOException {
        this.name = name;
    }

    public static void main(String[] args) {
        try {
            clientFrontend = new ClientFrontend();

            String name = clientFrontend.logIn();
            Client client = new Client("Teszt");
            new Thread(() -> {
                 client.run();
            }).start();
            clientFrontend.getFold().addEventListener(sender -> client.onFold());
            clientFrontend.getCall().addEventListener(sender -> client.onCall());
            clientFrontend.getRaise().addEventListener((sender, amount) -> client.onRaise(amount));
            clientFrontend.getQuit().addEventListener(sender -> client.onQuit());

            clientFrontend.startGame();
            //TODO valahogy máshogy megoldani
            while (true) {
                Thread.sleep(1000);
            }

//            new Thread(() -> {
//                // eseménykezelők beállítása
//            }).start();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
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

            //TODO ez csak debug
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (channel.isActive()) {
                //TODO stuff
                Thread.sleep(2000);
//                System.out.println("Write something");
//                int b = Integer.parseInt(in.readLine());
//                PlayerAction a = new PlayerAction(b, 3000);
//                channel.writeAndFlush(getSocketString(a));
            }
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
        PlayerAction a = new PlayerAction(0);
        channel.writeAndFlush(getSocketString(a));

    }

    /**
     * Check vagy call, az előző tét megadása.
     */
    private void onCall() {
        PlayerAction a = new PlayerAction(1);
        String b = getSocketString(a);
        channel.writeAndFlush(b);

    }

    /**
     * Emelés.
     */
    private void onRaise(int amount) {
        PlayerAction a = new PlayerAction(2, amount);
        channel.writeAndFlush(getSocketString(a));
    }

    /**
     * Kilépés.
     */
    private void onQuit() {
        channel.close();
        System.exit(0);
    }

    private void onChangeName(String name) {
        this.name = name;
    }

    public static void refreshState(GameState state) {
        Client.state = state;
        //TODO conversion
        clientFrontend.updateState(state);
    }

}
