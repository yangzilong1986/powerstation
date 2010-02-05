 package org.apache.hivemind.util;
 
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.internal.Module;
 
 public class InstanceCreationUtils
 {
   public static Object createInstance(Module module, String initializer, Location location)
   {
     int commax = initializer.indexOf(44);
 
     String className = (commax < 0) ? initializer : initializer.substring(0, commax);
 
     Class objectClass = module.resolveType(className);
     try
     {
       Object result = objectClass.newInstance();
 
       HiveMind.setLocation(result, location);
 
       if (commax > 0) {
         PropertyUtils.configureProperties(result, initializer.substring(commax + 1));
       }
       return result;
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(UtilMessages.unableToInstantiateInstanceOfClass(objectClass, ex), location, ex);
     }
   }
 }