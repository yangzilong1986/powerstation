package org.apache.hivemind;

import java.util.List;

public abstract interface Element extends Locatable
{
  public abstract String getElementName();

  public abstract List getAttributes();

  public abstract String getAttributeValue(String paramString);

  public abstract boolean isEmpty();

  public abstract List getElements();

  public abstract String getContent();
}