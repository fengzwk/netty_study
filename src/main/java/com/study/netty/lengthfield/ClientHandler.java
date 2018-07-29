package com.study.netty.lengthfield;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.Random;

public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        Random random = new Random();

        for(int i =0; i<7; i++){
            int num = random.nextInt(200);
            int length =  String.valueOf(num).length()+11;
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello world"+num, CharsetUtil.UTF_8);
            ByteBuf lengthbuf = Unpooled.copyInt(length);

            CompositeByteBuf byteBufs = Unpooled.compositeBuffer();
            byteBufs.addComponent(true,lengthbuf);
            byteBufs.addComponent(true,byteBuf);

            ctx.writeAndFlush(byteBufs);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

    }
}
