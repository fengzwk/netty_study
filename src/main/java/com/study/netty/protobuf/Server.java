package com.study.netty.protobuf;

import com.google.protobuf.MessageLite;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

    public static void main(String[] args) throws Exception{
        EventLoopGroup parent = new NioEventLoopGroup(1);
        EventLoopGroup child = new NioEventLoopGroup(10);

        MessageLite messageLite =   Student.StudentDefine.newBuilder().build();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(parent,child)
                       .channel(NioServerSocketChannel.class)
                       .childHandler(new ServerProtobufInitialization(messageLite));
        ChannelFuture future = serverBootstrap.bind(8090).sync();
        future.channel().closeFuture().sync();

    }
}
