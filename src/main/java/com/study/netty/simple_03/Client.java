package com.study.netty.simple_03;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

public class Client {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup(1);
        AttributeKey<Integer> id = AttributeKey.newInstance("ID");

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                     .channel(NioSocketChannel.class)
                     .handler(new ClientChannelInitialization())
                     .option(ChannelOption.SO_TIMEOUT,5000)
                     .option(ChannelOption.SO_KEEPALIVE,true)
                     .attr(id,123456);


            ChannelFuture future = bootstrap.connect("localhost", 8899);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
