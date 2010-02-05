package org.apache.hivemind.internal;

import java.util.List;
import java.util.Map;
import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.hivemind.schema.Schema;

public abstract interface ConfigurationPoint extends ExtensionPoint
{
  public abstract List getElements();

  public abstract boolean areElementsMappable();

  public abstract Map getElementsAsMap()
    throws ApplicationRuntimeException;

  public abstract Schema getContributionsSchema();
}