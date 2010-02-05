package org.apache.hivemind.schema;

import org.apache.hivemind.internal.Module;

public abstract interface SchemaProcessor
{
  public abstract void addElement(Object paramObject);

  public abstract void push(Object paramObject);

  public abstract Object pop();

  public abstract Object peek();

  public abstract Object peek(int paramInt);

  public abstract Module getContributingModule();

  public abstract Module getDefiningModule();

  public abstract String getElementPath();

  public abstract Translator getContentTranslator();

  public abstract Translator getAttributeTranslator(String paramString);

  public abstract Translator getTranslator(String paramString);
}