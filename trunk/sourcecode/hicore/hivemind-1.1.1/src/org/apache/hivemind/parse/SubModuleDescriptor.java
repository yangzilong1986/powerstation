 package org.apache.hivemind.parse;
 
 import org.apache.hivemind.Resource;
 import org.apache.hivemind.impl.BaseLocatable;
 import org.apache.hivemind.util.ToStringBuilder;
 
 public final class SubModuleDescriptor extends BaseLocatable
 {
   private Resource _descriptor;
 
   public Resource getDescriptor()
   {
     return this._descriptor;
   }
 
   public void setDescriptor(Resource descriptor)
   {
     this._descriptor = descriptor;
   }
 
   public String toString()
   {
     ToStringBuilder builder = new ToStringBuilder(this);
     builder.append("descriptor", this._descriptor);
 
     return builder.toString();
   }
 }