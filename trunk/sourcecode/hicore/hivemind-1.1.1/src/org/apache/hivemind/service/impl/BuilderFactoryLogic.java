 package org.apache.hivemind.service.impl;
 
 import java.lang.reflect.Constructor;
 import java.lang.reflect.InvocationTargetException;
 import java.lang.reflect.Method;
 import java.lang.reflect.Modifier;
 import java.util.ArrayList;
 import java.util.Collection;
 import java.util.Collections;
 import java.util.Comparator;
 import java.util.HashSet;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Set;
 import org.apache.commons.logging.Log;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.ErrorHandler;
 import org.apache.hivemind.ErrorLog;
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.ServiceImplementationFactoryParameters;
 import org.apache.hivemind.impl.BaseLocatable;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.service.EventLinker;
 import org.apache.hivemind.util.ConstructorUtils;
 import org.apache.hivemind.util.PropertyUtils;
 
 public class BuilderFactoryLogic
 {
   private ServiceImplementationFactoryParameters _factoryParameters;
   private String _serviceId;
   private BuilderParameter _parameter;
   private Log _log;
   private Module _contributingModule;
 
   public BuilderFactoryLogic(ServiceImplementationFactoryParameters factoryParameters, BuilderParameter parameter)
   {
     this._factoryParameters = factoryParameters;
     this._parameter = parameter;
 
     this._log = this._factoryParameters.getLog();
     this._serviceId = factoryParameters.getServiceId();
     this._contributingModule = factoryParameters.getInvokingModule();
   }
 
   public Object createService()
   {
     try
     {
       Object result = instantiateCoreServiceInstance();
 
       setProperties(result);
 
       registerForEvents(result);
 
       invokeInitializer(result);
 
       return result;
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(ServiceMessages.failureBuildingService(this._serviceId, ex), this._parameter.getLocation(), ex);
     }
   }
 
   private void error(String message, Location location, Throwable cause)
   {
     this._factoryParameters.getErrorLog().error(message, location, cause);
   }
 
   private Object instantiateCoreServiceInstance()
   {
     Class serviceClass = this._contributingModule.resolveType(this._parameter.getClassName());
 
     List parameters = this._parameter.getParameters();
 
     if ((this._parameter.getAutowireServices()) && (parameters.isEmpty()))
     {
       return instantiateConstructorAutowiredInstance(serviceClass);
     }
 
     return instantiateExplicitConstructorInstance(serviceClass, parameters);
   }
 
   private Object instantiateExplicitConstructorInstance(Class serviceClass, List builderParameters)
   {
     int numberOfParams = builderParameters.size();
     List constructorCandidates = getServiceConstructorsOfLength(serviceClass, numberOfParams);
 
     Iterator candidates = constructorCandidates.iterator(); if (candidates.hasNext())
     {
       Constructor candidate = (Constructor)candidates.next();
 
       Class[] parameterTypes = candidate.getParameterTypes();
 
       Object[] parameters = new Object[parameterTypes.length];
 
       for (int i = 0; i < numberOfParams; ++i) {
         BuilderFacet facet;
         do facet = (BuilderFacet)builderParameters.get(i);
 
         while (!(facet.isAssignableToType(this._factoryParameters, parameterTypes[i])));
 
         parameters[i] = facet.getFacetValue(this._factoryParameters, parameterTypes[i]);
       }
 
       return ConstructorUtils.invoke(candidate, parameters);
     }
 
     throw new ApplicationRuntimeException(ServiceMessages.unableToFindAutowireConstructor(), this._parameter.getLocation(), null);
   }
 
   private List getServiceConstructorsOfLength(Class serviceClass, int length)
   {
     List fixedLengthConstructors = new ArrayList();
 
     Constructor[] constructors = serviceClass.getDeclaredConstructors();
 
     for (int i = 0; i < constructors.length; ++i)
     {
       if (!(Modifier.isPublic(constructors[i].getModifiers()))) {
         continue;
       }
       Class[] parameterTypes = constructors[i].getParameterTypes();
 
       if (parameterTypes.length != length) {
         continue;
       }
       fixedLengthConstructors.add(constructors[i]);
     }
 
     return fixedLengthConstructors;
   }
 
   private Object instantiateConstructorAutowiredInstance(Class serviceClass)
   {
     List serviceConstructorCandidates = getOrderedServiceConstructors(serviceClass);
 
     Iterator candidates = serviceConstructorCandidates.iterator();
     if (candidates.hasNext())
     {
       Constructor candidate = (Constructor)candidates.next();
 
       Class[] parameterTypes = candidate.getParameterTypes();
 
       Object[] parameters = new Object[parameterTypes.length];
 
       for (int i = 0; i < parameters.length; ++i) {
         do {
           BuilderFacet facet = this._parameter.getFacetForType(this._factoryParameters, parameterTypes[i]);
 
           if ((facet != null) && (facet.canAutowireConstructorParameter())) {
             parameters[i] = facet.getFacetValue(this._factoryParameters, parameterTypes[i]); break label148: } }
         while (!(this._contributingModule.containsService(parameterTypes[i])));
         label148: parameters[i] = this._contributingModule.getService(parameterTypes[i]);
       }
 
       return ConstructorUtils.invoke(candidate, parameters);
     }
 
     throw new ApplicationRuntimeException(ServiceMessages.unableToFindAutowireConstructor(), this._parameter.getLocation(), null);
   }
 
   private List getOrderedServiceConstructors(Class serviceClass)
   {
     List orderedInterfaceConstructors = new ArrayList();
 
     Constructor[] constructors = serviceClass.getDeclaredConstructors();
 
     for (int i = 0; i < constructors.length; ++i)
     {
       if (!(Modifier.isPublic(constructors[i].getModifiers()))) {
         continue;
       }
       Class[] parameterTypes = constructors[i].getParameterTypes();
 
       if (parameterTypes.length > 0)
       {
         Set seenTypes = new HashSet();
 
         for (int j = 0; j < parameterTypes.length; ++j)
         {
           if (!(parameterTypes[j].isInterface())) break label133; if (seenTypes.contains(parameterTypes[j])) {
             break label133;
           }
           seenTypes.add(parameterTypes[j]);
         }
       }
 
       label133: orderedInterfaceConstructors.add(constructors[i]);
     }
 
     Collections.sort(orderedInterfaceConstructors, new Comparator()
     {
       public int compare(Object o1, Object o2)
       {
         return (((Constructor)o2).getParameterTypes().length - ((Constructor)o1).getParameterTypes().length);
       }
     });
     return orderedInterfaceConstructors;
   }
 
   private void invokeInitializer(Object service)
   {
     String methodName = this._parameter.getInitializeMethod();
 
     boolean allowMissing = HiveMind.isBlank(methodName);
 
     String searchMethodName = (allowMissing) ? "initializeService" : methodName;
     try
     {
       findAndInvokeInitializerMethod(service, searchMethodName, allowMissing);
     }
     catch (InvocationTargetException ex)
     {
       Throwable cause = ex.getTargetException();
 
       error(ServiceMessages.unableToInitializeService(this._serviceId, searchMethodName, service.getClass(), cause), this._parameter.getLocation(), cause);
     }
     catch (Exception ex)
     {
       error(ServiceMessages.unableToInitializeService(this._serviceId, searchMethodName, service.getClass(), ex), this._parameter.getLocation(), ex);
     }
   }
 
   private void findAndInvokeInitializerMethod(Object service, String methodName, boolean allowMissing)
     throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
   {
     Class serviceClass = service.getClass();
     try
     {
       Method m = serviceClass.getMethod(methodName, null);
 
       m.invoke(service, null);
     }
     catch (NoSuchMethodException ex)
     {
       if (allowMissing) {
         return;
       }
       throw ex;
     }
   }
 
   private void registerForEvents(Object result)
   {
     List eventRegistrations = this._parameter.getEventRegistrations();
 
     if (eventRegistrations.isEmpty()) {
       return;
     }
     EventLinker linker = new EventLinkerImpl(this._factoryParameters.getErrorLog());
 
     Iterator i = eventRegistrations.iterator();
     while (i.hasNext())
     {
       EventRegistration er = (EventRegistration)i.next();
 
       linker.addEventListener(er.getProducer(), er.getEventSetName(), result, er.getLocation());
     }
   }
 
   private void setProperties(Object service)
   {
     List properties = this._parameter.getProperties();
     int count = properties.size();
 
     Set writeableProperties = new HashSet(PropertyUtils.getWriteableProperties(service));
 
     for (int i = 0; i < count; ++i)
     {
       BuilderFacet facet = (BuilderFacet)properties.get(i);
 
       String propertyName = wireProperty(service, facet);
 
       if (propertyName != null) {
         writeableProperties.remove(propertyName);
       }
     }
     if (this._parameter.getAutowireServices())
       autowireServices(service, writeableProperties);
   }
 
   private String wireProperty(Object service, BuilderFacet facet)
   {
     String propertyName = facet.getPropertyName();
     try
     {
       String autowirePropertyName = facet.autowire(service, this._factoryParameters);
 
       if (autowirePropertyName != null) {
         return autowirePropertyName;
       }
 
       if (propertyName == null) {
         return null;
       }
       Class targetType = PropertyUtils.getPropertyType(service, propertyName);
 
       Object value = facet.getFacetValue(this._factoryParameters, targetType);
 
       PropertyUtils.write(service, propertyName, value);
 
       if (this._log.isDebugEnabled()) {
         this._log.debug("Set property " + propertyName + " to " + value);
       }
       return propertyName;
     }
     catch (Exception ex)
     {
       error(ex.getMessage(), facet.getLocation(), ex);
     }
     return null;
   }
 
   private void autowireServices(Object service, Collection propertyNames)
   {
     Iterator i = propertyNames.iterator();
     while (i.hasNext())
     {
       String propertyName = (String)i.next();
 
       autowireProperty(service, propertyName);
     }
   }
 
   private void autowireProperty(Object service, String propertyName)
   {
     Class propertyType = PropertyUtils.getPropertyType(service, propertyName);
     try
     {
       if (this._contributingModule.containsService(propertyType))
       {
         Object collaboratingService = this._contributingModule.getService(propertyType);
         PropertyUtils.write(service, propertyName, collaboratingService);
 
         if (this._log.isDebugEnabled())
         {
           this._log.debug("Autowired service property " + propertyName + " to " + collaboratingService);
         }
       }
 
     }
     catch (Exception ex)
     {
       getErrorHandler().error(this._log, ServiceMessages.autowirePropertyFailure(propertyName, this._serviceId, ex), this._parameter.getLocation(), ex);
     }
   }
 
   private ErrorHandler getErrorHandler()
   {
     return this._contributingModule.getErrorHandler();
   }
 }