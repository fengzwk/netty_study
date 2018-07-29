package com.study.netty.multiserver;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Server01ChannelHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        System.out.println("收到客户端["+ctx.channel().remoteAddress()+"]消息 ，准备中转");
        EventLoop eventExecutors = ctx.channel().eventLoop();

        final String[] results = new String[1];
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventExecutors)
                .channel(NioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {

                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        ByteBuf byteBuf = Unpooled.buffer();
                        byteBuf.writeBytes("hello world!".getBytes());
                        ctx.writeAndFlush(byteBuf);
                    }

                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx1, ByteBuf msg) throws Exception {
                        System.out.printf("server01 inner handler");
                        byte[] bs = new byte[msg.readableBytes()];
                        msg.readBytes(bs);
                        String  str = new String(bs);

                        System.out.println("转发接受到的结果："+str);
                        ByteBuf byteBuf = Unpooled.copiedBuffer(str.getBytes());
                        ctx.writeAndFlush(byteBuf);
                    }
                });
        ChannelFuture localhost = bootstrap.connect("localhost", 9999);
        localhost.addListener((future)->{
            if(future.isSuccess()){
                System.out.println("转发成功！！");
            }else{
                System.out.println("转发失败");
            }
        });

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }
}
