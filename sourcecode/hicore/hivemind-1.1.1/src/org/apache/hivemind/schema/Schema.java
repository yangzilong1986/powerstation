package org.apache.hivemind.schema;

import java.util.List;
import org.apache.hivemind.Locatable;
import org.apache.hivemind.internal.Module;
import org.apache.hivemind.parse.AnnotationHolder;

public abstract interface Schema extends AnnotationHolder, Locatable
{
  public abstract String getId();

  public abstract List getElementModel();

  public abstract boolean canInstancesBeKeyed();

  public abstract boolean visibleToModule(String paramString);

  public abstract Module getDefiningModule();
}