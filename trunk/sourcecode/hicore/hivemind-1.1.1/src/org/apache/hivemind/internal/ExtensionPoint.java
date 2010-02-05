package org.apache.hivemind.internal;

import org.apache.commons.logging.Log;
import org.apache.hivemind.ErrorLog;
import org.apache.hivemind.Locatable;

public abstract interface ExtensionPoint extends Locatable
{
  public abstract String getExtensionPointId();

  public abstract Module getModule();

  public abstract boolean visibleToModule(Module paramModule);

  public abstract Log getLog();

  public abstract ErrorLog getErrorLog();
}