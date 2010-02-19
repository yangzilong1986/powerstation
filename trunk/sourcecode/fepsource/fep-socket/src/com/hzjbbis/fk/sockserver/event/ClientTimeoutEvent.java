package com.hzjbbis.fk.sockserver.event;

import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.socket.IChannel;
import com.hzjbbis.fk.common.spi.socket.ISocketServer;
import com.hzjbbis.fk.message.IMessage;

public class ClientTimeoutEvent implements IEvent {
    private final EventType type = EventType.CLIENTTIMEOUT;
    private ISocketServer server;
    private IChannel client;

    public ClientTimeoutEvent(IChannel c) {
        this.client = c;
        this.server = c.getServer();
    }

    public EventType getType() {
        return this.type;
    }

    public void setType(EventType type) {
    }

    public final ISocketServer getServer() {
        return this.server;
    }

    public final IChannel getClient() {
        return this.client;
    }

    public Object getSource() {
        return this.server;
    }

    public void setSource(Object src) {
    }

    public IMessage getMessage() {
        return null;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(128);
        sb.append("client timeout event. client=").append(this.client);
        sb.append(",server=").append(this.server.getPort());
        return sb.toString();
    }
}