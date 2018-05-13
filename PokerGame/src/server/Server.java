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
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerInitializer());

            bootstrap.bind(PORT).sync().channel().closeFuture().sync();
            System.out.println("SERVER IS RUNNING");

        } catch (Exception e) {

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
