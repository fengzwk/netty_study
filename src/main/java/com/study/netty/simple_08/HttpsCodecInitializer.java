package com.study.netty.simple_08;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

public class HttpsCodecInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext context;

    private final boolean isClient;

    public HttpsCodecInitializer(SslContext context, boolean isClient) {
        this.context = context;
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        SSLEngine sslEngine = context.newEngine(ch.alloc());
        pipeline.addLast(new SslHandler(sslEngine));

        /***ssl + http 就是https*/
        if(isClient){
            pipeline.addLast(new HttpClientCodec());
        }else{
            pipeline.addLast(new HttpServerCodec());
        }
    }
}
