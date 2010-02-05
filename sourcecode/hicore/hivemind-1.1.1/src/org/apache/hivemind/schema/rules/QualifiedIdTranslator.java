 package org.apache.hivemind.schema.rules;
 
 import org.apache.hivemind.Location;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.schema.Translator;
 import org.apache.hivemind.util.IdUtils;
 
 public class QualifiedIdTranslator
   implements Translator
 {
   public Object translate(Module contributingModule, Class propertyType, String inputValue, Location location)
   {
     if (inputValue == null) {
       return null;
     }
     return IdUtils.qualify(contributingModule.getModuleId(), inputValue);
   }
 }