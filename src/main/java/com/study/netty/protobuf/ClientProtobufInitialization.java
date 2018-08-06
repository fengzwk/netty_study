package com.study.netty.protobuf;

import com.google.protobuf.MessageLite;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClientProtobufInitialization extends ChannelInitializer<SocketChannel> {

    private MessageLite messageLite;

    public ClientProtobufInitialization(MessageLite messageLite) {
        this.messageLite = messageLite;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(new ProtobufDecoder(messageLite));
        pipeline.addLast(new ProtobufEncoder());
        pipeline.addLast(new ObjectHandler());
    }

    public static final class ObjectHandler extends SimpleChannelInboundHandler<Student.StudentDefine> {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            Student.StudentDefine studentDefine = Student.StudentDefine.newBuilder()
                                                    .setAge(23)
                                                    .setHight(200)
                                                    .setName("zhagsan")
                                                    .setWeight(234)
                                                    .addPostition("sale")
                                                    .addPostition("marker")
                                                    .addPostition("teacher").build();
            ctx.writeAndFlush(studentDefine);
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Student.StudentDefine msg) throws Exception {

        }
    }
}
