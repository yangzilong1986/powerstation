/*    */ package com.hisun.hiexpression;
/*    */ 
/*    */ import [Ljava.lang.String;;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.util.HiICSProperty;
/*    */ import com.hisun.util.HiResource;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.ArrayList;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class HiExpMethodSource
/*    */ {
/* 16 */   private static Logger log = HiLog.getLogger("expression.trc");
/*    */ 
/*    */   public static Method getMethod(String method, int nargs) throws Exception {
/* 19 */     if (nargs <= 0) {
/* 20 */       argClasses = new Class[] { Object.class };
/* 21 */       return getStaticMethod(method, argClasses);
/*    */     }
/* 23 */     Class[] argClasses = { Object.class, [Ljava.lang.String.class };
/*    */ 
/* 25 */     return getStaticMethod(method, argClasses);
/*    */   }
/*    */ 
/*    */   private static Method getStaticMethod(String staticMethodName, Class[] argClasses) throws Exception
/*    */   {
/* 30 */     Method thisMethod = null;
/* 31 */     ArrayList classes = new ArrayList();
/* 32 */     classes.add(HiExpBasicFunctions.class);
/* 33 */     classes.add(HiExpMath.class);
/* 34 */     String tmp = HiICSProperty.getProperty("expr.extend");
/*    */ 
/* 36 */     if (!(StringUtils.isEmpty(tmp))) {
/* 37 */       String[] tmps = StringUtils.split(tmp, ", ");
/* 38 */       for (int i = 0; i < tmps.length; ++i) {
/*    */         try {
/* 40 */           classes.add(HiResource.loadClass(tmps[i]));
/*    */         } catch (Exception e) {
/* 42 */           log.error("load class:[" + tmps[i] + "] failure:[ " + e + "]", e);
/*    */         }
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 48 */     for (int i = 0; i < classes.size(); ) {
/*    */       try {
/* 50 */         Class clazz = (Class)classes.get(i);
/* 51 */         thisMethod = clazz.getMethod(staticMethodName, argClasses);
/*    */       }
/*    */       catch (SecurityException e) {
/* 54 */         throw e;
/*    */       }
/*    */       catch (NoSuchMethodException e)
/*    */       {
/*    */         while (true) {
/* 48 */           ++i;
/*    */         }
/*    */ 
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 59 */     if (thisMethod == null)
/* 60 */       throw new NoSuchMethodException(staticMethodName);
/* 61 */     return thisMethod;
/*    */   }
/*    */ }