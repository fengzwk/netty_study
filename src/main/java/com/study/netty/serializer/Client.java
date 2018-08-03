package com.study.netty.serializer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.serial.SerialMarshallerFactory;

public class Client {

    public static void main(String[] args) throws  Exception{
        EventLoopGroup group = new NioEventLoopGroup();

        SerialMarshallerFactory factory = new SerialMarshallerFactory();
        MarshallingConfiguration configuration = new MarshallingConfiguration();
        MarshallerProvider defaultMarshallerProvider = new DefaultMarshallerProvider(factory,configuration);
        UnmarshallerProvider unmarshallerProvider = new DefaultUnmarshallerProvider(factory,configuration);

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ClientMarshallingInitializer(defaultMarshallerProvider,unmarshallerProvider));

        ChannelFuture future = bootstrap.connect("localhost", 8090).sync();
        future.channel().closeFuture().sync();
    }
}
