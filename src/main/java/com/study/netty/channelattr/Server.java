package com.study.netty.channelattr;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

    public static void main(String[] args) {
        EventLoopGroup parent = new NioEventLoopGroup(1);
        EventLoopGroup child = new NioEventLoopGroup(10);

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parent,child)
                     .childHandler(new ServerChannelInitialization())
                     .channel(NioServerSocketChannel.class);

            ChannelFuture future = bootstrap.bind(8899).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            parent.shutdownGracefully();
            child.shutdownGracefully();
        }
    }
}
