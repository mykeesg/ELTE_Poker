/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import model.Player;
import static network.Communication.getSocketString;
import network.GameState;
import network.PlayerAction;

/**
 *
 * @author iron2414
 */
public class ServerHandler extends SimpleChannelInboundHandler {

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public ServerHandler() {
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Someone is trying to join");
        Player p = new Player("Player " + Server.getPlayerList().size(), null, null, null);
        p.setMoney(1000);
        Server.getPlayerList().add(p);
        Channel incoming = ctx.channel();
        System.out.println(channels.size());
        channels.add(incoming);
        System.out.println(channels.size());
        incoming.read();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("SOMEONE DISCONNECTED");
        Channel incoming = ctx.channel();
        channels.remove(ctx.channel());
        //TODO player listából kivenni jövő félévre feladat
    }

    //TODO a játék állapotát frissíteni a kliens üzenetének megfelelően. Ha nem épp ő a soron következő játékos, akkor visszaküldeni egy hibaüzenetet.
    @Override
    protected void channelRead0(ChannelHandlerContext chc, Object message) throws Exception {
        Channel incoming = chc.channel();
        int i = 0;
        boolean found = false;
        for (Channel ch : channels) {
            if (ch.equals(incoming)) {
                found = true;
                System.out.println("SERVER handler: channel found id: " + i);
            }
            if (!found) {
                ++i;
            }
        }
        if (Server.getCurrentPlayerIndex() == i) {
            System.out.println("A MEGFELELO JÁTÉKOS KÜLDÖTT ÜZENETET: "+i +"-"+ Server.getPlayerList().get(i));
            Gson gson = new GsonBuilder().create();
            PlayerAction action = gson.fromJson((String) message, PlayerAction.class);
            Server.setAction(action);
            Server.setMessageRecieved(true);
        }
        incoming.read();
    }

    public static void refreshStates(int index, GameState state) {
        int i =0;
        //TODO refatkor + optimalization
        for(Channel channel : channels) {
            if(index == i) {
                channel.writeAndFlush(getSocketString(state));
            }
            i++;
        }
    }
}
