 package com.hisun.ccb.atc.multi;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.atc.common.HiDbtSqlHelper;
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiICSProperty;
 import java.io.File;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.Collection;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import org.apache.commons.lang.StringUtils;
 
 public class HiMulti
 {
   public int GenDataSet(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     Iterator i$;
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     String recKey = HiArgUtils.getStringNotNull(argsMap, "RecKey");
 
     String type = argsMap.get("Type");
     if ((StringUtils.isEmpty(recKey)) || (StringUtils.isEmpty(type)))
     {
       throw new HiException("220049");
     }
     recKey = recKey.trim();
     String flagStr = argsMap.get("Flag");
     boolean flag = (flagStr != null) && (flagStr.equalsIgnoreCase("ADD"));
 
     String fileDir = HiICSProperty.getWorkDir() + File.separator + "tmp/Multi_";
 
     String fileName = fileDir + recKey + ".dat";
     if (log.isDebugEnabled())
     {
       log.debug("FILE NAME IS:[" + fileName + "]");
     }
 
     HiETF root = mess.getETFBody();
     if (type.equalsIgnoreCase("SQL"))
     {
       if (log.isDebugEnabled())
       {
         log.debug("PREPARE TO QUERY DATA FROM DB...");
       }
       String sqlCmd = HiArgUtils.getStringNotNull(argsMap, "SqlCmd");
 
       sqlCmd = HiDbtSqlHelper.getDynSentence(ctx, sqlCmd, root);
       HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
 
       List list = null;
       list = dbUtil.execQuery(sqlCmd);
 
       if (list == null)
         throw new HiException("220038");
       if (list.size() == 0) {
         return 2;
       }
       if (log.isDebugEnabled())
       {
         log.debug("COUNTS OF RESULT IS:[" + list.size() + "]");
       }
 
       for (i$ = list.iterator(); i$.hasNext(); ) { Object obj = i$.next();
 
         Map map = (Map)obj;
         Collection names = map.keySet();
         HiETF nodeEtf = HiETFFactory.createETF();
         nodeEtf.setName("REC");
         for (String name : names)
         {
           HiETF etf = HiETFFactory.createETF();
           etf.setName(name);
           etf.setValue((String)map.get(name));
           nodeEtf.appendNode(etf);
         }
 
         writeToFile(fileName, nodeEtf.toString(), flag);
         if (log.isDebugEnabled()) {
           log.debug("WRITE TO FILE:[" + fileName + "], content:[" + nodeEtf.toString() + "] flag:[" + flag + "]");
         }
 
         flag = true;
       }
     }
     else
     {
       HiDataBaseUtil dbUtil;
       Iterator i$;
       if (type.equalsIgnoreCase("SQLExtend"))
       {
         String table = HiArgUtils.getStringNotNull(argsMap, "Table");
 
         String cndSts = HiArgUtils.getStringNotNull(argsMap, "CndSts");
 
         if ((StringUtils.isEmpty(table)) || (StringUtils.isEmpty(cndSts)))
           throw new HiException("213307");
         cndSts = HiDbtSqlHelper.getDynSentence(ctx, cndSts, root);
 
         String sqlQueryMaintable = "SELECT * FROM " + table + " WHERE " + cndSts;
 
         dbUtil = ctx.getDataBaseUtil();
 
         List list = null;
         list = dbUtil.execQuery(sqlQueryMaintable);
 
         if ((list == null) || (list.size() == 0))
           throw new HiException("220040");
         for (i$ = list.iterator(); i$.hasNext(); ) { Object obj = i$.next();
 
           Map map = (Map)obj;
           String extTableName = (String)map.get("CTBLNam");
           String extTableKey = (String)map.get("extkey");
 
           StringBuffer queryExtTableBf = new StringBuffer();
           queryExtTableBf.append("SELECT * FROM ");
           queryExtTableBf.append(extTableName);
           queryExtTableBf.append("WHERE");
           queryExtTableBf.append("extkey");
           queryExtTableBf.append("=");
           queryExtTableBf.append(extTableKey);
 
           List extList = dbUtil.execQuery(queryExtTableBf.toString());
           Map extMap = (Map)extList.iterator();
 
           HiETF nodeEtf = HiETFFactory.createETF();
           nodeEtf.setName("REC");
 
           if (log.isDebugEnabled())
           {
             log.debug("ADD DATA FROM MAIN TABLE");
           }
           Collection names = map.keySet();
           for (String name : names)
           {
             HiETF etf = HiETFFactory.createETF();
             etf.setName(name);
             etf.setValue((String)map.get(name));
             nodeEtf.appendNode(etf);
           }
           if (log.isDebugEnabled())
           {
             log.debug("ADD DATA FROM EXTEND TABLE");
           }
           Collection extNames = extMap.keySet();
           for (String name : extNames)
           {
             HiETF etf = HiETFFactory.createETF();
             etf.setName(name);
             etf.setValue((String)extMap.get(name));
             nodeEtf.appendNode(etf);
           }
           writeToFile(fileName, nodeEtf.toString(), flag);
           flag = true;
         }
       }
       else if (type.equalsIgnoreCase("ETF"))
       {
         String grpName = argsMap.get("GrpName");
         String fieldsStr = argsMap.get("Fields");
 
         HiETF etfRoot = mess.getETFBody();
         HiETF nodeEtf = null;
 
         if (StringUtils.isEmpty(grpName))
         {
           if (StringUtils.isEmpty(fieldsStr))
           {
             nodeEtf = etfRoot.cloneNode();
             nodeEtf.setName("REC");
           }
           else
           {
             nodeEtf = HiETFFactory.createETF();
             nodeEtf.setName("REC");
 
             Collection fields = getRegContent(fieldsStr, "|");
 
             for (String field : fields)
             {
               nodeEtf.setChildValue(field, etfRoot.getChildValue(field));
             }
           }
           writeToFile(fileName, nodeEtf.toString(), flag);
         }
         else
         {
           int count = 0;
 
           HiETF etfGroup = null;
           for (; ; ++count)
           {
             String tmpName = grpName + "_" + Integer.toString(count + 1);
             etfGroup = etfRoot.getChildNode(tmpName);
             if (etfGroup == null)
             {
               break;
             }
 
             if (StringUtils.isEmpty(fieldsStr))
             {
               nodeEtf = etfGroup.cloneNode();
               nodeEtf.setName("REC");
             }
             else
             {
               nodeEtf = HiETFFactory.createETF();
               nodeEtf.setName("REC");
 
               Collection fields = getRegContent(fieldsStr, "|");
 
               for (String field : fields)
               {
                 nodeEtf.setChildValue(field, etfGroup.getChildValue(field));
               }
             }
             writeToFile(fileName, nodeEtf.toString(), flag);
             flag = true;
           }
         }
       } else if (type.equalsIgnoreCase("FILE"))
       {
         String inFile = argsMap.get("InFile");
         String cfgFile = argsMap.get("CfgFil");
         if ((StringUtils.isEmpty(inFile)) || (StringUtils.isEmpty(cfgFile)))
           throw new HiException("213307"); 
       }
     }
     return 0;
   }
 
   public int QueryPage(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     HiMultiDTO md = null;
     String recKey = HiArgUtils.getStringNotNull(argsMap, "RecKey");
 
     String queryCmd = HiArgUtils.getStringNotNull(argsMap, "QryCmd");
 
     int re = 0;
     if ((StringUtils.isEmpty(recKey)) || (StringUtils.isEmpty(queryCmd)))
       throw new HiException("213307");
     recKey = recKey.trim();
 
     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
 
     int pageNum = 1;
     int pageRecCounts = 1;
     String pageNumStr = null;
     String pageRecCounts_str = null;
     String sql_query = "SELECT NUM_PERPAGE, CURR_PAGE  FROM PUBMULQRY WHERE REC_KEY='" + recKey + "'";
 
     List list = dbUtil.execQuery(sql_query);
 
     if (list == null)
       throw new HiException("220038");
     if (list.size() == 0) {
       return 2;
     }
     Map map = (Map)list.iterator().next();
     pageNumStr = (String)map.get("CURR_PAGE");
     pageRecCounts_str = (String)map.get("NUM_PERPAGE");
     try
     {
       pageRecCounts = Integer.parseInt(pageRecCounts_str);
       pageNum = Integer.parseInt(pageNumStr);
     }
     catch (NumberFormatException e)
     {
       throw new HiException("220049");
     }
     if (log.isDebugEnabled())
     {
       log.debug("NUM PERPAGE IS:[" + pageRecCounts + "]");
       log.debug("CURRENT PAGE IS:[" + pageNum + "]");
     }
     md = new HiMultiDTO(recKey, pageRecCounts);
     String fileDir = HiICSProperty.getWorkDir() + File.separator + "tmp/Multi_";
 
     md.setFileDir(fileDir);
 
     md.setMultiQuery(HiQueryFactory.getProcessor(md));
 
     if (log.isDebugEnabled())
     {
       log.debug("DIR OF DATA SOURCE FILE IS:[" + fileDir + "]");
     }
 
     if (queryCmd.equalsIgnoreCase("050000"))
     {
       if (log.isDebugEnabled())
       {
         log.debug("QUERY FIRST PAGE...");
       }
       pageNum = 1;
       md.setPageNum(pageNum);
     }
     else if (queryCmd.equalsIgnoreCase("060000"))
     {
       if (log.isDebugEnabled())
       {
         log.debug("QUERY END PAGE...");
       }
       pageNum = HiMultiService.getTotalPage(md);
       md.setEndPage(true);
       md.setPageNum(pageNum);
     }
     else if (queryCmd.equalsIgnoreCase("070000"))
     {
       if (log.isDebugEnabled())
       {
         log.debug("PAGEUP, AND CURRENT PAGE IS:[" + pageNum + "]");
       }
       --pageNum;
       pageNum = (pageNum < 1) ? 1 : pageNum;
       md.setPageNum(pageNum);
     }
     else if (queryCmd.equalsIgnoreCase("080000"))
     {
       if (log.isDebugEnabled())
       {
         log.debug("PAGEDOWN, AND CURRENT PAGE IS:[" + pageNum + "]");
       }
       ++pageNum;
       int totalPage = HiMultiService.getTotalPage(md);
       pageNum = (pageNum > totalPage) ? totalPage : pageNum;
       md.setPageNum(pageNum);
     } else if (queryCmd.indexOf("00") == 0)
     {
       String pageStr = queryCmd.substring(2);
       try
       {
         pageNum = Integer.parseInt(pageStr);
       }
       catch (NumberFormatException e) {
         throw new HiException("220049", "Page num is not int type");
       }
 
       int totalPage = HiMultiService.getTotalPage(md);
       pageNum = (pageNum > totalPage) ? totalPage : pageNum;
       md.setPageNum(pageNum);
       if (log.isDebugEnabled()) {
         log.debug("QUERY CERTAIN PAGE, pageNum=" + pageNum);
       }
     }
     getCertainPage(md, mess, ctx);
 
     String time = getSysTime();
     String date = getSysDate();
 
     StringBuffer updateSqlBf = new StringBuffer();
     updateSqlBf.append("UPDATE  PUBMULQRY SET CURR_PAGE=");
     updateSqlBf.append(pageNum);
     updateSqlBf.append(" ,SYS_DATE='");
     updateSqlBf.append(time);
     updateSqlBf.append("' WHERE REC_KEY='");
     updateSqlBf.append(recKey);
     updateSqlBf.append("'");
 
     String sql_update = "update PUBMULQRY set CURR_PAGE=" + pageNum + " ,SYS_DATE='" + date + "' ,SYS_TIME='" + time + "' where REC_KEY='" + recKey + "'";
 
     log.debug("prepare to update PUBMULQRY and sql_update=" + sql_update);
     dbUtil.execUpdate(updateSqlBf.toString());
     return re;
   }
 
   public int QueryFirstPage(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     log.debug("begin to query firstPage");
     HiMultiDTO md = null;
     String recKey = argsMap.get("RecKey");
     String numPerPage_str = argsMap.get("RecNumPerPage");
     log.debug("recKey=" + recKey + " RecNumPerPage=" + numPerPage_str);
     if ((StringUtils.isEmpty(recKey)) || (StringUtils.isEmpty(numPerPage_str))) {
       throw new HiException("213307");
     }
     int numPerPage = 1;
     try
     {
       numPerPage = Integer.parseInt(numPerPage_str);
     }
     catch (NumberFormatException e) {
       throw new HiException("220049", "Page num is not int type");
     }
 
     String fileDir = HiICSProperty.getWorkDir() + File.separator + "tmp/Multi_";
 
     md = new HiMultiDTO(recKey.trim(), numPerPage);
     md.setFirstPage(true);
     md.setPageNum(1);
     md.setFileDir(fileDir);
     md.setPageRecCounts(numPerPage);
     log.debug("MultiDTO has been created");
     md.setMultiQuery(HiQueryFactory.getProcessor(md));
     int totalPage = HiMultiService.getTotalPage(md);
     int total = HiMultiService.getTotalRec(md);
 
     log.debug("totalpage=" + totalPage + " toal=" + total);
 
     String time = getSysTime();
     String date = getSysDate();
     log.debug("time=[" + time + "] date=[" + date + "]");
     log.debug("md.getRecKey() =" + md.getRecKey());
     log.debug("md.getPageRecCounts()=" + md.getPageRecCounts());
 
     StringBuffer delSqlBf = new StringBuffer();
     delSqlBf.append("delete from PUBMULQRY where REC_KEY='");
     delSqlBf.append(md.getRecKey());
     delSqlBf.append("'");
 
     StringBuffer insertSqlBf = new StringBuffer();
     insertSqlBf.append("INSERT INTO PUBMULQRY(REC_KEY,NUM_PERPAGE,CURR_PAGE,TOTAL,TOTAL_PAGE,SYS_DATE,SYS_TIME) values('");
 
     insertSqlBf.append(md.getRecKey());
     insertSqlBf.append("',");
     insertSqlBf.append(md.getPageRecCounts());
     insertSqlBf.append(",1,");
     insertSqlBf.append(total);
     insertSqlBf.append(",");
     insertSqlBf.append(totalPage);
     insertSqlBf.append(",'");
     insertSqlBf.append(date);
     insertSqlBf.append("','");
     insertSqlBf.append(time);
     insertSqlBf.append("')");
 
     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
     dbUtil.execUpdate(delSqlBf.toString());
     dbUtil.execUpdate(insertSqlBf.toString());
 
     getCertainPage(md, mess, ctx);
 
     return 0;
   }
 
   private void getCertainPage(HiMultiDTO md, HiMessage mess, HiMessageContext ctx)
     throws HiException
   {
     Logger log = HiLog.getLogger(mess);
     HiETF rootEtf = mess.getETFBody();
     int totalPage = HiMultiService.getTotalPage(md);
     int recNum = HiMultiService.getCurrPageRecCounts(md);
     int totalRecNum = HiMultiService.getTotalRec(md);
     Collection etfChildren = HiMultiService.query(md);
     int i = 0;
     for (HiETF etf : etfChildren)
     {
       rootEtf.appendNode(etf);
     }
     rootEtf.setChildValue("REC_NUM", String.valueOf(recNum));
     rootEtf.setChildValue("PAG_CNT", String.valueOf(totalPage));
     rootEtf.setChildValue("PAG_NO", String.valueOf(md.getPageNum()));
     rootEtf.setChildValue("TOT_REC_NUM", String.valueOf(totalRecNum));
   }
 
   private String getSysTime()
   {
     Calendar cd = Calendar.getInstance();
     long timeValue = cd.getTimeInMillis();
     String time = new SimpleDateFormat("hhmmss").format(Long.valueOf(timeValue));
     return time;
   }
 
   private String getSysDate()
   {
     Calendar cd = Calendar.getInstance();
     long timeValue = cd.getTimeInMillis();
     String date = new SimpleDateFormat("yyyyMMdd").format(Long.valueOf(timeValue));
     return date;
   }
 
   private Collection<String> getRegContent(String content, String reg)
   {
     if (content == null)
       return null;
     Collection reStrs = new ArrayList();
     int start = 0;
     int end = 0;
     while (content.length() > 0)
     {
       end = content.indexOf(reg);
       String part = content.substring(start, end);
       reStrs.add(part);
       content = content.substring(end + 1);
     }
     return reStrs;
   }
 
   private void writeToFile(String fileName, String content, boolean isAdd)
     throws HiException
   {
     File file = new File(fileName);
     FileOutputStream fos = null;
     try
     {
       fos = new FileOutputStream(file, isAdd);
       byte[] buf = content.getBytes();
       fos.write(String.format("%08d", new Object[] { Integer.valueOf(content.length()) }).getBytes());
       fos.write(buf);
       try
       {
         fos.close();
       }
       catch (IOException e) {
         throw new HiException("220079", fileName, e);
       }
     }
     catch (IOException e)
     {
     }
     finally
     {
       try
       {
         fos.close();
       }
       catch (IOException e) {
         throw new HiException("220079", fileName, e);
       }
     }
   }
 }