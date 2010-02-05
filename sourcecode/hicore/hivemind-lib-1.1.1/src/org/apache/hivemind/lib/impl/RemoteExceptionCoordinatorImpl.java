 package org.apache.hivemind.lib.impl;
 
 import java.util.ArrayList;
 import java.util.List;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.lib.RemoteExceptionCoordinator;
 import org.apache.hivemind.lib.RemoteExceptionEvent;
 import org.apache.hivemind.lib.RemoteExceptionListener;
 
 public class RemoteExceptionCoordinatorImpl
   implements RemoteExceptionCoordinator
 {
   private boolean _locked;
   private List _listeners;
 
   private void checkLocked(String methodName)
   {
     if (this._locked)
       throw new ApplicationRuntimeException(ImplMessages.coordinatorLocked(methodName));
   }
 
   public synchronized void addRemoteExceptionListener(RemoteExceptionListener listener)
   {
     checkLocked("addRemoteExceptionListener");
 
     if (this._listeners == null) {
       this._listeners = new ArrayList();
     }
     this._listeners.add(listener);
   }
 
   public synchronized void removeRemoteExceptionListener(RemoteExceptionListener listener)
   {
     checkLocked("removeRemoteExceptionListener");
 
     if (this._listeners == null) {
       return;
     }
     this._listeners.remove(listener);
   }
 
   public synchronized void fireRemoteExceptionDidOccur(Object source, Throwable exception)
   {
     checkLocked("sendNotification");
 
     if ((this._listeners == null) || (this._listeners.size() == 0)) {
       return;
     }
     RemoteExceptionEvent event = new RemoteExceptionEvent(source, exception);
 
     int count = this._listeners.size();
 
     this._locked = true;
     try
     {
       for (int i = 0; i < count; ++i)
       {
         RemoteExceptionListener listener = (RemoteExceptionListener)this._listeners.get(i);
 
         listener.remoteExceptionDidOccur(event);
       }
     }
     finally
     {
       this._locked = false;
     }
   }
 }