package org.apache.hivemind.service;

import java.util.Locale;

public abstract interface ThreadLocale
{
  public abstract void setLocale(Locale paramLocale);

  public abstract Locale getLocale();
}