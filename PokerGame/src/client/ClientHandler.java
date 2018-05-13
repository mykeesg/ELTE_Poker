/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 *
 * @author iron2414
 */
public class ClientHandler extends SimpleChannelInboundHandler {

    public ClientHandler() {
    }

    //TODO ezen az alapon valszeg ez sem fog menni?
    @Override
    protected void channelRead0(ChannelHandlerContext chc, Object i) throws Exception {
        System.out.println(i);
        //TODO frissíteni a kliens állapotát az üzenetnek megfelelően
    }
    
}
