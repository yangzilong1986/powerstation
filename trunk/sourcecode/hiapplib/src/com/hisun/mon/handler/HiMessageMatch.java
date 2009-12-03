/*    */ package com.hisun.mon.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiContext;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ 
/*    */ public class HiMessageMatch
/*    */   implements IHandler
/*    */ {
/* 14 */   private String reqProcess = "reqProc";
/* 15 */   private String rspProcess = "rspProc";
/*    */ 
/* 18 */   final Logger log = (Logger)HiContext.getCurrentContext()
/* 18 */     .getProperty("SVR.log");
/*    */ 
/*    */   public void process(HiMessageContext ctx) throws HiException
/*    */   {
/* 22 */     HiMessage msg = ctx.getCurrentMsg();
/*    */ 
/* 24 */     Object body = msg.getBody();
/* 25 */     if (body instanceof HiByteBuffer) {
/* 26 */       HiByteBuffer bb = (HiByteBuffer)body;
/* 27 */       byte type = bb.charAt(0);
/* 28 */       if (this.log.isDebugEnabled()) {
/* 29 */         this.log.debug("Msg Type:" + type);
/*    */       }
/* 31 */       if (type % 2 == 0) {
/* 32 */         msg.setHeadItem("SCH", "rq");
/* 33 */         ctx.setProperty("_SUBPROCESS", this.reqProcess);
/*    */       } else {
/* 35 */         msg.setHeadItem("SCH", "rp");
/* 36 */         ctx.setProperty("_SUBPROCESS", this.rspProcess);
/*    */       }
/*    */ 
/* 39 */       msg.setBody(new HiByteBuffer(bb.subbyte(1, bb.length() - 1)));
/*    */     }
/*    */   }
/*    */ 
/*    */   public void setReqProcess(String val)
/*    */   {
/* 45 */     this.reqProcess = val;
/*    */   }
/*    */ 
/*    */   public void setRspProcess(String val) {
/* 49 */     this.rspProcess = val;
/*    */   }
/*    */ }