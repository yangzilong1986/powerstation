package com.hisun.engine.invoke;

import com.hisun.exception.HiException;
import com.hisun.message.HiMessageContext;

public abstract interface HiIAction
{
  public abstract void afterProcess(HiMessageContext paramHiMessageContext)
    throws HiException;

  public abstract void beforeProcess(HiMessageContext paramHiMessageContext)
    throws HiException;

  public abstract void process(HiMessageContext paramHiMessageContext)
    throws HiException;

  public abstract String getNodeName();
}