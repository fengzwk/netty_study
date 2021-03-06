package com.study.netty.sslhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

public class ClientInitialization extends ChannelInitializer<SocketChannel> {

    private final SslContext sslContext;

    private final boolean startTls;

    public ClientInitialization(SslContext sslContext, boolean startTls) {
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
