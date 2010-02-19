package com.hzjbbis.fas.protocol.gw.codec;

import com.hzjbbis.fas.protocol.codec.MessageDecoder;
import com.hzjbbis.fas.protocol.conf.ProtocolDataConfig;
import com.hzjbbis.fk.message.IMessage;

public abstract class AbstractMessageDecoder implements MessageDecoder {
    protected ProtocolDataConfig dataConfig;

    public void setDataConfig(ProtocolDataConfig dataConfig) {
        this.dataConfig = dataConfig;
    }

    public abstract Object decode(IMessage paramIMessage);
}