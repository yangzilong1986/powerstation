 package org.apache.hivemind.impl;
 
 import java.util.AbstractMap;
 import java.util.Map;
 import java.util.Set;
 
 public final class ElementsInnerProxyMap extends AbstractMap
 {
   private Map _inner;
   private ConfigurationPointImpl _point;
   private ElementsProxyMap _outer;
 
   ElementsInnerProxyMap(ConfigurationPointImpl point, ElementsProxyMap outer)
   {
     this._point = point;
     this._outer = outer;
 
     this._outer.setInner(this);
   }
 
   public Set entrySet()
   {
     return inner().entrySet();
   }
 
   private synchronized Map inner()
   {
     if (this._inner == null)
     {
       this._inner = this._point.constructMapElements();
 
       this._outer.setInner(this._inner);
     }
 
     return this._inner;
   }
 
   public boolean equals(Object o)
   {
     if (this == o) {
       return true;
     }
     if (o == null) {
       return false;
     }
     return inner().equals(o);
   }
 
   public int hashCode()
   {
     return inner().hashCode();
   }
 
   public synchronized String toString()
   {
     if (this._inner != null) {
       return this._inner.toString();
     }
     return "<Element Map Proxy for " + this._point.getExtensionPointId() + ">";
   }
 }