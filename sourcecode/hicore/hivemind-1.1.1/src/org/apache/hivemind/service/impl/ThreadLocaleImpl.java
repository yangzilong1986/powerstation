 package org.apache.hivemind.service.impl;
 
 import java.util.Locale;
 import org.apache.hivemind.service.ThreadLocale;
 import org.apache.hivemind.util.Defense;
 
 public class ThreadLocaleImpl
   implements ThreadLocale
 {
   private Locale _locale;
 
   public ThreadLocaleImpl(Locale locale)
   {
     setLocale(locale);
   }
 
   public void setLocale(Locale locale)
   {
     Defense.notNull(locale, "locale");
 
     this._locale = locale;
   }
 
   public Locale getLocale()
   {
     return this._locale;
   }
 }