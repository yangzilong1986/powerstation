 package org.apache.hivemind.impl;
 
 import java.util.List;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.ErrorLog;
 import org.apache.hivemind.Occurances;
 import org.apache.hivemind.ServiceImplementationFactory;
 import org.apache.hivemind.ServiceImplementationFactoryParameters;
 import org.apache.hivemind.internal.ExtensionPoint;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.internal.ServiceImplementationConstructor;
 import org.apache.hivemind.internal.ServicePoint;
 import org.apache.hivemind.schema.Schema;
 
 public final class InvokeFactoryServiceConstructor extends BaseLocatable
   implements ServiceImplementationConstructor
 {
   private String _factoryServiceId;
   private ServicePoint _serviceExtensionPoint;
   private Module _contributingModule;
   private List _parameters;
   private ServiceImplementationFactory _factory;
   private List _convertedParameters;
 
   public Object constructCoreServiceImplementation()
   {
     setupFactoryAndParameters();
     try
     {
       ServiceImplementationFactoryParameters factoryParameters = new ServiceImplementationFactoryParametersImpl(this._serviceExtensionPoint, this._contributingModule, this._convertedParameters);
 
       return this._factory.createCoreServiceImplementation(factoryParameters);
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(ex.getMessage(), super.getLocation(), ex);
     }
   }
 
   private synchronized void setupFactoryAndParameters()
   {
     if (this._factory != null)
       return;
     ServicePoint factoryPoint = this._contributingModule.getServicePoint(this._factoryServiceId);
 
     Occurances expected = factoryPoint.getParametersCount();
 
     this._factory = ((ServiceImplementationFactory)factoryPoint.getService(ServiceImplementationFactory.class));
 
     Schema schema = factoryPoint.getParametersSchema();
 
     ErrorLog errorLog = this._serviceExtensionPoint.getErrorLog();
 
     SchemaProcessorImpl processor = new SchemaProcessorImpl(errorLog, schema);
 
     processor.process(this._parameters, this._contributingModule);
 
     this._convertedParameters = processor.getElements();
 
     checkParameterCounts(errorLog, expected);
   }
 
   private void checkParameterCounts(ErrorLog log, Occurances expected)
   {
     int actual = this._convertedParameters.size();
 
     if (expected.inRange(actual)) {
       return;
     }
     String message = ImplMessages.wrongNumberOfParameters(this._factoryServiceId, actual, expected);
 
     log.error(message, super.getLocation(), null);
   }
 
   public Module getContributingModule()
   {
     return this._contributingModule;
   }
 
   public void setContributingModule(Module module)
   {
     this._contributingModule = module;
   }
 
   public List getParameters()
   {
     return this._parameters;
   }
 
   public ServicePoint getServiceExtensionPoint()
   {
     return this._serviceExtensionPoint;
   }
 
   public void setParameters(List list)
   {
     this._parameters = list;
   }
 
   public void setFactoryServiceId(String string)
   {
     this._factoryServiceId = string;
   }
 
   public void setServiceExtensionPoint(ServicePoint point)
   {
     this._serviceExtensionPoint = point;
   }
 }