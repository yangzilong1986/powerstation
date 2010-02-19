package com.hzjbbis.fas.protocol.conf;

public class ProtocolHandlerConfig {
    private String messageType;
    private String handlerClass;
    private CodecFactoryConfig codecFactory;

    public String getMessageType() {
        return this.messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getHandlerClass() {
        return this.handlerClass;
    }

    public void setHandlerClass(String handlerClass) {
        this.handlerClass = handlerClass;
    }

    public CodecFactoryConfig getCodecFactory() {
        return this.codecFactory;
    }

    public void setCodecFactory(CodecFactoryConfig codecFactory) {
        this.codecFactory = codecFactory;
    }
}