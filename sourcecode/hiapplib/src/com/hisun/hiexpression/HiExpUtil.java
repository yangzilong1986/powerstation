/*    */ package com.hisun.hiexpression;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ public class HiExpUtil
/*    */ {
/*    */   private static String[] getArguments(Object[] values)
/*    */   {
/* 10 */     if ((values == null) || (values.length == 0)) {
/* 11 */       return null;
/*    */     }
/* 13 */     String[] args = new String[values.length];
/* 14 */     for (int i = 0; i < values.length; ++i) {
/* 15 */       args[i] = ((String)values[i]);
/*    */     }
/*    */ 
/* 18 */     return args;
/*    */   }
/*    */ 
/*    */   public static Object invokeStaticMethod(Method thisMethod, Object data, Object[] args) throws Exception
/*    */   {
/*    */     try {
/* 24 */       String[] rargs = getArguments(args);
/* 25 */       if (rargs != null)
/*    */       {
/* 27 */         aa = new Object[] { data, rargs };
/* 28 */         returnValue = thisMethod.invoke(null, aa);
/* 29 */         return returnValue;
/*    */       }
/* 31 */       Object[] aa = { data };
/* 32 */       Object returnValue = thisMethod.invoke(null, aa);
/* 33 */       return returnValue;
/*    */     }
/*    */     catch (InvocationTargetException e) {
/* 36 */       throw HiException.makeException(e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public static Method getStaticMethod(String className, String staticMethodName, int nargs)
/*    */     throws Exception
/*    */   {
/* 43 */     return HiExpMethodSource.getMethod(staticMethodName, nargs);
/*    */   }
/*    */ }