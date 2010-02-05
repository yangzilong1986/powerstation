package org.apache.hivemind.methodmatch;

import org.apache.hivemind.service.MethodSignature;

public abstract class MethodFilter
{
  public abstract boolean matchMethod(MethodSignature paramMethodSignature);
}