package org.apache.hivemind;

import java.net.URL;
import java.util.Locale;

public abstract interface Resource
{
  public abstract URL getResourceURL();

  public abstract String getName();

  public abstract Resource getLocalization(Locale paramLocale);

  public abstract Resource getRelativeResource(String paramString);

  public abstract String getPath();

  public abstract Locale getLocale();
}