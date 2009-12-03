/*    */ package com.hisun.engine.invoke;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiContext;
/*    */ import com.hisun.util.HiResource;
/*    */ 
/*    */ public class HiStrategyFactory
/*    */ {
/*    */   public static final String STRATEGY_KEY = "STRATEGY_KEY";
/*    */ 
/*    */   public static HiIStrategy getStrategyInstance(String strClassName)
/*    */     throws HiException
/*    */   {
/* 17 */     HiIStrategy ins = null;
/* 18 */     HiContext context = HiContext.getCurrentContext();
/* 19 */     if (context.containsProperty("STRATEGY_KEY")) {
/* 20 */       ins = (HiIStrategy)context.getProperty("STRATEGY_KEY");
/*    */     }
/*    */     else {
/*    */       try
/*    */       {
/* 25 */         ins = (HiIStrategy)HiResource.loadClass(strClassName).newInstance();
/*    */ 
/* 27 */         context.setProperty("STRATEGY_KEY", ins);
/*    */       }
/*    */       catch (ClassNotFoundException e)
/*    */       {
/* 31 */         throw new HiException(e);
/*    */       }
/*    */       catch (InstantiationException e)
/*    */       {
/* 35 */         throw new HiException(e);
/*    */       }
/*    */       catch (IllegalAccessException e)
/*    */       {
/* 39 */         throw new HiException(e);
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 66 */     return ins;
/*    */   }
/*    */ }