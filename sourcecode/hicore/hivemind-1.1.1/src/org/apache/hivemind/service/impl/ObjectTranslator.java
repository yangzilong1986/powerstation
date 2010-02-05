 package org.apache.hivemind.service.impl;
 
 import java.util.HashMap;
 import java.util.Map;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.ErrorLog;
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.schema.Translator;
 import org.apache.hivemind.service.ObjectProvider;
 
 public class ObjectTranslator
   implements Translator
 {
   private ErrorLog _errorLog;
   private Map _providers;
 
   public ObjectTranslator()
   {
     this._providers = new HashMap();
   }
 
   public Object translate(Module contributingModule, Class propertyType, String inputValue, Location location)
   {
     if (HiveMind.isBlank(inputValue)) {
       return null;
     }
     int colonx = inputValue.indexOf(58);
 
     if (colonx < 1)
     {
       this._errorLog.error(ServiceMessages.invalidProviderSelector(inputValue), null, null);
 
       return null;
     }
 
     String prefix = inputValue.substring(0, colonx);
 
     ObjectProvider provider = (ObjectProvider)this._providers.get(prefix);
 
     if (provider == null)
     {
       this._errorLog.error(ServiceMessages.unknownProviderPrefix(prefix), location, null);
 
       return null;
     }
 
     String locator = inputValue.substring(colonx + 1);
     try
     {
       return provider.provideObject(contributingModule, propertyType, locator, location);
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(ex.getMessage(), location, ex);
     }
   }
 
   public void setContributions(Map map)
   {
     this._providers = map;
   }
 
   public void setErrorLog(ErrorLog errorLog)
   {
     this._errorLog = errorLog;
   }
 }