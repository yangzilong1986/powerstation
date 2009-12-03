/*     */ package com.hisun.atc.common;
/*     */ 
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.exception.HiAppException;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiStringUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiDbtUtils
/*     */ {
/*     */   public static int dbtsqlinsrec(String tableName, HiETF root, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  39 */     ArrayList params = new ArrayList();
/*  40 */     String sqlCmd = HiDbtSqlHelper.buildInsertSentence(ctx, root, tableName, params);
/*  41 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*  42 */     if (log.isInfoEnabled()) {
/*  43 */       log.info("dtsqlinsrec:[" + sqlCmd + "]:[" + params + "]");
/*     */     }
/*     */ 
/*  46 */     int ret = 0;
/*  47 */     ret = ctx.getDataBaseUtil().execUpdate(sqlCmd, params);
/*     */ 
/*  49 */     if (ret == 0) {
/*  50 */       return 1;
/*     */     }
/*  52 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int dbtsqlupdrec(String tableName, String keyName, String keyValue, int keyType, HiETF root, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  76 */     String cndSts = HiDbtSqlHelper.getKeyCndSts(keyName, keyValue, keyType);
/*     */ 
/*  78 */     String sqlCmd = HiDbtSqlHelper.buildUpdateSentence(ctx, root, tableName, cndSts, false);
/*     */ 
/*  81 */     ctx.getDataBaseUtil().execUpdate(sqlCmd);
/*  82 */     return 0;
/*     */   }
/*     */ 
/*     */   public static void dbtsqlrdrec(String tableName, String keyName, String keyValue, int keyType, HiETF root, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 106 */     String cndSts = HiDbtSqlHelper.getKeyCndSts(keyName, keyValue, keyType);
/* 107 */     String sqlCmd = "SELECT * FROM " + tableName + " WHERE " + cndSts;
/*     */ 
/* 109 */     dbtsqlqueryrec(sqlCmd, root, ctx);
/*     */   }
/*     */ 
/*     */   public static int dbtsqlquery(String sqlCmd, HiETF root, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 125 */     return dbtsqlquery_limit(sqlCmd, -1, root, ctx);
/*     */   }
/*     */ 
/*     */   public static void dbtsqlqueryrec(String sqlCmd, HiETF root, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 142 */     List queryRs = ctx.getDataBaseUtil().execQuery(sqlCmd);
/*     */ 
/* 146 */     if ((queryRs != null) && (queryRs.size() == 0)) {
/* 147 */       throw new HiAppException(-100, "220040", "数据库无此记录");
/*     */     }
/*     */ 
/* 151 */     Map queryRec = (HashMap)queryRs.get(0);
/*     */ 
/* 154 */     HiDbtSqlHelper.sqlData2Etf(queryRec, root);
/*     */   }
/*     */ 
/*     */   public static HiDBCursor dbtsqlcursor(String sqlCmd, String flag, HiDBCursor cursor, HiETF root, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 274 */     return HiDbtSqlHelper.dbtCursor(sqlCmd, flag, cursor, root, ctx, false);
/*     */   }
/*     */ 
/*     */   public static HiDBCursor dbtextcursor(String tableName, String cond, String flag, HiDBCursor cursor, HiETF root, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 300 */     String sqlCmd = "SELECT * FROM " + tableName + " WHERE " + cond;
/*     */ 
/* 302 */     return HiDbtSqlHelper.dbtCursor(sqlCmd, flag, cursor, root, ctx, true);
/*     */   }
/*     */ 
/*     */   public static int dbtextinsrec(String tableName, HiETF root, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 317 */     return dbtextinsrecdeep("011", tableName, root, ctx);
/*     */   }
/*     */ 
/*     */   public static int dbtextinsrecdeep(String extKey, String tableName, HiETF root, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 333 */     HiMessage mess = ctx.getCurrentMsg();
/*     */ 
/* 335 */     Logger log = HiLog.getLogger(mess);
/*     */ 
/* 337 */     String childTableName = null;
/*     */ 
/* 339 */     childTableName = HiDbtSqlHelper.getChildTableName(tableName, ctx);
/*     */ 
/* 341 */     if (extKey.equals("011")) {
/* 342 */       extKey = HiDbtSqlHelper.getExtKey(ctx);
/*     */     }
/*     */ 
/* 347 */     HashMap colsMap = ctx.getTableMetaData(tableName);
/* 348 */     if ((colsMap == null) || (colsMap.size() == 0)) {
/* 349 */       throw new HiException("220056", "取表结构失败：" + tableName);
/*     */     }
/*     */ 
/* 353 */     String sqlcmd = "";
/* 354 */     String values = "";
/*     */ 
/* 356 */     for (Iterator it = colsMap.entrySet().iterator(); it.hasNext(); ) {
/* 357 */       Map.Entry entry = (Map.Entry)it.next();
/* 358 */       String name = (String)entry.getKey();
/* 359 */       String value = root.getChildValue(name);
/*     */ 
/* 362 */       if (name.equalsIgnoreCase("EXTKEY")) {
/* 363 */         if (sqlcmd.length() > 0) {
/* 364 */           sqlcmd = sqlcmd + "," + name;
/*     */ 
/* 366 */           values = values + "," + extKey;
/*     */         }
/*     */         else {
/* 369 */           sqlcmd = sqlcmd + name;
/* 370 */           values = values + extKey;
/*     */         }
/*     */ 
/*     */       }
/* 374 */       else if (name.equalsIgnoreCase("CTBLNM")) {
/* 375 */         if (sqlcmd.length() > 0) {
/* 376 */           sqlcmd = sqlcmd + "," + name;
/*     */ 
/* 378 */           values = values + ",'" + childTableName + "'";
/*     */         }
/*     */         else {
/* 381 */           sqlcmd = sqlcmd + name;
/* 382 */           values = values + "'" + childTableName + "'";
/*     */         }
/*     */ 
/*     */       }
/* 386 */       else if (value != null) {
/* 387 */         if (sqlcmd.length() > 0) {
/* 388 */           sqlcmd = sqlcmd + "," + name;
/*     */ 
/* 390 */           values = values + ",'" + value + "'";
/*     */         }
/*     */         else {
/* 393 */           sqlcmd = sqlcmd + name;
/* 394 */           values = values + "'" + value + "'";
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 400 */     sqlcmd = "INSERT INTO " + tableName.toUpperCase() + "(" + sqlcmd + ") VALUES(" + values + ")";
/*     */ 
/* 402 */     if (log.isInfoEnabled()) {
/* 403 */       log.info("插入记录[" + sqlcmd + "]");
/*     */     }
/* 405 */     int ret = ctx.getDataBaseUtil().execUpdate(sqlcmd);
/* 406 */     if (ret == 0) {
/* 407 */       return ret;
/*     */     }
/*     */ 
/* 411 */     if (!(StringUtils.isEmpty(childTableName))) {
/* 412 */       ret = dbtextinsrecdeep(extKey, childTableName, root, ctx);
/*     */     }
/* 414 */     return ret;
/*     */   }
/*     */ 
/*     */   public static int dbtextupdreccon(String tableName, String cond, HiETF root, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 432 */     int ret = HiDbtSqlHelper.extUpdateRecord(tableName, cond, root, ctx);
/*     */ 
/* 435 */     String sqlQueryChild = HiStringUtils.format("SELECT CTBLNM, EXTKEY FROM %s WHERE %s", tableName, cond);
/*     */ 
/* 437 */     List queryRs = ctx.getDataBaseUtil().execQuery(sqlQueryChild);
/*     */ 
/* 440 */     if ((queryRs == null) || (queryRs.size() == 0))
/*     */     {
/* 444 */       return ret;
/*     */     }
/*     */ 
/* 447 */     Map queryRec = (HashMap)queryRs.get(0);
/* 448 */     String childTable = (String)queryRec.get("CTBLNM");
/* 449 */     String ExtKey = (String)queryRec.get("EXTKEY");
/*     */ 
/* 452 */     if ((StringUtils.isNotEmpty(childTable)) && (StringUtils.isNotEmpty(ExtKey)))
/*     */     {
/* 454 */       String CndSts = HiStringUtils.format(" EXTKEY = %s ", ExtKey);
/*     */ 
/* 456 */       ret = HiDbtSqlHelper.extUpdateRecord(childTable, CndSts, root, ctx);
/*     */     }
/*     */ 
/* 459 */     return ret;
/*     */   }
/*     */ 
/*     */   public static void dbtextrdreccon(String tableName, String cond, HiETF root, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 476 */     HiMessage mess = ctx.getCurrentMsg();
/* 477 */     Logger log = HiLog.getLogger(mess);
/*     */ 
/* 480 */     String sqlcmd = HiStringUtils.format("SELECT * FROM %s WHERE %s", tableName, cond);
/*     */ 
/* 483 */     if (log.isInfoEnabled()) {
/* 484 */       log.info("查询语句:[" + sqlcmd + "]");
/*     */     }
/* 486 */     List queryRs = ctx.getDataBaseUtil().execQuery(sqlcmd);
/*     */ 
/* 490 */     if ((queryRs == null) || (queryRs.size() == 0)) {
/* 491 */       throw new HiAppException(-100, "220040", "数据库无此记录");
/*     */     }
/*     */ 
/* 495 */     Map queryRec = (HashMap)queryRs.get(0);
/*     */ 
/* 498 */     HiDbtSqlHelper.extSqlData2Etf(queryRec, root, ctx);
/*     */   }
/*     */ 
/*     */   public static int dbtextquery(String tableName, String cond, HiETF root, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 518 */     return dbtextquery_limit(tableName, cond, -1, root, ctx);
/*     */   }
/*     */ 
/*     */   public static int dbtextdelreccon(String tableName, String cond, HiETF root, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 536 */     int ret = 0;
/* 537 */     boolean isExt = true;
/*     */ 
/* 540 */     String sqlQueryChild = HiStringUtils.format("SELECT CTBLNM, EXTKEY FROM %s WHERE %s", tableName, cond);
/*     */ 
/* 543 */     List queryRs = null;
/*     */     try
/*     */     {
/* 546 */       queryRs = ctx.getDataBaseUtil().execQuery(sqlQueryChild);
/*     */     } catch (HiException e) {
/* 548 */       isExt = false;
/*     */     }
/*     */ 
/* 552 */     String sqlCmd = HiStringUtils.format("DELETE FROM %s WHERE %s", tableName, cond);
/*     */ 
/* 555 */     ret = ctx.getDataBaseUtil().execUpdate(sqlCmd);
/*     */ 
/* 558 */     HiMessage msg = ctx.getCurrentMsg();
/* 559 */     Logger log = HiLog.getLogger(msg);
/* 560 */     if (isExt)
/*     */     {
/* 562 */       if ((queryRs != null) && (queryRs.size() == 0))
/*     */       {
/* 566 */         return ret;
/*     */       }
/*     */ 
/* 569 */       Map queryRec = (HashMap)queryRs.get(0);
/* 570 */       String childTable = (String)queryRec.get("CTBLNM");
/* 571 */       String ExtKey = (String)queryRec.get("EXTKEY");
/*     */ 
/* 574 */       if ((StringUtils.isNotEmpty(childTable)) && (StringUtils.isNotEmpty(ExtKey)))
/*     */       {
/* 576 */         if (log.isInfoEnabled()) {
/* 577 */           log.info("CTBLNM[" + childTable + "],EXTKEY[" + ExtKey + "]");
/*     */         }
/*     */ 
/* 580 */         String CndSts = HiStringUtils.format(" EXTKEY = %s ", ExtKey);
/*     */ 
/* 582 */         dbtextdelreccon(childTable, CndSts, root, ctx);
/*     */       }
/*     */     }
/* 585 */     return ret;
/*     */   }
/*     */ 
/*     */   public static int dbtextquery_limit(String tableName, String cond, int limits, HiETF root, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 638 */     String sqlCmd = HiStringUtils.format("SELECT * FROM %s WHERE %s", tableName, cond);
/*     */ 
/* 641 */     List queryRs = ctx.getDataBaseUtil().execQuery(sqlCmd, limits);
/*     */ 
/* 643 */     int rsSize = queryRs.size();
/* 644 */     if (rsSize == 0) {
/* 645 */       return rsSize;
/*     */     }
/*     */ 
/* 649 */     if ((limits < 0) || (rsSize < limits)) {
/* 650 */       limits = rsSize;
/*     */     }
/*     */ 
/* 655 */     String preName = root.getName() + "_";
/* 656 */     for (int i = 0; i < limits; ++i) {
/* 657 */       HiETF recNode = root.addNode(preName + i);
/*     */ 
/* 659 */       Map queryRec = (HashMap)queryRs.get(i);
/* 660 */       HiDbtSqlHelper.extSqlData2Etf(queryRec, recNode, ctx);
/*     */     }
/* 662 */     return limits;
/*     */   }
/*     */ 
/*     */   public static int dbtextquery_limit(String tableName, String cond, int limits, HiETF root, String recName, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 687 */     String sqlCmd = HiStringUtils.format("SELECT * FROM %s WHERE %s", tableName, cond);
/*     */ 
/* 690 */     List queryRs = ctx.getDataBaseUtil().execQuery(sqlCmd, limits);
/*     */ 
/* 692 */     int rsSize = queryRs.size();
/* 693 */     if (rsSize == 0) {
/* 694 */       return rsSize;
/*     */     }
/*     */ 
/* 698 */     if ((limits < 0) || (rsSize < limits)) {
/* 699 */       limits = rsSize;
/*     */     }
/*     */ 
/* 704 */     String preName = recName + "_";
/* 705 */     for (int i = 0; i < limits; ++i) {
/* 706 */       HiETF recNode = root.addNode(preName + i);
/*     */ 
/* 708 */       Map queryRec = (HashMap)queryRs.get(i);
/* 709 */       HiDbtSqlHelper.extSqlData2Etf(queryRec, recNode, ctx);
/*     */     }
/* 711 */     return limits;
/*     */   }
/*     */ 
/*     */   public static int dbtsqlquery_limit(String sqlCmd, int limits, HiETF root, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 732 */     List queryRs = ctx.getDataBaseUtil().execQuery(sqlCmd);
/*     */ 
/* 734 */     int rsSize = queryRs.size();
/* 735 */     if (rsSize == 0) {
/* 736 */       return rsSize;
/*     */     }
/*     */ 
/* 740 */     if ((limits < 0) || (rsSize < limits)) {
/* 741 */       limits = rsSize;
/*     */     }
/*     */ 
/* 746 */     String preName = root.getName() + "_";
/* 747 */     for (int i = 0; i < limits; ++i) {
/* 748 */       HiETF recNode = root.addNode(preName + (i + 1));
/*     */ 
/* 750 */       Map queryRec = (HashMap)queryRs.get(i);
/* 751 */       HiDbtSqlHelper.sqlData2Etf(queryRec, recNode);
/*     */     }
/* 753 */     return limits;
/*     */   }
/*     */ 
/*     */   public static int dbtsqlupdreccon(String tableName, String cndSts, HiETF root, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 774 */     String sqlCmd = HiDbtSqlHelper.buildUpdateSentence(ctx, root, tableName, cndSts, false);
/*     */ 
/* 777 */     ctx.getDataBaseUtil().execUpdate(sqlCmd);
/* 778 */     return 0;
/*     */   }
/*     */ }