 package org.apache.hivemind.impl;
 
 import java.util.AbstractList;
 import java.util.List;
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.events.RegistryShutdownListener;
 
 public final class ElementsProxyList extends AbstractList
   implements RegistryShutdownListener
 {
   private List _inner;
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
 
   public Object get(int index)
   {
     checkShutdown();
 
     return this._inner.get(index);
   }
 
   public int size()
   {
     checkShutdown();
 
     return this._inner.size();
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
 
   public void setInner(List list)
   {
     this._inner = list;
   }
 }