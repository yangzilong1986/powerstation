/*     */ package com.hisun.atc;
/*     */ 
/*     */ import com.hisun.atc.common.HiArgUtils;
/*     */ import com.hisun.atc.common.HiDbtSqlHelper;
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.database.HiResultSet;
/*     */ import com.hisun.exception.HiAppException;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.sqn.HiSqnMng;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import com.hisun.util.HiStringUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiTranCtl
/*     */ {
/*  36 */   private static HiStringManager sm = HiStringManager.getManager();
/*     */ 
/*     */   public int getVirtualTeller(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     String txnCnl;
/*  59 */     HiMessage msg = ctx.getCurrentMsg();
/*  60 */     Logger log = HiLog.getLogger(msg);
/*  61 */     HiETF root = (HiETF)msg.getBody();
/*     */ 
/*  66 */     String brNo = HiArgUtils.getStringNotNull(root, "BR_NO");
/*  67 */     if (args.size() > 0)
/*  68 */       txnCnl = HiArgUtils.getStringNotNull(args, "TxnCnl");
/*     */     else {
/*  70 */       txnCnl = HiArgUtils.getStringNotNull(root, "TXN_CNL");
/*     */     }
/*     */ 
/*  73 */     String cnlSub = args.get("CnlSub");
/*  74 */     if (StringUtils.isEmpty(cnlSub)) {
/*  75 */       cnlSub = HiArgUtils.getString(root, "CNL_SUB");
/*     */     }
/*  77 */     if (log.isDebugEnabled()) {
/*  78 */       log.debug(sm.getString("HiTranCtl.getVirtualTeller.param", brNo, txnCnl, cnlSub));
/*     */     }
/*     */ 
/*  97 */     root.setChildValue("TLR_ID", HiSqnMng.getDumTlr(brNo, txnCnl, cnlSub));
/*     */ 
/*  99 */     if (log.isInfoEnabled()) {
/* 100 */       log.info(sm.getString("HiTranCtl.getVirtualTeller.TlrId", root.getChildValue("TLR_ID")));
/*     */     }
/*     */ 
/* 103 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getLogNo(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     int num;
/* 122 */     HiMessage mess = ctx.getCurrentMsg();
/* 123 */     Logger log = HiLog.getLogger(mess);
/* 124 */     HiETF root = (HiETF)mess.getBody();
/*     */ 
/* 128 */     if ((num = args.getInt("Num")) < 0) {
/* 129 */       throw new HiException("220026", String.valueOf(num));
/*     */     }
/*     */ 
/* 132 */     if (num == 0) {
/* 133 */       num = 1;
/*     */     }
/* 135 */     if (log.isDebugEnabled()) {
/* 136 */       log.debug(sm.getString("HiTranCtl.getLogNo.param", String.valueOf(num)));
/*     */     }
/*     */ 
/* 151 */     HiMessage msg = ctx.getCurrentMsg();
/* 152 */     String logNo = HiSqnMng.getLogNo(msg, num);
/* 153 */     root.setChildValue("LOG_NO", logNo);
/* 154 */     if (log.isInfoEnabled()) {
/* 155 */       log.info(sm.getString("HiTranCtl.getLogNo.LogNo", logNo));
/*     */     }
/* 157 */     return 0;
/*     */   }
/*     */ 
/*     */   public int GetSeqNo(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     int seqNo;
/* 188 */     HiMessage mess = ctx.getCurrentMsg();
/* 189 */     Logger log = HiLog.getLogger(mess);
/* 190 */     HiETF root = (HiETF)mess.getBody();
/* 191 */     String sqlCmd = null;
/*     */ 
/* 196 */     boolean circled = false;
/*     */ 
/* 198 */     String tblName = HiArgUtils.getStringNotNull(args, "TblNam");
/* 199 */     String seqCol = HiArgUtils.getStringNotNull(args, "SeqCol");
/* 200 */     int maxLen = args.getInt("Len");
/* 201 */     String colName = args.get("ColNam");
/* 202 */     if (StringUtils.isEmpty(colName)) {
/* 203 */       colName = seqCol;
/*     */     }
/*     */ 
/* 206 */     String cndStsDef = args.get("CndSts");
/* 207 */     if (StringUtils.isEmpty(cndStsDef)) {
/* 208 */       sqlCmd = "SELECT %s FROM %s ";
/* 209 */       sqlCmd = HiStringUtils.format(sqlCmd, seqCol, tblName);
/*     */     } else {
/* 211 */       sqlCmd = "SELECT %s FROM %s WHERE %s ";
/*     */ 
/* 213 */       cndStsDef = HiDbtSqlHelper.getDynSentence(ctx, cndStsDef);
/* 214 */       sqlCmd = HiStringUtils.format(sqlCmd, seqCol, tblName, cndStsDef);
/*     */     }
/* 216 */     circled = args.getBoolean("Circle");
/* 217 */     if (log.isDebugEnabled()) {
/* 218 */       log.debug(sm.getString("HiTranClt.getSeqNo.param", new Object[] { tblName, seqCol, String.valueOf(maxLen), cndStsDef, colName, String.valueOf(circled) }));
/*     */     }
/*     */ 
/* 223 */     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
/*     */     try
/*     */     {
/*     */       String value;
/* 225 */       String sqlCmd1 = null;
/*     */ 
/* 227 */       if (StringUtils.isEmpty(cndStsDef)) {
/* 228 */         sqlCmd1 = "UPDATE %s SET %s = %s";
/* 229 */         sqlCmd1 = HiStringUtils.format(sqlCmd1, tblName, seqCol, seqCol);
/*     */       }
/*     */       else {
/* 232 */         sqlCmd1 = "UPDATE %s SET %s = %s WHERE %s ";
/* 233 */         sqlCmd1 = HiStringUtils.format(sqlCmd1, tblName, seqCol, seqCol, cndStsDef);
/*     */       }
/*     */ 
/* 236 */       dbUtil.execUpdate(sqlCmd1);
/*     */ 
/* 238 */       HiResultSet rs = dbUtil.execQuerySQL(sqlCmd);
/* 239 */       if (rs.size() == 0) {
/* 240 */         throw new HiException("220040", sqlCmd);
/*     */       }
/* 242 */       seqNo = rs.getInt(0, 0);
/* 243 */       int nextSeqNo = seqNo + 1;
/* 244 */       if (String.valueOf(nextSeqNo).length() > maxLen) {
/* 245 */         if (circled)
/* 246 */           seqNo = 1;
/*     */         else {
/* 248 */           throw new HiException("220062", tblName, String.valueOf(seqCol), String.valueOf(nextSeqNo));
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 253 */       nextSeqNo = seqNo + 1;
/* 254 */       if (StringUtils.isEmpty(cndStsDef)) {
/* 255 */         sqlCmd = "UPDATE %s SET %s = '%s'";
/* 256 */         value = StringUtils.leftPad(String.valueOf(nextSeqNo), maxLen, "0");
/*     */ 
/* 258 */         sqlCmd = HiStringUtils.format(sqlCmd, tblName, seqCol, value);
/*     */       } else {
/* 260 */         sqlCmd = "UPDATE %s SET %s = '%s' WHERE %s ";
/*     */ 
/* 262 */         value = StringUtils.leftPad(String.valueOf(nextSeqNo), maxLen, "0");
/*     */ 
/* 264 */         sqlCmd = HiStringUtils.format(sqlCmd, tblName, seqCol, value, cndStsDef);
/*     */       }
/*     */ 
/* 267 */       dbUtil.execUpdate(sqlCmd);
/*     */     } finally {
/* 269 */       dbUtil.commit();
/*     */     }
/*     */ 
/* 272 */     if (log.isDebugEnabled()) {
/* 273 */       log.debug(sm.getString("HiTranCtl.getSeqNo.value", colName, StringUtils.leftPad(String.valueOf(seqNo), maxLen, "0")));
/*     */     }
/*     */ 
/* 276 */     root.setChildValue(colName, StringUtils.leftPad(String.valueOf(seqNo), maxLen, "0"));
/*     */ 
/* 278 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getSeqNoCircle(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 308 */     args.put("Circle", "true");
/* 309 */     return GetSeqNo(args, ctx);
/*     */   }
/*     */ 
/*     */   public int getPubSeqNo(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     int seqNo;
/* 342 */     HiMessage mess = ctx.getCurrentMsg();
/* 343 */     Logger log = HiLog.getLogger(mess);
/* 344 */     HiETF root = (HiETF)mess.getBody();
/*     */ 
/* 348 */     int ret = 0;
/* 349 */     boolean circled = false;
/*     */ 
/* 352 */     String brNo = root.getChildValue("BR_NO");
/*     */ 
/* 354 */     if (StringUtils.isEmpty(brNo)) {
/* 355 */       brNo = ctx.getBCFG("BR_NO");
/*     */     }
/* 357 */     if (StringUtils.isEmpty(brNo)) {
/* 358 */       throw new HiException("220082", "BR_NO");
/*     */     }
/*     */ 
/* 361 */     String seqName = HiArgUtils.getStringNotNull(args, "SeqNam");
/* 362 */     String seqCond = args.get("SeqCnd");
/* 363 */     if (seqCond == null)
/* 364 */       seqCond = "";
/* 365 */     int maxLen = args.getInt("Len");
/* 366 */     if (maxLen <= 0) {
/* 367 */       throw new HiAppException(-1, "220026", "Len");
/*     */     }
/*     */ 
/* 370 */     int seqCnt = args.getInt("SeqCnt");
/*     */ 
/* 372 */     String acDate = HiArgUtils.getString(root, "ACC_DT");
/*     */ 
/* 374 */     if (seqCnt <= 0) {
/* 375 */       seqCnt = 1;
/*     */     }
/*     */ 
/* 378 */     circled = args.getBoolean("Circle");
/* 379 */     if (log.isDebugEnabled()) {
/* 380 */       log.debug(sm.getString("HiTranCtl.getPubSeqNo.param", new Object[] { brNo, seqName, seqCond, String.valueOf(maxLen), String.valueOf(seqCnt), String.valueOf(circled) }));
/*     */     }
/*     */ 
/* 385 */     String sqlCmd = "SELECT SEL_VAL FROM PUBSEQREC WHERE BR_NO='%s' AND SEQ_NM='%s' AND SEQ_CND='%s'";
/* 386 */     sqlCmd = HiStringUtils.format(sqlCmd, brNo, seqName, seqCond);
/*     */ 
/* 388 */     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
/*     */     try
/*     */     {
/* 391 */       String sqlCmd1 = null;
/* 392 */       sqlCmd1 = "UPDATE PUBSEQREC SET SEL_VAL = SEL_VAL WHERE BR_NO='%s' AND SEQ_NM='%s' AND SEQ_CND='%s'";
/* 393 */       sqlCmd1 = HiStringUtils.format(sqlCmd1, brNo, seqName, seqCond);
/*     */ 
/* 395 */       dbUtil.execUpdate(sqlCmd1);
/*     */ 
/* 397 */       if (log.isDebugEnabled()) {
/* 398 */         log.debug(sm.getString("HiTranCtl.sqlCmd", sqlCmd));
/*     */       }
/*     */ 
/* 401 */       HiResultSet rs = dbUtil.execQuerySQL(sqlCmd);
/*     */ 
/* 403 */       if (rs.size() == 0) {
/* 404 */         seqNo = 1;
/* 405 */         sqlCmd = "INSERT INTO PUBSEQREC VALUES('%s', '%s', '%s', %s)";
/* 406 */         sqlCmd = HiStringUtils.format(sqlCmd, brNo, seqName, seqCond, String.valueOf(seqCnt + 1));
/*     */ 
/* 408 */         if (log.isDebugEnabled()) {
/* 409 */           log.debug(sm.getString("HiTranCtl.sqlCmd", sqlCmd));
/*     */         }
/* 411 */         dbUtil.execUpdate(sqlCmd);
/*     */       } else {
/* 413 */         seqNo = rs.getInt(0, 0);
/*     */ 
/* 431 */         if (ret > 0) {
/* 432 */           seqNo = 1;
/* 433 */         } else if (ret < 0) {
/* 434 */           dbUtil.rollback();
/* 435 */           throw new HiException("220061", acDate, seqCond);
/*     */         }
/*     */ 
/* 439 */         int nextSeqNo = seqNo + seqCnt;
/*     */ 
/* 441 */         if (String.valueOf(nextSeqNo).length() > maxLen) {
/* 442 */           if (!(circled)) {
/* 443 */             dbUtil.rollback();
/* 444 */             throw new HiException("220062", "PUBSEQREC", seqName, seqCond, String.valueOf(nextSeqNo));
/*     */           }
/*     */ 
/* 450 */           if (circled) {
/* 451 */             seqNo = 1;
/* 452 */             nextSeqNo = seqCnt + seqNo;
/*     */           }
/*     */         }
/* 455 */         sqlCmd = "UPDATE PUBSEQREC SET SEL_VAL = %s WHERE BR_NO='%s' AND SEQ_NM='%s' AND SEQ_CND='%s'";
/* 456 */         String value = StringUtils.leftPad(String.valueOf(nextSeqNo), maxLen, "0");
/*     */ 
/* 458 */         sqlCmd = HiStringUtils.format(sqlCmd, value, brNo, seqName, seqCond);
/*     */ 
/* 460 */         dbUtil.execUpdate(sqlCmd);
/*     */       }
/*     */     } finally {
/* 463 */       dbUtil.commit();
/*     */     }
/*     */ 
/* 466 */     root.setChildValue("SEL_VAL", StringUtils.leftPad(String.valueOf(seqNo), maxLen, "0"));
/*     */ 
/* 468 */     if (log.isDebugEnabled()) {
/* 469 */       log.debug(sm.getString("HiTranCtl.getPubSeqNo.SelVal", root.getChildValue("SEL_VAL")));
/*     */     }
/*     */ 
/* 473 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getPubSeqNoCircle(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 505 */     args.put("Circle", "true");
/* 506 */     return getPubSeqNo(args, ctx);
/*     */   }
/*     */ 
/*     */   public int nGetPubSeqNo(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     int seqNo;
/* 536 */     HiMessage mess = ctx.getCurrentMsg();
/* 537 */     Logger log = HiLog.getLogger(mess);
/* 538 */     HiETF root = (HiETF)mess.getBody();
/*     */ 
/* 542 */     int ret = 0;
/* 543 */     boolean circled = false;
/*     */ 
/* 545 */     String brNo = HiArgUtils.getStringNotNull(root, "BR_NO");
/* 546 */     String seqName = HiArgUtils.getStringNotNull(args, "SeqNam");
/* 547 */     int maxLen = args.getInt("Len");
/* 548 */     int seqCnt = args.getInt("SeqCnt");
/* 549 */     String cycCond = args.get("CycCnd");
/* 550 */     String seqCond = null;
/*     */ 
/* 552 */     String acDate = HiArgUtils.getStringNotNull(root, "ACC_DT");
/*     */ 
/* 554 */     if (seqCnt <= 0) {
/* 555 */       seqCnt = 1;
/*     */     }
/* 557 */     circled = args.getBoolean("Circle");
/* 558 */     if (log.isDebugEnabled()) {
/* 559 */       log.debug(sm.getString("HiTranCtl.getPubSeqNo.param", new Object[] { brNo, seqName, seqCond, String.valueOf(maxLen), String.valueOf(seqCnt), String.valueOf(circled) }));
/*     */     }
/*     */ 
/* 564 */     String sqlCmd = "SELECT SEL_VAL, SEQ_CND FROM PUBSEQREC WHERE BR_NO='%s' AND SEQ_NM='%s'";
/* 565 */     sqlCmd = HiStringUtils.format(sqlCmd, brNo, seqName);
/*     */ 
/* 567 */     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
/*     */     try {
/* 569 */       String sqlCmd1 = null;
/* 570 */       sqlCmd1 = "UPDATE PUBSEQREC SET SEL_VAL = SE_VAL WHERE BR_NO='%s' AND SEQ_NM='%s'";
/* 571 */       sqlCmd1 = HiStringUtils.format(sqlCmd1, brNo, seqName);
/*     */ 
/* 573 */       dbUtil.execUpdate(sqlCmd1);
/*     */ 
/* 575 */       if (log.isDebugEnabled()) {
/* 576 */         log.debug(sm.getString("HiTranCtl.sqlCmd", sqlCmd));
/*     */       }
/*     */ 
/* 579 */       HiResultSet rs = dbUtil.execQuerySQL(sqlCmd);
/*     */ 
/* 581 */       if (rs.size() == 0) {
/* 582 */         seqNo = 1;
/* 583 */         sqlCmd = "INSERT INTO PUBSEQREC VALUES('%s', '%s', '%s', %s)";
/* 584 */         sqlCmd = HiStringUtils.format(sqlCmd, brNo, seqName, acDate, String.valueOf(seqCnt + 1));
/*     */ 
/* 586 */         if (log.isDebugEnabled()) {
/* 587 */           log.debug(sm.getString("HiTranCtl.sqlCmd", sqlCmd));
/*     */         }
/* 589 */         dbUtil.execUpdate(sqlCmd);
/*     */       } else {
/* 591 */         seqNo = rs.getInt(0, 0);
/* 592 */         seqCond = rs.getValue(0, 1);
/*     */ 
/* 594 */         if (StringUtils.equalsIgnoreCase(cycCond, "Y")) {
/* 595 */           ret = StringUtils.substring(acDate, 0, 4).compareTo(StringUtils.substring(seqCond, 0, 4));
/*     */         }
/* 597 */         else if (StringUtils.equalsIgnoreCase(cycCond, "M")) {
/* 598 */           ret = StringUtils.substring(acDate, 0, 6).compareTo(StringUtils.substring(seqCond, 0, 6));
/*     */         }
/*     */         else {
/* 601 */           ret = StringUtils.substring(acDate, 0, 8).compareTo(StringUtils.substring(seqCond, 0, 8));
/*     */         }
/*     */ 
/* 604 */         if (ret > 0) {
/* 605 */           seqNo = 1;
/* 606 */         } else if (ret < 0) {
/* 607 */           dbUtil.rollback();
/* 608 */           throw new HiException("220061", acDate, seqCond);
/*     */         }
/*     */ 
/* 612 */         int nextSeqNo = seqNo + seqCnt;
/*     */ 
/* 614 */         if (String.valueOf(nextSeqNo).length() > maxLen) {
/* 615 */           if (!(circled)) {
/* 616 */             dbUtil.rollback();
/* 617 */             throw new HiException("220062", "PUBSEQREC", seqName, seqCond, String.valueOf(nextSeqNo));
/*     */           }
/*     */ 
/* 623 */           if (circled) {
/* 624 */             seqNo = 1;
/* 625 */             nextSeqNo = seqCnt + seqNo;
/*     */           }
/*     */         }
/* 628 */         sqlCmd = "UPDATE PUBSEQREC SET SEL_VAL = %s, SEQ_CND = '%s' WHERE BR_NO='%s' AND SEQ_NM='%s'";
/* 629 */         String value = StringUtils.leftPad(String.valueOf(nextSeqNo), maxLen, "0");
/*     */ 
/* 631 */         sqlCmd = HiStringUtils.format(sqlCmd, value, acDate, brNo, seqName);
/*     */ 
/* 633 */         dbUtil.execUpdate(sqlCmd);
/*     */       }
/*     */     } finally {
/* 636 */       dbUtil.commit();
/*     */     }
/* 638 */     root.setChildValue("SEL_VAL", StringUtils.leftPad(String.valueOf(seqNo), maxLen, "0"));
/*     */ 
/* 640 */     if (log.isDebugEnabled()) {
/* 641 */       log.debug(sm.getString("HiTranCtl.getPubSeqNo.SelVal", root.getChildValue("SEL_VAL")));
/*     */     }
/*     */ 
/* 645 */     return 0;
/*     */   }
/*     */ }