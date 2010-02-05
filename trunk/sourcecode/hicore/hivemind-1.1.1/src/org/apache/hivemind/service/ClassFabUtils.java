 package org.apache.hivemind.service;
 
 import java.lang.reflect.Method;
 import java.lang.reflect.Proxy;
 
 public class ClassFabUtils
 {
   private static long _uid = System.currentTimeMillis();
   private static final char QUOTE = 34;
 
   public static synchronized String generateClassName(String baseName)
   {
     return "$" + baseName + "_" + Long.toHexString(_uid++);
   }
 
   public static synchronized String generateClassName(Class interfaceClass)
   {
     String name = interfaceClass.getName();
 
     int dotx = name.lastIndexOf(46);
 
     return generateClassName(name.substring(dotx + 1));
   }
 
   public static String getJavaClassName(Class inputClass)
   {
     if (inputClass.isArray()) {
       return getJavaClassName(inputClass.getComponentType()) + "[]";
     }
     return inputClass.getName();
   }
 
   public static boolean isToString(Method method)
   {
     if (!(method.getName().equals("toString"))) {
       return false;
     }
     if (method.getParameterTypes().length > 0) {
       return false;
     }
     return method.getReturnType().equals(String.class);
   }
 
   public static void addToStringMethod(ClassFab classFab, String toStringResult)
   {
     StringBuffer buffer = new StringBuffer("return ");
     buffer.append('"');
     buffer.append(toStringResult);
     buffer.append('"');
     buffer.append(";");
 
     classFab.addMethod(1, new MethodSignature(String.class, "toString", null, null), buffer.toString());
   }
 
   public static Class getInstanceClass(Object instance, Class interfaceClass)
   {
     Class instanceClass = instance.getClass();
 
     if (Proxy.isProxyClass(instanceClass)) {
       return interfaceClass;
     }
     return instanceClass;
   }
 
   public static void addNoOpMethod(ClassFab cf, MethodSignature m)
   {
     StringBuffer body = new StringBuffer("{ ");
 
     Class returnType = m.getReturnType();
 
     if (returnType != Void.TYPE)
     {
       body.append("return");
 
       if (returnType.isPrimitive())
       {
         if (returnType == Boolean.TYPE)
           body.append(" false");
         else if (returnType == Long.TYPE)
           body.append(" 0L");
         else if (returnType == Float.TYPE)
           body.append(" 0.0f");
         else if (returnType == Double.TYPE)
           body.append(" 0.0d");
         else {
           body.append(" 0");
         }
       }
       else {
         body.append(" null");
       }
 
       body.append(";");
     }
 
     body.append(" }");
 
     cf.addMethod(1, m, body.toString());
   }
 }