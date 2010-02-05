 package org.apache.hivemind.lib.impl;
 
 import org.apache.hivemind.ServiceImplementationFactory;
 import org.apache.hivemind.ServiceImplementationFactoryParameters;
 import org.apache.hivemind.impl.BaseLocatable;
 import org.apache.hivemind.lib.DefaultImplementationBuilder;
 
 public class PlaceholderFactory extends BaseLocatable
   implements ServiceImplementationFactory
 {
   private DefaultImplementationBuilder _builder;
 
   public Object createCoreServiceImplementation(ServiceImplementationFactoryParameters factoryParameters)
   {
     return this._builder.buildDefaultImplementation(factoryParameters.getServiceInterface());
   }
 
   public void setBuilder(DefaultImplementationBuilder builder)
   {
     this._builder = builder;
   }
 }