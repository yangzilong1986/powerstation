/*     */ package com.hisun.data.cache;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.exception.HiSQLException;
/*     */ import java.sql.Clob;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ 
/*     */ public class HiDBUtil
/*     */ {
/*     */   private HiDBConnection DBConnection;
/*     */   private Connection conn;
/*     */   private String dsName;
/*     */ 
/*     */   public HiDBUtil()
/*     */   {
/*  24 */     this.DBConnection = new HiDBConnection();
/*  25 */     this.conn = null;
/*     */   }
/*     */ 
/*     */   protected List execQuery(String strSql) throws HiException {
/*  29 */     PreparedStatement stmt = null;
/*  30 */     ResultSet rs = null;
/*     */     try
/*     */     {
/*  33 */       if (this.conn == null) {
/*  34 */         this.conn = this.DBConnection.getConnection(this.dsName);
/*     */       }
/*  36 */       stmt = this.conn.prepareStatement(strSql);
/*  37 */       ArrayList list = new ArrayList();
/*  38 */       rs = stmt.executeQuery();
/*  39 */       ResultSetMetaData meta = rs.getMetaData();
/*  40 */       int cols = meta.getColumnCount();
/*  41 */       while (rs.next()) {
/*  42 */         values = new HashMap();
/*  43 */         for (int i = 0; i < cols; ++i) {
/*  44 */           String strColName = meta.getColumnName(i + 1);
/*  45 */           Object value = rs.getObject(i + 1);
/*  46 */           if (value == null) {
/*  47 */             value = "";
/*     */           }
/*  49 */           if (value instanceof Clob) {
/*  50 */             Clob tmp = (Clob)value;
/*  51 */             value = tmp.getSubString(1L, (int)tmp.length());
/*  52 */             values.put(strColName.toUpperCase(), value);
/*     */           } else {
/*  54 */             values.put(strColName.toUpperCase(), String.valueOf(value).trim());
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*  61 */         list.add(values);
/*     */       }
/*     */ 
/*  64 */       HashMap values = Collections.unmodifiableList(list);
/*     */ 
/*  73 */       return values;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  66 */       if (e instanceof HiException);
/*  69 */       throw new HiSQLException("215016", e, strSql);
/*     */     }
/*     */     finally {
/*  72 */       close(stmt, rs);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void close(Statement stmt, ResultSet rs)
/*     */   {
/*     */     try
/*     */     {
/*  84 */       if (rs != null)
/*  85 */         rs.close();
/*  86 */       if (stmt != null)
/*  87 */         stmt.close();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/*     */     try {
/*  97 */       if (this.conn != null) {
/*  98 */         this.conn.commit();
/*  99 */         this.conn.close();
/* 100 */         this.conn = null;
/*     */       }
/*     */     } catch (Exception e) {
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getDsName() {
/* 107 */     return this.dsName;
/*     */   }
/*     */ 
/*     */   public void setDsName(String dsName) {
/* 111 */     this.dsName = dsName;
/*     */   }
/*     */ }