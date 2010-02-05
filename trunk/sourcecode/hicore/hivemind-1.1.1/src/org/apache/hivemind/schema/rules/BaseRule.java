package org.apache.hivemind.schema.rules;

import org.apache.hivemind.Element;
import org.apache.hivemind.impl.BaseLocatable;
import org.apache.hivemind.schema.Rule;
import org.apache.hivemind.schema.SchemaProcessor;

public abstract class BaseRule extends BaseLocatable
  implements Rule
{
  public void begin(SchemaProcessor processor, Element element)
  {
  }

  public void end(SchemaProcessor processor, Element element)
  {
  }
}