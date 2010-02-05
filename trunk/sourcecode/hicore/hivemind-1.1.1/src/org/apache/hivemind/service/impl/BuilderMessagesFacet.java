 package org.apache.hivemind.service.impl;
 
 import org.apache.hivemind.Messages;
 import org.apache.hivemind.ServiceImplementationFactoryParameters;
 import org.apache.hivemind.internal.Module;
 
 public class BuilderMessagesFacet extends BuilderFacet
 {
   public Object getFacetValue(ServiceImplementationFactoryParameters factoryParameters, Class targetType)
   {
     return factoryParameters.getInvokingModule().getMessages();
   }
 
   public boolean isAssignableToType(ServiceImplementationFactoryParameters factoryParameters, Class targetType)
   {
     return (targetType == Messages.class);
   }
 
   protected String getDefaultPropertyName()
   {
     return "messages";
   }
 
   public boolean canAutowireConstructorParameter()
   {
     return true;
   }
 }