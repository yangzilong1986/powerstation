package com.hisun.engine.invoke;

import com.hisun.exception.HiException;
import java.util.List;

public abstract interface HiIEngineModel
{
  public abstract void addChilds(HiIEngineModel paramHiIEngineModel)
    throws HiException;

  public abstract void setParent(HiIEngineModel paramHiIEngineModel)
    throws HiException;

  public abstract HiIEngineModel getParent()
    throws HiException;

  public abstract List getChilds()
    throws HiException;

  public abstract void loadAfter()
    throws HiException;
}