 package org.apache.hivemind.service;
 
 import org.apache.hivemind.impl.BaseLocatable;
 import org.apache.hivemind.util.ToStringBuilder;
 
 public class MethodContribution extends BaseLocatable
 {
   private String _methodPattern;
   private boolean _include;
 
   public boolean getInclude()
   {
     return this._include;
   }
 
   public String getMethodPattern()
   {
     return this._methodPattern;
   }
 
   public void setInclude(boolean b)
   {
     this._include = b;
   }
 
   public void setMethodPattern(String string)
   {
     this._methodPattern = string;
   }
 
   public String toString()
   {
     ToStringBuilder builder = new ToStringBuilder(this);
 
     builder.append("methodPattern", this._methodPattern);
     builder.append("include", this._include);
 
     return builder.toString();
   }
 }