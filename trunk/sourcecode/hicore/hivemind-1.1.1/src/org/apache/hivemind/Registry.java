package org.apache.hivemind;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public abstract interface Registry extends SymbolSource
{
  public abstract boolean containsConfiguration(String paramString);

  public abstract boolean containsService(Class paramClass);

  public abstract boolean containsService(String paramString, Class paramClass);

  public abstract List getConfiguration(String paramString);

  public abstract boolean isConfigurationMappable(String paramString);

  public abstract Map getConfigurationAsMap(String paramString);

  public abstract String expandSymbols(String paramString, Location paramLocation);

  public abstract Object getService(String paramString, Class paramClass);

  public abstract Object getService(Class paramClass);

  public abstract Locale getLocale();

  public abstract void shutdown();

  public abstract void setupThread();

  public abstract void cleanupThread();

  public abstract List getServiceIds(Class paramClass);

  public abstract Messages getModuleMessages(String paramString);
}