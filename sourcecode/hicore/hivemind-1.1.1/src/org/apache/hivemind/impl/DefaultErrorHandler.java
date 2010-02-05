 package org.apache.hivemind.impl;
 
 import org.apache.commons.logging.Log;
 import org.apache.hivemind.ErrorHandler;
 import org.apache.hivemind.Location;
 
 public class DefaultErrorHandler
   implements ErrorHandler
 {
   public void error(Log log, String message, Location location, Throwable cause)
   {
     String output = (location == null) ? ImplMessages.unlocatedError(message) : ImplMessages.locatedError(location, message);
 
     log.error(output, cause);
   }
 }