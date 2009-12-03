/*    */ package com.hisun.web.service;
/*    */ 
/*    */ import com.hisun.dispatcher.HiRouterOut;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ 
/*    */ public class HiInvokeServiceImpl
/*    */   implements IInvokeService
/*    */ {
/*    */   public HiETF invoke(HiETF reqETF)
/*    */     throws HiException
/*    */   {
/* 18 */     return reqETF;
/*    */   }
/*    */ 
/*    */   public HiMessage invoke(HiMessage msg) throws HiException {
/* 22 */     HiMessageContext ctx = new HiMessageContext();
/* 23 */     ctx.setCurrentMsg(msg);
/* 24 */     HiMessageContext.setCurrentContext(ctx);
/* 25 */     msg = HiRouterOut.syncProcess(msg);
/* 26 */     return msg;
/*    */   }
/*    */ }