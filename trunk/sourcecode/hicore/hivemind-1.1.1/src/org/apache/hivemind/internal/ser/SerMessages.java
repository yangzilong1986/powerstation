 package org.apache.hivemind.internal.ser;
 
 import org.apache.hivemind.impl.AbstractMessages;
 import org.apache.hivemind.impl.MessageFormatter;
 
 class SerMessages
 {
   protected static MessageFormatter _formatter = new MessageFormatter(SerMessages.class);
 
   static String noSupportSet()
   {
     return _formatter.getMessage("no-support-set");
   }
 }