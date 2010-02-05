package org.apache.hivemind.schema;

import org.apache.hivemind.Element;
import org.apache.hivemind.Locatable;

public abstract interface Rule extends Locatable
{
  public abstract void begin(SchemaProcessor paramSchemaProcessor, Element paramElement);

  public abstract void end(SchemaProcessor paramSchemaProcessor, Element paramElement);
}