package com.study.netty.channelattr;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;

public class ClientChannelHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Channel ID : "+ctx.channel().attr(AttributeKey.valueOf("ID")).get());
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello world!", CharsetUtil.UTF_8);
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
         byte[] bs = new byte[msg.readableBytes()];
         msg.readBytes(bs);
         String result = new String(bs);
         System.out.println(result);
         ctx.close();
    }
}
