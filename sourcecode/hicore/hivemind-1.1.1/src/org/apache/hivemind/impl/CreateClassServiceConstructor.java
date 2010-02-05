 package org.apache.hivemind.impl;
 
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.internal.ServiceImplementationConstructor;
 import org.apache.hivemind.util.InstanceCreationUtils;
 
 public final class CreateClassServiceConstructor extends BaseLocatable
   implements ServiceImplementationConstructor
 {
   private Module _contributingModule;
   private String _instanceClassName;
 
   public Object constructCoreServiceImplementation()
   {
     return InstanceCreationUtils.createInstance(this._contributingModule, this._instanceClassName, super.getLocation());
   }
 
   public Module getContributingModule()
   {
     return this._contributingModule;
   }
 
   public String getInstanceClassName()
   {
     return this._instanceClassName;
   }
 
   public void setContributingModule(Module module)
   {
     this._contributingModule = module;
   }
 
   public void setInstanceClassName(String string)
   {
     this._instanceClassName = string;
   }
 }