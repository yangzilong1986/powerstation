/*     */ package com.hisun.server;
/*     */ 
/*     */ import com.hisun.dispatcher.HiRouterOut;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.message.HiXmlETF;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import javax.xml.soap.SOAPBody;
/*     */ import javax.xml.soap.SOAPEnvelope;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import org.apache.axis.MessageContext;
/*     */ import org.apache.axis.constants.Style;
/*     */ import org.apache.axis.constants.Use;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ public class HiService
/*     */ {
/*  27 */   private static final Logger logger = Logger.getLogger(HiService.class);
/*     */   public static final String reality_service = "REALITY_SERVICE";
/*     */   public static final String service_maps = "SERVICE_MAPS";
/*     */ 
/*     */   public void process(SOAPEnvelope req, SOAPEnvelope resp)
/*     */     throws SOAPException
/*     */   {
/*  36 */     if (logger.isDebugEnabled())
/*     */     {
/*  38 */       logger.debug("process(SOAPEnvelope, SOAPEnvelope) - start");
/*     */     }
/*     */ 
/*  41 */     SOAPBody reqBody = req.getBody();
/*     */     try
/*     */     {
/*  44 */       System.out.println("HiService is start[" + req);
/*  45 */       System.out.println("*********************");
/*  46 */       System.out.println(MessageContext.getCurrentContext().getOperationStyle().getName());
/*     */ 
/*  48 */       System.out.println(MessageContext.getCurrentContext().getOperationUse().getName());
/*     */ 
/*  51 */       System.out.println("name[" + req.getClass().getName());
/*     */ 
/*  57 */       HashMap serviceMap = (HashMap)MessageContext.getCurrentContext().getProperty("SERVICE_MAPS");
/*     */ 
/*  60 */       String strServiceName = (String)MessageContext.getCurrentContext().getProperty("REALITY_SERVICE");
/*     */ 
/*  63 */       System.out.println("strServiceName[" + strServiceName + "]");
/*     */ 
/*  65 */       String strMethodName = reqBody.getFirstChild().getLocalName();
/*     */ 
/*  70 */       System.out.println("operation[" + strMethodName + "]");
/*     */ 
/*  72 */       HashMap operMap = (HashMap)serviceMap.get(strServiceName);
/*  73 */       String strCode = (String)operMap.get(strMethodName);
/*     */ 
/*  75 */       System.out.println("strCode[" + strCode + "]");
/*     */ 
/*  77 */       HiMessage mess = new HiMessage("axis", "PLTN0");
/*  78 */       System.out.println("HiMessage[" + mess + "]");
/*  79 */       Logger log = HiLog.getLogger(mess);
/*  80 */       if (log.isDebugEnabled())
/*     */       {
/*  82 */         log.debug("request body[" + reqBody + "]");
/*     */       }
/*  84 */       mess.setHeadItem("STC", strCode);
/*     */ 
/*  86 */       mess.setBody(reqBody.getOwnerDocument());
/*     */ 
/*  88 */       mess.setHeadItem("ECT", "text/xml");
/*  89 */       mess.setHeadItem("SCH", "rq");
/*  90 */       mess.setHeadItem("STM", new Long(System.currentTimeMillis()));
/*     */ 
/*  94 */       HiMessageContext messContext = new HiMessageContext();
/*  95 */       messContext.setCurrentMsg(mess);
/*  96 */       HiMessageContext.setCurrentMessageContext(messContext);
/*  97 */       HiRouterOut.process(messContext);
/*     */ 
/*  99 */       if (mess.getBody() instanceof HiXmlETF)
/*     */       {
/* 101 */         resp.getBody().addDocument(((HiXmlETF)mess.getBody()).toDOMDocument());
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Throwable e)
/*     */     {
/* 107 */       logger.error("process(SOAPEnvelope, SOAPEnvelope)", e);
/*     */ 
/* 109 */       e.printStackTrace();
/* 110 */       throw new SOAPException(e);
/*     */     }
/*     */ 
/* 113 */     if (!(logger.isDebugEnabled()))
/*     */       return;
/* 115 */     logger.debug("process(SOAPEnvelope, SOAPEnvelope) - end");
/*     */   }
/*     */ }