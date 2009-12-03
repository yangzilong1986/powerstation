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
/*    */ public class HiMsgPreProcess
/*    */   implements IHandler
/*    */ {
/* 13 */   private String reqType = "0";
/*    */ 
/* 19 */   final Logger log = (Logger)HiContext.getCurrentContext()
/* 19 */     .getProperty("SVR.log");
/*    */ 
/*    */   public void setReqType(String val)
/*    */   {
/* 16 */     this.reqType = val;
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 22 */     HiMessage msg = ctx.getCurrentMsg();
/*    */ 
/* 24 */     Object body = msg.getBody();
/* 25 */     if (body instanceof HiByteBuffer) {
/* 26 */       HiByteBuffer bb = (HiByteBuffer)body;
/*    */ 
/* 28 */       byte type = bb.charAt(0);
/* 29 */       if (type != 48) {
/* 30 */         this.log.error("Request Message Type is Error,should be" + this.reqType);
/* 31 */         throw new HiException("215027", "Message Package Type is Error,should be:" + this.reqType);
/*    */       }
/* 33 */       msg.setBody(new HiByteBuffer(bb.subbyte(1, bb.length() - 1)));
/*    */     }
/*    */   }
/*    */ }