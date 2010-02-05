package org.apache.hivemind.internal;

public abstract interface ServiceModel
{
  public abstract Object getService();

  public abstract void instantiateService();
}