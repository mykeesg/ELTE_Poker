/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 *
 * @author iron2414
 */
public class ServerEncoder extends MessageToByteEncoder {

    //TODO eldönteni hogy milyen típust várok és ezeket implementálni. Lehet célszerű lenne mindig a teljes állapotot elküldeni és akkor fix a méret.
    @Override
    protected void encode(ChannelHandlerContext chc, Object msg, ByteBuf out) throws Exception {
         out.writeInt(2);
    }
    
}
