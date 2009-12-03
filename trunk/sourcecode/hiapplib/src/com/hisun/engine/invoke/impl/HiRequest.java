/*    */ package com.hisun.engine.invoke.impl;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ 
/*    */ public class HiRequest extends HiAbstractRqAndRp
/*    */ {
/*    */   public String getNodeName()
/*    */   {
/* 19 */     return "Request";
/*    */   }
/*    */ 
/*    */   public void afterProcess(HiMessageContext messContext)
/*    */     throws HiException
/*    */   {
/* 26 */     super.afterProcess(messContext);
/* 27 */     HiMessage mess = messContext.getCurrentMsg();
/* 28 */     Logger log = HiLog.getLogger(mess);
/* 29 */     if (!(log.isDebugEnabled()))
/*    */       return;
/* 31 */     log.debug("HiRequest(HiMessageContext) - end");
/*    */   }
/*    */ }