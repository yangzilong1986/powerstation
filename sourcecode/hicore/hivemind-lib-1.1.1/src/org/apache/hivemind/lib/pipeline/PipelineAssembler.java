 package org.apache.hivemind.lib.pipeline;
 
 import java.util.List;
 import org.apache.hivemind.ErrorLog;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.impl.BaseLocatable;
 import org.apache.hivemind.lib.DefaultImplementationBuilder;
 import org.apache.hivemind.order.Orderer;
 import org.apache.hivemind.service.ClassFactory;
 
 public class PipelineAssembler extends BaseLocatable
 {
   private ErrorLog _errorLog;
   private String _serviceId;
   private Class _serviceInterface;
   private Class _filterInterface;
   private ClassFactory _classFactory;
   private DefaultImplementationBuilder _defaultBuilder;
   private Orderer _orderer;
   private Object _terminator;
   private Location _terminatorLocation;
 
   public PipelineAssembler(ErrorLog errorLog, String serviceId, Class serviceInterface, Class filterInterface, ClassFactory classFactory, DefaultImplementationBuilder defaultBuilder)
   {
     this._errorLog = errorLog;
     this._serviceId = serviceId;
     this._serviceInterface = serviceInterface;
     this._filterInterface = filterInterface;
     this._classFactory = classFactory;
     this._defaultBuilder = defaultBuilder;
 
     this._orderer = new Orderer(this._errorLog, "filter");
   }
 
   public void addFilter(String name, String prereqs, String postreqs, Object filter, Location location)
   {
     if (!(checkInterface(this._filterInterface, filter, location))) {
       return;
     }
     FilterHolder holder = new FilterHolder(filter, location);
 
     this._orderer.add(holder, name, prereqs, postreqs);
   }
 
   public void setTerminator(Object terminator, Location terminatorLocation)
   {
     if (this._terminator != null)
     {
       this._errorLog.error(PipelineMessages.duplicateTerminator(terminator, this._serviceId, this._terminator, this._terminatorLocation), terminatorLocation, null);
 
       return;
     }
 
     if (!(checkInterface(this._serviceInterface, terminator, terminatorLocation))) {
       return;
     }
     this._terminator = terminator;
     this._terminatorLocation = terminatorLocation;
   }
 
   Object getTerminator()
   {
     return this._terminator;
   }
 
   private boolean checkInterface(Class interfaceType, Object instance, Location location)
   {
     if (interfaceType.isAssignableFrom(instance.getClass())) {
       return true;
     }
     this._errorLog.error(PipelineMessages.incorrectInterface(instance, interfaceType, this._serviceId), location, null);
 
     return false;
   }
 
   public Object createPipeline()
   {
     List filterHolders = this._orderer.getOrderedObjects();
     int count = filterHolders.size();
 
     BridgeBuilder bb = new BridgeBuilder(this._errorLog, this._serviceId, this._serviceInterface, this._filterInterface, this._classFactory);
 
     Object next = (this._terminator != null) ? this._terminator : this._defaultBuilder.buildDefaultImplementation(this._serviceInterface);
 
     for (int i = count - 1; i >= 0; --i)
     {
       FilterHolder h = (FilterHolder)filterHolders.get(i);
       Object filter = h.getFilter();
 
       next = bb.instantiateBridge(next, filter);
     }
 
     return next;
   }
 }