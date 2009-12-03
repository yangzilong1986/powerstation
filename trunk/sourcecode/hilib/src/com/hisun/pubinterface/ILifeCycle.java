package com.hisun.pubinterface;

public abstract interface ILifeCycle
{
  public abstract String getName();

  public abstract String getInfo();

  public abstract void start();

  public abstract void stop();

  public abstract boolean isRunning();

  public abstract void init();

  public abstract void destroy();

  public abstract void destroy(int paramInt);
}