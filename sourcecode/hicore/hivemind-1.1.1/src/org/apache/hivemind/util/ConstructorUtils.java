 package org.apache.hivemind.util;
 
 import java.lang.reflect.Constructor;
 import java.lang.reflect.InvocationTargetException;
 import java.lang.reflect.Modifier;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.apache.hivemind.ApplicationRuntimeException;
 
 public class ConstructorUtils
 {
   private static final Map _primitiveMap = new HashMap();
 
   public static Object invokeConstructor(Class targetClass, Object[] parameters)
   {
     if (parameters == null) {
       parameters = new Object[0];
     }
     Class[] parameterTypes = new Class[parameters.length];
 
     for (int i = 0; i < parameters.length; ++i) {
       parameterTypes[i] = ((parameters[i] == null) ? null : parameters[i].getClass());
     }
     return invokeMatchingConstructor(targetClass, parameterTypes, parameters);
   }
 
   private static Object invokeMatchingConstructor(Class targetClass, Class[] parameterTypes, Object[] parameters)
   {
     Constructor[] constructors = targetClass.getConstructors();
 
     for (int i = 0; i < constructors.length; ++i)
     {
       Constructor c = constructors[i];
 
       if (isMatch(c, parameterTypes)) {
         return invoke(c, parameters);
       }
     }
     throw new ApplicationRuntimeException(UtilMessages.noMatchingConstructor(targetClass), null);
   }
 
   private static boolean isMatch(Constructor c, Class[] types)
   {
     Class[] actualTypes = c.getParameterTypes();
 
     if (actualTypes.length != types.length) {
       return false;
     }
     for (int i = 0; i < types.length; ++i)
     {
       if ((types[i] == null) && (!(actualTypes[i].isPrimitive()))) {
         continue;
       }
       if (!(isCompatible(actualTypes[i], types[i]))) {
         return false;
       }
     }
     return true;
   }
 
   public static boolean isCompatible(Class actualType, Class parameterType)
   {
     if (actualType.isAssignableFrom(parameterType)) {
       return true;
     }
 
     if (actualType.isPrimitive())
     {
       Class wrapperClass = (Class)_primitiveMap.get(actualType);
 
       return wrapperClass.isAssignableFrom(parameterType);
     }
 
     return false;
   }
 
   public static Object invoke(Constructor c, Object[] parameters)
   {
     try
     {
       return c.newInstance(parameters);
     }
     catch (InvocationTargetException ex)
     {
       Throwable cause = ex.getTargetException();
 
       throw new ApplicationRuntimeException(UtilMessages.invokeFailed(c, cause), null, cause);
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(UtilMessages.invokeFailed(c, ex), null, ex);
     }
   }
 
   public static List getConstructorsOfLength(Class clazz, int length)
   {
     List fixedLengthConstructors = new ArrayList(1);
 
     Constructor[] constructors = clazz.getDeclaredConstructors();
 
     for (int i = 0; i < constructors.length; ++i)
     {
       if (!(Modifier.isPublic(constructors[i].getModifiers()))) {
         continue;
       }
       Class[] parameterTypes = constructors[i].getParameterTypes();
 
       if (parameterTypes.length == length) {
         fixedLengthConstructors.add(constructors[i]);
       }
     }
     return fixedLengthConstructors;
   }
 
   static
   {
     _primitiveMap.put(Boolean.TYPE, Boolean.class);
     _primitiveMap.put(Byte.TYPE, Byte.class);
     _primitiveMap.put(Character.TYPE, Character.class);
     _primitiveMap.put(Short.TYPE, Short.class);
     _primitiveMap.put(Integer.TYPE, Integer.class);
     _primitiveMap.put(Long.TYPE, Long.class);
     _primitiveMap.put(Float.TYPE, Float.class);
     _primitiveMap.put(Double.TYPE, Double.class);
   }
 }