package com.study.netty.lengthfield;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

    public static void main(String[] args) throws Exception {
        EventLoopGroup parent = new NioEventLoopGroup(1);
        EventLoopGroup child = new NioEventLoopGroup(10);

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(parent,child)
                 .channel(NioServerSocketChannel.class)
                 .childHandler(new LengthFieldHandlerInitializer());

        ChannelFuture future = bootstrap.bind(8893).sync();
        future.channel().closeFuture().sync();
    }
}
