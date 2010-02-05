 package org.apache.hivemind.service.impl;
 
 import org.apache.commons.logging.Log;
 import org.apache.hivemind.service.ClassFabUtils;
 
 public class LoggingUtils
 {
   private static final int BUFFER_SIZE = 100;
 
   public static void entry(Log log, String methodName, Object[] args)
   {
     StringBuffer buffer = new StringBuffer(100);
 
     buffer.append("BEGIN ");
     buffer.append(methodName);
     buffer.append("(");
 
     int count = (args == null) ? 0 : args.length;
 
     for (int i = 0; i < count; ++i)
     {
       Object arg = args[i];
 
       if (i > 0) {
         buffer.append(", ");
       }
       convert(buffer, arg);
     }
 
     buffer.append(")");
 
     log.debug(buffer.toString());
   }
 
   public static void exit(Log log, String methodName, Object result)
   {
     StringBuffer buffer = new StringBuffer(100);
 
     buffer.append("END ");
     buffer.append(methodName);
     buffer.append("() [");
 
     convert(buffer, result);
 
     buffer.append("]");
 
     log.debug(buffer.toString());
   }
 
   public static void voidExit(Log log, String methodName)
   {
     StringBuffer buffer = new StringBuffer(100);
 
     buffer.append("END ");
     buffer.append(methodName);
     buffer.append("()");
 
     log.debug(buffer.toString());
   }
 
   public static void exception(Log log, String methodName, Throwable t)
   {
     StringBuffer buffer = new StringBuffer(100);
 
     buffer.append("EXCEPTION ");
     buffer.append(methodName);
     buffer.append("() -- ");
 
     buffer.append(t.getClass().getName());
 
     log.debug(buffer.toString(), t);
   }
 
   public static void convert(StringBuffer buffer, Object input)
   {
     if (input == null)
     {
       buffer.append("<null>");
       return;
     }
 
     if (!(input instanceof Object[]))
     {
       buffer.append(input.toString());
       return;
     }
 
     buffer.append("(");
     buffer.append(ClassFabUtils.getJavaClassName(input.getClass()));
     buffer.append("){");
 
     Object[] array = (Object[])(Object[])input;
     int count = array.length;
 
     for (int i = 0; i < count; ++i)
     {
       if (i > 0) {
         buffer.append(", ");
       }
 
       convert(buffer, array[i]);
     }
 
     buffer.append("}");
   }
 }