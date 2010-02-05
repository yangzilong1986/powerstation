package org.apache.hivemind.service;

public abstract interface ThreadLocalStorage
{
  public abstract Object get(String paramString);

  public abstract void put(String paramString, Object paramObject);

  public abstract void clear();
}