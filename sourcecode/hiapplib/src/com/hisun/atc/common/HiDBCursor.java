/*     */ package com.hisun.atc.common;
/*     */ 
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiStringUtils;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiDBCursor
/*     */ {
/*  26 */   public int ret = 0;
/*     */ 
/*  28 */   private Connection conn = null;
/*     */ 
/*  30 */   private PreparedStatement stmt = null;
/*     */ 
/*  32 */   private ResultSet rs = null;
/*     */ 
/*  34 */   private ResultSetMetaData meta = null;
/*     */ 
/*  36 */   private int colNum = 0;
/*     */ 
/*  38 */   private HiMessageContext tranData = null;
/*     */ 
/*     */   public HiDBCursor(HiMessage mess, String strSql, HiMessageContext tranData) throws HiException
/*     */   {
/*  42 */     Logger log = HiLog.getLogger(mess);
/*  43 */     if (log.isInfoEnabled())
/*  44 */       log.info(strSql);
/*     */     try
/*     */     {
/*  47 */       this.tranData = tranData;
/*  48 */       this.conn = tranData.getDataBaseUtil().getConnection();
/*  49 */       if (log.isInfoEnabled()) {
/*  50 */         log.info("HiDBCursor.strSql:[" + strSql + "]");
/*     */       }
/*  52 */       this.stmt = this.conn.prepareStatement(strSql);
/*  53 */       this.stmt.setFetchSize(1000);
/*  54 */       this.rs = this.stmt.executeQuery();
/*  55 */       if (this.rs != null) {
/*  56 */         this.meta = this.rs.getMetaData();
/*  57 */         this.colNum = this.meta.getColumnCount();
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/*  61 */       tranData.getDataBaseUtil().close(this.stmt);
/*  62 */       throw HiException.makeException("220205", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public HiDBCursor(HiMessage mess, String strSql, List paramList, HiMessageContext tranData)
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/*  74 */       this.tranData = tranData;
/*  75 */       strSql = HiStringUtils.format(strSql, paramList);
/*     */ 
/*  82 */       Logger log = HiLog.getLogger(mess);
/*  83 */       if (log.isInfoEnabled()) {
/*  84 */         log.info("HiDBCursor.strSql:[" + strSql + "]");
/*     */       }
/*  86 */       this.conn = tranData.getDataBaseUtil().getConnection();
/*  87 */       this.stmt = this.conn.prepareStatement(strSql);
/*  88 */       this.stmt.setFetchSize(1000);
/*     */ 
/* 100 */       this.rs = this.stmt.executeQuery();
/* 101 */       if (this.rs != null) {
/* 102 */         this.meta = this.rs.getMetaData();
/* 103 */         this.colNum = this.meta.getColumnCount();
/*     */       }
/*     */     } catch (Exception e) {
/* 106 */       tranData.getDataBaseUtil().close(this.stmt);
/*     */ 
/* 108 */       throw HiException.makeException("220205", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public HiDBCursor(HiMessageContext ctx, String strSql, List paramList) throws HiException {
/*     */     try {
/* 114 */       this.tranData = ctx;
/* 115 */       Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 116 */       if (log.isInfoEnabled()) {
/* 117 */         log.info("HiDBCursor.strSql:[" + strSql + "]");
/*     */       }
/* 119 */       this.conn = this.tranData.getDataBaseUtil().getConnection();
/* 120 */       this.stmt = this.conn.prepareStatement(strSql);
/* 121 */       this.stmt.setFetchSize(1000);
/*     */ 
/* 123 */       for (int i = 0; (paramList != null) && (i < paramList.size()); ++i) {
/* 124 */         String value = (String)paramList.get(i);
/* 125 */         if (log.isInfoEnabled()) {
/* 126 */           log.info("[" + i + "]:" + value);
/*     */         }
/* 128 */         this.stmt.setObject(i + 1, value);
/*     */       }
/*     */ 
/* 131 */       this.rs = this.stmt.executeQuery();
/* 132 */       if (this.rs != null) {
/* 133 */         this.meta = this.rs.getMetaData();
/* 134 */         this.colNum = this.meta.getColumnCount();
/*     */       }
/*     */     } catch (Exception e) {
/* 137 */       ctx.getDataBaseUtil().close(this.stmt);
/* 138 */       throw HiException.makeException("220205", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public HashMap next() throws HiException
/*     */   {
/* 144 */     if (this.rs == null)
/*     */     {
/* 146 */       throw new HiException("220206", "当前ResultSet为null");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 151 */       if (this.rs.next()) {
/* 152 */         HashMap values = new HashMap();
/* 153 */         for (int i = 0; i < this.colNum; ++i) {
/* 154 */           String strColName = this.meta.getColumnName(i + 1);
/* 155 */           String colVal = this.rs.getString(i + 1);
/* 156 */           if (colVal == null) {
/* 157 */             colVal = "";
/*     */           }
/* 159 */           values.put(strColName.toUpperCase(), StringUtils.trim(colVal));
/*     */         }
/*     */ 
/* 163 */         return values;
/*     */       }
/* 165 */       return null;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 169 */       throw new HiException("220206", "cursor:next", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void close() throws HiException
/*     */   {
/*     */     try {
/* 176 */       if (this.rs != null) {
/* 177 */         this.rs.close();
/*     */       }
/* 179 */       if (this.stmt != null)
/* 180 */         this.stmt.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 184 */       throw new HiException("220206", "关闭失败!", e);
/*     */     }
/*     */   }
/*     */ }