 package org.apache.hivemind.schema.rules;
 
 import java.beans.PropertyEditor;
 import java.beans.PropertyEditorManager;
 import java.util.Map;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.schema.Translator;
 
 public class SmartTranslator
   implements Translator
 {
   private String _default;
 
   public SmartTranslator()
   {
   }
 
   public SmartTranslator(String initializer)
   {
     Map m = RuleUtils.convertInitializer(initializer);
 
     this._default = ((String)m.get("default"));
   }
 
   public Object translate(Module contributingModule, Class propertyType, String inputValue, Location location)
   {
     if (inputValue == null)
     {
       if (this._default == null) {
         return null;
       }
       inputValue = this._default;
     }
 
     if ((propertyType.equals(String.class)) || (propertyType.equals(Object.class))) {
       return inputValue;
     }
 
     try
     {
       PropertyEditor e = PropertyEditorManager.findEditor(propertyType);
 
       if (e == null) {
         throw new ApplicationRuntimeException(RulesMessages.noPropertyEditor(propertyType), location, null);
       }
 
       e.setAsText(inputValue);
 
       return e.getValue();
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(RulesMessages.smartTranslatorError(inputValue, propertyType, ex), location, ex);
     }
   }
 }