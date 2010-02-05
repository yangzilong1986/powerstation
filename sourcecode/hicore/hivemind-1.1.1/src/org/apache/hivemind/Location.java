package org.apache.hivemind;

public abstract interface Location
{
  public abstract Resource getResource();

  public abstract int getLineNumber();

  public abstract int getColumnNumber();
}