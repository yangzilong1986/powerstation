package com.hisun.hilib.component;

import com.hisun.exception.HiException;
import com.hisun.hilib.HiATLParam;
import com.hisun.message.HiMessageContext;

public abstract interface HiIComponent
{
  public abstract int execute(HiATLParam paramHiATLParam, HiMessageContext paramHiMessageContext)
    throws HiException;
}