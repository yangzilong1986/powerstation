 package org.apache.hivemind.service.impl;
 
 import org.apache.commons.logging.Log;
 import org.apache.hivemind.ServiceImplementationFactoryParameters;
 
 public class BuilderLogFacet extends BuilderFacet
 {
   public Object getFacetValue(ServiceImplementationFactoryParameters factoryParameters, Class targetType)
   {
     return factoryParameters.getLog();
   }
 
   public boolean isAssignableToType(ServiceImplementationFactoryParameters factoryParameters, Class targetType)
   {
     return (targetType == Log.class);
   }
 
   protected String getDefaultPropertyName()
   {
     return "log";
   }
 
   public boolean canAutowireConstructorParameter()
   {
     return true;
   }
 }