 package org.apache.hivemind.ant;
 
 import java.util.ArrayList;
 import java.util.Collection;
 import java.util.HashSet;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Set;
 import javax.xml.parsers.DocumentBuilder;
 import javax.xml.parsers.DocumentBuilderFactory;
 import javax.xml.parsers.ParserConfigurationException;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.Attribute;
 import org.apache.hivemind.ClassResolver;
 import org.apache.hivemind.ErrorHandler;
 import org.apache.hivemind.ModuleDescriptorProvider;
 import org.apache.hivemind.Occurances;
 import org.apache.hivemind.Resource;
 import org.apache.hivemind.impl.DefaultClassResolver;
 import org.apache.hivemind.impl.DefaultErrorHandler;
 import org.apache.hivemind.impl.XmlModuleDescriptorProvider;
 import org.apache.hivemind.internal.Visibility;
 import org.apache.hivemind.parse.AbstractServiceDescriptor;
 import org.apache.hivemind.parse.AbstractServiceInvocationDescriptor;
 import org.apache.hivemind.parse.AnnotationHolder;
 import org.apache.hivemind.parse.AttributeMappingDescriptor;
 import org.apache.hivemind.parse.BaseAnnotationHolder;
 import org.apache.hivemind.parse.ConfigurationPointDescriptor;
 import org.apache.hivemind.parse.ContributionDescriptor;
 import org.apache.hivemind.parse.ConversionDescriptor;
 import org.apache.hivemind.parse.CreateInstanceDescriptor;
 import org.apache.hivemind.parse.DependencyDescriptor;
 import org.apache.hivemind.parse.ImplementationDescriptor;
 import org.apache.hivemind.parse.InstanceBuilder;
 import org.apache.hivemind.parse.InterceptorDescriptor;
 import org.apache.hivemind.parse.InvokeFactoryDescriptor;
 import org.apache.hivemind.parse.ModuleDescriptor;
 import org.apache.hivemind.parse.ServicePointDescriptor;
 import org.apache.hivemind.parse.SubModuleDescriptor;
 import org.apache.hivemind.schema.AttributeModel;
 import org.apache.hivemind.schema.ElementModel;
 import org.apache.hivemind.schema.Rule;
 import org.apache.hivemind.schema.impl.SchemaImpl;
 import org.apache.hivemind.schema.rules.CreateObjectRule;
 import org.apache.hivemind.schema.rules.InvokeParentRule;
 import org.apache.hivemind.schema.rules.PushAttributeRule;
 import org.apache.hivemind.schema.rules.PushContentRule;
 import org.apache.hivemind.schema.rules.ReadAttributeRule;
 import org.apache.hivemind.schema.rules.ReadContentRule;
 import org.apache.hivemind.schema.rules.SetModuleRule;
 import org.apache.hivemind.schema.rules.SetParentRule;
 import org.apache.hivemind.schema.rules.SetPropertyRule;
 import org.apache.hivemind.util.IdUtils;
 import org.w3c.dom.Document;
 import org.w3c.dom.Node;
 
 public class RegistrySerializer
 {
   private Set _processedSchemas = new HashSet();
 
   private List _providers = new ArrayList();
   private ErrorHandler _handler;
   private Document _document;
   private ModuleDescriptor _md;
 
   public RegistrySerializer()
   {
     this._handler = new DefaultErrorHandler();
   }
 
   public void addModuleDescriptorProvider(ModuleDescriptorProvider provider)
   {
     this._providers.add(provider);
   }
 
   public Document createRegistryDocument()
   {
     DocumentBuilder builder = getBuilder();
 
     this._document = builder.newDocument();
 
     org.w3c.dom.Element registry = this._document.createElement("registry");
 
     this._document.appendChild(registry);
 
     for (Iterator i = this._providers.iterator(); i.hasNext(); )
     {
       ModuleDescriptorProvider provider = (ModuleDescriptorProvider)i.next();
 
       processModuleDescriptorProvider(registry, provider);
     }
 
     return this._document;
   }
 
   private void processModuleDescriptorProvider(org.w3c.dom.Element registry, ModuleDescriptorProvider provider)
   {
     for (Iterator j = provider.getModuleDescriptors(this._handler).iterator(); j.hasNext(); )
     {
       this._md = ((ModuleDescriptor)j.next());
 
       org.w3c.dom.Element module = getModuleElement(this._md);
 
       registry.appendChild(module);
     }
   }
 
   private org.w3c.dom.Element getModuleElement(ModuleDescriptor md)
   {
     org.w3c.dom.Element module = this._document.createElement("module");
 
     module.setAttribute("id", md.getModuleId());
     module.setAttribute("version", md.getVersion());
     module.setAttribute("package", md.getPackageName());
 
     module.appendChild(this._document.createTextNode(md.getAnnotation()));
 
     addDependencies(module);
 
     addServicePoints(module);
 
     addConfigurationPoints(module);
 
     addContributions(module);
 
     addImplementations(module);
 
     addSchemas(module);
 
     addSubModules(module);
 
     return module;
   }
 
   private void addDependencies(org.w3c.dom.Element module)
   {
     List dependencies = this._md.getDependencies();
 
     if (dependencies == null)
       return;
     for (Iterator i = dependencies.iterator(); i.hasNext(); )
     {
       DependencyDescriptor dd = (DependencyDescriptor)i.next();
 
       org.w3c.dom.Element dependency = getDependencyElement(dd);
 
       module.appendChild(dependency);
     }
   }
 
   private void addServicePoints(org.w3c.dom.Element module)
   {
     List servicePoints = this._md.getServicePoints();
 
     if (servicePoints == null)
       return;
     for (Iterator i = servicePoints.iterator(); i.hasNext(); )
     {
       ServicePointDescriptor spd = (ServicePointDescriptor)i.next();
 
       org.w3c.dom.Element servicePoint = getServicePointElement(spd);
 
       module.appendChild(servicePoint);
 
       SchemaImpl s = spd.getParametersSchema();
 
       if ((s != null) && (s.getId() != null))
         addSchema(module, s, "schema");
     }
   }
 
   private void addConfigurationPoints(org.w3c.dom.Element module)
   {
     List configurationPoints = this._md.getConfigurationPoints();
 
     if (configurationPoints == null)
       return;
     for (Iterator i = configurationPoints.iterator(); i.hasNext(); )
     {
       ConfigurationPointDescriptor cpd = (ConfigurationPointDescriptor)i.next();
 
       org.w3c.dom.Element configurationPoint = getConfigurationPointElement(cpd);
 
       module.appendChild(configurationPoint);
 
       SchemaImpl s = cpd.getContributionsSchema();
 
       if ((s != null) && (s.getId() != null))
         addSchema(module, s, "schema");
     }
   }
 
   private void addContributions(org.w3c.dom.Element module)
   {
     List contributions = this._md.getContributions();
 
     if (contributions == null)
       return;
     for (Iterator i = contributions.iterator(); i.hasNext(); )
     {
       ContributionDescriptor cd = (ContributionDescriptor)i.next();
 
       org.w3c.dom.Element contribution = getContributionElement(cd);
 
       module.appendChild(contribution);
     }
   }
 
   private void addImplementations(org.w3c.dom.Element module)
   {
     List implementations = this._md.getImplementations();
 
     if (implementations == null)
       return;
     for (Iterator i = implementations.iterator(); i.hasNext(); )
     {
       ImplementationDescriptor id = (ImplementationDescriptor)i.next();
 
       org.w3c.dom.Element implementation = getImplementationElement(id);
 
       module.appendChild(implementation);
     }
   }
 
   private void addSchemas(org.w3c.dom.Element module)
   {
     Collection schemas = this._md.getSchemas();
 
     for (Iterator i = schemas.iterator(); i.hasNext(); )
     {
       SchemaImpl s = (SchemaImpl)i.next();
 
       addSchema(module, s, "schema");
     }
   }
 
   private void addSubModules(org.w3c.dom.Element module)
   {
     List subModules = this._md.getSubModules();
 
     if (subModules == null)
       return;
     for (Iterator i = subModules.iterator(); i.hasNext(); )
     {
       SubModuleDescriptor smd = (SubModuleDescriptor)i.next();
 
       org.w3c.dom.Element subModule = getSubModuleElement(smd);
 
       module.appendChild(subModule);
     }
   }
 
   private org.w3c.dom.Element getDependencyElement(DependencyDescriptor dd)
   {
     org.w3c.dom.Element dependency = this._document.createElement("dependency");
 
     dependency.setAttribute("module-id", dd.getModuleId());
     dependency.setAttribute("version", dd.getVersion());
 
     return dependency;
   }
 
   private org.w3c.dom.Element getServicePointElement(ServicePointDescriptor spd)
   {
     Iterator i;
     org.w3c.dom.Element servicePoint = this._document.createElement("service-point");
 
     servicePoint.setAttribute("id", qualify(spd.getId()));
     servicePoint.setAttribute("interface", spd.getInterfaceClassName());
     if (spd.getVisibility() == Visibility.PRIVATE)
       servicePoint.setAttribute("visibility", "private");
     if (spd.getParametersCount() != Occurances.REQUIRED) {
       servicePoint.setAttribute("parameters-occurs", spd.getParametersCount().getName().toLowerCase());
     }
 
     servicePoint.appendChild(this._document.createTextNode(spd.getAnnotation()));
 
     if (spd.getParametersSchema() != null)
       addSchema(servicePoint, spd.getParametersSchema(), "parameters-schema");
     else if (spd.getParametersSchemaId() != null) {
       servicePoint.setAttribute("parameters-schema-id", qualify(spd.getParametersSchemaId()));
     }
     InstanceBuilder ib = spd.getInstanceBuilder();
 
     if (ib != null)
     {
       org.w3c.dom.Element instanceBuilder = getInstanceBuilderElement(ib);
 
       servicePoint.appendChild(instanceBuilder);
     }
 
     List interceptors = spd.getInterceptors();
 
     if (interceptors != null)
     {
       for (i = interceptors.iterator(); i.hasNext(); )
       {
         InterceptorDescriptor icd = (InterceptorDescriptor)i.next();
 
         org.w3c.dom.Element interceptor = getInterceptorElement(icd);
 
         servicePoint.appendChild(interceptor);
       }
     }
 
     return servicePoint;
   }
 
   private org.w3c.dom.Element getConfigurationPointElement(ConfigurationPointDescriptor cpd)
   {
     org.w3c.dom.Element configurationPoint = this._document.createElement("configuration-point");
 
     configurationPoint.setAttribute("id", qualify(cpd.getId()));
     if (cpd.getVisibility() == Visibility.PRIVATE) {
       configurationPoint.setAttribute("visibility", "private");
     }
     configurationPoint.appendChild(this._document.createTextNode(cpd.getAnnotation()));
 
     if (cpd.getContributionsSchema() != null)
       addSchema(configurationPoint, cpd.getContributionsSchema(), "schema");
     else if (cpd.getContributionsSchemaId() != null) {
       configurationPoint.setAttribute("schema-id", qualify(cpd.getContributionsSchemaId()));
     }
     return configurationPoint;
   }
 
   private org.w3c.dom.Element getContributionElement(ContributionDescriptor cd)
   {
     Iterator i;
     org.w3c.dom.Element contribution = this._document.createElement("contribution");
 
     contribution.setAttribute("configuration-id", qualify(cd.getConfigurationId()));
 
     if (cd.getConditionalExpression() != null) {
       contribution.setAttribute("if", cd.getConditionalExpression());
     }
     List parameters = cd.getElements();
 
     if (parameters != null)
     {
       for (i = parameters.iterator(); i.hasNext(); )
       {
         org.apache.hivemind.Element parameter = (org.apache.hivemind.Element)i.next();
 
         org.w3c.dom.Element element = getParamterElement(parameter);
 
         contribution.appendChild(element);
       }
     }
 
     contribution.appendChild(this._document.createTextNode(cd.getAnnotation()));
 
     return contribution;
   }
 
   private org.w3c.dom.Element getImplementationElement(ImplementationDescriptor id)
   {
     Iterator i;
     org.w3c.dom.Element implementation = this._document.createElement("implementation");
 
     implementation.setAttribute("service-id", qualify(id.getServiceId()));
 
     if (id.getConditionalExpression() != null) {
       implementation.setAttribute("if", id.getConditionalExpression());
     }
     implementation.appendChild(this._document.createTextNode(id.getAnnotation()));
 
     InstanceBuilder ib = id.getInstanceBuilder();
 
     if (ib != null)
     {
       org.w3c.dom.Element instanceBuilder = getInstanceBuilderElement(ib);
 
       implementation.appendChild(instanceBuilder);
     }
 
     List interceptors = id.getInterceptors();
 
     if (interceptors != null)
     {
       for (i = interceptors.iterator(); i.hasNext(); )
       {
         InterceptorDescriptor icd = (InterceptorDescriptor)i.next();
 
         org.w3c.dom.Element interceptor = getInterceptorElement(icd);
 
         implementation.appendChild(interceptor);
       }
     }
 
     return implementation;
   }
 
   private org.w3c.dom.Element getSubModuleElement(SubModuleDescriptor smd)
   {
     org.w3c.dom.Element subModule = this._document.createElement("sub-module");
 
     subModule.setAttribute("descriptor", smd.getDescriptor().getPath());
 
     return subModule;
   }
 
   private org.w3c.dom.Element getInstanceBuilderElement(InstanceBuilder ib)
   {
     org.w3c.dom.Element instanceBuilder;
     Iterator i;
     if (ib instanceof CreateInstanceDescriptor)
     {
       CreateInstanceDescriptor cid = (CreateInstanceDescriptor)ib;
       instanceBuilder = this._document.createElement("create-instance");
 
       instanceBuilder.setAttribute("class", cid.getInstanceClassName());
       if (!(cid.getServiceModel().equals("singleton")))
         instanceBuilder.setAttribute("model", cid.getServiceModel());
     }
     else
     {
       InvokeFactoryDescriptor ifd = (InvokeFactoryDescriptor)ib;
       instanceBuilder = this._document.createElement("invoke-factory");
 
       if (!(ifd.getFactoryServiceId().equals("hivemind.BuilderFactory")))
         instanceBuilder.setAttribute("service-id", qualify(ifd.getFactoryServiceId()));
       if (ifd.getServiceModel() != null) {
         instanceBuilder.setAttribute("model", ifd.getServiceModel());
       }
       List parameters = ifd.getParameters();
 
       if (parameters != null)
       {
         for (i = parameters.iterator(); i.hasNext(); )
         {
           org.apache.hivemind.Element parameter = (org.apache.hivemind.Element)i.next();
 
           org.w3c.dom.Element element = getParamterElement(parameter);
 
           instanceBuilder.appendChild(element);
         }
       }
     }
 
     return instanceBuilder;
   }
 
   private org.w3c.dom.Element getInterceptorElement(InterceptorDescriptor icd)
   {
     org.w3c.dom.Element interceptor = this._document.createElement("interceptor");
 
     interceptor.setAttribute("service-id", qualify(icd.getFactoryServiceId()));
     if (icd.getBefore() != null)
       interceptor.setAttribute("before", icd.getBefore());
     if (icd.getAfter() != null)
       interceptor.setAttribute("after", icd.getAfter());
     return interceptor;
   }
 
   private org.w3c.dom.Element getParamterElement(org.apache.hivemind.Element parameter)
   {
     org.w3c.dom.Element element = this._document.createElement(parameter.getElementName());
 
     List attributes = parameter.getAttributes();
 
     for (Iterator i = attributes.iterator(); i.hasNext(); )
     {
       Attribute attribute = (Attribute)i.next();
 
       element.setAttribute(attribute.getName(), attribute.getValue());
     }
 
     List elements = parameter.getElements();
 
     for (Iterator i = elements.iterator(); i.hasNext(); )
     {
       org.apache.hivemind.Element nestedParameter = (org.apache.hivemind.Element)i.next();
 
       element.appendChild(getParamterElement(nestedParameter));
     }
 
     return element;
   }
 
   private void addSchema(org.w3c.dom.Element container, SchemaImpl s, String elementName)
   {
     if (this._processedSchemas.contains(s)) {
       return;
     }
     org.w3c.dom.Element schema = this._document.createElement(elementName);
 
     if (s.getId() != null) {
       schema.setAttribute("id", qualify(s.getId()));
     }
     if (s.getVisibility() == Visibility.PRIVATE) {
       schema.setAttribute("visibility", "private");
     }
     schema.appendChild(this._document.createTextNode(s.getAnnotation()));
 
     for (Iterator j = s.getElementModel().iterator(); j.hasNext(); )
     {
       ElementModel em = (ElementModel)j.next();
 
       org.w3c.dom.Element element = getElementElement(em);
 
       schema.appendChild(element);
     }
 
     container.appendChild(schema);
 
     this._processedSchemas.add(s);
   }
 
   private org.w3c.dom.Element getRulesElement(ElementModel em)
   {
     org.w3c.dom.Element rules = this._document.createElement("rules");
 
     for (Iterator i = em.getRules().iterator(); i.hasNext(); )
     {
       Rule r = (Rule)i.next();
 
       org.w3c.dom.Element rule = null;
 
       if (r instanceof CreateObjectRule)
       {
         CreateObjectRule cor = (CreateObjectRule)r;
         rule = this._document.createElement("create-object");
 
         rule.setAttribute("class", cor.getClassName());
       }
       else if (r instanceof InvokeParentRule)
       {
         InvokeParentRule ipr = (InvokeParentRule)r;
         rule = this._document.createElement("invoke-parent");
 
         rule.setAttribute("method", ipr.getMethodName());
         if (ipr.getDepth() != 1)
           rule.setAttribute("depth", Integer.toString(ipr.getDepth()));
       }
       else if (r instanceof PushAttributeRule)
       {
         PushAttributeRule par = (PushAttributeRule)r;
         rule = this._document.createElement("push-attribute");
 
         rule.setAttribute("attribute", par.getAttributeName());
       }
       else if (r instanceof PushContentRule)
       {
         rule = this._document.createElement("push-content");
       }
       else if (r instanceof ReadAttributeRule)
       {
         ReadAttributeRule rar = (ReadAttributeRule)r;
         rule = this._document.createElement("read-attribute");
 
         rule.setAttribute("property", rar.getPropertyName());
         rule.setAttribute("attribute", rar.getAttributeName());
         if (!(rar.getSkipIfNull()))
           rule.setAttribute("skip-if-null", "false");
         if (rar.getTranslator() != null)
           rule.setAttribute("translator", rar.getTranslator());
       }
       else if (r instanceof ReadContentRule)
       {
         ReadContentRule rcr = (ReadContentRule)r;
         rule = this._document.createElement("read-content");
 
         rule.setAttribute("property", rcr.getPropertyName());
       }
       else if (r instanceof SetModuleRule)
       {
         SetModuleRule smr = (SetModuleRule)r;
         rule = this._document.createElement("set-module");
 
         rule.setAttribute("property", smr.getPropertyName());
       }
       else if (r instanceof SetParentRule)
       {
         SetParentRule spr = (SetParentRule)r;
         rule = this._document.createElement("set-parent");
 
         rule.setAttribute("property", spr.getPropertyName());
       }
       else if (r instanceof SetPropertyRule)
       {
         SetPropertyRule spr = (SetPropertyRule)r;
         rule = this._document.createElement("set-property");
 
         rule.setAttribute("property", spr.getPropertyName());
         rule.setAttribute("value", spr.getValue());
       }
       else
       {
         Iterator j;
         if (r instanceof ConversionDescriptor)
         {
           ConversionDescriptor cd = (ConversionDescriptor)r;
           rule = this._document.createElement("conversion");
 
           rule.setAttribute("class", cd.getClassName());
           if (!(cd.getParentMethodName().equals("addElement"))) {
             rule.setAttribute("parent-method", cd.getParentMethodName());
           }
           for (j = cd.getAttributeMappings().iterator(); j.hasNext(); )
           {
             AttributeMappingDescriptor amd = (AttributeMappingDescriptor)j.next();
 
             org.w3c.dom.Element map = this._document.createElement("map");
 
             map.setAttribute("attribute", amd.getAttributeName());
             map.setAttribute("property", amd.getPropertyName());
 
             rule.appendChild(map);
           }
         }
         else
         {
           rule = this._document.createElement("custom");
 
           rule.setAttribute("class", r.getClass().getName());
         }
       }
       if (rule != null)
         rules.appendChild(rule);
     }
     return rules;
   }
 
   private org.w3c.dom.Element getElementElement(ElementModel em)
   {
     org.w3c.dom.Element element = this._document.createElement("element");
     element.setAttribute("name", em.getElementName());
 
     element.appendChild(this._document.createTextNode(em.getAnnotation()));
 
     for (Iterator i = em.getAttributeModels().iterator(); i.hasNext(); )
     {
       AttributeModel am = (AttributeModel)i.next();
 
       org.w3c.dom.Element attribute = getAttributeElement(am);
 
       element.appendChild(attribute);
     }
 
     for (i = em.getElementModel().iterator(); i.hasNext(); )
     {
       ElementModel nestedEm = (ElementModel)i.next();
 
       org.w3c.dom.Element nestedElement = getElementElement(nestedEm);
 
       element.appendChild(nestedElement);
     }
 
     if (!(em.getRules().isEmpty()))
     {
       org.w3c.dom.Element rules = getRulesElement(em);
 
       element.appendChild(rules);
     }
 
     return element;
   }
 
   private org.w3c.dom.Element getAttributeElement(AttributeModel am)
   {
     org.w3c.dom.Element attribute = this._document.createElement("attribute");
 
     attribute.setAttribute("name", am.getName());
     if (am.isRequired())
       attribute.setAttribute("required", "true");
     if (am.isUnique())
       attribute.setAttribute("unique", "true");
     if (!(am.getTranslator().equals("smart"))) {
       attribute.setAttribute("translator", am.getTranslator());
     }
     attribute.appendChild(this._document.createTextNode(am.getAnnotation()));
 
     return attribute;
   }
 
   private String qualify(String id)
   {
     return IdUtils.qualify(this._md.getModuleId(), id);
   }
 
   private DocumentBuilder getBuilder()
   {
     DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
 
     factory.setIgnoringComments(true);
     try
     {
       return factory.newDocumentBuilder();
     }
     catch (ParserConfigurationException e)
     {
       throw new ApplicationRuntimeException(e);
     }
   }
 
   public static Document createDefaultRegistryDocument()
   {
     ClassResolver resolver = new DefaultClassResolver();
     ModuleDescriptorProvider provider = new XmlModuleDescriptorProvider(resolver);
 
     RegistrySerializer serializer = new RegistrySerializer();
 
     serializer.addModuleDescriptorProvider(provider);
 
     return serializer.createRegistryDocument();
   }
 }