 package org.apache.hivemind.schema.rules;
 
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.schema.Translator;
 
 public class ServiceTranslator
   implements Translator
 {
   public Object translate(Module contributingModule, Class propertyType, String inputValue, Location location)
   {
     if (HiveMind.isBlank(inputValue)) {
       return null;
     }
     return contributingModule.getService(inputValue, Object.class);
   }
 }