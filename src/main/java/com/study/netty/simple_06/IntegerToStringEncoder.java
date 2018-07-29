package com.study.netty.simple_06;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class IntegerToStringEncoder extends MessageToMessageEncoder<Integer> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
        System.out.println(msg);
          out.add(String.valueOf(msg));
    }
}
