package com.hzjbbis.fk.common.spi.socket.abstra;

import com.hzjbbis.fk.common.spi.socket.IChannel;
import com.hzjbbis.fk.common.spi.socket.ISocketServer;
import com.hzjbbis.fk.message.IMessage;

public abstract class BaseClientChannel implements IChannel {
    private int requestNum = -1;

    public abstract void close();

    public long getLastIoTime() {
        return 0L;
    }

    public long getLastReadTime() {
        return 0L;
    }

    public int getLastingWrite() {
        return 0;
    }

    public abstract String getPeerAddr();

    public abstract String getPeerIp();

    public abstract int getPeerPort();

    public ISocketServer getServer() {
        return null;
    }

    public void setIoThread(Object threadObj) {
    }

    public void setLastIoTime() {
    }

    public void setLastReadTime() {
    }

    public abstract boolean send(IMessage paramIMessage);

    public abstract boolean sendMessage(IMessage paramIMessage);

    public int sendQueueSize() {
        return 0;
    }

    public void setMaxSendQueueSize(int maxSendQueueSize) {
    }

    public int getRequestNum() {
        return this.requestNum;
    }

    public void setRequestNum(int requestNum) {
        this.requestNum = requestNum;
    }

    public abstract boolean isActive();
}