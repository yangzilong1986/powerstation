/*     */ package com.hisun.atmp;
/*     */ 
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.database.HiResultSet;
/*     */ import com.hisun.exception.HiAppException;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import java.util.ArrayList;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiFit
/*     */ {
/*     */   public static synchronized void loadFit(HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  20 */     ArrayList fitTable = getRootCtxFitTable();
/*  21 */     if (fitTable != null) {
/*  22 */       return;
/*     */     }
/*  24 */     reLoadFit(ctx);
/*     */   }
/*     */ 
/*     */   public static synchronized void reLoadFit(HiMessageContext ctx) throws HiException {
/*  28 */     ArrayList fitTable = new ArrayList();
/*     */ 
/*  30 */     String sqlCmd = "SELECT * FROM ATMFIT ORDER BY CAST(FITLEN AS BIGINT) DESC";
/*  31 */     HiResultSet rs = ctx.getDataBaseUtil().execQuerySQL(sqlCmd);
/*     */     try {
/*  33 */       for (int i = 0; i < rs.size(); ++i) {
/*  34 */         HiFitItem item = new HiFitItem();
/*     */ 
/*  36 */         String value = null;
/*  37 */         value = rs.getValue(i, "FitTrk");
/*  38 */         item.fitTrk = NumberUtils.toInt(value.trim());
/*  39 */         if ((item.fitTrk != 2) && (item.fitTrk != 3)) {
/*  40 */           throw new HiAppException(-1, "220316", "FitTrk");
/*     */         }
/*     */ 
/*  45 */         value = rs.getValue(i, "FitOfs");
/*  46 */         item.fitOfs = NumberUtils.toInt(value.trim());
/*  47 */         if (item.fitOfs < 0) {
/*  48 */           throw new HiAppException(-1, "220316", "FitOfs");
/*     */         }
/*     */ 
/*  53 */         item.fitCtt = rs.getValue(i, "FitCtt").trim();
/*     */ 
/*  57 */         value = rs.getValue(i, "FitLen");
/*  58 */         item.fitLen = NumberUtils.toInt(value.trim());
/*     */ 
/*  60 */         if (item.fitLen <= 0) {
/*  61 */           throw new HiAppException(-1, "220316", "FitLen");
/*     */         }
/*     */ 
/*  67 */         value = rs.getValue(i, "CrdTrk");
/*  68 */         item.crdTrk = NumberUtils.toInt(value.trim());
/*     */ 
/*  70 */         if ((item.crdTrk != 2) && (item.crdTrk != 3)) {
/*  71 */           throw new HiAppException(-1, "220316", "CrdTrk");
/*     */         }
/*     */ 
/*  77 */         value = rs.getValue(i, "CrdOfs");
/*  78 */         item.crdOfs = NumberUtils.toInt(value.trim());
/*     */ 
/*  80 */         if (item.crdOfs < 0) {
/*  81 */           throw new HiAppException(-1, "220316", "CrdOfs");
/*     */         }
/*     */ 
/*  86 */         value = rs.getValue(i, "CrdLen");
/*  87 */         item.crdLen = NumberUtils.toInt(value.trim());
/*     */ 
/*  90 */         if (item.crdLen < 0) {
/*  91 */           throw new HiAppException(-1, "220316", "CrdLen");
/*     */         }
/*     */ 
/*  95 */         item.crdFlg = rs.getValue(i, "CrdFlg").trim();
/*     */ 
/*  99 */         value = rs.getValue(i, "CdCdOf");
/* 100 */         item.cdCdOf = NumberUtils.toInt(value.trim());
/*     */ 
/* 102 */         if (item.cdCdOf < 0) {
/* 103 */           throw new HiAppException(-1, "220316", "CdCdOf");
/*     */         }
/*     */ 
/* 107 */         item.crdNam = rs.getValue(i, "CrdNam");
/*     */ 
/* 109 */         item.bnkTyp = rs.getValue(i, "BnkTyp").trim();
/*     */ 
/* 112 */         value = rs.getValue(i, "VaDtOf");
/* 113 */         item.vaDtOf = NumberUtils.toInt(value.trim());
/*     */ 
/* 120 */         fitTable.add(item.toMap());
/*     */       }
/*     */     } catch (Exception e) {
/* 123 */       fitTable.clear();
/* 124 */       throw HiException.makeException(e);
/*     */     }
/* 126 */     setRootCtxFitTable(fitTable);
/*     */   }
/*     */ 
/*     */   public static ArrayList getFitTable() {
/* 130 */     return getRootCtxFitTable();
/*     */   }
/*     */ 
/*     */   private static ArrayList getRootCtxFitTable() {
/* 134 */     HiContext rootCtx = HiContext.getRootContext();
/* 135 */     ArrayList fittable = (ArrayList)rootCtx.getProperty("_FIT_TABLE");
/* 136 */     return fittable;
/*     */   }
/*     */ 
/*     */   private static void setRootCtxFitTable(ArrayList fitTable) {
/* 140 */     HiContext rootCtx = HiContext.getRootContext();
/* 141 */     rootCtx.setProperty("_FIT_TABLE", fitTable);
/*     */   }
/*     */ }