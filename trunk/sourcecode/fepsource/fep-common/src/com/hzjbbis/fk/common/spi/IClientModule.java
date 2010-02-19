package com.hzjbbis.fk.common.spi;

import com.hzjbbis.fk.message.IMessage;

public abstract interface IClientModule extends IModule {
    public abstract boolean sendMessage(IMessage paramIMessage);
}