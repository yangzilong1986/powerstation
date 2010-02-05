 package org.apache.hivemind.methodmatch;
 
 import org.apache.hivemind.Location;
 import org.apache.hivemind.impl.AbstractMessages;
 import org.apache.hivemind.impl.MessageFormatter;
 
 class MethodMatchMessages
 {
   protected static MessageFormatter _formatter = new MessageFormatter(MethodMatchMessages.class);
 
   public static String missingNamePattern(String methodPattern)
   {
     return _formatter.format("missing-name-pattern", methodPattern);
   }
 
   public static String invalidNamePattern(String methodPattern)
   {
     return _formatter.format("invalid-name-pattern", methodPattern);
   }
 
   public static String invalidParametersPattern(String methodPattern)
   {
     return _formatter.format("invalid-parameters-pattern", methodPattern);
   }
 
   public static String exceptionAtLocation(Location location, Throwable cause)
   {
     return _formatter.format("exception-at-location", location, cause);
   }
 }