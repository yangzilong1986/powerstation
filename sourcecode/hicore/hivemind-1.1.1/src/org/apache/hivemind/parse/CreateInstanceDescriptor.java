 package org.apache.hivemind.parse;
 
 import org.apache.hivemind.impl.BaseLocatable;
 import org.apache.hivemind.impl.CreateClassServiceConstructor;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.internal.ServiceImplementationConstructor;
 import org.apache.hivemind.internal.ServicePoint;
 import org.apache.hivemind.util.ToStringBuilder;
 
 public final class CreateInstanceDescriptor extends BaseLocatable
   implements InstanceBuilder
 {
   private String _serviceModel;
   private String _instanceClassName;
 
   public CreateInstanceDescriptor()
   {
     this._serviceModel = "singleton";
   }
 
   public String getInstanceClassName()
   {
     return this._instanceClassName;
   }
 
   public void setInstanceClassName(String string)
   {
     this._instanceClassName = string;
   }
 
   public ServiceImplementationConstructor createConstructor(ServicePoint point, Module contributingModule)
   {
     CreateClassServiceConstructor result = new CreateClassServiceConstructor();
 
     result.setLocation(super.getLocation());
     result.setContributingModule(contributingModule);
     result.setInstanceClassName(this._instanceClassName);
 
     return result;
   }
 
   public String toString()
   {
     ToStringBuilder builder = new ToStringBuilder(this);
 
     builder.append("instanceClassName", this._instanceClassName);
     builder.append("serviceModel", this._serviceModel);
 
     return builder.toString();
   }
 
   public String getServiceModel()
   {
     return this._serviceModel;
   }
 
   public void setServiceModel(String serviceModel)
   {
     this._serviceModel = serviceModel;
   }
 }