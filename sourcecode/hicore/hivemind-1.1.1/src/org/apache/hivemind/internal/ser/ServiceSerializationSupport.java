package org.apache.hivemind.internal.ser;

public abstract interface ServiceSerializationSupport
{
  public abstract ServiceToken getServiceTokenForService(String paramString);

  public abstract Object getServiceFromToken(ServiceToken paramServiceToken);
}