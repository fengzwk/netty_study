package com.study.netty.channelattr;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ServerChannelInitialization extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new ServerChannelHandler());
    }
}
