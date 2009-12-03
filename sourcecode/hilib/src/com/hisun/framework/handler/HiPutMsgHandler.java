/*    */ package com.hisun.framework.handler;
/*    */ 
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ 
/*    */ public class HiPutMsgHandler
/*    */   implements IHandler
/*    */ {
/*    */   public void process(HiMessageContext ctx)
/*    */   {
/* 15 */     HiMessage msg = ctx.getCurrentMsg();
/*    */ 
/* 17 */     Object header = msg.getHead();
/* 18 */     synchronized (header)
/*    */     {
/* 20 */       msg.setHeadItem("_MSG_RECVED", "1");
/* 21 */       header.notify();
/*    */     }
/*    */   }
/*    */ }