package com.hzjbbis.db.batch.event.adapt;

import com.hzjbbis.db.batch.AsyncService;
import com.hzjbbis.db.batch.BaseBpEventHandler;
import com.hzjbbis.db.batch.event.BpLog2DbEvent;
import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.message.IMessage;

public class BaseLog2DbHandler extends BaseBpEventHandler {
    protected static final EventType type;

    static {
        type = EventType.BP_LOG_DB;
    }

    public void handleEvent(IEvent event) {
        if ((!($assertionsDisabled)) && (event.getType() != type)) throw new AssertionError();
        BpLog2DbEvent e = (BpLog2DbEvent) event;
        handleLog2Db(e.getService(), e.getMessage());
    }

    public void handleLog2Db(AsyncService service, IMessage msg) {
    }

    public EventType type() {
        return type;
    }
}