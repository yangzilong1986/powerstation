/*    */ package com.hisun.mng.handler;
/*    */ 
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ 
/*    */ public class ETF2ByteHandler
/*    */ {
/*    */   public HiMessage process(HiMessage msg)
/*    */   {
/* 11 */     HiETF root = msg.getETFBody();
/* 12 */     byte[] data = root.toString().getBytes();
/*    */ 
/* 14 */     msg.setBody(data);
/* 15 */     return msg;
/*    */   }
/*    */ }