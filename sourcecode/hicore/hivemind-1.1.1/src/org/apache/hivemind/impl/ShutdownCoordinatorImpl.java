 package org.apache.hivemind.impl;
 
 import java.util.HashSet;
 import java.util.Iterator;
 import java.util.Set;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.apache.hivemind.ShutdownCoordinator;
 import org.apache.hivemind.events.RegistryShutdownListener;
 import org.apache.hivemind.util.EventListenerList;
 
 public final class ShutdownCoordinatorImpl
   implements ShutdownCoordinator
 {
   private final Log _log;
   private Set alreadyShutdown;
   private EventListenerList _listenerList;
 
   public ShutdownCoordinatorImpl()
   {
     this(LogFactory.getLog(ShutdownCoordinatorImpl.class));
   }
 
   public ShutdownCoordinatorImpl(Log log)
   {
     this._log = log;
   }
 
   public synchronized void addRegistryShutdownListener(RegistryShutdownListener s)
   {
     if (this._listenerList == null) {
       this._listenerList = new EventListenerList();
     }
     this._listenerList.addListener(s);
   }
 
   public synchronized void removeRegistryShutdownListener(RegistryShutdownListener s)
   {
     if (this._listenerList != null)
       this._listenerList.removeListener(s);
   }
 
   public void shutdown()
   {
     if (this._listenerList == null) {
       return;
     }
     Iterator i = this._listenerList.getListeners();
 
     this._listenerList = null;
 
     while (i.hasNext())
     {
       RegistryShutdownListener s = (RegistryShutdownListener)i.next();
 
       shutdown(s);
     }
 
     this._listenerList = null;
   }
 
   private void shutdown(RegistryShutdownListener s)
   {
     if (this.alreadyShutdown == null)
     {
       this.alreadyShutdown = new HashSet();
     }
     Long id = new Long(System.identityHashCode(s));
     if (this.alreadyShutdown.contains(id))
       return;
     try
     {
       s.registryDidShutdown();
     }
     catch (RuntimeException ex)
     {
       this._log.error(ImplMessages.shutdownCoordinatorFailure(s, ex), ex);
     }
     finally
     {
       this.alreadyShutdown.add(id);
     }
   }
 }