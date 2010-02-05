 package org.apache.hivemind.parse;
 
 import org.apache.hivemind.impl.BaseLocatable;
 import org.apache.hivemind.util.ToStringBuilder;
 
 public final class DependencyDescriptor extends BaseLocatable
 {
   private String _moduleId;
   private String _version;
 
   public String getModuleId()
   {
     return this._moduleId;
   }
 
   public void setModuleId(String moduleId)
   {
     this._moduleId = moduleId;
   }
 
   public String getVersion()
   {
     return this._version;
   }
 
   public void setVersion(String version)
   {
     this._version = version;
   }
 
   public String toString()
   {
     ToStringBuilder builder = new ToStringBuilder(this);
     builder.append("moduleId", this._moduleId);
     builder.append("version", this._version);
 
     return builder.toString();
   }
 }