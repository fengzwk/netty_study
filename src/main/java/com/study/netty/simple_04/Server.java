package com.study.netty.simple_04;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.oio.OioDatagramChannel;
import io.netty.util.CharsetUtil;

public class Server {

    public static void main(String[] args) {
        EventLoopGroup parent =new NioEventLoopGroup(1);

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(parent)
                     .channel(NioDatagramChannel.class)
                     .option(ChannelOption.SO_BROADCAST,true)
                     .handler(new SimpleChannelInboundHandler<DatagramPacket>() {
                         @Override
                         protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
                             String req = packet.content().toString(CharsetUtil.UTF_8);//上面说了，通过content()来获取消息内容
                             System.out.println(req);
                             ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("谚语查询结果：1",
                                                    CharsetUtil.UTF_8), packet.sender()));
                         }
                     });

            ChannelFuture future = bootstrap.bind(8899);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            parent.shutdownGracefully();
        }
    }
}
