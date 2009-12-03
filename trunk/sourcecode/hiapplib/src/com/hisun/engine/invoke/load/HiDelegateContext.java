/*    */ package com.hisun.engine.invoke.load;
/*    */ 
/*    */ import com.hisun.engine.invoke.impl.HiAbstractApplication;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiContext;
/*    */ 
/*    */ public class HiDelegateContext extends HiAbstractApplication
/*    */ {
/*    */   public HiDelegateContext()
/*    */     throws HiException
/*    */   {
/* 17 */     this.context = HiContext.createAndPushContext();
/*    */   }
/*    */ 
/*    */   public void setMap(String strName, String strValue)
/*    */   {
/* 22 */     this.context.setProperty(strName, strValue);
/*    */   }
/*    */ }