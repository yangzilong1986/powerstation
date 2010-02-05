package org.apache.hivemind.internal;

import org.apache.hivemind.Locatable;

public abstract interface ServiceImplementationConstructor extends Locatable
{
  public abstract Module getContributingModule();

  public abstract Object constructCoreServiceImplementation();
}