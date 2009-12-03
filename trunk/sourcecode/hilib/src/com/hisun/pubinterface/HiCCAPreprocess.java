/*    */ package com.hisun.pubinterface;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.message.HiMessageHelper;
/*    */ 
/*    */ public abstract class HiCCAPreprocess
/*    */ {
/*    */   public void request(HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 10 */     HiMessage msg = ctx.getCurrentMsg();
/*    */ 
/* 12 */     if ((!(HiMessageHelper.isOutterMessage(msg))) || (!(HiMessageHelper.isRequestMessage(msg))))
/*    */       return;
/* 14 */     doRequest(ctx);
/*    */   }
/*    */ 
/*    */   public void response(HiMessageContext ctx) throws HiException
/*    */   {
/* 19 */     HiMessage msg = ctx.getCurrentMsg();
/*    */ 
/* 21 */     if ((!(HiMessageHelper.isOutterMessage(msg))) || (!(HiMessageHelper.isResponseMessage(msg))))
/*    */       return;
/* 23 */     doResponse(ctx);
/*    */   }
/*    */ 
/*    */   protected abstract void doRequest(HiMessageContext paramHiMessageContext)
/*    */     throws HiException;
/*    */ 
/*    */   protected abstract void doResponse(HiMessageContext paramHiMessageContext)
/*    */     throws HiException;
/*    */ }