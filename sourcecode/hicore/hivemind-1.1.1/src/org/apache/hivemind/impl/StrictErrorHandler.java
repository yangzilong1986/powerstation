 package org.apache.hivemind.impl;
 
 import org.apache.commons.logging.Log;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.ErrorHandler;
 import org.apache.hivemind.Location;
 
 public class StrictErrorHandler
   implements ErrorHandler
 {
   public void error(Log log, String message, Location location, Throwable cause)
   {
     String exceptionMessage = (location == null) ? ImplMessages.unlocatedError(message) : ImplMessages.locatedError(location, message);
 
     throw new ApplicationRuntimeException(exceptionMessage, location, cause);
   }
 }