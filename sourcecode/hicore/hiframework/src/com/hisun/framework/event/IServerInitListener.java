package com.hisun.framework.event;

import com.hisun.exception.HiException;

public abstract interface IServerInitListener {
    public abstract void serverInit(ServerEvent paramServerEvent) throws HiException;
}