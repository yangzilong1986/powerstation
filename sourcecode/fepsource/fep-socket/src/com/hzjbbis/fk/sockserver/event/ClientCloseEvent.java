package com.hzjbbis.fk.sockserver.event;

import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.socket.IServerSideChannel;
import com.hzjbbis.fk.common.spi.socket.ISocketServer;
import com.hzjbbis.fk.message.IMessage;

public class ClientCloseEvent implements IEvent {
    private final EventType type = EventType.CLIENTCLOSE;
    private ISocketServer server;
    private IServerSideChannel client;

    public ClientCloseEvent(IServerSideChannel c) {
        this.server = c.getServer();
        this.client = c;
    }

    public EventType getType() {
        return this.type;
    }

    public void setType(EventType type) {
    }

    public final ISocketServer getServer() {
        return this.server;
    }

    public final IServerSideChannel getClient() {
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
}