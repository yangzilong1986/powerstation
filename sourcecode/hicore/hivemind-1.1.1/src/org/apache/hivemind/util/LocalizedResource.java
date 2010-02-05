 package org.apache.hivemind.util;
 
 import java.util.Locale;
 
 public class LocalizedResource
 {
   private String _resourcePath;
   private Locale _resourceLocale;
 
   public LocalizedResource(String resourcePath, Locale resourceLocale)
   {
     this._resourcePath = resourcePath;
     this._resourceLocale = resourceLocale;
   }
 
   public Locale getResourceLocale()
   {
     return this._resourceLocale;
   }
 
   public String getResourcePath()
   {
     return this._resourcePath;
   }
 }