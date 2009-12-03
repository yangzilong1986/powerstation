/*    */ package com.hisun.web.service;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiETFFactory;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.util.HiServiceRequest;
/*    */ import com.hisun.web.util.HiPropertiesUtils;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import java.net.URISyntaxException;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.apache.commons.lang.math.NumberUtils;
/*    */ 
/*    */ public class HiCallHostServiceImpl
/*    */   implements HiCallHostService
/*    */ {
/*    */   public static final String TRACE_FILE = "trace_file";
/*    */   protected String serverName;
/*    */   protected String logLevel;
/*    */   protected String msgType;
/*    */   protected HttpServletRequest webRequest;
/*    */ 
/*    */   public HiCallHostServiceImpl()
/*    */   {
/* 23 */     this.serverName = "MNGWEB";
/* 24 */     this.logLevel = "1";
/* 25 */     this.msgType = "MNGTYPE";
/*    */   }
/*    */ 
/*    */   public HiETF callhost(HiMessage hiMessage, ServletContext context)
/*    */     throws HiException
/*    */   {
/* 31 */     HiMessageContext ctx = new HiMessageContext();
/* 32 */     hiMessage.setHeadItem("SCH", "rq");
/*    */ 
/* 35 */     long curtime = System.currentTimeMillis();
/* 36 */     hiMessage.setHeadItem("STM", new Long(curtime));
/*    */ 
/* 38 */     ctx.setCurrentMsg(hiMessage);
/* 39 */     HiMessageContext.setCurrentContext(ctx);
/* 40 */     HiMessage msg = null;
/*    */     try
/*    */     {
/* 43 */       msg = HiServiceRequest.invoke(HiPropertiesUtils.getProperties("IP", context), NumberUtils.toInt(HiPropertiesUtils.getProperties("PORT", context)), hiMessage);
/*    */     }
/*    */     catch (URISyntaxException e)
/*    */     {
/* 48 */       e.printStackTrace();
/*    */     } catch (IOException e) {
/* 50 */       e.printStackTrace();
/*    */     } finally {
/* 52 */       HiLog.close(hiMessage);
/*    */     }
/* 54 */     return msg.getETFBody();
/*    */   }
/*    */ 
/*    */   public HiETF callhost(String code, HiETF etf, ServletContext context) throws HiException {
/* 58 */     if (etf == null) {
/* 59 */       etf = HiETFFactory.createETF();
/*    */     }
/* 61 */     HiMessage hiMessage = new HiMessage(this.serverName, this.msgType);
/* 62 */     hiMessage.setHeadItem("STC", code);
/* 63 */     hiMessage.setHeadItem("STF", this.logLevel);
/*    */ 
/* 65 */     hiMessage.setBody(etf);
/* 66 */     System.out.println("rsp hiMessage : " + hiMessage);
/* 67 */     return callhost(hiMessage, context);
/*    */   }
/*    */ 
/*    */   public void setLogLevel(String logLevel)
/*    */   {
/* 72 */     this.logLevel = logLevel;
/*    */   }
/*    */ 
/*    */   public void setMsgType(String msgType) {
/* 76 */     this.msgType = msgType;
/*    */   }
/*    */ 
/*    */   public void setServerName(String serverName) {
/* 80 */     this.serverName = serverName;
/*    */   }
/*    */ 
/*    */   public void setWebRequest(HttpServletRequest webRequest) {
/* 84 */     this.webRequest = webRequest;
/*    */   }
/*    */ }