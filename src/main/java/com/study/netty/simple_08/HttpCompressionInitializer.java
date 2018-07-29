package com.study.netty.simple_08;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;

public class HttpCompressionInitializer extends ChannelInitializer<SocketChannel> {

    private final boolean isClient;

    public HttpCompressionInitializer(boolean isClient){
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        if(isClient){
            pipeline.addLast(new HttpClientCodec());
            pipeline.addLast(new HttpContentDecompressor()); //对服务器端的消息进行解压缩
        }else{
            pipeline.addLast(new HttpServerCodec());
            pipeline.addLast(new HttpContentCompressor()); //服务器压缩数据
        }
    }
}
