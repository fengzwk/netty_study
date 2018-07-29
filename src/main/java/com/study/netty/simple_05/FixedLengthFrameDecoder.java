package com.study.netty.simple_05;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class FixedLengthFrameDecoder extends ByteToMessageDecoder {

    private int frameLength;

    public FixedLengthFrameDecoder(int frameLength){
        if(frameLength <= 0 ){
            return;
        }
        this.frameLength = frameLength;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            while(in.readableBytes() >= frameLength){
                ByteBuf byteBuf = in.readBytes(frameLength);
                out.add(byteBuf);
            }
    }
}
