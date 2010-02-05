 package org.apache.hivemind.impl.servicemodel;
 
 import java.util.List;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.Locatable;
 import org.apache.hivemind.ShutdownCoordinator;
 import org.apache.hivemind.events.RegistryShutdownListener;
 import org.apache.hivemind.impl.ConstructableServicePoint;
 import org.apache.hivemind.impl.InterceptorStackImpl;
 import org.apache.hivemind.impl.ProxyBuilder;
 import org.apache.hivemind.internal.ExtensionPoint;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.internal.ServiceImplementationConstructor;
 import org.apache.hivemind.internal.ServiceInterceptorContribution;
 import org.apache.hivemind.internal.ServiceModel;
 import org.apache.hivemind.internal.ServicePoint;
 import org.apache.hivemind.service.ClassFab;
 import org.apache.hivemind.util.ConstructorUtils;
 
 public abstract class AbstractServiceModelImpl
   implements ServiceModel
 {
   protected final Log _log;
   private ConstructableServicePoint _servicePoint;
   private Class _bridgeProxyClass;
 
   public AbstractServiceModelImpl(ConstructableServicePoint servicePoint)
   {
     this._log = LogFactory.getLog(servicePoint.getExtensionPointId());
 
     this._servicePoint = servicePoint;
   }
 
   protected Object addInterceptors(Object core)
   {
     List interceptors = this._servicePoint.getOrderedInterceptorContributions();
 
     int count = (interceptors == null) ? 0 : interceptors.size();
 
     if (count == 0) {
       return core;
     }
     InterceptorStackImpl stack = new InterceptorStackImpl(this._log, this._servicePoint, core);
 
     for (int i = count - 1; i >= 0; --i)
     {
       ServiceInterceptorContribution ic = (ServiceInterceptorContribution)interceptors.get(i);
 
       stack.process(ic);
     }
 
     return stack.peek();
   }
 
   protected Object constructCoreServiceImplementation()
   {
     if (this._log.isDebugEnabled()) {
       this._log.debug("Constructing core service implementation for service " + this._servicePoint.getExtensionPointId());
     }
 
     Class serviceInterface = this._servicePoint.getServiceInterface();
     Class declaredInterface = this._servicePoint.getDeclaredInterface();
 
     ServiceImplementationConstructor constructor = this._servicePoint.getServiceConstructor();
     Object result = constructor.constructCoreServiceImplementation();
 
     if (result == null) {
       throw new ApplicationRuntimeException(ServiceModelMessages.factoryReturnedNull(this._servicePoint), constructor.getLocation(), null);
     }
 
     if ((!(serviceInterface.isInstance(result))) && (!(declaredInterface.isInstance(result)))) {
       throw new ApplicationRuntimeException(ServiceModelMessages.factoryWrongInterface(this._servicePoint, result, serviceInterface), constructor.getLocation(), null);
     }
 
     HiveMind.setLocation(result, constructor.getLocation());
 
     return result;
   }
 
   protected Object constructServiceImplementation()
   {
     Object result = constructNewServiceImplementation();
 
     this._servicePoint.clearConstructorInformation();
 
     return result;
   }
 
   protected Object constructNewServiceImplementation()
   {
     try
     {
       Object core = constructCoreServiceImplementation();
 
       Object intercepted = addInterceptors(core);
 
       return intercepted;
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(ServiceModelMessages.unableToConstructService(this._servicePoint, ex), ex);
     }
   }
 
   public ConstructableServicePoint getServicePoint()
   {
     return this._servicePoint;
   }
 
   protected Object constructBridgeProxy(Object service)
   {
     Class bridgeProxyClass = getBridgeProxyClass(service);
 
     return ConstructorUtils.invokeConstructor(bridgeProxyClass, new Object[] { service });
   }
 
   private synchronized Class getBridgeProxyClass(Object service)
   {
     if (this._bridgeProxyClass == null) {
       this._bridgeProxyClass = constructBridgeProxyClass(service);
     }
     return this._bridgeProxyClass;
   }
 
   private Class constructBridgeProxyClass(Object service)
   {
     ProxyBuilder builder = new ProxyBuilder("BridgeProxy", getServicePoint());
 
     ClassFab cf = builder.getClassFab();
 
     Class serviceType = service.getClass();
 
     cf.addField("_service", serviceType);
 
     cf.addConstructor(new Class[] { serviceType }, null, "{ super(); _service = $1; }");
 
     builder.addServiceMethods("_service");
 
     return cf.createClass();
   }
 
   protected void registerWithShutdownCoordinator(Object service)
   {
     if (!(service instanceof RegistryShutdownListener))
       return;
     ShutdownCoordinator coordinator = (ShutdownCoordinator)getServicePoint().getModule().getService(ShutdownCoordinator.class);
 
     RegistryShutdownListener asListener = (RegistryShutdownListener)service;
     coordinator.addRegistryShutdownListener(asListener);
   }
 
   public abstract void instantiateService();
 
   public abstract Object getService();
 }