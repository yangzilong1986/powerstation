package com.hzjbbis.fas.protocol.codec;

import com.hzjbbis.fas.protocol.conf.ProtocolDataConfig;
import com.hzjbbis.fk.message.IMessage;

public abstract interface MessageDecoder {
    public abstract void setDataConfig(ProtocolDataConfig paramProtocolDataConfig);

    public abstract Object decode(IMessage paramIMessage);
}