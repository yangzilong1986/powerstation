package com.hzjbbis.db.batch.event.adapt;

import com.hzjbbis.db.batch.BaseBpEventHandler;
import com.hzjbbis.db.batch.event.BpBatchDelayEvent;
import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;

public class BatchDelayHandler extends BaseBpEventHandler {
    private static final EventType type;

    static {
        type = EventType.BP_BATCH_DELAY;
    }

    public void handleEvent(IEvent event) {
        if ((!($assertionsDisabled)) && (event.getType() != type)) throw new AssertionError();
        BpBatchDelayEvent ev = (BpBatchDelayEvent) event;
        ev.getDao().batchUpdate();
    }

    public EventType type() {
        return type;
    }
}