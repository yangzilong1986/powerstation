 package org.apache.hivemind.service.impl;
 
 import org.apache.hivemind.Location;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.service.ObjectProvider;
 
 public class ClassObjectProvider
   implements ObjectProvider
 {
   public Object provideObject(Module contributingModule, Class propertyType, String locator, Location location)
   {
     return contributingModule.resolveType(locator);
   }
 }