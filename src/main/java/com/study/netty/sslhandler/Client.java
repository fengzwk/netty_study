package com.study.netty.sslhandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContextBuilder;

public class Client {

    public static void main(String[] args) throws  Exception{
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                 .channel(NioSocketChannel.class)
                 .handler(new ClientInitialization(SslContextBuilder.forClient().build(),true));

        ChannelFuture future = bootstrap.connect("localhost", 8899).sync();
        future.channel().closeFuture().sync();
    }
}
