package org.apache.hivemind;

public abstract interface ErrorLog
{
  public abstract void error(String paramString, Location paramLocation, Throwable paramThrowable);
}