/*    */ package com.hisun.engine.invoke.impl;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ 
/*    */ public class HiResponse extends HiAbstractRqAndRp
/*    */ {
/*    */   public String getNodeName()
/*    */   {
/* 19 */     return "Response";
/*    */   }
/*    */ 
/*    */   public void afterProcess(HiMessageContext messContext)
/*    */     throws HiException
/*    */   {
/* 30 */     HiMessage mess = messContext.getCurrentMsg();
/* 31 */     Logger log = HiLog.getLogger(mess);
/* 32 */     super.afterProcess(messContext);
/* 33 */     mess.delHeadItemVal("STC");
/* 34 */     if (!(log.isDebugEnabled()))
/*    */       return;
/* 36 */     log.debug("HiResponse(HiMessageContext) - end");
/*    */   }
/*    */ }