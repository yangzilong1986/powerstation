 package org.apache.hivemind.parse;
 
 import java.io.BufferedInputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.util.Enumeration;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.Properties;
 import java.util.Set;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.Attribute;
 import org.apache.hivemind.ClassResolver;
 import org.apache.hivemind.ErrorHandler;
 import org.apache.hivemind.Occurances;
 import org.apache.hivemind.Resource;
 import org.apache.hivemind.impl.AttributeImpl;
 import org.apache.hivemind.impl.BaseLocatable;
 import org.apache.hivemind.impl.ElementImpl;
 import org.apache.hivemind.internal.Visibility;
 import org.apache.hivemind.schema.ElementModel;
 import org.apache.hivemind.schema.Rule;
 import org.apache.hivemind.schema.impl.AttributeModelImpl;
 import org.apache.hivemind.schema.impl.ElementModelImpl;
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
 import org.apache.oro.text.regex.MalformedPatternException;
 import org.apache.oro.text.regex.Pattern;
 import org.apache.oro.text.regex.Perl5Compiler;
 import org.apache.oro.text.regex.Perl5Matcher;
 
 public final class DescriptorParser extends AbstractParser
 {
   private static final String DEFAULT_SERVICE_MODEL = "singleton";
   private static final Log LOG = LogFactory.getLog(DescriptorParser.class);
   private static final int STATE_START = 0;
   private static final int STATE_MODULE = 1;
   private static final int STATE_CONFIGURATION_POINT = 3;
   private static final int STATE_CONTRIBUTION = 4;
   private static final int STATE_SERVICE_POINT = 5;
   private static final int STATE_CREATE_INSTANCE = 6;
   private static final int STATE_IMPLEMENTATION = 8;
   private static final int STATE_SCHEMA = 9;
   private static final int STATE_ELEMENT = 10;
   private static final int STATE_RULES = 11;
   private static final int STATE_COLLECT_SERVICE_PARAMETERS = 12;
   private static final int STATE_CONVERSION = 13;
   private static final int STATE_LWDOM = 100;
   private static final int STATE_NO_CONTENT = 300;
   private static final String SIMPLE_ID = "[a-zA-Z0-9_]+";
   public static final String ID_PATTERN = "^[a-zA-Z0-9_]+$";
   public static final String MODULE_ID_PATTERN = "^[a-zA-Z0-9_]+(\\.[a-zA-Z0-9_]+)*$";
   public static final String VERSION_PATTERN = "[0-9]+(\\.[0-9]+){2}$";
   private Map _attributes = new HashMap();
 
   private Map _elementParseInfo = new HashMap();
   private ModuleDescriptor _moduleDescriptor;
   private ErrorHandler _errorHandler;
   private ClassResolver _resolver;
   private Perl5Compiler _compiler;
   private Perl5Matcher _matcher;
   private Map _compiledPatterns;
   private final Map _ruleMap = new HashMap();
 
   private final Map OCCURS_MAP = new HashMap();
   private final Map VISIBILITY_MAP;
 
   public DescriptorParser(ErrorHandler errorHandler)
   {
     this.OCCURS_MAP.put("0..1", Occurances.OPTIONAL);
     this.OCCURS_MAP.put("1", Occurances.REQUIRED);
     this.OCCURS_MAP.put("1..n", Occurances.ONE_PLUS);
     this.OCCURS_MAP.put("0..n", Occurances.UNBOUNDED);
     this.OCCURS_MAP.put("none", Occurances.NONE);
 
     this.VISIBILITY_MAP = new HashMap();
 
     this.VISIBILITY_MAP.put("public", Visibility.PUBLIC);
     this.VISIBILITY_MAP.put("private", Visibility.PRIVATE);
 
     this._errorHandler = errorHandler;
 
     initializeFromPropertiesFile();
   }
 
   public void begin(String elementName, Map attributes)
   {
     this._attributes = attributes;
 
     switch (super.getState())
     {
     case 0:
       beginStart(elementName);
       break;
     case 1:
       beginModule(elementName);
       break;
     case 3:
       beginConfigurationPoint(elementName);
       break;
     case 4:
       beginContribution(elementName);
       break;
     case 100:
       beginLWDom(elementName);
       break;
     case 5:
       beginServicePoint(elementName);
       break;
     case 8:
       beginImplementation(elementName);
       break;
     case 9:
       beginSchema(elementName);
       break;
     case 10:
       beginElement(elementName);
       break;
     case 11:
       beginRules(elementName);
       break;
     case 12:
       beginCollectServiceParameters(elementName);
       break;
     case 13:
       beginConversion(elementName);
       break;
     default:
       super.unexpectedElement(elementName);
     }
   }
 
   private void beginCollectServiceParameters(String elementName)
   {
     ElementImpl element = buildLWDomElement(elementName);
 
     AbstractServiceInvocationDescriptor sid = (AbstractServiceInvocationDescriptor)super.peekObject();
 
     sid.addParameter(element);
 
     super.push(elementName, element, 100, false);
   }
 
   private void beginConfigurationPoint(String elementName)
   {
     if (elementName.equals("schema"))
     {
       enterEmbeddedConfigurationPointSchema(elementName);
       return;
     }
 
     super.unexpectedElement(elementName);
   }
 
   private void beginContribution(String elementName)
   {
     ElementImpl element = buildLWDomElement(elementName);
 
     ContributionDescriptor ed = (ContributionDescriptor)super.peekObject();
     ed.addElement(element);
 
     super.push(elementName, element, 100, false);
   }
 
   private void beginConversion(String elementName)
   {
     if (elementName.equals("map"))
     {
       ConversionDescriptor cd = (ConversionDescriptor)super.peekObject();
 
       AttributeMappingDescriptor amd = new AttributeMappingDescriptor();
 
       push(elementName, amd, 300);
 
       checkAttributes();
 
       amd.setAttributeName(getAttribute("attribute"));
       amd.setPropertyName(getAttribute("property"));
 
       cd.addAttributeMapping(amd);
 
       return;
     }
 
     super.unexpectedElement(elementName);
   }
 
   private void beginElement(String elementName)
   {
     if (elementName.equals("attribute"))
     {
       enterAttribute(elementName);
       return;
     }
 
     if (elementName.equals("conversion"))
     {
       enterConversion(elementName);
       return;
     }
 
     if (elementName.equals("rules"))
     {
       enterRules(elementName);
       return;
     }
 
     if (elementName.equals("element"))
     {
       ElementModelImpl elementModel = (ElementModelImpl)super.peekObject();
 
       elementModel.addElementModel(enterElement(elementName));
       return;
     }
 
     super.unexpectedElement(elementName);
   }
 
   private void beginImplementation(String elementName)
   {
     if (elementName.equals("create-instance"))
     {
       enterCreateInstance(elementName);
       return;
     }
 
     if (elementName.equals("invoke-factory"))
     {
       enterInvokeFactory(elementName);
       return;
     }
 
     if (elementName.equals("interceptor"))
     {
       enterInterceptor(elementName);
       return;
     }
 
     super.unexpectedElement(elementName);
   }
 
   private void beginLWDom(String elementName)
   {
     ElementImpl element = buildLWDomElement(elementName);
 
     ElementImpl parent = (ElementImpl)super.peekObject();
     parent.addElement(element);
 
     super.push(elementName, element, 100, false);
   }
 
   private void beginModule(String elementName)
   {
     if (elementName.equals("configuration-point"))
     {
       enterConfigurationPoint(elementName);
 
       return;
     }
 
     if (elementName.equals("contribution"))
     {
       enterContribution(elementName);
       return;
     }
 
     if (elementName.equals("service-point"))
     {
       enterServicePoint(elementName);
 
       return;
     }
 
     if (elementName.equals("implementation"))
     {
       enterImplementation(elementName);
 
       return;
     }
 
     if (elementName.equals("schema"))
     {
       enterSchema(elementName);
       return;
     }
 
     if (elementName.equals("sub-module"))
     {
       enterSubModule(elementName);
 
       return;
     }
 
     if (elementName.equals("dependency"))
     {
       enterDependency(elementName);
 
       return;
     }
 
     super.unexpectedElement(elementName);
   }
 
   private void beginRules(String elementName)
   {
     if (elementName.equals("create-object"))
     {
       enterCreateObject(elementName);
       return;
     }
 
     if (elementName.equals("invoke-parent"))
     {
       enterInvokeParent(elementName);
       return;
     }
 
     if (elementName.equals("read-attribute"))
     {
       enterReadAttribute(elementName);
       return;
     }
 
     if (elementName.equals("read-content"))
     {
       enterReadContent(elementName);
       return;
     }
 
     if (elementName.equals("set-module"))
     {
       enterSetModule(elementName);
       return;
     }
 
     if (elementName.equals("set-property"))
     {
       enterSetProperty(elementName);
       return;
     }
 
     if (elementName.equals("push-attribute"))
     {
       enterPushAttribute(elementName);
       return;
     }
 
     if (elementName.equals("push-content"))
     {
       enterPushContent(elementName);
       return;
     }
 
     if (elementName.equals("set-parent"))
     {
       enterSetParent(elementName);
       return;
     }
 
     if (elementName.equals("custom"))
     {
       enterCustom(elementName);
 
       return;
     }
 
     super.unexpectedElement(elementName);
   }
 
   private void beginSchema(String elementName)
   {
     if (elementName.equals("element"))
     {
       SchemaImpl schema = (SchemaImpl)super.peekObject();
 
       schema.addElementModel(enterElement(elementName));
       return;
     }
 
     super.unexpectedElement(elementName);
   }
 
   private void beginServicePoint(String elementName)
   {
     if (elementName.equals("parameters-schema"))
     {
       enterParametersSchema(elementName);
       return;
     }
 
     beginImplementation(elementName);
   }
 
   private void beginStart(String elementName)
   {
     if (!(elementName.equals("module"))) {
       throw new ApplicationRuntimeException(ParseMessages.notModule(elementName, super.getLocation()), super.getLocation(), null);
     }
 
     ModuleDescriptor md = new ModuleDescriptor(this._resolver, this._errorHandler);
 
     push(elementName, md, 1);
 
     checkAttributes();
 
     md.setModuleId(getValidatedAttribute("id", "^[a-zA-Z0-9_]+(\\.[a-zA-Z0-9_]+)*$", "module-id-format"));
     md.setVersion(getValidatedAttribute("version", "[0-9]+(\\.[0-9]+){2}$", "version-format"));
 
     String packageName = getAttribute("package");
     if (packageName == null) {
       packageName = md.getModuleId();
     }
     md.setPackageName(packageName);
 
     this._moduleDescriptor = md;
   }
 
   protected void push(String elementName, Object object, int state)
   {
     if (object instanceof AnnotationHolder)
       super.push(elementName, object, state, false);
     else
       super.push(elementName, object, state, true);
   }
 
   private ElementImpl buildLWDomElement(String elementName)
   {
     ElementImpl result = new ElementImpl();
     result.setElementName(elementName);
 
     Iterator i = this._attributes.entrySet().iterator();
     while (i.hasNext())
     {
       Map.Entry entry = (Map.Entry)i.next();
 
       String name = (String)entry.getKey();
       String value = (String)entry.getValue();
 
       Attribute a = new AttributeImpl(name, value);
 
       result.addAttribute(a);
     }
 
     return result;
   }
 
   private void checkAttributes()
   {
     checkAttributes(super.peekElementName());
   }
 
   private void checkAttributes(String elementName)
   {
     String name;
     Iterator i = this._attributes.keySet().iterator();
 
     ElementParseInfo epi = (ElementParseInfo)this._elementParseInfo.get(elementName);
 
     if (epi == null)
     {
       epi = new ElementParseInfo();
       this._elementParseInfo.put(elementName, epi);
     }
 
     while (i.hasNext())
     {
       name = (String)i.next();
 
       if (!(epi.isKnown(name))) {
         this._errorHandler.error(LOG, ParseMessages.unknownAttribute(name, super.getElementPath()), super.getLocation(), null);
       }
 
     }
 
     i = epi.getRequiredNames();
     while (i.hasNext())
     {
       name = (String)i.next();
 
       if (!(this._attributes.containsKey(name)))
         throw new ApplicationRuntimeException(ParseMessages.requiredAttribute(name, super.getElementPath(), super.getLocation()));
     }
   }
 
   public void end(String elementName)
   {
     switch (super.getState())
     {
     case 100:
       endLWDom();
       break;
     case 13:
       endConversion();
       break;
     case 9:
       endSchema();
       break;
     default:
       String content = super.peekContent();
 
       if ((content != null) && (super.peekObject() instanceof AnnotationHolder)) {
         ((AnnotationHolder)super.peekObject()).setAnnotation(content);
       }
 
     }
 
     super.pop();
   }
 
   private void endSchema()
   {
     SchemaImpl schema = (SchemaImpl)super.peekObject();
 
     schema.setAnnotation(super.peekContent());
     try
     {
       schema.validateKeyAttributes();
     }
     catch (ApplicationRuntimeException e)
     {
       this._errorHandler.error(LOG, ParseMessages.invalidElementKeyAttribute(schema.getId(), e), e.getLocation(), e);
     }
   }
 
   private void endConversion()
   {
     ConversionDescriptor cd = (ConversionDescriptor)super.peekObject();
 
     cd.addRulesForModel();
   }
 
   private void endLWDom()
   {
     ElementImpl element = (ElementImpl)super.peekObject();
     element.setContent(super.peekContent());
   }
 
   private void enterAttribute(String elementName)
   {
     ElementModelImpl elementModel = (ElementModelImpl)super.peekObject();
 
     AttributeModelImpl attributeModel = new AttributeModelImpl();
 
     push(elementName, attributeModel, 300);
 
     checkAttributes();
 
     attributeModel.setName(getAttribute("name"));
     attributeModel.setRequired(getBooleanAttribute("required", false));
     attributeModel.setUnique(getBooleanAttribute("unique", false));
     attributeModel.setTranslator(getAttribute("translator", "smart"));
 
     elementModel.addAttributeModel(attributeModel);
   }
 
   private void enterConfigurationPoint(String elementName)
   {
     ModuleDescriptor md = (ModuleDescriptor)super.peekObject();
 
     ConfigurationPointDescriptor cpd = new ConfigurationPointDescriptor();
 
     push(elementName, cpd, 3);
 
     checkAttributes();
 
     cpd.setId(getValidatedAttribute("id", "^[a-zA-Z0-9_]+$", "id-format"));
 
     Occurances count = (Occurances)getEnumAttribute("occurs", this.OCCURS_MAP);
 
     if (count != null) {
       cpd.setCount(count);
     }
     Visibility visibility = (Visibility)getEnumAttribute("visibility", this.VISIBILITY_MAP);
 
     if (visibility != null) {
       cpd.setVisibility(visibility);
     }
     cpd.setContributionsSchemaId(getAttribute("schema-id"));
 
     md.addConfigurationPoint(cpd);
   }
 
   private void enterContribution(String elementName)
   {
     ModuleDescriptor md = (ModuleDescriptor)super.peekObject();
 
     ContributionDescriptor cd = new ContributionDescriptor();
 
     push(elementName, cd, 4);
 
     checkAttributes();
 
     cd.setConfigurationId(getAttribute("configuration-id"));
     cd.setConditionalExpression(getAttribute("if"));
 
     md.addContribution(cd);
   }
 
   private void enterConversion(String elementName)
   {
     ElementModelImpl elementModel = (ElementModelImpl)super.peekObject();
 
     ConversionDescriptor cd = new ConversionDescriptor(this._errorHandler, elementModel);
 
     push(elementName, cd, 13);
 
     checkAttributes();
 
     cd.setClassName(getAttribute("class"));
 
     String methodName = getAttribute("parent-method");
 
     if (methodName != null) {
       cd.setParentMethodName(methodName);
     }
     elementModel.addRule(cd);
   }
 
   private void enterCreateInstance(String elementName)
   {
     AbstractServiceDescriptor sd = (AbstractServiceDescriptor)super.peekObject();
     CreateInstanceDescriptor cid = new CreateInstanceDescriptor();
 
     push(elementName, cid, 6);
 
     checkAttributes();
 
     cid.setInstanceClassName(getAttribute("class"));
 
     String model = getAttribute("model", "singleton");
 
     cid.setServiceModel(model);
 
     sd.setInstanceBuilder(cid);
   }
 
   private void enterCreateObject(String elementName)
   {
     ElementModelImpl elementModel = (ElementModelImpl)super.peekObject();
     CreateObjectRule rule = new CreateObjectRule();
     push(elementName, rule, 300);
 
     checkAttributes();
 
     rule.setClassName(getAttribute("class"));
 
     elementModel.addRule(rule);
   }
 
   private void enterCustom(String elementName)
   {
     ElementModelImpl elementModel = (ElementModelImpl)super.peekObject();
 
     push(elementName, null, 300);
 
     checkAttributes();
 
     String ruleClassName = getAttribute("class");
 
     Rule rule = getCustomRule(ruleClassName);
 
     elementModel.addRule(rule);
   }
 
   private ElementModel enterElement(String elementName)
   {
     ElementModelImpl result = new ElementModelImpl();
 
     push(elementName, result, 10);
 
     checkAttributes();
 
     result.setElementName(getAttribute("name"));
     result.setKeyAttribute(getAttribute("key-attribute"));
     result.setContentTranslator(getAttribute("content-translator"));
 
     return result;
   }
 
   private void enterEmbeddedConfigurationPointSchema(String elementName)
   {
     ConfigurationPointDescriptor cpd = (ConfigurationPointDescriptor)super.peekObject();
 
     SchemaImpl schema = new SchemaImpl();
 
     push(elementName, schema, 9);
 
     if (cpd.getContributionsSchemaId() != null)
     {
       cpd.setContributionsSchemaId(null);
       cpd.setContributionsSchema(schema);
       this._errorHandler.error(LOG, ParseMessages.multipleContributionsSchemas(cpd.getId(), schema.getLocation()), schema.getLocation(), null);
     }
     else
     {
       cpd.setContributionsSchema(schema);
     }
     checkAttributes("schema{embedded}");
   }
 
   private void enterParametersSchema(String elementName)
   {
     ServicePointDescriptor spd = (ServicePointDescriptor)super.peekObject();
     SchemaImpl schema = new SchemaImpl();
 
     push(elementName, schema, 9);
 
     checkAttributes();
 
     if (spd.getParametersSchemaId() != null)
     {
       spd.setParametersSchemaId(null);
       spd.setParametersSchema(schema);
       this._errorHandler.error(LOG, ParseMessages.multipleParametersSchemas(spd.getId(), schema.getLocation()), schema.getLocation(), null);
     }
     else
     {
       spd.setParametersSchema(schema);
     }
   }
 
   private void enterImplementation(String elementName) {
     ModuleDescriptor md = (ModuleDescriptor)super.peekObject();
 
     ImplementationDescriptor id = new ImplementationDescriptor();
 
     push(elementName, id, 8);
 
     checkAttributes();
 
     id.setServiceId(getAttribute("service-id"));
     id.setConditionalExpression(getAttribute("if"));
 
     md.addImplementation(id);
   }
 
   private void enterInterceptor(String elementName)
   {
     AbstractServiceDescriptor sd = (AbstractServiceDescriptor)super.peekObject();
     InterceptorDescriptor id = new InterceptorDescriptor();
 
     push(elementName, id, 12);
 
     checkAttributes();
 
     id.setFactoryServiceId(getAttribute("service-id"));
 
     id.setBefore(getAttribute("before"));
     id.setAfter(getAttribute("after"));
     id.setName(getAttribute("name"));
     sd.addInterceptor(id);
   }
 
   private void enterInvokeFactory(String elementName)
   {
     AbstractServiceDescriptor sd = (AbstractServiceDescriptor)super.peekObject();
     InvokeFactoryDescriptor ifd = new InvokeFactoryDescriptor();
 
     push(elementName, ifd, 12);
 
     checkAttributes();
 
     ifd.setFactoryServiceId(getAttribute("service-id", "hivemind.BuilderFactory"));
 
     String model = getAttribute("model", "singleton");
 
     ifd.setServiceModel(model);
 
     sd.setInstanceBuilder(ifd);
   }
 
   private void enterInvokeParent(String elementName)
   {
     ElementModelImpl elementModel = (ElementModelImpl)super.peekObject();
     InvokeParentRule rule = new InvokeParentRule();
 
     push(elementName, rule, 300);
 
     checkAttributes();
 
     rule.setMethodName(getAttribute("method"));
 
     if (this._attributes.containsKey("depth")) {
       rule.setDepth(getIntAttribute("depth"));
     }
     elementModel.addRule(rule);
   }
 
   private void enterReadAttribute(String elementName)
   {
     ElementModelImpl elementModel = (ElementModelImpl)super.peekObject();
     ReadAttributeRule rule = new ReadAttributeRule();
 
     push(elementName, rule, 300);
 
     checkAttributes();
 
     rule.setPropertyName(getAttribute("property"));
     rule.setAttributeName(getAttribute("attribute"));
     rule.setSkipIfNull(getBooleanAttribute("skip-if-null", true));
     rule.setTranslator(getAttribute("translator"));
 
     elementModel.addRule(rule);
   }
 
   private void enterReadContent(String elementName)
   {
     ElementModelImpl elementModel = (ElementModelImpl)super.peekObject();
     ReadContentRule rule = new ReadContentRule();
 
     push(elementName, rule, 300);
 
     checkAttributes();
 
     rule.setPropertyName(getAttribute("property"));
 
     elementModel.addRule(rule);
   }
 
   private void enterRules(String elementName)
   {
     ElementModelImpl elementModel = (ElementModelImpl)super.peekObject();
 
     push(elementName, elementModel, 11);
   }
 
   private void enterSchema(String elementName)
   {
     SchemaImpl schema = new SchemaImpl();
 
     push(elementName, schema, 9);
 
     checkAttributes();
 
     String id = getValidatedAttribute("id", "^[a-zA-Z0-9_]+$", "id-format");
 
     schema.setId(id);
 
     Visibility visibility = (Visibility)getEnumAttribute("visibility", this.VISIBILITY_MAP);
 
     if (visibility != null) {
       schema.setVisibility(visibility);
     }
     this._moduleDescriptor.addSchema(schema);
   }
 
   private void enterServicePoint(String elementName)
   {
     ModuleDescriptor md = (ModuleDescriptor)super.peekObject();
 
     ServicePointDescriptor spd = new ServicePointDescriptor();
 
     push(elementName, spd, 5);
 
     checkAttributes();
 
     String id = getValidatedAttribute("id", "^[a-zA-Z0-9_]+$", "id-format");
 
     String interfaceAttribute = getAttribute("interface", id);
 
     String interfaceName = IdUtils.qualify(this._moduleDescriptor.getPackageName(), interfaceAttribute);
 
     spd.setId(id);
 
     spd.setInterfaceClassName(interfaceName);
 
     spd.setParametersSchemaId(getAttribute("parameters-schema-id"));
 
     Occurances count = (Occurances)getEnumAttribute("parameters-occurs", this.OCCURS_MAP);
 
     if (count != null) {
       spd.setParametersCount(count);
     }
     Visibility visibility = (Visibility)getEnumAttribute("visibility", this.VISIBILITY_MAP);
 
     if (visibility != null) {
       spd.setVisibility(visibility);
     }
     md.addServicePoint(spd);
   }
 
   private void enterSetModule(String elementName)
   {
     ElementModelImpl elementModel = (ElementModelImpl)super.peekObject();
     SetModuleRule rule = new SetModuleRule();
 
     push(elementName, rule, 300);
 
     checkAttributes();
 
     rule.setPropertyName(getAttribute("property"));
 
     elementModel.addRule(rule);
   }
 
   private void enterSetParent(String elementName)
   {
     ElementModelImpl elementModel = (ElementModelImpl)super.peekObject();
     SetParentRule rule = new SetParentRule();
 
     push(elementName, rule, 300);
 
     checkAttributes();
 
     rule.setPropertyName(getAttribute("property"));
 
     elementModel.addRule(rule);
   }
 
   private void enterSetProperty(String elementName)
   {
     ElementModelImpl elementModel = (ElementModelImpl)super.peekObject();
 
     SetPropertyRule rule = new SetPropertyRule();
 
     push(elementName, rule, 300);
 
     checkAttributes();
 
     rule.setPropertyName(getAttribute("property"));
     rule.setValue(getAttribute("value"));
 
     elementModel.addRule(rule);
   }
 
   private void enterPushAttribute(String elementName)
   {
     ElementModelImpl elementModel = (ElementModelImpl)super.peekObject();
 
     PushAttributeRule rule = new PushAttributeRule();
 
     push(elementName, rule, 300);
 
     checkAttributes();
 
     rule.setAttributeName(getAttribute("attribute"));
 
     elementModel.addRule(rule);
   }
 
   private void enterPushContent(String elementName)
   {
     ElementModelImpl elementModel = (ElementModelImpl)super.peekObject();
 
     PushContentRule rule = new PushContentRule();
 
     push(elementName, rule, 300);
 
     checkAttributes();
 
     elementModel.addRule(rule);
   }
 
   private void enterSubModule(String elementName)
   {
     ModuleDescriptor md = (ModuleDescriptor)super.peekObject();
 
     SubModuleDescriptor smd = new SubModuleDescriptor();
 
     push(elementName, smd, 300);
 
     checkAttributes();
 
     Resource descriptor = super.getResource().getRelativeResource(getAttribute("descriptor"));
 
     smd.setDescriptor(descriptor);
 
     md.addSubModule(smd);
   }
 
   private void enterDependency(String elementName)
   {
     ModuleDescriptor md = (ModuleDescriptor)super.peekObject();
 
     DependencyDescriptor dd = new DependencyDescriptor();
 
     push(elementName, dd, 300);
 
     checkAttributes();
 
     dd.setModuleId(getAttribute("module-id"));
     dd.setVersion(getAttribute("version"));
 
     md.addDependency(dd);
   }
 
   private String getAttribute(String name)
   {
     return ((String)this._attributes.get(name));
   }
 
   private String getAttribute(String name, String defaultValue)
   {
     String result = (String)this._attributes.get(name);
 
     if (result == null) {
       result = defaultValue;
     }
     return result;
   }
 
   private String getValidatedAttribute(String name, String pattern, String formatKey)
   {
     String result = getAttribute(name);
 
     if (!(validateFormat(result, pattern))) {
       this._errorHandler.error(LOG, ParseMessages.invalidAttributeFormat(name, result, super.getElementPath(), formatKey), super.getLocation(), null);
     }
 
     return result;
   }
 
   private boolean validateFormat(String input, String pattern)
   {
     if (this._compiler == null)
     {
       this._compiler = new Perl5Compiler();
       this._matcher = new Perl5Matcher();
       this._compiledPatterns = new HashMap();
     }
 
     Pattern compiled = (Pattern)this._compiledPatterns.get(pattern);
     if (compiled == null)
     {
       try
       {
         compiled = this._compiler.compile(pattern);
       }
       catch (MalformedPatternException ex)
       {
         throw new ApplicationRuntimeException(ex);
       }
 
       this._compiledPatterns.put(pattern, compiled);
     }
 
     return this._matcher.matches(input, compiled);
   }
 
   private boolean getBooleanAttribute(String name, boolean defaultValue)
   {
     String value = getAttribute(name);
 
     if (value == null) {
       return defaultValue;
     }
     if (value.equals("true")) {
       return true;
     }
     if (value.equals("false")) {
       return false;
     }
     this._errorHandler.error(LOG, ParseMessages.booleanAttribute(value, name, super.getElementPath()), super.getLocation(), null);
 
     return defaultValue;
   }
 
   private Rule getCustomRule(String ruleClassName)
   {
     Rule result = (Rule)this._ruleMap.get(ruleClassName);
 
     if (result == null)
     {
       result = instantiateRule(ruleClassName);
 
       this._ruleMap.put(ruleClassName, result);
     }
 
     return result;
   }
 
   private Object getEnumAttribute(String name, Map translations)
   {
     String value = getAttribute(name);
 
     if (value == null) {
       return null;
     }
     Object result = translations.get(value);
 
     if (result == null) {
       this._errorHandler.error(LOG, ParseMessages.invalidAttributeValue(value, name, super.getElementPath()), super.getLocation(), null);
     }
 
     return result;
   }
 
   private int getIntAttribute(String name)
   {
     String value = getAttribute(name);
     try
     {
       return Integer.parseInt(value);
     }
     catch (NumberFormatException ex)
     {
       this._errorHandler.error(LOG, ParseMessages.invalidNumericValue(value, name, super.getElementPath()), super.getLocation(), ex);
     }
 
     return 0;
   }
 
   private void initializeFromProperties(Properties p)
   {
     Enumeration e = p.propertyNames();
 
     while (e.hasMoreElements())
     {
       String key = (String)e.nextElement();
       String value = p.getProperty(key);
 
       initializeFromProperty(key, value);
     }
   }
 
   private void initializeFromPropertiesFile()
   {
     Properties p = new Properties();
     try
     {
       InputStream propertiesIn = super.getClass().getResourceAsStream("DescriptorParser.properties");
 
       InputStream bufferedIn = new BufferedInputStream(propertiesIn);
 
       p.load(bufferedIn);
 
       bufferedIn.close();
     }
     catch (IOException ex)
     {
       this._errorHandler.error(LOG, ParseMessages.unableToInitialize(ex), null, ex);
     }
 
     initializeFromProperties(p);
   }
 
   private void initializeFromProperty(String key, String value)
   {
     if (!(key.startsWith("required.")))
       return;
     initializeRequired(key, value);
     return;
   }
 
   private void initializeRequired(String key, String value)
   {
     boolean required = value.equals("true");
 
     int lastdotx = key.lastIndexOf(46);
 
     String elementName = key.substring(9, lastdotx);
     String attributeName = key.substring(lastdotx + 1);
 
     ElementParseInfo epi = (ElementParseInfo)this._elementParseInfo.get(elementName);
 
     if (epi == null)
     {
       epi = new ElementParseInfo();
       this._elementParseInfo.put(elementName, epi);
     }
 
     epi.addAttribute(attributeName, required);
   }
 
   private Rule instantiateRule(String ruleClassName)
   {
     try
     {
       Class ruleClass = this._resolver.findClass(ruleClassName);
 
       return ((Rule)ruleClass.newInstance());
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(ParseMessages.badRuleClass(ruleClassName, super.getLocation(), ex), super.getLocation(), ex);
     }
   }
 
   public void initialize(Resource resource, ClassResolver resolver)
   {
     super.initializeParser(resource, 0);
 
     this._resolver = resolver;
   }
 
   public ModuleDescriptor getModuleDescriptor()
   {
     return this._moduleDescriptor;
   }
 
   public void reset()
   {
     super.resetParser();
 
     this._moduleDescriptor = null;
     this._attributes.clear();
     this._resolver = null;
   }
 }