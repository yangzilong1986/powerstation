package org.apache.hivemind;

public abstract interface Orderable
{
  public abstract String getName();

  public abstract String getFollowingNames();

  public abstract String getPrecedingNames();
}