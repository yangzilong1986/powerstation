package org.apache.hivemind.impl;

import java.util.List;
import org.apache.hivemind.events.RegistryShutdownListener;
import org.apache.hivemind.internal.ServiceImplementationConstructor;
import org.apache.hivemind.internal.ServicePoint;

public abstract interface ConstructableServicePoint extends ServicePoint
{
  public abstract ServiceImplementationConstructor getServiceConstructor();

  public abstract List getOrderedInterceptorContributions();

  public abstract void clearConstructorInformation();

  public abstract void addRegistryShutdownListener(RegistryShutdownListener paramRegistryShutdownListener);
}