 package org.apache.hivemind.impl;
 
 import java.text.Format;
 import java.text.MessageFormat;
 import java.util.Locale;
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.Messages;
 import org.apache.hivemind.util.Defense;
 
 public abstract class AbstractMessages
   implements Messages
 {
   public String format(String key, Object[] args)
   {
     String pattern = getMessage(key);
 
     for (int i = 0; i < args.length; ++i)
     {
       Object arg = args[i];
 
       if ((arg != null) && (arg instanceof Throwable)) {
         args[i] = extractMessage((Throwable)arg);
       }
 
     }
 
     MessageFormat messageFormat = new MessageFormat("");
     messageFormat.setLocale(getLocale());
     messageFormat.applyPattern(pattern);
 
     return messageFormat.format(args);
   }
 
   private String extractMessage(Throwable t)
   {
     String message = t.getMessage();
 
     return ((HiveMind.isNonBlank(message)) ? message : t.getClass().getName());
   }
 
   public String format(String key, Object arg0)
   {
     return format(key, new Object[] { arg0 });
   }
 
   public String format(String key, Object arg0, Object arg1)
   {
     return format(key, new Object[] { arg0, arg1 });
   }
 
   public String format(String key, Object arg0, Object arg1, Object arg2)
   {
     return format(key, new Object[] { arg0, arg1, arg2 });
   }
 
   public String getMessage(String key)
   {
     Defense.notNull(key, "key");
 
     String result = findMessage(key);
 
     if (result == null) {
       result = "[" + key.toUpperCase() + "]";
     }
     return result;
   }
 
   protected abstract Locale getLocale();
 
   protected abstract String findMessage(String paramString);
 }