package com.hisun.client;

public abstract interface WebServiceContext
{
  public abstract RequestParam getRequestParam(int paramInt);

  public abstract int requestParamSize();

  public abstract String getProcessor();

  public abstract String getNamespace();

  public abstract String getEndpoint();

  public abstract String getActionURL();

  public abstract String getOperationName();
}