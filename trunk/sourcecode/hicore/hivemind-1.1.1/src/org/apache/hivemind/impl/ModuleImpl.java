 package org.apache.hivemind.impl;
 
 import java.util.HashMap;
 import java.util.List;
 import java.util.Locale;
 import java.util.Map;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.ClassResolver;
 import org.apache.hivemind.ErrorHandler;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.Messages;
 import org.apache.hivemind.SymbolSource;
 import org.apache.hivemind.internal.ConfigurationPoint;
 import org.apache.hivemind.internal.MessageFinder;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.internal.RegistryInfrastructure;
 import org.apache.hivemind.internal.ServiceModelFactory;
 import org.apache.hivemind.internal.ServicePoint;
 import org.apache.hivemind.schema.Translator;
 import org.apache.hivemind.service.ThreadLocale;
 import org.apache.hivemind.util.IdUtils;
 import org.apache.hivemind.util.ToStringBuilder;
 
 public final class ModuleImpl extends BaseLocatable
   implements Module
 {
   private String _moduleId;
   private String _packageName;
   private RegistryInfrastructure _registry;
   private ClassResolver _resolver;
   private Messages _messages;
   private final Map _typeCache;
 
   public ModuleImpl()
   {
     this._typeCache = new HashMap();
   }
 
   public List getConfiguration(String extensionPointId) {
     String qualifiedId = IdUtils.qualify(this._moduleId, extensionPointId);
 
     return this._registry.getConfiguration(qualifiedId, this);
   }
 
   public boolean isConfigurationMappable(String configurationId)
   {
     String qualifiedId = IdUtils.qualify(this._moduleId, configurationId);
 
     return this._registry.getConfigurationPoint(qualifiedId, this).areElementsMappable();
   }
 
   public Map getConfigurationAsMap(String configurationId)
   {
     String qualifiedId = IdUtils.qualify(this._moduleId, configurationId);
 
     return this._registry.getConfigurationPoint(qualifiedId, this).getElementsAsMap();
   }
 
   public String getModuleId()
   {
     return this._moduleId;
   }
 
   public void setPackageName(String packageName)
   {
     this._packageName = packageName;
   }
 
   public boolean containsService(Class serviceInterface)
   {
     return this._registry.containsService(serviceInterface, this);
   }
 
   public Object getService(String serviceId, Class serviceInterface)
   {
     String qualifiedId = IdUtils.qualify(this._moduleId, serviceId);
 
     return this._registry.getService(qualifiedId, serviceInterface, this);
   }
 
   public Object getService(Class serviceInterface)
   {
     return this._registry.getService(serviceInterface, this);
   }
 
   public void setModuleId(String string)
   {
     this._moduleId = string;
   }
 
   public void setRegistry(RegistryInfrastructure registry)
   {
     this._registry = registry;
   }
 
   public void setClassResolver(ClassResolver resolver)
   {
     this._resolver = resolver;
   }
 
   public ClassResolver getClassResolver()
   {
     return this._resolver;
   }
 
   public synchronized Messages getMessages()
   {
     if (this._messages == null)
     {
       ThreadLocale threadLocale = (ThreadLocale)this._registry.getService("hivemind.ThreadLocale", ThreadLocale.class, this);
 
       MessageFinder finder = new MessageFinderImpl(super.getLocation().getResource());
 
       this._messages = new ModuleMessages(finder, threadLocale);
     }
 
     return this._messages;
   }
 
   public String expandSymbols(String input, Location location)
   {
     return this._registry.expandSymbols(input, location);
   }
 
   public String toString()
   {
     ToStringBuilder builder = new ToStringBuilder(this);
 
     builder.append("moduleId", this._moduleId);
     builder.append("classResolver", this._resolver);
 
     return builder.toString();
   }
 
   public ServicePoint getServicePoint(String serviceId)
   {
     String qualifiedId = IdUtils.qualify(this._moduleId, serviceId);
 
     return this._registry.getServicePoint(qualifiedId, this);
   }
 
   public ServiceModelFactory getServiceModelFactory(String name)
   {
     return this._registry.getServiceModelFactory(name);
   }
 
   public Translator getTranslator(String translator)
   {
     return this._registry.getTranslator(translator);
   }
 
   public Locale getLocale()
   {
     return this._registry.getLocale();
   }
 
   public ErrorHandler getErrorHandler()
   {
     return this._registry.getErrorHander();
   }
 
   public String valueForSymbol(String symbol)
   {
     return this._registry.valueForSymbol(symbol);
   }
 
   public synchronized Class resolveType(String type)
   {
     Class result = (Class)this._typeCache.get(type);
 
     if (result == null)
     {
       result = findTypeInClassResolver(type);
 
       this._typeCache.put(type, result);
     }
 
     return result;
   }
 
   private Class findTypeInClassResolver(String type)
   {
     Class result = this._resolver.checkForClass(type);
 
     if (result == null) {
       result = this._resolver.checkForClass(this._packageName + "." + type);
     }
     if (result == null) {
       throw new ApplicationRuntimeException(ImplMessages.unableToConvertType(type, this._packageName));
     }
 
     return result;
   }
 }