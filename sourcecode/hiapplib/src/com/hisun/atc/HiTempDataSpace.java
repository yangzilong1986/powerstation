/*     */ package com.hisun.atc;
/*     */ 
/*     */ import com.hisun.atc.common.HiArgUtils;
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.database.HiResultSet;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiTempDataSpace
/*     */ {
/*  19 */   private static HiStringManager sm = HiStringManager.getManager();
/*     */ 
/*  23 */   private static ConcurrentHashMap memoryTD = new ConcurrentHashMap();
/*     */ 
/*     */   public int DeleteTDS(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  41 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/*  42 */     String recKey = HiArgUtils.getStringNotNull(argsMap, "RecKey");
/*  43 */     String txnCode = argsMap.get("TxnCod");
/*  44 */     if (StringUtils.isEmpty(txnCode)) {
/*  45 */       txnCode = HiArgUtils.getStringNotNull(etf, "TXN_CD");
/*     */     }
/*  47 */     String type = argsMap.get("type");
/*  48 */     String brNo = etf.getChildValue("BR_NO");
/*  49 */     if (isMemoryDS(type)) {
/*  50 */       for (int k = 1; ; ++k) {
/*  51 */         String tmpReckey = getMemRecKey(brNo, txnCode, recKey, k);
/*  52 */         if (!(memoryTD.containsKey(tmpReckey))) {
/*  53 */           if (k != 1) break;
/*  54 */           return 2;
/*     */         }
/*     */ 
/*  58 */         memoryTD.remove(tmpReckey);
/*     */       }
/*     */     } else {
/*  61 */       String sqlCmd = "DELETE FROM PUBTDSTBL WHERE BR_NO = '%s' AND TXN_CD = '%s' AND REC_KEY = '%s'";
/*  62 */       int ret = ctx.getDataBaseUtil().execUpdate(sqlCmd, brNo, txnCode, recKey);
/*     */ 
/*  64 */       if (ret == 0)
/*  65 */         return 2;
/*     */     }
/*  67 */     return 0;
/*     */   }
/*     */ 
/*     */   public int UpdateTDS(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  85 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/*  86 */     int idx = 0;
/*  87 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*  88 */     String recKey = HiArgUtils.getStringNotNull(argsMap, "RecKey");
/*     */ 
/*  90 */     String type = null;
/*  91 */     if (argsMap.contains("type")) {
/*  92 */       type = argsMap.get("type");
/*     */     }
/*     */ 
/*  95 */     ++idx;
/*  96 */     String value = null;
/*  97 */     String[] fields = StringUtils.split(recKey, '|');
/*  98 */     for (int i = 0; i < fields.length; ++i) {
/*  99 */       value = (String)ctx.getSpecExpre(etf, fields[i]);
/* 100 */       if (fields[i].startsWith("$"))
/* 101 */         value = (String)ctx.getSpecExpre(etf, fields[i]);
/*     */       else {
/* 103 */         value = fields[i];
/*     */       }
/* 105 */       if (i == 0)
/* 106 */         recKey = value;
/*     */       else {
/* 108 */         recKey = recKey + value;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 113 */     String txnCode = argsMap.get("TxnCod");
/* 114 */     if (StringUtils.isEmpty(txnCode))
/* 115 */       txnCode = HiArgUtils.getStringNotNull(etf, "TXN_CD");
/*     */     else {
/* 117 */       ++idx;
/*     */     }
/*     */ 
/* 120 */     String brNo = etf.getChildValue("BR_NO");
/*     */ 
/* 123 */     HiETF root = null;
/* 124 */     for (int k = 1; idx < argsMap.size(); ++k) {
/* 125 */       fields = StringUtils.split(argsMap.getValue(idx), "|");
/* 126 */       root = HiETFFactory.createETF("REC_" + idx, "");
/* 127 */       for (int j = 0; j < fields.length; ++j) {
/* 128 */         value = etf.getChildValue(fields[j]);
/* 129 */         root.setChildValue(fields[j], value);
/*     */       }
/* 131 */       String recIdx = StringUtils.leftPad(String.valueOf(idx), 2, '0');
/* 132 */       String recValue = root.toString();
/* 133 */       if (isMemoryDS(type)) {
/* 134 */         String tmpReckey = getMemRecKey(brNo, txnCode, recKey, k);
/* 135 */         if (!(memoryTD.containsKey(tmpReckey))) {
/* 136 */           return 2;
/*     */         }
/* 138 */         memoryTD.put(tmpReckey, recValue);
/*     */       } else {
/* 140 */         String sqlCmd = "UPDATE PUBTDSTBL SET REC='%s' WHERE BR_NO = '%s' AND TXN_CD = '%s' AND REC_KEY = '%s' AND REC_IDX = '%s'";
/* 141 */         int ret = ctx.getDataBaseUtil().execUpdate(sqlCmd, recValue, brNo, txnCode, recKey, recIdx);
/*     */ 
/* 143 */         if (ret == 0)
/* 144 */           return 2;
/*     */       }
/* 124 */       ++idx;
/*     */     }
/*     */ 
/* 148 */     return 0;
/*     */   }
/*     */ 
/*     */   public int SaveToTDS(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 167 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/* 168 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 169 */     int idx = 0;
/* 170 */     String recKey = HiArgUtils.getStringNotNull(argsMap, "RecKey");
/* 171 */     ++idx;
/* 172 */     String type = null;
/* 173 */     if (argsMap.contains("type")) {
/* 174 */       type = argsMap.get("type");
/* 175 */       ++idx;
/*     */     }
/* 177 */     String value = null;
/* 178 */     String[] fields = StringUtils.split(recKey, '|');
/* 179 */     for (int i = 0; i < fields.length; ++i) {
/* 180 */       if (fields[i].startsWith("$"))
/* 181 */         value = (String)ctx.getSpecExpre(etf, fields[i]);
/*     */       else {
/* 183 */         value = fields[i];
/*     */       }
/* 185 */       if (i == 0)
/* 186 */         recKey = value;
/*     */       else {
/* 188 */         recKey = recKey + value;
/*     */       }
/*     */     }
/*     */ 
/* 192 */     String txnCode = argsMap.get("TxnCod");
/* 193 */     if (StringUtils.isEmpty(txnCode))
/* 194 */       txnCode = HiArgUtils.getStringNotNull(etf, "TXN_CD");
/*     */     else {
/* 196 */       ++idx;
/*     */     }
/*     */ 
/* 199 */     String brNo = etf.getChildValue("BR_NO");
/*     */ 
/* 202 */     HiETF root = null;
/* 203 */     for (int k = 1; idx < argsMap.size(); ++idx) {
/* 204 */       fields = StringUtils.split(argsMap.getValue(idx), "|");
/* 205 */       root = HiETFFactory.createETF("REC_" + idx, "");
/* 206 */       for (int j = 0; j < fields.length; ++j) {
/* 207 */         value = etf.getChildValue(fields[j]);
/* 208 */         root.setChildValue(fields[j], value);
/*     */       }
/* 210 */       String recIdx = StringUtils.leftPad(String.valueOf(idx), 2, '0');
/* 211 */       String recValue = root.toString();
/* 212 */       if (isMemoryDS(type)) {
/* 213 */         String tmpReckey = getMemRecKey(brNo, txnCode, recKey, k);
/* 214 */         if (memoryTD.containsKey(tmpReckey)) {
/* 215 */           return 1;
/*     */         }
/* 217 */         memoryTD.put(tmpReckey, recValue);
/*     */       } else {
/* 219 */         String sqlCmd = "INSERT INTO PUBTDSTBL VALUES('%s', '%s', '%s', '%s', '%s')";
/* 220 */         int ret = ctx.getDataBaseUtil().execUpdate(sqlCmd, brNo, txnCode, recKey, recIdx, recValue);
/*     */ 
/* 222 */         if (ret == 0)
/* 223 */           return 1;
/*     */       }
/* 203 */       ++k;
/*     */     }
/*     */ 
/* 227 */     return 0;
/*     */   }
/*     */ 
/*     */   public int LoadFromTDSToGroup(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 245 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/* 246 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 247 */     String recKey = HiArgUtils.getStringNotNull(argsMap, "RecKey");
/* 248 */     String value = null;
/* 249 */     String[] fields = StringUtils.split(recKey, '|');
/* 250 */     for (int i = 0; i < fields.length; ++i) {
/* 251 */       if (fields[i].startsWith("$"))
/* 252 */         value = (String)ctx.getSpecExpre(etf, fields[i]);
/*     */       else {
/* 254 */         value = fields[i];
/*     */       }
/* 256 */       if (i == 0)
/* 257 */         recKey = value;
/*     */       else {
/* 259 */         recKey = recKey + value;
/*     */       }
/*     */     }
/*     */ 
/* 263 */     String txnCode = argsMap.get("TxnCod");
/* 264 */     if (StringUtils.isEmpty(txnCode)) {
/* 265 */       txnCode = HiArgUtils.getStringNotNull(etf, "TXN_CD");
/*     */     }
/*     */ 
/* 268 */     String brNo = etf.getChildValue("BR_NO");
/* 269 */     String type = argsMap.get("type");
/* 270 */     HiETF root = null;
/* 271 */     if (isMemoryDS(type)) {
/* 272 */       for (int idx = 1; ; ++idx) {
/* 273 */         String tmpReckey = getMemRecKey(brNo, txnCode, recKey, idx);
/* 274 */         if ((!(memoryTD.containsKey(tmpReckey))) && 
/* 275 */           (idx == 1)) {
/* 276 */           return 2;
/*     */         }
/*     */ 
/* 279 */         root = HiETFFactory.createETF((String)memoryTD.get(tmpReckey));
/* 280 */         etf.combine(root, false);
/*     */       }
/*     */     }
/*     */ 
/* 284 */     String sqlCmd = "SELECT Rec  FROM pubtdstbl WHERE BR_NO='%s' AND TXN_CD='%s' AND REC_KEY='%s'";
/* 285 */     HiResultSet resultSet = ctx.getDataBaseUtil().execQuerySQL(sqlCmd, brNo, txnCode, recKey);
/*     */ 
/* 287 */     if (resultSet.size() == 0) {
/* 288 */       return 2;
/*     */     }
/* 290 */     for (int i = 0; i < resultSet.size(); ++i) {
/* 291 */       root = HiETFFactory.createETF(resultSet.getValue(i, 0));
/* 292 */       etf.combine(root, false);
/*     */     }
/*     */ 
/* 296 */     return 0;
/*     */   }
/*     */ 
/*     */   public int LoadFromTDS(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 312 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/* 313 */     int idx = 0;
/* 314 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 315 */     String recKey = HiArgUtils.getStringNotNull(argsMap, "RecKey");
/* 316 */     ++idx;
/* 317 */     String value = null;
/* 318 */     String[] fields = StringUtils.split(recKey, '|');
/* 319 */     for (int i = 0; i < fields.length; ++i) {
/* 320 */       if (fields[i].startsWith("$"))
/* 321 */         value = (String)ctx.getSpecExpre(etf, fields[i]);
/*     */       else {
/* 323 */         value = fields[i];
/*     */       }
/* 325 */       if (i == 0)
/* 326 */         recKey = value;
/*     */       else {
/* 328 */         recKey = recKey + value;
/*     */       }
/*     */     }
/*     */ 
/* 332 */     String txnCode = argsMap.get("TxnCod");
/* 333 */     if (StringUtils.isEmpty(txnCode))
/* 334 */       txnCode = HiArgUtils.getStringNotNull(etf, "TXN_CD");
/*     */     else {
/* 336 */       ++idx;
/*     */     }
/* 338 */     String brNo = etf.getChildValue("BR_NO");
/*     */ 
/* 340 */     HiETF root = null;
/* 341 */     String type = null;
/* 342 */     if (argsMap.contains("type")) {
/* 343 */       type = argsMap.get("type");
/* 344 */       ++idx;
/*     */     }
/* 346 */     if (isMemoryDS(type)) {
/* 347 */       if (log.isDebugEnabled()) {
/* 348 */         log.debug("Memory TDS:" + memoryTD);
/*     */       }
/* 350 */       for (int k = 1; ; ++k) {
/* 351 */         String tmpReckey = getMemRecKey(brNo, txnCode, recKey, k);
/* 352 */         if (!(memoryTD.containsKey(tmpReckey))) {
/* 353 */           if (k != 1) break;
/* 354 */           return 2;
/*     */         }
/*     */ 
/* 358 */         root = HiETFFactory.createETF((String)memoryTD.get(tmpReckey));
/* 359 */         etf.combine(root, false);
/*     */       }
/*     */     } else {
/* 362 */       String sqlCmd = "SELECT Rec  FROM pubtdstbl WHERE BR_NO='%s' AND TXN_CD='%s' AND REC_KEY='%s'";
/* 363 */       HiResultSet resultSet = ctx.getDataBaseUtil().execQuerySQL(sqlCmd, brNo, txnCode, recKey);
/*     */ 
/* 365 */       if (resultSet.size() == 0) {
/* 366 */         return 2;
/*     */       }
/*     */ 
/* 369 */       for (int i = 0; i < resultSet.size(); ++i) {
/* 370 */         root = HiETFFactory.createETF(resultSet.getValue(i, 0));
/* 371 */         etf.combine(root, true);
/*     */       }
/*     */     }
/* 374 */     return 0;
/*     */   }
/*     */ 
/*     */   private String getMemRecKey(String brNo, String txnCd, String recKey, int recIdx)
/*     */   {
/* 379 */     if (brNo == null) {
/* 380 */       return "999999_" + txnCd + "_" + recKey + "_" + recIdx;
/*     */     }
/* 382 */     return brNo + "_" + txnCd + "_" + recKey + "_" + recIdx;
/*     */   }
/*     */ 
/*     */   private boolean isMemoryDS(String type)
/*     */   {
/* 387 */     return "MEM".equals(type);
/*     */   }
/*     */ }