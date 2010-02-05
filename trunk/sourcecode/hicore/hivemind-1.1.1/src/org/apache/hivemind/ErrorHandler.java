package org.apache.hivemind;

import org.apache.commons.logging.Log;

public abstract interface ErrorHandler
{
  public abstract void error(Log paramLog, String paramString, Location paramLocation, Throwable paramThrowable);
}