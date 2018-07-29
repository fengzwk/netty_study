package com.study.netty.simple_02;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server01 {

    public static void main(String[] args) throws Exception {
        EventLoopGroup parent = new NioEventLoopGroup(1);
        EventLoopGroup child = new NioEventLoopGroup(10);

        try {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(parent,child)
                       .channel(NioServerSocketChannel.class)
                       .childHandler(new Server01ChannelInitialization());

        ChannelFuture future = serverBootstrap.bind(8899).sync();
        future.channel().closeFuture().sync();
        } finally {
            parent.shutdownGracefully();
            child.shutdownGracefully();
        }
    }
}
