package org.apache.hivemind.service;

public abstract interface MethodFab
{
  public abstract void addCatch(Class paramClass, String paramString);

  public abstract void extend(String paramString, boolean paramBoolean);
}