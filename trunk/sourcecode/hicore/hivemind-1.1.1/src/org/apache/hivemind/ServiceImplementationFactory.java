package org.apache.hivemind;

public abstract interface ServiceImplementationFactory
{
  public abstract Object createCoreServiceImplementation(ServiceImplementationFactoryParameters paramServiceImplementationFactoryParameters);
}