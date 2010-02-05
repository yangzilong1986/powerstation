package org.apache.hivemind.service;

public abstract interface ClassFactory
{
  public abstract ClassFab newClass(String paramString, Class paramClass);

  public abstract InterfaceFab newInterface(String paramString);
}