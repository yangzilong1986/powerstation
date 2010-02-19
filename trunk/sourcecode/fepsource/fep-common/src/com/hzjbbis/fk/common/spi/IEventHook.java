package com.hzjbbis.fk.common.spi;

public abstract interface IEventHook extends IEventHandler {
    public abstract void setSource(Object paramObject);

    public abstract void postEvent(IEvent paramIEvent);

    public abstract boolean start();

    public abstract void stop();

    public abstract String profile();

    public abstract long getLastEventTime();

    public abstract void setEventTrace(IEventTrace paramIEventTrace);
}