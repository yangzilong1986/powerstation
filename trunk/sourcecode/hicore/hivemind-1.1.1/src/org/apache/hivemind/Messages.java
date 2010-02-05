package org.apache.hivemind;

public abstract interface Messages
{
  public abstract String getMessage(String paramString);

  public abstract String format(String paramString, Object[] paramArrayOfObject);

  public abstract String format(String paramString, Object paramObject);

  public abstract String format(String paramString, Object paramObject1, Object paramObject2);

  public abstract String format(String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
}