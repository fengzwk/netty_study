package com.study.netty.protobuf;

import com.google.protobuf.MessageLite;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
public class Client {

    public static void main(String[] args) throws  Exception{
        EventLoopGroup group = new NioEventLoopGroup();

        MessageLite messageLite =   Student.StudentDefine.newBuilder().build();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ClientProtobufInitialization(messageLite));

        ChannelFuture future = bootstrap.connect("localhost", 8090).sync();
        future.channel().closeFuture().sync();
    }
}
