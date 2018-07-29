package com.study.netty.codec_02;

import io.netty.channel.CombinedChannelDuplexHandler;

public class CombinedByteCharCodec extends CombinedChannelDuplexHandler<ByteToCharDecoder,CharToByteEncoder> {

    public CombinedByteCharCodec(){
        super(new ByteToCharDecoder(),new CharToByteEncoder());
    }

}