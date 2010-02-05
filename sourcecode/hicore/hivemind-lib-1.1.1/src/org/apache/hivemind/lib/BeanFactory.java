package org.apache.hivemind.lib;

public abstract interface BeanFactory
{
  public abstract boolean contains(String paramString);

  public abstract Object get(String paramString);
}