 package com.hisun.web.tag;

 import com.hisun.hiexpression.HiExpBasicFunctions;
 import org.apache.commons.beanutils.MethodUtils;
 import org.apache.commons.lang.StringUtils;
 
 public class HiExpression
 {
   public static String expr(String expr, String arg)
     throws Exception
   {
     return expr(expr, new String[] { arg });
   }
 
   public static String expr2(String expr, String arg0, String arg1) throws Exception {
     return expr(expr, new String[] { arg0, arg1 });
   }
 
   public static String expr3(String expr, String arg0, String arg1, String arg2) throws Exception {
     return expr(expr, new String[] { arg0, arg1, arg2 });
   }
 
   public static String expr4(String expr, String arg0, String arg1, String arg2, String arg3) throws Exception {
     return expr(expr, new String[] { arg0, arg1, arg2, arg3 });
   }
 
   public static String expr5(String expr, String arg0, String arg1, String arg2, String arg3, String arg4) throws Exception {
     return expr(expr, new String[] { arg0, arg1, arg2, arg3, arg4 });
   }
 
   public static String expr(String expr, String[] args) throws Exception {
     if (StringUtils.isBlank(args[0])) {
       return args[0];
     }
     Object[] objectArgs = new Object[2];
     objectArgs[0] = new Object();
     objectArgs[1] = args;
     return ((String)MethodUtils.invokeExactMethod(new HiExpBasicFunctions(), expr, objectArgs));
   }
 }