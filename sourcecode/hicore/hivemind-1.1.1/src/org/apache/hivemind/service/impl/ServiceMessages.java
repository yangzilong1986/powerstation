 package org.apache.hivemind.service.impl;
 
 import java.beans.EventSetDescriptor;
 import java.beans.FeatureDescriptor;
 import javassist.CtClass;
 import javassist.CtMember;
 import javassist.CtMethod;
 import org.apache.hivemind.InterceptorStack;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.impl.AbstractMessages;
 import org.apache.hivemind.impl.MessageFormatter;
 import org.apache.hivemind.service.MethodSignature;
 
 class ServiceMessages
 {
   protected static MessageFormatter _formatter = new MessageFormatter(ServiceMessages.class);
 
   static String unableToInitializeService(String serviceId, String methodName, Class serviceClass, Throwable ex)
   {
     return _formatter.format("unable-to-initialize-service", new Object[] { serviceId, methodName, serviceClass.getName(), ex });
   }
 
   static String errorInstantiatingInterceptor(String serviceId, InterceptorStack stack, Class interceptorClass, Throwable cause)
   {
     return _formatter.format("error-instantiating-interceptor", new Object[] { serviceId, stack.getServiceInterface().getName(), stack.getServiceExtensionPointId(), interceptorClass.getName(), cause });
   }
 
   static String unableToAddField(String fieldName, CtClass ctClass, Throwable cause)
   {
     return _formatter.format("unable-to-add-field", fieldName, ctClass.getName(), cause);
   }
 
   static String unableToAddMethod(MethodSignature methodSignature, CtClass ctClass, Throwable cause)
   {
     return _formatter.format("unable-to-add-method", methodSignature, ctClass.getName(), cause);
   }
 
   static String unableToAddConstructor(CtClass ctClass, Throwable cause)
   {
     return _formatter.format("unable-to-add-constructor", ctClass.getName(), cause);
   }
 
   static String unableToWriteClass(CtClass ctClass, Throwable cause)
   {
     return _formatter.format("unable-to-write-class", ctClass.getName(), cause);
   }
 
   static String unableToCreateClass(String name, Class superClass, Throwable cause)
   {
     return _formatter.format("unable-to-create-class", name, superClass.getName(), cause);
   }
 
   static String unableToLookupClass(String name, Throwable cause)
   {
     return _formatter.format("unable-to-lookup", name, cause);
   }
 
   static String notCompatibleWithEvent(Object consumer, EventSetDescriptor set, Object producer)
   {
     return _formatter.format("not-compatible-with-event", new Object[] { consumer, set.getListenerType().getName(), set.getName(), producer });
   }
 
   static String noSuchEventSet(Object producer, String name)
   {
     return _formatter.format("no-such-event-set", producer, name);
   }
 
   static String noEventMatches(Object consumer, Object producer)
   {
     return _formatter.format("no-event-matches", consumer, producer);
   }
 
   static String unableToAddListener(Object producer, EventSetDescriptor set, Object consumer, Location location, Throwable cause)
   {
     return _formatter.format("unable-to-add-listener", new Object[] { consumer, producer, set.getName(), location, cause });
   }
 
   static String unableToIntrospectClass(Class targetClass, Throwable cause)
   {
     return _formatter.format("unable-to-introspect-class", targetClass.getName(), cause);
   }
 
   static String unableToAddCatch(Class exceptionClass, CtMethod method, Throwable cause)
   {
     return _formatter.format("unable-to-add-catch", exceptionClass.getName(), method.getDeclaringClass().getName(), cause);
   }
 
   static String duplicateMethodInClass(MethodSignature ms, ClassFabImpl cf)
   {
     return _formatter.format("duplicate-method-in-class", ms, cf.getName());
   }
 
   static String unableToExtendMethod(MethodSignature ms, String className, Throwable cause)
   {
     return _formatter.format("unable-to-extend-method", ms, className, cause);
   }
 
   static String invalidProviderSelector(String selector)
   {
     return _formatter.format("invalid-provider-selector", selector);
   }
 
   static String unknownProviderPrefix(String prefix)
   {
     return _formatter.format("unknown-provider-prefix", prefix);
   }
 
   static String duplicateProviderPrefix(String prefix, Location priorLocation)
   {
     return _formatter.format("duplicate-provider-prefix", prefix, priorLocation);
   }
 
   static String invalidServicePropertyLocator(String locator)
   {
     return _formatter.format("invalid-service-property-locator", locator);
   }
 
   static String failureBuildingService(String serviceId, Throwable cause)
   {
     return _formatter.format("failure-building-service", serviceId, cause);
   }
 
   static String autowirePropertyFailure(String propertyName, String serviceId, Throwable cause)
   {
     return _formatter.format("autowire-property-failure", propertyName, serviceId, cause);
   }
 
   static String unableToFindAutowireConstructor()
   {
     return _formatter.getMessage("unable-to-find-autowire-constructor");
   }
 
   static String unableToCreateInterface(String name, Exception cause)
   {
     return _formatter.format("unable-to-create-interface", name, cause);
   }
 
   static String threadCleanupException(Throwable cause)
   {
     return _formatter.format("thread-cleanup-exception", cause);
   }
 }