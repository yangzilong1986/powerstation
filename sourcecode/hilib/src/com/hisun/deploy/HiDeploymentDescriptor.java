package com.hisun.deploy;

import com.hisun.exception.HiException;
import com.hisun.hilog4j.Logger;

public abstract interface HiDeploymentDescriptor
{
  public abstract void createDescriptor(String paramString1, String paramString2)
    throws HiException;

  public abstract void setLog(Logger paramLogger);
}