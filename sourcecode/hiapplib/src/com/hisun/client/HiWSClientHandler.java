 package com.hisun.client;
 
 import com.hisun.exception.HiException;
 import com.hisun.framework.event.IServerInitListener;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.message.HiXmlETF;
 import com.hisun.pubinterface.IHandler;
 import com.hisun.util.HiByteBuffer;
 
 public class HiWSClientHandler
   implements IHandler, IServerInitListener
 {
   private Logger _log = null;
 
   public void process(HiMessageContext ctx)
     throws HiException
   {
     WebServiceContext context = null;
     HiMessage mess = ctx.getCurrentMsg();
     Object bd = mess.getBody();
     if (!(bd instanceof HiXmlETF))
     {
       if (bd instanceof HiByteBuffer) {
         context = HiWebServiceContext.createContext(((HiByteBuffer)bd).toString());
       }
     }
     String processorName = context.getProcessor();
     WebServiceProcessor processor = null;
     if ((processorName == null) || (processorName.length() == 0))
     {
       processor = new HiWebServiceProcessor();
     }
 
     Object o = processor.send(context);
     this._log.info("WebService return :" + o);
     mess.setBody(new HiByteBuffer(((String)o).getBytes()));
   }
 
   public static void main(String[] s)
   {
     HiMessageContext c = new HiMessageContext();
 
     String s1 = "<?xml version='1.0' encoding='UTF-8'?><Root><namespace>http://server.webservice.core.epm</namespace><endpoint>http://192.168.101.200:7001/web/services/GenericServer?wsdl</endpoint><actionURL></actionURL><operationName>invoke</operationName><param name=\"path\">epm/ca/wsinterface/interfaces/service/FftService</param><param name=\"methodName\">PAYMENT</param><param name=\"dataXmlStr\"><DBSET><R><C N=\"DSDW\">B1050000</C><C N=\"JYLSH\">B105000020090205000000B009070760</C><C N=\"JFFS\">0201</C><C N=\"JZRQ\">20090205</C><C N=\"BARCODE\">528120060189221000060104        </C></R></DBSET></param></Root>";
     HiMessageContext message = new HiMessageContext();
     HiMessage msg = new HiMessage("TEST01", "PLTINO");
     message.setCurrentMsg(msg);
     msg.setBody(new HiByteBuffer(s1.getBytes()));
     try
     {
       new HiWSClientHandler().process(message);
     }
     catch (HiException e) {
       e.printStackTrace();
     }
   }
 
   public void serverInit(ServerEvent arg0) throws HiException
   {
     this._log = arg0.getLog();
   }
 }