 package org.apache.hivemind.parse;
 
 import java.util.ArrayList;
 import java.util.List;
 import org.apache.hivemind.util.ToStringBuilder;
 
 public abstract class AbstractServiceDescriptor extends BaseAnnotationHolder
 {
   private InstanceBuilder _instanceBuilder;
   private List _interceptors;
 
   public String toString()
   {
     ToStringBuilder builder = new ToStringBuilder(this);
 
     extendDescription(builder);
 
     builder.append("instanceBuilder", this._instanceBuilder);
     builder.append("interceptors", this._interceptors);
 
     return builder.toString();
   }
 
   protected abstract void extendDescription(ToStringBuilder paramToStringBuilder);
 
   public InstanceBuilder getInstanceBuilder()
   {
     return this._instanceBuilder;
   }
 
   public void setInstanceBuilder(InstanceBuilder descriptor)
   {
     this._instanceBuilder = descriptor;
   }
 
   public void addInterceptor(InterceptorDescriptor interceptor)
   {
     if (this._interceptors == null) {
       this._interceptors = new ArrayList();
     }
     this._interceptors.add(interceptor);
   }
 
   public List getInterceptors()
   {
     return this._interceptors;
   }
 }