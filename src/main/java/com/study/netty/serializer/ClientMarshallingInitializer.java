package com.study.netty.serializer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

import java.io.Serializable;

public class ServerMarshallingInitializer extends ChannelInitializer<SocketChannel> {

    private final MarshallerProvider marshallerProvider;

    private final UnmarshallerProvider unmarshallerProvider;

    public ServerMarshallingInitializer(MarshallerProvider marshallerProvider, UnmarshallerProvider unmarshallerProvider) {
        this.marshallerProvider = marshallerProvider;
        this.unmarshallerProvider = unmarshallerProvider;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MarshallingEncoder(marshallerProvider));
        pipeline.addLast(new MarshallingDecoder(unmarshallerProvider));
    }

    public static final class ObjectHandler extends SimpleChannelInboundHandler<Serializable> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Serializable msg) throws Exception {
            Student student = (Student)msg;
            System.out.println(student);
        }
    }
}
