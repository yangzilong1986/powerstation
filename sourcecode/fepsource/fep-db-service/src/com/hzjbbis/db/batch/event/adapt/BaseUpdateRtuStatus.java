package com.hzjbbis.db.batch.event.adapt;

import com.hzjbbis.db.batch.BaseBpEventHandler;
import com.hzjbbis.db.batch.event.FeUpdateRtuStatus;
import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;

public class BaseUpdateRtuStatus extends BaseBpEventHandler {
    private static final EventType type;

    static {
        type = EventType.FE_RTU_CHANNEL;
    }

    public void handleEvent(IEvent event) {
        if ((!($assertionsDisabled)) && (event.getType() != type)) throw new AssertionError();
        FeUpdateRtuStatus ev = (FeUpdateRtuStatus) event;
        this.service.addToDao(ev.getRtu(), this.key);
    }

    public EventType type() {
        return type;
    }
}