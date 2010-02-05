package org.apache.hivemind.conditional;

public abstract interface EvaluationContext
{
  public abstract boolean isPropertySet(String paramString);

  public abstract boolean doesClassExist(String paramString);
}