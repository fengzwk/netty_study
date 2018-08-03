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

public class ClientMarshallingInitializer extends ChannelInitializer<SocketChannel> {

    private final MarshallerProvider marshallerProvider;

    private final UnmarshallerProvider unmarshallerProvider;

    public ClientMarshallingInitializer(MarshallerProvider marshallerProvider, UnmarshallerProvider unmarshallerProvider) {
        this.marshallerProvider = marshallerProvider;
        this.unmarshallerProvider = unmarshallerProvider;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MarshallingEncoder(marshallerProvider));
        pipeline.addLast(new MarshallingDecoder(unmarshallerProvider));
        pipeline.addLast(new ObjectHandler());
    }

    public static final class ObjectHandler extends SimpleChannelInboundHandler<Serializable> {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            Student student = new Student();
            student.setAddress("beijing");
            student.setAge(23);
            student.setName("张三");

            ctx.writeAndFlush(student);
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Serializable msg) throws Exception {

        }
    }
}
