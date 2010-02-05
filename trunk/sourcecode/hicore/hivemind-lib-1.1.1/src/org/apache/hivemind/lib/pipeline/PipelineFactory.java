 package org.apache.hivemind.lib.pipeline;
 
 import java.util.Iterator;
 import java.util.List;
 import org.apache.hivemind.ErrorLog;
 import org.apache.hivemind.ServiceImplementationFactory;
 import org.apache.hivemind.ServiceImplementationFactoryParameters;
 import org.apache.hivemind.impl.BaseLocatable;
 import org.apache.hivemind.lib.DefaultImplementationBuilder;
 import org.apache.hivemind.service.ClassFactory;
 
 public class PipelineFactory extends BaseLocatable
   implements ServiceImplementationFactory
 {
   private ClassFactory _classFactory;
   private DefaultImplementationBuilder _defaultImplementationBuilder;
   private ErrorLog _errorLog;
 
   public Object createCoreServiceImplementation(ServiceImplementationFactoryParameters factoryParameters)
   {
     PipelineParameters pp = (PipelineParameters)factoryParameters.getParameters().get(0);
 
     PipelineAssembler pa = new PipelineAssembler(this._errorLog, factoryParameters.getServiceId(), factoryParameters.getServiceInterface(), pp.getFilterInterface(), this._classFactory, this._defaultImplementationBuilder);
 
     Object terminator = pp.getTerminator();
 
     if (terminator != null) {
       pa.setTerminator(terminator, pp.getLocation());
     }
     List l = pp.getPipelineConfiguration();
 
     Iterator i = l.iterator();
     while (i.hasNext())
     {
       PipelineContribution c = (PipelineContribution)i.next();
 
       c.informAssembler(pa);
     }
 
     return pa.createPipeline();
   }
 
   public void setClassFactory(ClassFactory factory)
   {
     this._classFactory = factory;
   }
 
   public void setDefaultImplementationBuilder(DefaultImplementationBuilder builder)
   {
     this._defaultImplementationBuilder = builder;
   }
 
   public void setErrorLog(ErrorLog errorLog)
   {
     this._errorLog = errorLog;
   }
 }