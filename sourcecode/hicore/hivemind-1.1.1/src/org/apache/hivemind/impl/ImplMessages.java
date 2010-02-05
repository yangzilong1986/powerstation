 package org.apache.hivemind.impl;
 
 import java.net.URL;
 import java.util.Collection;
 import java.util.Iterator;
 import org.apache.hivemind.ClassResolver;
 import org.apache.hivemind.Element;
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.Locatable;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.Occurances;
 import org.apache.hivemind.Resource;
 import org.apache.hivemind.events.RegistryShutdownListener;
 import org.apache.hivemind.internal.ConfigurationPoint;
 import org.apache.hivemind.internal.ExtensionPoint;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.internal.ServiceImplementationConstructor;
 import org.apache.hivemind.internal.ServiceInterceptorContribution;
 import org.apache.hivemind.internal.ServicePoint;
 import org.apache.hivemind.parse.ContributionDescriptor;
 import org.apache.hivemind.parse.DependencyDescriptor;
 import org.apache.hivemind.schema.SchemaProcessor;
 
 class ImplMessages
 {
   private static final MessageFormatter _formatter = new MessageFormatter(ImplMessages.class, "ImplStrings");
 
   static String recursiveServiceBuild(ServicePoint point)
   {
     return _formatter.format("recursive-service-build", point.getExtensionPointId());
   }
 
   static String recursiveConfiguration(String pointId)
   {
     return _formatter.format("recursive-configuration", pointId);
   }
 
   static String unableToConstructConfiguration(String pointId, Throwable exception)
   {
     return _formatter.format("unable-to-construct-configuration", pointId, exception.getMessage());
   }
 
   static String unknownServiceModel(String name)
   {
     return _formatter.format("unknown-service-model", name);
   }
 
   static String unknownTranslatorName(String name, String configurationId)
   {
     return _formatter.format("unknown-translator-name", name, configurationId);
   }
 
   static String duplicateTranslatorName(String name, Location oldLocation)
   {
     return _formatter.format("duplicate-translator-name", name, HiveMind.getLocationString(oldLocation));
   }
 
   static String translatorInstantiationFailure(Class translatorClass, Throwable cause)
   {
     return _formatter.format("translator-instantiation-failure", translatorClass.getName(), cause);
   }
 
   static String unqualifiedServicePoint(String serviceId, String matchingIds)
   {
     return _formatter.format("unqualified-service-point", serviceId, matchingIds);
   }
 
   static String noSuchServicePoint(String serviceId)
   {
     return _formatter.format("no-such-service-point", serviceId);
   }
 
   static String unableToLoadClass(String name, ClassLoader loader, Throwable cause)
   {
     return _formatter.format("unable-to-load-class", name, loader, cause);
   }
 
   static String nullInterceptor(ServiceInterceptorContribution contribution, ServicePoint point)
   {
     return _formatter.format("null-interceptor", contribution.getFactoryServiceId(), point.getExtensionPointId());
   }
 
   static String interceptorDoesNotImplementInterface(Object interceptor, ServiceInterceptorContribution contribution, ServicePoint point, Class serviceInterface)
   {
     return _formatter.format("interceptor-does-not-implement-interface", new Object[] { interceptor, contribution.getFactoryServiceId(), point.getExtensionPointId(), serviceInterface.getName() });
   }
 
   static String unableToReadMessages(URL url)
   {
     return _formatter.format("unable-to-read-messages", url);
   }
 
   static String unableToParse(Resource resource, Throwable cause)
   {
     return _formatter.format("unable-to-parse", resource, cause);
   }
 
   static String unableToFindModules(ClassResolver resolver, Throwable cause)
   {
     return _formatter.format("unable-to-find-modules", resolver, cause);
   }
 
   static String duplicateModuleId(String moduleId, Location locationOfExisting, Location locationOfDuplicate)
   {
     return _formatter.format("duplicate-module-id", moduleId, locationOfExisting.getResource(), locationOfDuplicate.getResource());
   }
 
   static String duplicateExtensionPointId(String pointId, ExtensionPoint existingPoint)
   {
     return _formatter.format("duplicate-extension-point", pointId, existingPoint.getLocation());
   }
 
   static String unknownConfigurationPoint(String moduleId, ContributionDescriptor descriptor)
   {
     return _formatter.format("unknown-configuration-extension-point", moduleId, descriptor.getConfigurationId());
   }
 
   static String unknownServicePoint(Module sourceModule, String pointId)
   {
     return _formatter.format("unknown-service-extension-point", sourceModule.getModuleId(), pointId);
   }
 
   static String missingService(ServicePoint point)
   {
     return _formatter.format("missing-service", point.getExtensionPointId());
   }
 
   static String duplicateFactory(Module sourceModule, String pointId, ServicePointImpl existing)
   {
     return _formatter.format("duplicate-factory", sourceModule.getModuleId(), pointId, existing.getServiceConstructor().getContributingModule().getModuleId());
   }
 
   static String wrongNumberOfContributions(ConfigurationPoint point, int actualCount, Occurances expectation)
   {
     return _formatter.format("wrong-number-of-contributions", point.getExtensionPointId(), contributionCount(actualCount), occurances(expectation));
   }
 
   static String occurances(Occurances occurances)
   {
     return _formatter.getMessage("occurances." + occurances.getName());
   }
 
   static String contributionCount(int count)
   {
     return _formatter.format("contribution-count", new Integer(count));
   }
 
   static String wrongNumberOfParameters(String factoryServiceId, int actualCount, Occurances expectation)
   {
     return _formatter.format("wrong-number-of-parameters", factoryServiceId, contributionCount(actualCount), occurances(expectation));
   }
 
   static String noSuchConfiguration(String pointId)
   {
     return _formatter.format("no-such-configuration", pointId);
   }
 
   static String noSuchSymbol(String name)
   {
     return _formatter.format("no-such-symbol", name);
   }
 
   static String symbolSourceContribution()
   {
     return _formatter.getMessage("symbol-source-contribution");
   }
 
   static String unknownAttribute(String name)
   {
     return _formatter.format("unknown-attribute", name);
   }
 
   static String missingAttribute(String name)
   {
     return _formatter.format("missing-attribute", name);
   }
 
   static String uniqueAttributeConstraintBroken(String name, String value, Location priorLocation)
   {
     return _formatter.format("unique-attribute-constraint-broken", name, value, priorLocation);
   }
 
   static String elementErrors(SchemaProcessor processor, Element element)
   {
     return _formatter.format("element-errors", processor.getElementPath(), element.getLocation());
   }
 
   static String unknownElement(SchemaProcessor processor, Element element)
   {
     return _formatter.format("unknown-element", processor.getElementPath());
   }
 
   static String badInterface(String interfaceName, String pointId)
   {
     return _formatter.format("bad-interface", interfaceName, pointId);
   }
 
   static String serviceWrongInterface(ServicePoint servicePoint, Class requestedInterface)
   {
     return _formatter.format("service-wrong-interface", servicePoint.getExtensionPointId(), requestedInterface.getName(), servicePoint.getServiceInterface().getName());
   }
 
   static String shutdownCoordinatorFailure(RegistryShutdownListener listener, Throwable cause)
   {
     return _formatter.format("shutdown-coordinator-failure", listener, cause);
   }
 
   static String unlocatedError(String message)
   {
     return _formatter.format("unlocated-error", message);
   }
 
   static String locatedError(Location location, String message)
   {
     return _formatter.format("located-error", location, message);
   }
 
   static String interceptorContribution()
   {
     return _formatter.getMessage("interceptor-contribution");
   }
 
   static String registryAlreadyStarted()
   {
     return _formatter.getMessage("registry-already-started");
   }
 
   static String noServicePointForInterface(Class interfaceClass)
   {
     return _formatter.format("no-service-point-for-interface", interfaceClass.getName());
   }
 
   static String multipleServicePointsForInterface(Class interfaceClass, Collection matchingPoints)
   {
     StringBuffer buffer = new StringBuffer("{");
 
     boolean following = false;
 
     Iterator i = matchingPoints.iterator();
     while (i.hasNext())
     {
       if (following) {
         buffer.append(", ");
       }
       ServicePoint p = (ServicePoint)i.next();
 
       buffer.append(p.getExtensionPointId());
 
       following = true;
     }
 
     buffer.append("}");
 
     return _formatter.format("multiple-service-points-for-interface", interfaceClass.getName(), buffer);
   }
 
   static String incompleteTranslator(TranslatorContribution c)
   {
     return _formatter.format("incomplete-translator", c.getName());
   }
 
   static String schemaStackViolation(SchemaProcessor processor)
   {
     return _formatter.format("schema-stack-violation", processor.getElementPath());
   }
 
   static String subModuleDoesNotExist(Resource subModuleDescriptor)
   {
     return _formatter.format("sub-module-does-not-exist", subModuleDescriptor);
   }
 
   static String dependencyOnUnknownModule(DependencyDescriptor dependency)
   {
     return _formatter.format("dependency-on-unknown-module", dependency.getModuleId());
   }
 
   static String dependencyVersionMismatch(DependencyDescriptor dependency)
   {
     return _formatter.format("dependency-version-mismatch", dependency.getModuleId(), dependency.getVersion());
   }
 
   private static String convertModule(Module module)
   {
     if (module == null) {
       return _formatter.getMessage("null-module");
     }
     return _formatter.format("module", module.getModuleId());
   }
 
   static String unableToResolveSchema(String schemaId)
   {
     return _formatter.format("unable-to-resolve-schema", schemaId);
   }
 
   static String schemaNotVisible(String schemaId, String moduleId)
   {
     return _formatter.format("schema-not-visible", schemaId, moduleId);
   }
 
   static String serviceNotVisible(String serviceId, Module module)
   {
     return _formatter.format("service-not-visible", serviceId, convertModule(module));
   }
 
   static String configurationNotVisible(String configurationId, Module module)
   {
     return _formatter.format("configuration-not-visible", configurationId, convertModule(module));
   }
 
   static String configurationPointNotVisible(ConfigurationPoint point, Module contributingModule)
   {
     return _formatter.format("configuration-point-not-visible", point.getExtensionPointId(), contributingModule.getModuleId());
   }
 
   static String servicePointNotVisible(ServicePoint point, Module contributingModule)
   {
     return _formatter.format("service-point-not-visible", point.getExtensionPointId(), contributingModule.getModuleId());
   }
 
   static String unableToMapConfiguration(ConfigurationPoint point)
   {
     return _formatter.format("unable-to-map-configuration", point.getExtensionPointId());
   }
 
   static String unableToConvertType(String type, String packageName)
   {
     return _formatter.format("unable-to-convert-type", type, packageName);
   }
 }