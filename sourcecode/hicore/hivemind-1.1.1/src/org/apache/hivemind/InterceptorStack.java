package org.apache.hivemind;

import org.apache.commons.logging.Log;
import org.apache.hivemind.internal.Module;

public abstract interface InterceptorStack
{
  public abstract String getServiceExtensionPointId();

  public abstract Module getServiceModule();

  public abstract Class getServiceInterface();

  public abstract Object peek();

  public abstract void push(Object paramObject);

  public abstract Log getServiceLog();
}