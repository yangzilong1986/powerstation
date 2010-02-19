package com.hzjbbis.fk.common.spi.socket;

import com.hzjbbis.fk.exception.SocketClientCloseException;

public abstract interface IClientIO {
    public abstract boolean onReceive(IServerSideChannel paramIServerSideChannel) throws SocketClientCloseException;

    public abstract boolean onSend(IServerSideChannel paramIServerSideChannel) throws SocketClientCloseException;
}