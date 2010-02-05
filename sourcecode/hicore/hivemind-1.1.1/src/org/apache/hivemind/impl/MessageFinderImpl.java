 package org.apache.hivemind.impl;
 
 import java.io.BufferedInputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.net.URL;
 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Locale;
 import java.util.Map;
 import java.util.Properties;
 import org.apache.hivemind.Resource;
 import org.apache.hivemind.internal.MessageFinder;
 import org.apache.hivemind.util.Defense;
 import org.apache.hivemind.util.IOUtils;
 import org.apache.hivemind.util.LocalizedNameGenerator;
 
 public class MessageFinderImpl
   implements MessageFinder
 {
   private static final String EXTENSION = ".properties";
   private Resource _baseResource;
   private String _baseName;
   private Map _propertiesMap = new HashMap();
 
   private Properties _emptyProperties = new Properties();
 
   public MessageFinderImpl(Resource baseResource)
   {
     Defense.notNull(baseResource, "baseResource");
 
     this._baseResource = baseResource;
 
     String name = this._baseResource.getName();
     int dotx = name.lastIndexOf(46);
 
     this._baseName = name.substring(0, dotx);
   }
 
   public String getMessage(String key, Locale locale)
   {
     return findProperties(locale).getProperty(key);
   }
 
   private synchronized Properties findProperties(Locale locale)
   {
     Properties result = (Properties)this._propertiesMap.get(locale);
 
     if (result == null) {
       result = buildProperties(locale);
     }
     return result;
   }
 
   private Properties buildProperties(Locale locale)
   {
     Properties result = this._emptyProperties;
 
     List localizations = findLocalizations(locale);
 
     Iterator i = localizations.iterator();
     while (i.hasNext())
     {
       Localization l = (Localization)i.next();
 
       result = readProperties(l.getLocale(), l.getResource(), result);
     }
 
     return result;
   }
 
   private Properties readProperties(Locale locale, Resource propertiesResource, Properties parent)
   {
     Properties result = (Properties)this._propertiesMap.get(locale);
 
     if (result != null) {
       return result;
     }
     URL url = propertiesResource.getResourceURL();
 
     if (url == null)
       result = parent;
     else {
       result = readPropertiesFile(url, parent);
     }
     this._propertiesMap.put(locale, result);
 
     return result;
   }
 
   private Properties readPropertiesFile(URL url, Properties parent)
   {
     InputStream stream = null;
 
     Properties result = new Properties(parent);
     try
     {
       stream = new BufferedInputStream(url.openStream());
 
       result.load(stream);
 
       stream.close();
 
       stream = null;
     }
     catch (IOException ex)
     {
     }
     finally
     {
       IOUtils.close(stream);
     }
 
     return result;
   }
 
   private List findLocalizations(Locale locale)
   {
     List result = new ArrayList();
 
     LocalizedNameGenerator g = new LocalizedNameGenerator(this._baseName, locale, ".properties");
 
     while (g.more())
     {
       String name = g.next();
 
       Localization l = new Localization(g.getCurrentLocale(), this._baseResource.getRelativeResource(name));
 
       result.add(l);
     }
 
     Collections.reverse(result);
 
     return result;
   }
 
   private static class Localization
   {
     private Locale _locale;
     private Resource _resource;
 
     Localization(Locale locale, Resource resource)
     {
       this._locale = locale;
       this._resource = resource;
     }
 
     public Locale getLocale()
     {
       return this._locale;
     }
 
     public Resource getResource()
     {
       return this._resource;
     }
   }
 }