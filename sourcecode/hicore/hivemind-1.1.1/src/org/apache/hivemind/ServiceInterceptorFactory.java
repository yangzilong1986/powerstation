package org.apache.hivemind;

import java.util.List;
import org.apache.hivemind.internal.Module;

public abstract interface ServiceInterceptorFactory
{
  public abstract void createInterceptor(InterceptorStack paramInterceptorStack, Module paramModule, List paramList);
}