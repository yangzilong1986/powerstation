 package com.hisun.common;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiServiceRequest;
 import com.hisun.web.filter.HiConfig;
 
 public class HiCallhostUtil
 {
   public static HiETF callhost(HiConfig config, String txncode, HiETF etf)
   {
     if (etf == null) {
       etf = HiETFFactory.createETF();
     }
     String serverName = config.getServerName();
     String msgType = config.getMsgType();
     String logLevel = config.getLogLevel();
     String ip = config.getIp();
     int port = config.getPort();
 
     HiMessage hiMessage = new HiMessage(serverName, msgType);
     hiMessage.setHeadItem("STC", txncode);
     hiMessage.setHeadItem("STF", logLevel);
 
     hiMessage.setBody(etf);
 
     HiMessageContext ctx = new HiMessageContext();
     hiMessage.setHeadItem("SCH", "rq");
 
     long curtime = System.currentTimeMillis();
     hiMessage.setHeadItem("STM", new Long(curtime));
 
     ctx.setCurrentMsg(hiMessage);
     HiMessageContext.setCurrentContext(ctx);
     HiMessage msg = null;
     try
     {
       msg = HiServiceRequest.invoke(ip, port, hiMessage);
     }
     catch (HiException e)
     {
       e.printStackTrace();
     } finally {
       HiLog.close(hiMessage);
     }
     return msg.getETFBody();
   }
 }