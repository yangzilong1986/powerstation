package com.hzjbbis.db.batch.event;

import com.hzjbbis.db.batch.dao.IBatchDao;
import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.common.spi.IEvent;
import com.hzjbbis.fk.message.IMessage;

public class BpBatchDelayEvent implements IEvent {
    private static final EventType type = EventType.BP_BATCH_DELAY;
    private IBatchDao dao;

    public BpBatchDelayEvent(IBatchDao dao) {
        this.dao = dao;
    }

    public IMessage getMessage() {
        return null;
    }

    public Object getSource() {
        return null;
    }

    public EventType getType() {
        return type;
    }

    public void setSource(Object src) {
    }

    public IBatchDao getDao() {
        return this.dao;
    }

    public void setDao(IBatchDao dao) {
        this.dao = dao;
    }
}