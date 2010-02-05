 package org.apache.hivemind.parse;
 
 import org.apache.hivemind.util.ToStringBuilder;
 
 public final class ImplementationDescriptor extends AbstractServiceDescriptor
 {
   private String _serviceId;
   private String _conditionalExpression;
 
   public String getServiceId()
   {
     return this._serviceId;
   }
 
   public void setServiceId(String string)
   {
     this._serviceId = string;
   }
 
   protected void extendDescription(ToStringBuilder builder)
   {
     builder.append("serviceId", this._serviceId);
     builder.append("conditionalExpression", this._conditionalExpression);
   }
 
   public String getConditionalExpression()
   {
     return this._conditionalExpression;
   }
 
   public void setConditionalExpression(String conditionalExpression)
   {
     this._conditionalExpression = conditionalExpression;
   }
 }