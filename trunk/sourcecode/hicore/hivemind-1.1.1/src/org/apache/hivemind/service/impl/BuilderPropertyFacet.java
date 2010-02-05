 package org.apache.hivemind.service.impl;
 
 import java.util.HashMap;
 import java.util.Map;
 import org.apache.hivemind.ServiceImplementationFactoryParameters;
 import org.apache.hivemind.impl.BaseLocatable;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.schema.Translator;
 import org.apache.hivemind.util.ConstructorUtils;
 
 public class BuilderPropertyFacet extends BuilderFacet
 {
   private String _translatorName;
   private String _literalValue;
   private Map _valuesCache;
 
   public BuilderPropertyFacet()
   {
     this._valuesCache = new HashMap();
   }
 
   public Object getFacetValue(ServiceImplementationFactoryParameters factoryParameters, Class targetType)
   {
     Object result = this._valuesCache.get(targetType);
 
     if (result == null)
     {
       Translator translator = factoryParameters.getInvokingModule().getTranslator(this._translatorName);
 
       result = translator.translate(factoryParameters.getInvokingModule(), targetType, this._literalValue, super.getLocation());
 
       this._valuesCache.put(targetType, result);
     }
 
     return result;
   }
 
   public boolean isAssignableToType(ServiceImplementationFactoryParameters factoryParameters, Class targetType)
   {
     Object facetValue = getFacetValue(factoryParameters, targetType);
 
     if (facetValue == null) {
       return (!(targetType.isPrimitive()));
     }
     return ConstructorUtils.isCompatible(targetType, facetValue.getClass());
   }
 
   public void setTranslator(String translatorName)
   {
     this._translatorName = translatorName;
   }
 
   public void setValue(String value)
   {
     this._literalValue = value;
   }
 }