/*    */ package com.hisun.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ import org.apache.commons.lang.math.NumberUtils;
/*    */ 
/*    */ public class HiLSNSVRHandler
/*    */   implements IHandler
/*    */ {
/*    */   public void doRequest(HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 27 */     HiMessage msg1 = ctx.getCurrentMsg();
/* 28 */     HiByteBuffer byteBuffer = (HiByteBuffer)msg1.getBody();
/* 29 */     HiMessage msg2 = new HiMessage(byteBuffer.toString());
/* 30 */     String stm = msg2.getHeadItem("STM");
/* 31 */     if (stm != null) {
/* 32 */       msg2.setHeadItem("STM", new Long(NumberUtils.toLong(stm)));
/*    */     }
/* 34 */     String etm = msg2.getHeadItem("ETM");
/* 35 */     if (etm != null) {
/* 36 */       msg2.setHeadItem("ETM", new Long(NumberUtils.toLong(etm)));
/*    */     }
/*    */ 
/* 39 */     ctx.setCurrentMsg(msg2);
/*    */   }
/*    */ 
/*    */   public void doResponse(HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 48 */     HiMessage msg1 = ctx.getCurrentMsg();
/* 49 */     HiByteBuffer byteBuffer = new HiByteBuffer(200);
/* 50 */     byteBuffer.append(msg1.toString());
/* 51 */     msg1.setBody(byteBuffer);
/* 52 */     ctx.setCurrentMsg(msg1);
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext arg0) throws HiException
/*    */   {
/*    */   }
/*    */ 
/*    */   public void specProc(HiMessageContext ctx) throws HiException {
/* 60 */     HiMessage msg1 = ctx.getCurrentMsg();
/* 61 */     HiETF root = msg1.getETFBody();
/* 62 */     root.removeChildNode("1");
/*    */   }
/*    */ }