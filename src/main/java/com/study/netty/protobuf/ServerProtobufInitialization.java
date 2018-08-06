package com.study.netty.protobuf;

import com.google.protobuf.MessageLite;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

public class ServerProtobufInitialization extends ChannelInitializer<SocketChannel> {

    private MessageLite messageLite;

    public ServerProtobufInitialization(MessageLite messageLite) {
        this.messageLite = messageLite;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        pipeline.addLast(new ProtobufEncoder());
        pipeline.addLast(new ProtobufDecoder(messageLite));
        pipeline.addLast(new ObjectHandler());
    }

    public static final class ObjectHandler extends SimpleChannelInboundHandler<com.study.netty.protobuf.Student.StudentDefine> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, com.study.netty.protobuf.Student.StudentDefine msg) throws Exception {
            Student.StudentDefine student = (Student.StudentDefine)msg;
            System.out.println(student);
        }
    }
}
