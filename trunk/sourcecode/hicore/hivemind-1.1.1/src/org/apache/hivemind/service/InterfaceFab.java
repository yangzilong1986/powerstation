package org.apache.hivemind.service;

public abstract interface InterfaceFab
{
  public abstract void addInterface(Class paramClass);

  public abstract void addMethod(MethodSignature paramMethodSignature);

  public abstract Class createInterface();
}