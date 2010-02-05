 package org.apache.hivemind.service.impl;
 
 import org.apache.hivemind.impl.BaseLocatable;
 
 public class EventRegistration extends BaseLocatable
 {
   private Object _producer;
   private String _eventSetName;
 
   public String getEventSetName()
   {
     return this._eventSetName;
   }
 
   public Object getProducer()
   {
     return this._producer;
   }
 
   public void setEventSetName(String string)
   {
     this._eventSetName = string;
   }
 
   public void setProducer(Object object)
   {
     this._producer = object;
   }
 }