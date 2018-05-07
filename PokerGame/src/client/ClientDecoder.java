/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 *
 * @author iron2414
 */
public class ClientDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext chc, ByteBuf in, List<Object> out) throws Exception {
        //TODO eldönteni hogy milyen típust várok és ezeket implementálni. Lehet célszerű lenne mindig a teljes állapotot frissíteni és akkor fix a méret.
        if(in.readableBytes() < 4) {
            return;
        }
        ByteBuf b = in.readBytes(4);
        
        int number = b.getInt(0);
        out.add(number);
    }
    
}
