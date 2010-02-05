 package org.apache.hivemind.impl;
 
 import java.util.Locale;
 import java.util.MissingResourceException;
 import java.util.ResourceBundle;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 
 public class MessageFormatter extends AbstractMessages
 {
   private Log _log;
   private ResourceBundle _bundle;
 
   public MessageFormatter(Log log, ResourceBundle bundle)
   {
     this._log = log;
     this._bundle = bundle;
   }
 
   public MessageFormatter(Class referenceClass)
   {
     this(referenceClass, getStringsName(referenceClass));
   }
 
   public MessageFormatter(Class referenceClass, String name)
   {
     this(LogFactory.getLog(referenceClass), referenceClass, name);
   }
 
   public MessageFormatter(Log log, Class referenceClass, String name)
   {
     this(log, getResourceBundleName(referenceClass, name));
   }
 
   public MessageFormatter(Log log, String bundleName)
   {
     this(log, ResourceBundle.getBundle(bundleName));
   }
 
   protected String findMessage(String key)
   {
     try
     {
       return this._bundle.getString(key);
     }
     catch (MissingResourceException ex)
     {
       this._log.error("Missing resource key: " + key + "."); }
     return null;
   }
 
   protected Locale getLocale()
   {
     return Locale.getDefault();
   }
 
   private static String getStringsName(Class referenceClass)
   {
     String className = referenceClass.getName();
 
     int lastDotIndex = className.lastIndexOf(46);
 
     String justClass = className.substring(lastDotIndex + 1);
 
     int mpos = justClass.indexOf("Messages");
 
     return justClass.substring(0, mpos) + "Strings";
   }
 
   private static String getResourceBundleName(Class referenceClass, String name)
   {
     String packageName = null;
     if (referenceClass.getPackage() != null)
     {
       packageName = referenceClass.getPackage().getName();
     }
     else
     {
       int lastDotIndex = referenceClass.getName().lastIndexOf(46);
       packageName = (lastDotIndex == -1) ? "" : referenceClass.getName().substring(0, lastDotIndex);
     }
 
     return packageName + "." + name;
   }
 }