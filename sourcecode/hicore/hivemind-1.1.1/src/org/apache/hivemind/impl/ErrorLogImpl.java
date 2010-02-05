 package org.apache.hivemind.impl;
 
 import org.apache.commons.logging.Log;
 import org.apache.hivemind.ErrorHandler;
 import org.apache.hivemind.ErrorLog;
 import org.apache.hivemind.Location;
 
 public class ErrorLogImpl
   implements ErrorLog
 {
   private ErrorHandler _errorHandler;
   private Log _log;
 
   public ErrorLogImpl(ErrorHandler errorHandler, Log log)
   {
     this._errorHandler = errorHandler;
     this._log = log;
   }
 
   public void error(String message, Location location, Throwable cause)
   {
     this._errorHandler.error(this._log, message, location, cause);
   }
 }