package com.hzjbbis.fk.common.spi;

public abstract interface IModStatistics {
    public abstract long getLastReceiveTime();

    public abstract long getLastSendTime();

    public abstract long getTotalRecvMessages();

    public abstract long getTotalSendMessages();

    public abstract int getMsgRecvPerMinute();

    public abstract int getMsgSendPerMinute();
}