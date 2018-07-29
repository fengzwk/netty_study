package com.study.netty.coder_01;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

public class ToIntegerDecoder extends ReplayingDecoder<DecoderEnum> {

    public ToIntegerDecoder(DecoderEnum decoderEnum){
        super(decoderEnum);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(state() == DecoderEnum.HEAD){
            CharSequence charSequence = in.readCharSequence(10, CharsetUtil.UTF_8);
            out.add(charSequence);
            System.out.println("head read finished!!");
            state(DecoderEnum.BODY);
        }else if(state() == DecoderEnum.BODY){
            CharSequence charSequence = in.readCharSequence(40,CharsetUtil.UTF_8);
            out.add(charSequence);
            System.out.println("body read finished!!");
            state(DecoderEnum.END);
        }else if(state() == DecoderEnum.END){
            CharSequence charSequence = in.readCharSequence(10,CharsetUtil.UTF_8);
            out.add(charSequence);
            System.out.println("end read finished!!");
            state(DecoderEnum.HEAD);
        }
    }
}
