/*    */ package com.hisun.framework.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.framework.HiDefaultServer;
/*    */ import com.hisun.framework.imp.HiDefaultProcess;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiContext;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.util.HiStringManager;
/*    */ 
/*    */ public class HiCallProcessHandler
/*    */   implements IHandler
/*    */ {
/*    */   private String param;
/*    */   private Logger log;
/* 28 */   private static HiStringManager sm = HiStringManager.getManager();
/*    */   private HiDefaultServer server;
/*    */ 
/*    */   public void process(HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 33 */     HiMessage msg = ctx.getCurrentMsg();
/*    */ 
/* 35 */     String processname = this.param;
/* 36 */     if (processname == null) {
/* 37 */       processname = (String)ctx.getProperty("_SUBPROCESS");
/*    */     }
/* 39 */     HiDefaultProcess process = this.server.getProcessByName(processname);
/*    */ 
/* 41 */     if (process == null) {
/* 42 */       String errmsg = sm.getString("HiCallProcessHandler.err", msg.getRequestId(), this.param);
/*    */ 
/* 44 */       throw new HiException("211004", errmsg);
/*    */     }
/*    */ 
/* 47 */     process.process(ctx);
/*    */   }
/*    */ 
/*    */   public String getParam() {
/* 51 */     return this.param;
/*    */   }
/*    */ 
/*    */   public void setParam(String param) {
/* 55 */     this.param = param;
/*    */   }
/*    */ 
/*    */   public void setLog(Logger log) {
/* 59 */     this.log = log;
/*    */ 
/* 62 */     this.server = ((HiDefaultServer)HiContext.getCurrentContext().getProperty("SVR.server"));
/*    */   }
/*    */ }