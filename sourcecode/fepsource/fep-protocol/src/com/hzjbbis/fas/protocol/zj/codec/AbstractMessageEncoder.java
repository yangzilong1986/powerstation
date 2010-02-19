package com.hzjbbis.fas.protocol.zj.codec;

import com.hzjbbis.fas.protocol.codec.MessageEncoder;
import com.hzjbbis.fas.protocol.conf.ProtocolDataConfig;
import com.hzjbbis.fk.message.IMessage;

public abstract class AbstractMessageEncoder implements MessageEncoder {
    protected ProtocolDataConfig dataConfig;

    public void setDataConfig(ProtocolDataConfig dataConfig) {
        this.dataConfig = dataConfig;
    }

    public abstract IMessage[] encode(Object paramObject);
}