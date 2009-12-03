package com.hisun.pubinterface;

import com.hisun.exception.HiException;
import com.hisun.message.HiMessageContext;

public abstract interface IHandlerFilter
{
  public abstract void process(HiMessageContext paramHiMessageContext, IHandler paramIHandler)
    throws HiException;
}