/*    */ package com.hisun.server;
/*    */ 
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import java.util.HashMap;
/*    */ import org.apache.axis.AxisFault;
/*    */ import org.apache.axis.Constants;
/*    */ import org.apache.axis.MessageContext;
/*    */ import org.apache.axis.handlers.BasicHandler;
/*    */ import org.apache.axis.utils.Messages;
/*    */ 
/*    */ public class HiAdapterHandler extends BasicHandler
/*    */ {
/* 16 */   protected static Logger log = HiLog.getLogger("SYS.trc");
/*    */ 
/*    */   public void invoke(MessageContext msgContext) throws AxisFault
/*    */   {
/*    */     try
/*    */     {
/* 22 */       String strOldTargerSer = msgContext.getTargetService();
/*    */ 
/* 24 */       msgContext.setProperty("REALITY_SERVICE", strOldTargerSer);
/*    */ 
/* 26 */       String strFileName = (String)getOption("fileName");
/* 27 */       HashMap serviceMap = HiParseServices.getDefaultParseService(strFileName).getServiceMap();
/*    */ 
/* 30 */       if (!(serviceMap.containsKey(strOldTargerSer))) {
/* 31 */         throw new AxisFault(Constants.QNAME_NO_SERVICE_FAULT_CODE, Messages.getMessage("noService05", "" + strOldTargerSer), null, null);
/*    */       }
/*    */ 
/* 35 */       msgContext.setProperty("SERVICE_MAPS", serviceMap);
/*    */ 
/* 37 */       msgContext.setTargetService("IcsService");
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 41 */       log.error(Messages.getMessage("exception00"), e);
/* 42 */       throw AxisFault.makeFault(e);
/*    */     }
/*    */   }
/*    */ }