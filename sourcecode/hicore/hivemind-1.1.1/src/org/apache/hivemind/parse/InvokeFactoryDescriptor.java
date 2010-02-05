 package org.apache.hivemind.parse;
 
 import org.apache.hivemind.impl.BaseLocatable;
 import org.apache.hivemind.impl.InvokeFactoryServiceConstructor;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.internal.ServiceImplementationConstructor;
 import org.apache.hivemind.internal.ServicePoint;
 import org.apache.hivemind.util.ToStringBuilder;
 
 public final class InvokeFactoryDescriptor extends AbstractServiceInvocationDescriptor
   implements InstanceBuilder
 {
   private String _serviceModel;
 
   public ServiceImplementationConstructor createConstructor(ServicePoint point, Module contributingModule)
   {
     InvokeFactoryServiceConstructor result = new InvokeFactoryServiceConstructor();
 
     result.setLocation(super.getLocation());
     result.setContributingModule(contributingModule);
     result.setParameters(super.getParameters());
     result.setFactoryServiceId(super.getFactoryServiceId());
     result.setServiceExtensionPoint(point);
 
     return result;
   }
 
   public String getServiceModel()
   {
     return this._serviceModel;
   }
 
   public void setServiceModel(String serviceModel)
   {
     this._serviceModel = serviceModel;
   }
 
   protected void extendDescription(ToStringBuilder builder)
   {
     builder.append("serviceModel", this._serviceModel);
   }
 }