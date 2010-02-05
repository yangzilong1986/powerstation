 package org.apache.hivemind.lib.impl;
 
 import java.rmi.RemoteException;
 import org.apache.hivemind.lib.NameLookup;
 import org.apache.hivemind.lib.RemoteExceptionCoordinator;
 
 public abstract class AbstractEJBProxy
 {
   private NameLookup _nameLookup;
   private RemoteExceptionCoordinator _coordinator;
 
   protected AbstractEJBProxy(NameLookup nameLookup, RemoteExceptionCoordinator coordinator)
   {
     this._nameLookup = nameLookup;
     this._coordinator = coordinator;
   }
 
   protected Object _lookup(String name)
   {
     return this._nameLookup.lookup(name, Object.class);
   }
 
   protected abstract void _clearCachedReferences();
 
   protected void _handleRemoteException(RemoteException ex)
   {
     _clearCachedReferences();
     this._coordinator.fireRemoteExceptionDidOccur(this, ex);
   }
 }