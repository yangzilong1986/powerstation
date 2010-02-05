 package org.apache.hivemind.lib.factory;
 
 import org.apache.hivemind.impl.BaseLocatable;
 
 public class BeanFactoryContribution extends BaseLocatable
 {
   private String _name;
   private Class _beanClass;
   private Boolean _cacheable;
 
   public Boolean getCacheable()
   {
     return this._cacheable;
   }
 
   public String getName()
   {
     return this._name;
   }
 
   public Class getBeanClass()
   {
     return this._beanClass;
   }
 
   public void setCacheable(Boolean cacheable)
   {
     this._cacheable = cacheable;
   }
 
   public void setName(String string)
   {
     this._name = string;
   }
 
   public void setBeanClass(Class objectClass)
   {
     this._beanClass = objectClass;
   }
 
   public boolean getStoreResultInCache(boolean defaultCacheable)
   {
     return ((this._cacheable == null) ? defaultCacheable : this._cacheable.booleanValue());
   }
 }