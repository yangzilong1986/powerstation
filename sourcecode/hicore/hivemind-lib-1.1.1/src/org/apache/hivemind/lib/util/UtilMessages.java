 package org.apache.hivemind.lib.util;
 
 import org.apache.hivemind.impl.AbstractMessages;
 import org.apache.hivemind.impl.MessageFormatter;
 import org.apache.hivemind.service.ClassFabUtils;
 
 class UtilMessages
 {
   protected static MessageFormatter _formatter = new MessageFormatter(UtilMessages.class);
 
   static String duplicateRegistration(Class subjectClass)
   {
     return _formatter.format("duplicate-registration", ClassFabUtils.getJavaClassName(subjectClass));
   }
 
   static String strategyNotFound(Class subjectClass)
   {
     return _formatter.format("strategy-not-found", ClassFabUtils.getJavaClassName(subjectClass));
   }
 }