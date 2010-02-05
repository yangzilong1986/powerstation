 package org.apache.hivemind.service.impl;
 
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.service.ObjectProvider;
 import org.apache.hivemind.util.PropertyUtils;
 
 public class ServicePropertyObjectProvider
   implements ObjectProvider
 {
   public Object provideObject(Module contributingModule, Class propertyType, String locator, Location location)
   {
     int commax = locator.indexOf(58);
 
     if (commax < 2)
     {
       throw new ApplicationRuntimeException(ServiceMessages.invalidServicePropertyLocator(locator), location, null);
     }
 
     String serviceId = locator.substring(0, commax);
     String propertyName = locator.substring(commax + 1);
 
     Object service = contributingModule.getService(serviceId, Object.class);
 
     return PropertyUtils.read(service, propertyName);
   }
 }