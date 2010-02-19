package com.hzjbbis.fk.sockserver.event;

import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.sockserver.AsyncSocketClient;
import com.hzjbbis.fk.sockserver.message.SimpleMessage;

public class RecvSimpleMessageEvent implements IEvent {
    private EventType type = EventType.MSG_SIMPLE_RECV;
    private SimpleMessage message;
    private AsyncSocketClient client;

    public RecvSimpleMessageEvent(IMessage m) {
        this.message = ((SimpleMessage) m);
        this.client = ((AsyncSocketClient) m.getSource());
    }

    public Object getSource() {
        return this.client.getServer();
    }

    public EventType getType() {
        return this.type;
    }

    public void setSource(Object src) {
    }

    public void setType(EventType type) {
    }

    public SimpleMessage getMessage() {
        return this.message;
    }

    public void setMessage(SimpleMessage message) {
        this.message = message;
    }

    public AsyncSocketClient getClient() {
        return this.client;
    }

    public void setClient(AsyncSocketClient client) {
        this.client = client;
    }
}