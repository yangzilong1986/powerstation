 package org.apache.hivemind.schema.rules;
 
 import java.lang.reflect.Field;
 import java.util.Map;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.schema.Translator;
 
 public class EnumerationTranslator
   implements Translator
 {
   private Map _mappings;
   private String _className;
   private Class _class;
 
   public EnumerationTranslator(String initializer)
   {
     int commax = initializer.indexOf(44);
 
     this._className = initializer.substring(0, commax);
 
     this._mappings = RuleUtils.convertInitializer(initializer.substring(commax + 1));
   }
 
   private synchronized Class getClass(Module contributingModule)
   {
     if (this._class == null) {
       this._class = contributingModule.resolveType(this._className);
     }
     return this._class;
   }
 
   public Object translate(Module contributingModule, Class propertyType, String inputValue, Location location)
   {
     if (HiveMind.isBlank(inputValue)) {
       return null;
     }
     Class c = getClass(contributingModule);
 
     String fieldName = (String)this._mappings.get(inputValue);
 
     if (fieldName == null) {
       throw new ApplicationRuntimeException(RulesMessages.enumNotRecognized(inputValue), location, null);
     }
 
     try
     {
       Field f = c.getField(fieldName);
 
       return f.get(null);
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(RulesMessages.enumError(c, fieldName, ex), location, ex);
     }
   }
 }