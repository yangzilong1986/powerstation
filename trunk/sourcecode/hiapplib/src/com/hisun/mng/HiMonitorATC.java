 package com.hisun.mng;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.exception.HiException;
 import com.hisun.handler.HiLogIDHandler;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.mon.HiMonitorEventInfo;
 import com.hisun.mon.HiMonitorEventInfoPool;
 import com.hisun.mon.HiRunStatInfo;
 import com.hisun.mon.HiRunStatInfoPool;
 import com.hisun.mon.HiRunTimeInfo;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.Set;
 import java.util.concurrent.ConcurrentHashMap;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.time.DateFormatUtils;
 
 public class HiMonitorATC
 {
   public int GetStatInfo(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiRunStatInfoPool statInfoPool = HiRunStatInfoPool.getInstance();
 
     HiETF root = ctx.getCurrentMsg().getETFBody();
 
     HiETF grpNod = root.addNode("TOT_STAT_INFO");
     statInfo2Etf(grpNod, statInfoPool.totalRunStatInfo());
 
     grpNod = root.addNode("CUR_STAT_INFO");
     statInfo2Etf(grpNod, statInfoPool.curRunStatInfo());
 
     int k = 1;
     Iterator iter = statInfoPool.last7DRunStatInfos();
     while (iter.hasNext()) {
       HiRunStatInfo statInfo = (HiRunStatInfo)iter.next();
       grpNod = root.addNode("LAST_7D_STAT_INFO_" + k);
       statInfo2Etf(grpNod, statInfo);
       ++k;
     }
     root.setChildValue("LAST_7D_STAT_INFO_NUM", String.valueOf(k - 1));
 
     k = 1;
 
     iter = statInfoPool.last100RunTimeInfos();
     while (iter.hasNext()) {
       HiRunTimeInfo timeInfo = (HiRunTimeInfo)iter.next();
       grpNod = root.addNode("LAST_100_RUN_INFO" + k);
       timeInfo2Etf(grpNod, timeInfo);
       ++k;
     }
     root.setChildValue("LAST_100_RUN_INFO_NUM", String.valueOf(k - 1));
     return 0;
   }
 
   public int GetMonitorEventInfo(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiMonitorEventInfoPool eventInfoPool = HiMonitorEventInfoPool.getInstance();
 
     HiMonitorEventInfo eventInfo = null;
     String grpNam = args.get("GrpNam");
     if (StringUtils.isBlank(grpNam)) {
       grpNam = "GRP";
     }
     HiETF root = ctx.getCurrentMsg().getETFBody();
     int k = 1;
     Iterator iter = eventInfoPool.getEventInfo();
     while (iter.hasNext()) {
       eventInfo = (HiMonitorEventInfo)iter.next();
       HiETF grpNod = root.addNode(grpNam + "_" + k);
       eventInfo2ETF(eventInfo, grpNod);
       ++k;
     }
     return 0;
   }
 
   private HiETF eventInfo2ETF(HiMonitorEventInfo eventInfo, HiETF grpNod) {
     grpNod.setChildValue("EXT_MSG", eventInfo.getExtMsg());
     grpNod.setChildValue("ID", eventInfo.getId());
     grpNod.setChildValue("LEVEL", eventInfo.getLevel());
     grpNod.setChildValue("MSG", eventInfo.getMsg());
     grpNod.setChildValue("ORIGIN", eventInfo.getOrigin());
     grpNod.setChildValue("SIP", eventInfo.getSip());
     grpNod.setChildValue("SUB_TYP", eventInfo.getSubType());
     grpNod.setChildValue("TYPE", eventInfo.getType());
     grpNod.setChildValue("TIME", DateFormatUtils.format(eventInfo.getTime(), "MM/dd HH:mm:ss"));
     return grpNod;
   }
 
   private HiETF statInfo2Etf(HiETF grpNod, HiRunStatInfo statInfo) {
     grpNod.setChildValue("MAX_TM", String.valueOf(statInfo.getMaxTime()));
     grpNod.setChildValue("MIN_TM", String.valueOf(statInfo.getMinTime()));
     grpNod.setChildValue("AVG_TM", String.valueOf((int)(statInfo.getAvgTime() * 100.0D) / 100.0D));
     grpNod.setChildValue("SYS_DT", DateFormatUtils.format(statInfo.getLastSysTm(), "MM/dd HH:mm:ss"));
     Iterator iter = statInfo.getProcStatMap().keySet().iterator();
     while (iter.hasNext()) {
       String name = (String)iter.next();
       Long value = (Long)statInfo.getProcStatMap().get(name);
       grpNod.setChildValue(name, String.valueOf(value.intValue()));
     }
     return grpNod;
   }
 
   private HiETF timeInfo2Etf(HiETF grpNod, HiRunTimeInfo timeInfo)
   {
     Long value;
     grpNod.setChildValue("ELAPSE_TM", String.valueOf(timeInfo.getElapseTm()));
     grpNod.setChildValue("SYS_TM", DateFormatUtils.format(timeInfo.getSysTm(), "MM/dd HH:mm:ss"));
     grpNod.setChildValue("ID", timeInfo.getId());
     grpNod.setChildValue("MSG_ID", timeInfo.getMsgId());
     grpNod.setChildValue("MSG_CD", timeInfo.getMsgCod());
     grpNod.setChildValue("MSG_TYP", timeInfo.getMsgTyp());
     if (timeInfo.getExtMap() == null) {
       return grpNod;
     }
     Iterator iter = timeInfo.getExtMap().keySet().iterator();
     while (iter.hasNext()) {
       String name = (String)iter.next();
       value = (Long)timeInfo.getExtMap().get(name);
     }
 
     return grpNod;
   }
 
   public static int SetLogIDLogLevel(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String logID = HiArgUtils.getStringNotNull(argsMap, "LogID");
     String logLvl = HiArgUtils.getStringNotNull(argsMap, "LogLvl");
     HiContext rootCtx = HiContext.getRootContext();
     ConcurrentHashMap logIDMap = HiLogIDHandler.getLogIDMap();
     logIDMap.put(logID, logLvl);
     return 0;
   }
 }