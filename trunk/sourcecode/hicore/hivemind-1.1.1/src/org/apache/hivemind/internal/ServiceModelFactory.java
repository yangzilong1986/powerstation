package org.apache.hivemind.internal;

import org.apache.hivemind.impl.ConstructableServicePoint;

public abstract interface ServiceModelFactory
{
  public abstract ServiceModel createServiceModelForService(ConstructableServicePoint paramConstructableServicePoint);
}