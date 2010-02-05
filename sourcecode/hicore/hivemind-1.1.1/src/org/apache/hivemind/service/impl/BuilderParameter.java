 package org.apache.hivemind.service.impl;
 
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import org.apache.hivemind.ServiceImplementationFactoryParameters;
 import org.apache.hivemind.impl.BaseLocatable;
 
 public class BuilderParameter extends BaseLocatable
 {
   private String _className;
   private List _properties;
   private List _parameters;
   private Map _typeFacetMap;
   private List _events;
   private String _initializeMethod;
   private boolean _autowireServices;
 
   public BuilderParameter()
   {
     this._properties = new ArrayList();
 
     this._parameters = new ArrayList();
 
     this._typeFacetMap = new HashMap();
 
     this._events = new ArrayList();
   }
 
   public String getClassName()
   {
     return this._className;
   }
 
   public void addParameter(BuilderFacet facet)
   {
     this._parameters.add(facet);
   }
 
   public List getParameters()
   {
     return this._parameters;
   }
 
   public void addProperty(BuilderFacet facet)
   {
     this._properties.add(facet);
   }
 
   public List getProperties()
   {
     return this._properties;
   }
 
   public BuilderFacet getFacetForType(ServiceImplementationFactoryParameters factoryParameters, Class targetType)
   {
     BuilderFacet result = (BuilderFacet)this._typeFacetMap.get(targetType);
 
     if (result == null)
     {
       for (Iterator i = this._properties.iterator(); i.hasNext(); )
       {
         BuilderFacet facet = (BuilderFacet)i.next();
 
         if ((facet.canAutowireConstructorParameter()) && (facet.isAssignableToType(factoryParameters, targetType)))
         {
           result = facet;
           break;
         }
       }
 
       this._typeFacetMap.put(targetType, result);
     }
 
     return result;
   }
 
   public void setClassName(String string)
   {
     this._className = string;
   }
 
   public void addEventRegistration(EventRegistration registration)
   {
     this._events.add(registration);
   }
 
   public List getEventRegistrations()
   {
     return this._events;
   }
 
   public String getInitializeMethod()
   {
     return this._initializeMethod;
   }
 
   public void setInitializeMethod(String string)
   {
     this._initializeMethod = string;
   }
 
   public boolean getAutowireServices()
   {
     return this._autowireServices;
   }
 
   public void setAutowireServices(boolean autowireServices)
   {
     this._autowireServices = autowireServices;
   }
 }