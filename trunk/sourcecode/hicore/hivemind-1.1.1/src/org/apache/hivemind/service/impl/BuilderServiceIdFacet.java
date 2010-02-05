 package org.apache.hivemind.service.impl;
 
 import org.apache.hivemind.ServiceImplementationFactoryParameters;
 
 public class BuilderServiceIdFacet extends BuilderFacet
 {
   public Object getFacetValue(ServiceImplementationFactoryParameters parameters, Class targetType)
   {
     return parameters.getServiceId();
   }
 
   public boolean isAssignableToType(ServiceImplementationFactoryParameters factoryParameters, Class targetType)
   {
     return (targetType == String.class);
   }
 
   protected String getDefaultPropertyName()
   {
     return "serviceId";
   }
 }