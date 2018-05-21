/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import network.GameState;
import network.PlayerAction;

/**
 *
 * @author iron2414
 */
public class ClientHandler extends SimpleChannelInboundHandler<String> {

    public ClientHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext chc, String message) throws Exception {
        Gson gson = new GsonBuilder().create();
        //TODO kicserélni a megfelelő osztályra
        System.out.println(message);
        String lastChar = message.substring(message.length() - 1);
        if (!lastChar.equals("}")) {
            return;
        }
        try {
            GameState action = gson.fromJson((String) message, GameState.class);
            Client.refreshState(action);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("INBOUND MESSAGE ERROR:" + e);
        }
        return;

    }

}
