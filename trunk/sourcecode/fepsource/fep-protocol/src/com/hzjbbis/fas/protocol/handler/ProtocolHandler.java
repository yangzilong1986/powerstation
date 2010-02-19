package com.hzjbbis.fas.protocol.handler;

import com.hzjbbis.fas.model.FaalRequest;
import com.hzjbbis.fas.protocol.codec.MessageCodecFactory;
import com.hzjbbis.fk.message.IMessage;

public abstract interface ProtocolHandler {
    public abstract void setCodecFactory(MessageCodecFactory paramMessageCodecFactory);

    public abstract MessageCodecFactory getCodecFactory();

    public abstract Object process(IMessage paramIMessage);

    public abstract IMessage[] createMessage(FaalRequest paramFaalRequest);
}