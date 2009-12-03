/*     */ package com.hisun.atc.rpt.xml;
/*     */ 
/*     */ import com.hisun.atc.rpt.HiRptContext;
/*     */ import com.hisun.atc.rpt.HiRptExp;
/*     */ import com.hisun.atc.rpt.data.Appender;
/*     */ import com.hisun.atc.rpt.data.Appenders;
/*     */ import com.hisun.atc.rpt.data.RecordWriter;
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.exception.HiSQLException;
/*     */ import com.hisun.hiexpression.HiExpFactory;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.util.HiStringUtils;
/*     */ import com.hisun.xml.Located;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class HiSqlNode extends Located
/*     */ {
/*     */   String sql;
/*     */   String fieldlist;
/*     */   public Appender prefixAppender;
/*     */ 
/*     */   public HiSqlNode()
/*     */   {
/*  36 */     this.prefixAppender = Appenders.zero(); }
/*     */ 
/*     */   public int process(HiRptContext ctx, RecordWriter datafile) {
/*  39 */     Map vars = ctx.vars;
/*  40 */     HiDataBaseUtil dbutil = ctx.getDBUtil();
/*  41 */     String[] params = null;
/*  42 */     if ((this.fieldlist != null) && (this.fieldlist.length() != 0))
/*  43 */       params = parseFieldList(this.fieldlist, vars, ctx.logger);
/*  44 */     return append(ctx, datafile, params, dbutil);
/*     */   }
/*     */ 
/*     */   private String[] parseFieldList(String fields, Map vars, Logger log)
/*     */   {
/*  49 */     String[] list = fields.split("\\|");
/*  50 */     for (int i = 0; i < list.length; ++i) {
/*     */       try {
/*  52 */         String prelex = HiExpFactory.preLex(list[i]);
/*  53 */         list[i] = new HiRptExp(prelex).getValue(vars);
/*     */       } catch (Exception e) {
/*  55 */         log.error("parse filedlist error:" + fields, e);
/*  56 */         return null;
/*     */       }
/*     */     }
/*  59 */     return list;
/*     */   }
/*     */ 
/*     */   private int append(HiRptContext ctx, RecordWriter out, String[] params, HiDataBaseUtil dbutil)
/*     */   {
/*  70 */     String strSql = this.sql;
/*  71 */     int n = 0;
/*  72 */     if (params != null) {
/*  73 */       strSql = HiStringUtils.format(this.sql, params);
/*     */     }
/*     */ 
/*  79 */     ctx.info("SqlNode: 执行sql语句:" + strSql);
/*     */ 
/*  81 */     Connection conn = null;
/*  82 */     PreparedStatement stmt = null;
/*  83 */     ResultSet rs = null;
/*     */     try {
/*  85 */       conn = dbutil.getConnection();
/*  86 */       stmt = conn.prepareStatement(strSql);
/*  87 */       rs = stmt.executeQuery();
/*  88 */       ResultSetMetaData meta = rs.getMetaData();
/*  89 */       int cols = meta.getColumnCount();
/*  90 */       while (rs.next()) {
/*  91 */         ++n;
/*     */ 
/*  96 */         dealRecord(out, rs, meta, cols);
/*     */       }
/*     */ 
/* 101 */       ctx.info("SqlNode: 插入了" + n + "条记录");
/*     */     } catch (Exception e) {
/* 103 */       if (!(e instanceof HiException))
/*     */       {
/* 107 */         e = new HiSQLException("215016", e, strSql); }
/* 108 */       ctx.error(e.getMessage(), e);
/* 109 */       ctx.runtimeException(e);
/*     */     } finally {
/* 111 */       dbutil.close(stmt, rs);
/*     */     }
/* 113 */     return n;
/*     */   }
/*     */ 
/*     */   protected void dealRecord(RecordWriter out, ResultSet rs, ResultSetMetaData meta, int cols) throws SQLException
/*     */   {
/* 118 */     this.prefixAppender.append(out);
/* 119 */     for (int i = 0; i < cols; ++i) {
/* 120 */       String strColName = meta.getColumnName(i + 1);
/* 121 */       String value = null;
/* 122 */       if (rs.getObject(i + 1) != null) {
/* 123 */         value = rs.getObject(i + 1).toString();
/*     */       }
/*     */       else
/* 126 */         value = "";
/* 127 */       out.appendRecordValue(strColName.trim(), value.trim());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setSql(String sql) {
/* 132 */     this.sql = sql;
/*     */   }
/*     */ 
/*     */   public void setFieldlist(String fieldlist) {
/* 136 */     this.fieldlist = fieldlist;
/*     */   }
/*     */ }