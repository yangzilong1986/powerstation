 package org.apache.hivemind.schema.rules;
 
 import org.apache.hivemind.Location;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.schema.Translator;
 
 public class ServicePointTranslator
   implements Translator
 {
   public Object translate(Module contributingModule, Class propertyType, String inputValue, Location location)
   {
     return contributingModule.getServicePoint(inputValue);
   }
 }