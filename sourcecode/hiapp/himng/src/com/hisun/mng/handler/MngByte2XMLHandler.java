 package com.hisun.mng.handler;
 
 import com.hisun.exception.HiException;
 import com.hisun.message.HiETFFactory;
 import com.hisun.message.HiMessage;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.DocumentHelper;
 import org.dom4j.Element;
 
 public class MngByte2XMLHandler
 {
   public static final String MNGTYPE = "MNG_TYPE";
   public String logLevel;
 
   public void setLogLevel(String logLevel)
   {
     this.logLevel = logLevel;
   }
 
   public HiMessage process(HiMessage msg) throws HiException {
     byte[] data = (byte[])(byte[])msg.getBody();
     String sdata = new String(data);
     String mngtype = StringUtils.substringBefore(sdata, "<");
 
     msg.setHeadItem("MNG_TYPE", mngtype);
 
     msg.setHeadItem("ECT", "text/xml");
     if (StringUtils.isNotBlank(this.logLevel)) {
       msg.setHeadItem("STF", this.logLevel);
     }
 
     sdata = StringUtils.substringAfter(sdata, mngtype);
 
     Element xmlContent = null;
     try {
       xmlContent = DocumentHelper.parseText(sdata).getRootElement();
     } catch (DocumentException e) {
       throw new HiException("CO0010", "Build ETF Tree failure from text.", e);
     }
 
     msg.setBody(HiETFFactory.createETF());
 
     String code = xmlContent.elementText("CODE");
     msg.setHeadItem("STC", code);
     msg.setHeadItem("MNGXML", xmlContent);
     return msg;
   }
 }