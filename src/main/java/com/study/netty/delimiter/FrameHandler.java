package com.study.netty.delimiter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class FrameHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
         byte[] bs = new byte[msg.readableBytes()];
         msg.readBytes(bs);
         System.out.println("获取消息："+new String(bs));
    }
}
