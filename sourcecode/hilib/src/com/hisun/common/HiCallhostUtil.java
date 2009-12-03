/*    */ package com.hisun.common;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiETFFactory;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.util.HiServiceRequest;
/*    */ import com.hisun.web.filter.HiConfig;
/*    */ 
/*    */ public class HiCallhostUtil
/*    */ {
/*    */   public static HiETF callhost(HiConfig config, String txncode, HiETF etf)
/*    */   {
/* 15 */     if (etf == null) {
/* 16 */       etf = HiETFFactory.createETF();
/*    */     }
/* 18 */     String serverName = config.getServerName();
/* 19 */     String msgType = config.getMsgType();
/* 20 */     String logLevel = config.getLogLevel();
/* 21 */     String ip = config.getIp();
/* 22 */     int port = config.getPort();
/*    */ 
/* 24 */     HiMessage hiMessage = new HiMessage(serverName, msgType);
/* 25 */     hiMessage.setHeadItem("STC", txncode);
/* 26 */     hiMessage.setHeadItem("STF", logLevel);
/*    */ 
/* 28 */     hiMessage.setBody(etf);
/*    */ 
/* 30 */     HiMessageContext ctx = new HiMessageContext();
/* 31 */     hiMessage.setHeadItem("SCH", "rq");
/*    */ 
/* 34 */     long curtime = System.currentTimeMillis();
/* 35 */     hiMessage.setHeadItem("STM", new Long(curtime));
/*    */ 
/* 37 */     ctx.setCurrentMsg(hiMessage);
/* 38 */     HiMessageContext.setCurrentContext(ctx);
/* 39 */     HiMessage msg = null;
/*    */     try
/*    */     {
/* 42 */       msg = HiServiceRequest.invoke(ip, port, hiMessage);
/*    */     }
/*    */     catch (HiException e)
/*    */     {
/* 47 */       e.printStackTrace();
/*    */     } finally {
/* 49 */       HiLog.close(hiMessage);
/*    */     }
/* 51 */     return msg.getETFBody();
/*    */   }
/*    */ }