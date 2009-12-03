package com.hisun.engine.invoke;

import com.hisun.exception.HiException;
import com.hisun.message.HiMessageContext;

public abstract interface HiIStrategy
{
  public abstract void beforeConverMess(HiMessageContext paramHiMessageContext)
    throws HiException;

  public abstract void afterConverMess(HiMessageContext paramHiMessageContext)
    throws HiException;
}