 package org.apache.hivemind.service.impl;
 
 import org.apache.hivemind.Location;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.service.ObjectProvider;
 import org.apache.hivemind.util.InstanceCreationUtils;
 
 public class ObjectInstanceObjectProvider
   implements ObjectProvider
 {
   public Object provideObject(Module contributingModule, Class propertyType, String locator, Location location)
   {
     return InstanceCreationUtils.createInstance(contributingModule, locator, location);
   }
 }