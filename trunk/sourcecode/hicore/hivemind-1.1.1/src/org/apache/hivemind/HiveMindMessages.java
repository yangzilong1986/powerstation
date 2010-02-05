 package org.apache.hivemind;
 
 import org.apache.hivemind.impl.AbstractMessages;
 import org.apache.hivemind.impl.MessageFormatter;
 import org.apache.hivemind.service.ClassFabUtils;
 
 public class HiveMindMessages
 {
   protected static MessageFormatter _formatter = new MessageFormatter(HiveMindMessages.class);
 
   public static String unimplementedMethod(Object instance, String methodName)
   {
     return _formatter.format("unimplemented-method", instance.getClass().getName(), methodName);
   }
 
   public static String registryShutdown()
   {
     return _formatter.getMessage("registry-shutdown");
   }
 
   public static String unknownLocation()
   {
     return _formatter.getMessage("unknown-location");
   }
 
   public static String paramNotNull(String parameterName)
   {
     return _formatter.format("param-not-null", parameterName);
   }
 
   public static String paramWrongType(String parameterName, Object parameter, Class expectedType)
   {
     return _formatter.format("param-wrong-type", parameterName, ClassFabUtils.getJavaClassName(parameter.getClass()), ClassFabUtils.getJavaClassName(expectedType));
   }
 }