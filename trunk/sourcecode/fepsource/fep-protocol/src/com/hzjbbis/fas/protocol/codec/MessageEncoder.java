package com.hzjbbis.fas.protocol.codec;

import com.hzjbbis.fas.protocol.conf.ProtocolDataConfig;
import com.hzjbbis.fk.message.IMessage;

public abstract interface MessageEncoder {
    public abstract void setDataConfig(ProtocolDataConfig paramProtocolDataConfig);

    public abstract IMessage[] encode(Object paramObject);
}