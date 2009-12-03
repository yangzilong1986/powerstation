/*     */ package com.hisun.engine.invoke;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.message.HiXmlETF;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ public class HiXmlStategy extends HiAbstractStrategy
/*     */ {
/*     */   public Object createBeforeMess(HiMessage mess, boolean isInnerMess)
/*     */     throws HiException
/*     */   {
/*  27 */     if (isInnerMess)
/*     */     {
/*  29 */       if (!(mess.hasHeadItem("plain_type"))) {
/*  30 */         mess.setHeadItem("plain_type", "byte");
/*     */       }
/*  32 */       String XMLEncoding = HiMessageContext.getCurrentMessageContext().getStrProp("NSDECLARE", "XMLEncoding");
/*  33 */       String rootName = HiMessageContext.getCurrentMessageContext().getStrProp("NSDECLARE", "RootName");
/*  34 */       HiXmlETF etf = null;
/*  35 */       if (StringUtils.isNotBlank(rootName)) {
/*  36 */         etf = new HiXmlETF(rootName, "");
/*  37 */         etf.getNode().setName(rootName);
/*     */       } else {
/*  39 */         etf = (HiXmlETF)HiETFFactory.createXmlETF();
/*     */       }
/*     */ 
/*  42 */       if (StringUtils.isNotBlank(XMLEncoding)) {
/*  43 */         etf.getNode().getDocument().setXMLEncoding(XMLEncoding);
/*     */       }
/*  45 */       return etf;
/*     */     }
/*     */ 
/*  49 */     Object body = mess.getBody();
/*  50 */     if (body instanceof HiByteBuffer)
/*     */     {
/*  52 */       mess.setHeadItem("plain_type", "byte");
/*     */ 
/*  54 */       return HiETFFactory.createETF(((HiByteBuffer)body).toString());
/*     */     }
/*  56 */     if (body instanceof org.w3c.dom.Document)
/*     */     {
/*  58 */       mess.setHeadItem("plain_type", "xml");
/*     */ 
/*  60 */       return HiETFFactory.createXmlETF((org.w3c.dom.Document)body);
/*     */     }
/*  62 */     if (body instanceof byte[])
/*     */     {
/*  64 */       mess.setHeadItem("plain_type", "byte");
/*     */ 
/*  66 */       return HiETFFactory.createETF(new String((byte[])(byte[])body));
/*     */     }
/*     */ 
/*  71 */     throw new HiException("EN0010", "createBeforeMess:The Plain Message Type is not valid.");
/*     */   }
/*     */ 
/*     */   public Object createAfterMess(HiMessage mess)
/*     */     throws HiException
/*     */   {
/*  81 */     Object newSb = mess.getObjectHeadItem("PlainText");
/*     */ 
/*  83 */     HiXmlETF bodyRoot = (HiXmlETF)newSb;
/*  84 */     String plainType = mess.getHeadItem("plain_type");
/*  85 */     Logger log = HiLog.getLogger(mess);
/*  86 */     if (log.isInfoEnabled())
/*     */     {
/*  88 */       log.info(HiStringManager.getManager().getString("HiAbstractStrategy.afterConverMess1", String.valueOf(bodyRoot.getXmlString().length()), bodyRoot.getXmlString()));
/*     */     }
/*     */ 
/*  94 */     if (StringUtils.equalsIgnoreCase(plainType, "byte"))
/*     */     {
/*  97 */       return new HiByteBuffer(bodyRoot.getNode().getDocument().asXML().getBytes());
/*     */     }
/*  99 */     if (StringUtils.equalsIgnoreCase(plainType, "xml"))
/*     */     {
/* 102 */       return bodyRoot.toDOMDocument();
/*     */     }
/* 104 */     if (StringUtils.equalsIgnoreCase(plainType, "PLAIN_TYPE_ETF"))
/*     */     {
/* 107 */       return bodyRoot;
/*     */     }
/*     */ 
/* 111 */     throw new HiException("213340", plainType);
/*     */   }
/*     */ }