package org.apache.hivemind.schema;

import org.apache.hivemind.Location;
import org.apache.hivemind.internal.Module;

public abstract interface Translator
{
  public abstract Object translate(Module paramModule, Class paramClass, String paramString, Location paramLocation);
}