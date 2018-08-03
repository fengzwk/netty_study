package com.study.netty.bigfile;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.FileRegion;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.CharsetUtil;
import org.junit.Test;

public class TestMain {

    @Test
    public void testBigFileHandler(){
        EmbeddedChannel channel = new EmbeddedChannel(new BigFileHandlerFileRegion());

        String filepath = "E:\\document\\Hibernate实战（第2版）.pdf";
        ByteBuf byteBuf = Unpooled.copiedBuffer(filepath, CharsetUtil.UTF_8);
        channel.writeInbound(byteBuf);

        FileRegion o = channel.readOutbound();//有文件从channelread方法中输出，所以要读取出栈数据
    }
}
