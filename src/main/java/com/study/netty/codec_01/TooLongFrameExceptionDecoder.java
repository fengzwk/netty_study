package com.study.netty.codec_01;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

public class TooLongFrameExceptionDecoder extends ByteToMessageDecoder {

    private final int MAX_FRAME_SIZE = 10;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() >= MAX_FRAME_SIZE){
            in.skipBytes(in.readableBytes());
            throw  new TooLongFrameException();
        }
        out.add(in.readBytes(in.readableBytes()));
    }
}
