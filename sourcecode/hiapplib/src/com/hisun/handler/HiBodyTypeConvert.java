/*    */ package com.hisun.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiETFFactory;
/*    */ import com.hisun.message.HiETFUtils;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.message.HiXmlETF;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ import java.io.PrintStream;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.w3c.dom.Document;
/*    */ 
/*    */ public class HiBodyTypeConvert
/*    */   implements IHandler
/*    */ {
/*    */   public void etf2bb(HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 34 */     HiMessage msg = ctx.getCurrentMsg();
/* 35 */     Object o = msg.getBody();
/* 36 */     if (o instanceof HiETF) {
/* 37 */       HiETF root = (HiETF)o;
/* 38 */       msg.setBody(new HiByteBuffer(root.toString().getBytes()));
/*    */     }
/*    */   }
/*    */ 
/*    */   public void bb2etf(HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 47 */     HiMessage msg = ctx.getCurrentMsg();
/* 48 */     Object o = msg.getBody();
/* 49 */     if (o instanceof HiByteBuffer) {
/* 50 */       HiByteBuffer buf = (HiByteBuffer)o;
/* 51 */       msg.setBody(HiETFFactory.createETF(buf.toString()));
/*    */     }
/*    */   }
/*    */ 
/*    */   public void etf2dom(HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 60 */     HiMessage msg = ctx.getCurrentMsg();
/* 61 */     if (msg.getBody() instanceof HiETF) {
/* 62 */       HiXmlETF etfBody = (HiXmlETF)msg.getETFBody();
/* 63 */       Document doc = HiETFUtils.convertToDOM(etfBody);
/* 64 */       msg.setBody(doc);
/* 65 */       System.out.println("etf01" + etfBody);
/* 66 */       System.out.println("doc01:" + doc);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void dom2etf(HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 76 */     HiMessage msg = ctx.getCurrentMsg();
/* 77 */     if (msg.getBody() instanceof Document) {
/* 78 */       Document doc = (Document)msg.getBody();
/* 79 */       msg.setBody(HiETFUtils.convertToXmlETF(doc));
/*    */     }
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 87 */     HiMessage msg = ctx.getCurrentMsg();
/* 88 */     String sch = msg.getHeadItem("SCH");
/* 89 */     if (StringUtils.equals(sch, "rq"))
/* 90 */       etf2dom(ctx);
/*    */     else
/* 92 */       dom2etf(ctx);
/*    */   }
/*    */ }