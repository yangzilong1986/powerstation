package org.apache.hivemind;

import java.net.URL;

public abstract interface ClassResolver
{
  public abstract URL getResource(String paramString);

  public abstract Class findClass(String paramString);

  public abstract Class checkForClass(String paramString);

  public abstract ClassLoader getClassLoader();
}