package org.apache.hivemind.parse;

import org.apache.hivemind.Locatable;
import org.apache.hivemind.internal.Module;
import org.apache.hivemind.internal.ServiceImplementationConstructor;
import org.apache.hivemind.internal.ServicePoint;

public abstract interface InstanceBuilder extends Locatable
{
  public abstract String getServiceModel();

  public abstract ServiceImplementationConstructor createConstructor(ServicePoint paramServicePoint, Module paramModule);
}