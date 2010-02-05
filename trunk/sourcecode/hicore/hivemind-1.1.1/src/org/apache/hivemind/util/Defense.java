 package org.apache.hivemind.util;
 
 import org.apache.hivemind.HiveMindMessages;
 
 public final class Defense
 {
   public static void notNull(Object parameter, String parameterName)
   {
     if (parameter == null)
       throw new NullPointerException(HiveMindMessages.paramNotNull(parameterName));
   }
 
   public static void isAssignable(Object parameter, Class expectedType, String parameterName)
   {
     notNull(parameter, parameterName);
 
     if (!(expectedType.isAssignableFrom(parameter.getClass())))
       throw new ClassCastException(HiveMindMessages.paramWrongType(parameterName, parameter, expectedType));
   }
 }