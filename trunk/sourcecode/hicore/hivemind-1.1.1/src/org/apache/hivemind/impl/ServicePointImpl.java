 package org.apache.hivemind.impl;
 
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.Occurances;
 import org.apache.hivemind.ShutdownCoordinator;
 import org.apache.hivemind.events.RegistryShutdownListener;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.internal.ServiceImplementationConstructor;
 import org.apache.hivemind.internal.ServiceInterceptorContribution;
 import org.apache.hivemind.internal.ServiceModel;
 import org.apache.hivemind.internal.ServiceModelFactory;
 import org.apache.hivemind.order.Orderer;
 import org.apache.hivemind.schema.Schema;
 import org.apache.hivemind.service.InterfaceSynthesizer;
 import org.apache.hivemind.util.ToStringBuilder;
 
 public final class ServicePointImpl extends AbstractExtensionPoint
   implements ConstructableServicePoint
 {
   private Object _service;
   private boolean _building;
   private String _serviceInterfaceName;
   private Class _serviceInterface;
   private Class _declaredInterface;
   private ServiceImplementationConstructor _defaultServiceConstructor;
   private ServiceImplementationConstructor _serviceConstructor;
   private List _interceptorContributions;
   private boolean _interceptorsOrdered;
   private Schema _parametersSchema;
   private Occurances _parametersCount;
   private String _serviceModel;
   private ShutdownCoordinator _shutdownCoordinator;
   private ServiceModel _serviceModelObject;
 
   protected void extendDescription(ToStringBuilder builder)
   {
     if (this._service != null) {
       builder.append("service", this._service);
     }
     builder.append("serviceInterfaceName", this._serviceInterfaceName);
     builder.append("defaultServiceConstructor", this._defaultServiceConstructor);
     builder.append("serviceConstructor", this._serviceConstructor);
     builder.append("interceptorContributions", this._interceptorContributions);
     builder.append("parametersSchema", this._parametersSchema);
     builder.append("parametersCount", this._parametersCount);
     builder.append("serviceModel", this._serviceModel);
 
     if (this._building)
       builder.append("building", this._building);
   }
 
   public void addInterceptorContribution(ServiceInterceptorContribution contribution)
   {
     if (this._interceptorContributions == null) {
       this._interceptorContributions = new ArrayList();
     }
     this._interceptorContributions.add(contribution);
   }
 
   public synchronized Class getServiceInterface()
   {
     if (this._serviceInterface == null) {
       this._serviceInterface = lookupServiceInterface();
     }
     return this._serviceInterface;
   }
 
   public synchronized Class getDeclaredInterface()
   {
     if (this._declaredInterface == null) {
       this._declaredInterface = lookupDeclaredInterface();
     }
     return this._declaredInterface;
   }
 
   public String getServiceInterfaceClassName()
   {
     return this._serviceInterfaceName;
   }
 
   private Class lookupDeclaredInterface()
   {
     Class result = null;
     try
     {
       result = super.getModule().resolveType(this._serviceInterfaceName);
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(ImplMessages.badInterface(this._serviceInterfaceName, super.getExtensionPointId()), super.getLocation(), ex);
     }
 
     return result;
   }
 
   private Class lookupServiceInterface()
   {
     Class declaredInterface = getDeclaredInterface();
 
     if (declaredInterface.isInterface()) {
       return declaredInterface;
     }
 
     InterfaceSynthesizer is = (InterfaceSynthesizer)super.getModule().getService("hivemind.InterfaceSynthesizer", InterfaceSynthesizer.class);
 
     return is.synthesizeInterface(declaredInterface);
   }
 
   public void setServiceConstructor(ServiceImplementationConstructor contribution, boolean defaultConstructor)
   {
     if (defaultConstructor)
       this._defaultServiceConstructor = contribution;
     else
       this._serviceConstructor = contribution;
   }
 
   public void setServiceInterfaceName(String string)
   {
     this._serviceInterfaceName = string;
   }
 
   public void setParametersSchema(Schema schema)
   {
     this._parametersSchema = schema;
   }
 
   public Schema getParametersSchema()
   {
     return this._parametersSchema;
   }
 
   public ServiceImplementationConstructor getServiceConstructor(boolean defaultConstructor)
   {
     return ((defaultConstructor) ? this._defaultServiceConstructor : this._serviceConstructor);
   }
 
   private synchronized Object getService()
   {
     if (this._service == null)
     {
       if (this._building) {
         throw new ApplicationRuntimeException(ImplMessages.recursiveServiceBuild(this));
       }
       this._building = true;
       try
       {
         ServiceModelFactory factory = super.getModule().getServiceModelFactory(getServiceModel());
 
         this._serviceModelObject = factory.createServiceModelForService(this);
 
         this._service = this._serviceModelObject.getService();
       }
       finally
       {
         this._building = false;
       }
     }
 
     return this._service;
   }
 
   public Object getService(Class serviceInterface)
   {
     Object result = getService();
 
     if (!(serviceInterface.isAssignableFrom(result.getClass())))
     {
       throw new ApplicationRuntimeException(ImplMessages.serviceWrongInterface(this, serviceInterface), super.getLocation(), null);
     }
 
     return result;
   }
 
   public String getServiceModel()
   {
     return this._serviceModel;
   }
 
   public void setServiceModel(String model)
   {
     this._serviceModel = model;
   }
 
   public void clearConstructorInformation()
   {
     this._serviceConstructor = null;
     this._interceptorContributions = null;
   }
 
   public List getOrderedInterceptorContributions()
   {
     if (!(this._interceptorsOrdered))
     {
       this._interceptorContributions = orderInterceptors();
       this._interceptorsOrdered = true;
     }
 
     return this._interceptorContributions;
   }
 
   private List orderInterceptors()
   {
     if (HiveMind.isEmpty(this._interceptorContributions)) {
       return null;
     }
 
     Log log = LogFactory.getLog(super.getExtensionPointId());
 
     Orderer orderer = new Orderer(log, super.getModule().getErrorHandler(), ImplMessages.interceptorContribution());
 
     Iterator i = this._interceptorContributions.iterator();
     while (i.hasNext())
     {
       ServiceInterceptorContribution sic = (ServiceInterceptorContribution)i.next();
 
       orderer.add(sic, sic.getName(), sic.getPrecedingInterceptorIds(), sic.getFollowingInterceptorIds());
     }
 
     return orderer.getOrderedObjects();
   }
 
   public void setShutdownCoordinator(ShutdownCoordinator coordinator)
   {
     this._shutdownCoordinator = coordinator;
   }
 
   public void addRegistryShutdownListener(RegistryShutdownListener listener)
   {
     this._shutdownCoordinator.addRegistryShutdownListener(listener);
   }
 
   public void forceServiceInstantiation()
   {
     getService();
 
     this._serviceModelObject.instantiateService();
   }
 
   public Occurances getParametersCount()
   {
     return this._parametersCount;
   }
 
   public void setParametersCount(Occurances occurances)
   {
     this._parametersCount = occurances;
   }
 
   public ServiceImplementationConstructor getServiceConstructor()
   {
     return ((this._serviceConstructor == null) ? this._defaultServiceConstructor : this._serviceConstructor);
   }
 }