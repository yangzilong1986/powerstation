/*     */ package com.hisun.mng;
/*     */ 
/*     */ import com.hisun.atc.common.HiArgUtils;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.handler.HiLogIDHandler;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.mon.HiMonitorEventInfo;
/*     */ import com.hisun.mon.HiMonitorEventInfoPool;
/*     */ import com.hisun.mon.HiRunStatInfo;
/*     */ import com.hisun.mon.HiRunStatInfoPool;
/*     */ import com.hisun.mon.HiRunTimeInfo;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.time.DateFormatUtils;
/*     */ 
/*     */ public class HiMonitorATC
/*     */ {
/*     */   public int GetStatInfo(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  47 */     HiRunStatInfoPool statInfoPool = HiRunStatInfoPool.getInstance();
/*     */ 
/*  49 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/*     */ 
/*  51 */     HiETF grpNod = root.addNode("TOT_STAT_INFO");
/*  52 */     statInfo2Etf(grpNod, statInfoPool.totalRunStatInfo());
/*     */ 
/*  54 */     grpNod = root.addNode("CUR_STAT_INFO");
/*  55 */     statInfo2Etf(grpNod, statInfoPool.curRunStatInfo());
/*     */ 
/*  58 */     int k = 1;
/*  59 */     Iterator iter = statInfoPool.last7DRunStatInfos();
/*  60 */     while (iter.hasNext()) {
/*  61 */       HiRunStatInfo statInfo = (HiRunStatInfo)iter.next();
/*  62 */       grpNod = root.addNode("LAST_7D_STAT_INFO_" + k);
/*  63 */       statInfo2Etf(grpNod, statInfo);
/*  64 */       ++k;
/*     */     }
/*  66 */     root.setChildValue("LAST_7D_STAT_INFO_NUM", String.valueOf(k - 1));
/*     */ 
/*  68 */     k = 1;
/*     */ 
/*  70 */     iter = statInfoPool.last100RunTimeInfos();
/*  71 */     while (iter.hasNext()) {
/*  72 */       HiRunTimeInfo timeInfo = (HiRunTimeInfo)iter.next();
/*  73 */       grpNod = root.addNode("LAST_100_RUN_INFO" + k);
/*  74 */       timeInfo2Etf(grpNod, timeInfo);
/*  75 */       ++k;
/*     */     }
/*  77 */     root.setChildValue("LAST_100_RUN_INFO_NUM", String.valueOf(k - 1));
/*  78 */     return 0;
/*     */   }
/*     */ 
/*     */   public int GetMonitorEventInfo(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  91 */     HiMonitorEventInfoPool eventInfoPool = HiMonitorEventInfoPool.getInstance();
/*     */ 
/*  93 */     HiMonitorEventInfo eventInfo = null;
/*  94 */     String grpNam = args.get("GrpNam");
/*  95 */     if (StringUtils.isBlank(grpNam)) {
/*  96 */       grpNam = "GRP";
/*     */     }
/*  98 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/*  99 */     int k = 1;
/* 100 */     Iterator iter = eventInfoPool.getEventInfo();
/* 101 */     while (iter.hasNext()) {
/* 102 */       eventInfo = (HiMonitorEventInfo)iter.next();
/* 103 */       HiETF grpNod = root.addNode(grpNam + "_" + k);
/* 104 */       eventInfo2ETF(eventInfo, grpNod);
/* 105 */       ++k;
/*     */     }
/* 107 */     return 0;
/*     */   }
/*     */ 
/*     */   private HiETF eventInfo2ETF(HiMonitorEventInfo eventInfo, HiETF grpNod) {
/* 111 */     grpNod.setChildValue("EXT_MSG", eventInfo.getExtMsg());
/* 112 */     grpNod.setChildValue("ID", eventInfo.getId());
/* 113 */     grpNod.setChildValue("LEVEL", eventInfo.getLevel());
/* 114 */     grpNod.setChildValue("MSG", eventInfo.getMsg());
/* 115 */     grpNod.setChildValue("ORIGIN", eventInfo.getOrigin());
/* 116 */     grpNod.setChildValue("SIP", eventInfo.getSip());
/* 117 */     grpNod.setChildValue("SUB_TYP", eventInfo.getSubType());
/* 118 */     grpNod.setChildValue("TYPE", eventInfo.getType());
/* 119 */     grpNod.setChildValue("TIME", DateFormatUtils.format(eventInfo.getTime(), "MM/dd HH:mm:ss"));
/* 120 */     return grpNod;
/*     */   }
/*     */ 
/*     */   private HiETF statInfo2Etf(HiETF grpNod, HiRunStatInfo statInfo) {
/* 124 */     grpNod.setChildValue("MAX_TM", String.valueOf(statInfo.getMaxTime()));
/* 125 */     grpNod.setChildValue("MIN_TM", String.valueOf(statInfo.getMinTime()));
/* 126 */     grpNod.setChildValue("AVG_TM", String.valueOf((int)(statInfo.getAvgTime() * 100.0D) / 100.0D));
/* 127 */     grpNod.setChildValue("SYS_DT", DateFormatUtils.format(statInfo.getLastSysTm(), "MM/dd HH:mm:ss"));
/* 128 */     Iterator iter = statInfo.getProcStatMap().keySet().iterator();
/* 129 */     while (iter.hasNext()) {
/* 130 */       String name = (String)iter.next();
/* 131 */       Long value = (Long)statInfo.getProcStatMap().get(name);
/* 132 */       grpNod.setChildValue(name, String.valueOf(value.intValue()));
/*     */     }
/* 134 */     return grpNod;
/*     */   }
/*     */ 
/*     */   private HiETF timeInfo2Etf(HiETF grpNod, HiRunTimeInfo timeInfo)
/*     */   {
/*     */     Long value;
/* 140 */     grpNod.setChildValue("ELAPSE_TM", String.valueOf(timeInfo.getElapseTm()));
/* 141 */     grpNod.setChildValue("SYS_TM", DateFormatUtils.format(timeInfo.getSysTm(), "MM/dd HH:mm:ss"));
/* 142 */     grpNod.setChildValue("ID", timeInfo.getId());
/* 143 */     grpNod.setChildValue("MSG_ID", timeInfo.getMsgId());
/* 144 */     grpNod.setChildValue("MSG_CD", timeInfo.getMsgCod());
/* 145 */     grpNod.setChildValue("MSG_TYP", timeInfo.getMsgTyp());
/* 146 */     if (timeInfo.getExtMap() == null) {
/* 147 */       return grpNod;
/*     */     }
/* 149 */     Iterator iter = timeInfo.getExtMap().keySet().iterator();
/* 150 */     while (iter.hasNext()) {
/* 151 */       String name = (String)iter.next();
/* 152 */       value = (Long)timeInfo.getExtMap().get(name);
/*     */     }
/*     */ 
/* 155 */     return grpNod;
/*     */   }
/*     */ 
/*     */   public static int SetLogIDLogLevel(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 168 */     String logID = HiArgUtils.getStringNotNull(argsMap, "LogID");
/* 169 */     String logLvl = HiArgUtils.getStringNotNull(argsMap, "LogLvl");
/* 170 */     HiContext rootCtx = HiContext.getRootContext();
/* 171 */     ConcurrentHashMap logIDMap = HiLogIDHandler.getLogIDMap();
/* 172 */     logIDMap.put(logID, logLvl);
/* 173 */     return 0;
/*     */   }
/*     */ }