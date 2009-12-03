/*    */ package com.hisun.engine.invoke;
/*    */ 
/*    */ import com.hisun.engine.HiEngineUtilities;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiETFFactory;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ 
/*    */ public abstract class HiAbstractStrategy
/*    */   implements HiIStrategy
/*    */ {
/*    */   public void beforeConverMess(HiMessageContext messContext)
/*    */     throws HiException
/*    */   {
/*    */     Object converBody;
/* 24 */     HiMessage mess = messContext.getCurrentMsg();
/* 25 */     Logger log = HiLog.getLogger(mess);
/* 26 */     if (log.isDebugEnabled())
/*    */     {
/* 28 */       log.debug("beforeConverMess(HiMessageContext) - start[" + super.getClass() + "]");
/*    */     }
/*    */ 
/* 32 */     if (HiEngineUtilities.isInnerMessage(mess))
/*    */     {
/* 34 */       converBody = createBeforeMess(mess, true);
/*    */ 
/* 36 */       mess.setHeadItem("PlainText", converBody);
/*    */     }
/*    */     else
/*    */     {
/* 40 */       if (mess.getBody() instanceof HiETF) {
/* 41 */         return;
/*    */       }
/* 43 */       converBody = createBeforeMess(mess, false);
/* 44 */       mess.setHeadItem("PlainText", converBody);
/*    */ 
/* 46 */       HiETF etf = HiETFFactory.createETF();
/* 47 */       mess.setBody(etf);
/*    */     }
/* 49 */     if (!(log.isDebugEnabled()))
/*    */       return;
/* 51 */     log.debug("beforeConverMess(HiMessageContext) - end");
/*    */   }
/*    */ 
/*    */   public void afterConverMess(HiMessageContext messContext)
/*    */     throws HiException
/*    */   {
/* 58 */     HiMessage mess = messContext.getCurrentMsg();
/* 59 */     Logger log = HiLog.getLogger(mess);
/* 60 */     if (log.isDebugEnabled())
/*    */     {
/* 62 */       log.debug("afterConverMess(HiMessageContext) - start[" + super.getClass() + "]");
/*    */     }
/*    */ 
/* 66 */     if (HiEngineUtilities.isInnerMessage(mess))
/*    */     {
/* 68 */       Object body = createAfterMess(mess);
/* 69 */       mess.setBody(body);
/* 70 */       mess.setHeadItem("ECT", "text/plain");
/*    */     }
/*    */     else
/*    */     {
/* 74 */       mess.setHeadItem("ECT", "text/etf");
/*    */     }
/*    */ 
/* 83 */     mess.delHeadItem("PlainText");
/* 84 */     mess.delHeadItem("PlainOffset");
/*    */   }
/*    */ 
/*    */   public abstract Object createAfterMess(HiMessage paramHiMessage)
/*    */     throws HiException;
/*    */ 
/*    */   public abstract Object createBeforeMess(HiMessage paramHiMessage, boolean paramBoolean)
/*    */     throws HiException;
/*    */ }