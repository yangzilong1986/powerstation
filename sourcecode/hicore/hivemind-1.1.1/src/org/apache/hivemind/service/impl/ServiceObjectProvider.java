 package org.apache.hivemind.service.impl;
 
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.service.ObjectProvider;
 
 public class ServiceObjectProvider
   implements ObjectProvider
 {
   public Object provideObject(Module contributingModule, Class propertyType, String locator, Location location)
   {
     if (HiveMind.isBlank(locator)) {
       return null;
     }
     return contributingModule.getService(locator, Object.class);
   }
 }