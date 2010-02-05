 package org.apache.hivemind.lib.impl;
 
 import org.apache.hivemind.impl.BaseLocatable;
 
 public class ServicePropertyFactoryParameter extends BaseLocatable
 {
   private Object _service;
   private String _propertyName;
 
   public String getPropertyName()
   {
     return this._propertyName;
   }
 
   public Object getService()
   {
     return this._service;
   }
 
   public void setPropertyName(String string)
   {
     this._propertyName = string;
   }
 
   public void setService(Object object)
   {
     this._service = object;
   }
 }