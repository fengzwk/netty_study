package com.study.netty.simple_08;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpAggregatorInitializer extends ChannelInitializer<SocketChannel> {

    private final boolean isClient;

    public HttpAggregatorInitializer(boolean isClient){
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if(isClient){
            pipeline.addLast(new HttpClientCodec());
        }else{
            pipeline.addLast(new HttpServerCodec());
        }

        pipeline.addLast(new HttpObjectAggregator(512*1024));
    }
}
