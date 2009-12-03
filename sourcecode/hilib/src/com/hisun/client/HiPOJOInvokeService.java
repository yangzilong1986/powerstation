/*    */ package com.hisun.client;
/*    */ 
/*    */ import com.hisun.dispatcher.HiRouterOut;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.register.HiRegisterService;
/*    */ import com.hisun.register.HiServiceObject;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class HiPOJOInvokeService extends HiInvokeService
/*    */ {
/*    */   public HiPOJOInvokeService()
/*    */   {
/*    */   }
/*    */ 
/*    */   public HiPOJOInvokeService(String code)
/*    */   {
/* 26 */     super(code);
/*    */   }
/*    */ 
/*    */   protected HiETF doInvoke(String code, HiETF root) throws Exception {
/* 30 */     if (this.logger.isInfoEnabled()) {
/* 31 */       this.logger.info("request data:[" + code + ":" + this.etf + "]");
/*    */     }
/* 33 */     String serverName = info.getServerName();
/*    */     try {
/* 35 */       HiServiceObject serviceObject = HiRegisterService.getService(code);
/*    */ 
/* 37 */       serverName = serviceObject.getServerName();
/*    */     }
/*    */     catch (HiException e)
/*    */     {
/*    */     }
/* 42 */     HiMessage msg = new HiMessage(serverName, info.getMsgType());
/* 43 */     msg.setBody(this.etf);
/* 44 */     msg.setHeadItem("STC", code);
/* 45 */     msg.setHeadItem("SCH", "rq");
/* 46 */     msg.setHeadItem("ECT", "text/etf");
/* 47 */     long curtime = System.currentTimeMillis();
/* 48 */     msg.setHeadItem("STM", new Long(curtime));
/*    */ 
/* 50 */     HiMessageContext ctx = new HiMessageContext();
/* 51 */     ctx.setCurrentMsg(msg);
/* 52 */     HiMessageContext.setCurrentMessageContext(ctx);
/*    */ 
/* 54 */     msg = HiRouterOut.syncProcess(msg);
/* 55 */     this.etf = msg.getETFBody();
/* 56 */     if (this.logger.isInfoEnabled()) {
/* 57 */       this.logger.info("response data:[" + code + ":" + this.etf + "]");
/*    */     }
/* 59 */     return this.etf;
/*    */   }
/*    */ }