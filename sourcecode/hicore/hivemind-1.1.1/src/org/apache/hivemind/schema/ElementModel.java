package org.apache.hivemind.schema;

import java.util.List;
import org.apache.hivemind.Locatable;
import org.apache.hivemind.parse.AnnotationHolder;

public abstract interface ElementModel extends AnnotationHolder, Locatable
{
  public abstract String getElementName();

  public abstract List getElementModel();

  public abstract List getAttributeModels();

  public abstract AttributeModel getAttributeModel(String paramString);

  public abstract String getKeyAttribute();

  public abstract List getRules();

  public abstract String getContentTranslator();
}