package com.study.netty.simple_01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;

import java.nio.charset.Charset;

/**
 * Created by zhang on 2018/6/19.
 */
@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf)msg;
        System.out.println("接收客端端消息："+byteBuf.toString(Charset.forName("UTF-8")));

        String serverMsg = "我是服务器，接收到你的消息："+byteBuf.toString(Charset.forName("UTF-8"));
        byteBuf.clear();
        byteBuf.writeBytes(serverMsg.getBytes());
        ctx.write(byteBuf);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
         cause.printStackTrace();
         ctx.close();
    }
}
