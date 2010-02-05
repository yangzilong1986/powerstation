 package org.apache.hivemind.schema.rules;
 
 import java.util.Locale;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.Locatable;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.Resource;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.schema.Translator;
 
 public class ResourceTranslator
   implements Translator
 {
   public Object translate(Module contributingModule, Class propertyType, String inputValue, Location location)
   {
     if (HiveMind.isBlank(inputValue)) {
       return null;
     }
     Locale locale = contributingModule.getLocale();
 
     Resource descriptor = contributingModule.getLocation().getResource();
 
     Resource baseResource = descriptor.getRelativeResource(inputValue);
 
     Resource result = baseResource.getLocalization(locale);
 
     if (result == null) {
       throw new ApplicationRuntimeException(RulesMessages.resourceLocalizationError(inputValue, contributingModule));
     }
 
     return result;
   }
 }