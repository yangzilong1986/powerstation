package com.hzjbbis.fk.message;

public abstract interface MessageLoader {
    public abstract IMessage loadMessage(String paramString);

    public abstract String serializeMessage(IMessage paramIMessage);
}