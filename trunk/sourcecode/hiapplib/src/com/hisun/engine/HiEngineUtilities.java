 package com.hisun.engine;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 import edu.emory.mathcs.backport.java.util.concurrent.ConcurrentHashMap;
 import org.apache.commons.lang.StringUtils;
 
 public class HiEngineUtilities
 {
   private static final String CUR_FLOW_STEP = "CurFlowStep";
 
   public static boolean isInnerMessage(HiMessage mess)
     throws HiException
   {
     Logger log = HiLog.getLogger(mess);
     String strType = mess.getHeadItem("ECT");
     if (strType == null) {
       throw new HiException("213332", "ECT");
     }
 
     return "text/etf".equals(strType);
   }
 
   private static void processBAS(String strName, Object value, boolean isSet, String strSign, HiMessageContext messContext)
   {
     String strKey = StringUtils.substringAfter(strName, strSign);
     if (isSet)
       messContext.setBaseSource(strKey, value);
     else
       messContext.removeBaseSource(strKey);
   }
 
   private static void processETF(HiMessage mess, String strName, Object value, boolean isSet, String strSign)
   {
     String strKey = StringUtils.substringAfter(strName, strSign);
     HiETF etf = (HiETF)mess.getBody();
     if (isSet)
       etf.setGrandChildNode(strKey, value.toString());
     else
       etf.removeChildNode(strKey);
   }
 
   public static void processFlow(String strName, Object value, boolean isSet, HiMessageContext messContext)
     throws HiException
   {
     HiMessage mess = messContext.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
 
     if (log.isDebugEnabled()) {
       if (value == null) {
         log.debug(HiStringManager.getManager().getString("HiEngineUtilities.processFlow", strName));
       }
       else {
         log.debug(HiStringManager.getManager().getString("HiEngineUtilities.processFlow1", strName, value));
       }
     }
 
     if (strName.startsWith("@ETF")) {
       processETF(mess, strName, value, isSet, "@ETF.");
     } else if (strName.startsWith("$")) {
       processETF(mess, strName, value, isSet, "$");
     } else if (strName.startsWith("@BAS")) {
       processBAS(strName, value, isSet, "@BAS.", messContext);
     }
     else if (strName.startsWith("~")) {
       processBAS(strName, value, isSet, "~", messContext);
     } else if (strName.startsWith("@MSG")) {
       processMess(mess, strName, value, isSet, "@MSG.");
     } else if (strName.startsWith("%")) {
       processMess(mess, strName, value, isSet, "%"); } else {
       if (strName.startsWith("@BCFG"))
         return;
       if (strName.startsWith("@PARA")) {
         messContext.setPara(StringUtils.substringAfter(strName, "@PARA."), value);
       } else if (strName.startsWith("#")) {
         messContext.setPara(StringUtils.substringAfter(strName, "#"), value);
       } else if (strName.startsWith("@")) {
         setValueToDS(messContext, strName, value);
       } else {
         HiETF etf = (HiETF)mess.getBody();
         if (isSet)
           if (value == null)
             etf.setGrandChildNode(strName, "");
           else
             etf.setGrandChildNode(strName, value.toString());
         else
           etf.removeGrandChild(strName); 
       }
     }
   }
 
   private static void setValueToDS(HiMessageContext ctx, String name, Object value) throws HiException {
     String key = null;
 
     int idx = name.indexOf(".");
     Object o = ctx.getBaseSource(name.substring(1, idx));
     key = name.substring(idx + 1);
     if (o instanceof HiETF)
       ((HiETF)o).setGrandChildNode(key, value.toString());
     else if (o instanceof HiMessage)
       ((HiMessage)o).setHeadItem(key, value);
     else if (o instanceof ConcurrentHashMap)
       ((ConcurrentHashMap)o).put(key, value);
     else
       throw new HiException("220320", name.substring(1, idx));
   }
 
   private static void processMess(HiMessage mess, String strName, Object value, boolean isSet, String strSign)
   {
     String strKey = StringUtils.substringAfter(strName, strSign);
     if (isSet)
       mess.setHeadItem(strKey, value);
     else
       mess.delHeadItem(strKey);
   }
 
   public static String getCurFlowStep() {
     String flowStep = HiMessageContext.getCurrentMessageContext().getStrProp("CurFlowStep");
 
     if (flowStep == null)
       flowStep = "1";
     return StringUtils.leftPad(flowStep, 2, '0');
   }
 
   public static void setCurFlowStep(int i) {
     HiMessageContext ctx = HiMessageContext.getCurrentMessageContext();
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (log.isInfoEnabled())
       HiMessageContext.getCurrentMessageContext().setProperty("CurFlowStep", String.valueOf(i + 1));
   }
 
   public static void timeoutCheck(HiMessage message)
     throws HiException
   {
     Logger log = HiLog.getLogger(message);
     if (log.isDebugEnabled()) {
       log.debug("HiRouterOut.timeoutCheck() - start");
     }
 
     Object o = message.getObjectHeadItem("ETM");
     if (!(o instanceof Long)) {
       if (log.isDebugEnabled()) {
         log.debug("HiRouterOut.timeoutCheck() - end");
       }
       return;
     }
     long etm = ((Long)o).longValue();
     if ((etm > 0L) && (System.currentTimeMillis() > etm)) {
       throw new HiException("212001");
     }
 
     if (log.isDebugEnabled())
       log.debug("HiRouterOut.timeoutCheck() - end");
   }
 }