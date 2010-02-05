package org.apache.hivemind.internal;

import org.apache.hivemind.InterceptorStack;
import org.apache.hivemind.Locatable;

public abstract interface ServiceInterceptorContribution extends Locatable
{
  public abstract String getName();

  public abstract String getFactoryServiceId();

  public abstract void createInterceptor(InterceptorStack paramInterceptorStack);

  public abstract String getPrecedingInterceptorIds();

  public abstract String getFollowingInterceptorIds();
}