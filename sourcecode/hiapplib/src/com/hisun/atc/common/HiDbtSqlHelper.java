/*      */ package com.hisun.atc.common;
/*      */ 
/*      */ import com.hisun.database.HiDataBaseUtil;
/*      */ import com.hisun.exception.HiException;
/*      */ import com.hisun.exception.HiSQLException;
/*      */ import com.hisun.hilog4j.HiLog;
/*      */ import com.hisun.hilog4j.Logger;
/*      */ import com.hisun.message.HiETF;
/*      */ import com.hisun.message.HiETFFactory;
/*      */ import com.hisun.message.HiMessage;
/*      */ import com.hisun.message.HiMessageContext;
/*      */ import com.hisun.util.HiStringManager;
/*      */ import com.hisun.util.HiStringUtils;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ 
/*      */ public class HiDbtSqlHelper
/*      */ {
/*   39 */   private static HiStringManager sm = HiStringManager.getManager();
/*      */ 
/*      */   public static String getKeyCndSts(String keyName, String keyValue, int keyType)
/*      */   {
/*   56 */     String cndSts = "";
/*   57 */     if (StringUtils.isNotBlank(keyName))
/*      */     {
/*   60 */       if (keyType == 0)
/*      */       {
/*   62 */         cndSts = cndSts + keyName + "='" + keyValue + "'";
/*      */       }
/*      */       else
/*      */       {
/*   66 */         cndSts = cndSts + keyName + "=" + keyValue;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*   71 */     return cndSts;
/*      */   }
/*      */ 
/*      */   public static String buildUpdateSentence(HiMessageContext ctx, HiETF root, String tableName, String cndSts, boolean isExt, ArrayList param)
/*      */     throws HiException
/*      */   {
/*   77 */     if ((StringUtils.isEmpty(tableName)) || (root == null))
/*      */     {
/*   79 */       throw new HiException("220026", "前置系统错误");
/*      */     }
/*   81 */     StringBuffer sqlCmd = new StringBuffer();
/*      */ 
/*   83 */     HashMap tblMeta = ctx.getTableMetaData(tableName);
/*      */ 
/*   85 */     sqlCmd.append("UPDATE ");
/*   86 */     sqlCmd.append(tableName);
/*   87 */     sqlCmd.append(" SET ");
/*      */ 
/*   89 */     Iterator metaIt = tblMeta.entrySet().iterator();
/*      */ 
/*   91 */     Map.Entry metaEntry = null;
/*   92 */     String colNam = null;
/*   93 */     String colEtf = null;
/*      */ 
/*   95 */     while (metaIt.hasNext())
/*      */     {
/*   97 */       metaEntry = (Map.Entry)metaIt.next();
/*      */ 
/*   99 */       colNam = (String)metaEntry.getKey();
/*  100 */       if (isExt)
/*      */       {
/*  102 */         if (colNam.equalsIgnoreCase("CTBLNM")) continue; if (colNam.equalsIgnoreCase("EXTKEY")) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  108 */       colEtf = root.getChildValue(colNam);
/*  109 */       if (colEtf == null) continue; if ("".equals(colEtf)) {
/*      */         continue;
/*      */       }
/*      */ 
/*  113 */       sqlCmd.append(colNam);
/*  114 */       sqlCmd.append("=");
/*  115 */       param.add(colEtf);
/*  116 */       sqlCmd.append('?');
/*      */ 
/*  129 */       sqlCmd.append(",");
/*      */     }
/*      */ 
/*  133 */     sqlCmd.deleteCharAt(sqlCmd.length() - 1);
/*      */ 
/*  136 */     if (StringUtils.isNotBlank(cndSts))
/*      */     {
/*  138 */       sqlCmd.append(" WHERE ");
/*  139 */       sqlCmd.append(cndSts);
/*      */     }
/*      */ 
/*  142 */     return sqlCmd.toString();
/*      */   }
/*      */ 
/*      */   public static String buildUpdateSentence(HiMessageContext ctx, HiETF root, String tableName, String cndSts, boolean isExt)
/*      */     throws HiException
/*      */   {
/*  162 */     if ((StringUtils.isEmpty(tableName)) || (root == null))
/*      */     {
/*  164 */       throw new HiException("220026", "前置系统错误");
/*      */     }
/*  166 */     StringBuffer sqlCmd = new StringBuffer();
/*      */ 
/*  168 */     HashMap tblMeta = ctx.getTableMetaData(tableName);
/*      */ 
/*  170 */     sqlCmd.append("UPDATE ");
/*  171 */     sqlCmd.append(tableName);
/*  172 */     sqlCmd.append(" SET ");
/*      */ 
/*  174 */     Iterator metaIt = tblMeta.entrySet().iterator();
/*      */ 
/*  176 */     Map.Entry metaEntry = null;
/*  177 */     String colNam = null;
/*  178 */     HiETF colEtf = null;
/*      */ 
/*  180 */     while (metaIt.hasNext())
/*      */     {
/*  182 */       metaEntry = (Map.Entry)metaIt.next();
/*      */ 
/*  184 */       colNam = (String)metaEntry.getKey();
/*  185 */       if (isExt)
/*      */       {
/*  187 */         if (colNam.equalsIgnoreCase("CTBLNM")) continue; if (colNam.equalsIgnoreCase("EXTKEY")) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  193 */       colEtf = root.getChildNode(colNam);
/*  194 */       if (colEtf == null) continue; if ("".equals(colEtf)) {
/*      */         continue;
/*      */       }
/*      */ 
/*  198 */       sqlCmd.append(colNam);
/*  199 */       sqlCmd.append("=");
/*  200 */       if (isNumberType((String)tblMeta.get(colNam))) {
/*  201 */         sqlCmd.append(colEtf.getValue());
/*      */       } else {
/*  203 */         sqlCmd.append("'");
/*  204 */         sqlCmd.append(sqlEscape(colEtf.getValue()));
/*  205 */         sqlCmd.append("'");
/*      */       }
/*  207 */       sqlCmd.append(",");
/*      */     }
/*      */ 
/*  211 */     sqlCmd.deleteCharAt(sqlCmd.length() - 1);
/*      */ 
/*  214 */     if (StringUtils.isNotBlank(cndSts))
/*      */     {
/*  216 */       sqlCmd.append(" WHERE ");
/*  217 */       sqlCmd.append(cndSts);
/*      */     }
/*      */ 
/*  220 */     return sqlCmd.toString();
/*      */   }
/*      */ 
/*      */   public static String buildUpdate1Sentence(HiMessageContext ctx, HiETF root, String tableName, String cndSts, boolean isExt)
/*      */     throws HiException
/*      */   {
/*  240 */     if ((StringUtils.isEmpty(tableName)) || (root == null))
/*      */     {
/*  242 */       throw new HiException("220026", "前置系统错误");
/*      */     }
/*  244 */     StringBuffer sqlCmd = new StringBuffer();
/*      */ 
/*  246 */     HashMap tblMeta = ctx.getTableMetaData(tableName);
/*      */ 
/*  248 */     sqlCmd.append("UPDATE ");
/*  249 */     sqlCmd.append(tableName);
/*  250 */     sqlCmd.append(" SET ");
/*      */ 
/*  252 */     Iterator metaIt = tblMeta.entrySet().iterator();
/*      */ 
/*  254 */     Map.Entry metaEntry = null;
/*  255 */     String colNam = null;
/*  256 */     HiETF colEtf = null;
/*      */ 
/*  258 */     while (metaIt.hasNext())
/*      */     {
/*  260 */       metaEntry = (Map.Entry)metaIt.next();
/*      */ 
/*  262 */       colNam = (String)metaEntry.getKey();
/*  263 */       if (isExt)
/*      */       {
/*  265 */         if (colNam.equalsIgnoreCase("CTBLNM")) continue; if (colNam.equalsIgnoreCase("EXTKEY")) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  271 */       colEtf = root.getChildNode(colNam);
/*  272 */       if (colEtf == null) {
/*      */         continue;
/*      */       }
/*      */ 
/*  276 */       sqlCmd.append(colNam);
/*  277 */       sqlCmd.append("=");
/*  278 */       if (isNumberType((String)tblMeta.get(colNam))) {
/*  279 */         sqlCmd.append(colEtf.getValue());
/*      */       } else {
/*  281 */         sqlCmd.append("'");
/*  282 */         sqlCmd.append(sqlEscape(colEtf.getValue()));
/*  283 */         sqlCmd.append("'");
/*      */       }
/*  285 */       sqlCmd.append(",");
/*      */     }
/*      */ 
/*  289 */     sqlCmd.deleteCharAt(sqlCmd.length() - 1);
/*      */ 
/*  292 */     if (StringUtils.isNotBlank(cndSts))
/*      */     {
/*  294 */       sqlCmd.append(" WHERE ");
/*  295 */       sqlCmd.append(cndSts);
/*      */     }
/*      */ 
/*  298 */     return sqlCmd.toString();
/*      */   }
/*      */ 
/*      */   public static String buildInsertSentence(HiMessageContext ctx, HiETF etfBody, String tableName, ArrayList params)
/*      */     throws HiException
/*      */   {
/*  315 */     StringBuffer names = new StringBuffer();
/*  316 */     StringBuffer values = new StringBuffer();
/*      */ 
/*  325 */     Map tblMeta = ctx.getTableMetaData(tableName);
/*      */ 
/*  327 */     Iterator metaIt = tblMeta.entrySet().iterator();
/*  328 */     boolean first = true;
/*  329 */     while (metaIt.hasNext())
/*      */     {
/*  331 */       Map.Entry metaEntry = (Map.Entry)metaIt.next();
/*      */ 
/*  333 */       String colNam = (String)metaEntry.getKey();
/*  334 */       String colEtf = etfBody.getChildValue(colNam);
/*      */ 
/*  336 */       if (colEtf == null) continue; if ("".equals(colEtf))
/*      */       {
/*      */         continue;
/*      */       }
/*      */ 
/*  344 */       if (!(first)) {
/*  345 */         names.append(",");
/*  346 */         values.append(",");
/*      */       }
/*  348 */       names.append(colNam);
/*  349 */       values.append('?');
/*  350 */       params.add(colEtf);
/*      */ 
/*  370 */       first = false;
/*      */     }
/*      */ 
/*  375 */     StringBuffer sqlCmd = new StringBuffer();
/*  376 */     sqlCmd.append("INSERT INTO ");
/*  377 */     sqlCmd.append(tableName);
/*  378 */     sqlCmd.append(" (");
/*  379 */     sqlCmd.append(names);
/*  380 */     sqlCmd.append(") VALUES (");
/*  381 */     sqlCmd.append(values);
/*  382 */     sqlCmd.append(")");
/*      */ 
/*  384 */     return sqlCmd.toString();
/*      */   }
/*      */ 
/*      */   static int extUpdateRecord(String tableName, String cond, HiETF root, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  404 */     String sqlSentence = buildUpdateSentence(ctx, root, tableName, cond, true);
/*      */ 
/*  407 */     return ctx.getDataBaseUtil().execUpdate(sqlSentence);
/*      */   }
/*      */ 
/*      */   static String getChildTableName(String tablename, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  422 */     HiMessage mess = ctx.getCurrentMsg();
/*  423 */     HiETF root = mess.getETFBody();
/*  424 */     Logger log = HiLog.getLogger(mess);
/*  425 */     String childTableName = null;
/*      */ 
/*  427 */     String sqlstring = HiStringUtils.format("SELECT CNDFLD FROM PUBTBLFLD WHERE TBLNAM = '%s'", tablename);
/*      */ 
/*  429 */     if (log.isInfoEnabled())
/*      */     {
/*  431 */       log.info("查询子表条件域：[" + sqlstring + "]");
/*      */     }
/*  433 */     List lRec = ctx.getDataBaseUtil().execQuery(sqlstring);
/*  434 */     if ((lRec != null) && (lRec.size() == 1))
/*      */     {
/*  436 */       Map queryRec = (HashMap)lRec.get(0);
/*  437 */       String field = (String)queryRec.get("CNDFLD");
/*  438 */       String fieldvalue = root.getChildValue(field);
/*  439 */       if (!(StringUtils.isEmpty(fieldvalue)))
/*      */       {
/*  441 */         sqlstring = HiStringUtils.format("SELECT CTBLNM FROM PUBTBLCND WHERE TBLNAM = '%s' AND FLDVAL='%s'", tablename, fieldvalue);
/*      */ 
/*  445 */         if (log.isInfoEnabled())
/*      */         {
/*  447 */           log.info("查询扩展表名：[" + sqlstring + "]");
/*      */         }
/*  449 */         List lRec2 = ctx.getDataBaseUtil().execQuery(sqlstring);
/*  450 */         if ((lRec2 != null) && (lRec2.size() == 1))
/*      */         {
/*  452 */           queryRec = (HashMap)lRec2.get(0);
/*  453 */           childTableName = (String)queryRec.get("CTBLNM");
/*  454 */           if (log.isInfoEnabled())
/*      */           {
/*  456 */             log.info("扩展表名为：[" + childTableName + "]");
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  463 */     return childTableName;
/*      */   }
/*      */ 
/*      */   static String getExtKey(HiMessageContext tranData)
/*      */     throws HiException
/*      */   {
/*  475 */     List lRec2 = tranData.getDataBaseUtil().execQuery("SELECT NEXTVAL FOR PUBSEQKEY FROM TABLE(VALUES(1)) AS ANNONY");
/*      */ 
/*  477 */     if ((lRec2 != null) && (lRec2.size() == 1))
/*      */     {
/*  479 */       Map queryRec = (HashMap)lRec2.get(0);
/*  480 */       Iterator recIt = queryRec.entrySet().iterator();
/*  481 */       Map.Entry recEntry = (Map.Entry)recIt.next();
/*  482 */       String ExtKey = (String)recEntry.getValue();
/*  483 */       return StringUtils.leftPad(ExtKey, 11, '0');
/*      */     }
/*      */ 
/*  486 */     return null;
/*      */   }
/*      */ 
/*      */   public static int dbtCursor(String sqlCmd, String flag, String cursorName, HiETF root, HiMessageContext ctx, boolean isExt)
/*      */     throws HiException
/*      */   {
/*      */     HiDBCursor cursor;
/*  513 */     int ret = 0;
/*  514 */     if (StringUtils.isEmpty(cursorName))
/*      */     {
/*  516 */       cursorName = "CURSOR.CURSOR_1";
/*      */     }
/*      */     else
/*      */     {
/*  520 */       cursorName = "CURSOR." + cursorName.toUpperCase();
/*      */     }
/*      */ 
/*  523 */     if (StringUtils.equalsIgnoreCase(flag, "O"))
/*      */     {
/*  525 */       cursor = new HiDBCursor(ctx.getCurrentMsg(), sqlCmd, ctx);
/*      */ 
/*  527 */       ctx.setBaseSource(cursorName, cursor);
/*      */     }
/*  529 */     else if (StringUtils.equalsIgnoreCase(flag, "F"))
/*      */     {
/*  532 */       cursor = (HiDBCursor)ctx.getBaseSource(cursorName);
/*  533 */       if (cursor == null)
/*      */       {
/*  536 */         throw new HiException("220096", cursorName);
/*      */       }
/*      */ 
/*  540 */       Map curRec = cursor.next();
/*      */ 
/*  542 */       if (curRec == null)
/*      */       {
/*  546 */         ret = 100;
/*  547 */         return ret;
/*      */       }
/*      */ 
/*  551 */       if (isExt)
/*      */       {
/*  553 */         extSqlData2Etf(curRec, root, ctx);
/*      */       }
/*      */       else
/*      */       {
/*  557 */         sqlData2Etf(curRec, root);
/*      */       }
/*  559 */       ret = 1;
/*      */     }
/*  561 */     else if (StringUtils.equalsIgnoreCase(flag, "C"))
/*      */     {
/*  564 */       cursor = (HiDBCursor)ctx.getBaseSource(cursorName);
/*  565 */       if (cursor == null)
/*      */       {
/*  568 */         throw new HiException("220096", cursorName);
/*      */       }
/*      */ 
/*  572 */       cursor.close();
/*  573 */       ctx.removeBaseSource(cursorName);
/*      */     }
/*      */ 
/*  576 */     return ret;
/*      */   }
/*      */ 
/*      */   public static HiDBCursor dbtCursor(String sqlCmd, String flag, HiDBCursor cursor, HiETF root, HiMessageContext ctx, boolean isExt)
/*      */     throws HiException
/*      */   {
/*  603 */     int ret = 0;
/*      */ 
/*  605 */     if (StringUtils.equalsIgnoreCase(flag, "O"))
/*      */     {
/*  607 */       cursor = new HiDBCursor(ctx.getCurrentMsg(), sqlCmd, ctx);
/*      */     }
/*  609 */     else if (StringUtils.equalsIgnoreCase(flag, "F"))
/*      */     {
/*  611 */       if (cursor == null)
/*      */       {
/*  614 */         throw new HiException("220096");
/*      */       }
/*      */ 
/*  618 */       Map curRec = cursor.next();
/*      */ 
/*  620 */       if (curRec == null)
/*      */       {
/*  624 */         cursor.ret = 100;
/*  625 */         return cursor;
/*      */       }
/*      */ 
/*  629 */       if (isExt)
/*      */       {
/*  631 */         extSqlData2Etf(curRec, root, ctx);
/*      */       }
/*      */       else
/*      */       {
/*  635 */         sqlData2Etf(curRec, root);
/*      */       }
/*  637 */       ret = 1;
/*      */     }
/*  639 */     else if (StringUtils.equalsIgnoreCase(flag, "C"))
/*      */     {
/*  641 */       if (cursor == null)
/*      */       {
/*  643 */         throw new HiException("220096");
/*      */       }
/*      */ 
/*  647 */       cursor.close();
/*      */     }
/*      */ 
/*  651 */     if (cursor != null)
/*      */     {
/*  653 */       cursor.ret = ret;
/*      */     }
/*      */ 
/*  656 */     return cursor;
/*      */   }
/*      */ 
/*      */   static void sqlData2Etf(Map curRec, HiETF root)
/*      */   {
/*  667 */     Iterator recIt = curRec.entrySet().iterator();
/*      */ 
/*  670 */     while (recIt.hasNext())
/*      */     {
/*  672 */       Map.Entry recEntry = (Map.Entry)recIt.next();
/*  673 */       root.setChildValue((String)recEntry.getKey(), (String)recEntry.getValue());
/*      */     }
/*      */   }
/*      */ 
/*      */   public static List execMultiQuery(File file, String strSql, int nMaxCol, HiMessageContext ctx, int iMaxLinePerPage)
/*      */     throws HiException
/*      */   {
/*  682 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*  683 */     if (log.isDebugEnabled())
/*  684 */       log.debug("execMultiQuery is start,sql=[" + strSql + "]");
/*  685 */     Connection conn = ctx.getDataBaseUtil().getConnection();
/*  686 */     PreparedStatement stmt = null;
/*  687 */     ResultSet rs = null;
/*  688 */     FileOutputStream out = null;
/*      */     try
/*      */     {
/*  691 */       stmt = conn.prepareStatement(strSql);
/*  692 */       ArrayList list = new ArrayList();
/*  693 */       rs = stmt.executeQuery();
/*  694 */       writeQueryFile(iMaxLinePerPage, file, nMaxCol, ctx, log, rs, list);
/*  695 */       ArrayList localArrayList1 = list;
/*      */ 
/*  718 */       return localArrayList1;
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  706 */       throw new HiSQLException("215026", e, strSql);
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/*  713 */         out.close();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*      */       }
/*  718 */       ctx.getDataBaseUtil().close(stmt, rs);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static void writeQueryFile(int iMaxLinePerPage, File file, int nMaxCol, HiMessageContext ctx, Logger log, ResultSet rs, ArrayList list) throws SQLException, FileNotFoundException, IOException
/*      */   {
/*  724 */     FileOutputStream out = null;
/*  725 */     ResultSetMetaData meta = rs.getMetaData();
/*  726 */     int cols = meta.getColumnCount();
/*  727 */     int nRowCounts = 1;
/*      */     try {
/*  729 */       out = new FileOutputStream(file);
/*  730 */       HiETF etf = ctx.getCurrentMsg().getETFBody();
/*      */ 
/*  732 */       while (rs.next())
/*      */       {
/*  734 */         HashMap values = new HashMap();
/*  735 */         HiETF recNode = null;
/*  736 */         if (nRowCounts > iMaxLinePerPage)
/*  737 */           recNode = HiETFFactory.createETF("REC_" + nRowCounts, "");
/*      */         else
/*  739 */           recNode = etf.addNode("REC_" + nRowCounts, "");
/*  740 */         for (int i = 0; i < cols; ++i)
/*      */         {
/*  742 */           String strColName = meta.getColumnName(i + 1);
/*  743 */           Object value = rs.getObject(i + 1);
/*  744 */           String strValue = "";
/*  745 */           if (value != null)
/*  746 */             strValue = String.valueOf(value).trim();
/*  747 */           values.put(strColName.toUpperCase(), strValue);
/*      */ 
/*  749 */           recNode.addNode(strColName, strValue);
/*      */         }
/*  751 */         if (nRowCounts == nMaxCol)
/*      */           break;
/*  753 */         if (nRowCounts > 1)
/*  754 */           out.write("\n".getBytes());
/*  755 */         out.write(recNode.toString().getBytes());
/*  756 */         list.add(values);
/*  757 */         ++nRowCounts;
/*      */       }
/*  759 */       if (log.isDebugEnabled()) {
/*  760 */         log.debug("execMultiQuery is end.......");
/*      */       }
/*  762 */       if (nRowCounts > nMaxCol)
/*  763 */         log.warn("[Warning!] 超过最大限制数[1000]，实际条数[" + nRowCounts + "]");
/*      */     } finally {
/*  765 */       out.close();
/*      */     }
/*      */   }
/*      */ 
/*      */   public static List execMultiQueryExt(String strTableName, File file, String strSql, int nMaxCol, HiMessageContext ctx, int iMaxLinePerPage)
/*      */     throws HiException
/*      */   {
/*  773 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*  774 */     if (log.isDebugEnabled()) {
/*  775 */       log.debug("execMultiQueryExt is start,sql=[" + strSql + "]");
/*      */     }
/*  777 */     Connection conn = ctx.getDataBaseUtil().getConnection();
/*  778 */     PreparedStatement stmt = null;
/*  779 */     ResultSet rs = null;
/*  780 */     FileOutputStream out = null;
/*      */     try
/*      */     {
/*  783 */       String strExtSql = "SELECT DISTINCT CTBLNM FROM " + strTableName + " where length(CTBLNM)>0";
/*  784 */       if (log.isInfoEnabled())
/*      */       {
/*  786 */         log.info("execMultiQueryExt strExtSql[" + strExtSql + "]");
/*      */       }
/*  788 */       stmt = conn.prepareStatement(strExtSql);
/*  789 */       String strExtTable = null;
/*  790 */       rs = stmt.executeQuery();
/*  791 */       while (rs.next())
/*      */       {
/*  793 */         strExtTable = rs.getString(1);
/*  794 */         if (!(StringUtils.isNotEmpty(strExtTable)))
/*      */           continue;
/*  796 */         strExtTable = strExtTable.trim();
/*      */       }
/*      */ 
/*  799 */       stmt.close();
/*  800 */       rs.close();
/*      */ 
/*  802 */       strSql = StringUtils.replace(strSql, "FROM " + strTableName, "FROM " + strTableName + " left join " + strExtTable + " on " + strTableName + ".EXTKEY = " + strExtTable + ".EXTKEY ");
/*      */ 
/*  806 */       if (log.isInfoEnabled())
/*      */       {
/*  808 */         log.info("execMultiQueryExt Sql[" + strSql + "]");
/*      */       }
/*      */ 
/*  812 */       stmt = conn.prepareStatement(strSql);
/*  813 */       ArrayList list = new ArrayList();
/*  814 */       rs = stmt.executeQuery();
/*  815 */       writeQueryFile(iMaxLinePerPage, file, nMaxCol, ctx, log, rs, list);
/*  816 */       ArrayList localArrayList1 = list;
/*      */ 
/*  839 */       return localArrayList1;
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  827 */       throw new HiSQLException("215026", e, strSql);
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/*  834 */         out.close();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*      */       }
/*  839 */       ctx.getDataBaseUtil().close(stmt, rs);
/*      */     }
/*      */   }
/*      */ 
/*      */   static void extSqlData2Etf(Map curRec, HiETF root, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  854 */     Iterator recIt = curRec.entrySet().iterator();
/*      */ 
/*  857 */     String extKey = null;
/*  858 */     String childTable = null;
/*      */ 
/*  862 */     while (recIt.hasNext())
/*      */     {
/*  864 */       Map.Entry recEntry = (Map.Entry)recIt.next();
/*  865 */       String colName = (String)recEntry.getKey();
/*  866 */       String colVal = (String)recEntry.getValue();
/*      */ 
/*  868 */       if (colName.equalsIgnoreCase("CTBLNM"))
/*      */       {
/*  870 */         childTable = colVal;
/*      */       }
/*  872 */       if (colName.equalsIgnoreCase("EXTKEY"))
/*      */       {
/*  874 */         extKey = colVal;
/*      */       }
/*      */ 
/*  878 */       root.setChildValue(colName, colVal);
/*      */     }
/*      */ 
/*  883 */     if ((!(StringUtils.isNotBlank(childTable))) || (!(StringUtils.isNotBlank(extKey)))) {
/*      */       return;
/*      */     }
/*  886 */     String extCond = " EXTKEY=" + extKey;
/*  887 */     HiDbtUtils.dbtextrdreccon(childTable, extCond, root, ctx);
/*      */   }
/*      */ 
/*      */   public static String getDynSentence(HiMessageContext ctx, String alias)
/*      */     throws HiException
/*      */   {
/*  901 */     return getDynSentence(ctx, alias, ctx.getCurrentMsg().getETFBody(), false);
/*      */   }
/*      */ 
/*      */   public static String getDynSentence(HiMessageContext ctx, String alias, HiETF curEtf)
/*      */     throws HiException
/*      */   {
/*  918 */     String sqlSentence = HiArgUtils.getStringNotNull(ctx, "SENTENCE." + alias);
/*      */ 
/*  920 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*      */ 
/*  922 */     String strFields = (String)ctx.getProperty("FIELDS." + alias);
/*      */ 
/*  924 */     if (StringUtils.isEmpty(strFields)) {
/*  925 */       return sqlSentence;
/*      */     }
/*  927 */     String[] fields = StringUtils.split(strFields, "|");
/*  928 */     String[] values = new String[fields.length];
/*  929 */     for (int i = 0; i < fields.length; ++i)
/*      */     {
/*  931 */       values[i] = ((String)ctx.getSpecExpre(curEtf, fields[i]));
/*      */     }
/*      */ 
/*  935 */     sqlSentence = HiStringUtils.format(sqlSentence, values);
/*  936 */     if (log.isInfoEnabled()) {
/*  937 */       log.info(sm.getString("HiArgUtils.getDynSentence00", sqlSentence));
/*      */     }
/*  939 */     return sqlSentence;
/*      */   }
/*      */ 
/*      */   public static String getDynSentence(HiMessageContext ctx, String alias, HiETF curEtf, boolean escape)
/*      */     throws HiException
/*      */   {
/*  955 */     String sqlSentence = HiArgUtils.getStringNotNull(ctx, "SENTENCE." + alias);
/*      */ 
/*  957 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*      */ 
/*  959 */     String strFields = (String)ctx.getProperty("FIELDS." + alias);
/*      */ 
/*  961 */     if (StringUtils.isEmpty(strFields)) {
/*  962 */       return sqlSentence;
/*      */     }
/*  964 */     String[] fields = StringUtils.split(strFields, "|");
/*  965 */     String[] values = new String[fields.length];
/*  966 */     for (int i = 0; i < fields.length; ++i)
/*      */     {
/*  968 */       values[i] = ((String)ctx.getSpecExpre(curEtf, fields[i]));
/*  969 */       if (escape) {
/*  970 */         values[i] = sqlEscape(values[i]);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  975 */     sqlSentence = HiStringUtils.format(sqlSentence, values);
/*  976 */     if (log.isInfoEnabled()) {
/*  977 */       log.info(sm.getString("HiArgUtils.getDynSentence00", sqlSentence));
/*      */     }
/*  979 */     return sqlSentence;
/*      */   }
/*      */ 
/*      */   public static String getDynSentence(HiMessageContext ctx, String alias, HiETF curEtf, String[] clobNames, ArrayList params)
/*      */     throws HiException
/*      */   {
/*  996 */     String sqlSentence = HiArgUtils.getStringNotNull(ctx, "SENTENCE." + alias);
/*      */ 
/*  998 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*      */ 
/* 1000 */     String strFields = (String)ctx.getProperty("FIELDS." + alias);
/*      */ 
/* 1002 */     if (StringUtils.isEmpty(strFields)) {
/* 1003 */       return sqlSentence;
/*      */     }
/* 1005 */     String[] fields = StringUtils.split(strFields, "|");
/*      */ 
/* 1007 */     for (int i = 0; i < fields.length; ++i)
/*      */     {
/* 1009 */       String value = (String)ctx.getSpecExpre(curEtf, fields[i]);
/* 1010 */       value = sqlEscape(value);
/* 1011 */       boolean founded = false;
/* 1012 */       for (int j = 0; j < clobNames.length; ++j) {
/* 1013 */         if (clobNames[j].equalsIgnoreCase(fields[i])) {
/* 1014 */           founded = true;
/*      */         }
/*      */       }
/*      */ 
/* 1018 */       if (founded)
/*      */       {
/* 1020 */         params.add(value.getBytes());
/*      */       }
/*      */       else params.add(value);
/*      */ 
/*      */     }
/*      */ 
/* 1026 */     if (log.isInfoEnabled()) {
/* 1027 */       log.info(sm.getString("HiArgUtils.getDynSentence00", sqlSentence, params));
/*      */     }
/* 1029 */     return sqlSentence;
/*      */   }
/*      */ 
/*      */   public static String replace(HiMessageContext ctx, HiETF curEtf, String sqlSts, String fldLst, String deli)
/*      */   {
/* 1052 */     String[] fields = StringUtils.split(fldLst, deli);
/* 1053 */     String[] values = new String[fields.length];
/* 1054 */     for (int i = 0; i < fields.length; ++i)
/*      */     {
/* 1057 */       values[i] = ((String)ctx.getSpecExpre(curEtf, fields[i]));
/*      */     }
/*      */ 
/* 1060 */     sqlSts = HiStringUtils.format(sqlSts, values);
/* 1061 */     return sqlSts;
/*      */   }
/*      */ 
/*      */   public static String sqlEscape(String value) {
/* 1065 */     return StringUtils.replace(value, "'", "''");
/*      */   }
/*      */ 
/*      */   public static boolean isNumberType(String type) {
/* 1069 */     return (("NUMBER".equals(type)) || ("INTEGER".equals(type)));
/*      */   }
/*      */ 
/*      */   public static boolean isStringType(String type) {
/* 1073 */     return (("VARCHAR2".equals(type)) || ("VARCHAR".equals(type)) || ("LONG".equals(type)));
/*      */   }
/*      */ 
/*      */   public static boolean isCLOBType(String type) {
/* 1077 */     return "CLOB".equals(type);
/*      */   }
/*      */ }