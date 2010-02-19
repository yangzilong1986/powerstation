package com.hzjbbis.fk.sockserver.event;

import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.common.spi.socket.IServerSideChannel;
import com.hzjbbis.fk.message.IMessage;

public class ClientWriteReqEvent implements IEvent {
    private final EventType type = EventType.CLIENT_WRITE_REQ;
    private IServerSideChannel client;

    public ClientWriteReqEvent(IServerSideChannel c) {
        this.client = c;
    }

    public EventType getType() {
        return this.type;
    }

    public void setType(EventType type) {
    }

    public final IServerSideChannel getClient() {
        return this.client;
    }

    public Object getSource() {
        return this.client.getServer();
    }

    public void setSource(Object src) {
    }

    public IMessage getMessage() {
        return null;
    }
}