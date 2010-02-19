package com.hzjbbis.fk.sockserver.event;

import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.socket.ISocketServer;
import com.hzjbbis.fk.message.IMessage;

public class ServerStoppedEvent implements IEvent {
    private final EventType type = EventType.SERVERSTOPPED;
    private ISocketServer server = null;

    public ServerStoppedEvent(ISocketServer s) {
        this.server = s;
    }

    public EventType getType() {
        return this.type;
    }

    public void setType(EventType type) {
    }

    public final ISocketServer getServer() {
        return this.server;
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
        sb.append("server stopped event. server=").append(this.server);
        return sb.toString();
    }
}