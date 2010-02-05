package org.apache.hivemind.service;

import org.apache.hivemind.Location;
import org.apache.hivemind.internal.Module;

public abstract interface ObjectProvider
{
  public abstract Object provideObject(Module paramModule, Class paramClass, String paramString, Location paramLocation);
}