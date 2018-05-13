/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 *
 * @author iron2414
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    public ServerInitializer() {
    }

    @Override
    protected void initChannel(SocketChannel c) throws Exception {
        ChannelPipeline pipeline = c.pipeline();

//        pipeline.addLast("decoder", new ServerDecoder());
//        pipeline.addLast("encoder", new ServerEncoder());

        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());

        pipeline.addLast("handler", new ServerHandler());
    }

}
