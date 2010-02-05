package com.hisun.framework.event;

import com.hisun.exception.HiException;

public abstract interface IServerStopListener {
    public abstract void serverStop(ServerEvent paramServerEvent) throws HiException;
}