package org.apache.hivemind.internal;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.hivemind.ErrorHandler;
import org.apache.hivemind.Location;
import org.apache.hivemind.SymbolSource;
import org.apache.hivemind.schema.Translator;

public abstract interface RegistryInfrastructure extends SymbolSource
{
  public abstract Object getService(String paramString, Class paramClass, Module paramModule);

  public abstract Object getService(Class paramClass, Module paramModule);

  public abstract List getConfiguration(String paramString, Module paramModule);

  public abstract boolean isConfigurationMappable(String paramString, Module paramModule);

  public abstract Map getConfigurationAsMap(String paramString, Module paramModule);

  public abstract ConfigurationPoint getConfigurationPoint(String paramString, Module paramModule);

  public abstract ServicePoint getServicePoint(String paramString, Module paramModule);

  public abstract String expandSymbols(String paramString, Location paramLocation);

  public abstract ServiceModelFactory getServiceModelFactory(String paramString);

  public abstract Translator getTranslator(String paramString);

  public abstract Locale getLocale();

  public abstract ErrorHandler getErrorHander();

  public abstract boolean containsConfiguration(String paramString, Module paramModule);

  public abstract boolean containsService(Class paramClass, Module paramModule);

  public abstract boolean containsService(String paramString, Class paramClass, Module paramModule);

  public abstract void startup();

  public abstract void shutdown();

  public abstract void setupThread();

  public abstract void cleanupThread();

  public abstract List getServiceIds(Class paramClass);

  public abstract Module getModule(String paramString);
}