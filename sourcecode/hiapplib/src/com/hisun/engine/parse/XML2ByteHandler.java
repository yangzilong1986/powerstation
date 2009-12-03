/*    */ package com.hisun.engine.parse;
/*    */ 
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ 
/*    */ public class XML2ByteHandler
/*    */ {
/*    */   public HiMessage process(HiMessage msg)
/*    */   {
/* 11 */     HiETF root = (HiETF)msg.getBody();
/* 12 */     byte[] data = root.getXmlString().getBytes();
/*    */ 
/* 14 */     msg.setBody(data);
/* 15 */     return msg;
/*    */   }
/*    */ }