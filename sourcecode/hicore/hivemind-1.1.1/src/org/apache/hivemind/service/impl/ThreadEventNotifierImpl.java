 package org.apache.hivemind.service.impl;
 
 import java.util.Iterator;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.apache.hivemind.service.ThreadCleanupListener;
 import org.apache.hivemind.service.ThreadEventNotifier;
 import org.apache.hivemind.util.Defense;
 import org.apache.hivemind.util.EventListenerList;
 
 public class ThreadEventNotifierImpl
   implements ThreadEventNotifier
 {
   private static final Log DEFAULT_LOG = LogFactory.getLog(ThreadEventNotifier.class);
   private final Log _log;
   private final ThreadLocal _storage;
 
   public ThreadEventNotifierImpl()
   {
     this(DEFAULT_LOG);
   }
 
   public ThreadEventNotifierImpl(Log log)
   {
     this._storage = new ThreadLocal();
 
     Defense.notNull(log, "log");
 
     this._log = log;
   }
 
   public void addThreadCleanupListener(ThreadCleanupListener listener)
   {
     EventListenerList list = (EventListenerList)this._storage.get();
 
     if (list == null)
     {
       list = new EventListenerList();
       this._storage.set(list);
     }
 
     list.addListener(listener);
   }
 
   public void removeThreadCleanupListener(ThreadCleanupListener listener)
   {
     EventListenerList list = (EventListenerList)this._storage.get();
 
     if (list != null)
       list.removeListener(listener);
   }
 
   public void fireThreadCleanup()
   {
     EventListenerList list = (EventListenerList)this._storage.get();
 
     if (list == null) {
       return;
     }
     Iterator i = list.getListeners();
 
     this._storage.set(null);
 
     while (i.hasNext())
     {
       ThreadCleanupListener listener = (ThreadCleanupListener)i.next();
       try
       {
         listener.threadDidCleanup();
       }
       catch (RuntimeException ex)
       {
         this._log.warn(ServiceMessages.threadCleanupException(ex), ex);
       }
     }
   }
 }