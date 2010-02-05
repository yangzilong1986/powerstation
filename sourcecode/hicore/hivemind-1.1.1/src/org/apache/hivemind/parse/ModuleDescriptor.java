 package org.apache.hivemind.parse;
 
 import java.util.ArrayList;
 import java.util.Collection;
 import java.util.Collections;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.apache.hivemind.ClassResolver;
 import org.apache.hivemind.ErrorHandler;
 import org.apache.hivemind.impl.BaseLocatable;
 import org.apache.hivemind.schema.Schema;
 import org.apache.hivemind.schema.impl.SchemaImpl;
 import org.apache.hivemind.util.ToStringBuilder;
 
 public final class ModuleDescriptor extends BaseAnnotationHolder
 {
   private static final Log LOG = LogFactory.getLog(ModuleDescriptor.class);
   private String _moduleId;
   private String _version;
   private String _packageName;
   private List _servicePoints;
   private List _implementations;
   private List _configurationPoints;
   private List _contributions;
   private List _subModules;
   private List _dependencies;
   private Map _schemas;
   private ClassResolver _resolver;
   private ErrorHandler _errorHandler;
 
   public ModuleDescriptor(ClassResolver resolver, ErrorHandler errorHandler)
   {
     this._resolver = resolver;
     this._errorHandler = errorHandler;
   }
 
   public String toString()
   {
     ToStringBuilder builder = new ToStringBuilder(this);
 
     builder.append("moduleId", this._moduleId);
     builder.append("version", this._version);
 
     return builder.toString();
   }
 
   public void addServicePoint(ServicePointDescriptor service)
   {
     if (this._servicePoints == null) {
       this._servicePoints = new ArrayList();
     }
     this._servicePoints.add(service);
   }
 
   public List getServicePoints()
   {
     return this._servicePoints;
   }
 
   public void addImplementation(ImplementationDescriptor descriptor)
   {
     if (this._implementations == null) {
       this._implementations = new ArrayList();
     }
     this._implementations.add(descriptor);
   }
 
   public List getImplementations()
   {
     return this._implementations;
   }
 
   public void addConfigurationPoint(ConfigurationPointDescriptor descriptor)
   {
     if (this._configurationPoints == null) {
       this._configurationPoints = new ArrayList();
     }
     this._configurationPoints.add(descriptor);
   }
 
   public List getConfigurationPoints()
   {
     return this._configurationPoints;
   }
 
   public void addContribution(ContributionDescriptor descriptor)
   {
     if (this._contributions == null) {
       this._contributions = new ArrayList();
     }
     this._contributions.add(descriptor);
   }
 
   public List getContributions()
   {
     return this._contributions;
   }
 
   public void addSubModule(SubModuleDescriptor subModule)
   {
     if (this._subModules == null) {
       this._subModules = new ArrayList();
     }
     this._subModules.add(subModule);
   }
 
   public List getSubModules()
   {
     return this._subModules;
   }
 
   public void addDependency(DependencyDescriptor dependency)
   {
     if (this._dependencies == null) {
       this._dependencies = new ArrayList();
     }
     this._dependencies.add(dependency);
   }
 
   public List getDependencies()
   {
     return this._dependencies;
   }
 
   public void addSchema(SchemaImpl schema)
   {
     if (this._schemas == null) {
       this._schemas = new HashMap();
     }
     String schemaId = schema.getId();
 
     Schema existing = getSchema(schemaId);
 
     if (existing != null)
     {
       this._errorHandler.error(LOG, ParseMessages.duplicateSchema(this._moduleId + '.' + schemaId, existing), schema.getLocation(), null);
 
       return;
     }
 
     this._schemas.put(schemaId, schema);
   }
 
   public Schema getSchema(String id)
   {
     return ((this._schemas == null) ? null : (Schema)this._schemas.get(id));
   }
 
   public Collection getSchemas()
   {
     return ((this._schemas != null) ? this._schemas.values() : Collections.EMPTY_LIST);
   }
 
   public String getModuleId()
   {
     return this._moduleId;
   }
 
   public String getVersion()
   {
     return this._version;
   }
 
   public void setModuleId(String string)
   {
     this._moduleId = string;
   }
 
   public void setVersion(String string)
   {
     this._version = string;
   }
 
   public ClassResolver getClassResolver()
   {
     return this._resolver;
   }
 
   public String getPackageName()
   {
     return this._packageName;
   }
 
   public void setPackageName(String packageName)
   {
     this._packageName = packageName;
   }
 }