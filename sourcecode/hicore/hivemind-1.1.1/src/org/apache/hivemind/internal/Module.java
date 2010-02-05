package org.apache.hivemind.internal;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.hivemind.ClassResolver;
import org.apache.hivemind.ErrorHandler;
import org.apache.hivemind.Locatable;
import org.apache.hivemind.Location;
import org.apache.hivemind.Messages;
import org.apache.hivemind.SymbolSource;
import org.apache.hivemind.schema.Translator;

public abstract interface Module extends Locatable, SymbolSource
{
  public abstract String getModuleId();

  public abstract boolean containsService(Class paramClass);

  public abstract Object getService(String paramString, Class paramClass);

  public abstract Object getService(Class paramClass);

  public abstract ServicePoint getServicePoint(String paramString);

  public abstract List getConfiguration(String paramString);

  public abstract boolean isConfigurationMappable(String paramString);

  public abstract Map getConfigurationAsMap(String paramString);

  public abstract ClassResolver getClassResolver();

  public abstract Class resolveType(String paramString);

  public abstract Messages getMessages();

  public abstract Translator getTranslator(String paramString);

  public abstract ServiceModelFactory getServiceModelFactory(String paramString);

  public abstract Locale getLocale();

  public abstract String expandSymbols(String paramString, Location paramLocation);

  public abstract ErrorHandler getErrorHandler();
}