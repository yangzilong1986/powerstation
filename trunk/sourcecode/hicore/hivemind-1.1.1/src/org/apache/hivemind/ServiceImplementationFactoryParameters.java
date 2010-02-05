package org.apache.hivemind;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.hivemind.internal.Module;

public abstract interface ServiceImplementationFactoryParameters
{
  public abstract String getServiceId();

  public abstract Class getServiceInterface();

  public abstract Log getLog();

  public abstract ErrorLog getErrorLog();

  public abstract Module getInvokingModule();

  public abstract List getParameters();

  public abstract Object getFirstParameter();
}