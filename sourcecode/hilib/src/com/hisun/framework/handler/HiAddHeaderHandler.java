/*    */ package com.hisun.framework.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.util.HiStringManager;
/*    */ 
/*    */ public class HiAddHeaderHandler
/*    */   implements IHandler
/*    */ {
/*    */   private String param;
/*    */   private String[][] headers;
/*    */   private Logger log;
/* 24 */   private static HiStringManager sm = HiStringManager.getManager();
/*    */ 
/*    */   public void process(HiMessageContext ctx) throws HiException {
/* 27 */     HiMessage msg = ctx.getCurrentMsg();
/* 28 */     parseParam();
/* 29 */     msgsetHeader(msg);
/*    */   }
/*    */ 
/*    */   private void parseParam()
/*    */   {
/* 36 */     if (this.param == null)
/* 37 */       return;
/* 38 */     if (this.headers != null) {
/* 39 */       return;
/*    */     }
/*    */ 
/* 42 */     String[] pp = this.param.split(";");
/* 43 */     this.headers = new String[pp.length][2];
/* 44 */     for (int i = 0; i < pp.length; ++i) {
/* 45 */       String[] header = pp[i].split("=");
/* 46 */       this.headers[i][0] = header[0];
/* 47 */       this.headers[i][1] = header[1];
/*    */     }
/*    */   }
/*    */ 
/*    */   private void msgsetHeader(HiMessage msg) {
/* 52 */     if (this.headers == null) {
/* 53 */       return;
/*    */     }
/* 55 */     for (int i = 0; i < this.headers.length; ++i)
/*    */     {
/* 57 */       msg.addHeadItem(this.headers[i][0], this.headers[i][1]);
/*    */ 
/* 59 */       if (this.log.isDebugEnabled())
/* 60 */         this.log.debug(sm.getString("callService.debug.setheader", this.headers[i][0], this.headers[i][1]));
/*    */     }
/*    */   }
/*    */ 
/*    */   public String getParam()
/*    */   {
/* 67 */     return this.param;
/*    */   }
/*    */ 
/*    */   public void setParam(String param) {
/* 71 */     this.param = param;
/*    */   }
/*    */ 
/*    */   public void setLog(Logger log) {
/* 75 */     this.log = log;
/*    */   }
/*    */ }