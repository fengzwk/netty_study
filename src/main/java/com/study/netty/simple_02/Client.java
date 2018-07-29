package com.study.netty.simple_02;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

    public static void main(String[] args) throws Exception{

        EventLoopGroup eventLoop = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoop)
                     .channel(NioSocketChannel.class)
                     .handler(new ClientChannelInitialization());

            ChannelFuture future = bootstrap.connect("localhost", 8899);
            future.channel().closeFuture().sync();
        } finally {
            eventLoop.shutdownGracefully();
        }
    }
}
