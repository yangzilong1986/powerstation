/*    */ package com.hisun.mng.handler;
/*    */ 
/*    */ import com.hisun.message.HiMessage;
/*    */ import org.dom4j.Element;
/*    */ 
/*    */ public class XML2ByteHandler
/*    */ {
/*    */   public HiMessage process(HiMessage msg)
/*    */   {
/* 10 */     Element root = (Element)msg.getObjectHeadItem("MNGXML");
/* 11 */     byte[] data = root.asXML().getBytes();
/*    */ 
/* 13 */     msg.setBody(data);
/* 14 */     return msg;
/*    */   }
/*    */ }