package com.hzjbbis.fk.common.spi;

import com.hzjbbis.fk.common.EventType;
import com.hzjbbis.fk.message.IMessage;

public abstract interface IEvent {
    public abstract EventType getType();

    public abstract Object getSource();

    public abstract void setSource(Object paramObject);

    public abstract IMessage getMessage();
}