package com.hzjbbis.fk.common.spi.abstra;

import com.hzjbbis.fk.common.spi.IModule;

public abstract class BaseModule implements IModule {
    public String getName() {
        return "undefine";
    }

    public String getTxfs() {
        return "??";
    }

    public boolean isActive() {
        return true;
    }

    public abstract boolean start();

    public abstract void stop();

    public long getLastReceiveTime() {
        return 0L;
    }

    public long getLastSendTime() {
        return 0L;
    }

    public int getMsgRecvPerMinute() {
        return 0;
    }

    public int getMsgSendPerMinute() {
        return 0;
    }

    public long getTotalRecvMessages() {
        return 0L;
    }

    public long getTotalSendMessages() {
        return 0L;
    }

    public String profile() {
        return "<profile>empty</profile>";
    }
}