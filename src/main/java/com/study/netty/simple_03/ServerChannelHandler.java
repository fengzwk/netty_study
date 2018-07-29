package com.study.netty.simple_03;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

public class ServerChannelHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("Channel ID : "+ctx.channel().attr(AttributeKey.valueOf("ID")).get());
        ByteBuf byteBuf = Unpooled.copiedBuffer("yes".getBytes());
        ctx.writeAndFlush(byteBuf);
        ctx.close();
    }
}
