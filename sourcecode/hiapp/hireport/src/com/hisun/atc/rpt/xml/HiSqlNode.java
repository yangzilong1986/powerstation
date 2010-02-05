 package com.hisun.atc.rpt.xml;
 
 import com.hisun.atc.rpt.HiRptContext;
 import com.hisun.atc.rpt.HiRptExp;
 import com.hisun.atc.rpt.data.Appender;
 import com.hisun.atc.rpt.data.Appenders;
 import com.hisun.atc.rpt.data.RecordWriter;
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.exception.HiException;
 import com.hisun.exception.HiSQLException;
 import com.hisun.hiexpression.HiExpFactory;
 import com.hisun.hilog4j.Logger;
 import com.hisun.util.HiStringUtils;
 import com.hisun.xml.Located;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.ResultSetMetaData;
 import java.sql.SQLException;
 import java.util.Map;
 
 public class HiSqlNode extends Located
 {
   String sql;
   String fieldlist;
   public Appender prefixAppender;
 
   public HiSqlNode()
   {
     this.prefixAppender = Appenders.zero(); }
 
   public int process(HiRptContext ctx, RecordWriter datafile) {
     Map vars = ctx.vars;
     HiDataBaseUtil dbutil = ctx.getDBUtil();
     String[] params = null;
     if ((this.fieldlist != null) && (this.fieldlist.length() != 0))
       params = parseFieldList(this.fieldlist, vars, ctx.logger);
     return append(ctx, datafile, params, dbutil);
   }
 
   private String[] parseFieldList(String fields, Map vars, Logger log)
   {
     String[] list = fields.split("\\|");
     for (int i = 0; i < list.length; ++i) {
       try {
         String prelex = HiExpFactory.preLex(list[i]);
         list[i] = new HiRptExp(prelex).getValue(vars);
       } catch (Exception e) {
         log.error("parse filedlist error:" + fields, e);
         return null;
       }
     }
     return list;
   }
 
   private int append(HiRptContext ctx, RecordWriter out, String[] params, HiDataBaseUtil dbutil)
   {
     String strSql = this.sql;
     int n = 0;
     if (params != null) {
       strSql = HiStringUtils.format(this.sql, params);
     }
 
     ctx.info("SqlNode: 执行sql语句:" + strSql);
 
     Connection conn = null;
     PreparedStatement stmt = null;
     ResultSet rs = null;
     try {
       conn = dbutil.getConnection();
       stmt = conn.prepareStatement(strSql);
       rs = stmt.executeQuery();
       ResultSetMetaData meta = rs.getMetaData();
       int cols = meta.getColumnCount();
       while (rs.next()) {
         ++n;
 
         dealRecord(out, rs, meta, cols);
       }
 
       ctx.info("SqlNode: 插入了" + n + "条记录");
     } catch (Exception e) {
       if (!(e instanceof HiException))
       {
         e = new HiSQLException("215016", e, strSql); }
       ctx.error(e.getMessage(), e);
       ctx.runtimeException(e);
     } finally {
       dbutil.close(stmt, rs);
     }
     return n;
   }
 
   protected void dealRecord(RecordWriter out, ResultSet rs, ResultSetMetaData meta, int cols) throws SQLException
   {
     this.prefixAppender.append(out);
     for (int i = 0; i < cols; ++i) {
       String strColName = meta.getColumnName(i + 1);
       String value = null;
       if (rs.getObject(i + 1) != null) {
         value = rs.getObject(i + 1).toString();
       }
       else
         value = "";
       out.appendRecordValue(strColName.trim(), value.trim());
     }
   }
 
   public void setSql(String sql) {
     this.sql = sql;
   }
 
   public void setFieldlist(String fieldlist) {
     this.fieldlist = fieldlist;
   }
 }