package com.study.netty.lengthfield;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class LengthFieldHandlerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        /**lengthAdjustment 和 initialBytesToStrip尽量加上，避免出现不必要的换行符*/
        pipeline.addLast(new LengthFieldBasedFrameDecoder(18 ,0,4,-4,4));
        pipeline.addLast(new FrameHandler());
    }
}
