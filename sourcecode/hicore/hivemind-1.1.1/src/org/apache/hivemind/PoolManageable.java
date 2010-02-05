package org.apache.hivemind;

public abstract interface PoolManageable
{
  public abstract void activateService();

  public abstract void passivateService();
}