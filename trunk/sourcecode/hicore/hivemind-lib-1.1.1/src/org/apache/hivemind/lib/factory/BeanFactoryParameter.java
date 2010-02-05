 package org.apache.hivemind.lib.factory;
 
 import java.util.List;
 
 public class BeanFactoryParameter
 {
   private Class _vendClass;
   private boolean _defaultCacheable;
   private List _contributions;
 
   public BeanFactoryParameter()
   {
     this._vendClass = Object.class;
     this._defaultCacheable = true;
   }
 
   public List getContributions()
   {
     return this._contributions;
   }
 
   public boolean getDefaultCacheable()
   {
     return this._defaultCacheable;
   }
 
   public Class getVendClass()
   {
     return this._vendClass;
   }
 
   public void setContributions(List list)
   {
     this._contributions = list;
   }
 
   public void setDefaultCacheable(boolean b)
   {
     this._defaultCacheable = b;
   }
 
   public void setVendClass(Class class1)
   {
     this._vendClass = class1;
   }
 }