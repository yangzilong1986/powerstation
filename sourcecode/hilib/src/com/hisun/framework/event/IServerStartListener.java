package com.hisun.framework.event;

import com.hisun.exception.HiException;

public abstract interface IServerStartListener
{
  public abstract void serverStart(ServerEvent paramServerEvent)
    throws HiException;
}