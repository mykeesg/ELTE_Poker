/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 *
 * @author iron2414
 */
public class ClientEncoder extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext chc, Object msg, ByteBuf out) throws Exception {
        //TODO a megfelelő üzenetet átküldeni a gombnak megfelelően, raise esetén vele együtt a mennyiséget is. (enum)
         out.writeInt(2);
    }
    
}
