 package com.hisun.atc.common;
 
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.exception.HiAppException;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringUtils;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.Set;
 import org.apache.commons.lang.StringUtils;
 
 public class HiDbtUtils
 {
   public static int dbtsqlinsrec(String tableName, HiETF root, HiMessageContext ctx)
     throws HiException
   {
     ArrayList params = new ArrayList();
     String sqlCmd = HiDbtSqlHelper.buildInsertSentence(ctx, root, tableName, params);
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (log.isInfoEnabled()) {
       log.info("dtsqlinsrec:[" + sqlCmd + "]:[" + params + "]");
     }
 
     int ret = 0;
     ret = ctx.getDataBaseUtil().execUpdate(sqlCmd, params);
 
     if (ret == 0) {
       return 1;
     }
     return 0;
   }
 
   public static int dbtsqlupdrec(String tableName, String keyName, String keyValue, int keyType, HiETF root, HiMessageContext ctx)
     throws HiException
   {
     String cndSts = HiDbtSqlHelper.getKeyCndSts(keyName, keyValue, keyType);
 
     String sqlCmd = HiDbtSqlHelper.buildUpdateSentence(ctx, root, tableName, cndSts, false);
 
     ctx.getDataBaseUtil().execUpdate(sqlCmd);
     return 0;
   }
 
   public static void dbtsqlrdrec(String tableName, String keyName, String keyValue, int keyType, HiETF root, HiMessageContext ctx)
     throws HiException
   {
     String cndSts = HiDbtSqlHelper.getKeyCndSts(keyName, keyValue, keyType);
     String sqlCmd = "SELECT * FROM " + tableName + " WHERE " + cndSts;
 
     dbtsqlqueryrec(sqlCmd, root, ctx);
   }
 
   public static int dbtsqlquery(String sqlCmd, HiETF root, HiMessageContext ctx)
     throws HiException
   {
     return dbtsqlquery_limit(sqlCmd, -1, root, ctx);
   }
 
   public static void dbtsqlqueryrec(String sqlCmd, HiETF root, HiMessageContext ctx)
     throws HiException
   {
     List queryRs = ctx.getDataBaseUtil().execQuery(sqlCmd);
 
     if ((queryRs != null) && (queryRs.size() == 0)) {
       throw new HiAppException(-100, "220040", "数据库无此记录");
     }
 
     Map queryRec = (HashMap)queryRs.get(0);
 
     HiDbtSqlHelper.sqlData2Etf(queryRec, root);
   }
 
   public static HiDBCursor dbtsqlcursor(String sqlCmd, String flag, HiDBCursor cursor, HiETF root, HiMessageContext ctx)
     throws HiException
   {
     return HiDbtSqlHelper.dbtCursor(sqlCmd, flag, cursor, root, ctx, false);
   }
 
   public static HiDBCursor dbtextcursor(String tableName, String cond, String flag, HiDBCursor cursor, HiETF root, HiMessageContext ctx)
     throws HiException
   {
     String sqlCmd = "SELECT * FROM " + tableName + " WHERE " + cond;
 
     return HiDbtSqlHelper.dbtCursor(sqlCmd, flag, cursor, root, ctx, true);
   }
 
   public static int dbtextinsrec(String tableName, HiETF root, HiMessageContext ctx)
     throws HiException
   {
     return dbtextinsrecdeep("011", tableName, root, ctx);
   }
 
   public static int dbtextinsrecdeep(String extKey, String tableName, HiETF root, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
 
     Logger log = HiLog.getLogger(mess);
 
     String childTableName = null;
 
     childTableName = HiDbtSqlHelper.getChildTableName(tableName, ctx);
 
     if (extKey.equals("011")) {
       extKey = HiDbtSqlHelper.getExtKey(ctx);
     }
 
     HashMap colsMap = ctx.getTableMetaData(tableName);
     if ((colsMap == null) || (colsMap.size() == 0)) {
       throw new HiException("220056", "取表结构失败：" + tableName);
     }
 
     String sqlcmd = "";
     String values = "";
 
     for (Iterator it = colsMap.entrySet().iterator(); it.hasNext(); ) {
       Map.Entry entry = (Map.Entry)it.next();
       String name = (String)entry.getKey();
       String value = root.getChildValue(name);
 
       if (name.equalsIgnoreCase("EXTKEY")) {
         if (sqlcmd.length() > 0) {
           sqlcmd = sqlcmd + "," + name;
 
           values = values + "," + extKey;
         }
         else {
           sqlcmd = sqlcmd + name;
           values = values + extKey;
         }
 
       }
       else if (name.equalsIgnoreCase("CTBLNM")) {
         if (sqlcmd.length() > 0) {
           sqlcmd = sqlcmd + "," + name;
 
           values = values + ",'" + childTableName + "'";
         }
         else {
           sqlcmd = sqlcmd + name;
           values = values + "'" + childTableName + "'";
         }
 
       }
       else if (value != null) {
         if (sqlcmd.length() > 0) {
           sqlcmd = sqlcmd + "," + name;
 
           values = values + ",'" + value + "'";
         }
         else {
           sqlcmd = sqlcmd + name;
           values = values + "'" + value + "'";
         }
       }
 
     }
 
     sqlcmd = "INSERT INTO " + tableName.toUpperCase() + "(" + sqlcmd + ") VALUES(" + values + ")";
 
     if (log.isInfoEnabled()) {
       log.info("插入记录[" + sqlcmd + "]");
     }
     int ret = ctx.getDataBaseUtil().execUpdate(sqlcmd);
     if (ret == 0) {
       return ret;
     }
 
     if (!(StringUtils.isEmpty(childTableName))) {
       ret = dbtextinsrecdeep(extKey, childTableName, root, ctx);
     }
     return ret;
   }
 
   public static int dbtextupdreccon(String tableName, String cond, HiETF root, HiMessageContext ctx)
     throws HiException
   {
     int ret = HiDbtSqlHelper.extUpdateRecord(tableName, cond, root, ctx);
 
     String sqlQueryChild = HiStringUtils.format("SELECT CTBLNM, EXTKEY FROM %s WHERE %s", tableName, cond);
 
     List queryRs = ctx.getDataBaseUtil().execQuery(sqlQueryChild);
 
     if ((queryRs == null) || (queryRs.size() == 0))
     {
       return ret;
     }
 
     Map queryRec = (HashMap)queryRs.get(0);
     String childTable = (String)queryRec.get("CTBLNM");
     String ExtKey = (String)queryRec.get("EXTKEY");
 
     if ((StringUtils.isNotEmpty(childTable)) && (StringUtils.isNotEmpty(ExtKey)))
     {
       String CndSts = HiStringUtils.format(" EXTKEY = %s ", ExtKey);
 
       ret = HiDbtSqlHelper.extUpdateRecord(childTable, CndSts, root, ctx);
     }
 
     return ret;
   }
 
   public static void dbtextrdreccon(String tableName, String cond, HiETF root, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
 
     String sqlcmd = HiStringUtils.format("SELECT * FROM %s WHERE %s", tableName, cond);
 
     if (log.isInfoEnabled()) {
       log.info("查询语句:[" + sqlcmd + "]");
     }
     List queryRs = ctx.getDataBaseUtil().execQuery(sqlcmd);
 
     if ((queryRs == null) || (queryRs.size() == 0)) {
       throw new HiAppException(-100, "220040", "数据库无此记录");
     }
 
     Map queryRec = (HashMap)queryRs.get(0);
 
     HiDbtSqlHelper.extSqlData2Etf(queryRec, root, ctx);
   }
 
   public static int dbtextquery(String tableName, String cond, HiETF root, HiMessageContext ctx)
     throws HiException
   {
     return dbtextquery_limit(tableName, cond, -1, root, ctx);
   }
 
   public static int dbtextdelreccon(String tableName, String cond, HiETF root, HiMessageContext ctx)
     throws HiException
   {
     int ret = 0;
     boolean isExt = true;
 
     String sqlQueryChild = HiStringUtils.format("SELECT CTBLNM, EXTKEY FROM %s WHERE %s", tableName, cond);
 
     List queryRs = null;
     try
     {
       queryRs = ctx.getDataBaseUtil().execQuery(sqlQueryChild);
     } catch (HiException e) {
       isExt = false;
     }
 
     String sqlCmd = HiStringUtils.format("DELETE FROM %s WHERE %s", tableName, cond);
 
     ret = ctx.getDataBaseUtil().execUpdate(sqlCmd);
 
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (isExt)
     {
       if ((queryRs != null) && (queryRs.size() == 0))
       {
         return ret;
       }
 
       Map queryRec = (HashMap)queryRs.get(0);
       String childTable = (String)queryRec.get("CTBLNM");
       String ExtKey = (String)queryRec.get("EXTKEY");
 
       if ((StringUtils.isNotEmpty(childTable)) && (StringUtils.isNotEmpty(ExtKey)))
       {
         if (log.isInfoEnabled()) {
           log.info("CTBLNM[" + childTable + "],EXTKEY[" + ExtKey + "]");
         }
 
         String CndSts = HiStringUtils.format(" EXTKEY = %s ", ExtKey);
 
         dbtextdelreccon(childTable, CndSts, root, ctx);
       }
     }
     return ret;
   }
 
   public static int dbtextquery_limit(String tableName, String cond, int limits, HiETF root, HiMessageContext ctx)
     throws HiException
   {
     String sqlCmd = HiStringUtils.format("SELECT * FROM %s WHERE %s", tableName, cond);
 
     List queryRs = ctx.getDataBaseUtil().execQuery(sqlCmd, limits);
 
     int rsSize = queryRs.size();
     if (rsSize == 0) {
       return rsSize;
     }
 
     if ((limits < 0) || (rsSize < limits)) {
       limits = rsSize;
     }
 
     String preName = root.getName() + "_";
     for (int i = 0; i < limits; ++i) {
       HiETF recNode = root.addNode(preName + i);
 
       Map queryRec = (HashMap)queryRs.get(i);
       HiDbtSqlHelper.extSqlData2Etf(queryRec, recNode, ctx);
     }
     return limits;
   }
 
   public static int dbtextquery_limit(String tableName, String cond, int limits, HiETF root, String recName, HiMessageContext ctx)
     throws HiException
   {
     String sqlCmd = HiStringUtils.format("SELECT * FROM %s WHERE %s", tableName, cond);
 
     List queryRs = ctx.getDataBaseUtil().execQuery(sqlCmd, limits);
 
     int rsSize = queryRs.size();
     if (rsSize == 0) {
       return rsSize;
     }
 
     if ((limits < 0) || (rsSize < limits)) {
       limits = rsSize;
     }
 
     String preName = recName + "_";
     for (int i = 0; i < limits; ++i) {
       HiETF recNode = root.addNode(preName + i);
 
       Map queryRec = (HashMap)queryRs.get(i);
       HiDbtSqlHelper.extSqlData2Etf(queryRec, recNode, ctx);
     }
     return limits;
   }
 
   public static int dbtsqlquery_limit(String sqlCmd, int limits, HiETF root, HiMessageContext ctx)
     throws HiException
   {
     List queryRs = ctx.getDataBaseUtil().execQuery(sqlCmd);
 
     int rsSize = queryRs.size();
     if (rsSize == 0) {
       return rsSize;
     }
 
     if ((limits < 0) || (rsSize < limits)) {
       limits = rsSize;
     }
 
     String preName = root.getName() + "_";
     for (int i = 0; i < limits; ++i) {
       HiETF recNode = root.addNode(preName + (i + 1));
 
       Map queryRec = (HashMap)queryRs.get(i);
       HiDbtSqlHelper.sqlData2Etf(queryRec, recNode);
     }
     return limits;
   }
 
   public static int dbtsqlupdreccon(String tableName, String cndSts, HiETF root, HiMessageContext ctx)
     throws HiException
   {
     String sqlCmd = HiDbtSqlHelper.buildUpdateSentence(ctx, root, tableName, cndSts, false);
 
     ctx.getDataBaseUtil().execUpdate(sqlCmd);
     return 0;
   }
 }