/*    */ package com.hisun.atmp.handler;
/*    */ 
/*    */ import com.hisun.engine.HiMessagePool;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ 
/*    */ public class HiDiaboRestoreMsgHeader
/*    */   implements IHandler
/*    */ {
/*    */   public void process(HiMessageContext messContext)
/*    */     throws HiException
/*    */   {
/* 26 */     HiMessage mess = messContext.getCurrentMsg();
/*    */ 
/* 28 */     String sip = mess.getHeadItem("SIP");
/* 29 */     String stc = mess.getHeadItem("STC");
/*    */ 
/* 31 */     HiMessagePool mp = HiMessagePool.getMessagePool(messContext);
/* 32 */     HiMessage msg1 = (HiMessage)mp.getHeader(sip + stc);
/*    */ 
/* 34 */     if (msg1 == null) {
/* 35 */       throw new HiException("310009");
/*    */     }
/* 37 */     mess.setRequestId(msg1.getRequestId());
/* 38 */     mess.setHeadItem("STC", msg1.getHeadItem("STC"));
/* 39 */     mess.setHeadItem("STF", msg1.getHeadItem("STF"));
/*    */   }
/*    */ }