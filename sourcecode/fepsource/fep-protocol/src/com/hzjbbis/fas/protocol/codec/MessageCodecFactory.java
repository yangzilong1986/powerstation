package com.hzjbbis.fas.protocol.codec;

import com.hzjbbis.fas.protocol.conf.CodecFactoryConfig;

public abstract interface MessageCodecFactory {
    public abstract void setConfig(CodecFactoryConfig paramCodecFactoryConfig);

    public abstract MessageEncoder getEncoder(int paramInt);

    public abstract MessageDecoder getDecoder(int paramInt);
}