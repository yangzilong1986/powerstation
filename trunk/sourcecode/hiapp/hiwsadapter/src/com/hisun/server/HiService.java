 package com.hisun.server;
 
 import com.hisun.dispatcher.HiRouterOut;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.message.HiXmlETF;
 import java.io.PrintStream;
 import java.util.HashMap;
 import javax.xml.soap.SOAPBody;
 import javax.xml.soap.SOAPEnvelope;
 import javax.xml.soap.SOAPException;
 import org.apache.axis.MessageContext;
 import org.apache.axis.constants.Style;
 import org.apache.axis.constants.Use;
 import org.w3c.dom.Node;
 
 public class HiService
 {
   private static final Logger logger = Logger.getLogger(HiService.class);
   public static final String reality_service = "REALITY_SERVICE";
   public static final String service_maps = "SERVICE_MAPS";
 
   public void process(SOAPEnvelope req, SOAPEnvelope resp)
     throws SOAPException
   {
     if (logger.isDebugEnabled())
     {
       logger.debug("process(SOAPEnvelope, SOAPEnvelope) - start");
     }
 
     SOAPBody reqBody = req.getBody();
     try
     {
       System.out.println("HiService is start[" + req);
       System.out.println("*********************");
       System.out.println(MessageContext.getCurrentContext().getOperationStyle().getName());
 
       System.out.println(MessageContext.getCurrentContext().getOperationUse().getName());
 
       System.out.println("name[" + req.getClass().getName());
 
       HashMap serviceMap = (HashMap)MessageContext.getCurrentContext().getProperty("SERVICE_MAPS");
 
       String strServiceName = (String)MessageContext.getCurrentContext().getProperty("REALITY_SERVICE");
 
       System.out.println("strServiceName[" + strServiceName + "]");
 
       String strMethodName = reqBody.getFirstChild().getLocalName();
 
       System.out.println("operation[" + strMethodName + "]");
 
       HashMap operMap = (HashMap)serviceMap.get(strServiceName);
       String strCode = (String)operMap.get(strMethodName);
 
       System.out.println("strCode[" + strCode + "]");
 
       HiMessage mess = new HiMessage("axis", "PLTN0");
       System.out.println("HiMessage[" + mess + "]");
       Logger log = HiLog.getLogger(mess);
       if (log.isDebugEnabled())
       {
         log.debug("request body[" + reqBody + "]");
       }
       mess.setHeadItem("STC", strCode);
 
       mess.setBody(reqBody.getOwnerDocument());
 
       mess.setHeadItem("ECT", "text/xml");
       mess.setHeadItem("SCH", "rq");
       mess.setHeadItem("STM", new Long(System.currentTimeMillis()));
 
       HiMessageContext messContext = new HiMessageContext();
       messContext.setCurrentMsg(mess);
       HiMessageContext.setCurrentMessageContext(messContext);
       HiRouterOut.process(messContext);
 
       if (mess.getBody() instanceof HiXmlETF)
       {
         resp.getBody().addDocument(((HiXmlETF)mess.getBody()).toDOMDocument());
       }
 
     }
     catch (Throwable e)
     {
       logger.error("process(SOAPEnvelope, SOAPEnvelope)", e);
 
       e.printStackTrace();
       throw new SOAPException(e);
     }
 
     if (!(logger.isDebugEnabled()))
       return;
     logger.debug("process(SOAPEnvelope, SOAPEnvelope) - end");
   }
 }