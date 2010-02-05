 package com.hisun.mng;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import java.util.List;
 import java.util.Set;
 import org.apache.commons.lang.StringUtils;
 
 public class HiManagement extends HiMngUtils
 {
   public int MonLogin(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     return 0;
   }
 
   private int doServerMonControl(String onoff, String sip, String sport) {
     if (StringUtils.equals(onoff, "1")) {
       if (0 == addServerSendNode(sip, sport))
         break label44;
       return -1;
     }
     if (StringUtils.equals(onoff, "0")) {
       if (0 == delServerSendNode(sip, sport))
         break label44;
       return -1;
     }
 
     return -1;
 
     label44: return 0;
   }
 
   private int delServerSendNode(String sip, String sport) {
     ipTable.remove(new HiMngUtils.IP_PORT(this, sip, sport));
     return 0;
   }
 
   private int addServerSendNode(String sip, String sport) {
     ipTable.add(new HiMngUtils.IP_PORT(this, sip, sport));
     return 0;
   }
 
   public int doBootServer(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     HiETF etf = msg.getETFBody();
 
     Logger log = HiLog.getLogger(msg);
     return 0;
   }
 
   public int doHaltServer(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     return 0;
   }
 
   public int doOpenServerLog(HiATLParam argsMap, HiMessageContext ctx) throws HiException {
     return 0;
   }
 
   public int doCloseServerLog(HiATLParam argsMap, HiMessageContext ctx) throws HiException {
     return 0;
   }
 
   public int getServerInfo(HiATLParam argsMap, HiMessageContext ctx) throws HiException {
     return 0;
   }
 
   public int doServerReload(HiATLParam argsMap, HiMessageContext ctx) throws HiException {
     return 0;
   }
 
   public int getSystemConf(HiATLParam argsMap, HiMessageContext ctx) throws HiException {
     return 0;
   }
 
   public int doSystemConf(HiATLParam argsMap, HiMessageContext ctx) throws HiException {
     return 0;
   }
 
   private int doServerControl(HiETF etf) {
     List list = etf.getChildNodes("server");
 
     return 0;
   }
 }