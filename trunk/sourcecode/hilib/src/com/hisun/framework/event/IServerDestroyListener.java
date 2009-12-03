package com.hisun.framework.event;

import com.hisun.exception.HiException;

public abstract interface IServerDestroyListener
{
  public abstract void serverDestroy(ServerEvent paramServerEvent)
    throws HiException;
}