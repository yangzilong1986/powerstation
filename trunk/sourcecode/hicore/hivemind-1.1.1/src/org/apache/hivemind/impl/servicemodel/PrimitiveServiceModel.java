 package org.apache.hivemind.impl.servicemodel;
 
 import org.apache.hivemind.impl.ConstructableServicePoint;
 
 public final class PrimitiveServiceModel extends AbstractServiceModelImpl
 {
   private Object _constructedService;
 
   public PrimitiveServiceModel(ConstructableServicePoint servicePoint)
   {
     super(servicePoint);
   }
 
   public synchronized Object getService()
   {
     if (this._constructedService == null)
     {
       this._constructedService = super.constructServiceImplementation();
 
       super.registerWithShutdownCoordinator(this._constructedService);
     }
 
     return this._constructedService;
   }
 
   public void instantiateService()
   {
     getService();
   }
 }