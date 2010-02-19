package com.hzjbbis.db.batch.event.adapt;

import com.hzjbbis.db.batch.AsyncService;
import com.hzjbbis.db.batch.BaseBpEventHandler;
import com.hzjbbis.db.batch.event.BpReadTaskEvent;
import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.message.IMessage;

public class BaseReadTaskHandler extends BaseBpEventHandler {
    protected static final EventType type;

    static {
        type = EventType.BP_READ_TASK;
    }

    public void handleEvent(IEvent event) {
        if ((!($assertionsDisabled)) && (event.getType() != type)) throw new AssertionError();
        BpReadTaskEvent e = (BpReadTaskEvent) event;
        handleReadTask(e.getService(), e.getMessage());
    }

    public void handleReadTask(AsyncService service, IMessage msg) {
    }

    public EventType type() {
        return type;
    }
}