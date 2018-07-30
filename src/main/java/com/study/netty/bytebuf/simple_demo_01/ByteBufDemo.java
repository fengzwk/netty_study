package com.study.netty.bytebuf.simple_demo_01;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledDirectByteBuf;
import io.netty.util.CharsetUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhang on 2018/6/19.
 */
public class ByteBufDemo {

    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8);
        ByteBuf length = Unpooled.copyInt(11);

        ByteBuf slice = byteBuf.slice(0, 20);
        byte[] bs = new byte[slice.readableBytes()];
        slice.readBytes(bs);
        System.out.println(bs);
    }
}
