package com.study.netty.multiserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class Server02ChannelHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf)msg;
        String result = "";
        if(!byteBuf.hasArray()){
            System.out.println("direct buffer");
            byte[] bs = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bs);
            result = new String(bs);
        }
        System.out.println("收到["+ctx.channel().remoteAddress()+"]消息: "+result);

        byteBuf.clear();
        byteBuf.writeBytes("已经收到消息，谢谢！！".getBytes());
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }
}
