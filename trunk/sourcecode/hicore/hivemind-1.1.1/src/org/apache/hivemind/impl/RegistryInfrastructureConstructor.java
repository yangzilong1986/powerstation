 package org.apache.hivemind.impl;
 
 import java.util.Collection;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Locale;
 import java.util.Map;
 import org.apache.commons.logging.Log;
 import org.apache.hivemind.ErrorHandler;
 import org.apache.hivemind.Locatable;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.Occurances;
 import org.apache.hivemind.ShutdownCoordinator;
 import org.apache.hivemind.conditional.EvaluationContextImpl;
 import org.apache.hivemind.conditional.Node;
 import org.apache.hivemind.conditional.Parser;
 import org.apache.hivemind.internal.ConfigurationPoint;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.internal.RegistryInfrastructure;
 import org.apache.hivemind.internal.ServicePoint;
 import org.apache.hivemind.parse.AbstractServiceDescriptor;
 import org.apache.hivemind.parse.AbstractServiceInvocationDescriptor;
 import org.apache.hivemind.parse.ConfigurationPointDescriptor;
 import org.apache.hivemind.parse.ContributionDescriptor;
 import org.apache.hivemind.parse.DependencyDescriptor;
 import org.apache.hivemind.parse.ImplementationDescriptor;
 import org.apache.hivemind.parse.InstanceBuilder;
 import org.apache.hivemind.parse.InterceptorDescriptor;
 import org.apache.hivemind.parse.ModuleDescriptor;
 import org.apache.hivemind.parse.ServicePointDescriptor;
 import org.apache.hivemind.schema.Schema;
 import org.apache.hivemind.schema.impl.SchemaImpl;
 import org.apache.hivemind.util.IdUtils;
 
 public class RegistryInfrastructureConstructor
 {
   private ErrorHandler _errorHandler;
   private Log _log;
   private RegistryAssembly _assembly;
   private Parser _conditionalExpressionParser;
   private Map _moduleDescriptors = new HashMap();
 
   private Map _modules = new HashMap();
 
   private Map _schemas = new HashMap();
 
   private Map _servicePoints = new HashMap();
 
   private Map _configurationPoints = new HashMap();
 
   private ShutdownCoordinator _shutdownCoordinator = new ShutdownCoordinatorImpl();
 
   public RegistryInfrastructureConstructor(ErrorHandler errorHandler, Log log, RegistryAssembly assembly)
   {
     this._errorHandler = errorHandler;
     this._log = log;
     this._assembly = assembly;
   }
 
   public RegistryInfrastructure constructRegistryInfrastructure(Locale locale)
   {
     RegistryInfrastructureImpl result = new RegistryInfrastructureImpl(this._errorHandler, locale);
 
     addServiceAndConfigurationPoints(result);
 
     addImplementationsAndContributions();
 
     checkForMissingServices();
 
     checkContributionCounts();
 
     result.setShutdownCoordinator(this._shutdownCoordinator);
 
     addModulesToRegistry(result);
 
     return result;
   }
 
   public void addModuleDescriptor(ModuleDescriptor md)
   {
     String id = md.getModuleId();
 
     if (this._log.isDebugEnabled()) {
       this._log.debug("Processing module " + id);
     }
     if (this._modules.containsKey(id))
     {
       Module existing = (Module)this._modules.get(id);
 
       this._errorHandler.error(this._log, ImplMessages.duplicateModuleId(id, existing.getLocation(), md.getLocation()), null, null);
 
       return;
     }
 
     ModuleImpl module = new ModuleImpl();
 
     module.setLocation(md.getLocation());
     module.setModuleId(id);
     module.setPackageName(md.getPackageName());
     module.setClassResolver(md.getClassResolver());
 
     if (size(md.getDependencies()) > 0) {
       this._assembly.addPostProcessor(new ModuleDependencyChecker(md));
     }
     for (Iterator schemas = md.getSchemas().iterator(); schemas.hasNext(); )
     {
       SchemaImpl schema = (SchemaImpl)schemas.next();
 
       schema.setModule(module);
 
       this._schemas.put(IdUtils.qualify(id, schema.getId()), schema);
     }
 
     this._modules.put(id, module);
 
     this._moduleDescriptors.put(id, md);
   }
 
   private void addServiceAndConfigurationPoints(RegistryInfrastructureImpl infrastructure)
   {
     for (Iterator i = this._moduleDescriptors.values().iterator(); i.hasNext(); )
     {
       ModuleDescriptor md = (ModuleDescriptor)i.next();
 
       String id = md.getModuleId();
 
       ModuleImpl module = (ModuleImpl)this._modules.get(id);
 
       addServicePoints(infrastructure, module, md);
 
       addConfigurationPoints(infrastructure, module, md);
     }
   }
 
   private void addServicePoints(RegistryInfrastructureImpl infrastructure, Module module, ModuleDescriptor md)
   {
     String moduleId = md.getModuleId();
     List services = md.getServicePoints();
     int count = size(services);
 
     for (int i = 0; i < count; ++i)
     {
       ServicePointDescriptor sd = (ServicePointDescriptor)services.get(i);
 
       String pointId = moduleId + "." + sd.getId();
 
       ServicePoint existingPoint = (ServicePoint)this._servicePoints.get(pointId);
 
       if (existingPoint != null)
       {
         this._errorHandler.error(this._log, ImplMessages.duplicateExtensionPointId(pointId, existingPoint), sd.getLocation(), null);
       }
       else
       {
         if (this._log.isDebugEnabled()) {
           this._log.debug("Creating service point " + pointId);
         }
 
         ServicePointImpl point = new ServicePointImpl();
 
         point.setExtensionPointId(pointId);
         point.setLocation(sd.getLocation());
         point.setModule(module);
 
         point.setServiceInterfaceName(sd.getInterfaceClassName());
 
         point.setParametersSchema(findSchema(sd.getParametersSchema(), module, sd.getParametersSchemaId(), point.getLocation()));
 
         point.setParametersCount(sd.getParametersCount());
         point.setVisibility(sd.getVisibility());
 
         point.setShutdownCoordinator(this._shutdownCoordinator);
 
         infrastructure.addServicePoint(point);
 
         this._servicePoints.put(pointId, point);
 
         addInternalImplementations(module, pointId, sd);
       }
     }
   }
 
   private void addConfigurationPoints(RegistryInfrastructureImpl registry, Module module, ModuleDescriptor md)
   {
     String moduleId = md.getModuleId();
     List points = md.getConfigurationPoints();
     int count = size(points);
 
     for (int i = 0; i < count; ++i)
     {
       ConfigurationPointDescriptor cpd = (ConfigurationPointDescriptor)points.get(i);
 
       String pointId = moduleId + "." + cpd.getId();
 
       ConfigurationPoint existingPoint = (ConfigurationPoint)this._configurationPoints.get(pointId);
 
       if (existingPoint != null)
       {
         this._errorHandler.error(this._log, ImplMessages.duplicateExtensionPointId(pointId, existingPoint), cpd.getLocation(), null);
       }
       else
       {
         if (this._log.isDebugEnabled()) {
           this._log.debug("Creating configuration point " + pointId);
         }
         ConfigurationPointImpl point = new ConfigurationPointImpl();
 
         point.setExtensionPointId(pointId);
         point.setLocation(cpd.getLocation());
         point.setModule(module);
         point.setExpectedCount(cpd.getCount());
 
         point.setContributionsSchema(findSchema(cpd.getContributionsSchema(), module, cpd.getContributionsSchemaId(), cpd.getLocation()));
 
         point.setVisibility(cpd.getVisibility());
 
         point.setShutdownCoordinator(this._shutdownCoordinator);
 
         registry.addConfigurationPoint(point);
 
         this._configurationPoints.put(pointId, point);
       }
     }
   }
 
   private void addContributionElements(Module sourceModule, ConfigurationPointImpl point, List elements)
   {
     if (size(elements) == 0) {
       return;
     }
     if (this._log.isDebugEnabled()) {
       this._log.debug("Adding contributions to configuration point " + point.getExtensionPointId());
     }
 
     ContributionImpl c = new ContributionImpl();
     c.setContributingModule(sourceModule);
     c.addElements(elements);
 
     point.addContribution(c);
   }
 
   private void addModulesToRegistry(RegistryInfrastructureImpl registry)
   {
     Iterator i = this._modules.values().iterator();
     while (i.hasNext())
     {
       ModuleImpl module = (ModuleImpl)i.next();
 
       if (this._log.isDebugEnabled()) {
         this._log.debug("Adding module " + module.getModuleId() + " to registry");
       }
       module.setRegistry(registry);
     }
   }
 
   private void addImplementationsAndContributions()
   {
     for (Iterator i = this._moduleDescriptors.values().iterator(); i.hasNext(); )
     {
       ModuleDescriptor md = (ModuleDescriptor)i.next();
 
       if (this._log.isDebugEnabled()) {
         this._log.debug("Adding contributions from module " + md.getModuleId());
       }
       addImplementations(md);
       addContributions(md);
     }
   }
 
   private void addImplementations(ModuleDescriptor md)
   {
     String moduleId = md.getModuleId();
     Module sourceModule = (Module)this._modules.get(moduleId);
 
     List implementations = md.getImplementations();
     int count = size(implementations);
 
     for (int i = 0; i < count; ++i)
     {
       ImplementationDescriptor impl = (ImplementationDescriptor)implementations.get(i);
 
       if (!(includeContribution(impl.getConditionalExpression(), sourceModule, impl.getLocation()))) {
         continue;
       }
 
       String pointId = impl.getServiceId();
       String qualifiedId = IdUtils.qualify(moduleId, pointId);
 
       addImplementations(sourceModule, qualifiedId, impl);
     }
   }
 
   private void addContributions(ModuleDescriptor md)
   {
     String moduleId = md.getModuleId();
     Module sourceModule = (Module)this._modules.get(moduleId);
 
     List contributions = md.getContributions();
     int count = size(contributions);
 
     for (int i = 0; i < count; ++i)
     {
       ContributionDescriptor cd = (ContributionDescriptor)contributions.get(i);
 
       if (!(includeContribution(cd.getConditionalExpression(), sourceModule, cd.getLocation()))) {
         continue;
       }
       String pointId = cd.getConfigurationId();
       String qualifiedId = IdUtils.qualify(moduleId, pointId);
 
       ConfigurationPointImpl point = (ConfigurationPointImpl)this._configurationPoints.get(qualifiedId);
 
       if (point == null)
       {
         this._errorHandler.error(this._log, ImplMessages.unknownConfigurationPoint(moduleId, cd), cd.getLocation(), null);
       }
       else if (!(point.visibleToModule(sourceModule)))
       {
         this._errorHandler.error(this._log, ImplMessages.configurationPointNotVisible(point, sourceModule), cd.getLocation(), null);
       }
       else
       {
         addContributionElements(sourceModule, point, cd.getElements());
       }
     }
   }
 
   private Schema findSchema(SchemaImpl schema, Module module, String schemaId, Location location) {
     if (schema != null)
     {
       schema.setModule(module);
       return schema;
     }
 
     if (schemaId == null) {
       return null;
     }
     String moduleId = module.getModuleId();
     String qualifiedId = IdUtils.qualify(moduleId, schemaId);
 
     return getSchema(qualifiedId, moduleId, location);
   }
 
   private Schema getSchema(String schemaId, String referencingModule, Location reference)
   {
     Schema schema = (Schema)this._schemas.get(schemaId);
 
     if (schema == null) {
       this._errorHandler.error(this._log, ImplMessages.unableToResolveSchema(schemaId), reference, null);
     }
     else if (!(schema.visibleToModule(referencingModule)))
     {
       this._errorHandler.error(this._log, ImplMessages.schemaNotVisible(schemaId, referencingModule), reference, null);
 
       schema = null;
     }
 
     return schema;
   }
 
   private void addInternalImplementations(Module sourceModule, String pointId, ServicePointDescriptor spd)
   {
     InstanceBuilder builder = spd.getInstanceBuilder();
     List interceptors = spd.getInterceptors();
 
     if ((builder == null) && (interceptors == null)) {
       return;
     }
     if (builder != null) {
       addServiceInstanceBuilder(sourceModule, pointId, builder, true);
     }
     if (interceptors == null) {
       return;
     }
     int count = size(interceptors);
 
     for (int i = 0; i < count; ++i)
     {
       InterceptorDescriptor id = (InterceptorDescriptor)interceptors.get(i);
       addInterceptor(sourceModule, pointId, id);
     }
   }
 
   private void addImplementations(Module sourceModule, String pointId, ImplementationDescriptor id)
   {
     InstanceBuilder builder = id.getInstanceBuilder();
     List interceptors = id.getInterceptors();
 
     if (builder != null) {
       addServiceInstanceBuilder(sourceModule, pointId, builder, false);
     }
     int count = size(interceptors);
     for (int i = 0; i < count; ++i)
     {
       InterceptorDescriptor ind = (InterceptorDescriptor)interceptors.get(i);
 
       addInterceptor(sourceModule, pointId, ind);
     }
   }
 
   private void addServiceInstanceBuilder(Module sourceModule, String pointId, InstanceBuilder builder, boolean isDefault)
   {
     if (this._log.isDebugEnabled()) {
       this._log.debug("Adding " + builder + " to service extension point " + pointId);
     }
     ServicePointImpl point = (ServicePointImpl)this._servicePoints.get(pointId);
 
     if (point == null)
     {
       this._errorHandler.error(this._log, ImplMessages.unknownServicePoint(sourceModule, pointId), builder.getLocation(), null);
 
       return;
     }
 
     if (!(point.visibleToModule(sourceModule)))
     {
       this._errorHandler.error(this._log, ImplMessages.servicePointNotVisible(point, sourceModule), builder.getLocation(), null);
 
       return;
     }
 
     if (point.getServiceConstructor(isDefault) != null)
     {
       this._errorHandler.error(this._log, ImplMessages.duplicateFactory(sourceModule, pointId, point), builder.getLocation(), null);
 
       return;
     }
 
     point.setServiceModel(builder.getServiceModel());
     point.setServiceConstructor(builder.createConstructor(point, sourceModule), isDefault);
   }
 
   private void addInterceptor(Module sourceModule, String pointId, InterceptorDescriptor id)
   {
     if (this._log.isDebugEnabled()) {
       this._log.debug("Adding " + id + " to service extension point " + pointId);
     }
     ServicePointImpl point = (ServicePointImpl)this._servicePoints.get(pointId);
 
     String sourceModuleId = sourceModule.getModuleId();
 
     if (point == null)
     {
       this._errorHandler.error(this._log, ImplMessages.unknownServicePoint(sourceModule, pointId), id.getLocation(), null);
 
       return;
     }
 
     if (!(point.visibleToModule(sourceModule)))
     {
       this._errorHandler.error(this._log, ImplMessages.servicePointNotVisible(point, sourceModule), id.getLocation(), null);
 
       return;
     }
 
     ServiceInterceptorContributionImpl sic = new ServiceInterceptorContributionImpl();
 
     sic.setFactoryServiceId(IdUtils.qualify(sourceModuleId, id.getFactoryServiceId()));
     sic.setLocation(id.getLocation());
 
     sic.setFollowingInterceptorIds(IdUtils.qualifyList(sourceModuleId, id.getBefore()));
     sic.setPrecedingInterceptorIds(IdUtils.qualifyList(sourceModuleId, id.getAfter()));
     sic.setName((id.getName() != null) ? IdUtils.qualify(sourceModuleId, id.getName()) : null);
     sic.setContributingModule(sourceModule);
     sic.setParameters(id.getParameters());
 
     point.addInterceptorContribution(sic);
   }
 
   private void checkForMissingServices()
   {
     Iterator i = this._servicePoints.values().iterator();
     while (i.hasNext())
     {
       ServicePointImpl point = (ServicePointImpl)i.next();
 
       if (point.getServiceConstructor() != null) {
         continue;
       }
       this._errorHandler.error(this._log, ImplMessages.missingService(point), null, null);
     }
   }
 
   private void checkContributionCounts()
   {
     Iterator i = this._configurationPoints.values().iterator();
 
     while (i.hasNext())
     {
       ConfigurationPointImpl point = (ConfigurationPointImpl)i.next();
 
       Occurances expected = point.getExpectedCount();
 
       int actual = point.getContributionCount();
 
       if (expected.inRange(actual)) {
         continue;
       }
       this._errorHandler.error(this._log, ImplMessages.wrongNumberOfContributions(point, actual, expected), point.getLocation(), null);
     }
   }
 
   private boolean includeContribution(String expression, Module module, Location location)
   {
     if (expression == null) {
       return true;
     }
     if (this._conditionalExpressionParser == null) {
       this._conditionalExpressionParser = new Parser();
     }
     try
     {
       Node node = this._conditionalExpressionParser.parse(expression);
 
       return node.evaluate(new EvaluationContextImpl(module.getClassResolver()));
     }
     catch (RuntimeException ex)
     {
       this._errorHandler.error(this._log, ex.getMessage(), location, ex);
     }
     return false;
   }
 
   private static int size(Collection c)
   {
     return ((c == null) ? 0 : c.size());
   }
 
   private class ModuleDependencyChecker
     implements Runnable
   {
     private ModuleDescriptor _source;
 
     public ModuleDependencyChecker(ModuleDescriptor paramModuleDescriptor)
     {
       this._source = paramModuleDescriptor;
     }
 
     public void run()
     {
       List dependencies = this._source.getDependencies();
       int count = RegistryInfrastructureConstructor.access$000(dependencies);
 
       for (int i = 0; i < count; ++i)
       {
         DependencyDescriptor dependency = (DependencyDescriptor)dependencies.get(i);
         checkDependency(dependency);
       }
     }
 
     private void checkDependency(DependencyDescriptor dependency)
     {
       ModuleDescriptor requiredModule = (ModuleDescriptor)RegistryInfrastructureConstructor.this._moduleDescriptors.get(dependency.getModuleId());
 
       if (requiredModule == null)
       {
         RegistryInfrastructureConstructor.this._errorHandler.error(RegistryInfrastructureConstructor.this._log, ImplMessages.dependencyOnUnknownModule(dependency), dependency.getLocation(), null);
 
         return;
       }
 
       if ((dependency.getVersion() == null) || (dependency.getVersion().equals(requiredModule.getVersion()))) {
         return;
       }
       RegistryInfrastructureConstructor.this._errorHandler.error(RegistryInfrastructureConstructor.this._log, ImplMessages.dependencyVersionMismatch(dependency), dependency.getLocation(), null);
 
       return;
     }
   }
 }