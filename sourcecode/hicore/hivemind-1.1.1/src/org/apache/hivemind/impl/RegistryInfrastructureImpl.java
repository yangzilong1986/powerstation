 package org.apache.hivemind.impl;
 
 import java.util.Collection;
 import java.util.Collections;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.LinkedList;
 import java.util.List;
 import java.util.Locale;
 import java.util.Map;
 import org.apache.commons.logging.LogFactory;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.ErrorHandler;
 import org.apache.hivemind.HiveMindMessages;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.ShutdownCoordinator;
 import org.apache.hivemind.SymbolSource;
 import org.apache.hivemind.SymbolSourceContribution;
 import org.apache.hivemind.internal.ConfigurationPoint;
 import org.apache.hivemind.internal.ExtensionPoint;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.internal.RegistryInfrastructure;
 import org.apache.hivemind.internal.ServiceModelFactory;
 import org.apache.hivemind.internal.ServicePoint;
 import org.apache.hivemind.internal.ser.ServiceSerializationHelper;
 import org.apache.hivemind.internal.ser.ServiceSerializationSupport;
 import org.apache.hivemind.internal.ser.ServiceToken;
 import org.apache.hivemind.order.Orderer;
 import org.apache.hivemind.schema.Translator;
 import org.apache.hivemind.service.ThreadEventNotifier;
 import org.apache.hivemind.util.Defense;
 import org.apache.hivemind.util.PropertyUtils;
 import org.apache.hivemind.util.ToStringBuilder;
 
 public final class RegistryInfrastructureImpl
   implements RegistryInfrastructure, ServiceSerializationSupport
 {
   private static final String SYMBOL_SOURCES = "hivemind.SymbolSources";
   private Map _servicePoints = new HashMap();
 
   private Map _servicePointsByInterfaceClassName = new HashMap();
 
   private Map _configurationPoints = new HashMap();
   private SymbolSource[] _variableSources;
   private ErrorHandler _errorHandler;
   private Locale _locale;
   private ShutdownCoordinator _shutdownCoordinator;
   private Map _serviceTokens;
   private Map _serviceModelFactories;
   private boolean _started = false;
 
   private boolean _shutdown = false;
   private ThreadEventNotifier _threadEventNotifier;
   private TranslatorManager _translatorManager;
   private SymbolExpander _expander;
 
   public RegistryInfrastructureImpl(ErrorHandler errorHandler, Locale locale)
   {
     this._errorHandler = errorHandler;
     this._locale = locale;
 
     this._translatorManager = new TranslatorManager(this, errorHandler);
 
     this._expander = new SymbolExpander(this._errorHandler, this);
   }
 
   public Locale getLocale()
   {
     return this._locale;
   }
 
   public void addServicePoint(ServicePoint point)
   {
     checkStarted();
 
     this._servicePoints.put(point.getExtensionPointId(), point);
 
     addServicePointByInterface(point);
   }
 
   private void addServicePointByInterface(ServicePoint point)
   {
     String key = point.getServiceInterfaceClassName();
 
     List l = (List)this._servicePointsByInterfaceClassName.get(key);
 
     if (l == null)
     {
       l = new LinkedList();
       this._servicePointsByInterfaceClassName.put(key, l);
     }
 
     l.add(point);
   }
 
   public void addConfigurationPoint(ConfigurationPoint point)
   {
     checkStarted();
 
     this._configurationPoints.put(point.getExtensionPointId(), point);
   }
 
   public ServicePoint getServicePoint(String serviceId, Module module)
   {
     checkShutdown();
     ServicePoint result = (ServicePoint)this._servicePoints.get(serviceId);
     if (result == null)
     {
       if (serviceId.indexOf(46) == -1)
       {
         List possibleMatches = getMatchingServiceIds(serviceId);
         if (!(possibleMatches.isEmpty()))
         {
           StringBuffer sb = new StringBuffer();
           for (Iterator i = possibleMatches.iterator(); i.hasNext(); )
           {
             String matching = (String)i.next();
             sb.append('"');
             sb.append(matching);
             sb.append('"');
             if (i.hasNext())
             {
               sb.append(", ");
             }
           }
           throw new ApplicationRuntimeException(ImplMessages.unqualifiedServicePoint(serviceId, sb.toString()));
         }
 
       }
 
       throw new ApplicationRuntimeException(ImplMessages.noSuchServicePoint(serviceId));
     }
 
     if (!(result.visibleToModule(module))) {
       throw new ApplicationRuntimeException(ImplMessages.serviceNotVisible(serviceId, module));
     }
     return result;
   }
 
   private List getMatchingServiceIds(String serviceId)
   {
     List possibleMatches = new LinkedList();
     for (Iterator i = this._servicePoints.values().iterator(); i.hasNext(); )
     {
       ServicePoint servicePoint = (ServicePoint)i.next();
       if (servicePoint.getExtensionPointId().equals(servicePoint.getModule().getModuleId() + "." + serviceId))
       {
         possibleMatches.add(servicePoint.getExtensionPointId());
       }
     }
     return possibleMatches;
   }
 
   public Object getService(String serviceId, Class serviceInterface, Module module)
   {
     ServicePoint point = getServicePoint(serviceId, module);
 
     return point.getService(serviceInterface);
   }
 
   public Object getService(Class serviceInterface, Module module)
   {
     String key = serviceInterface.getName();
 
     List servicePoints = (List)this._servicePointsByInterfaceClassName.get(key);
 
     if (servicePoints == null) {
       servicePoints = Collections.EMPTY_LIST;
     }
     ServicePoint point = null;
     int count = 0;
 
     Iterator i = servicePoints.iterator();
     while (i.hasNext())
     {
       ServicePoint sp = (ServicePoint)i.next();
 
       if (!(sp.visibleToModule(module))) {
         continue;
       }
       point = sp;
 
       ++count;
     }
 
     if (count == 0) {
       throw new ApplicationRuntimeException(ImplMessages.noServicePointForInterface(serviceInterface));
     }
 
     if (count > 1) {
       throw new ApplicationRuntimeException(ImplMessages.multipleServicePointsForInterface(serviceInterface, servicePoints));
     }
 
     return point.getService(serviceInterface);
   }
 
   public ConfigurationPoint getConfigurationPoint(String configurationId, Module module)
   {
     checkShutdown();
 
     ConfigurationPoint result = (ConfigurationPoint)this._configurationPoints.get(configurationId);
 
     if (result == null) {
       throw new ApplicationRuntimeException(ImplMessages.noSuchConfiguration(configurationId));
     }
     if (!(result.visibleToModule(module))) {
       throw new ApplicationRuntimeException(ImplMessages.configurationNotVisible(configurationId, module));
     }
 
     return result;
   }
 
   public List getConfiguration(String configurationId, Module module)
   {
     ConfigurationPoint point = getConfigurationPoint(configurationId, module);
 
     return point.getElements();
   }
 
   public boolean isConfigurationMappable(String configurationId, Module module)
   {
     ConfigurationPoint point = getConfigurationPoint(configurationId, module);
 
     return point.areElementsMappable();
   }
 
   public Map getConfigurationAsMap(String configurationId, Module module)
   {
     ConfigurationPoint point = getConfigurationPoint(configurationId, module);
 
     return point.getElementsAsMap();
   }
 
   public String toString()
   {
     ToStringBuilder builder = new ToStringBuilder(this);
 
     builder.append("locale", this._locale);
 
     return builder.toString();
   }
 
   public String expandSymbols(String text, Location location)
   {
     return this._expander.expandSymbols(text, location);
   }
 
   public String valueForSymbol(String name)
   {
     checkShutdown();
 
     SymbolSource[] sources = getSymbolSources();
 
     for (int i = 0; i < sources.length; ++i)
     {
       String value = sources[i].valueForSymbol(name);
 
       if (value != null) {
         return value;
       }
     }
     return null;
   }
 
   private synchronized SymbolSource[] getSymbolSources()
   {
     if (this._variableSources != null) {
       return this._variableSources;
     }
     List contributions = getConfiguration("hivemind.SymbolSources", null);
 
     Orderer o = new Orderer(LogFactory.getLog("hivemind.SymbolSources"), this._errorHandler, ImplMessages.symbolSourceContribution());
 
     Iterator i = contributions.iterator();
     while (i.hasNext())
     {
       SymbolSourceContribution c = (SymbolSourceContribution)i.next();
 
       o.add(c, c.getName(), c.getPrecedingNames(), c.getFollowingNames());
     }
 
     List sources = o.getOrderedObjects();
 
     int count = sources.size();
 
     this._variableSources = new SymbolSource[count];
 
     for (int j = 0; j < count; ++j)
     {
       SymbolSourceContribution c = (SymbolSourceContribution)sources.get(j);
       this._variableSources[j] = c.getSource();
     }
 
     return this._variableSources;
   }
 
   public void setShutdownCoordinator(ShutdownCoordinator coordinator)
   {
     this._shutdownCoordinator = coordinator;
   }
 
   public synchronized void shutdown()
   {
     checkShutdown();
 
     ServiceSerializationHelper.setServiceSerializationSupport(null);
 
     ShutdownCoordinator coordinatorService = (ShutdownCoordinator)getService("hivemind.ShutdownCoordinator", ShutdownCoordinator.class, null);
 
     coordinatorService.shutdown();
 
     this._shutdown = true;
 
     this._shutdownCoordinator.shutdown();
 
     this._servicePoints = null;
     this._servicePointsByInterfaceClassName = null;
     this._configurationPoints = null;
     this._shutdownCoordinator = null;
     this._variableSources = null;
     this._serviceModelFactories = null;
     this._threadEventNotifier = null;
     this._serviceTokens = null;
 
     PropertyUtils.clearCache();
   }
 
   private void checkShutdown()
   {
     if (this._shutdown)
       throw new ApplicationRuntimeException(HiveMindMessages.registryShutdown());
   }
 
   private void checkStarted()
   {
     if (this._started)
       throw new IllegalStateException(ImplMessages.registryAlreadyStarted());
   }
 
   public void startup()
   {
     checkStarted();
 
     ServiceSerializationHelper.setServiceSerializationSupport(this);
 
     this._started = true;
 
     Runnable startup = (Runnable)getService("hivemind.Startup", Runnable.class, null);
 
     startup.run();
   }
 
   public synchronized ServiceModelFactory getServiceModelFactory(String name)
   {
     if (this._serviceModelFactories == null) {
       readServiceModelFactories();
     }
     ServiceModelFactory result = (ServiceModelFactory)this._serviceModelFactories.get(name);
 
     if (result == null) {
       throw new ApplicationRuntimeException(ImplMessages.unknownServiceModel(name));
     }
     return result;
   }
 
   private void readServiceModelFactories()
   {
     List l = getConfiguration("hivemind.ServiceModels", null);
 
     this._serviceModelFactories = new HashMap();
 
     Iterator i = l.iterator();
 
     while (i.hasNext())
     {
       ServiceModelContribution smc = (ServiceModelContribution)i.next();
 
       String name = smc.getName();
 
       this._serviceModelFactories.put(name, smc.getFactory());
     }
   }
 
   public synchronized void cleanupThread()
   {
     if (this._threadEventNotifier == null) {
       this._threadEventNotifier = ((ThreadEventNotifier)getService("hivemind.ThreadEventNotifier", ThreadEventNotifier.class, null));
     }
 
     this._threadEventNotifier.fireThreadCleanup();
   }
 
   public boolean containsConfiguration(String configurationId, Module module)
   {
     checkShutdown();
 
     ConfigurationPoint result = (ConfigurationPoint)this._configurationPoints.get(configurationId);
 
     return ((result != null) && (result.visibleToModule(module)));
   }
 
   public boolean containsService(Class serviceInterface, Module module)
   {
     checkShutdown();
 
     String key = serviceInterface.getName();
 
     List servicePoints = (List)this._servicePointsByInterfaceClassName.get(key);
 
     if (servicePoints == null) {
       return false;
     }
     int count = 0;
 
     Iterator i = servicePoints.iterator();
     while (i.hasNext())
     {
       ServicePoint point = (ServicePoint)i.next();
 
       if (point.visibleToModule(module)) {
         ++count;
       }
     }
     return (count == 1);
   }
 
   public boolean containsService(String serviceId, Class serviceInterface, Module module)
   {
     checkShutdown();
 
     ServicePoint point = (ServicePoint)this._servicePoints.get(serviceId);
 
     if (point == null) {
       return false;
     }
     return ((point.visibleToModule(module)) && (point.getServiceInterface().equals(serviceInterface)));
   }
 
   public ErrorHandler getErrorHander()
   {
     return this._errorHandler;
   }
 
   public Translator getTranslator(String constructor)
   {
     return this._translatorManager.getTranslator(constructor);
   }
 
   public Object getServiceFromToken(ServiceToken token)
   {
     Defense.notNull(token, "token");
 
     checkShutdown();
 
     String serviceId = token.getServiceId();
 
     ServicePoint sp = (ServicePoint)this._servicePoints.get(serviceId);
 
     return sp.getService(Object.class);
   }
 
   public synchronized ServiceToken getServiceTokenForService(String serviceId)
   {
     Defense.notNull(serviceId, "serviceId");
 
     checkShutdown();
 
     if (this._serviceTokens == null) {
       this._serviceTokens = new HashMap();
     }
     ServiceToken result = (ServiceToken)this._serviceTokens.get(serviceId);
 
     if (result == null)
     {
       result = new ServiceToken(serviceId);
       this._serviceTokens.put(serviceId, result);
     }
 
     return result;
   }
 
   public void setupThread()
   {
     ServiceSerializationHelper.setServiceSerializationSupport(this);
   }
 
   public Module getModule(String moduleId)
   {
     for (Iterator i = this._servicePoints.values().iterator(); i.hasNext(); )
     {
       ServicePoint servicePoint = (ServicePoint)i.next();
 
       if (servicePoint.getModule().getModuleId().equals(moduleId))
       {
         return servicePoint.getModule();
       }
     }
     return null;
   }
 
   public List getServiceIds(Class serviceInterface)
   {
     List serviceIds = new LinkedList();
     if (serviceInterface == null)
     {
       return serviceIds;
     }
     for (Iterator i = this._servicePoints.values().iterator(); i.hasNext(); )
     {
       ServicePoint servicePoint = (ServicePoint)i.next();
 
       if ((serviceInterface.getName().equals(servicePoint.getServiceInterfaceClassName())) && (servicePoint.visibleToModule(null)))
       {
         serviceIds.add(servicePoint.getExtensionPointId());
       }
     }
 
     return serviceIds;
   }
 }