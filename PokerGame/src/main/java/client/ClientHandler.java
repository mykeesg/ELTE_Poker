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

    private String subMessage = "";

    public ClientHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext chc, String message) throws Exception {
        Gson gson = new GsonBuilder().create();
        System.out.println(message);
        if (message.length() < 1) {
            return;
        }
        this.subMessage += message;
        char lastChar = subMessage.charAt(subMessage.length() - 1);
        if (lastChar != '}') {
            return;
        }
        try {
            GameState action = gson.fromJson(this.subMessage, GameState.class);
            this.subMessage = "";
            Client.refreshState(action);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("INBOUND MESSAGE ERROR:" + e);
        }
        return;

    }

}