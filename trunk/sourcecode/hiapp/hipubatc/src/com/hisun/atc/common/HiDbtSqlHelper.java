 package com.hisun.atc.common;
 
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.exception.HiException;
 import com.hisun.exception.HiSQLException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 import com.hisun.util.HiStringUtils;
 import java.io.File;
 import java.io.FileNotFoundException;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.ResultSetMetaData;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.Set;
 import org.apache.commons.lang.StringUtils;
 
 public class HiDbtSqlHelper
 {
   private static HiStringManager sm = HiStringManager.getManager();
 
   public static String getKeyCndSts(String keyName, String keyValue, int keyType)
   {
     String cndSts = "";
     if (StringUtils.isNotBlank(keyName))
     {
       if (keyType == 0)
       {
         cndSts = cndSts + keyName + "='" + keyValue + "'";
       }
       else
       {
         cndSts = cndSts + keyName + "=" + keyValue;
       }
 
     }
 
     return cndSts;
   }
 
   public static String buildUpdateSentence(HiMessageContext ctx, HiETF root, String tableName, String cndSts, boolean isExt, ArrayList param)
     throws HiException
   {
     if ((StringUtils.isEmpty(tableName)) || (root == null))
     {
       throw new HiException("220026", "前置系统错误");
     }
     StringBuffer sqlCmd = new StringBuffer();
 
     HashMap tblMeta = ctx.getTableMetaData(tableName);
 
     sqlCmd.append("UPDATE ");
     sqlCmd.append(tableName);
     sqlCmd.append(" SET ");
 
     Iterator metaIt = tblMeta.entrySet().iterator();
 
     Map.Entry metaEntry = null;
     String colNam = null;
     String colEtf = null;
 
     while (metaIt.hasNext())
     {
       metaEntry = (Map.Entry)metaIt.next();
 
       colNam = (String)metaEntry.getKey();
       if (isExt)
       {
         if (colNam.equalsIgnoreCase("CTBLNM")) continue; if (colNam.equalsIgnoreCase("EXTKEY")) {
           continue;
         }
 
       }
 
       colEtf = root.getChildValue(colNam);
       if (colEtf == null) continue; if ("".equals(colEtf)) {
         continue;
       }
 
       sqlCmd.append(colNam);
       sqlCmd.append("=");
       param.add(colEtf);
       sqlCmd.append('?');
 
       sqlCmd.append(",");
     }
 
     sqlCmd.deleteCharAt(sqlCmd.length() - 1);
 
     if (StringUtils.isNotBlank(cndSts))
     {
       sqlCmd.append(" WHERE ");
       sqlCmd.append(cndSts);
     }
 
     return sqlCmd.toString();
   }
 
   public static String buildUpdateSentence(HiMessageContext ctx, HiETF root, String tableName, String cndSts, boolean isExt)
     throws HiException
   {
     if ((StringUtils.isEmpty(tableName)) || (root == null))
     {
       throw new HiException("220026", "前置系统错误");
     }
     StringBuffer sqlCmd = new StringBuffer();
 
     HashMap tblMeta = ctx.getTableMetaData(tableName);
 
     sqlCmd.append("UPDATE ");
     sqlCmd.append(tableName);
     sqlCmd.append(" SET ");
 
     Iterator metaIt = tblMeta.entrySet().iterator();
 
     Map.Entry metaEntry = null;
     String colNam = null;
     HiETF colEtf = null;
 
     while (metaIt.hasNext())
     {
       metaEntry = (Map.Entry)metaIt.next();
 
       colNam = (String)metaEntry.getKey();
       if (isExt)
       {
         if (colNam.equalsIgnoreCase("CTBLNM")) continue; if (colNam.equalsIgnoreCase("EXTKEY")) {
           continue;
         }
 
       }
 
       colEtf = root.getChildNode(colNam);
       if (colEtf == null) continue; if ("".equals(colEtf)) {
         continue;
       }
 
       sqlCmd.append(colNam);
       sqlCmd.append("=");
       if (isNumberType((String)tblMeta.get(colNam))) {
         sqlCmd.append(colEtf.getValue());
       } else {
         sqlCmd.append("'");
         sqlCmd.append(sqlEscape(colEtf.getValue()));
         sqlCmd.append("'");
       }
       sqlCmd.append(",");
     }
 
     sqlCmd.deleteCharAt(sqlCmd.length() - 1);
 
     if (StringUtils.isNotBlank(cndSts))
     {
       sqlCmd.append(" WHERE ");
       sqlCmd.append(cndSts);
     }
 
     return sqlCmd.toString();
   }
 
   public static String buildUpdate1Sentence(HiMessageContext ctx, HiETF root, String tableName, String cndSts, boolean isExt)
     throws HiException
   {
     if ((StringUtils.isEmpty(tableName)) || (root == null))
     {
       throw new HiException("220026", "前置系统错误");
     }
     StringBuffer sqlCmd = new StringBuffer();
 
     HashMap tblMeta = ctx.getTableMetaData(tableName);
 
     sqlCmd.append("UPDATE ");
     sqlCmd.append(tableName);
     sqlCmd.append(" SET ");
 
     Iterator metaIt = tblMeta.entrySet().iterator();
 
     Map.Entry metaEntry = null;
     String colNam = null;
     HiETF colEtf = null;
 
     while (metaIt.hasNext())
     {
       metaEntry = (Map.Entry)metaIt.next();
 
       colNam = (String)metaEntry.getKey();
       if (isExt)
       {
         if (colNam.equalsIgnoreCase("CTBLNM")) continue; if (colNam.equalsIgnoreCase("EXTKEY")) {
           continue;
         }
 
       }
 
       colEtf = root.getChildNode(colNam);
       if (colEtf == null) {
         continue;
       }
 
       sqlCmd.append(colNam);
       sqlCmd.append("=");
       if (isNumberType((String)tblMeta.get(colNam))) {
         sqlCmd.append(colEtf.getValue());
       } else {
         sqlCmd.append("'");
         sqlCmd.append(sqlEscape(colEtf.getValue()));
         sqlCmd.append("'");
       }
       sqlCmd.append(",");
     }
 
     sqlCmd.deleteCharAt(sqlCmd.length() - 1);
 
     if (StringUtils.isNotBlank(cndSts))
     {
       sqlCmd.append(" WHERE ");
       sqlCmd.append(cndSts);
     }
 
     return sqlCmd.toString();
   }
 
   public static String buildInsertSentence(HiMessageContext ctx, HiETF etfBody, String tableName, ArrayList params)
     throws HiException
   {
     StringBuffer names = new StringBuffer();
     StringBuffer values = new StringBuffer();
 
     Map tblMeta = ctx.getTableMetaData(tableName);
 
     Iterator metaIt = tblMeta.entrySet().iterator();
     boolean first = true;
     while (metaIt.hasNext())
     {
       Map.Entry metaEntry = (Map.Entry)metaIt.next();
 
       String colNam = (String)metaEntry.getKey();
       String colEtf = etfBody.getChildValue(colNam);
 
       if (colEtf == null) continue; if ("".equals(colEtf))
       {
         continue;
       }
 
       if (!(first)) {
         names.append(",");
         values.append(",");
       }
       names.append(colNam);
       values.append('?');
       params.add(colEtf);
 
       first = false;
     }
 
     StringBuffer sqlCmd = new StringBuffer();
     sqlCmd.append("INSERT INTO ");
     sqlCmd.append(tableName);
     sqlCmd.append(" (");
     sqlCmd.append(names);
     sqlCmd.append(") VALUES (");
     sqlCmd.append(values);
     sqlCmd.append(")");
 
     return sqlCmd.toString();
   }
 
   static int extUpdateRecord(String tableName, String cond, HiETF root, HiMessageContext ctx)
     throws HiException
   {
     String sqlSentence = buildUpdateSentence(ctx, root, tableName, cond, true);
 
     return ctx.getDataBaseUtil().execUpdate(sqlSentence);
   }
 
   static String getChildTableName(String tablename, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     HiETF root = mess.getETFBody();
     Logger log = HiLog.getLogger(mess);
     String childTableName = null;
 
     String sqlstring = HiStringUtils.format("SELECT CNDFLD FROM PUBTBLFLD WHERE TBLNAM = '%s'", tablename);
 
     if (log.isInfoEnabled())
     {
       log.info("查询子表条件域：[" + sqlstring + "]");
     }
     List lRec = ctx.getDataBaseUtil().execQuery(sqlstring);
     if ((lRec != null) && (lRec.size() == 1))
     {
       Map queryRec = (HashMap)lRec.get(0);
       String field = (String)queryRec.get("CNDFLD");
       String fieldvalue = root.getChildValue(field);
       if (!(StringUtils.isEmpty(fieldvalue)))
       {
         sqlstring = HiStringUtils.format("SELECT CTBLNM FROM PUBTBLCND WHERE TBLNAM = '%s' AND FLDVAL='%s'", tablename, fieldvalue);
 
         if (log.isInfoEnabled())
         {
           log.info("查询扩展表名：[" + sqlstring + "]");
         }
         List lRec2 = ctx.getDataBaseUtil().execQuery(sqlstring);
         if ((lRec2 != null) && (lRec2.size() == 1))
         {
           queryRec = (HashMap)lRec2.get(0);
           childTableName = (String)queryRec.get("CTBLNM");
           if (log.isInfoEnabled())
           {
             log.info("扩展表名为：[" + childTableName + "]");
           }
         }
       }
 
     }
 
     return childTableName;
   }
 
   static String getExtKey(HiMessageContext tranData)
     throws HiException
   {
     List lRec2 = tranData.getDataBaseUtil().execQuery("SELECT NEXTVAL FOR PUBSEQKEY FROM TABLE(VALUES(1)) AS ANNONY");
 
     if ((lRec2 != null) && (lRec2.size() == 1))
     {
       Map queryRec = (HashMap)lRec2.get(0);
       Iterator recIt = queryRec.entrySet().iterator();
       Map.Entry recEntry = (Map.Entry)recIt.next();
       String ExtKey = (String)recEntry.getValue();
       return StringUtils.leftPad(ExtKey, 11, '0');
     }
 
     return null;
   }
 
   public static int dbtCursor(String sqlCmd, String flag, String cursorName, HiETF root, HiMessageContext ctx, boolean isExt)
     throws HiException
   {
     HiDBCursor cursor;
     int ret = 0;
     if (StringUtils.isEmpty(cursorName))
     {
       cursorName = "CURSOR.CURSOR_1";
     }
     else
     {
       cursorName = "CURSOR." + cursorName.toUpperCase();
     }
 
     if (StringUtils.equalsIgnoreCase(flag, "O"))
     {
       cursor = new HiDBCursor(ctx.getCurrentMsg(), sqlCmd, ctx);
 
       ctx.setBaseSource(cursorName, cursor);
     }
     else if (StringUtils.equalsIgnoreCase(flag, "F"))
     {
       cursor = (HiDBCursor)ctx.getBaseSource(cursorName);
       if (cursor == null)
       {
         throw new HiException("220096", cursorName);
       }
 
       Map curRec = cursor.next();
 
       if (curRec == null)
       {
         ret = 100;
         return ret;
       }
 
       if (isExt)
       {
         extSqlData2Etf(curRec, root, ctx);
       }
       else
       {
         sqlData2Etf(curRec, root);
       }
       ret = 1;
     }
     else if (StringUtils.equalsIgnoreCase(flag, "C"))
     {
       cursor = (HiDBCursor)ctx.getBaseSource(cursorName);
       if (cursor == null)
       {
         throw new HiException("220096", cursorName);
       }
 
       cursor.close();
       ctx.removeBaseSource(cursorName);
     }
 
     return ret;
   }
 
   public static HiDBCursor dbtCursor(String sqlCmd, String flag, HiDBCursor cursor, HiETF root, HiMessageContext ctx, boolean isExt)
     throws HiException
   {
     int ret = 0;
 
     if (StringUtils.equalsIgnoreCase(flag, "O"))
     {
       cursor = new HiDBCursor(ctx.getCurrentMsg(), sqlCmd, ctx);
     }
     else if (StringUtils.equalsIgnoreCase(flag, "F"))
     {
       if (cursor == null)
       {
         throw new HiException("220096");
       }
 
       Map curRec = cursor.next();
 
       if (curRec == null)
       {
         cursor.ret = 100;
         return cursor;
       }
 
       if (isExt)
       {
         extSqlData2Etf(curRec, root, ctx);
       }
       else
       {
         sqlData2Etf(curRec, root);
       }
       ret = 1;
     }
     else if (StringUtils.equalsIgnoreCase(flag, "C"))
     {
       if (cursor == null)
       {
         throw new HiException("220096");
       }
 
       cursor.close();
     }
 
     if (cursor != null)
     {
       cursor.ret = ret;
     }
 
     return cursor;
   }
 
   static void sqlData2Etf(Map curRec, HiETF root)
   {
     Iterator recIt = curRec.entrySet().iterator();
 
     while (recIt.hasNext())
     {
       Map.Entry recEntry = (Map.Entry)recIt.next();
       root.setChildValue((String)recEntry.getKey(), (String)recEntry.getValue());
     }
   }
 
   public static List execMultiQuery(File file, String strSql, int nMaxCol, HiMessageContext ctx, int iMaxLinePerPage)
     throws HiException
   {
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (log.isDebugEnabled())
       log.debug("execMultiQuery is start,sql=[" + strSql + "]");
     Connection conn = ctx.getDataBaseUtil().getConnection();
     PreparedStatement stmt = null;
     ResultSet rs = null;
     FileOutputStream out = null;
     try
     {
       stmt = conn.prepareStatement(strSql);
       ArrayList list = new ArrayList();
       rs = stmt.executeQuery();
       writeQueryFile(iMaxLinePerPage, file, nMaxCol, ctx, log, rs, list);
       ArrayList localArrayList1 = list;
 
       return localArrayList1;
     }
     catch (IOException e)
     {
     }
     catch (Exception e)
     {
       throw new HiSQLException("215026", e, strSql);
     }
     finally
     {
       try
       {
         out.close();
       }
       catch (Exception e)
       {
       }
       ctx.getDataBaseUtil().close(stmt, rs);
     }
   }
 
   private static void writeQueryFile(int iMaxLinePerPage, File file, int nMaxCol, HiMessageContext ctx, Logger log, ResultSet rs, ArrayList list) throws SQLException, FileNotFoundException, IOException
   {
     FileOutputStream out = null;
     ResultSetMetaData meta = rs.getMetaData();
     int cols = meta.getColumnCount();
     int nRowCounts = 1;
     try {
       out = new FileOutputStream(file);
       HiETF etf = ctx.getCurrentMsg().getETFBody();
 
       while (rs.next())
       {
         HashMap values = new HashMap();
         HiETF recNode = null;
         if (nRowCounts > iMaxLinePerPage)
           recNode = HiETFFactory.createETF("REC_" + nRowCounts, "");
         else
           recNode = etf.addNode("REC_" + nRowCounts, "");
         for (int i = 0; i < cols; ++i)
         {
           String strColName = meta.getColumnName(i + 1);
           Object value = rs.getObject(i + 1);
           String strValue = "";
           if (value != null)
             strValue = String.valueOf(value).trim();
           values.put(strColName.toUpperCase(), strValue);
 
           recNode.addNode(strColName, strValue);
         }
         if (nRowCounts == nMaxCol)
           break;
         if (nRowCounts > 1)
           out.write("\n".getBytes());
         out.write(recNode.toString().getBytes());
         list.add(values);
         ++nRowCounts;
       }
       if (log.isDebugEnabled()) {
         log.debug("execMultiQuery is end.......");
       }
       if (nRowCounts > nMaxCol)
         log.warn("[Warning!] 超过最大限制数[1000]，实际条数[" + nRowCounts + "]");
     } finally {
       out.close();
     }
   }
 
   public static List execMultiQueryExt(String strTableName, File file, String strSql, int nMaxCol, HiMessageContext ctx, int iMaxLinePerPage)
     throws HiException
   {
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (log.isDebugEnabled()) {
       log.debug("execMultiQueryExt is start,sql=[" + strSql + "]");
     }
     Connection conn = ctx.getDataBaseUtil().getConnection();
     PreparedStatement stmt = null;
     ResultSet rs = null;
     FileOutputStream out = null;
     try
     {
       String strExtSql = "SELECT DISTINCT CTBLNM FROM " + strTableName + " where length(CTBLNM)>0";
       if (log.isInfoEnabled())
       {
         log.info("execMultiQueryExt strExtSql[" + strExtSql + "]");
       }
       stmt = conn.prepareStatement(strExtSql);
       String strExtTable = null;
       rs = stmt.executeQuery();
       while (rs.next())
       {
         strExtTable = rs.getString(1);
         if (!(StringUtils.isNotEmpty(strExtTable)))
           continue;
         strExtTable = strExtTable.trim();
       }
 
       stmt.close();
       rs.close();
 
       strSql = StringUtils.replace(strSql, "FROM " + strTableName, "FROM " + strTableName + " left join " + strExtTable + " on " + strTableName + ".EXTKEY = " + strExtTable + ".EXTKEY ");
 
       if (log.isInfoEnabled())
       {
         log.info("execMultiQueryExt Sql[" + strSql + "]");
       }
 
       stmt = conn.prepareStatement(strSql);
       ArrayList list = new ArrayList();
       rs = stmt.executeQuery();
       writeQueryFile(iMaxLinePerPage, file, nMaxCol, ctx, log, rs, list);
       ArrayList localArrayList1 = list;
 
       return localArrayList1;
     }
     catch (IOException e)
     {
     }
     catch (Exception e)
     {
       throw new HiSQLException("215026", e, strSql);
     }
     finally
     {
       try
       {
         out.close();
       }
       catch (Exception e)
       {
       }
       ctx.getDataBaseUtil().close(stmt, rs);
     }
   }
 
   static void extSqlData2Etf(Map curRec, HiETF root, HiMessageContext ctx)
     throws HiException
   {
     Iterator recIt = curRec.entrySet().iterator();
 
     String extKey = null;
     String childTable = null;
 
     while (recIt.hasNext())
     {
       Map.Entry recEntry = (Map.Entry)recIt.next();
       String colName = (String)recEntry.getKey();
       String colVal = (String)recEntry.getValue();
 
       if (colName.equalsIgnoreCase("CTBLNM"))
       {
         childTable = colVal;
       }
       if (colName.equalsIgnoreCase("EXTKEY"))
       {
         extKey = colVal;
       }
 
       root.setChildValue(colName, colVal);
     }
 
     if ((!(StringUtils.isNotBlank(childTable))) || (!(StringUtils.isNotBlank(extKey)))) {
       return;
     }
     String extCond = " EXTKEY=" + extKey;
     HiDbtUtils.dbtextrdreccon(childTable, extCond, root, ctx);
   }
 
   public static String getDynSentence(HiMessageContext ctx, String alias)
     throws HiException
   {
     return getDynSentence(ctx, alias, ctx.getCurrentMsg().getETFBody(), false);
   }
 
   public static String getDynSentence(HiMessageContext ctx, String alias, HiETF curEtf)
     throws HiException
   {
     String sqlSentence = HiArgUtils.getStringNotNull(ctx, "SENTENCE." + alias);
 
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
 
     String strFields = (String)ctx.getProperty("FIELDS." + alias);
 
     if (StringUtils.isEmpty(strFields)) {
       return sqlSentence;
     }
     String[] fields = StringUtils.split(strFields, "|");
     String[] values = new String[fields.length];
     for (int i = 0; i < fields.length; ++i)
     {
       values[i] = ((String)ctx.getSpecExpre(curEtf, fields[i]));
     }
 
     sqlSentence = HiStringUtils.format(sqlSentence, values);
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiArgUtils.getDynSentence00", sqlSentence));
     }
     return sqlSentence;
   }
 
   public static String getDynSentence(HiMessageContext ctx, String alias, HiETF curEtf, boolean escape)
     throws HiException
   {
     String sqlSentence = HiArgUtils.getStringNotNull(ctx, "SENTENCE." + alias);
 
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
 
     String strFields = (String)ctx.getProperty("FIELDS." + alias);
 
     if (StringUtils.isEmpty(strFields)) {
       return sqlSentence;
     }
     String[] fields = StringUtils.split(strFields, "|");
     String[] values = new String[fields.length];
     for (int i = 0; i < fields.length; ++i)
     {
       values[i] = ((String)ctx.getSpecExpre(curEtf, fields[i]));
       if (escape) {
         values[i] = sqlEscape(values[i]);
       }
 
     }
 
     sqlSentence = HiStringUtils.format(sqlSentence, values);
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiArgUtils.getDynSentence00", sqlSentence));
     }
     return sqlSentence;
   }
 
   public static String getDynSentence(HiMessageContext ctx, String alias, HiETF curEtf, String[] clobNames, ArrayList params)
     throws HiException
   {
     String sqlSentence = HiArgUtils.getStringNotNull(ctx, "SENTENCE." + alias);
 
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
 
     String strFields = (String)ctx.getProperty("FIELDS." + alias);
 
     if (StringUtils.isEmpty(strFields)) {
       return sqlSentence;
     }
     String[] fields = StringUtils.split(strFields, "|");
 
     for (int i = 0; i < fields.length; ++i)
     {
       String value = (String)ctx.getSpecExpre(curEtf, fields[i]);
       value = sqlEscape(value);
       boolean founded = false;
       for (int j = 0; j < clobNames.length; ++j) {
         if (clobNames[j].equalsIgnoreCase(fields[i])) {
           founded = true;
         }
       }
 
       if (founded)
       {
         params.add(value.getBytes());
       }
       else params.add(value);
 
     }
 
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiArgUtils.getDynSentence00", sqlSentence, params));
     }
     return sqlSentence;
   }
 
   public static String replace(HiMessageContext ctx, HiETF curEtf, String sqlSts, String fldLst, String deli)
   {
     String[] fields = StringUtils.split(fldLst, deli);
     String[] values = new String[fields.length];
     for (int i = 0; i < fields.length; ++i)
     {
       values[i] = ((String)ctx.getSpecExpre(curEtf, fields[i]));
     }
 
     sqlSts = HiStringUtils.format(sqlSts, values);
     return sqlSts;
   }
 
   public static String sqlEscape(String value) {
     return StringUtils.replace(value, "'", "''");
   }
 
   public static boolean isNumberType(String type) {
     return (("NUMBER".equals(type)) || ("INTEGER".equals(type)));
   }
 
   public static boolean isStringType(String type) {
     return (("VARCHAR2".equals(type)) || ("VARCHAR".equals(type)) || ("LONG".equals(type)));
   }
 
   public static boolean isCLOBType(String type) {
     return "CLOB".equals(type);
   }
 }