 package org.apache.hivemind.impl.servicemodel;
 
 import java.util.ArrayList;
 import java.util.List;
 import org.apache.commons.logging.Log;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.PoolManageable;
 import org.apache.hivemind.events.RegistryShutdownListener;
 import org.apache.hivemind.impl.ConstructableServicePoint;
 import org.apache.hivemind.impl.ProxyUtils;
 import org.apache.hivemind.internal.ExtensionPoint;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.internal.ServicePoint;
 import org.apache.hivemind.service.ThreadCleanupListener;
 import org.apache.hivemind.service.ThreadEventNotifier;
 
 public class PooledServiceModel extends AbstractServiceModelImpl
 {
   protected static final String SERVICE_ACCESSOR_METHOD_NAME = "_service";
   private final Object _serviceProxy;
   private final ThreadEventNotifier _notifier;
   private final ThreadLocal _activeService = new ThreadLocal();
 
   private final List _servicePool = new ArrayList();
   private Class _serviceInterface;
   private static final PoolManageable NULL_MANAGEABLE = new PoolManageable() { public void activateService() {  }
 
     public void passivateService() {  } } ;
 
   public PooledServiceModel(ConstructableServicePoint servicePoint)
   {
     super(servicePoint);
 
     this._serviceInterface = servicePoint.getServiceInterface();
 
     Module module = super.getServicePoint().getModule();
 
     this._notifier = ((ThreadEventNotifier)module.getService("hivemind.ThreadEventNotifier", ThreadEventNotifier.class));
 
     this._serviceProxy = constructServiceProxy();
   }
 
   public Object getService()
   {
     return this._serviceProxy;
   }
 
   private Object constructServiceProxy()
   {
     ConstructableServicePoint servicePoint = super.getServicePoint();
 
     if (this._log.isDebugEnabled()) {
       this._log.debug("Creating PooledProxy for service " + servicePoint.getExtensionPointId());
     }
     Object proxy = ProxyUtils.createDelegatingProxy("PooledProxy", this, "getServiceImplementationForCurrentThread", servicePoint);
 
     Object intercepted = super.addInterceptors(proxy);
 
     RegistryShutdownListener outerProxy = ProxyUtils.createOuterProxy(intercepted, servicePoint);
 
     servicePoint.addRegistryShutdownListener(outerProxy);
 
     return outerProxy;
   }
 
   public Object getServiceImplementationForCurrentThread()
   {
     PooledService pooled = (PooledService)this._activeService.get();
 
     if (pooled == null)
     {
       pooled = obtainPooledService();
 
       pooled.activate();
 
       this._notifier.addThreadCleanupListener(pooled);
       this._activeService.set(pooled);
     }
 
     return pooled.getService();
   }
 
   private PooledService obtainPooledService()
   {
     PooledService result = getServiceFromPool();
 
     if (result == null) {
       result = constructPooledService();
     }
     return result;
   }
 
   private synchronized PooledService getServiceFromPool()
   {
     int count = this._servicePool.size();
 
     if (count == 0) {
       return null;
     }
     return ((PooledService)this._servicePool.remove(count - 1));
   }
 
   private synchronized void returnServiceToPool(PooledService pooled)
   {
     this._servicePool.add(pooled);
   }
 
   private PooledService constructPooledService()
   {
     try
     {
       Object core = super.constructCoreServiceImplementation();
 
       if (!(this._serviceInterface.isInstance(core))) {
         core = super.constructBridgeProxy(core);
       }
       super.registerWithShutdownCoordinator(core);
 
       return new PooledService(core);
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(ServiceModelMessages.unableToConstructService(super.getServicePoint(), ex), ex);
     }
   }
 
   private void unbindPooledServiceFromCurrentThread(PooledService pooled)
   {
     this._activeService.set(null);
 
     pooled.passivate();
 
     returnServiceToPool(pooled);
   }
 
   public void instantiateService()
   {
     getServiceImplementationForCurrentThread();
   }
 
   private class PooledService
     implements ThreadCleanupListener
   {
     private Object _core;
     private PoolManageable _managed;
 
     PooledService(Object paramObject)
     {
       this._core = paramObject;
 
       if (paramObject instanceof PoolManageable)
         this._managed = ((PoolManageable)paramObject);
       else
         this._managed = PooledServiceModel.NULL_MANAGEABLE;
     }
 
     public void threadDidCleanup()
     {
       PooledServiceModel.this.unbindPooledServiceFromCurrentThread(this);
     }
 
     void activate()
     {
       this._managed.activateService();
     }
 
     void passivate()
     {
       this._managed.passivateService();
     }
 
     public Object getService()
     {
       return this._core;
     }
   }
 }