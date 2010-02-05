 package com.hisun.engine.invoke;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETFFactory;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.message.HiXmlETF;
 import com.hisun.util.HiByteBuffer;
 import com.hisun.util.HiStringManager;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Element;
 
 public class HiETFStategy extends HiAbstractStrategy
 {
   public Object createBeforeMess(HiMessage mess, boolean isInnerMess)
     throws HiException
   {
     if (isInnerMess)
     {
       if (!(mess.hasHeadItem("plain_type"))) {
         mess.setHeadItem("plain_type", "byte");
       }
       String XMLEncoding = HiMessageContext.getCurrentMessageContext().getStrProp("NSDECLARE", "XMLEncoding");
       String rootName = HiMessageContext.getCurrentMessageContext().getStrProp("NSDECLARE", "RootName");
       HiXmlETF etf = null;
       if (StringUtils.isNotBlank(rootName)) {
         etf = new HiXmlETF(rootName, "");
         etf.getNode().setName(rootName);
       } else {
         etf = (HiXmlETF)HiETFFactory.createXmlETF();
       }
 
       if (StringUtils.isNotBlank(XMLEncoding)) {
         etf.getNode().getDocument().setXMLEncoding(XMLEncoding);
       }
       return etf;
     }
 
     Object body = mess.getBody();
     if (body instanceof HiByteBuffer)
     {
       mess.setHeadItem("plain_type", "byte");
 
       return HiETFParse.parseTextETF(((HiByteBuffer)body).toString());
     }
     if (body instanceof org.w3c.dom.Document)
     {
       mess.setHeadItem("plain_type", "xml");
 
       return HiETFFactory.createXmlETF((org.w3c.dom.Document)body);
     }
     if (body instanceof byte[])
     {
       mess.setHeadItem("plain_type", "byte");
 
       return HiETFParse.parseTextETF(new String((byte[])(byte[])body));
     }
 
     throw new HiException("EN0010", "createBeforeMess:The Plain Message Type is not valid.");
   }
 
   public Object createAfterMess(HiMessage mess)
     throws HiException
   {
     Object newSb = mess.getObjectHeadItem("PlainText");
 
     HiXmlETF bodyRoot = (HiXmlETF)newSb;
     String plainType = mess.getHeadItem("plain_type");
     Logger log = HiLog.getLogger(mess);
     if (log.isInfoEnabled())
     {
       log.info(HiStringManager.getManager().getString("HiAbstractStrategy.afterConverMess1", String.valueOf(bodyRoot.getXmlString().length()), bodyRoot.getXmlString()));
     }
 
     if (StringUtils.equalsIgnoreCase(plainType, "byte"))
     {
       return new HiByteBuffer(bodyRoot.getNode().getDocument().asXML().getBytes());
     }
     if (StringUtils.equalsIgnoreCase(plainType, "xml"))
     {
       return bodyRoot.toDOMDocument();
     }
     if (StringUtils.equalsIgnoreCase(plainType, "PLAIN_TYPE_ETF"))
     {
       return bodyRoot;
     }
 
     throw new HiException("213340", plainType);
   }
 }