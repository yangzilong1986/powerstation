/*    */ package com.hisun.engine.invoke.impl;
/*    */ 
/*    */ import com.hisun.engine.invoke.HiIAction;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ 
/*    */ public class HiProcess
/*    */ {
/*    */   public static void process(HiIAction action, HiMessageContext messContext)
/*    */     throws HiException
/*    */   {
/* 23 */     action.beforeProcess(messContext);
/* 24 */     action.process(messContext);
/* 25 */     action.afterProcess(messContext);
/*    */   }
/*    */ }