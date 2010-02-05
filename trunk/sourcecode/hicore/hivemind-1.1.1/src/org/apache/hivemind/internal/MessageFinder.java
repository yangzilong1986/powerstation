package org.apache.hivemind.internal;

import java.util.Locale;

public abstract interface MessageFinder
{
  public abstract String getMessage(String paramString, Locale paramLocale);
}