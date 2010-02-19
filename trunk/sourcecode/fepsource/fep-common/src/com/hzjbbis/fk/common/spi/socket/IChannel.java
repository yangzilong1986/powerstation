package com.hzjbbis.fk.common.spi.socket;

import com.hzjbbis.fk.message.IMessage;

public abstract interface IChannel {
    public abstract void close();

    public abstract boolean send(IMessage paramIMessage);

    public abstract int sendQueueSize();

    public abstract void setMaxSendQueueSize(int paramInt);

    public abstract ISocketServer getServer();

    public abstract void setIoThread(Object paramObject);

    public abstract String getPeerIp();

    public abstract int getPeerPort();

    public abstract String getPeerAddr();

    public abstract long getLastIoTime();

    public abstract void setLastIoTime();

    public abstract long getLastReadTime();

    public abstract void setLastReadTime();

    public abstract void setRequestNum(int paramInt);

    public abstract int getRequestNum();
}