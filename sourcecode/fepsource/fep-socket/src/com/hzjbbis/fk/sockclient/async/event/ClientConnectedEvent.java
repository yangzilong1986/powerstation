package com.hzjbbis.fk.sockclient.async.event;

import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.socket.IChannel;
import com.hzjbbis.fk.common.spi.socket.ISocketServer;
import com.hzjbbis.fk.message.IMessage;

public class ClientConnectedEvent implements IEvent {
    private final EventType type = EventType.CLIENT_CONNECTED;
    private ISocketServer server;
    private IChannel client;

    public ClientConnectedEvent(ISocketServer s, IChannel c) {
        this.server = s;
        this.client = c;
    }

    public IMessage getMessage() {
        return null;
    }

    public Object getSource() {
        return this.server;
    }

    public EventType getType() {
        return this.type;
    }

    public void setSource(Object src) {
    }

    public ISocketServer getServer() {
        return this.server;
    }

    public IChannel getClient() {
        return this.client;
    }

    public String toString() {
        return "ClientConnectedEvent,client=" + this.client.getPeerAddr();
    }
}