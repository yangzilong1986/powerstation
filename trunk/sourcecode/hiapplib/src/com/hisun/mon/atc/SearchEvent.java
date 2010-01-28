 package com.hisun.mon.atc;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.atc.common.HiFile;
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiXmlHelper;
 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.time.DateFormatUtils;
 import org.dom4j.Element;
 
 public class SearchEvent
 {
   public int execute(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     String srcDir = HiArgUtils.getStringNotNull(args, "dir");
 
     String sys_nm = HiArgUtils.getStringNotNull(args, "sys_nm");
 
     String monevttbl = HiArgUtils.getStringNotNull(args, "evttbl");
     String monevtconfig = HiArgUtils.getStringNotNull(args, "evtconfig");
 
     String lastLogTim = args.get("lastLogTim");
 
     HiFile file = new HiFile();
 
     List eventList = new ArrayList();
     Element rootConfig = null;
     Element logsNode = null;
     rootConfig = (Element)ctx.getProperty("CONFIGDECLARE." + monevtconfig.toUpperCase());
     if (rootConfig == null) {
       throw new HiException("220036", "ConfigDeclare[" + monevtconfig + "] not find!");
     }
 
     logsNode = rootConfig.element("Logs");
     if ((logsNode == null) || (logsNode.elements().size() <= 0)) {
       log.warn("Not Any Logs Node find on config!");
       return 0;
     }
 
     Calendar calendar = Calendar.getInstance();
     String curDate = DateFormatUtils.format(calendar.getTime(), "yyyy-MM-dd");
     String curDate2 = StringUtils.remove(curDate, '-');
 
     Iterator logIt = logsNode.elementIterator("Log");
     Element logItem = null;
 
     String fileName = null;
     String srcFile = null;
 
     break label516:
     logItem = (Element)logIt.next();
     eventList.clear();
 
     fileName = logItem.attributeValue("name");
     if (fileName != null) if (fileName.length() != 0)
       {
         String event = logItem.attributeValue("event");
         if (event != null) if (event.length() != 0)
           {
             String[] events = StringUtils.split(event, '|');
 
             for (int i = 0; i < events.length; ++i) {
               Element ListNode = HiXmlHelper.getNodeByAttr(rootConfig, "List", "name", events[i]);
               if (ListNode == null) {
                 continue;
               }
               eventList.add(ListNode);
             }
 
             if (eventList.size() != 0)
             {
               srcFile = HiArgUtils.absoutePath(srcDir + fileName);
               try {
                 file.open(srcFile, "r");
               }
               catch (HiException e) {
                 log.error(e.getAppMessage());
 
                 break label516: } } }
       }
     try {
       lastLogTim = getLastLogTime(ctx, fileName, sys_nm, monevttbl, curDate2);
       label516: SearchEventFromLog(ctx, eventList, file, fileName, lastLogTim, sys_nm, monevttbl, curDate, curDate2);
     } catch (Exception e) {
       throw HiException.makeException(e);
     } finally {
       try {
         file.close();
       } catch (HiException e) {
         log.error(e.getAppMessage());
 
         break label516:
       }
 
     }
 
     return 0;
   }
 
   private String getLastLogTime(HiMessageContext ctx, String file, String sys_nm, String monevttbl, String curDate)
     throws HiException
   {
     StringBuffer sqlbuf = new StringBuffer();
     sqlbuf.append("SELECT * FROM (SELECT TIME LOPR_TIM FROM ");
     sqlbuf.append(monevttbl);
     sqlbuf.append(" WHERE SYS_NM='");
     sqlbuf.append(sys_nm);
     sqlbuf.append("' AND TXN_DAT='");
     sqlbuf.append(curDate);
     sqlbuf.append("' AND LOG_TYPE='");
     sqlbuf.append(file);
     sqlbuf.append("' order by LOPR_TIM desc) WHERE ROWNUM=1");
     List list = ctx.getDataBaseUtil().execQuery(sqlbuf.toString());
     if ((list != null) && (list.size() != 0)) {
       Map map = (HashMap)list.get(0);
       return ((String)map.get("LOPR_TIM"));
     }
     return null;
   }
 
   private void SearchEventFromLog(HiMessageContext ctx, List eventsNode, HiFile file, String logType, String lastLogTim, String sys_nm, String monevttbl, String curDate, String curDate2)
     throws HiException
   {
     String exceptType = "1";
 
     Iterator it = null;
 
     String line = null;
 
     boolean start = false;
     String logTime = null;
     String logTimeLine = null;
     List values = new ArrayList();
 
     while ((line = file.readLine()) != null) {
       if ((line.startsWith(curDate)) && (line.length() > 19)) {
         logTimeLine = line;
         logTime = line.substring(11, 19);
         logTime = StringUtils.remove(logTime, ':');
         if ((lastLogTim == null) || (Integer.parseInt(lastLogTim) < Integer.parseInt(logTime))) {
           start = true;
         }
       }
 
       if (!(start)) {
         continue;
       }
       for (int i = 0; i < eventsNode.size(); ++i) {
         it = ((Element)eventsNode.get(i)).elementIterator("Item");
         while (it.hasNext()) {
           Element exceptionItem = (Element)it.next();
           String exceptStr = exceptionItem.attributeValue("name");
 
           if ((!(StringUtils.isNotBlank(exceptStr))) || 
             (!(StringUtils.containsIgnoreCase(line, exceptStr)))) continue;
           exceptType = exceptionItem.attributeValue("type");
           if (StringUtils.isBlank(exceptType)) {
             exceptType = "1";
           }
           String exceptDesc = exceptionItem.attributeValue("desc");
           if ((exceptDesc == null) || (exceptDesc.length() == 0)) {
             exceptDesc = line;
           }
           StringBuffer sql = new StringBuffer();
 
           sql.append("INSERT INTO ");
           sql.append(monevttbl);
           sql.append(" (SYS_NM,TXN_DAT,TIME,MSG,TYPE,EX_MSG,LOG_TYPE) values(?,?,?,?,?,?,?)");
           values.clear();
           values.add(sys_nm);
           values.add(curDate2);
           values.add(logTime);
           values.add(exceptDesc);
           values.add(exceptType);
           values.add(logTimeLine);
           values.add(logType);
 
           ctx.getDataBaseUtil().execUpdate(sql.toString(), values);
 
           start = false;
           break;
         }
 
         if (!(start)) {
           break;
         }
       }
     }
     values.clear();
     values = null;
   }
 }