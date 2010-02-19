package com.hzjbbis.fk.common.spi.socket;

import com.hzjbbis.fk.common.spi.IModStatistics;
import com.hzjbbis.fk.common.spi.IModule;
import com.hzjbbis.fk.message.IMessage;

import java.nio.ByteBuffer;

public abstract interface ISocketServer extends IModStatistics, IModule {
    public abstract int getPort();

    public abstract String getServerAddress();

    public abstract IClientIO getIoHandler();

    public abstract int getIoThreadSize();

    public abstract void removeClient(IServerSideChannel paramIServerSideChannel);

    public abstract int getClientSize();

    public abstract IServerSideChannel[] getClients();

    public abstract IMessage createMessage(ByteBuffer paramByteBuffer);

    public abstract int getBufLength();

    public abstract int getMaxContinueRead();

    public abstract int getWriteFirstCount();

    public abstract void setLastReceiveTime(long paramLong);

    public abstract void setLastSendTime(long paramLong);

    public abstract void incRecvMessage();

    public abstract void incSendMessage();
}