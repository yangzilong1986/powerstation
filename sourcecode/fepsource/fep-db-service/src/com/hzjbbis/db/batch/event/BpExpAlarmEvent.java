package com.hzjbbis.db.batch.event;

import com.hzjbbis.db.batch.AsyncService;
import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.message.IMessage;

public class BpExpAlarmEvent implements IEvent {
    private static final EventType type = EventType.BP_EXP_ALARM;
    private IMessage message;
    private AsyncService service;

    public BpExpAlarmEvent(AsyncService service, IMessage msg) {
        this.service = service;
        this.message = msg;
    }

    public IMessage getMessage() {
        return this.message;
    }

    public AsyncService getService() {
        return this.service;
    }

    public AsyncService getSource() {
        return this.service;
    }

    public EventType getType() {
        return type;
    }

    public void setSource(Object src) {
    }
}