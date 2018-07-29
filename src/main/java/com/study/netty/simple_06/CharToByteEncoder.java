package com.study.netty.simple_06;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class CharToByteEncoder extends MessageToByteEncoder<Character> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Character msg, ByteBuf out) throws Exception {
        System.out.println("invoke encoder");
            out.writeChar(msg);
    }
}
