 package org.apache.hivemind.impl;
 
 import java.util.AbstractList;
 import java.util.List;
 
 public final class ElementsInnerProxyList extends AbstractList
 {
   private List _inner;
   private ConfigurationPointImpl _point;
   private ElementsProxyList _outer;
 
   ElementsInnerProxyList(ConfigurationPointImpl point, ElementsProxyList outer)
   {
     this._point = point;
     this._outer = outer;
 
     this._outer.setInner(this);
   }
 
   private synchronized List inner()
   {
     if (this._inner == null)
     {
       this._inner = this._point.constructElements();
 
       this._outer.setInner(this._inner);
     }
 
     return this._inner;
   }
 
   public Object get(int index)
   {
     return inner().get(index);
   }
 
   public int size()
   {
     return inner().size();
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
     return "<Element List Proxy for " + this._point.getExtensionPointId() + ">";
   }
 }