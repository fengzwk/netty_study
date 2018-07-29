package com.study.netty.simple_06;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import static org.junit.Assert.*;
import org.junit.Test;

public class UnitTest {

    @Test
    public void testShortToByteEncoder(){
        EmbeddedChannel channel = new EmbeddedChannel(new ShortToByteEncoder());
        channel.writeOutbound(Short.valueOf("1"));
        channel.writeOutbound(Short.valueOf("2"));
        channel.writeOutbound(Short.valueOf("3"));
        channel.writeOutbound(Short.valueOf("4"));

        channel.finish();
        ByteBuf one = Unpooled.copyShort(1);
        ByteBuf two = Unpooled.copyShort(2);
        ByteBuf three = Unpooled.copyShort(3);
        ByteBuf four = Unpooled.copyShort(4);

        assertEquals(one,channel.readOutbound());
        assertEquals(two,channel.readOutbound());
        assertEquals(three,channel.readOutbound());
        assertEquals(four,channel.readOutbound());
    }

    @Test
    public void testIntegerToStringEncoder(){
        EmbeddedChannel channel = new EmbeddedChannel(new IntegerToStringEncoder());

        channel.writeOutbound(1);
        channel.writeOutbound(2);
        channel.writeOutbound(3);
        channel.writeOutbound(4);

        String one =String.valueOf(1);
        String two = String.valueOf(2);
        String three = String.valueOf(3);
        String four = String.valueOf(4);

        assertEquals(one,channel.readOutbound());
        assertEquals(two,channel.readOutbound());
        assertEquals(three,channel.readOutbound());
        assertEquals(four,channel.readOutbound());
    }

    @Test
    public void testCombineCodec(){
        EmbeddedChannel channel = new EmbeddedChannel(new CombinedByteCharCodec());
        ByteBuf one = Unpooled.buffer().writeChar(1);
        ByteBuf two = Unpooled.buffer().writeChar(2);
        ByteBuf three = Unpooled.buffer().writeChar(3);
        ByteBuf four = Unpooled.buffer().writeChar(4);

        channel.writeInbound(one);
        channel.writeInbound(two);
        channel.writeInbound(three);
        channel.writeInbound(four);

        Character char1 = 1;
        Character char2 = 2;
        Character char3 = 3;
        Character char4 = 4;

        assertEquals(char1,channel.readInbound());
        assertEquals(char2,channel.readInbound());
        assertEquals(char3,channel.readInbound());
        assertEquals(char4,channel.readInbound());

        channel.writeOutbound(char1);
        channel.writeOutbound(char2);
        channel.writeOutbound(char3);
        channel.writeOutbound(char4);

        ByteBuf oneout = Unpooled.buffer().writeChar(1);
        ByteBuf twoout = Unpooled.buffer().writeChar(2);
        ByteBuf threeout = Unpooled.buffer().writeChar(3);
        ByteBuf fourout = Unpooled.buffer().writeChar(4);

        assertEquals(oneout,channel.readOutbound());
        assertEquals(twoout,channel.readOutbound());
        assertEquals(threeout,channel.readOutbound());
        assertEquals(fourout,channel.readOutbound());


    }
}