package com.study.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.net.InetSocketAddress;

public class ChatServer {

    private final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);

    private final EventLoopGroup group = new NioEventLoopGroup();

    private Channel serverChannel;


    public ChannelFuture start(InetSocketAddress inetSocketAddress) throws Exception{
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(group)
                       .channel(NioServerSocketChannel.class)
                       .childHandler(createInitializer());
        ChannelFuture channelFuture = serverBootstrap.bind(inetSocketAddress).sync();
        serverChannel = channelFuture.channel();
        channelFuture.syncUninterruptibly();
        return channelFuture;
    }

    public ChannelInitializer createInitializer(){
        return new ChatServerInitializer(channelGroup);
    }

    public void destroy(){
        if(serverChannel != null){
            serverChannel.close();
        }
        channelGroup.close();
        group.shutdownGracefully();
    }

    public static void main(String[] args) throws Exception{
        ChatServer chatServer = new ChatServer();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8899);
        ChannelFuture future = chatServer.start(inetSocketAddress);

        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            chatServer.destroy();
        }));
        future.channel().closeFuture().syncUninterruptibly();
    }
}
