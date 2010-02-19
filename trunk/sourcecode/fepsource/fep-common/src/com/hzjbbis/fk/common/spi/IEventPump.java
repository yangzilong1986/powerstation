package com.hzjbbis.fk.common.spi;

public abstract interface IEventPump {
    public abstract void post(IEvent paramIEvent);
}