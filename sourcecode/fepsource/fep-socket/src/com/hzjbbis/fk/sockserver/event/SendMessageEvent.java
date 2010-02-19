package com.hzjbbis.fk.sockserver.event;

import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.socket.IChannel;
import com.hzjbbis.fk.common.spi.socket.ISocketServer;
import com.hzjbbis.fk.message.IMessage;

public class SendMessageEvent implements IEvent {
    private final EventType type = EventType.MSG_SENT;
    private IMessage message;
    private IChannel client;
    private ISocketServer server;

    public SendMessageEvent(IMessage m, IChannel c) {
        this.message = m;
        this.client = c;
        this.server = c.getServer();
    }

    public Object getSource() {
        return this.server;
    }

    public EventType getType() {
        return this.type;
    }

    public void setSource(Object src) {
    }

    public final IMessage getMessage() {
        return this.message;
    }

    public final IChannel getClient() {
        return this.client;
    }

    public final ISocketServer getServer() {
        return this.server;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(1024);
        sb.append("send event. server=").append(this.server.getPort()).append(",client=");
        sb.append(this.client).append(",发送:");
        sb.append(this.message);
        return sb.toString();
    }
}