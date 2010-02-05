 package org.apache.hivemind.service.impl;
 
 import java.util.Map;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.service.ObjectProvider;
 
 public class ConfigurationObjectProvider
   implements ObjectProvider
 {
   public Object provideObject(Module contributingModule, Class propertyType, String locator, Location location)
   {
     if ((propertyType == Map.class) && (contributingModule.isConfigurationMappable(locator))) {
       return contributingModule.getConfigurationAsMap(locator);
     }
     return contributingModule.getConfiguration(locator);
   }
 }