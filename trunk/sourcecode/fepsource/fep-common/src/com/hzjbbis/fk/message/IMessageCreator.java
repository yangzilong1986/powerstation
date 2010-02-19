package com.hzjbbis.fk.message;

public abstract interface IMessageCreator {
    public abstract IMessage create();

    public abstract IMessage createHeartBeat(int paramInt);
}