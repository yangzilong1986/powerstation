 package org.apache.hivemind.impl;
 
 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.List;
 import java.util.Map;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.Occurances;
 import org.apache.hivemind.ShutdownCoordinator;
 import org.apache.hivemind.internal.ConfigurationPoint;
 import org.apache.hivemind.internal.Contribution;
 import org.apache.hivemind.schema.Schema;
 import org.apache.hivemind.util.ToStringBuilder;
 
 public final class ConfigurationPointImpl extends AbstractExtensionPoint
   implements ConfigurationPoint
 {
   private static final Log LOG = LogFactory.getLog(ConfigurationPointImpl.class);
   private List _elements;
   private List _elementsProxy;
   private Map _mappedElements;
   private Map _mappedElementsProxy;
   private boolean _canElementsBeMapped;
   private Occurances _expectedCount;
   private List _contributions;
   private boolean _building;
   private Schema _contributionsSchema;
   private ShutdownCoordinator _shutdownCoordinator;
 
   public ConfigurationPointImpl()
   {
     this._canElementsBeMapped = false;
   }
 
   protected void extendDescription(ToStringBuilder builder)
   {
     builder.append("expectedCount", this._expectedCount);
     builder.append("contributions", this._contributions);
     builder.append("schema", this._contributionsSchema);
   }
 
   public int getContributionCount()
   {
     if (this._contributions == null) {
       return 0;
     }
     int total = 0;
 
     int count = this._contributions.size();
     for (int i = 0; i < count; ++i)
     {
       Contribution c = (Contribution)this._contributions.get(i);
       total += c.getElements().size();
     }
 
     return total;
   }
 
   public void addContribution(Contribution c)
   {
     if (this._contributions == null) {
       this._contributions = new ArrayList();
     }
     this._contributions.add(c);
   }
 
   public Occurances getExpectedCount()
   {
     return this._expectedCount;
   }
 
   public void setExpectedCount(Occurances occurances)
   {
     this._expectedCount = occurances;
   }
 
   public synchronized List getElements()
   {
     if (this._elements != null) {
       return this._elements;
     }
     if (this._elementsProxy == null)
     {
       ElementsProxyList outerProxy = new ElementsProxyList();
 
       new ElementsInnerProxyList(this, outerProxy);
 
       this._shutdownCoordinator.addRegistryShutdownListener(outerProxy);
 
       this._elementsProxy = outerProxy;
     }
 
     return this._elementsProxy;
   }
 
   public boolean areElementsMappable()
   {
     return this._canElementsBeMapped;
   }
 
   public synchronized Map getElementsAsMap()
   {
     if (!(areElementsMappable())) {
       throw new ApplicationRuntimeException(ImplMessages.unableToMapConfiguration(this));
     }
     if (this._mappedElements != null) {
       return this._mappedElements;
     }
     if (this._mappedElementsProxy == null)
     {
       ElementsProxyMap outerProxy = new ElementsProxyMap();
 
       new ElementsInnerProxyMap(this, outerProxy);
 
       this._shutdownCoordinator.addRegistryShutdownListener(outerProxy);
 
       this._mappedElementsProxy = outerProxy;
     }
 
     return this._mappedElementsProxy;
   }
 
   synchronized List constructElements()
   {
     if (this._building) {
       throw new ApplicationRuntimeException(ImplMessages.recursiveConfiguration(super.getExtensionPointId()));
     }
 
     try
     {
       if (this._elements == null)
       {
         this._building = true;
 
         processContributionElements();
       }
 
       this._elementsProxy = null;
 
       List localList = this._elements;
 
       return localList;
     }
     finally
     {
       this._building = false;
     }
   }
 
   synchronized Map constructMapElements()
   {
     if (this._building) {
       throw new ApplicationRuntimeException(ImplMessages.recursiveConfiguration(super.getExtensionPointId()));
     }
 
     try
     {
       if (this._mappedElements == null)
       {
         this._building = true;
 
         processContributionElements();
       }
 
       this._mappedElementsProxy = null;
 
       Map localMap = this._mappedElements;
 
       return localMap;
     }
     finally
     {
       this._building = false;
     }
   }
 
   private void processContributionElements()
   {
     if (LOG.isDebugEnabled()) {
       LOG.debug("Constructing extension point " + super.getExtensionPointId());
     }
     if (this._contributions == null)
     {
       this._elements = Collections.EMPTY_LIST;
       this._mappedElements = Collections.EMPTY_MAP;
 
       return;
     }
 
     SchemaProcessorImpl processor = new SchemaProcessorImpl(super.getErrorLog(), this._contributionsSchema);
 
     int count = this._contributions.size();
     try
     {
       for (int i = 0; i < count; ++i)
       {
         Contribution extension = (Contribution)this._contributions.get(i);
 
         processor.process(extension.getElements(), extension.getContributingModule());
       }
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(ImplMessages.unableToConstructConfiguration(super.getExtensionPointId(), ex), ex);
     }
 
     if (areElementsMappable()) {
       this._mappedElements = Collections.unmodifiableMap(processor.getMappedElements());
     }
     this._elements = Collections.unmodifiableList(processor.getElements());
 
     this._contributionsSchema = null;
     this._contributions = null;
   }
 
   public Schema getSchema()
   {
     return this._contributionsSchema;
   }
 
   public void setContributionsSchema(Schema schema)
   {
     this._contributionsSchema = schema;
 
     this._canElementsBeMapped = ((this._contributionsSchema != null) && (this._contributionsSchema.canInstancesBeKeyed()));
   }
 
   public Schema getContributionsSchema()
   {
     return this._contributionsSchema;
   }
 
   public void setShutdownCoordinator(ShutdownCoordinator coordinator)
   {
     this._shutdownCoordinator = coordinator;
   }
 }