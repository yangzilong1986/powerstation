 package org.apache.hivemind.schema.rules;
 
 import java.util.Map;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.schema.Translator;
 
 public class BooleanTranslator
   implements Translator
 {
   private Boolean _defaultValue = Boolean.FALSE;
 
   public BooleanTranslator()
   {
   }
 
   public BooleanTranslator(String initializer)
   {
     Map m = RuleUtils.convertInitializer(initializer);
 
     String defaultInit = (String)m.get("default");
 
     if (defaultInit != null)
       this._defaultValue = Boolean.valueOf(defaultInit);
   }
 
   public Object translate(Module contributingModule, Class propertyType, String inputValue, Location location)
   {
     if (HiveMind.isBlank(inputValue)) {
       return this._defaultValue;
     }
     if (inputValue.equals("true")) {
       return Boolean.TRUE;
     }
     if (inputValue.equals("false")) {
       return Boolean.FALSE;
     }
     throw new ApplicationRuntimeException(RulesMessages.invalidBooleanValue(inputValue), location, null);
   }
 }