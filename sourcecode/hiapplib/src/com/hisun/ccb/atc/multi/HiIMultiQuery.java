package com.hisun.ccb.atc.multi;

import com.hisun.exception.HiException;
import java.util.Collection;

public abstract interface HiIMultiQuery
{
  public abstract Collection<String> process()
    throws HiException;

  public abstract int getTotalCounts()
    throws HiException;

  public abstract int getTotalPage()
    throws HiException;
}