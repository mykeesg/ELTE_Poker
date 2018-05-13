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
import model.Player;
import static network.Communication.getSocketString;
import network.PokerState;

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
        Player p = new Player("", null, null, null);
        Server.getPlayerList().add(p);
        Channel incoming = ctx.channel();
        incoming.read();
//        Channel incoming = ctx.channel();
//        for (Channel channel : channels) {
//            channel.write("[SERVER] - " + incoming.remoteAddress() + " has joined!\n");
//        }
//        channels.add(ctx.channel());
//        System.out.println(channels);
//        incoming.read();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        channels.remove(ctx.channel());
        //TODO player listából kivenni
    }

    //TODO a játék állapotát frissíteni a kliens üzenetének megfelelően. Ha nem épp ő a soron következő játékos, akkor visszaküldeni egy hibaüzenetet.
    @Override
    protected void channelRead0(ChannelHandlerContext chc, Object message) throws Exception {
        Channel incoming = chc.channel();
        System.out.println("SERVER handler: " + message);
        incoming.read();
    }

    public static void refreshStates() {
        int i = 0;
        for (Channel channel : channels) {
            PokerState state = Server.getState(i);
            channel.write(getSocketString(state));
            i++;
        }
    }
}
