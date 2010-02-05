 package org.apache.hivemind.service.impl;
 
 import org.apache.hivemind.ServiceImplementationFactory;
 import org.apache.hivemind.ServiceImplementationFactoryParameters;
 
 public class BuilderFactory
   implements ServiceImplementationFactory
 {
   public Object createCoreServiceImplementation(ServiceImplementationFactoryParameters factoryParameters)
   {
     BuilderParameter parameter = (BuilderParameter)factoryParameters.getFirstParameter();
 
     BuilderFactoryLogic logic = new BuilderFactoryLogic(factoryParameters, parameter);
 
     return logic.createService();
   }
 }