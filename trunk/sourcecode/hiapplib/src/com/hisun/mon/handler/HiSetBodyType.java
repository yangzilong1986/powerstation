/*    */ package com.hisun.mon.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ 
/*    */ public class HiSetBodyType
/*    */   implements IHandler
/*    */ {
/* 10 */   private String type = "1";
/*    */ 
/*    */   public void setType(String type) {
/* 13 */     this.type = type;
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext ctx) throws HiException {
/* 17 */     HiMessage msg = ctx.getCurrentMsg();
/*    */ 
/* 19 */     HiByteBuffer oldBody = (HiByteBuffer)msg.getBody();
/* 20 */     HiByteBuffer newBody = new HiByteBuffer(oldBody.length() + 1);
/* 21 */     newBody.append(this.type);
/* 22 */     newBody.append(oldBody.getBytes());
/*    */ 
/* 24 */     msg.setBody(newBody);
/*    */   }
/*    */ }