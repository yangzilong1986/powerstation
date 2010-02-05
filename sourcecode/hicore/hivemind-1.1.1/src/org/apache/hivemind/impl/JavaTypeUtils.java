 package org.apache.hivemind.impl;
 
 import java.util.HashMap;
 import java.util.Map;
 
 public class JavaTypeUtils
 {
   private static Map PRIMITIVE_TYPE_CODES = new HashMap();
   private static final Map PRIMITIVE_CLASSES;
 
   public static String getJVMClassName(String type)
   {
     if (!(type.endsWith("[]"))) {
       return type;
     }
 
     StringBuffer buffer = new StringBuffer();
 
     while (type.endsWith("[]"))
     {
       buffer.append("[");
       type = type.substring(0, type.length() - 2);
     }
 
     String primitiveIdentifier = (String)PRIMITIVE_TYPE_CODES.get(type);
     if (primitiveIdentifier != null) {
       buffer.append(primitiveIdentifier);
     }
     else {
       buffer.append("L");
       buffer.append(type);
       buffer.append(";");
     }
 
     return buffer.toString();
   }
 
   public static Class getPrimtiveClass(String type)
   {
     return ((Class)PRIMITIVE_CLASSES.get(type));
   }
 
   static
   {
     PRIMITIVE_TYPE_CODES.put("boolean", "Z");
     PRIMITIVE_TYPE_CODES.put("short", "S");
     PRIMITIVE_TYPE_CODES.put("int", "I");
     PRIMITIVE_TYPE_CODES.put("long", "J");
     PRIMITIVE_TYPE_CODES.put("float", "F");
     PRIMITIVE_TYPE_CODES.put("double", "D");
     PRIMITIVE_TYPE_CODES.put("char", "C");
     PRIMITIVE_TYPE_CODES.put("byte", "B");
 
     PRIMITIVE_CLASSES = new HashMap();
 
     PRIMITIVE_CLASSES.put("boolean", Boolean.TYPE);
     PRIMITIVE_CLASSES.put("short", Short.TYPE);
     PRIMITIVE_CLASSES.put("char", Character.TYPE);
     PRIMITIVE_CLASSES.put("byte", Byte.TYPE);
     PRIMITIVE_CLASSES.put("int", Integer.TYPE);
     PRIMITIVE_CLASSES.put("long", Long.TYPE);
     PRIMITIVE_CLASSES.put("float", Float.TYPE);
     PRIMITIVE_CLASSES.put("double", Double.TYPE);
   }
 }