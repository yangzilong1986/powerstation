package com.hzjbbis.fk.common.spi;

import com.hzjbbis.fk.message.IMessage;

public abstract interface IMessageQueue {
    public abstract boolean sendMessage(IMessage paramIMessage);

    public abstract IMessage take() throws InterruptedException;

    public abstract IMessage poll();

    public abstract void offer(IMessage paramIMessage);

    public abstract int size();
}