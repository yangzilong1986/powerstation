package com.hzjbbis.fas.protocol.conf;

import java.util.List;

public class ProtocolProviderConfig {
    private List handlers;

    public ProtocolHandlerConfig getProtocolHandlerConfig(String messageType) {
        if (this.handlers == null) {
            return null;
        }

        for (int i = 0; i < this.handlers.size(); ++i) {
            ProtocolHandlerConfig handler = (ProtocolHandlerConfig) this.handlers.get(i);
            if (handler.getMessageType().equals(messageType)) {
                return handler;
            }
        }

        return null;
    }

    public List getHandlers() {
        return this.handlers;
    }

    public void setHandlers(List handlers) {
        this.handlers = handlers;
    }
}