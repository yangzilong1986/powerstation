 package org.apache.hivemind.parse;
 
 import java.util.ArrayList;
 import java.util.List;
 import org.apache.hivemind.Element;
 import org.apache.hivemind.impl.BaseLocatable;
 import org.apache.hivemind.util.ToStringBuilder;
 
 public abstract class AbstractServiceInvocationDescriptor extends BaseLocatable
 {
   private String _factoryServiceId;
   private List _parameters;
 
   public void addParameter(Element parameter)
   {
     if (this._parameters == null) {
       this._parameters = new ArrayList();
     }
     this._parameters.add(parameter);
   }
 
   public List getParameters()
   {
     return this._parameters;
   }
 
   public String getFactoryServiceId()
   {
     return this._factoryServiceId;
   }
 
   public void setFactoryServiceId(String string)
   {
     this._factoryServiceId = string;
   }
 
   public String toString()
   {
     ToStringBuilder builder = new ToStringBuilder(this);
 
     builder.append("factoryServiceId", this._factoryServiceId);
     builder.append("parameters", this._parameters);
 
     extendDescription(builder);
 
     return builder.toString();
   }
 
   protected void extendDescription(ToStringBuilder builder)
   {
   }
 }