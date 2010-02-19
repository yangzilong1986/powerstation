package com.hzjbbis.fas.protocol.codec;

import com.hzjbbis.fas.protocol.handler.ProtocolHandler;
import com.hzjbbis.fas.protocol.handler.ProtocolHandlerFactory;

public abstract class MessageCodecUtil {
    private static final ProtocolHandlerFactory handlerFactory = ProtocolHandlerFactory.getInstance();

    private static MessageEncoder getEncoder(Class messageType, int funCode) {
        ProtocolHandler handler = handlerFactory.getProtocolHandler(messageType);
        return handler.getCodecFactory().getEncoder(funCode);
    }

    private static MessageDecoder getDecoder(Class messageType, int funCode) {
        ProtocolHandler handler = handlerFactory.getProtocolHandler(messageType);
        return handler.getCodecFactory().getDecoder(funCode);
    }
}