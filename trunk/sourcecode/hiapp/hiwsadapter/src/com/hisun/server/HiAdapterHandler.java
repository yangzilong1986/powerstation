 package com.hisun.server;
 
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import java.util.HashMap;
 import org.apache.axis.AxisFault;
 import org.apache.axis.Constants;
 import org.apache.axis.MessageContext;
 import org.apache.axis.handlers.BasicHandler;
 import org.apache.axis.utils.Messages;
 
 public class HiAdapterHandler extends BasicHandler
 {
   protected static Logger log = HiLog.getLogger("SYS.trc");
 
   public void invoke(MessageContext msgContext) throws AxisFault
   {
     try
     {
       String strOldTargerSer = msgContext.getTargetService();
 
       msgContext.setProperty("REALITY_SERVICE", strOldTargerSer);
 
       String strFileName = (String)getOption("fileName");
       HashMap serviceMap = HiParseServices.getDefaultParseService(strFileName).getServiceMap();
 
       if (!(serviceMap.containsKey(strOldTargerSer))) {
         throw new AxisFault(Constants.QNAME_NO_SERVICE_FAULT_CODE, Messages.getMessage("noService05", "" + strOldTargerSer), null, null);
       }
 
       msgContext.setProperty("SERVICE_MAPS", serviceMap);
 
       msgContext.setTargetService("IcsService");
     }
     catch (Exception e)
     {
       log.error(Messages.getMessage("exception00"), e);
       throw AxisFault.makeFault(e);
     }
   }
 }