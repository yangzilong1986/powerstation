 package org.apache.hivemind.service.impl;
 
 import org.apache.commons.logging.Log;
 import org.apache.hivemind.ServiceImplementationFactoryParameters;
 import org.apache.hivemind.impl.BaseLocatable;
 import org.apache.hivemind.util.PropertyUtils;
 
 public abstract class BuilderFacet extends BaseLocatable
 {
   private String _propertyName;
 
   public abstract Object getFacetValue(ServiceImplementationFactoryParameters paramServiceImplementationFactoryParameters, Class paramClass);
 
   public abstract boolean isAssignableToType(ServiceImplementationFactoryParameters paramServiceImplementationFactoryParameters, Class paramClass);
 
   public String getPropertyName()
   {
     return this._propertyName;
   }
 
   public void setPropertyName(String string)
   {
     this._propertyName = string;
   }
 
   public String autowire(Object target, ServiceImplementationFactoryParameters factoryParameters)
   {
     if (this._propertyName != null) {
       return null;
     }
     String defaultPropertyName = getDefaultPropertyName();
 
     if (defaultPropertyName == null) {
       return null;
     }
     if (!(PropertyUtils.isWritable(target, defaultPropertyName))) {
       return null;
     }
     Class propertyType = PropertyUtils.getPropertyType(target, defaultPropertyName);
 
     if (isAssignableToType(factoryParameters, propertyType))
     {
       Object facetValue = getFacetValue(factoryParameters, propertyType);
 
       PropertyUtils.write(target, defaultPropertyName, facetValue);
 
       Log log = factoryParameters.getLog();
 
       if (log.isDebugEnabled()) {
         log.debug("Autowired property " + defaultPropertyName + " to " + facetValue);
       }
       return defaultPropertyName;
     }
 
     return null;
   }
 
   protected String getDefaultPropertyName()
   {
     return null;
   }
 
   public boolean canAutowireConstructorParameter()
   {
     return false;
   }
 }