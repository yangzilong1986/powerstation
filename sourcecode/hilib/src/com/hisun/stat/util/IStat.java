package com.hisun.stat.util;

public abstract interface IStat
{
  public abstract void reset();

  public abstract void once(long paramLong);

  public abstract void once(long paramLong1, long paramLong2);

  public abstract void multi(int paramInt, long paramLong);

  public abstract void dump(StringBuffer paramStringBuffer);
}