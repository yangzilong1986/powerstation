package com.hisun.pubinterface;

import com.hisun.exception.HiException;

public abstract interface HiCloseable
{
  public abstract void close()
    throws HiException;
}