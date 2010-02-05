package org.apache.hivemind.internal;

import org.apache.hivemind.Occurances;
import org.apache.hivemind.schema.Schema;

public abstract interface ServicePoint extends ExtensionPoint
{
  public abstract Class getServiceInterface();

  public abstract Class getDeclaredInterface();

  public abstract String getServiceInterfaceClassName();

  public abstract Object getService(Class paramClass);

  public abstract Schema getParametersSchema();

  public abstract Occurances getParametersCount();

  public abstract void forceServiceInstantiation();
}