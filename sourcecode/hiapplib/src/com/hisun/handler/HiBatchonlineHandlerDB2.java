/*     */ package com.hisun.handler;
/*     */ 
/*     */ import com.hisun.atc.HiTransJnl;
/*     */ import com.hisun.atc.bat.HiPubBatchInfo;
/*     */ import com.hisun.atc.common.HiAtcLib;
/*     */ import com.hisun.atc.common.HiDBCursor;
/*     */ import com.hisun.atc.common.HiDbtUtils;
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.dispatcher.HiRouterOut;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.event.IServerDestroyListener;
/*     */ import com.hisun.framework.event.IServerInitListener;
/*     */ import com.hisun.framework.event.IServerPauseListener;
/*     */ import com.hisun.framework.event.IServerResumeListener;
/*     */ import com.hisun.framework.event.IServerStartListener;
/*     */ import com.hisun.framework.event.IServerStopListener;
/*     */ import com.hisun.framework.event.ServerEvent;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.pubinterface.IHandler;
/*     */ import com.hisun.util.HiStringUtils;
/*     */ import com.hisun.util.HiThreadPool;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiBatchonlineHandlerDB2
/*     */   implements IHandler, IServerInitListener, IServerStartListener, IServerStopListener, IServerPauseListener, IServerDestroyListener, IServerResumeListener
/*     */ {
/*  51 */   public static int DEFAULT_MIN_THREADS = 5;
/*  52 */   public static int DEFAULT_MAX_THREADS = 20;
/*  53 */   public static int DEFAULT_MAX_SEND_COUNT = 99;
/*     */   public static final String BAT_HOST_MSGTYP = "PLTIN0";
/*     */   public static final int NON_STATUS = -1;
/*     */   public static final int RUN_STATUS = 0;
/*     */   public static final int STOP_STATUS = 1;
/*     */   public static final int PAUSE_STATUS = 2;
/*     */   private String _serverType;
/*     */   private int _level;
/*     */   private int _numOfPthread;
/*     */   private int _tranNumOfOverTime;
/*     */   private HiThreadPool _pool;
/*     */   private int _currTranNumOfOverTime;
/*     */   private Logger _serverLog;
/*     */   private int _maxSendCount;
/*     */   private String _serverName;
/*     */   private Object _lock;
/*     */   private int _status;
/*     */   private boolean _registerErrorFlag;
/*     */   private HiContext _serverContext;
/*     */ 
/*     */   public HiBatchonlineHandlerDB2()
/*     */   {
/*  73 */     this._currTranNumOfOverTime = 0;
/*     */ 
/*  75 */     this._maxSendCount = DEFAULT_MAX_SEND_COUNT;
/*     */ 
/*  77 */     this._lock = new Object();
/*     */ 
/*  81 */     this._status = -1;
/*  82 */     this._registerErrorFlag = false;
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx) throws HiException {
/*  86 */     long lastTime = 0L;
/*  87 */     long nowTime = 0L;
/*     */ 
/*  89 */     String actDat = null;
/*  90 */     String sysId = null;
/*  91 */     HiPubBatchInfo batchInfo = new HiPubBatchInfo();
/*  92 */     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
/*  93 */     HashMap record = null;
/*  94 */     if (this._status != -1)
/*  95 */       return;
/*  96 */     this._status = 0;
/*  97 */     if (this._serverLog.isInfoEnabled())
/*  98 */       this._serverLog.info("批量发送通道处理开始");
/*     */     while (true)
/*     */     {
/* 101 */       if (this._status == 2) {
/* 102 */         sleep(2, this._serverLog);
/*     */       }
/* 104 */       if (this._status == 1) {
/*     */         break;
/*     */       }
/*     */ 
/* 108 */       nowTime = System.currentTimeMillis() / 1000L;
/*     */ 
/* 110 */       if ((nowTime - lastTime > 60L) || (lastTime == 0L)) {
/* 111 */         String strSql = "SELECT ACC_DT, SYS_ID FROM pubpltinf";
/* 112 */         record = dbUtil.readRecord(strSql);
/* 113 */         if (record.isEmpty()) {
/* 114 */           this._serverLog.warn("760009,取会计日期失败,稍后再试!");
/* 115 */           sleep(2, this._serverLog);
/*     */         }
/*     */ 
/* 118 */         actDat = (String)record.get("ACC_DT");
/* 119 */         sysId = (String)record.get("SYS_ID");
/* 120 */         lastTime = nowTime;
/*     */       }
/*     */ 
/* 123 */       if (this._serverType.equals("B"))
/* 124 */         checkServerTypeForB(dbUtil, actDat);
/*     */       else {
/* 126 */         checkServerTypeForA(dbUtil, actDat);
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 133 */         record = dbUtil.readRecord("SELECT * FROM PUBBATINF WHERE CMT_FLG = '%s'  ORDER BY Lst_Tm FETCH FIRST ROW ONLY", this._serverType);
/*     */       }
/*     */       catch (HiException e)
/*     */       {
/*     */         while (true) {
/* 138 */           this._serverLog.error("760012,查找待处理的批次时SELECT pubbatinf失败", e);
/* 139 */           sleep(5, this._serverLog);
/*     */         }
/*     */       }
/*     */ 
/* 143 */       if (record.isEmpty()) {
/* 144 */         sleep(5, this._serverLog);
/*     */       }
/*     */ 
/* 148 */       if (this._serverLog.isDebugEnabled()) {
/* 149 */         this._serverLog.debug("pubbatinf:[" + record + "]");
/*     */       }
/*     */ 
/* 152 */       batchInfo.setValuesFromMap(record);
/* 153 */       if (!(StringUtils.equals(actDat, batchInfo.acc_dt)))
/*     */       {
/* 155 */         this._serverLog.warn("760010, 原磁盘文件编号[" + batchInfo.dsk_no + "]因主机换日[" + actDat + "]被迫中止!");
/*     */         try
/*     */         {
/* 158 */           dbUtil.execUpdate("UPDATE pubbatinf SET CMT_FLG = 'S' WHERE  DSK_NO = '%s'", batchInfo.dsk_no);
/*     */ 
/* 162 */           dbUtil.commit();
/*     */         } catch (HiException e) {
/* 164 */           this._serverLog.error("760011,停止因主机换日而终止的批次时UPDATE pubbatinf失败", e);
/*     */ 
/* 166 */           dbUtil.rollback();
/*     */         }
/* 168 */         sleep(5, this._serverLog);
/*     */       }
/* 170 */       if (NumberUtils.toInt(batchInfo.snd_cnt) >= this._maxSendCount)
/*     */       {
/* 172 */         this._serverLog.warn("760046,原磁盘文件编号[" + batchInfo.dsk_no + "]发送次数已经到达最大值[" + this._maxSendCount + "]被迫中止!");
/*     */         try
/*     */         {
/* 175 */           dbUtil.execUpdate("UPDATE pubbatinf SET CMT_FLG = 'S' WHERE  DSK_NO = '%s'", batchInfo.dsk_no);
/*     */ 
/* 179 */           dbUtil.commit();
/*     */         } catch (HiException e) {
/* 181 */           this._serverLog.error("760011,停止因主机换日而终止的批次时UPDATE pubbatinf失败", e);
/*     */ 
/* 183 */           dbUtil.rollback();
/*     */         }
/* 185 */         sleep(5, this._serverLog);
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 194 */         sendRecord(ctx, batchInfo, sysId);
/*     */       } catch (HiException e) {
/* 196 */         dbUtil.rollback();
/* 197 */         this._serverLog.warn("处理磁盘文件编号=[" + batchInfo.dsk_no + "]的批次失败", e);
/*     */       }
/* 199 */       if (this._registerErrorFlag) {
/* 200 */         registerError(ctx, batchInfo);
/*     */       }
/* 202 */       sleep(5, this._serverLog);
/*     */     }
/* 204 */     this._status = -1;
/*     */   }
/*     */ 
/*     */   private int checkServerTypeForB(HiDataBaseUtil dbUtil, String actDat)
/*     */     throws HiException
/*     */   {
/* 216 */     int ret = 0;
/*     */     try {
/* 218 */       ret = dbUtil.execUpdate("UPDATE pubbatinf SET CMT_FLG = 'B' WHERE DSK_NO IN (SELECT  min(DSK_NO) FROM pubbatinf WHERE ACC_DT = '%s' AND CMT_FLG = '0' AND TXN_MOD = '1' AND CHK_FLG = '1' AND CAST(ORN_CNT AS INT) >= %s)", actDat, String.valueOf(this._level));
/*     */ 
/* 224 */       if (ret == 0)
/*     */       {
/* 226 */         ret = dbUtil.execUpdate("UPDATE pubbatinf SET CMT_FLG = 'B' WHERE DSK_NO IN (SELECT  min(DSK_NO) FROM pubbatinf WHERE ACC_DT = '%s' AND CMT_FLG = '0' AND TXN_MOD = '1' AND CHK_FLG = '1' AND CAST(ORN_CNT AS INT) < %s)", actDat, String.valueOf(this._level));
/*     */       }
/*     */ 
/* 232 */       dbUtil.commit();
/*     */     } catch (HiException e) {
/* 234 */       this._serverLog.error("大通道正在检查符合条件的批次记录", e);
/* 235 */       dbUtil.rollback();
/* 236 */       sleep(10, this._serverLog);
/*     */     }
/*     */ 
/* 239 */     if ((ret != 0) && (this._serverLog.isInfoEnabled())) {
/* 240 */       this._serverLog.info("找到符合条件的一个小批次,将由大通道处理!");
/*     */     }
/* 242 */     return ret;
/*     */   }
/*     */ 
/*     */   private int checkServerTypeForA(HiDataBaseUtil dbUtil, String actDat)
/*     */     throws HiException
/*     */   {
/* 253 */     int ret = 0;
/*     */     try {
/* 255 */       ret = dbUtil.execUpdate("UPDATE pubbatinf SET CMT_FLG = 'A' WHERE DSK_NO IN (SELECT  min(DSK_NO) FROM pubbatinf WHERE ACC_DT = '%s' AND CMT_FLG = '0' AND TXN_MOD = '1'  AND CHK_FLG = '1' AND CAST(ORN_CNT AS INT) < %s)", actDat, String.valueOf(this._level));
/*     */ 
/* 261 */       dbUtil.commit();
/*     */     } catch (HiException e) {
/* 263 */       this._serverLog.error("小通道正在检查符合条件的批次记录", e);
/* 264 */       dbUtil.rollback();
/* 265 */       sleep(10, this._serverLog);
/*     */     }
/* 267 */     if ((ret != 0) && (this._serverLog.isInfoEnabled())) {
/* 268 */       this._serverLog.info("找到符合条件的一个小批次,将由小通道处理!");
/*     */     }
/*     */ 
/* 271 */     return ret;
/*     */   }
/*     */ 
/*     */   private void sendRecord(HiMessageContext ctx, HiPubBatchInfo batchInfo, String sysId)
/*     */     throws HiException
/*     */   {
/*     */     label856: String strSql;
/* 289 */     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
/* 290 */     this._registerErrorFlag = true;
/* 291 */     if (this._serverType.equals("A")) {
/* 292 */       if (this._serverLog.isInfoEnabled()) {
/* 293 */         this._serverLog.info("小通道开始处理批次号=[" + batchInfo.dsk_no + "]的批次");
/*     */       }
/*     */     }
/* 296 */     else if (this._serverLog.isInfoEnabled()) {
/* 297 */       this._serverLog.info("大通道开始处理批次号=[" + batchInfo.dsk_no + "]的批次");
/*     */     }
/*     */ 
/* 301 */     String sqlCmd = "SELECT SYS_ID FROM pubbchlst WHERE BR_NO='%s'";
/*     */ 
/* 303 */     HashMap record = null;
/* 304 */     record = dbUtil.readRecord(sqlCmd, batchInfo.br_no);
/*     */ 
/* 306 */     if ((record != null) && (!(record.isEmpty()))) {
/* 307 */       sysId = (String)record.get("SYS_ID");
/*     */     }
/*     */ 
/* 310 */     String batTbl = batchInfo.trd_tbl;
/* 311 */     if (StringUtils.isBlank(batTbl)) {
/* 312 */       String strSql = "SELECT A.BAT_TBL FROM pubjnldef A, pubbatinf B WHERE B.DSK_NO = '%s'AND A.BR_NO = B.BR_NO AND A.BUS_TYP = B.BUS_TYP AND A.CRP_CD = B.CRP_CD";
/*     */ 
/* 314 */       record = dbUtil.readRecord(strSql, batchInfo.dsk_no);
/* 315 */       batTbl = (String)record.get("BAT_TBL");
/* 316 */       if (StringUtils.isBlank(batTbl)) {
/* 317 */         this._serverLog.warn("DSK_NO=[" + batchInfo.dsk_no + "]没有在pubjnldef中配置对应的批量业务流水表名!");
/*     */ 
/* 319 */         return;
/*     */       }
/*     */     }
/*     */ 
/* 323 */     String stmt = HiStringUtils.format("SELECT * FROM %s WHERE DSK_NO = '%s' AND (HTXN_STS = 'U' OR HTXN_STS = 'E') AND IS_TXN = 'Y'", batTbl, batchInfo.dsk_no);
/*     */ 
/* 329 */     HiMessage msg = ctx.getCurrentMsg();
/* 330 */     HiDBCursor cur = null;
/* 331 */     cur = HiDbtUtils.dbtsqlcursor(stmt, "O", null, msg.getETFBody(), ctx);
/* 332 */     HiETF root_rec = null;
/*     */ 
/* 334 */     HiMessage newMsg = null;
/* 335 */     ArrayList futures = new ArrayList();
/* 336 */     boolean hasProcess = false;
/*     */     try
/*     */     {
/* 339 */       i = 1;
/* 340 */       while (this._status != 1)
/*     */       {
/* 345 */         if (this._currTranNumOfOverTime >= this._tranNumOfOverTime) {
/* 346 */           this._serverLog.warn("760020,达到规定的超时,系统错误交易笔数退出");
/* 347 */           break;
/*     */         }
/*     */ 
/* 350 */         root_rec = HiETFFactory.createETF();
/* 351 */         cur = HiDbtUtils.dbtsqlcursor(stmt, "F", cur, root_rec, ctx);
/*     */ 
/* 354 */         if (cur.ret == 100) {
/*     */           break;
/*     */         }
/* 357 */         hasProcess = true;
/* 358 */         if (this._serverLog.isInfoEnabled()) {
/* 359 */           this._serverLog.info("760021, 处理磁盘文件编号[" + batchInfo.dsk_no + "]批次，第[" + i + "]笔");
/*     */         }
/*     */ 
/* 363 */         String brNo = root_rec.getChildValue("BR_NO");
/* 364 */         if (StringUtils.isBlank(brNo)) {
/* 365 */           brNo = batchInfo.br_no;
/*     */         } else {
/* 367 */           record = dbUtil.readRecord("SELECT SYS_ID FROM pubbchlst WHERE BR_NO = '%s'", brNo);
/*     */ 
/* 371 */           if (!(record.isEmpty())) {
/* 372 */             sysId = (String)record.get("SYS_ID");
/*     */           }
/*     */         }
/*     */ 
/* 376 */         String txnCnl = root_rec.getChildValue("TXN_CNL");
/* 377 */         if (StringUtils.isNotBlank(txnCnl)) {
/* 378 */           String cnlSub = root_rec.getChildValue("CNL_SUB");
/* 379 */           String tlrId = null;
/*     */ 
/* 381 */           tlrId = HiAtcLib.sqnGetDumTlr(ctx, brNo, txnCnl, cnlSub);
/* 382 */           root_rec.setChildValue("TLR_ID", tlrId);
/*     */         }
/*     */ 
/* 385 */         root_rec.setChildValue("TBL_NM", batTbl);
/* 386 */         root_rec.setChildValue("UPD_FLG", batchInfo.upd_flg);
/* 387 */         root_rec.setChildValue("FSYS_ID", sysId);
/*     */ 
/* 389 */         newMsg = new HiMessage(this._serverName, msg.getType());
/*     */ 
/* 391 */         if (msg.getHeadItem("STF") != null)
/* 392 */           newMsg.setHeadItem("STF", msg.getHeadItem("STF"));
/*     */         else {
/* 394 */           newMsg.setHeadItem("STF", "0");
/*     */         }
/* 396 */         newMsg.setHeadItem("STF", "1");
/*     */ 
/* 398 */         newMsg.setHeadItem("SCH", "rq");
/* 399 */         newMsg.setHeadItem("ECT", "text/etf");
/*     */ 
/* 402 */         newMsg.setBody(root_rec);
/* 403 */         BatchOnlineProcess bo = new BatchOnlineProcess();
/* 404 */         bo.setCurrentMsg(newMsg);
/*     */         while (true)
/*     */           try {
/* 407 */             futures.add(this._pool.submit(bo));
/*     */           }
/*     */           catch (RejectedExecutionException rje) {
/* 410 */             while (this._status != 1)
/*     */             {
/* 414 */               sleep(5, this._serverLog);
/*     */             }
/*     */           }
/* 339 */         ++i;
/*     */       }
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/* 420 */       cur.close();
/*     */     }
/*     */ 
/* 423 */     if (!(hasProcess)) {
/* 424 */       if (this._serverLog.isInfoEnabled()) {
/* 425 */         this._serverLog.info("磁盘文件编号[" + batchInfo.dsk_no + "]批次没有符合条件记录");
/*     */       }
/* 427 */       return;
/*     */     }
/*     */ 
/* 430 */     for (int i = 0; i < futures.size(); ++i) {
/*     */       try {
/* 432 */         ((Future)futures.get(i)).get(60L, TimeUnit.SECONDS);
/*     */       } catch (Exception e) {
/* 434 */         this._serverLog.error("等待线程结束异常", e);
/* 435 */         break label856:
/*     */       }
/*     */     }
/*     */ 
/* 439 */     if (this._serverLog.isInfoEnabled()) {
/* 440 */       this._serverLog.info("本次处理完毕!当前主机超时,系统错误数=[" + this._currTranNumOfOverTime + "],当前已用线程数=[" + (this._pool.getActiveCount() - 1) + "]");
/*     */     }
/*     */ 
/* 444 */     if (this._currTranNumOfOverTime != 0) {
/* 445 */       return;
/*     */     }
/*     */ 
/* 448 */     this._registerErrorFlag = false;
/* 449 */     if (StringUtils.equals(batchInfo.sum_flg, "Y"))
/*     */     {
/* 451 */       strSql = "SELECT CAST(COUNT(*) AS CHAR(8)) as SUC_CNT, CAST(SUM(CAST(TXN_AMT AS BIGINT)) AS CHAR(15)) as SUC_AMT FROM %s WHERE DSK_NO = '%s' AND IS_TXN = 'Y' AND (HTXN_STS = 'S' OR HRSP_CD = 'SC6128') ";
/*     */ 
/* 455 */       record = dbUtil.readRecord(strSql, batTbl, batchInfo.dsk_no);
/*     */ 
/* 457 */       if ((record != null) && (!(record.isEmpty()))) {
/* 458 */         String sucCnt = (String)record.get("SUC_CNT");
/* 459 */         String sucAmt = (String)record.get("SUC_AMT");
/* 460 */         if (StringUtils.isBlank(sucCnt))
/* 461 */           batchInfo.suc_cnt = "0";
/*     */         else {
/* 463 */           batchInfo.suc_cnt = sucCnt;
/*     */         }
/* 465 */         if (StringUtils.isBlank(sucAmt))
/* 466 */           batchInfo.suc_amt = "0";
/*     */         else {
/* 468 */           batchInfo.suc_amt = sucAmt;
/*     */         }
/* 470 */         strSql = "UPDATE pubbatinf SET CMT_FLG = 'C',SUC_CNT = '%s',SUC_AMT = '%s',FAL_CNT = CAST(CAST(ORN_CNT AS INT) - CAST(%s AS INT) AS CHAR(8)),FAL_AMT = CAST(CAST(ORN_AMT AS BIGINT) - CAST(%s AS BIGINT) AS CHAR(15)) WHERE  DSK_NO  = '%s'";
/*     */ 
/* 473 */         dbUtil.execUpdate(strSql, batchInfo.suc_cnt, batchInfo.suc_amt, batchInfo.suc_cnt, batchInfo.suc_amt, batchInfo.dsk_no);
/*     */ 
/* 475 */         dbUtil.commit();
/*     */       }
/*     */     } else {
/* 478 */       strSql = "UPDATE pubbatinf SET CMT_FLG = 'C' WHERE DSK_NO  = '%s'";
/* 479 */       dbUtil.execUpdate(strSql, batchInfo.dsk_no);
/* 480 */       dbUtil.commit();
/*     */     }
/*     */ 
/* 483 */     if ((StringUtils.isBlank(batchInfo.fTxn_Cd)) || (StringUtils.isBlank(batchInfo.obj_svr)))
/*     */     {
/* 485 */       return;
/*     */     }
/*     */ 
/* 488 */     HiTransJnl transJnl = new HiTransJnl();
/*     */ 
/* 490 */     transJnl.setTranId(batchInfo.dsk_no);
/* 491 */     if (this._serverType.equals("B"))
/* 492 */       transJnl.setSerNam("S.LBCSVR");
/*     */     else {
/* 494 */       transJnl.setSerNam("S.SBCSVR");
/*     */     }
/* 496 */     transJnl.setExpSer(batchInfo.fTxn_Cd);
/* 497 */     transJnl.setLogNo(batchInfo.dsk_no);
/* 498 */     transJnl.setItv(30);
/* 499 */     transJnl.setTmOut(30);
/* 500 */     transJnl.setTimOut(30);
/* 501 */     transJnl.setActDat(batchInfo.acc_dt);
/* 502 */     transJnl.setMaxTms(10);
/* 503 */     transJnl.register();
/*     */   }
/*     */ 
/*     */   private void registerError(HiMessageContext ctx, HiPubBatchInfo batchInfo)
/*     */     throws HiException
/*     */   {
/* 509 */     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
/*     */ 
/* 511 */     int snd_cnt = NumberUtils.toInt(batchInfo.snd_cnt) + 1;
/* 512 */     String sqlCmd = "UPDATE pubbatinf SET Snd_Cnt='%s', Lst_Tm='%s' WHERE Dsk_No='%s'";
/*     */     try {
/* 514 */       dbUtil.execUpdate(sqlCmd, String.valueOf(snd_cnt), String.valueOf(System.currentTimeMillis() / 1000L), batchInfo.dsk_no);
/*     */ 
/* 517 */       dbUtil.commit();
/*     */     } catch (HiException e) {
/* 519 */       this._serverLog.error("registerError", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void sleep(int second, Logger log) {
/*     */     try {
/* 525 */       Thread.currentThread(); Thread.sleep(second * 1000);
/*     */     } catch (InterruptedException e) {
/* 527 */       log.error(e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setAttribOfServer(String AttribOfServer) throws HiException {
/* 532 */     if ((!(StringUtils.equals(AttribOfServer, "A"))) && (!(StringUtils.equals(AttribOfServer, "B"))))
/*     */     {
/* 534 */       throw new HiException("231414", AttribOfServer);
/*     */     }
/*     */ 
/* 537 */     this._serverType = AttribOfServer;
/*     */   }
/*     */ 
/*     */   public void setNumOfLevel(String NumOfLevel) throws HiException {
/* 541 */     this._level = Integer.parseInt(NumOfLevel);
/* 542 */     if ((this._level > 500) || (this._level < 200))
/* 543 */       throw new HiException("231416", NumOfLevel);
/*     */   }
/*     */ 
/*     */   public void setNumOfPthread(String NumOfPthread) throws HiException
/*     */   {
/* 548 */     this._numOfPthread = Integer.parseInt(NumOfPthread);
/* 549 */     if ((this._numOfPthread > 20) || (this._numOfPthread < 1))
/* 550 */       throw new HiException("231415", NumOfPthread);
/*     */   }
/*     */ 
/*     */   public void setTranNumOfOverTime(String TranNumOfOverTime)
/*     */     throws HiException
/*     */   {
/* 557 */     this._tranNumOfOverTime = Integer.parseInt(TranNumOfOverTime);
/* 558 */     if ((this._tranNumOfOverTime > 20) || (this._tranNumOfOverTime < 1))
/* 559 */       throw new HiException("231417", TranNumOfOverTime);
/*     */   }
/*     */ 
/*     */   public void serverInit(ServerEvent arg0)
/*     */     throws HiException
/*     */   {
/* 565 */     this._serverContext = arg0.getServerContext();
/* 566 */     if (DEFAULT_MIN_THREADS > this._numOfPthread) {
/* 567 */       this._numOfPthread = DEFAULT_MIN_THREADS;
/*     */     }
/* 569 */     this._pool = HiThreadPool.createThreadPool(DEFAULT_MIN_THREADS, this._numOfPthread);
/*     */ 
/* 571 */     this._serverLog = arg0.getLog();
/* 572 */     this._serverName = arg0.getServerContext().getStrProp("SVR.name");
/*     */   }
/*     */ 
/*     */   public void serverStart(ServerEvent arg0) throws HiException
/*     */   {
/* 577 */     this._pool.submit(new HiStartWork());
/*     */   }
/*     */ 
/*     */   public void serverStop(ServerEvent arg0) throws HiException {
/* 581 */     if (this._status != 0)
/* 582 */       return;
/* 583 */     this._status = 1;
/* 584 */     this._pool.shutdown();
/*     */     try {
/* 586 */       this._pool.awaitTermination(30L, TimeUnit.SECONDS);
/*     */     } catch (InterruptedException e) {
/* 588 */       this._serverLog.error("serverStop", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void serverResume(ServerEvent arg0) {
/* 593 */     if (this._status != 2)
/* 594 */       return;
/* 595 */     this._status = 0;
/*     */   }
/*     */ 
/*     */   public void serverPause(ServerEvent arg0) {
/* 599 */     if (this._status != 0)
/* 600 */       return;
/* 601 */     this._status = 2;
/*     */   }
/*     */ 
/*     */   public void serverDestroy(ServerEvent arg0) throws HiException {
/* 605 */     serverStop(arg0);
/*     */   }
/*     */ 
/*     */   class BatchOnlineProcess
/*     */     implements Runnable
/*     */   {
/*     */     public static final String HOST_TRAN_SUCCESS = "N";
/*     */     public static final String HOST_TRAN_FAIL = "E";
/*     */     public static final String HOST_TRAN_CANCEL = "C";
/*     */     public static final String HOST_TRAN_REVERSE = "R";
/*     */     public static final String TRAN_SUCCESS = "S";
/*     */     public static final String TRAN_FAIL = "F";
/*     */     public static final String TRAN_CANCEL = "C";
/*     */     public static final String TRAN_REVERSE = "R";
/*     */     public static final String TRAN_UNKNOW = "U";
/*     */     public static final String TRAN_ERROR = "E";
/*     */     private HiMessage msg;
/*     */ 
/*     */     void setCurrentMsg(HiMessage msg)
/*     */     {
/* 644 */       this.msg = msg;
/*     */     }
/*     */ 
/*     */     public void run() {
/* 648 */       HiMessageContext ctx = new HiMessageContext();
/* 649 */       ctx.pushParent(HiBatchonlineHandlerDB2.this._serverContext);
/* 650 */       ctx.setCurrentMsg(this.msg);
/* 651 */       HiMessageContext.setCurrentMessageContext(ctx);
/*     */       try {
/* 653 */         process(ctx);
/*     */       } catch (Throwable t) {
/* 655 */         HiBatchonlineHandlerDB2.this._serverLog.error(t, t);
/*     */       } finally {
/* 657 */         if (ctx.getDataBaseUtil() != null)
/* 658 */           ctx.getDataBaseUtil().close();
/*     */       }
/*     */     }
/*     */ 
/*     */     private int process(HiMessageContext ctx)
/*     */     {
/* 664 */       String logNo = null; String updFlg = null; String stmt1 = null; String batTbl = null;
/* 665 */       String aHTxnSt = null; String hLogNo = null; String hRspCd = null; String tlrId = null;
/* 666 */       HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
/*     */ 
/* 668 */       HiETF etf = this.msg.getETFBody();
/* 669 */       HiETF root_o = null;
/* 670 */       Logger log = HiLog.getLogger(this.msg);
/*     */ 
/* 672 */       for (int i = 0; i < 1; ++i) {
/* 673 */         logNo = etf.getChildValue("LOG_NO");
/* 674 */         if (logNo == null) {
/* 675 */           log.warn("760031,找不到节点[LOG_NO]!");
/* 676 */           aHTxnSt = "E";
/* 677 */           break;
/*     */         }
/* 679 */         batTbl = etf.getChildValue("TBL_NM");
/* 680 */         if (batTbl == null) {
/* 681 */           log.warn("760031,找不到节点[TBL_NM]!");
/* 682 */           aHTxnSt = "E";
/* 683 */           break;
/*     */         }
/*     */ 
/* 686 */         tlrId = etf.getChildValue("TLR_ID");
/* 687 */         if (tlrId == null) {
/* 688 */           log.warn("760031,找不到节点[TLR_ID]!");
/* 689 */           aHTxnSt = "E";
/* 690 */           break;
/*     */         }
/*     */ 
/* 693 */         String aTxnCod = etf.getChildValue("HTXN_CD");
/* 694 */         if (aTxnCod == null) {
/* 695 */           log.warn("760031,找不到节点[HTXN_CD]!");
/* 696 */           aHTxnSt = "E";
/* 697 */           break;
/*     */         }
/* 699 */         this.msg.setHeadItem("STC", aTxnCod);
/* 700 */         updFlg = etf.getChildValue("UPD_FLG");
/* 701 */         if (updFlg == null) {
/* 702 */           log.warn("760031,找不到节点[UPD_FLG]!");
/* 703 */           aHTxnSt = "E";
/* 704 */           break;
/*     */         }
/* 706 */         String aTxnObj = etf.getChildValue("TXN_OBJ");
/* 707 */         if (StringUtils.isNotEmpty(aTxnObj)) {
/* 708 */           this.msg.setHeadItem("SDT", aTxnObj);
/*     */         }
/*     */ 
/* 711 */         if (log.isInfoEnabled()) {
/* 712 */           log.info("LOG_NO:[" + logNo + "], TLR_ID:[" + tlrId + "], HTXN_CD[:[" + aTxnCod + "], UPD_FLG[" + updFlg + "], TXN_OBJ:[" + aTxnObj + "]");
/*     */         }
/*     */ 
/*     */         try
/*     */         {
/* 718 */           this.msg.setHeadItem("STM", new Long(System.currentTimeMillis()));
/*     */ 
/* 720 */           HiRouterOut.process(ctx);
/* 721 */           this.msg = HiRouterOut.syncProcess(this.msg);
/* 722 */           HiMessageContext.setCurrentMessageContext(ctx);
/* 723 */           ctx.setCurrentMsg(this.msg);
/*     */         } catch (HiException e) {
/* 725 */           log.warn("调主机交易错误!", e);
/* 726 */           aHTxnSt = "E";
/* 727 */           ctx.setCurrentMsg(this.msg);
/* 728 */           break label585:
/*     */         }
/*     */ 
/* 731 */         String aError = this.msg.getHeadItem("SSC");
/* 732 */         if ((StringUtils.isNotEmpty(aError)) && (!(StringUtils.equals("000000", aError))))
/*     */         {
/* 734 */           log.warn("760031,前置错误,此交易失败:[" + aError + "]");
/* 735 */           aHTxnSt = "E";
/* 736 */           break;
/*     */         }
/*     */ 
/* 739 */         root_o = this.msg.getETFBody();
/* 740 */         aHTxnSt = root_o.getChildValue("MSG_TYP");
/* 741 */         if (aHTxnSt == null) {
/* 742 */           log.warn("760031,找不到节点[MSG_TYP]!");
/* 743 */           aHTxnSt = "E";
/* 744 */           break;
/*     */         }
/*     */ 
/* 747 */         if (StringUtils.equals("N", aHTxnSt)) {
/* 748 */           aHTxnSt = "S";
/*     */         }
/*     */ 
/* 751 */         if (StringUtils.equals("E", aHTxnSt)) {
/* 752 */           aHTxnSt = "F";
/*     */         }
/*     */ 
/* 755 */         hRspCd = root_o.getChildValue("HRSP_CD");
/* 756 */         if (hRspCd == null) {
/* 757 */           log.warn("760031,找不到节点[HRSP_CD]!");
/* 758 */           aHTxnSt = "E";
/* 759 */           break;
/*     */         }
/*     */ 
/* 762 */         hLogNo = root_o.getChildValue("HLOG_NO");
/* 763 */         if (hLogNo == null) {
/* 764 */           hLogNo = "         ";
/*     */         }
/*     */       }
/*     */ 
/* 768 */       for (i = 0; i < 1; ++i) {
/* 769 */         if ((!(StringUtils.equalsIgnoreCase(aHTxnSt, "S"))) && (!(StringUtils.equalsIgnoreCase(aHTxnSt, "F"))))
/*     */         {
/* 771 */           synchronized (HiBatchonlineHandlerDB2.this._lock) {
/* 772 */             label585: HiBatchonlineHandlerDB2.access$408(HiBatchonlineHandlerDB2.this);
/*     */           }
/*     */         }
/*     */ 
/* 776 */         if (logNo == null) {
/*     */           break;
/*     */         }
/* 779 */         String condition = null;
/*     */ 
/* 787 */         if (StringUtils.equals(updFlg, "Y")) {
/* 788 */           condition = bpCreateCondition(etf, root_o, log, ctx);
/*     */         }
/* 790 */         if (StringUtils.isNotEmpty(condition)) {
/* 791 */           stmt1 = "UPDATE %s SET HTXN_STS = '%s', HLOG_NO = '%s', HRSP_CD = '%s', TLR_ID='%s', %s WHERE LOG_NO = '%s'";
/* 792 */           stmt1 = HiStringUtils.format(stmt1, new String[] { batTbl, aHTxnSt, hLogNo, hRspCd, tlrId, condition, logNo });
/*     */         }
/*     */         else {
/* 795 */           stmt1 = "UPDATE %s SET TXN_STS='%s',HTXN_STS = '%s', HLOG_NO = '%s', HRSP_CD = '%s', TLR_ID='%s' WHERE LOG_NO = '%s'";
/* 796 */           stmt1 = HiStringUtils.format(stmt1, new String[] { batTbl, aHTxnSt, aHTxnSt, hLogNo, hRspCd, tlrId, logNo });
/*     */         }
/*     */ 
/* 799 */         if (log.isInfoEnabled()) {
/* 800 */           log.info("batchOnline-stmt1=[" + stmt1 + "]");
/*     */         }
/*     */         try
/*     */         {
/* 804 */           dbUtil.execUpdate(stmt1);
/* 805 */           dbUtil.commit();
/*     */         } catch (HiException e) {
/* 807 */           log.warn("batchOnline-stmt1 失败", e);
/*     */           try {
/* 809 */             dbUtil.rollback();
/*     */           }
/*     */           catch (Exception e1) {
/*     */           }
/*     */         }
/*     */       }
/* 815 */       HiMessageContext.removeCurrentContext();
/* 816 */       return 0;
/*     */     }
/*     */ 
/*     */     private String bpCreateCondition(HiETF root_i, HiETF root_o, Logger log, HiMessageContext ctx)
/*     */     {
/* 821 */       String strFields = root_o.getChildValue("UPD_FLD");
/* 822 */       if (StringUtils.isBlank(strFields)) {
/* 823 */         log.warn("760031,找不到节点[UPD_FLD]!");
/* 824 */         return null;
/*     */       }
/* 826 */       String[] fields = StringUtils.split(strFields, "|");
/*     */ 
/* 828 */       StringBuffer value = new StringBuffer();
/* 829 */       for (int i = 0; i < fields.length; ++i) {
/* 830 */         if (fields[i].indexOf("=") > 0) {
/* 831 */           if (i > 0) {
/* 832 */             value.append(",");
/*     */           }
/* 834 */           value.append(fields[i]);
/*     */         } else {
/* 836 */           if (i > 0) {
/* 837 */             value.append(",");
/*     */           }
/* 839 */           String v = (String)ctx.getSpecExpre(root_o, fields[i]);
/* 840 */           if (v == null)
/* 841 */             v = "";
/* 842 */           value.append(fields[i] + "='" + v + "'");
/*     */         }
/*     */       }
/* 845 */       return value.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */   class HiStartWork
/*     */     implements Runnable
/*     */   {
/*     */     public void run()
/*     */     {
/* 611 */       HiMessageContext ctx = new HiMessageContext();
/*     */       try {
/* 613 */         ctx.pushParent(HiBatchonlineHandlerDB2.this._serverContext);
/* 614 */         HiMessage msg = new HiMessage(HiBatchonlineHandlerDB2.this._serverName, "PLTIN0");
/* 615 */         ctx.setCurrentMsg(msg);
/* 616 */         HiMessageContext.setCurrentContext(ctx);
/* 617 */         HiBatchonlineHandlerDB2.this.process(ctx);
/*     */       } catch (Throwable t) {
/* 619 */         HiBatchonlineHandlerDB2.this._serverLog.error(t, t);
/*     */       } finally {
/* 621 */         if (ctx.getDataBaseUtil() != null)
/* 622 */           ctx.getDataBaseUtil().close();
/*     */       }
/*     */     }
/*     */   }
/*     */ }