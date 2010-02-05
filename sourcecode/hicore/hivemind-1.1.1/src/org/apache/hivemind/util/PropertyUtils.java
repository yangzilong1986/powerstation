 package org.apache.hivemind.util;
 
 import java.beans.BeanInfo;
 import java.beans.Introspector;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.HiveMind;
 
 public class PropertyUtils
 {
   private static final Map _classAdaptors = new HashMap();
 
   public static void write(Object target, String propertyName, Object value)
   {
     ClassAdaptor a = getAdaptor(target);
 
     a.write(target, propertyName, value);
   }
 
   public static void smartWrite(Object target, String propertyName, String value)
   {
     ClassAdaptor a = getAdaptor(target);
 
     a.smartWrite(target, propertyName, value);
   }
 
   public static void configureProperties(Object target, String initializer)
   {
     ClassAdaptor a = getAdaptor(target);
 
     a.configureProperties(target, initializer);
   }
 
   public static boolean isWritable(Object target, String propertyName)
   {
     return getAdaptor(target).isWritable(propertyName);
   }
 
   public static boolean isReadable(Object target, String propertyName)
   {
     return getAdaptor(target).isReadable(propertyName);
   }
 
   public static Object read(Object target, String propertyName)
   {
     ClassAdaptor a = getAdaptor(target);
 
     return a.read(target, propertyName);
   }
 
   public static Class getPropertyType(Object target, String propertyName)
   {
     ClassAdaptor a = getAdaptor(target);
 
     return a.getPropertyType(target, propertyName);
   }
 
   public static PropertyAdaptor getPropertyAdaptor(Object target, String propertyName)
   {
     ClassAdaptor a = getAdaptor(target);
 
     return a.getPropertyAdaptor(target, propertyName);
   }
 
   public static List getReadableProperties(Object target)
   {
     return getAdaptor(target).getReadableProperties();
   }
 
   public static List getWriteableProperties(Object target)
   {
     return getAdaptor(target).getWriteableProperties();
   }
 
   private static ClassAdaptor getAdaptor(Object target)
   {
     if (target == null) {
       throw new ApplicationRuntimeException(UtilMessages.nullObject());
     }
     Class targetClass = target.getClass();
 
     synchronized (HiveMind.INTROSPECTOR_MUTEX)
     {
       ClassAdaptor result = (ClassAdaptor)_classAdaptors.get(targetClass);
 
       if (result == null)
       {
         result = buildClassAdaptor(target, targetClass);
         _classAdaptors.put(targetClass, result);
       }
 
       return result;
     }
   }
 
   private static ClassAdaptor buildClassAdaptor(Object target, Class targetClass)
   {
     try
     {
       BeanInfo info = Introspector.getBeanInfo(targetClass);
 
       return new ClassAdaptor(info.getPropertyDescriptors());
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(UtilMessages.unableToIntrospect(targetClass, ex), target, null, ex);
     }
   }
 
   public static void clearCache()
   {
     synchronized (HiveMind.INTROSPECTOR_MUTEX)
     {
       _classAdaptors.clear();
       Introspector.flushCaches();
     }
   }
 }