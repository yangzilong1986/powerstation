 package org.apache.hivemind.lib.factory;
 
 import java.util.List;
 import org.apache.hivemind.ServiceImplementationFactory;
 import org.apache.hivemind.ServiceImplementationFactoryParameters;
 import org.apache.hivemind.impl.BaseLocatable;
 
 public class BeanFactoryBuilder extends BaseLocatable
   implements ServiceImplementationFactory
 {
   public Object createCoreServiceImplementation(ServiceImplementationFactoryParameters factoryParameters)
   {
     BeanFactoryParameter p = (BeanFactoryParameter)factoryParameters.getParameters().get(0);
 
     return new BeanFactoryImpl(factoryParameters.getErrorLog(), p.getVendClass(), p.getContributions(), p.getDefaultCacheable());
   }
 }