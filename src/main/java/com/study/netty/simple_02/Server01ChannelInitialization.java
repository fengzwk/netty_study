package com.study.netty.simple_02;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class Server01ChannelInitialization extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new Server01ChannelHandler());
    }
}
