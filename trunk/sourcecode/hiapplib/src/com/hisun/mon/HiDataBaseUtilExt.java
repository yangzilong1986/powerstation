/*     */ package com.hisun.mon;
/*     */ 
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.exception.HiSQLException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import java.io.StringReader;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.util.HashMap;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiDataBaseUtilExt
/*     */ {
/*     */   public static HashMap readRecord(HiMessageContext ctx, String strSql, String[] varArr, String[] varTypeArr)
/*     */     throws HiException
/*     */   {
/*  25 */     Connection conn = null;
/*  26 */     PreparedStatement stmt = null;
/*  27 */     ResultSet rs = null;
/*  28 */     HiETF etfBody = ctx.getCurrentMsg().getETFBody();
/*  29 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*  30 */     int num = contains(strSql, '?');
/*  31 */     if (num > varArr.length) {
/*  32 */       num = varArr.length;
/*     */     }
/*     */ 
/*  35 */     int type = 0;
/*     */     try {
/*  37 */       conn = ctx.getDataBaseUtil().getConnection();
/*  38 */       stmt = conn.prepareStatement(strSql);
/*  39 */       log.debug(Integer.valueOf(num));
/*  40 */       if (num > 0)
/*     */       {
/*  42 */         for (int i = 0; i < num; ++i)
/*     */         {
/*  44 */           if ((varTypeArr == null) || (i >= varTypeArr.length))
/*  45 */             type = 0;
/*     */           else {
/*  47 */             type = NumberUtils.toInt(varTypeArr[i], 0);
/*     */           }
/*  49 */           setVarValue(stmt, varArr[i], i + 1, type);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*  56 */       HashMap values = new HashMap();
/*  57 */       rs = stmt.executeQuery();
/*  58 */       ResultSetMetaData meta = rs.getMetaData();
/*  59 */       int cols = meta.getColumnCount();
/*  60 */       int index = 0;
/*  61 */       while (rs.next()) {
/*  62 */         for (int i = 0; i < cols; ++i) {
/*  63 */           String strColName = meta.getColumnName(i + 1);
/*  64 */           Object value = rs.getObject(i + 1);
/*  65 */           values.put(strColName.toUpperCase(), 
/*  66 */             String.valueOf(value).trim());
/*     */         }
/*     */ 
/*  69 */         ++index;
/*     */       }
/*  71 */       if (index > 1) {
/*  72 */         throw new HiException("215025", 
/*  73 */           "检索数据超出范围");
/*     */       }
/*     */ 
/*  77 */       return values;
/*     */     }
/*     */     catch (Exception e) {
/*  80 */       if (e instanceof HiException);
/*  83 */       throw new HiSQLException("215025", e, 
/*  84 */         strSql);
/*     */     } finally {
/*  86 */       ctx.getDataBaseUtil().close(stmt, rs);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void setVarValue(PreparedStatement stmt, String val, int idx, int type)
/*     */     throws Exception
/*     */   {
/*  93 */     switch (type)
/*     */     {
/*     */     case 0:
/*  95 */       if (val.length() < 2000) {
/*  96 */         stmt.setString(idx, val); return;
/*     */       }
/*  98 */       stmt.setCharacterStream(idx, new StringReader(val), val.length());
/*     */ 
/* 101 */       break;
/*     */     case 1:
/* 103 */       stmt.setInt(idx, Integer.parseInt(val));
/* 104 */       break;
/*     */     default:
/* 106 */       if (val.length() < 2000) {
/* 107 */         stmt.setString(idx, val); return;
/*     */       }
/* 109 */       stmt.setCharacterStream(idx, new StringReader(val), val.length());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static int contains(String text, char search)
/*     */   {
/* 120 */     int num = 0;
/* 121 */     int idx = 0;
/*     */     while (true) {
/* 123 */       idx = StringUtils.indexOf(text, search, idx);
/* 124 */       if (idx == -1) {
/*     */         break;
/*     */       }
/* 127 */       ++num;
/* 128 */       ++idx;
/*     */     }
/* 130 */     return num;
/*     */   }
/*     */ }