 package com.hisun.web.service;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiServiceRequest;
 import com.hisun.web.util.HiPropertiesUtils;
 import java.io.IOException;
 import java.io.PrintStream;
 import java.net.URISyntaxException;
 import javax.servlet.ServletContext;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class HiCallHostServiceImpl
   implements HiCallHostService
 {
   public static final String TRACE_FILE = "trace_file";
   protected String serverName;
   protected String logLevel;
   protected String msgType;
   protected HttpServletRequest webRequest;
 
   public HiCallHostServiceImpl()
   {
     this.serverName = "MNGWEB";
     this.logLevel = "1";
     this.msgType = "MNGTYPE";
   }
 
   public HiETF callhost(HiMessage hiMessage, ServletContext context)
     throws HiException
   {
     HiMessageContext ctx = new HiMessageContext();
     hiMessage.setHeadItem("SCH", "rq");
 
     long curtime = System.currentTimeMillis();
     hiMessage.setHeadItem("STM", new Long(curtime));
 
     ctx.setCurrentMsg(hiMessage);
     HiMessageContext.setCurrentContext(ctx);
     HiMessage msg = null;
     try
     {
       msg = HiServiceRequest.invoke(HiPropertiesUtils.getProperties("IP", context), NumberUtils.toInt(HiPropertiesUtils.getProperties("PORT", context)), hiMessage);
     }
     catch (URISyntaxException e)
     {
       e.printStackTrace();
     } catch (IOException e) {
       e.printStackTrace();
     } finally {
       HiLog.close(hiMessage);
     }
     return msg.getETFBody();
   }
 
   public HiETF callhost(String code, HiETF etf, ServletContext context) throws HiException {
     if (etf == null) {
       etf = HiETFFactory.createETF();
     }
     HiMessage hiMessage = new HiMessage(this.serverName, this.msgType);
     hiMessage.setHeadItem("STC", code);
     hiMessage.setHeadItem("STF", this.logLevel);
 
     hiMessage.setBody(etf);
     System.out.println("rsp hiMessage : " + hiMessage);
     return callhost(hiMessage, context);
   }
 
   public void setLogLevel(String logLevel)
   {
     this.logLevel = logLevel;
   }
 
   public void setMsgType(String msgType) {
     this.msgType = msgType;
   }
 
   public void setServerName(String serverName) {
     this.serverName = serverName;
   }
 
   public void setWebRequest(HttpServletRequest webRequest) {
     this.webRequest = webRequest;
   }
 }