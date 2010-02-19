package com.hzjbbis.fk.sockserver.event;

import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.socket.IChannel;
import com.hzjbbis.fk.common.spi.socket.ISocketServer;
import com.hzjbbis.fk.message.IMessage;

public class ReceiveMessageEvent implements IEvent {
    private final EventType type = EventType.MSG_RECV;
    private IMessage message;
    private IChannel client;
    private ISocketServer server;

    public ReceiveMessageEvent(IMessage m, IChannel c) {
        this.message = m;
        this.client = c;
        this.server = c.getServer();
    }

    public Object getSource() {
        return ((this.server != null) ? this.server : this.client);
    }

    public EventType getType() {
        return this.type;
    }

    public void setSource(Object src) {
    }

    public final IMessage getMessage() {
        return this.message;
    }

    public final void setMessage(IMessage msg) {
        this.message = msg;
    }

    public final IChannel getClient() {
        return this.client;
    }

    public final ISocketServer getServer() {
        return this.server;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(1024);
        if (this.server != null) sb.append("recv event. server=").append(this.server.getPort()).append(",client=");
        else sb.append("recv event. client=");
        sb.append(this.client).append(",接收:");
        sb.append(this.message);
        return sb.toString();
    }
}