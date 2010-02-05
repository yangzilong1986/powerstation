 package org.apache.hivemind.impl;
 
 import org.apache.hivemind.internal.ServiceModelFactory;
 
 public class ServiceModelContribution
 {
   private String _name;
   private ServiceModelFactory _factory;
 
   public ServiceModelFactory getFactory()
   {
     return this._factory;
   }
 
   public String getName()
   {
     return this._name;
   }
 
   public void setFactory(ServiceModelFactory factory)
   {
     this._factory = factory;
   }
 
   public void setName(String string)
   {
     this._name = string;
   }
 }