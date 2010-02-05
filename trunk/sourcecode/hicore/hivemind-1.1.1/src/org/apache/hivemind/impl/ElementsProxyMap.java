 package org.apache.hivemind.impl;
 
 import java.util.AbstractMap;
 import java.util.Map;
 import java.util.Set;
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.events.RegistryShutdownListener;
 
 public final class ElementsProxyMap extends AbstractMap
   implements RegistryShutdownListener
 {
   private Map _inner;
   private boolean _shutdown;
 
   public void registryDidShutdown()
   {
     this._shutdown = true;
     this._inner = null;
   }
 
   private void checkShutdown()
   {
     if (this._shutdown)
       throw HiveMind.createRegistryShutdownException();
   }
 
   public Set entrySet()
   {
     checkShutdown();
 
     return this._inner.entrySet();
   }
 
   public String toString()
   {
     return this._inner.toString();
   }
 
   public boolean equals(Object o)
   {
     return this._inner.equals(o);
   }
 
   public int hashCode()
   {
     return this._inner.hashCode();
   }
 
   public void setInner(Map map)
   {
     this._inner = map;
   }
 }