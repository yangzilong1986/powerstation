/*    */ package com.hisun.engine.invoke.load;
/*    */ 
/*    */ import com.hisun.engine.invoke.HiIEngineModel;
/*    */ import com.hisun.engine.invoke.impl.HiAbstractTransaction;
/*    */ import com.hisun.engine.invoke.impl.HiFunction;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiContext;
/*    */ 
/*    */ public class HiGlobalFunction extends HiAbstractTransaction
/*    */ {
/*    */   public void addChilds(HiIEngineModel child)
/*    */     throws HiException
/*    */   {
/* 19 */     super.addChilds(child);
/* 20 */     if (!(child instanceof HiFunction))
/*    */       return;
/* 22 */     HiFunction fun = (HiFunction)child;
/*    */ 
/* 25 */     HiContext.getCurrentContext().setProperty("GLOBALFUNCTION." + fun.getName(), child);
/*    */   }
/*    */ }