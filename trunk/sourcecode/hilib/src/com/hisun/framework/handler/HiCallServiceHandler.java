/*    */ package com.hisun.framework.handler;
/*    */ 
/*    */ import com.hisun.dispatcher.HiRouterOut;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.util.HiStringManager;
/*    */ 
/*    */ public class HiCallServiceHandler
/*    */   implements IHandler
/*    */ {
/*    */   private String param;
/*    */   private String[][] headers;
/*    */   private Logger log;
/* 25 */   private static HiStringManager sm = HiStringManager.getManager();
/*    */ 
/*    */   public void process(HiMessageContext ctx) throws HiException {
/* 28 */     HiMessage msg = ctx.getCurrentMsg();
/* 29 */     parseParam();
/* 30 */     msgsetHeader(msg);
/*    */ 
/* 32 */     HiRouterOut.process(ctx);
/*    */   }
/*    */ 
/*    */   private void parseParam()
/*    */   {
/* 42 */     if (this.param == null)
/* 43 */       return;
/* 44 */     if (this.headers != null) {
/* 45 */       return;
/*    */     }
/*    */ 
/* 48 */     String[] pp = this.param.split(";");
/* 49 */     this.headers = new String[pp.length][2];
/* 50 */     for (int i = 0; i < pp.length; ++i) {
/* 51 */       String[] header = pp[i].split("=");
/* 52 */       this.headers[i][0] = header[0];
/* 53 */       this.headers[i][1] = header[1];
/*    */     }
/*    */   }
/*    */ 
/*    */   private void msgsetHeader(HiMessage msg) {
/* 58 */     if (this.headers == null) {
/* 59 */       return;
/*    */     }
/* 61 */     for (int i = 0; i < this.headers.length; ++i) {
/* 62 */       msg.setHeadItem(this.headers[i][0], this.headers[i][1]);
/*    */ 
/* 64 */       if (this.log.isDebugEnabled())
/* 65 */         this.log.debug(sm.getString("callService.debug.setheader", this.headers[i][0], this.headers[i][1]));
/*    */     }
/*    */   }
/*    */ 
/*    */   public String getParam()
/*    */   {
/* 72 */     return this.param;
/*    */   }
/*    */ 
/*    */   public void setParam(String param) {
/* 76 */     this.param = param;
/*    */   }
/*    */ 
/*    */   public void setLog(Logger log) {
/* 80 */     this.log = log;
/*    */   }
/*    */ }