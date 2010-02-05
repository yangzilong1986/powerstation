 package org.apache.hivemind.impl;
 
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.apache.hivemind.ErrorLog;
 import org.apache.hivemind.internal.ExtensionPoint;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.internal.Visibility;
 import org.apache.hivemind.util.ToStringBuilder;
 
 public abstract class AbstractExtensionPoint extends BaseLocatable
   implements ExtensionPoint
 {
   private Module _module;
   private String _extensionPointId;
   private Visibility _visibility;
   private ErrorLog _errorLog;
 
   public synchronized String toString()
   {
     ToStringBuilder builder = new ToStringBuilder(this);
     builder.append("extensionPointId", this._extensionPointId);
     builder.append("visibility", this._visibility);
 
     extendDescription(builder);
 
     return builder.toString();
   }
 
   protected abstract void extendDescription(ToStringBuilder paramToStringBuilder);
 
   public void setExtensionPointId(String extensionPointId)
   {
     this._extensionPointId = extensionPointId;
   }
 
   public String getExtensionPointId()
   {
     return this._extensionPointId;
   }
 
   public void setModule(Module module)
   {
     this._module = module;
   }
 
   public Module getModule()
   {
     return this._module;
   }
 
   public void setVisibility(Visibility visibility)
   {
     this._visibility = visibility;
   }
 
   public boolean visibleToModule(Module module)
   {
     if (this._visibility == Visibility.PUBLIC) {
       return true;
     }
     return this._module.equals(module);
   }
 
   public Log getLog()
   {
     return LogFactory.getLog(getExtensionPointId());
   }
 
   public synchronized ErrorLog getErrorLog()
   {
     if (this._errorLog == null) {
       this._errorLog = new ErrorLogImpl(this._module.getErrorHandler(), getLog());
     }
     return this._errorLog;
   }
 }