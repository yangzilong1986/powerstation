package org.apache.hivemind.lib.util;

public abstract interface StrategyRegistry
{
  public abstract void register(Class paramClass, Object paramObject);

  public abstract Object getStrategy(Class paramClass);
}