package org.apache.hivemind.lib;

public abstract interface NameLookup
{
  public abstract Object lookup(String paramString, Class paramClass);
}