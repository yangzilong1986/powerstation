 package org.apache.hivemind.impl;
 
 import java.lang.reflect.Constructor;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.events.RegistryShutdownListener;
 import org.apache.hivemind.internal.ServiceModel;
 import org.apache.hivemind.internal.ServicePoint;
 import org.apache.hivemind.service.BodyBuilder;
 import org.apache.hivemind.service.ClassFab;
 import org.apache.hivemind.service.ClassFabUtils;
 import org.apache.hivemind.service.MethodSignature;
 import org.apache.hivemind.util.ConstructorUtils;
 
 public final class ProxyUtils
 {
   public static final String SERVICE_ACCESSOR_METHOD_NAME = "_service";
   public static final String DELEGATE_ACCESSOR_METHOD_NAME = "_delegate";
 
   public static Object createDelegatingProxy(String type, ServiceModel serviceModel, String delegationMethodName, ServicePoint servicePoint)
   {
     ProxyBuilder builder = new ProxyBuilder(type, servicePoint);
 
     ClassFab classFab = builder.getClassFab();
 
     addConstructor(classFab, serviceModel);
 
     addServiceAccessor(classFab, delegationMethodName, servicePoint);
 
     builder.addServiceMethods("_service()");
 
     Class proxyClass = classFab.createClass();
     try
     {
       Constructor c = proxyClass.getConstructor(new Class[] { serviceModel.getClass() });
 
       return c.newInstance(new Object[] { serviceModel });
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(ex);
     }
   }
 
   public static RegistryShutdownListener createOuterProxy(Object delegate, ServicePoint servicePoint)
   {
     ProxyBuilder builder = new ProxyBuilder("OuterProxy", servicePoint, true);
 
     ClassFab classFab = builder.getClassFab();
 
     addDelegateAccessor(classFab, servicePoint, delegate);
 
     builder.addServiceMethods("_delegate()");
 
     Class proxyClass = classFab.createClass();
     try
     {
       return ((RegistryShutdownListener)ConstructorUtils.invokeConstructor(proxyClass, new Object[] { delegate }));
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(ex);
     }
   }
 
   private static void addDelegateAccessor(ClassFab classFab, ServicePoint servicePoint, Object delegate)
   {
     classFab.addField("_shutdown", Boolean.TYPE);
 
     Class delegateClass = ClassFabUtils.getInstanceClass(delegate, servicePoint.getServiceInterface());
 
     classFab.addField("_delegate", delegateClass);
 
     classFab.addConstructor(new Class[] { delegateClass }, null, "{ super(); _delegate = $1; }");
 
     classFab.addInterface(RegistryShutdownListener.class);
     if (RegistryShutdownListener.class.isAssignableFrom(delegateClass))
     {
       classFab.addMethod(17, new MethodSignature(Void.TYPE, "registryDidShutdown", null, null), "{ _delegate.registryDidShutdown(); _delegate = null; _shutdown = true; }");
     }
     else
     {
       classFab.addMethod(17, new MethodSignature(Void.TYPE, "registryDidShutdown", null, null), "{ _delegate = null; _shutdown = true; }");
     }
 
     BodyBuilder builder = new BodyBuilder();
 
     builder.begin();
 
     builder.addln("if (_shutdown)");
     builder.addln("  throw org.apache.hivemind.HiveMind#createRegistryShutdownException();");
 
     builder.add("return _delegate;");
 
     builder.end();
 
     classFab.addMethod(18, new MethodSignature(delegateClass, "_delegate", null, null), builder.toString());
   }
 
   private static void addConstructor(ClassFab classFab, ServiceModel model)
   {
     Class modelClass = model.getClass();
 
     classFab.addField("_serviceModel", modelClass);
 
     classFab.addConstructor(new Class[] { modelClass }, null, "{ super(); _serviceModel = $1; }");
   }
 
   private static void addServiceAccessor(ClassFab classFab, String serviceModelMethodName, ServicePoint servicePoint)
   {
     Class serviceInterface = servicePoint.getServiceInterface();
 
     classFab.addField("_service", serviceInterface);
 
     BodyBuilder builder = new BodyBuilder();
     builder.begin();
 
     builder.add("return (");
     builder.add(serviceInterface.getName());
     builder.add(") _serviceModel.");
     builder.add(serviceModelMethodName);
     builder.add("();");
 
     builder.end();
 
     classFab.addMethod(18, new MethodSignature(serviceInterface, "_service", null, null), builder.toString());
   }
 }