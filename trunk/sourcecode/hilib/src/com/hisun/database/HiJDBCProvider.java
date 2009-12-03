/*      */ package com.hisun.database;
/*      */ 
/*      */ import com.hisun.exception.HiException;
/*      */ import com.hisun.exception.HiSQLException;
/*      */ import com.hisun.hilog4j.HiLog;
/*      */ import com.hisun.hilog4j.Logger;
/*      */ import com.hisun.util.HiICSProperty;
/*      */ import com.hisun.util.HiSemaphore;
/*      */ import com.hisun.util.HiStringUtils;
/*      */ import java.sql.CallableStatement;
/*      */ import java.sql.Clob;
/*      */ import java.sql.Connection;
/*      */ import java.sql.DatabaseMetaData;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ 
/*      */ public abstract class HiJDBCProvider
/*      */ {
/*      */   private static final String SQLSTATE_PRIMARY = "23505";
/*   45 */   protected static Logger log = HiLog.getLogger("database.trc");
/*      */ 
/* 1144 */   private HiSemaphore dbSemaphore = null;
/*      */ 
/* 1146 */   protected String _dsName = "_DB_NAME";
/*      */ 
/*      */   public void setDBConnection(String name)
/*      */   {
/*      */   }
/*      */ 
/*      */   protected Logger getLogger()
/*      */   {
/*   57 */     return log;
/*      */   }
/*      */ 
/*      */   public List execQueryBind(String strSql, String[] args)
/*      */     throws HiException
/*      */   {
/*   71 */     if (log.isDebugEnabled()) {
/*   72 */       log.debug("Query[" + strSql + "]");
/*      */     }
/*   74 */     Connection conn = null;
/*   75 */     PreparedStatement stmt = null;
/*   76 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*   79 */       conn = getConnection();
/*   80 */       stmt = conn.prepareStatement(strSql);
/*   81 */       for (int i = 0; (args != null) && (i < args.length); ++i) {
/*   82 */         stmt.setObject(i + 1, args[i]);
/*      */       }
/*   84 */       ArrayList list = new ArrayList();
/*   85 */       rs = stmt.executeQuery();
/*   86 */       ResultSetMetaData meta = rs.getMetaData();
/*   87 */       int cols = meta.getColumnCount();
/*   88 */       while (rs.next()) {
/*   89 */         values = new HashMap();
/*   90 */         for (int i = 0; i < cols; ++i) {
/*   91 */           String strColName = meta.getColumnName(i + 1);
/*   92 */           Object value = rs.getObject(i + 1);
/*   93 */           if (value == null) {
/*   94 */             value = "";
/*      */           }
/*   96 */           if (value instanceof Clob) {
/*   97 */             Clob tmp = (Clob)value;
/*   98 */             String value1 = tmp.getSubString(1L, (int)tmp.length());
/*   99 */             values.put(strColName.toUpperCase(), value1);
/*      */           } else {
/*  101 */             String value1 = rs.getString(i + 1);
/*  102 */             values.put(strColName.toUpperCase(), StringUtils.trim(value1));
/*      */           }
/*      */         }
/*      */ 
/*  106 */         list.add(values);
/*      */       }
/*      */ 
/*  109 */       HashMap values = Collections.unmodifiableList(list);
/*      */ 
/*  117 */       return values;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  111 */       if (e instanceof HiException);
/*  114 */       throw new HiSQLException("215016", e, strSql);
/*      */     }
/*      */     finally {
/*  117 */       close(stmt, rs);
/*      */     }
/*      */   }
/*      */ 
/*      */   public List execQueryBind(String strSql, List args)
/*      */     throws HiException
/*      */   {
/*  133 */     if (log.isDebugEnabled()) {
/*  134 */       log.debug("Query[" + strSql + "]");
/*      */     }
/*  136 */     Connection conn = null;
/*  137 */     PreparedStatement stmt = null;
/*  138 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  141 */       conn = getConnection();
/*  142 */       stmt = conn.prepareStatement(strSql);
/*  143 */       for (int i = 0; (args != null) && (i < args.size()); ++i) {
/*  144 */         stmt.setObject(i + 1, args.get(i));
/*      */       }
/*  146 */       ArrayList list = new ArrayList();
/*  147 */       rs = stmt.executeQuery();
/*  148 */       ResultSetMetaData meta = rs.getMetaData();
/*  149 */       int cols = meta.getColumnCount();
/*  150 */       while (rs.next()) {
/*  151 */         values = new HashMap();
/*  152 */         for (int i = 0; i < cols; ++i) {
/*  153 */           String strColName = meta.getColumnName(i + 1);
/*  154 */           Object value = rs.getObject(i + 1);
/*  155 */           if (value == null) {
/*  156 */             value = "";
/*      */           }
/*  158 */           if (value instanceof Clob) {
/*  159 */             Clob tmp = (Clob)value;
/*  160 */             String value1 = tmp.getSubString(1L, (int)tmp.length());
/*  161 */             values.put(strColName.toUpperCase(), value1);
/*      */           } else {
/*  163 */             String value1 = rs.getString(i + 1);
/*  164 */             values.put(strColName.toUpperCase(), StringUtils.trim(value1));
/*      */           }
/*      */         }
/*      */ 
/*  168 */         list.add(values);
/*      */       }
/*      */ 
/*  171 */       HashMap values = Collections.unmodifiableList(list);
/*      */ 
/*  179 */       return values;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  173 */       if (e instanceof HiException);
/*  176 */       throw new HiSQLException("215016", e, strSql);
/*      */     }
/*      */     finally {
/*  179 */       close(stmt, rs);
/*      */     }
/*      */   }
/*      */ 
/*      */   public List execQuery(String strSql, String arg1)
/*      */     throws HiException
/*      */   {
/*  195 */     return execQuery(strSql, new String[] { arg1 });
/*      */   }
/*      */ 
/*      */   public List execQuery(String strSql, String arg1, String arg2)
/*      */     throws HiException
/*      */   {
/*  213 */     return execQuery(strSql, new String[] { arg1, arg2 });
/*      */   }
/*      */ 
/*      */   public List execQueryBind(String strSql, String arg1, String arg2)
/*      */     throws HiException
/*      */   {
/*  230 */     return execQueryBind(strSql, new String[] { arg1, arg2 });
/*      */   }
/*      */ 
/*      */   public List execQuery(String strSql, String arg1, String arg2, String arg3)
/*      */     throws HiException
/*      */   {
/*  249 */     return execQuery(strSql, new String[] { arg1, arg2, arg3 });
/*      */   }
/*      */ 
/*      */   public List execQuery(String strSql, String arg1, String arg2, String arg3, String arg4)
/*      */     throws HiException
/*      */   {
/*  270 */     return execQuery(strSql, new String[] { arg1, arg2, arg3, arg4 });
/*      */   }
/*      */ 
/*      */   public List execQuery(String strSql, String arg1, String arg2, String arg3, String arg4, String arg5)
/*      */     throws HiException
/*      */   {
/*  293 */     return execQuery(strSql, new String[] { arg1, arg2, arg3, arg4, arg5 });
/*      */   }
/*      */ 
/*      */   public List execQuery(String strSql, String[] args)
/*      */     throws HiException
/*      */   {
/*  307 */     return execQuery(HiStringUtils.format(strSql, args));
/*      */   }
/*      */ 
/*      */   public List execQuery(String strSql, List args)
/*      */     throws HiException
/*      */   {
/*  321 */     return execQuery(HiStringUtils.format(strSql, args));
/*      */   }
/*      */ 
/*      */   public List execQuery(String strSql)
/*      */     throws HiException
/*      */   {
/*  335 */     return HiDBCache.execQuery(strSql, this);
/*      */   }
/*      */ 
/*      */   protected List internal_execQuery(String strSql)
/*      */     throws HiException
/*      */   {
/*  353 */     if (log.isDebugEnabled()) {
/*  354 */       log.debug("Query[" + strSql + "]");
/*      */     }
/*  356 */     Connection conn = null;
/*  357 */     PreparedStatement stmt = null;
/*  358 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  361 */       conn = getConnection();
/*  362 */       stmt = conn.prepareStatement(strSql);
/*  363 */       ArrayList list = new ArrayList();
/*  364 */       rs = stmt.executeQuery();
/*  365 */       ResultSetMetaData meta = rs.getMetaData();
/*  366 */       int cols = meta.getColumnCount();
/*  367 */       while (rs.next()) {
/*  368 */         values = new HashMap();
/*  369 */         for (int i = 0; i < cols; ++i) {
/*  370 */           String strColName = meta.getColumnName(i + 1);
/*  371 */           Object value = rs.getObject(i + 1);
/*  372 */           if (value == null) {
/*  373 */             value = "";
/*      */           }
/*  375 */           if (value instanceof Clob) {
/*  376 */             Clob tmp = (Clob)value;
/*  377 */             String value1 = tmp.getSubString(1L, (int)tmp.length());
/*  378 */             values.put(strColName.toUpperCase(), value1);
/*      */           } else {
/*  380 */             String value1 = rs.getString(i + 1);
/*  381 */             values.put(strColName.toUpperCase(), StringUtils.trim(value1));
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  388 */         list.add(values);
/*      */       }
/*      */ 
/*  391 */       HashMap values = Collections.unmodifiableList(list);
/*      */ 
/*  399 */       return values;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  393 */       if (e instanceof HiException);
/*  396 */       throw new HiSQLException("215016", e, strSql);
/*      */     }
/*      */     finally {
/*  399 */       close(stmt, rs);
/*      */     }
/*      */   }
/*      */ 
/*      */   public List execQuery(String strSql, int limits)
/*      */     throws HiException
/*      */   {
/*  416 */     return HiDBCache.execQuery(strSql, limits, this);
/*      */   }
/*      */ 
/*      */   protected List internal_execQuery(String strSql, int limits)
/*      */     throws HiException
/*      */   {
/*  434 */     Connection conn = null;
/*  435 */     PreparedStatement stmt = null;
/*  436 */     ResultSet rs = null;
/*  437 */     int count = 0;
/*      */     try {
/*  439 */       conn = getConnection();
/*  440 */       stmt = conn.prepareStatement(strSql);
/*  441 */       ArrayList list = new ArrayList();
/*  442 */       rs = stmt.executeQuery();
/*  443 */       ResultSetMetaData meta = rs.getMetaData();
/*  444 */       int cols = meta.getColumnCount();
/*  445 */       while ((rs.next()) && (((limits == -1) || (count < limits)))) {
/*  446 */         ++count;
/*  447 */         values = new HashMap();
/*  448 */         for (int i = 0; i < cols; ++i) {
/*  449 */           String strColName = meta.getColumnName(i + 1);
/*  450 */           Object value = rs.getObject(i + 1);
/*  451 */           if (value == null) {
/*  452 */             value = "";
/*      */           }
/*  454 */           values.put(strColName.toUpperCase(), String.valueOf(value).trim());
/*      */         }
/*      */ 
/*  460 */         list.add(values);
/*      */       }
/*  462 */       if (log.isDebugEnabled()) {
/*  463 */         log.debug("execQuery is end......." + strSql + "[Size=" + list.size() + "]");
/*      */       }
/*      */ 
/*  467 */       HashMap values = Collections.unmodifiableList(list);
/*      */ 
/*  475 */       return values;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  469 */       if (e instanceof HiException);
/*  472 */       throw new HiSQLException("215016", e, strSql);
/*      */     }
/*      */     finally {
/*  475 */       close(stmt, rs);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int execUpdateBind(String strSql, String[] args)
/*      */     throws HiException
/*      */   {
/*  490 */     if (log.isDebugEnabled()) {
/*  491 */       log.debug("Update[" + strSql + "]");
/*      */     }
/*      */ 
/*  494 */     Connection conn = null;
/*  495 */     PreparedStatement stmt = null;
/*      */     try {
/*  497 */       conn = getConnection();
/*  498 */       stmt = conn.prepareStatement(strSql);
/*  499 */       for (int i = 0; (args != null) && (i < args.length); ++i) {
/*  500 */         stmt.setObject(i + 1, args[i]);
/*      */       }
/*  502 */       int nRow = stmt.executeUpdate();
/*      */ 
/*  505 */       int i = nRow;
/*      */ 
/*  520 */       return i;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       int j;
/*  508 */       SQLException ex = e;
/*  509 */       String code = HiICSProperty.getProperty("sql.duppk");
/*  510 */       log.error("SQLState: [" + code + "][" + ex.getSQLState() + "]");
/*  511 */       if (StringUtils.equals(ex.getSQLState(), code))
/*      */       {
/*  513 */         log.error(strSql, e);
/*  514 */         j = 0;
/*      */ 
/*  520 */         close(stmt);
/*      */       }
/*  516 */       throw new HiSQLException("215021", e, strSql);
/*      */     }
/*      */     finally
/*      */     {
/*  520 */       close(stmt);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int execUpdateBind(String strSql, List args)
/*      */     throws HiException
/*      */   {
/*  535 */     if (log.isDebugEnabled()) {
/*  536 */       log.debug("Update[" + strSql + "]");
/*      */     }
/*      */ 
/*  539 */     Connection conn = null;
/*  540 */     PreparedStatement stmt = null;
/*      */     try {
/*  542 */       conn = getConnection();
/*  543 */       stmt = conn.prepareStatement(strSql);
/*  544 */       for (int i = 0; (args != null) && (i < args.size()); ++i) {
/*  545 */         stmt.setObject(i + 1, args.get(i));
/*      */       }
/*  547 */       int nRow = stmt.executeUpdate();
/*      */ 
/*  550 */       int i = nRow;
/*      */ 
/*  565 */       return i;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       int j;
/*  553 */       SQLException ex = e;
/*  554 */       String code = HiICSProperty.getProperty("sql.duppk");
/*  555 */       log.error("SQLState: [" + code + "][" + ex.getSQLState() + "]");
/*  556 */       if (StringUtils.equals(ex.getSQLState(), code))
/*      */       {
/*  558 */         log.error(strSql, e);
/*  559 */         j = 0;
/*      */ 
/*  565 */         close(stmt);
/*      */       }
/*  561 */       throw new HiSQLException("215021", e, strSql);
/*      */     }
/*      */     finally
/*      */     {
/*  565 */       close(stmt);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int execUpdate(String strSql, String arg1)
/*      */     throws HiException
/*      */   {
/*  581 */     return execUpdate(strSql, new String[] { arg1 });
/*      */   }
/*      */ 
/*      */   public int execUpdate(String strSql, String arg1, String arg2)
/*      */     throws HiException
/*      */   {
/*  598 */     return execUpdate(strSql, new String[] { arg1, arg2 });
/*      */   }
/*      */ 
/*      */   public int execUpdate(String strSql, String arg1, String arg2, String arg3)
/*      */     throws HiException
/*      */   {
/*  615 */     return execUpdate(strSql, new String[] { arg1, arg2, arg3 });
/*      */   }
/*      */ 
/*      */   public int execUpdate(String strSql, String arg1, String arg2, String arg3, String arg4)
/*      */     throws HiException
/*      */   {
/*  634 */     return execUpdate(strSql, new String[] { arg1, arg2, arg3, arg4 });
/*      */   }
/*      */ 
/*      */   public int execUpdate(String strSql, String arg1, String arg2, String arg3, String arg4, String arg5)
/*      */     throws HiException
/*      */   {
/*  655 */     return execUpdate(strSql, new String[] { arg1, arg2, arg3, arg4, arg5 });
/*      */   }
/*      */ 
/*      */   public int execUpdate(String strSql, String[] args)
/*      */     throws HiException
/*      */   {
/*  669 */     return execUpdate(HiStringUtils.format(strSql, args));
/*      */   }
/*      */ 
/*      */   public int execUpdate(String strSql, List args)
/*      */     throws HiException
/*      */   {
/*  683 */     if (log.isDebugEnabled()) {
/*  684 */       log.debug("Update[" + strSql + "]");
/*      */     }
/*      */ 
/*  687 */     Connection conn = null;
/*  688 */     PreparedStatement stmt = null;
/*      */     try {
/*  690 */       conn = getConnection();
/*  691 */       stmt = conn.prepareStatement(strSql);
/*  692 */       for (int i = 0; i < args.size(); ++i) {
/*  693 */         o = args.get(i);
/*  694 */         if (o instanceof byte[]) {
/*  695 */           byte[] buf = (byte[])(byte[])args.get(i);
/*  696 */           stmt.setString(i + 1, new String(buf, 0, buf.length));
/*      */         } else {
/*  698 */           stmt.setString(i + 1, args.get(i).toString());
/*      */         }
/*      */       }
/*  701 */       int nRow = stmt.executeUpdate();
/*  702 */       Object o = nRow;
/*      */ 
/*  716 */       return o;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       int i;
/*  705 */       SQLException ex = e;
/*  706 */       String code = HiICSProperty.getProperty("sql.duppk");
/*  707 */       if (StringUtils.equals(ex.getSQLState(), code))
/*      */       {
/*  709 */         log.error(strSql, e);
/*  710 */         i = 0;
/*      */ 
/*  716 */         close(stmt);
/*      */       }
/*  712 */       throw new HiSQLException("215021", e, strSql);
/*      */     }
/*      */     finally
/*      */     {
/*  716 */       close(stmt);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int execUpdate(String strSql)
/*      */     throws HiException
/*      */   {
/*  734 */     return HiDBCache.execUpdate(strSql, this);
/*      */   }
/*      */ 
/*      */   protected int internal_execUpdate(String strSql)
/*      */     throws HiException
/*      */   {
/*  753 */     if (log.isDebugEnabled()) {
/*  754 */       log.debug("Update[" + strSql + "]");
/*      */     }
/*      */ 
/*  757 */     Connection conn = null;
/*  758 */     PreparedStatement stmt = null;
/*      */     try {
/*  760 */       conn = getConnection();
/*  761 */       stmt = conn.prepareStatement(strSql);
/*  762 */       int nRow = stmt.executeUpdate();
/*      */ 
/*  765 */       int i = nRow;
/*      */ 
/*  780 */       return i;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       int j;
/*  768 */       SQLException ex = e;
/*  769 */       String code = HiICSProperty.getProperty("sql.duppk");
/*  770 */       log.error("SQLState: [" + code + "][" + ex.getSQLState() + "]");
/*  771 */       if (StringUtils.equals(ex.getSQLState(), code))
/*      */       {
/*  773 */         log.error(strSql, e);
/*  774 */         j = 0;
/*      */ 
/*  780 */         close(stmt);
/*      */       }
/*  776 */       throw new HiSQLException("215021", e, strSql);
/*      */     }
/*      */     finally
/*      */     {
/*  780 */       close(stmt);
/*      */     }
/*      */   }
/*      */ 
/*      */   public HashMap readRecord(String strSql, String arg1)
/*      */     throws HiException
/*      */   {
/*  796 */     return readRecord(strSql, new String[] { arg1 });
/*      */   }
/*      */ 
/*      */   public HashMap readRecord(String strSql, String arg1, String arg2)
/*      */     throws HiException
/*      */   {
/*  813 */     return readRecord(strSql, new String[] { arg1, arg2 });
/*      */   }
/*      */ 
/*      */   public HashMap readRecord(String strSql, String arg1, String arg2, String arg3)
/*      */     throws HiException
/*      */   {
/*  832 */     return readRecord(strSql, new String[] { arg1, arg2, arg3 });
/*      */   }
/*      */ 
/*      */   public HashMap readRecord(String strSql, String arg1, String arg2, String arg3, String arg4)
/*      */     throws HiException
/*      */   {
/*  853 */     return readRecord(strSql, new String[] { arg1, arg2, arg3, arg4 });
/*      */   }
/*      */ 
/*      */   public HashMap readRecord(String strSql, String arg1, String arg2, String arg3, String arg4, String arg5)
/*      */     throws HiException
/*      */   {
/*  876 */     return readRecord(strSql, new String[] { arg1, arg2, arg3, arg4, arg5 });
/*      */   }
/*      */ 
/*      */   public HashMap readRecord(String strSql, String[] args)
/*      */     throws HiException
/*      */   {
/*  890 */     return readRecord(HiStringUtils.format(strSql, args));
/*      */   }
/*      */ 
/*      */   public HashMap readRecord(String strSql, List args)
/*      */     throws HiException
/*      */   {
/*  904 */     return readRecord(HiStringUtils.format(strSql, args));
/*      */   }
/*      */ 
/*      */   public HashMap readRecord(String strSql)
/*      */     throws HiException
/*      */   {
/*  917 */     return HiDBCache.readRecord(strSql, this);
/*      */   }
/*      */ 
/*      */   protected HashMap internal_readRecord(String strSql)
/*      */     throws HiException
/*      */   {
/*  926 */     Connection conn = null;
/*  927 */     PreparedStatement stmt = null;
/*  928 */     ResultSet rs = null;
/*      */     try {
/*  930 */       conn = getConnection();
/*  931 */       stmt = conn.prepareStatement(strSql);
/*  932 */       HashMap values = new HashMap();
/*  933 */       rs = stmt.executeQuery();
/*  934 */       ResultSetMetaData meta = rs.getMetaData();
/*  935 */       int cols = meta.getColumnCount();
/*  936 */       int index = 0;
/*  937 */       while (rs.next()) {
/*  938 */         for (i = 0; i < cols; ++i) {
/*  939 */           String strColName = meta.getColumnName(i + 1);
/*  940 */           Object value = rs.getObject(i + 1);
/*  941 */           values.put(strColName.toUpperCase(), String.valueOf(value).trim());
/*      */         }
/*      */ 
/*  947 */         ++index;
/*      */       }
/*  949 */       if (index > 1) {
/*  950 */         throw new HiException("215025", "检索数据超出范围");
/*      */       }
/*      */ 
/*  955 */       int i = values;
/*      */ 
/*  964 */       return i;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  958 */       if (e instanceof HiException);
/*  961 */       throw new HiSQLException("215025", e, strSql);
/*      */     }
/*      */     finally {
/*  964 */       close(stmt, rs);
/*      */     }
/*      */   }
/*      */ 
/*      */   public HiResultSet execQuerySQL(String strSql, String arg1)
/*      */     throws HiException
/*      */   {
/*  980 */     return execQuerySQL(strSql, new String[] { arg1 });
/*      */   }
/*      */ 
/*      */   public HiResultSet execQuerySQL(String strSql, String arg1, String arg2)
/*      */     throws HiException
/*      */   {
/*  997 */     return execQuerySQL(strSql, new String[] { arg1, arg2 });
/*      */   }
/*      */ 
/*      */   public HiResultSet execQuerySQL(String strSql, String arg1, String arg2, String arg3)
/*      */     throws HiException
/*      */   {
/* 1016 */     return execQuerySQL(strSql, new String[] { arg1, arg2, arg3 });
/*      */   }
/*      */ 
/*      */   public HiResultSet execQuerySQL(String strSql, String arg1, String arg2, String arg3, String arg4)
/*      */     throws HiException
/*      */   {
/* 1037 */     return execQuerySQL(strSql, new String[] { arg1, arg2, arg3, arg4 });
/*      */   }
/*      */ 
/*      */   public HiResultSet execQuerySQL(String strSql, String arg1, String arg2, String arg3, String arg4, String arg5)
/*      */     throws HiException
/*      */   {
/* 1060 */     return execQuerySQL(strSql, new String[] { arg1, arg2, arg3, arg4, arg5 });
/*      */   }
/*      */ 
/*      */   public HiResultSet execQuerySQL(String strSql, List args)
/*      */     throws HiException
/*      */   {
/* 1076 */     return execQuerySQL(HiStringUtils.format(strSql, args));
/*      */   }
/*      */ 
/*      */   public HiResultSet execQuerySQL(String strSql, String[] args)
/*      */     throws HiException
/*      */   {
/* 1091 */     return execQuerySQL(HiStringUtils.format(strSql, args));
/*      */   }
/*      */ 
/*      */   public HiResultSet execQuerySQL(String strSql)
/*      */     throws HiException
/*      */   {
/* 1104 */     return HiDBCache.execQuerySQL(strSql, this);
/*      */   }
/*      */ 
/*      */   protected HiResultSet internal_execQuerySQL(String strSql)
/*      */     throws HiException
/*      */   {
/* 1121 */     if (log.isDebugEnabled()) {
/* 1122 */       log.debug("Query[" + strSql + "]");
/*      */     }
/* 1124 */     Connection conn = null;
/* 1125 */     PreparedStatement stmt = null;
/* 1126 */     ResultSet rs = null;
/* 1127 */     HiResultSet hrs = null;
/*      */     try {
/* 1129 */       conn = getConnection();
/* 1130 */       stmt = conn.prepareStatement(strSql);
/* 1131 */       rs = stmt.executeQuery();
/* 1132 */       hrs = new HiResultSet(rs);
/*      */     } catch (SQLException e) {
/*      */     }
/*      */     finally {
/* 1136 */       close(stmt, rs);
/*      */     }
/* 1138 */     return hrs;
/*      */   }
/*      */ 
/*      */   public abstract void pushConnection();
/*      */ 
/*      */   public abstract Connection popConnection();
/*      */ 
/*      */   public void setDsName(String dsName)
/*      */   {
/* 1177 */     this._dsName = dsName;
/*      */   }
/*      */ 
/*      */   public abstract Connection getConnection()
/*      */     throws HiException;
/*      */ 
/*      */   public HashMap getTableMetaData(String strTableName, Connection conn)
/*      */     throws HiException
/*      */   {
/* 1237 */     Statement stmt = null;
/* 1238 */     ResultSet rs = null;
/*      */     try {
/* 1240 */       strTableName = strTableName.toUpperCase();
/*      */ 
/* 1242 */       stmt = conn.createStatement();
/* 1243 */       rs = stmt.executeQuery("SELECT * FROM " + strTableName + " WHERE 1=0");
/*      */ 
/* 1245 */       ResultSetMetaData me = rs.getMetaData();
/* 1246 */       int nColumns = me.getColumnCount();
/* 1247 */       HashMap colInfos = new HashMap();
/* 1248 */       for (int i = 0; i < nColumns; ++i) {
/* 1249 */         String strColumnName = me.getColumnName(i + 1);
/* 1250 */         colInfos.put(strColumnName, me.getColumnTypeName(i + 1));
/*      */       }
/*      */ 
/* 1253 */       i = colInfos;
/*      */ 
/* 1264 */       return i;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 1259 */         if (rs != null)
/* 1260 */           rs.close();
/* 1261 */         if (stmt != null)
/* 1262 */           stmt.close();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public abstract void rollback()
/*      */     throws HiException;
/*      */ 
/*      */   public abstract void close();
/*      */ 
/*      */   public abstract void closeAll();
/*      */ 
/*      */   public void close(Statement stmt)
/*      */   {
/*      */     try
/*      */     {
/* 1316 */       if (stmt != null)
/* 1317 */         stmt.close();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public void close(Statement stmt, ResultSet rs)
/*      */   {
/*      */     try
/*      */     {
/* 1330 */       if (rs != null)
/* 1331 */         rs.close();
/* 1332 */       if (stmt != null)
/* 1333 */         stmt.close();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public abstract void commit()
/*      */     throws HiException;
/*      */ 
/*      */   public Map call(String name, Object[] inArgs)
/*      */     throws HiException
/*      */   {
/* 1365 */     CallableStatement stmt = null;
/* 1366 */     ResultSet rs = null;
/* 1367 */     LinkedHashMap outValue = null;
/* 1368 */     name = name.toUpperCase();
/*      */     try
/*      */     {
/*      */       HiProcedureParam param;
/* 1370 */       Connection conn = getConnection();
/*      */ 
/* 1372 */       ArrayList params = getProcParams(name);
/* 1373 */       String cmd = buildCallStatment(name, params.size());
/*      */ 
/* 1375 */       if (log.isDebugEnabled()) {
/* 1376 */         log.debug("CallProc:[" + cmd + "]");
/*      */       }
/* 1378 */       stmt = conn.prepareCall(cmd);
/*      */ 
/* 1380 */       int inNum = (inArgs != null) ? inArgs.length : 0;
/* 1381 */       for (int i = 0; i < inNum; ++i) {
/* 1382 */         if (log.isDebugEnabled()) {
/* 1383 */           log.debug("arg[" + i + "]:[" + inArgs[i] + "]");
/*      */         }
/* 1385 */         stmt.setObject(i + 1, inArgs[i]);
/*      */       }
/*      */ 
/* 1388 */       for (i = 0; i < params.size(); ++i) {
/* 1389 */         param = (HiProcedureParam)params.get(i);
/* 1390 */         if (log.isDebugEnabled()) {
/* 1391 */           log.debug("CallProc:[" + param.name + "]:[" + param.type + "]:[" + param.out + "]");
/*      */         }
/*      */ 
/* 1395 */         if (!(param.out)) {
/*      */           continue;
/*      */         }
/*      */ 
/* 1399 */         if (param.isCursor())
/* 1400 */           stmt.registerOutParameter(i + 1, -10);
/*      */         else {
/* 1402 */           stmt.registerOutParameter(i + 1, 12);
/*      */         }
/*      */       }
/*      */ 
/* 1406 */       stmt.execute();
/*      */ 
/* 1408 */       if (params.size() > 0) {
/* 1409 */         outValue = new LinkedHashMap();
/*      */       }
/*      */ 
/* 1412 */       for (i = 0; i < params.size(); ++i) {
/* 1413 */         param = (HiProcedureParam)params.get(i);
/* 1414 */         if (!(param.out)) {
/*      */           continue;
/*      */         }
/* 1417 */         Object o = stmt.getObject(i + 1);
/* 1418 */         if (o == null) {
/* 1419 */           outValue.put(param.name, "");
/*      */         }
/*      */         else {
/* 1422 */           if (o instanceof ResultSet) {
/* 1423 */             log.debug("CURSOR:[" + param.name + "]");
/* 1424 */             o = resultSetToHashMap((ResultSet)o);
/*      */           }
/* 1426 */           log.debug("[" + param.name + "]:[" + o + "]");
/* 1427 */           outValue.put(param.name, o); }
/*      */       }
/* 1429 */       i = Collections.unmodifiableMap(outValue);
/*      */ 
/* 1433 */       return i; } catch (Exception e) { } finally { close(stmt, rs);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Map call(String name, ArrayList inArgs)
/*      */     throws HiException
/*      */   {
/* 1447 */     return call(name, (inArgs != null) ? inArgs.toArray() : null);
/*      */   }
/*      */ 
/*      */   private ArrayList resultSetToHashMap(ResultSet rs) throws SQLException {
/* 1451 */     ArrayList list = new ArrayList();
/* 1452 */     while (rs.next()) {
/* 1453 */       log.debug("resultSetToHashMap01");
/* 1454 */       ResultSetMetaData rsmt = rs.getMetaData();
/* 1455 */       HashMap map = new HashMap();
/* 1456 */       for (int j = 0; j < rsmt.getColumnCount(); ++j) {
/* 1457 */         String name1 = rsmt.getColumnName(j + 1);
/* 1458 */         Object o1 = rs.getObject(name1);
/* 1459 */         log.debug("resultSetToHashMap:[" + name1.toUpperCase() + "]:[" + o1 + "]");
/*      */ 
/* 1461 */         if (o1 == null)
/* 1462 */           map.put(name1.toUpperCase(), "");
/*      */         else {
/* 1464 */           map.put(name1.toUpperCase(), o1);
/*      */         }
/*      */       }
/* 1467 */       list.add(map);
/*      */     }
/* 1469 */     return list;
/*      */   }
/*      */ 
/*      */   public ArrayList getProcParams(String name)
/*      */     throws SQLException, HiException
/*      */   {
/* 1482 */     ResultSet rs = null;
/*      */     try {
/* 1484 */       DatabaseMetaData dbmd = getConnection().getMetaData();
/* 1485 */       rs = dbmd.getProcedureColumns(null, null, name.toUpperCase(), null);
/* 1486 */       ArrayList params = new ArrayList();
/* 1487 */       while (rs.next()) {
/* 1488 */         param = new HiProcedureParam(rs.getString("COLUMN_NAME"), rs.getInt("COLUMN_TYPE"), rs.getString("TYPE_NAME"));
/*      */ 
/* 1491 */         params.add(new HiProcedureParam(rs.getString("COLUMN_NAME"), rs.getInt("COLUMN_TYPE"), rs.getString("TYPE_NAME")));
/*      */ 
/* 1493 */         if (log.isDebugEnabled()) {
/* 1494 */           log.debug("store procedure column:[" + param.name + "][" + param.type + "][" + param.out + "]");
/*      */         }
/*      */       }
/*      */ 
/* 1498 */       HiProcedureParam param = params;
/*      */ 
/* 1500 */       return param; } finally { rs.close();
/*      */     }
/*      */   }
/*      */ 
/*      */   public static boolean isOutParam(String type) {
/* 1505 */     return ((type.equals("4")) || (type.equals("3")));
/*      */   }
/*      */ 
/*      */   public static boolean isOutParam(int type) {
/* 1509 */     return ((type == 4) || (type == 3));
/*      */   }
/*      */ 
/*      */   private String buildCallStatment(String name, int paramNum) {
/* 1513 */     StringBuffer buf = new StringBuffer();
/* 1514 */     buf.append("{ call ");
/* 1515 */     buf.append(name);
/* 1516 */     buf.append("(");
/* 1517 */     for (int i = 0; i < paramNum; ++i) {
/* 1518 */       buf.append("?");
/* 1519 */       if (i < paramNum - 1) {
/* 1520 */         buf.append(",");
/*      */       }
/*      */     }
/* 1523 */     buf.append(")}");
/* 1524 */     return buf.toString();
/*      */   }
/*      */ }