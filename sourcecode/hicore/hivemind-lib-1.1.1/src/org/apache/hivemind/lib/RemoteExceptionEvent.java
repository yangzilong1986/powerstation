 package org.apache.hivemind.lib;
 
 import java.util.EventObject;
 
 public class RemoteExceptionEvent extends EventObject
 {
   private static final long serialVersionUID = 1L;
   private Throwable _exception;
 
   public RemoteExceptionEvent(Object source, Throwable exception)
   {
     super(source);
 
     this._exception = exception;
   }
 
   public Throwable getException()
   {
     return this._exception;
   }
 }