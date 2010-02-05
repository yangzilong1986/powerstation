 package org.apache.hivemind.impl;
 
 import java.util.List;
 import java.util.Locale;
 import java.util.Map;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.Messages;
 import org.apache.hivemind.Registry;
 import org.apache.hivemind.SymbolSource;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.internal.RegistryInfrastructure;
 
 public class RegistryImpl
   implements Registry
 {
   private RegistryInfrastructure _infrastructure;
 
   public RegistryImpl(RegistryInfrastructure infrastructure)
   {
     this._infrastructure = infrastructure;
   }
 
   public boolean containsConfiguration(String configurationId)
   {
     return this._infrastructure.containsConfiguration(configurationId, null);
   }
 
   public boolean containsService(Class serviceInterface)
   {
     return this._infrastructure.containsService(serviceInterface, null);
   }
 
   public boolean containsService(String serviceId, Class serviceInterface)
   {
     return this._infrastructure.containsService(serviceId, serviceInterface, null);
   }
 
   public List getConfiguration(String configurationId)
   {
     return this._infrastructure.getConfiguration(configurationId, null);
   }
 
   public boolean isConfigurationMappable(String configurationId)
   {
     return this._infrastructure.isConfigurationMappable(configurationId, null);
   }
 
   public Map getConfigurationAsMap(String configurationId)
   {
     return this._infrastructure.getConfigurationAsMap(configurationId, null);
   }
 
   public String expandSymbols(String input, Location location)
   {
     return this._infrastructure.expandSymbols(input, location);
   }
 
   public Object getService(String serviceId, Class serviceInterface)
   {
     return this._infrastructure.getService(serviceId, serviceInterface, null);
   }
 
   public Object getService(Class serviceInterface)
   {
     return this._infrastructure.getService(serviceInterface, null);
   }
 
   public Locale getLocale()
   {
     return this._infrastructure.getLocale();
   }
 
   public void shutdown()
   {
     this._infrastructure.shutdown();
   }
 
   public void cleanupThread()
   {
     this._infrastructure.cleanupThread();
   }
 
   public String valueForSymbol(String name)
   {
     return this._infrastructure.valueForSymbol(name);
   }
 
   public void setupThread()
   {
     this._infrastructure.setupThread();
   }
 
   public List getServiceIds(Class serviceInterface)
   {
     return this._infrastructure.getServiceIds(serviceInterface);
   }
 
   public Messages getModuleMessages(String moduleId)
   {
     Module module = this._infrastructure.getModule(moduleId);
     return ((module == null) ? null : module.getMessages());
   }
 }