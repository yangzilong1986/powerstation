package org.apache.hivemind.service;

import org.apache.hivemind.Location;

public abstract interface EventLinker
{
  public abstract void addEventListener(Object paramObject1, String paramString, Object paramObject2, Location paramLocation);
}