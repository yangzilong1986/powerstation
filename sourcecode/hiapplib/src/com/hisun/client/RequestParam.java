package com.hisun.client;

public abstract interface RequestParam
{
  public abstract String getName();

  public abstract String getType();

  public abstract int getIndex();

  public abstract Object getValue();
}