 package org.apache.hivemind.service.impl;
 
 import org.apache.hivemind.ErrorLog;
 import org.apache.hivemind.ServiceImplementationFactoryParameters;
 
 public class BuilderErrorLogFacet extends BuilderFacet
 {
   public Object getFacetValue(ServiceImplementationFactoryParameters factoryParameters, Class targetType)
   {
     return factoryParameters.getErrorLog();
   }
 
   public boolean isAssignableToType(ServiceImplementationFactoryParameters factoryParameters, Class targetType)
   {
     return (targetType == ErrorLog.class);
   }
 
   protected String getDefaultPropertyName()
   {
     return "errorLog";
   }
 
   public boolean canAutowireConstructorParameter()
   {
     return true;
   }
 }