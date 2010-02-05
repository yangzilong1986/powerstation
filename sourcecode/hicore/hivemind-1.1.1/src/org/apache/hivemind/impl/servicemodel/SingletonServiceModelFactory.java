 package org.apache.hivemind.impl.servicemodel;
 
 import org.apache.hivemind.impl.BaseLocatable;
 import org.apache.hivemind.impl.ConstructableServicePoint;
 import org.apache.hivemind.internal.ServiceModel;
 import org.apache.hivemind.internal.ServiceModelFactory;
 
 public class SingletonServiceModelFactory extends BaseLocatable
   implements ServiceModelFactory
 {
   public ServiceModel createServiceModelForService(ConstructableServicePoint servicePoint)
   {
     return new SingletonServiceModel(servicePoint);
   }
 }