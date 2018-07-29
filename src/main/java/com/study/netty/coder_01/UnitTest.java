package com.study.netty.coder_01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import static org.junit.Assert.*;

import io.netty.handler.codec.TooLongFrameException;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class UnitTest {

    @Test
    public void testFrameDecoder(){
        ByteBuf byteBuf = Unpooled.buffer();
        for(int i=0; i<9; i++){
            byteBuf.writeByte(i);
        }

        ByteBuf input = byteBuf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        assert channel.writeInbound(input.retain());
        assert  channel.finish();

        ByteBuf read = (ByteBuf)channel.readInbound();
        assertEquals(byteBuf.readSlice(3),read);
        read.release();

        read = (ByteBuf)channel.readInbound();
        assertEquals(byteBuf.readSlice(3),read);
        read.release();

        read = (ByteBuf)channel.readInbound();
        assertEquals(byteBuf.readSlice(3),read);
        read.release();

        assertNull(channel.readInbound());
        byteBuf.release();
    }

    @Test
    public void testFrameDecoder2(){
        ByteBuf byteBuf = Unpooled.buffer();
        for(int i=0; i<9; i++){
            byteBuf.writeByte(i);
        }

        ByteBuf input = byteBuf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        assertFalse(channel.writeInbound(input.readBytes(2)));
        assertTrue(channel.writeInbound(input.readBytes(7)));

        ByteBuf read = channel.readInbound();
        assertEquals(byteBuf.readSlice(3),read);
        read.release();

        read = channel.readInbound();
        assertEquals(byteBuf.readSlice(3),read);
        read.release();

        read = channel.readInbound();
        assertEquals(byteBuf.readSlice(3),read);
        read.release();

        assertNull(channel.readInbound());
        byteBuf.release();
    }

    @Test
    public void testAbsIntegerEcoder(){
        ByteBuf buf = Unpooled.buffer();
        for(int i=0; i<10; i++){
            buf.writeInt(-1*i);
        }

        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());
        assertTrue(channel.writeOutbound(buf));
        assertTrue(channel.finish());

        for(int i=0; i<10; i++){
            Object o = channel.readOutbound();
            assertEquals(i, o);
        }

        assertNull(channel.readOutbound());
    }

    @Test
    public void testFrameChunkDecoder(){
        ByteBuf byteBuf = Unpooled.buffer();
        for(int i=0; i<9; i++){
            byteBuf.writeByte(i);
        }

        ByteBuf input = byteBuf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(new FrameChunkDecoder(3));
        assertTrue(channel.writeInbound(input.readBytes(2)));
        try{
            channel.writeInbound(input.readBytes(4));
            fail();
        }catch (TooLongFrameException e){

        }

        assertTrue(channel.writeInbound(input.readBytes(3)));
        assertTrue(channel.finish());

        ByteBuf read =  (ByteBuf)channel.readInbound();
        assertEquals(byteBuf.readSlice(2),read);
        read.release();

        read = (ByteBuf)channel.readInbound();
        assertEquals(byteBuf.skipBytes(4).readSlice(3),read);
        read.release();
        byteBuf.release();
    }

    @Test
    public void testToIntegerDecoder() throws InterruptedException {
        String head = "this head!";
        String body = "this is body content: helloworld welcome";
        String end = "this end!!";

        ByteBuf headBuf = Unpooled.copiedBuffer(head,CharsetUtil.UTF_8);
        ByteBuf bodyBuf = Unpooled.copiedBuffer(body,CharsetUtil.UTF_8);
        ByteBuf endBuf = Unpooled.copiedBuffer(end,CharsetUtil.UTF_8);

        EmbeddedChannel channel = new EmbeddedChannel(new ToIntegerDecoder(DecoderEnum.HEAD));

        channel.writeInbound(headBuf.retain());
        TimeUnit.SECONDS.sleep(3);
        Object o = channel.readInbound();
        System.out.println(o);

        channel.writeInbound(bodyBuf.retain());
        TimeUnit.SECONDS.sleep(3);
        Object o1 = channel.readInbound();
        System.out.println(o1);

        channel.writeInbound(endBuf.retain());
        TimeUnit.SECONDS.sleep(3);
        Object o2 = channel.readInbound();
        System.out.println(o2);
    }

}
