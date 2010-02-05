 package org.apache.hivemind.servlet;
 
 import org.apache.hivemind.Registry;
 import org.apache.hivemind.impl.AbstractMessages;
 import org.apache.hivemind.impl.MessageFormatter;
 
 class ServletMessages
 {
   protected static MessageFormatter _formatter = new MessageFormatter(ServletMessages.class);
 
   static String filterCleanupError(Throwable cause)
   {
     return _formatter.format("filter-cleanup-error", cause);
   }
 
   static String filterInit()
   {
     return _formatter.getMessage("filter-init");
   }
 
   static String constructedRegistry(Registry registry, long millis)
   {
     return _formatter.format("constructed-registry", registry, new Long(millis));
   }
 }