package com.hzjbbis.fk.sockserver.event;

import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.socket.IChannel;
import com.hzjbbis.fk.message.IMessage;

public class MessageSendFailEvent implements IEvent {
    private final EventType type = EventType.MSG_SEND_FAIL;
    private IChannel client;
    private IMessage message;

    public MessageSendFailEvent(IMessage msg, IChannel c) {
        this.message = msg;
        this.client = c;
    }

    public EventType getType() {
        return this.type;
    }

    public void setType(EventType type) {
    }

    public IChannel getClient() {
        return this.client;
    }

    public IMessage getMessage() {
        return this.message;
    }

    public Object getSource() {
        return this.client.getServer();
    }

    public void setSource(Object src) {
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(1024);
        sb.append("message send failed event. client=");
        sb.append(this.client).append(",server=").append(this.client.getServer().getPort());
        sb.append(",messge=").append(this.message);
        return sb.toString();
    }
}