/*    */ package com.hisun.mng.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiETFFactory;
/*    */ import com.hisun.message.HiMessage;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.dom4j.Document;
/*    */ import org.dom4j.DocumentException;
/*    */ import org.dom4j.DocumentHelper;
/*    */ import org.dom4j.Element;
/*    */ 
/*    */ public class MngByte2XMLHandler
/*    */ {
/*    */   public static final String MNGTYPE = "MNG_TYPE";
/*    */   public String logLevel;
/*    */ 
/*    */   public void setLogLevel(String logLevel)
/*    */   {
/* 17 */     this.logLevel = logLevel;
/*    */   }
/*    */ 
/*    */   public HiMessage process(HiMessage msg) throws HiException {
/* 21 */     byte[] data = (byte[])(byte[])msg.getBody();
/* 22 */     String sdata = new String(data);
/* 23 */     String mngtype = StringUtils.substringBefore(sdata, "<");
/*    */ 
/* 25 */     msg.setHeadItem("MNG_TYPE", mngtype);
/*    */ 
/* 28 */     msg.setHeadItem("ECT", "text/xml");
/* 29 */     if (StringUtils.isNotBlank(this.logLevel)) {
/* 30 */       msg.setHeadItem("STF", this.logLevel);
/*    */     }
/*    */ 
/* 33 */     sdata = StringUtils.substringAfter(sdata, mngtype);
/*    */ 
/* 36 */     Element xmlContent = null;
/*    */     try {
/* 38 */       xmlContent = DocumentHelper.parseText(sdata).getRootElement();
/*    */     } catch (DocumentException e) {
/* 40 */       throw new HiException("CO0010", "Build ETF Tree failure from text.", e);
/*    */     }
/*    */ 
/* 44 */     msg.setBody(HiETFFactory.createETF());
/*    */ 
/* 47 */     String code = xmlContent.elementText("CODE");
/* 48 */     msg.setHeadItem("STC", code);
/* 49 */     msg.setHeadItem("MNGXML", xmlContent);
/* 50 */     return msg;
/*    */   }
/*    */ }