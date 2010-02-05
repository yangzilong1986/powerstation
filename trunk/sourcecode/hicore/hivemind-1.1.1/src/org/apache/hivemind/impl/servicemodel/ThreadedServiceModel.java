 package org.apache.hivemind.impl.servicemodel;
 
 import org.apache.commons.logging.Log;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.Discardable;
 import org.apache.hivemind.events.RegistryShutdownListener;
 import org.apache.hivemind.impl.ConstructableServicePoint;
 import org.apache.hivemind.impl.ProxyUtils;
 import org.apache.hivemind.internal.ExtensionPoint;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.internal.ServicePoint;
 import org.apache.hivemind.service.ThreadCleanupListener;
 import org.apache.hivemind.service.ThreadEventNotifier;
 
 public final class ThreadedServiceModel extends AbstractServiceModelImpl
 {
   protected static final String SERVICE_ACCESSOR_METHOD_NAME = "_service";
   private final Object _serviceProxy;
   private final ThreadEventNotifier _notifier;
   private final ThreadLocal _activeService = new ThreadLocal();
   private Class _serviceInterface;
 
   public ThreadedServiceModel(ConstructableServicePoint servicePoint)
   {
     super(servicePoint);
 
     this._serviceInterface = servicePoint.getServiceInterface();
 
     Module module = super.getServicePoint().getModule();
 
     this._notifier = ((ThreadEventNotifier)module.getService("hivemind.ThreadEventNotifier", ThreadEventNotifier.class));
 
     this._serviceProxy = createServiceProxy();
   }
 
   public Object getService()
   {
     return this._serviceProxy;
   }
 
   private Object createServiceProxy()
   {
     ConstructableServicePoint servicePoint = super.getServicePoint();
 
     if (this._log.isDebugEnabled()) {
       this._log.debug("Creating ThreadedProxy for service " + servicePoint.getExtensionPointId());
     }
     Object proxy = ProxyUtils.createDelegatingProxy("ThreadedProxy", this, "getServiceImplementationForCurrentThread", servicePoint);
 
     Object intercepted = super.addInterceptors(proxy);
 
     RegistryShutdownListener outerProxy = ProxyUtils.createOuterProxy(intercepted, servicePoint);
 
     servicePoint.addRegistryShutdownListener(outerProxy);
 
     return outerProxy;
   }
 
   public Object getServiceImplementationForCurrentThread()
   {
     Object result = this._activeService.get();
 
     if (result == null) {
       result = constructInstanceForCurrentThread();
     }
     return result;
   }
 
   private Object constructInstanceForCurrentThread()
   {
     try
     {
       Object core = super.constructCoreServiceImplementation();
 
       if (core instanceof RegistryShutdownListener) {
         this._log.error(ServiceModelMessages.registryCleanupIgnored(super.getServicePoint()));
       }
       this._notifier.addThreadCleanupListener(new CleanupListener(core));
 
       if (!(this._serviceInterface.isInstance(core))) {
         core = super.constructBridgeProxy(core);
       }
       this._activeService.set(core);
 
       return core;
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(ServiceModelMessages.unableToConstructService(super.getServicePoint(), ex), ex);
     }
   }
 
   private void unbindServiceFromCurrentThread()
   {
     this._activeService.set(null);
   }
 
   public void instantiateService()
   {
     getServiceImplementationForCurrentThread();
   }
 
   class CleanupListener
     implements ThreadCleanupListener
   {
     private final Object _core;
 
     CleanupListener(Object paramObject)
     {
       this._core = paramObject;
     }
 
     public void threadDidCleanup()
     {
       ThreadedServiceModel.this.unbindServiceFromCurrentThread();
 
       if (!(this._core instanceof Discardable))
         return;
       Discardable d = (Discardable)this._core;
 
       d.threadDidDiscardService();
     }
   }
 }