package org.apache.hivemind.schema;

import org.apache.hivemind.Locatable;
import org.apache.hivemind.parse.AnnotationHolder;

public abstract interface AttributeModel extends Locatable, AnnotationHolder
{
  public abstract String getName();

  public abstract boolean isRequired();

  public abstract String getTranslator();

  public abstract boolean isUnique();
}