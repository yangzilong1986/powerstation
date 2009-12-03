/*     */ package com.hisun.atc;
/*     */ 
/*     */ import com.hisun.atc.common.HiArgUtils;
/*     */ import com.hisun.atc.common.HiAtcLib;
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.engine.invoke.impl.HiAttributesHelper;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ 
/*     */ public class HiTxnJnl
/*     */ {
/*     */   public int InsertJournal(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  44 */     String txnjnl = ctx.getJnlTable();
/*  45 */     if (txnjnl == null) {
/*  46 */       throw new HiException("220052");
/*     */     }
/*     */ 
/*  49 */     return HiAtcLib.insertTable(txnjnl, ctx);
/*     */   }
/*     */ 
/*     */   public int UpdateOldJournalStatus(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  74 */     HiMessage mess = ctx.getCurrentMsg();
/*  75 */     String strType = null;
/*  76 */     String HTxnSt = args.get("HTxnSt");
/*  77 */     String TTxnSt = args.get("TTxnSt");
/*  78 */     String TxnSts = args.get("TxnSts");
/*     */ 
/*  80 */     if ((HTxnSt != null) && (TTxnSt != null) && (TxnSts != null))
/*  81 */       strType = "ALL";
/*  82 */     else if ((HTxnSt != null) && (TxnSts != null))
/*  83 */       strType = "HAF";
/*  84 */     else if ((TTxnSt != null) && (TxnSts != null))
/*  85 */       strType = "TAF";
/*  86 */     else if ((HTxnSt != null) && (TTxnSt != null))
/*  87 */       strType = "HAT";
/*  88 */     else if (HTxnSt != null)
/*  89 */       strType = "HST";
/*  90 */     else if (TTxnSt != null)
/*  91 */       strType = "THD";
/*  92 */     else if (TxnSts != null)
/*  93 */       strType = "FRT";
/*     */     else {
/*  95 */       throw new HiException("220026", "参数定义错误");
/*     */     }
/*     */ 
/*  98 */     HiETF etfRoot = (HiETF)mess.getBody();
/*  99 */     String strOLogNo = HiArgUtils.getStringNotNull(etfRoot, "OLOG_NO");
/*     */ 
/* 101 */     int ret = HiAtcLib.updTxnJnlSts(ctx, strType, strOLogNo, HTxnSt, TTxnSt, TxnSts);
/*     */ 
/* 103 */     if (ret == -1) {
/* 104 */       throw new HiException("220053", "更新原交易流水状态失败");
/*     */     }
/*     */ 
/* 108 */     return ret;
/*     */   }
/*     */ 
/*     */   public int UpdateJournalHost(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 130 */     HiMessage msg = ctx.getCurrentMsg();
/* 131 */     String txnjnltbl = ctx.getJnlTable();
/* 132 */     if (txnjnltbl == null) {
/* 133 */       throw new HiException("220052");
/*     */     }
/*     */ 
/* 136 */     HiETF etfRoot = (HiETF)msg.getBody();
/*     */ 
/* 139 */     StringBuffer sqlcmd = new StringBuffer();
/* 140 */     sqlcmd.append("UPDATE ");
/* 141 */     sqlcmd.append(txnjnltbl);
/* 142 */     sqlcmd.append(" SET ");
/*     */ 
/* 144 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "ACC_DT", HiArgUtils.getStringNull(etfRoot, "ACC_DT"), false);
/*     */ 
/* 146 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "AC_NO", HiArgUtils.getStringNull(etfRoot, "AC_NO"), true);
/*     */ 
/* 148 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "CRD_NO", HiArgUtils.getStringNull(etfRoot, "CRD_NO"), true);
/*     */ 
/* 150 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "ORG_NO", HiArgUtils.getStringNull(etfRoot, "ORG_NO"), true);
/*     */ 
/* 152 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "NOD_NO", HiArgUtils.getStringNull(etfRoot, "NOD_NO"), true);
/*     */ 
/* 154 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "NOD_TRC", HiArgUtils.getStringNull(etfRoot, "NOD_TRC"), true);
/*     */ 
/* 156 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "HLOG_NO", HiArgUtils.getStringNull(etfRoot, "HLOG_NO"), true);
/*     */ 
/* 158 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TCK_NO", HiArgUtils.getStringNull(etfRoot, "TCK_NO"), true);
/*     */ 
/* 160 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TCUS_NM", HiArgUtils.getStringNull(etfRoot, "TCUS_NM"), true);
/*     */ 
/* 162 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "HRSP_CD", HiArgUtils.getStringNull(etfRoot, "HRSP_CD"), true);
/*     */ 
/* 164 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "HTXN_STS", HiArgUtils.getStringNull(etfRoot, "HTXN_STS"), true);
/*     */ 
/* 166 */     for (int i = 0; i < args.size(); ++i) {
/* 167 */       String key = args.getName(i);
/* 168 */       String value = args.getValue(i);
/* 169 */       sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, key, value, true);
/*     */     }
/*     */ 
/* 172 */     String strLogNo = HiArgUtils.getStringNotNull(etfRoot, "LOG_NO");
/*     */ 
/* 175 */     sqlcmd.append(" WHERE LOG_NO='");
/* 176 */     sqlcmd.append(strLogNo);
/* 177 */     sqlcmd.append("'");
/*     */ 
/* 179 */     Logger log = HiLog.getLogger(msg);
/* 180 */     if (log.isInfoEnabled()) {
/* 181 */       log.info(sqlcmd);
/*     */     }
/* 183 */     int ret = ctx.getDataBaseUtil().execUpdate(sqlcmd.toString());
/* 184 */     if (ret == 0) {
/* 185 */       return 2;
/*     */     }
/* 187 */     return 0;
/*     */   }
/*     */ 
/*     */   public int UpdateJournal(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 209 */     HiMessage msg = ctx.getCurrentMsg();
/* 210 */     String txnjnltbl = ctx.getJnlTable();
/* 211 */     if (txnjnltbl == null) {
/* 212 */       throw new HiException("220052");
/*     */     }
/*     */ 
/* 215 */     HiETF etfRoot = (HiETF)msg.getBody();
/* 216 */     StringBuffer sqlcmd = new StringBuffer();
/* 217 */     sqlcmd.append("UPDATE ");
/* 218 */     sqlcmd.append(txnjnltbl);
/* 219 */     sqlcmd.append(" SET ");
/*     */ 
/* 221 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "ACC_DT", HiArgUtils.getStringNull(etfRoot, "ACC_DT"), false);
/*     */ 
/* 223 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "AC_NO", HiArgUtils.getStringNull(etfRoot, "AC_NO"), true);
/*     */ 
/* 225 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "CRD_NO", HiArgUtils.getStringNull(etfRoot, "CRD_NO"), true);
/*     */ 
/* 227 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "ORG_NO", HiArgUtils.getStringNull(etfRoot, "ORG_NO"), true);
/*     */ 
/* 229 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "NOD_NO", HiArgUtils.getStringNull(etfRoot, "NOD_NO"), true);
/*     */ 
/* 231 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "NOD_TRC", HiArgUtils.getStringNull(etfRoot, "NOD_TRC"), true);
/*     */ 
/* 233 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "HLOG_NO", HiArgUtils.getStringNull(etfRoot, "HLOG_NO"), true);
/*     */ 
/* 235 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TCK_NO", HiArgUtils.getStringNull(etfRoot, "TCK_NO"), true);
/*     */ 
/* 237 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "HRSP_CD", HiArgUtils.getStringNull(etfRoot, "HRSP_CD"), true);
/*     */ 
/* 239 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "HTXN_STS", HiArgUtils.getStringNull(etfRoot, "HTXN_STS"), true);
/*     */ 
/* 242 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "FRSP_CD", HiArgUtils.getStringNull(etfRoot, "FRSP_CD"), true);
/*     */ 
/* 244 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TCUS_ID", HiArgUtils.getStringNull(etfRoot, "TCUS_ID"), true);
/*     */ 
/* 246 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TCUS_NM", HiArgUtils.getStringNull(etfRoot, "TCUS_NM"), true);
/*     */ 
/* 248 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TACC_DT", HiArgUtils.getStringNull(etfRoot, "TACC_DT"), true);
/*     */ 
/* 250 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TLOG_NO", HiArgUtils.getStringNull(etfRoot, "TLOG_NO"), true);
/*     */ 
/* 252 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TRSP_CD", HiArgUtils.getStringNull(etfRoot, "TRSP_CD"), true);
/*     */ 
/* 254 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TTXN_STS", HiArgUtils.getStringNull(etfRoot, "TTXN_STS"), true);
/*     */ 
/* 256 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TXN_STS", HiArgUtils.getStringNull(etfRoot, "TXN_STS"), true);
/*     */ 
/* 259 */     for (int i = 0; i < args.size(); ++i) {
/* 260 */       String key = args.getName(i);
/* 261 */       String value = args.getValue(i);
/* 262 */       sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, key, value, true);
/*     */     }
/*     */ 
/* 265 */     String LogNo = HiArgUtils.getStringNotNull(etfRoot, "LOG_NO");
/*     */ 
/* 268 */     sqlcmd.append(" WHERE LOG_NO='");
/* 269 */     sqlcmd.append(LogNo);
/* 270 */     sqlcmd.append("'");
/* 271 */     Logger log = HiLog.getLogger(msg);
/* 272 */     if (log.isInfoEnabled()) {
/* 273 */       log.info(sqlcmd);
/*     */     }
/* 275 */     int ret = ctx.getDataBaseUtil().execUpdate(sqlcmd.toString());
/* 276 */     if (ret == 0) {
/* 277 */       return 2;
/*     */     }
/* 279 */     return 0;
/*     */   }
/*     */ 
/*     */   public int UpdateJournalThird(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 301 */     HiMessage mess = ctx.getCurrentMsg();
/* 302 */     String txnjnltbl = ctx.getJnlTable();
/* 303 */     if (txnjnltbl == null) {
/* 304 */       throw new HiException("220052", "未定义流水表");
/*     */     }
/*     */ 
/* 307 */     HiETF etfRoot = (HiETF)mess.getBody();
/*     */ 
/* 310 */     StringBuffer sqlcmd = new StringBuffer();
/* 311 */     sqlcmd.append("UPDATE ");
/* 312 */     sqlcmd.append(txnjnltbl);
/* 313 */     sqlcmd.append(" SET ");
/*     */ 
/* 315 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TTXN_STS", HiArgUtils.getStringNull(etfRoot, "TTXN_STS"), false);
/*     */ 
/* 317 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TCUS_ID", HiArgUtils.getStringNull(etfRoot, "TCUS_ID"), true);
/*     */ 
/* 319 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TCUS_NM", HiArgUtils.getStringNull(etfRoot, "TCUS_NM"), true);
/*     */ 
/* 321 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TACC_DT", HiArgUtils.getStringNull(etfRoot, "TACC_DT"), true);
/*     */ 
/* 323 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TLOG_NO", HiArgUtils.getStringNull(etfRoot, "TLOG_NO"), true);
/*     */ 
/* 325 */     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TRSP_CD", HiArgUtils.getStringNull(etfRoot, "TRSP_CD"), true);
/*     */ 
/* 328 */     for (int i = 0; i < args.size(); ++i) {
/* 329 */       String key = args.getName(i);
/* 330 */       String value = args.getValue(i);
/* 331 */       sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, key, value, true);
/*     */     }
/*     */ 
/* 334 */     String LogNo = HiArgUtils.getStringNotNull(etfRoot, "LOG_NO");
/*     */ 
/* 337 */     sqlcmd.append(" WHERE LOG_NO='");
/* 338 */     sqlcmd.append(LogNo);
/* 339 */     sqlcmd.append("'");
/*     */ 
/* 341 */     Logger log = HiLog.getLogger(mess);
/* 342 */     if (log.isInfoEnabled()) {
/* 343 */       log.info(sqlcmd);
/*     */     }
/* 345 */     int ret = ctx.getDataBaseUtil().execUpdate(sqlcmd.toString());
/* 346 */     if (ret == 0) {
/* 347 */       return 2;
/*     */     }
/* 349 */     return 0;
/*     */   }
/*     */ 
/*     */   public int UpdateOldJournalHost(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 370 */     HiMessage msg = ctx.getCurrentMsg();
/*     */ 
/* 372 */     HiETF etfRoot = (HiETF)msg.getBody();
/* 373 */     String strOLogNo = HiArgUtils.getStringNotNull(etfRoot, "OLOG_NO");
/* 374 */     String strHTxnSt = HiArgUtils.getStringNotNull(etfRoot, "HTXN_STS");
/* 375 */     if (!(strHTxnSt.equals("S"))) {
/* 376 */       throw new HiException("220054", "交易失败，不更新原交易流水");
/*     */     }
/*     */ 
/* 380 */     HiAttributesHelper attr = HiAttributesHelper.getAttribute(ctx);
/* 381 */     if ((attr.isIntegtypeHand()) || (attr.isIntegtypeSys()))
/* 382 */       strHTxnSt = "C";
/* 383 */     else if (attr.isIntegtypeCCL())
/* 384 */       strHTxnSt = "D";
/*     */     else {
/* 386 */       throw new HiException("220055", "交易属性非冲正及撤销，不需更新原交易流水,配置错");
/*     */     }
/*     */ 
/* 400 */     int ret = HiAtcLib.updTxnJnlSts(ctx, "HST", strOLogNo, strHTxnSt, null, null);
/*     */ 
/* 402 */     if (ret == -1) {
/* 403 */       throw new HiException("220053", "更新原交易流水状态失败");
/*     */     }
/*     */ 
/* 406 */     return ret;
/*     */   }
/*     */ 
/*     */   public int UpdateOldJournalThird(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 427 */     HiMessage msg = ctx.getCurrentMsg();
/* 428 */     HiETF etfRoot = (HiETF)msg.getBody();
/* 429 */     String strOLogNo = HiArgUtils.getStringNotNull(etfRoot, "OLOG_NO");
/* 430 */     String strTTxnSt = HiArgUtils.getStringNotNull(etfRoot, "TTXN_STS");
/* 431 */     if (!(strTTxnSt.equals("S"))) {
/* 432 */       throw new HiException("220054", "交易失败，不更新原交易流水");
/*     */     }
/*     */ 
/* 435 */     HiAttributesHelper attr = HiAttributesHelper.getAttribute(ctx);
/*     */ 
/* 437 */     if ((attr.isIntegtypeHand()) || (attr.isIntegtypeSys()))
/* 438 */       strTTxnSt = "C";
/* 439 */     else if (attr.isIntegtypeCCL())
/* 440 */       strTTxnSt = "D";
/*     */     else {
/* 442 */       throw new HiException("220055", "交易属性非冲正及撤销，不需更新原交易流水,配置错");
/*     */     }
/*     */ 
/* 457 */     int ret = HiAtcLib.updTxnJnlSts(ctx, "THD", strOLogNo, null, strTTxnSt, null);
/*     */ 
/* 459 */     if (ret == -1) {
/* 460 */       throw new HiException("220053", "更新原交易流水状态失败");
/*     */     }
/*     */ 
/* 464 */     return ret;
/*     */   }
/*     */ 
/*     */   public int UpdateOldJournal(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 485 */     HiMessage msg = ctx.getCurrentMsg();
/* 486 */     HiETF etfRoot = (HiETF)msg.getBody();
/* 487 */     String strOLogNo = HiArgUtils.getStringNotNull(etfRoot, "OLOG_NO");
/* 488 */     String strTTxnSt = HiArgUtils.getStringNull(etfRoot, "TTXN_STS");
/* 489 */     String strHTxnSt = HiArgUtils.getStringNull(etfRoot, "HTXN_STS");
/* 490 */     if (((strTTxnSt != null) && (!(strTTxnSt.equals("S")))) || ((strHTxnSt != null) && (!(strHTxnSt.equals("S")))) || ((strTTxnSt == null) && (strHTxnSt == null)))
/*     */     {
/* 494 */       throw new HiException("220054", "交易失败，不更新原交易流水");
/*     */     }
/*     */ 
/* 498 */     String strTxnSts = null;
/*     */ 
/* 500 */     HiAttributesHelper attr = HiAttributesHelper.getAttribute(ctx);
/*     */ 
/* 502 */     if ((attr.isIntegtypeHand()) || (attr.isIntegtypeSys()))
/* 503 */       strTTxnSt = "C";
/* 504 */     else if (attr.isIntegtypeCCL())
/* 505 */       strTTxnSt = "D";
/*     */     else {
/* 507 */       throw new HiException("220055", "交易属性非冲正及撤销，不需更新原交易流水,配置错");
/*     */     }
/*     */ 
/* 520 */     int ret = HiAtcLib.updTxnJnlSts(ctx, "ALL", strOLogNo, strTxnSts, strTxnSts, strTxnSts);
/*     */ 
/* 522 */     if (ret == -1) {
/* 523 */       throw new HiException("220053", "更新原交易流水状态失败");
/*     */     }
/*     */ 
/* 527 */     return ret;
/*     */   }
/*     */ }