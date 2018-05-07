/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 *
 * @author iron2414
 */
public class ServerHandler extends SimpleChannelInboundHandler {

    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public ServerHandler() {
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Someone trying to join");
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.write("[SERVER] - " + incoming.remoteAddress() + " has joined!\n");
        }
        channels.add(ctx.channel());
        System.out.println(channels);
		incoming.read();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.write("[SERVER] - " + incoming.remoteAddress() + " has left!\n");
        }
        channels.remove(ctx.channel());
    }

    //TODO a játék állapotát frissíteni a kliens üzenetének megfelelően. Ha nem épp ő a soron következő játékos, akkor visszaküldeni egy hibaüzenetet.
    @Override
    protected void channelRead0(ChannelHandlerContext chc, Object message) throws Exception {
        Channel incoming = chc.channel();
        System.out.println("SERVER handler: " + message);
        
        for (Channel channel : channels) {
            /*if (channel != incoming)*/ {
                channel.write("[" + incoming.remoteAddress() + "] " + message + "\n");
				channel.flush();
            }
        }
		incoming.read();
    }
}