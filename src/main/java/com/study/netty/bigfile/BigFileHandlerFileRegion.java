package com.study.netty.bigfile;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;

import java.io.File;
import java.io.FileInputStream;

public class BigFileHandlerFileRegion extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
           ByteBuf buf = (ByteBuf)msg;
           byte[] bs = new byte[buf.readableBytes()];
           buf.readBytes(bs);

           /**借助于零拷贝，只能用于文件的直接复制传输。如果需要将文件的数据复制到用户内存
            * 则建议使用ChunkedWriteHandler
            * */
           String filepath = new String(bs);
           File file = new File(filepath);
           FileInputStream fis = new FileInputStream(file);
           FileRegion fileRegion = new DefaultFileRegion(fis.getChannel(),0,file.length());

           ctx.channel().writeAndFlush(fileRegion)
                        .addListener(future->{
                           if(!future.isSuccess()){
                               System.out.println("复制失败");
                           }else{
                               System.out.println("复制成功");
                           }
                        });
    }
}
