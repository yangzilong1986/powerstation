package com.hisun.engine.pojo;

import com.hisun.exception.HiException;
import com.hisun.message.HiMessageContext;

public abstract interface HiITransaction
{
  public abstract void service(HiMessageContext paramHiMessageContext)
    throws HiException;
}