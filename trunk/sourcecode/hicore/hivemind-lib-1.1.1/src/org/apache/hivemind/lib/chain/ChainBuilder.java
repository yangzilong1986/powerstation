package org.apache.hivemind.lib.chain;

import java.util.List;

public abstract interface ChainBuilder
{
  public abstract Object buildImplementation(Class paramClass, List paramList, String paramString);
}