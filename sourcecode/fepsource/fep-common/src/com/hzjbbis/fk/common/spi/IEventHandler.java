package com.hzjbbis.fk.common.spi;

public abstract interface IEventHandler {
    public abstract void handleEvent(IEvent paramIEvent);
}