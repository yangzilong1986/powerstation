/*    */ package com.hisun.client;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.framework.event.IServerInitListener;
/*    */ import com.hisun.framework.event.ServerEvent;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.message.HiXmlETF;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ 
/*    */ public class HiWSClientHandler
/*    */   implements IHandler, IServerInitListener
/*    */ {
/* 14 */   private Logger _log = null;
/*    */ 
/*    */   public void process(HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 22 */     WebServiceContext context = null;
/* 23 */     HiMessage mess = ctx.getCurrentMsg();
/* 24 */     Object bd = mess.getBody();
/* 25 */     if (!(bd instanceof HiXmlETF))
/*    */     {
/* 27 */       if (bd instanceof HiByteBuffer) {
/* 28 */         context = HiWebServiceContext.createContext(((HiByteBuffer)bd).toString());
/*    */       }
/*    */     }
/* 31 */     String processorName = context.getProcessor();
/* 32 */     WebServiceProcessor processor = null;
/* 33 */     if ((processorName == null) || (processorName.length() == 0))
/*    */     {
/* 39 */       processor = new HiWebServiceProcessor();
/*    */     }
/*    */ 
/* 42 */     Object o = processor.send(context);
/* 43 */     this._log.info("WebService return :" + o);
/* 44 */     mess.setBody(new HiByteBuffer(((String)o).getBytes()));
/*    */   }
/*    */ 
/*    */   public static void main(String[] s)
/*    */   {
/* 49 */     HiMessageContext c = new HiMessageContext();
/*    */ 
/* 54 */     String s1 = "<?xml version='1.0' encoding='UTF-8'?><Root><namespace>http://server.webservice.core.epm</namespace><endpoint>http://192.168.101.200:7001/web/services/GenericServer?wsdl</endpoint><actionURL></actionURL><operationName>invoke</operationName><param name=\"path\">epm/ca/wsinterface/interfaces/service/FftService</param><param name=\"methodName\">PAYMENT</param><param name=\"dataXmlStr\"><DBSET><R><C N=\"DSDW\">B1050000</C><C N=\"JYLSH\">B105000020090205000000B009070760</C><C N=\"JFFS\">0201</C><C N=\"JZRQ\">20090205</C><C N=\"BARCODE\">528120060189221000060104        </C></R></DBSET></param></Root>";
/* 55 */     HiMessageContext message = new HiMessageContext();
/* 56 */     HiMessage msg = new HiMessage("TEST01", "PLTINO");
/* 57 */     message.setCurrentMsg(msg);
/* 58 */     msg.setBody(new HiByteBuffer(s1.getBytes()));
/*    */     try
/*    */     {
/* 61 */       new HiWSClientHandler().process(message);
/*    */     }
/*    */     catch (HiException e) {
/* 64 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public void serverInit(ServerEvent arg0) throws HiException
/*    */   {
/* 70 */     this._log = arg0.getLog();
/*    */   }
/*    */ }