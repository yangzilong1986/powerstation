package org.apache.hivemind.test;

public abstract interface ArgumentMatcher
{
  public abstract boolean compareArguments(Object paramObject1, Object paramObject2);
}