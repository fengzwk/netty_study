package com.study.netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;

import java.net.InetSocketAddress;

public class Client {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup(1);

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioDatagramChannel.class)
                     .group(group)
                     .option(ChannelOption.SO_BROADCAST,true)
                     .handler(new SimpleChannelInboundHandler<DatagramPacket>() {
                         @Override
                         protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
                             String response = packet.content().toString(CharsetUtil.UTF_8);
                             System.out.println(response);
                         }
                     });

            ChannelFuture future = bootstrap.bind(new InetSocketAddress(8080)).sync();
            future.channel().writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("谚语字典查询？",
                       CharsetUtil.UTF_8), new InetSocketAddress("255.255.255.255",8899))).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Future<?> future = group.shutdownGracefully();
            future.addListener((returnFuture)->{
                System.out.println("资源关闭");
            });
        }
    }
}
