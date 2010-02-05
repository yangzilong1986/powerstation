package org.apache.hivemind.service;

public abstract interface ClassFab
{
  public abstract void addInterface(Class paramClass);

  public abstract void addField(String paramString, Class paramClass);

  public abstract boolean containsMethod(MethodSignature paramMethodSignature);

  public abstract MethodFab addMethod(int paramInt, MethodSignature paramMethodSignature, String paramString);

  public abstract MethodFab getMethodFab(MethodSignature paramMethodSignature);

  public abstract void addConstructor(Class[] paramArrayOfClass1, Class[] paramArrayOfClass2, String paramString);

  public abstract Class createClass();
}