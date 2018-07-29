package com.study.netty.simple_07;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

public class ServerInitialization extends ChannelInitializer<SocketChannel> {
    private final SslContext sslContext;

    private final boolean startTls;

    public ServerInitialization(SslContext sslContext, boolean startTls) {
        this.sslContext = sslContext;
        this.startTls = startTls;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        SSLEngine sslEngine = sslContext.newEngine(ch.alloc());
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new SslHandler(sslEngine,startTls));
    }
}
