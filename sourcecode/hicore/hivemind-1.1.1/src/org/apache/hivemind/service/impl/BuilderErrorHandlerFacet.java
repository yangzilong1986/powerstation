 package org.apache.hivemind.service.impl;
 
 import org.apache.hivemind.ErrorHandler;
 import org.apache.hivemind.ServiceImplementationFactoryParameters;
 import org.apache.hivemind.internal.Module;
 
 public class BuilderErrorHandlerFacet extends BuilderFacet
 {
   public Object getFacetValue(ServiceImplementationFactoryParameters factoryParameters, Class targetType)
   {
     return factoryParameters.getInvokingModule().getErrorHandler();
   }
 
   public boolean isAssignableToType(ServiceImplementationFactoryParameters factoryParameters, Class targetType)
   {
     return (targetType == ErrorHandler.class);
   }
 
   protected String getDefaultPropertyName()
   {
     return "errorHandler";
   }
 
   public boolean canAutowireConstructorParameter()
   {
     return true;
   }
 }