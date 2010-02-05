package org.apache.hivemind;

import java.util.List;

public abstract interface ModuleDescriptorProvider
{
  public abstract List getModuleDescriptors(ErrorHandler paramErrorHandler);
}