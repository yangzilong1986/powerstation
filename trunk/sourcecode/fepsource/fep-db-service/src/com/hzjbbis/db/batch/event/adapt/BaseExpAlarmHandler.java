package com.hzjbbis.db.batch.event.adapt;

import com.hzjbbis.db.batch.AsyncService;
import com.hzjbbis.db.batch.BaseBpEventHandler;
import com.hzjbbis.db.batch.event.BpExpAlarmEvent;
import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.message.IMessage;

public class BaseExpAlarmHandler extends BaseBpEventHandler {
    protected static final EventType type;

    static {
        type = EventType.BP_EXP_ALARM;
    }

    public EventType type() {
        return type;
    }

    public void handleEvent(IEvent event) {
        if ((!(event == null)) && (event.getType() != type)) throw new AssertionError();
        BpExpAlarmEvent e = (BpExpAlarmEvent) event;
        handleExpAlarm(e.getService(), e.getMessage());
    }

    public void handleExpAlarm(AsyncService service, IMessage msg) {
    }
}