/*     */ package com.hisun.mon.atc;
/*     */ 
/*     */ import com.hisun.atc.common.HiArgUtils;
/*     */ import com.hisun.atc.common.HiFile;
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiXmlHelper;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.time.DateFormatUtils;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ public class SearchEvent
/*     */ {
/*     */   public int execute(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  30 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*  31 */     String srcDir = HiArgUtils.getStringNotNull(args, "dir");
/*     */ 
/*  33 */     String sys_nm = HiArgUtils.getStringNotNull(args, "sys_nm");
/*     */ 
/*  35 */     String monevttbl = HiArgUtils.getStringNotNull(args, "evttbl");
/*  36 */     String monevtconfig = HiArgUtils.getStringNotNull(args, "evtconfig");
/*     */ 
/*  38 */     String lastLogTim = args.get("lastLogTim");
/*     */ 
/*  40 */     HiFile file = new HiFile();
/*     */ 
/*  42 */     List eventList = new ArrayList();
/*  43 */     Element rootConfig = null;
/*  44 */     Element logsNode = null;
/*  45 */     rootConfig = (Element)ctx.getProperty("CONFIGDECLARE." + monevtconfig.toUpperCase());
/*  46 */     if (rootConfig == null) {
/*  47 */       throw new HiException("220036", "ConfigDeclare[" + monevtconfig + "] not find!");
/*     */     }
/*     */ 
/*  50 */     logsNode = rootConfig.element("Logs");
/*  51 */     if ((logsNode == null) || (logsNode.elements().size() <= 0)) {
/*  52 */       log.warn("Not Any Logs Node find on config!");
/*  53 */       return 0;
/*     */     }
/*     */ 
/*  56 */     Calendar calendar = Calendar.getInstance();
/*  57 */     String curDate = DateFormatUtils.format(calendar.getTime(), "yyyy-MM-dd");
/*  58 */     String curDate2 = StringUtils.remove(curDate, '-');
/*     */ 
/*  60 */     Iterator logIt = logsNode.elementIterator("Log");
/*  61 */     Element logItem = null;
/*     */ 
/*  63 */     String fileName = null;
/*  64 */     String srcFile = null;
/*     */ 
/*  67 */     break label516:
/*  68 */     logItem = (Element)logIt.next();
/*  69 */     eventList.clear();
/*     */ 
/*  71 */     fileName = logItem.attributeValue("name");
/*  72 */     if (fileName != null) if (fileName.length() != 0)
/*     */       {
/*  75 */         String event = logItem.attributeValue("event");
/*  76 */         if (event != null) if (event.length() != 0)
/*     */           {
/*  79 */             String[] events = StringUtils.split(event, '|');
/*     */ 
/*  81 */             for (int i = 0; i < events.length; ++i) {
/*  82 */               Element ListNode = HiXmlHelper.getNodeByAttr(rootConfig, "List", "name", events[i]);
/*  83 */               if (ListNode == null) {
/*     */                 continue;
/*     */               }
/*  86 */               eventList.add(ListNode);
/*     */             }
/*     */ 
/*  90 */             if (eventList.size() != 0)
/*     */             {
/*  93 */               srcFile = HiArgUtils.absoutePath(srcDir + fileName);
/*     */               try {
/*  95 */                 file.open(srcFile, "r");
/*     */               }
/*     */               catch (HiException e) {
/*  98 */                 log.error(e.getAppMessage());
/*     */ 
/* 100 */                 break label516: } } }
/*     */       }
/*     */     try {
/* 103 */       lastLogTim = getLastLogTime(ctx, fileName, sys_nm, monevttbl, curDate2);
/* 104 */       label516: SearchEventFromLog(ctx, eventList, file, fileName, lastLogTim, sys_nm, monevttbl, curDate, curDate2);
/*     */     } catch (Exception e) {
/* 106 */       throw HiException.makeException(e);
/*     */     } finally {
/*     */       try {
/* 109 */         file.close();
/*     */       } catch (HiException e) {
/* 111 */         log.error(e.getAppMessage());
/*     */ 
/* 113 */         break label516:
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 119 */     return 0;
/*     */   }
/*     */ 
/*     */   private String getLastLogTime(HiMessageContext ctx, String file, String sys_nm, String monevttbl, String curDate)
/*     */     throws HiException
/*     */   {
/* 125 */     StringBuffer sqlbuf = new StringBuffer();
/* 126 */     sqlbuf.append("SELECT * FROM (SELECT TIME LOPR_TIM FROM ");
/* 127 */     sqlbuf.append(monevttbl);
/* 128 */     sqlbuf.append(" WHERE SYS_NM='");
/* 129 */     sqlbuf.append(sys_nm);
/* 130 */     sqlbuf.append("' AND TXN_DAT='");
/* 131 */     sqlbuf.append(curDate);
/* 132 */     sqlbuf.append("' AND LOG_TYPE='");
/* 133 */     sqlbuf.append(file);
/* 134 */     sqlbuf.append("' order by LOPR_TIM desc) WHERE ROWNUM=1");
/* 135 */     List list = ctx.getDataBaseUtil().execQuery(sqlbuf.toString());
/* 136 */     if ((list != null) && (list.size() != 0)) {
/* 137 */       Map map = (HashMap)list.get(0);
/* 138 */       return ((String)map.get("LOPR_TIM"));
/*     */     }
/* 140 */     return null;
/*     */   }
/*     */ 
/*     */   private void SearchEventFromLog(HiMessageContext ctx, List eventsNode, HiFile file, String logType, String lastLogTim, String sys_nm, String monevttbl, String curDate, String curDate2)
/*     */     throws HiException
/*     */   {
/* 153 */     String exceptType = "1";
/*     */ 
/* 158 */     Iterator it = null;
/*     */ 
/* 160 */     String line = null;
/*     */ 
/* 162 */     boolean start = false;
/* 163 */     String logTime = null;
/* 164 */     String logTimeLine = null;
/* 165 */     List values = new ArrayList();
/*     */ 
/* 167 */     while ((line = file.readLine()) != null) {
/* 168 */       if ((line.startsWith(curDate)) && (line.length() > 19)) {
/* 169 */         logTimeLine = line;
/* 170 */         logTime = line.substring(11, 19);
/* 171 */         logTime = StringUtils.remove(logTime, ':');
/* 172 */         if ((lastLogTim == null) || (Integer.parseInt(lastLogTim) < Integer.parseInt(logTime))) {
/* 173 */           start = true;
/*     */         }
/*     */       }
/*     */ 
/* 177 */       if (!(start)) {
/*     */         continue;
/*     */       }
/* 180 */       for (int i = 0; i < eventsNode.size(); ++i) {
/* 181 */         it = ((Element)eventsNode.get(i)).elementIterator("Item");
/* 182 */         while (it.hasNext()) {
/* 183 */           Element exceptionItem = (Element)it.next();
/* 184 */           String exceptStr = exceptionItem.attributeValue("name");
/*     */ 
/* 186 */           if ((!(StringUtils.isNotBlank(exceptStr))) || 
/* 187 */             (!(StringUtils.containsIgnoreCase(line, exceptStr)))) continue;
/* 188 */           exceptType = exceptionItem.attributeValue("type");
/* 189 */           if (StringUtils.isBlank(exceptType)) {
/* 190 */             exceptType = "1";
/*     */           }
/* 192 */           String exceptDesc = exceptionItem.attributeValue("desc");
/* 193 */           if ((exceptDesc == null) || (exceptDesc.length() == 0)) {
/* 194 */             exceptDesc = line;
/*     */           }
/* 196 */           StringBuffer sql = new StringBuffer();
/*     */ 
/* 198 */           sql.append("INSERT INTO ");
/* 199 */           sql.append(monevttbl);
/* 200 */           sql.append(" (SYS_NM,TXN_DAT,TIME,MSG,TYPE,EX_MSG,LOG_TYPE) values(?,?,?,?,?,?,?)");
/* 201 */           values.clear();
/* 202 */           values.add(sys_nm);
/* 203 */           values.add(curDate2);
/* 204 */           values.add(logTime);
/* 205 */           values.add(exceptDesc);
/* 206 */           values.add(exceptType);
/* 207 */           values.add(logTimeLine);
/* 208 */           values.add(logType);
/*     */ 
/* 210 */           ctx.getDataBaseUtil().execUpdate(sql.toString(), values);
/*     */ 
/* 212 */           start = false;
/* 213 */           break;
/*     */         }
/*     */ 
/* 218 */         if (!(start)) {
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/* 223 */     values.clear();
/* 224 */     values = null;
/*     */   }
/*     */ }