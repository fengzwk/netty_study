package com.study.netty.multiserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server02 {

    public static void main(String[] args) throws Exception{
        EventLoopGroup parent = new NioEventLoopGroup(1);
        EventLoopGroup child = new NioEventLoopGroup(10);

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(parent,child)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new Server02ChannelInitialization());

            ChannelFuture future = serverBootstrap.bind(9999).sync();
            future.channel().closeFuture().sync();
        } finally {
            parent.shutdownGracefully();
            child.shutdownGracefully();
        }
    }
}
