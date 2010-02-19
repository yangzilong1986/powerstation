package com.hzjbbis.fk.common.events.event;

import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.message.IMessage;

public class KillThreadEvent implements IEvent {
    private final EventType type = EventType.SYS_KILLTHREAD;

    public Object getSource() {
        return null;
    }

    public EventType getType() {
        return this.type;
    }

    public void setSource(Object src) {
    }

    public void setType(EventType type) {
    }

    public IMessage getMessage() {
        return null;
    }
}