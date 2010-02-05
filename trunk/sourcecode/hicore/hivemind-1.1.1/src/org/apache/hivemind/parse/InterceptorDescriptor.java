 package org.apache.hivemind.parse;
 
 import org.apache.hivemind.util.ToStringBuilder;
 
 public final class InterceptorDescriptor extends AbstractServiceInvocationDescriptor
 {
   private String _before;
   private String _after;
   private String _name;
 
   public String getAfter()
   {
     return this._after;
   }
 
   public String getBefore()
   {
     return this._before;
   }
 
   public void setAfter(String string)
   {
     this._after = string;
   }
 
   public void setBefore(String string)
   {
     this._before = string;
   }
 
   public String getName()
   {
     return this._name;
   }
 
   public void setName(String name)
   {
     this._name = name;
   }
 
   protected void extendDescription(ToStringBuilder builder)
   {
     builder.append("before", this._before);
     builder.append("after", this._after);
     builder.append("name", this._name);
   }
 }