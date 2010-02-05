 package org.apache.hivemind.impl.servicemodel;
 
 import java.lang.reflect.Constructor;
 import org.apache.commons.logging.Log;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.events.RegistryShutdownListener;
 import org.apache.hivemind.impl.ConstructableServicePoint;
 import org.apache.hivemind.impl.ProxyBuilder;
 import org.apache.hivemind.internal.ExtensionPoint;
 import org.apache.hivemind.internal.ServicePoint;
 import org.apache.hivemind.service.BodyBuilder;
 import org.apache.hivemind.service.ClassFab;
 import org.apache.hivemind.service.MethodSignature;
 
 public final class SingletonServiceModel extends AbstractServiceModelImpl
 {
   protected static final String SERVICE_ACCESSOR_METHOD_NAME = "_service";
   private Object _serviceProxy;
   private SingletonInnerProxy _innerProxy;
   private Object _constructedService;
 
   public SingletonServiceModel(ConstructableServicePoint servicePoint)
   {
     super(servicePoint);
   }
 
   public synchronized Object getService()
   {
     if (this._serviceProxy == null) {
       this._serviceProxy = createSingletonProxy();
     }
     return this._serviceProxy;
   }
 
   public synchronized Object getActualServiceImplementation()
   {
     if (this._constructedService == null)
     {
       this._constructedService = super.constructServiceImplementation();
       super.registerWithShutdownCoordinator(this._constructedService);
     }
 
     Class serviceInterface = super.getServicePoint().getServiceInterface();
 
     if (!(serviceInterface.isInstance(this._constructedService))) {
       this._constructedService = super.constructBridgeProxy(this._constructedService);
     }
     return this._constructedService;
   }
 
   private Object createSingletonProxy()
   {
     if (this._log.isDebugEnabled()) {
       this._log.debug("Creating SingletonProxy for service " + super.getServicePoint().getExtensionPointId());
     }
 
     try
     {
       Class proxyClass = createSingletonProxyClass();
 
       Class innerProxyClass = createInnerProxyClass(proxyClass);
 
       Object result = proxyClass.newInstance();
 
       Constructor c = innerProxyClass.getConstructor(new Class[] { proxyClass, super.getClass() });
 
       this._innerProxy = ((SingletonInnerProxy)c.newInstance(new Object[] { result, this }));
 
       RegistryShutdownListener asListener = (RegistryShutdownListener)result;
 
       super.getServicePoint().addRegistryShutdownListener(asListener);
 
       return result;
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(ex);
     }
   }
 
   private Class createSingletonProxyClass()
   {
     ConstructableServicePoint servicePoint = super.getServicePoint();
 
     ProxyBuilder proxyBuilder = new ProxyBuilder("SingletonProxy", servicePoint, true);
 
     ClassFab classFab = proxyBuilder.getClassFab();
 
     Class serviceInterface = servicePoint.getServiceInterface();
 
     classFab.addField("_inner", serviceInterface);
     classFab.addField("_shutdown", Boolean.TYPE);
     if (!(RegistryShutdownListener.class.isAssignableFrom(serviceInterface)))
     {
       classFab.addInterface(RegistryShutdownListener.class);
 
       classFab.addMethod(17, new MethodSignature(Void.TYPE, "registryDidShutdown", null, null), "{ _shutdown = true; }");
     }
 
     classFab.addMethod(49, new MethodSignature(Void.TYPE, "_setInner", new Class[] { serviceInterface }, null), "{ _inner = $1; }");
 
     BodyBuilder builder = new BodyBuilder();
     builder.begin();
     builder.addln("if (_shutdown)");
     builder.begin();
     builder.addln("_inner = null;");
     builder.addln("throw org.apache.hivemind.HiveMind#createRegistryShutdownException();");
     builder.end();
 
     builder.addln("return _inner;");
     builder.end();
 
     classFab.addMethod(2, new MethodSignature(serviceInterface, "_getInner", null, null), builder.toString());
 
     proxyBuilder.addServiceMethods("_getInner()");
 
     return classFab.createClass();
   }
 
   private Class createInnerProxyClass(Class deferredProxyClass)
   {
     ServicePoint servicePoint = super.getServicePoint();
 
     Class serviceInterface = servicePoint.getServiceInterface();
     ProxyBuilder builder = new ProxyBuilder("InnerProxy", servicePoint);
 
     ClassFab classFab = builder.getClassFab();
 
     classFab.addField("_deferredProxy", deferredProxyClass);
     classFab.addField("_service", serviceInterface);
     classFab.addField("_serviceModel", super.getClass());
 
     BodyBuilder body = new BodyBuilder();
 
     body.begin();
 
     body.addln("super();");
     body.addln("_deferredProxy = $1;");
     body.addln("_serviceModel = $2;");
     body.addln("_deferredProxy._setInner(this);");
 
     body.end();
 
     classFab.addConstructor(new Class[] { deferredProxyClass, super.getClass() }, null, body.toString());
 
     body.clear();
     body.begin();
 
     body.add("if (_service == null)");
     body.begin();
 
     body.add("_service = (");
     body.add(serviceInterface.getName());
     body.addln(") _serviceModel.getActualServiceImplementation();");
 
     body.add("_deferredProxy._setInner(_service);");
 
     body.end();
 
     body.add("return _service;");
 
     body.end();
 
     classFab.addMethod(50, new MethodSignature(serviceInterface, "_service", null, null), body.toString());
 
     builder.addServiceMethods("_service()");
 
     body.clear();
     body.begin();
 
     body.add("_service();");
 
     body.end();
 
     classFab.addMethod(17, new MethodSignature(Void.TYPE, "_instantiateServiceImplementation", null, null), body.toString());
 
     classFab.addInterface(SingletonInnerProxy.class);
 
     return classFab.createClass();
   }
 
   public void instantiateService()
   {
     getService();
 
     this._innerProxy._instantiateServiceImplementation();
   }
 }