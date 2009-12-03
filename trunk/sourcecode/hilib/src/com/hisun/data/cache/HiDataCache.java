package com.hisun.data.cache;

import com.hisun.exception.HiException;
import java.util.ArrayList;

public abstract interface HiDataCache
{
  public abstract void validate()
    throws HiException;

  public abstract void load()
    throws HiException;

  public abstract ArrayList getDataList();

  public abstract String getValue(String paramString);
}