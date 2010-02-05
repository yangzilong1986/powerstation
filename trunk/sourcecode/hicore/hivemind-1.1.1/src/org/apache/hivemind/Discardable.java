package org.apache.hivemind;

public abstract interface Discardable
{
  public abstract void threadDidDiscardService();
}