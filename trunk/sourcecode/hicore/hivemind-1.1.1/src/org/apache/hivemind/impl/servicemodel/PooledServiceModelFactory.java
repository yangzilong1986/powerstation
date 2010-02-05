 package org.apache.hivemind.impl.servicemodel;
 
 import org.apache.hivemind.impl.ConstructableServicePoint;
 import org.apache.hivemind.internal.ServiceModel;
 import org.apache.hivemind.internal.ServiceModelFactory;
 
 public class PooledServiceModelFactory
   implements ServiceModelFactory
 {
   public ServiceModel createServiceModelForService(ConstructableServicePoint servicePoint)
   {
     return new PooledServiceModel(servicePoint);
   }
 }