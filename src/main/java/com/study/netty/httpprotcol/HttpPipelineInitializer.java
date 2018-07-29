package com.study.netty.httpprotcol;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class HttpPipelineInitializer extends ChannelInitializer<SocketChannel> {

    private boolean client;

    public HttpPipelineInitializer(boolean client){
        this.client = client;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if(client){
            pipeline.addLast(new HttpRequestEncoder());
            pipeline.addLast(new HttpResponseDecoder());
        }else{
            pipeline.addLast(new HttpRequestDecoder());
            pipeline.addLast(new HttpResponseEncoder());
        }
    }
}
