package com.hzjbbis.fk.common.events;

import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.message.IMessage;

public class MessageParseErrorEvent implements IEvent {
    private final EventType type = EventType.MSG_PARSE_ERROR;
    private IMessage message;
    private Object source;
    private String info;

    public MessageParseErrorEvent(IMessage msg) {
        this.message = msg;
        this.source = msg.getSource();
    }

    public MessageParseErrorEvent(IMessage msg, String info) {
        this.message = msg;
        this.source = msg.getSource();
        this.info = info;
    }

    public IMessage getMessage() {
        return this.message;
    }

    public Object getSource() {
        return this.source;
    }

    public EventType getType() {
        return this.type;
    }

    public void setSource(Object src) {
        this.source = src;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(512);
        sb.append("MessageParseErrorEvent,source=").append(this.source);
        sb.append(",packet=").append(this.message.getRawPacketString());
        if (this.info != null) sb.append(",info=").append(this.info);
        return sb.toString();
    }
}