 package org.apache.hivemind.schema.rules;
 
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.schema.Translator;
 import org.apache.hivemind.util.InstanceCreationUtils;
 
 public class InstanceTranslator
   implements Translator
 {
   public Object translate(Module contributingModule, Class propertyType, String inputValue, Location location)
   {
     if (HiveMind.isBlank(inputValue)) {
       return null;
     }
     return InstanceCreationUtils.createInstance(contributingModule, inputValue, location);
   }
 }