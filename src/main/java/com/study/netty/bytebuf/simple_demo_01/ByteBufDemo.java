package com.study.netty.bytebuf.simple_demo_01;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledDirectByteBuf;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zhang on 2018/6/19.
 */
public class ByteBufDemo {

    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes("hello".getBytes());
        System.out.println(ByteBufUtil.hexDump(byteBuf));
    }
}
