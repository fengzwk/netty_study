package com.study.netty.serializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.serial.SerialMarshallerFactory;

public class Server {

    public static void main(String[] args) throws Exception{
        EventLoopGroup parent = new NioEventLoopGroup(1);
        EventLoopGroup child = new NioEventLoopGroup(10);

        SerialMarshallerFactory factory = new SerialMarshallerFactory();
        MarshallingConfiguration configuration = new MarshallingConfiguration();
        MarshallerProvider defaultMarshallerProvider = new DefaultMarshallerProvider(factory,configuration);
        UnmarshallerProvider unmarshallerProvider = new DefaultUnmarshallerProvider(factory,configuration);

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(parent,child)
                       .channel(NioServerSocketChannel.class)
                       .childHandler(new ServerMarshallingInitializer(defaultMarshallerProvider,unmarshallerProvider));
        ChannelFuture future = serverBootstrap.bind(8090).sync();
        future.channel().closeFuture().sync();

    }
}
