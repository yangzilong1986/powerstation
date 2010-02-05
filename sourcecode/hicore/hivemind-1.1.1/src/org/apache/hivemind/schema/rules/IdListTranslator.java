 package org.apache.hivemind.schema.rules;
 
 import org.apache.hivemind.Location;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.schema.Translator;
 import org.apache.hivemind.util.IdUtils;
 
 public class IdListTranslator
   implements Translator
 {
   public Object translate(Module contributingModule, Class propertyType, String inputValue, Location location)
   {
     if (inputValue == null) {
       return null;
     }
     return IdUtils.qualifyList(contributingModule.getModuleId(), inputValue);
   }
 }