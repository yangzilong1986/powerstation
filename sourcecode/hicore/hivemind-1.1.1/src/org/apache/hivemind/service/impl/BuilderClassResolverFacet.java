 package org.apache.hivemind.service.impl;
 
 import org.apache.hivemind.ClassResolver;
 import org.apache.hivemind.ServiceImplementationFactoryParameters;
 import org.apache.hivemind.internal.Module;
 
 public class BuilderClassResolverFacet extends BuilderFacet
 {
   public Object getFacetValue(ServiceImplementationFactoryParameters factoryParameters, Class targetType)
   {
     return factoryParameters.getInvokingModule().getClassResolver();
   }
 
   public boolean isAssignableToType(ServiceImplementationFactoryParameters factoryParameters, Class targetType)
   {
     return (targetType == ClassResolver.class);
   }
 
   protected String getDefaultPropertyName()
   {
     return "classResolver";
   }
 
   public boolean canAutowireConstructorParameter()
   {
     return true;
   }
 }