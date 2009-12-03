/*    */ package com.hisun.mon.handler;
/*    */ 
/*    */ import com.hisun.engine.HiMessagePool;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiContext;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiETFFactory;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ 
/*    */ public class HiRestoreMsgHeader
/*    */   implements IHandler
/*    */ {
/* 18 */   final Logger log = (Logger)HiContext.getCurrentContext()
/* 18 */     .getProperty("SVR.log");
/*    */ 
/* 20 */   private String keyName = "Rsv_No";
/*    */ 
/*    */   public void setKeyName(String val) { this.keyName = val;
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/*    */     HiETF etf;
/* 26 */     HiMessage msg = ctx.getCurrentMsg();
/*    */ 
/* 28 */     Object body = msg.getBody();
/*    */ 
/* 30 */     if (body instanceof HiByteBuffer) {
/* 31 */       HiByteBuffer bb = (HiByteBuffer)body;
/*    */ 
/* 33 */       etf = HiETFFactory.createETF(bb.toString());
/*    */     }
/*    */     else {
/* 36 */       etf = (HiETF)body;
/*    */     }
/*    */ 
/* 39 */     String keyVal = etf.getGrandChildValue(this.keyName);
/*    */ 
/* 41 */     if (this.log.isDebugEnabled())
/*    */     {
/* 43 */       this.log.debug("Get Message match ID:" + keyVal);
/*    */     }
/*    */ 
/* 46 */     HiMessagePool mp = HiMessagePool.getMessagePool(ctx);
/* 47 */     HiMessage oldMsg = (HiMessage)mp.getHeader(keyVal);
/* 48 */     if (oldMsg == null)
/*    */     {
/* 50 */       throw new HiException("The Message is no exist,may be timeout,msg:" + oldMsg.toString());
/*    */     }
/*    */ 
/* 53 */     msg.setHead(oldMsg.getHead());
/* 54 */     msg.setHeadItem("SCH", "rp");
/*    */ 
/* 56 */     msg.setHeadItem("PlainText", etf);
/* 57 */     msg.setBody(HiETFFactory.createETF());
/*    */   }
/*    */ }