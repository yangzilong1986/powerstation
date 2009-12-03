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
/*     */ import com.hisun.framework.IServer;
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
/*     */ public class HiBatchonlineHandler
/*     */   implements IHandler, IServerInitListener, IServerStartListener, IServerStopListener, IServerPauseListener, IServerDestroyListener, IServerResumeListener
/*     */ {
/*  48 */   public static int DEFAULT_MIN_THREADS = 5;
/*  49 */   public static int DEFAULT_MAX_THREADS = 20;
/*  50 */   public static int DEFAULT_MAX_SEND_COUNT = 99;
/*     */   public static final String BAT_HOST_MSGTYP = "PLTIN0";
/*     */   public static final int NON_STATUS = -1;
/*     */   public static final int RUN_STATUS = 0;
/*     */   public static final int STOP_STATUS = 1;
/*     */   public static final int PAUSE_STATUS = 2;
/*     */   private String _serverType;
/*     */   private int _level;
/*     */   private int _numOfPthread;
/*     */   private int _queueSize;
/*     */   private IServer _server;
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
/*     */   private String brNo;
/*     */ 
/*     */   public HiBatchonlineHandler()
/*     */   {
/*  67 */     this._queueSize = 1000;
/*     */ 
/*  73 */     this._currTranNumOfOverTime = 0;
/*     */ 
/*  75 */     this._maxSendCount = DEFAULT_MAX_SEND_COUNT;
/*     */ 
/*  77 */     this._lock = new Object();
/*     */ 
/*  81 */     this._status = -1;
/*  82 */     this._registerErrorFlag = false;
/*     */ 
/*  87 */     this.brNo = ""; }
/*     */ 
/*     */   public void process(HiMessageContext ctx) throws HiException {
/*  90 */     long lastTime = 0L;
/*  91 */     long nowTime = 0L;
/*     */ 
/*  93 */     String actDat = null;
/*  94 */     String sysId = null;
/*  95 */     HiPubBatchInfo batchInfo = new HiPubBatchInfo();
/*  96 */     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
/*  97 */     HashMap record = null;
/*  98 */     if (this._status != -1)
/*  99 */       return;
/* 100 */     this._status = 0;
/* 101 */     if (this._serverLog.isInfoEnabled())
/* 102 */       this._serverLog.info("批量发送通道处理开始");
/*     */     while (true)
/*     */     {
/* 105 */       if (this._status == 2) {
/* 106 */         sleep(2, this._serverLog);
/*     */       }
/* 108 */       if (this._status == 1) {
/*     */         break;
/*     */       }
/*     */ 
/* 112 */       nowTime = System.currentTimeMillis() / 1000L;
/*     */ 
/* 114 */       if ((nowTime - lastTime > 60L) || (lastTime == 0L)) {
/* 115 */         String strSql = "SELECT ACC_DT, SYS_ID FROM pubpltinf";
/* 116 */         record = dbUtil.readRecord(strSql);
/* 117 */         if (record.isEmpty()) {
/* 118 */           this._serverLog.warn("760009,取会计日期失败,稍后再试!");
/* 119 */           sleep(2, this._serverLog);
/*     */         }
/*     */ 
/* 122 */         actDat = (String)record.get("ACC_DT");
/* 123 */         sysId = (String)record.get("SYS_ID");
/* 124 */         lastTime = nowTime;
/*     */       }
/*     */ 
/* 127 */       if (this._serverType.equals("B"))
/* 128 */         checkServerTypeForB(dbUtil, actDat, this.brNo);
/*     */       else {
/* 130 */         checkServerTypeForA(dbUtil, actDat, this.brNo);
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 141 */         record = dbUtil.readRecord("SELECT * FROM PUBBATINF WHERE (BR_NO='%s' or '%s'='') and CMT_FLG = '%s'  AND ROWNUM=1 ORDER BY Lst_Tm ", this.brNo, this.brNo, this._serverType);
/*     */       }
/*     */       catch (Throwable e)
/*     */       {
/*     */         while (true)
/*     */         {
/* 147 */           this._serverLog.error("760012,查找待处理的批次时SELECT pubbatinf失败", e);
/* 148 */           sleep(5, this._serverLog);
/*     */         }
/*     */       }
/*     */ 
/* 152 */       if (record.isEmpty()) {
/* 153 */         sleep(5, this._serverLog);
/*     */       }
/*     */ 
/* 157 */       if (this._serverLog.isDebugEnabled()) {
/* 158 */         this._serverLog.debug("pubbatinf:[" + record + "]");
/*     */       }
/*     */ 
/* 161 */       batchInfo.setValuesFromMap(record);
/* 162 */       if (!(StringUtils.equals(actDat, batchInfo.acc_dt)))
/*     */       {
/* 164 */         this._serverLog.warn("760010, 原磁盘文件编号[" + batchInfo.dsk_no + "]因主机换日[" + actDat + "]被迫中止!");
/*     */         try
/*     */         {
/* 167 */           dbUtil.execUpdate("UPDATE pubbatinf SET CMT_FLG = 'S' WHERE BR_NO='%s' and DSK_NO = '%s'", batchInfo.br_no, batchInfo.br_no, batchInfo.dsk_no);
/*     */ 
/* 171 */           dbUtil.commit();
/*     */         } catch (Throwable e) {
/* 173 */           this._serverLog.error("760011,停止因主机换日而终止的批次时UPDATE pubbatinf失败", e);
/*     */ 
/* 175 */           dbUtil.rollback();
/*     */         }
/* 177 */         sleep(5, this._serverLog);
/*     */       }
/* 179 */       if (NumberUtils.toInt(batchInfo.snd_cnt) >= this._maxSendCount)
/*     */       {
/* 181 */         this._serverLog.warn("760046,原磁盘文件编号[" + batchInfo.dsk_no + "]发送次数已经到达最大值[" + this._maxSendCount + "]被迫中止!");
/*     */         try
/*     */         {
/* 184 */           dbUtil.execUpdate("UPDATE pubbatinf SET CMT_FLG = 'S' WHERE BR_NO='%s' and DSK_NO = '%s'", batchInfo.br_no, batchInfo.dsk_no);
/*     */ 
/* 188 */           dbUtil.commit();
/*     */         } catch (Throwable e) {
/* 190 */           this._serverLog.error("760011,停止因主机换日而终止的批次时UPDATE pubbatinf失败", e);
/*     */ 
/* 192 */           dbUtil.rollback();
/*     */         }
/* 194 */         sleep(5, this._serverLog);
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 203 */         sendRecord(ctx, batchInfo, sysId);
/* 204 */         dbUtil.commit();
/*     */       } catch (Throwable e) {
/* 206 */         dbUtil.rollback();
/* 207 */         this._serverLog.warn("处理磁盘文件编号=[" + batchInfo.dsk_no + "]的批次失败", e);
/*     */       }
/* 209 */       if (this._registerErrorFlag) {
/* 210 */         registerError(ctx, batchInfo);
/*     */       }
/* 212 */       sleep(5, this._serverLog);
/* 213 */       this._currTranNumOfOverTime = 0;
/*     */     }
/* 215 */     this._status = -1;
/*     */   }
/*     */ 
/*     */   private int checkServerTypeForB(HiDataBaseUtil dbUtil, String actDat, String brNo)
/*     */     throws HiException
/*     */   {
/* 227 */     int ret = 0;
/*     */     try {
/* 229 */       ret = dbUtil.execUpdate("UPDATE pubbatinf SET CMT_FLG = 'B' WHERE (BR_NO='%s' or '%s'='') and DSK_NO IN (SELECT  min(DSK_NO) FROM pubbatinf WHERE ACC_DT = '%s' AND CMT_FLG = '0' AND TXN_MOD = '1' AND CHK_FLG = '1' AND CAST(ORN_CNT AS INT) >= %s)", brNo, brNo, actDat, String.valueOf(this._level));
/*     */ 
/* 235 */       if (ret == 0)
/*     */       {
/* 237 */         ret = dbUtil.execUpdate("UPDATE pubbatinf SET CMT_FLG = 'B' WHERE (BR_NO='%s' or '%s'='') and DSK_NO IN (SELECT  min(DSK_NO) FROM pubbatinf WHERE ACC_DT = '%s' AND CMT_FLG = '0' AND TXN_MOD = '1' AND CHK_FLG = '1' AND CAST(ORN_CNT AS INT) < %s)", brNo, brNo, actDat, String.valueOf(this._level));
/*     */       }
/*     */ 
/* 243 */       dbUtil.commit();
/*     */     } catch (Throwable e) {
/* 245 */       this._serverLog.error("大通道正在检查符合条件的批次记录", e);
/* 246 */       dbUtil.rollback();
/* 247 */       sleep(10, this._serverLog);
/*     */     }
/*     */ 
/* 250 */     if ((ret != 0) && (this._serverLog.isInfoEnabled())) {
/* 251 */       this._serverLog.info("找到符合条件的一个小批次,将由大通道处理!");
/*     */     }
/* 253 */     return ret;
/*     */   }
/*     */ 
/*     */   private int checkServerTypeForA(HiDataBaseUtil dbUtil, String actDat, String brNo)
/*     */     throws HiException
/*     */   {
/* 264 */     int ret = 0;
/*     */     try {
/* 266 */       ret = dbUtil.execUpdate("UPDATE pubbatinf SET CMT_FLG = 'A' WHERE (BR_NO='%s' or '%s'='') and DSK_NO IN (SELECT  min(DSK_NO) FROM pubbatinf WHERE ACC_DT = '%s' AND CMT_FLG = '0' AND TXN_MOD = '1'  AND CHK_FLG = '1' AND CAST(ORN_CNT AS INT) < %s)", brNo, brNo, actDat, String.valueOf(this._level));
/*     */ 
/* 272 */       dbUtil.commit();
/*     */     } catch (Throwable e) {
/* 274 */       this._serverLog.error("小通道正在检查符合条件的批次记录", e);
/* 275 */       dbUtil.rollback();
/* 276 */       sleep(10, this._serverLog);
/*     */     }
/* 278 */     if ((ret != 0) && (this._serverLog.isInfoEnabled())) {
/* 279 */       this._serverLog.info("找到符合条件的一个小批次,将由小通道处理!");
/*     */     }
/*     */ 
/* 282 */     return ret;
/*     */   }
/*     */ 
/*     */   private void sendRecord(HiMessageContext ctx, HiPubBatchInfo batchInfo, String sysId)
/*     */     throws HiException
/*     */   {
/*     */     label682: label816: String strSql;
/* 300 */     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
/* 301 */     this._registerErrorFlag = true;
/* 302 */     if (this._serverType.equals("A")) {
/* 303 */       if (this._serverLog.isInfoEnabled()) {
/* 304 */         this._serverLog.info("小通道开始处理批次号=[" + batchInfo.dsk_no + "]的批次");
/*     */       }
/*     */     }
/* 307 */     else if (this._serverLog.isInfoEnabled()) {
/* 308 */       this._serverLog.info("大通道开始处理批次号=[" + batchInfo.dsk_no + "]的批次");
/*     */     }
/*     */ 
/* 312 */     String sqlCmd = "SELECT SYS_ID FROM pubbchlst WHERE BR_NO='%s'";
/*     */ 
/* 314 */     HashMap record = null;
/* 315 */     record = dbUtil.readRecord(sqlCmd, batchInfo.br_no);
/*     */ 
/* 317 */     if ((record != null) && (!(record.isEmpty()))) {
/* 318 */       sysId = (String)record.get("SYS_ID");
/*     */     }
/*     */ 
/* 321 */     String batTbl = batchInfo.trd_tbl;
/* 322 */     if (StringUtils.isBlank(batTbl)) {
/* 323 */       String strSql = "SELECT A.BAT_TBL FROM pubjnldef A, pubbatinf B WHERE B.DSK_NO = '%s'AND A.BR_NO = B.BR_NO AND A.BUS_TYP = B.BUS_TYP AND A.CRP_CD = B.CRP_CD";
/*     */ 
/* 325 */       record = dbUtil.readRecord(strSql, batchInfo.dsk_no);
/* 326 */       batTbl = (String)record.get("BAT_TBL");
/* 327 */       if (StringUtils.isBlank(batTbl)) {
/* 328 */         this._serverLog.warn("DSK_NO=[" + batchInfo.dsk_no + "]没有在pubjnldef中配置对应的批量业务流水表名!");
/*     */ 
/* 330 */         return;
/*     */       }
/*     */     }
/*     */ 
/* 334 */     String stmt = HiStringUtils.format("SELECT * FROM %s WHERE DSK_NO = '%s' AND (HTXN_STS = 'U' OR HTXN_STS = 'E') AND IS_TXN = 'Y'", batTbl, batchInfo.dsk_no);
/*     */ 
/* 340 */     HiMessage msg = ctx.getCurrentMsg();
/* 341 */     HiDBCursor cur = null;
/* 342 */     cur = HiDbtUtils.dbtsqlcursor(stmt, "O", null, msg.getETFBody(), ctx);
/* 343 */     HiETF root_rec = null;
/*     */ 
/* 345 */     HiMessage newMsg = null;
/* 346 */     ArrayList futures = new ArrayList();
/* 347 */     boolean hasProcess = false;
/*     */     try
/*     */     {
/* 350 */       i = 1;
/* 351 */       while (this._status != 1)
/*     */       {
/* 356 */         if (this._currTranNumOfOverTime >= this._tranNumOfOverTime) {
/* 357 */           this._serverLog.warn("760020,达到规定的超时,系统错误交易笔数退出");
/* 358 */           break;
/*     */         }
/*     */ 
/* 361 */         root_rec = HiETFFactory.createETF();
/* 362 */         cur = HiDbtUtils.dbtsqlcursor(stmt, "F", cur, root_rec, ctx);
/*     */ 
/* 365 */         if (cur.ret == 100) {
/*     */           break;
/*     */         }
/* 368 */         hasProcess = true;
/* 369 */         if (this._serverLog.isInfoEnabled()) {
/* 370 */           this._serverLog.info("760021, 处理磁盘文件编号[" + batchInfo.dsk_no + "]批次，第[" + i + "]笔");
/*     */         }
/*     */ 
/* 374 */         String brNo = root_rec.getChildValue("BR_NO");
/* 375 */         if (StringUtils.isBlank(brNo)) {
/* 376 */           brNo = batchInfo.br_no;
/*     */         } else {
/* 378 */           record = dbUtil.readRecord("SELECT SYS_ID FROM pubbchlst WHERE BR_NO = '%s'", brNo);
/*     */ 
/* 382 */           if (!(record.isEmpty())) {
/* 383 */             sysId = (String)record.get("SYS_ID");
/*     */           }
/*     */         }
/*     */ 
/* 387 */         String txnCnl = root_rec.getChildValue("TXN_CNL");
/* 388 */         if (StringUtils.isNotBlank(txnCnl)) {
/* 389 */           String cnlSub = root_rec.getChildValue("CNL_SUB");
/* 390 */           String tlrId = null;
/*     */ 
/* 392 */           tlrId = HiAtcLib.sqnGetDumTlr(ctx, brNo, txnCnl, cnlSub);
/* 393 */           root_rec.setChildValue("TLR_ID", tlrId);
/*     */         }
/*     */ 
/* 396 */         root_rec.setChildValue("TBL_NM", batTbl);
/* 397 */         root_rec.setChildValue("UPD_FLG", batchInfo.upd_flg);
/* 398 */         root_rec.setChildValue("FSYS_ID", sysId);
/*     */ 
/* 400 */         newMsg = new HiMessage(this._serverName, msg.getType());
/*     */ 
/* 409 */         newMsg.setHeadItem("SCH", "rq");
/* 410 */         newMsg.setHeadItem("ECT", "text/etf");
/*     */ 
/* 413 */         newMsg.setBody(root_rec);
/* 414 */         BatchOnlineProcess bo = new BatchOnlineProcess();
/* 415 */         bo.setCurrentMsg(newMsg);
/*     */         while (true)
/*     */           try {
/* 418 */             futures.add(this._pool.submit(bo));
/*     */           }
/*     */           catch (RejectedExecutionException rje) {
/* 421 */             while (this._status != 1)
/*     */             {
/*     */               try
/*     */               {
/* 426 */                 Thread.sleep(200L);
/*     */               } catch (InterruptedException e) {
/* 428 */                 break label682:
/*     */               }
/*     */             }
/*     */           }
/* 350 */         ++i;
/*     */       }
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/* 435 */       cur.close();
/*     */     }
/*     */ 
/* 438 */     if (!(hasProcess)) {
/* 439 */       if (this._serverLog.isInfoEnabled()) {
/* 440 */         this._serverLog.info("磁盘文件编号[" + batchInfo.dsk_no + "]批次没有符合条件记录");
/*     */       }
/* 442 */       return;
/*     */     }
/*     */ 
/* 445 */     for (int i = 0; i < futures.size(); ++i) {
/*     */       try {
/* 447 */         ((Future)futures.get(i)).get(60L, TimeUnit.SECONDS);
/*     */       } catch (Exception e) {
/* 449 */         this._serverLog.error("等待线程结束异常", e);
/* 450 */         break label816:
/*     */       }
/*     */     }
/*     */ 
/* 454 */     if (this._serverLog.isInfoEnabled()) {
/* 455 */       this._serverLog.info("本次处理完毕!当前主机超时,系统错误数=[" + this._currTranNumOfOverTime + "],当前已用线程数=[" + (this._pool.getActiveCount() - 1) + "]");
/*     */     }
/*     */ 
/* 459 */     if (this._currTranNumOfOverTime != 0) {
/* 460 */       return;
/*     */     }
/*     */ 
/* 463 */     this._registerErrorFlag = false;
/* 464 */     if (StringUtils.equals(batchInfo.sum_flg, "Y"))
/*     */     {
/* 466 */       strSql = "SELECT CAST(COUNT(*) AS CHAR(8)) as SUC_CNT, CAST(SUM(CAST(TXN_AMT AS BIGINT)) AS CHAR(15)) as SUC_AMT FROM %s WHERE DSK_NO = '%s' AND IS_TXN = 'Y' AND (HTXN_STS = 'S' OR HRSP_CD = 'SC6128') ";
/*     */ 
/* 470 */       record = dbUtil.readRecord(strSql, batTbl, batchInfo.dsk_no);
/*     */ 
/* 472 */       if ((record != null) && (!(record.isEmpty()))) {
/* 473 */         String sucCnt = (String)record.get("SUC_CNT");
/* 474 */         String sucAmt = (String)record.get("SUC_AMT");
/* 475 */         if (StringUtils.isBlank(sucCnt))
/* 476 */           batchInfo.suc_cnt = "0";
/*     */         else {
/* 478 */           batchInfo.suc_cnt = sucCnt;
/*     */         }
/* 480 */         if (StringUtils.isBlank(sucAmt))
/* 481 */           batchInfo.suc_amt = "0";
/*     */         else {
/* 483 */           batchInfo.suc_amt = sucAmt;
/*     */         }
/* 485 */         strSql = "UPDATE pubbatinf SET CMT_FLG = 'C',SUC_CNT = '%s',SUC_AMT = '%s',FAL_CNT = CAST(CAST(ORN_CNT AS INT) - CAST(%s AS INT) AS CHAR(8)),FAL_AMT = CAST(CAST(ORN_AMT AS BIGINT) - CAST(%s AS BIGINT) AS CHAR(15)) WHERE  DSK_NO  = '%s'";
/*     */ 
/* 488 */         dbUtil.execUpdate(strSql, batchInfo.suc_cnt, batchInfo.suc_amt, batchInfo.suc_cnt, batchInfo.suc_amt, batchInfo.dsk_no);
/*     */ 
/* 490 */         dbUtil.commit();
/*     */       }
/*     */     } else {
/* 493 */       strSql = "UPDATE pubbatinf SET CMT_FLG = 'C' WHERE DSK_NO  = '%s'";
/* 494 */       dbUtil.execUpdate(strSql, batchInfo.dsk_no);
/* 495 */       dbUtil.commit();
/*     */     }
/*     */ 
/* 498 */     if ((StringUtils.isBlank(batchInfo.fTxn_Cd)) || (StringUtils.isBlank(batchInfo.obj_svr)))
/*     */     {
/* 500 */       return;
/*     */     }
/*     */ 
/* 503 */     HiTransJnl transJnl = new HiTransJnl();
/*     */ 
/* 505 */     transJnl.setTranId(batchInfo.dsk_no);
/* 506 */     if (this._serverType.equals("B"))
/* 507 */       transJnl.setSerNam("S.LBCSVR");
/*     */     else {
/* 509 */       transJnl.setSerNam("S.SBCSVR");
/*     */     }
/* 511 */     transJnl.setExpSer(batchInfo.fTxn_Cd);
/* 512 */     transJnl.setLogNo(batchInfo.dsk_no);
/* 513 */     transJnl.setItv(30);
/* 514 */     transJnl.setTmOut(30);
/* 515 */     transJnl.setTimOut(30);
/* 516 */     transJnl.setActDat(batchInfo.acc_dt);
/* 517 */     transJnl.setMaxTms(10);
/* 518 */     transJnl.register();
/*     */   }
/*     */ 
/*     */   private void registerError(HiMessageContext ctx, HiPubBatchInfo batchInfo)
/*     */     throws HiException
/*     */   {
/* 524 */     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
/*     */ 
/* 526 */     int snd_cnt = NumberUtils.toInt(batchInfo.snd_cnt) + 1;
/* 527 */     String sqlCmd = "UPDATE pubbatinf SET Snd_Cnt='%s', Lst_Tm='%s' WHERE BR_NO='%s' and Dsk_No='%s'";
/*     */     try {
/* 529 */       dbUtil.execUpdate(sqlCmd, String.valueOf(snd_cnt), String.valueOf(System.currentTimeMillis() / 1000L), batchInfo.br_no, batchInfo.dsk_no);
/*     */ 
/* 532 */       dbUtil.commit();
/*     */     } catch (Throwable e) {
/* 534 */       dbUtil.rollback();
/* 535 */       this._serverLog.error("registerError", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void sleep(int second, Logger log) {
/*     */     try {
/* 541 */       Thread.currentThread(); Thread.sleep(second * 1000);
/*     */     } catch (InterruptedException e) {
/* 543 */       log.error(e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setAttribOfServer(String AttribOfServer) throws HiException {
/* 548 */     if ((!(StringUtils.equals(AttribOfServer, "A"))) && (!(StringUtils.equals(AttribOfServer, "B"))))
/*     */     {
/* 550 */       throw new HiException("231414", AttribOfServer);
/*     */     }
/*     */ 
/* 553 */     this._serverType = AttribOfServer;
/*     */   }
/*     */ 
/*     */   public void setNumOfLevel(String NumOfLevel) throws HiException {
/* 557 */     this._level = Integer.parseInt(NumOfLevel);
/* 558 */     if ((this._level > 500) || (this._level < 200))
/* 559 */       throw new HiException("231416", NumOfLevel);
/*     */   }
/*     */ 
/*     */   public void setNumOfPthread(String NumOfPthread) throws HiException
/*     */   {
/* 564 */     this._numOfPthread = Integer.parseInt(NumOfPthread);
/* 565 */     if ((this._numOfPthread > 20) || (this._numOfPthread < 1))
/* 566 */       throw new HiException("231415", NumOfPthread);
/*     */   }
/*     */ 
/*     */   public void setTranNumOfOverTime(String TranNumOfOverTime)
/*     */     throws HiException
/*     */   {
/* 573 */     this._tranNumOfOverTime = Integer.parseInt(TranNumOfOverTime);
/* 574 */     if ((this._tranNumOfOverTime > 20) || (this._tranNumOfOverTime < 1))
/* 575 */       throw new HiException("231417", TranNumOfOverTime);
/*     */   }
/*     */ 
/*     */   public void serverInit(ServerEvent arg0)
/*     */     throws HiException
/*     */   {
/* 581 */     this._serverContext = arg0.getServerContext();
/* 582 */     if (DEFAULT_MIN_THREADS > this._numOfPthread) {
/* 583 */       this._numOfPthread = DEFAULT_MIN_THREADS;
/*     */     }
/* 585 */     this._pool = HiThreadPool.createThreadPool(DEFAULT_MIN_THREADS, this._numOfPthread, this._queueSize);
/*     */ 
/* 587 */     this._serverLog = arg0.getLog();
/* 588 */     this._serverName = arg0.getServerContext().getStrProp("SVR.name");
/*     */ 
/* 590 */     this._server = arg0.getServer();
/*     */   }
/*     */ 
/*     */   public void serverStart(ServerEvent arg0) throws HiException {
/* 594 */     this._pool.submit(new HiStartWork());
/*     */   }
/*     */ 
/*     */   public void serverStop(ServerEvent arg0) throws HiException {
/* 598 */     if (this._status != 0)
/* 599 */       return;
/* 600 */     this._status = 1;
/* 601 */     this._pool.shutdown();
/*     */     try {
/* 603 */       this._pool.awaitTermination(30L, TimeUnit.SECONDS);
/*     */     } catch (InterruptedException e) {
/* 605 */       this._serverLog.error("serverStop", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void serverResume(ServerEvent arg0) {
/* 610 */     if (this._status != 2)
/* 611 */       return;
/* 612 */     this._status = 0;
/*     */   }
/*     */ 
/*     */   public void serverPause(ServerEvent arg0) {
/* 616 */     if (this._status != 0)
/* 617 */       return;
/* 618 */     this._status = 2;
/*     */   }
/*     */ 
/*     */   public void serverDestroy(ServerEvent arg0) throws HiException {
/* 622 */     serverStop(arg0);
/*     */   }
/*     */ 
/*     */   public int getQueueSize()
/*     */   {
/* 871 */     return this._queueSize;
/*     */   }
/*     */ 
/*     */   public void setQueueSize(int size)
/*     */   {
/* 878 */     this._queueSize = size;
/*     */   }
/*     */ 
/*     */   public String getBrNo()
/*     */   {
/* 885 */     return this.brNo;
/*     */   }
/*     */ 
/*     */   public void setBrNo(String brNo)
/*     */   {
/* 892 */     this.brNo = brNo;
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
/* 662 */       this.msg = msg;
/*     */     }
/*     */ 
/*     */     public void run() {
/* 666 */       HiMessageContext ctx = new HiMessageContext();
/* 667 */       ctx.pushParent(HiBatchonlineHandler.this._serverContext);
/* 668 */       ctx.setCurrentMsg(this.msg);
/* 669 */       HiMessageContext.setCurrentMessageContext(ctx);
/*     */       try {
/* 671 */         process(ctx);
/*     */       } catch (Throwable t) {
/* 673 */         HiBatchonlineHandler.this._serverLog.error(t, t);
/*     */       } finally {
/* 675 */         if (ctx.getDataBaseUtil() != null)
/* 676 */           ctx.getDataBaseUtil().closeAll();
/*     */       }
/*     */     }
/*     */ 
/*     */     private int process(HiMessageContext ctx)
/*     */     {
/* 682 */       String logNo = null; String updFlg = null; String stmt1 = null; String batTbl = null;
/* 683 */       String aHTxnSt = null; String hLogNo = null; String hRspCd = null; String tlrId = null;
/* 684 */       HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
/*     */ 
/* 686 */       HiETF etf = this.msg.getETFBody();
/* 687 */       HiETF root_o = null;
/* 688 */       Logger log = HiLog.getLogger(this.msg);
/*     */       try {
/* 690 */         for (int i = 0; i < 1; ++i) {
/* 691 */           logNo = etf.getChildValue("LOG_NO");
/* 692 */           if (logNo == null) {
/* 693 */             log.warn("760031,找不到节点[LOG_NO]!");
/* 694 */             aHTxnSt = "E";
/* 695 */             break;
/*     */           }
/* 697 */           batTbl = etf.getChildValue("TBL_NM");
/* 698 */           if (batTbl == null) {
/* 699 */             log.warn("760031,找不到节点[TBL_NM]!");
/* 700 */             aHTxnSt = "E";
/* 701 */             break;
/*     */           }
/*     */ 
/* 704 */           tlrId = etf.getChildValue("TLR_ID");
/* 705 */           if (tlrId == null) {
/* 706 */             log.warn("760031,找不到节点[TLR_ID]!");
/* 707 */             aHTxnSt = "E";
/* 708 */             break;
/*     */           }
/*     */ 
/* 711 */           String aTxnCod = etf.getChildValue("HTXN_CD");
/* 712 */           if (aTxnCod == null) {
/* 713 */             log.warn("760031,找不到节点[HTXN_CD]!");
/* 714 */             aHTxnSt = "E";
/* 715 */             break;
/*     */           }
/* 717 */           this.msg.setHeadItem("STC", aTxnCod);
/* 718 */           updFlg = etf.getChildValue("UPD_FLG");
/* 719 */           if (updFlg == null) {
/* 720 */             log.warn("760031,找不到节点[UPD_FLG]!");
/* 721 */             aHTxnSt = "E";
/* 722 */             break;
/*     */           }
/* 724 */           String aTxnObj = etf.getChildValue("TXN_OBJ");
/* 725 */           if (StringUtils.isNotEmpty(aTxnObj)) {
/* 726 */             this.msg.setHeadItem("SDT", aTxnObj);
/*     */           }
/*     */ 
/* 729 */           if (log.isInfoEnabled()) {
/* 730 */             log.info("LOG_NO:[" + logNo + "], TLR_ID:[" + tlrId + "], HTXN_CD[:[" + aTxnCod + "], UPD_FLG[" + updFlg + "], TXN_OBJ:[" + aTxnObj + "]");
/*     */           }
/*     */ 
/*     */           try
/*     */           {
/* 736 */             this.msg.setHeadItem("STM", new Long(System.currentTimeMillis()));
/*     */ 
/* 738 */             this.msg = HiRouterOut.syncProcess(this.msg);
/* 739 */             HiMessageContext.setCurrentMessageContext(ctx);
/* 740 */             ctx.setCurrentMsg(this.msg);
/*     */           } catch (HiException e) {
/* 742 */             log.warn("调主机交易错误!", e);
/* 743 */             aHTxnSt = "E";
/* 744 */             ctx.setCurrentMsg(this.msg);
/* 745 */             break label590:
/*     */           }
/* 747 */           log = HiLog.getLogger(this.msg);
/* 748 */           String aError = this.msg.getHeadItem("SSC");
/* 749 */           if ((StringUtils.isNotEmpty(aError)) && (!(StringUtils.equals("000000", aError))))
/*     */           {
/* 751 */             log.warn("760031,前置错误,此交易失败:[" + aError + "]");
/* 752 */             aHTxnSt = "E";
/* 753 */             break;
/*     */           }
/*     */ 
/* 756 */           root_o = this.msg.getETFBody();
/* 757 */           aHTxnSt = root_o.getChildValue("MSG_TYP");
/* 758 */           if (aHTxnSt == null) {
/* 759 */             log.warn("760031,找不到节点[MSG_TYP]!");
/* 760 */             aHTxnSt = "E";
/* 761 */             break;
/*     */           }
/*     */ 
/* 764 */           if (StringUtils.equals("N", aHTxnSt)) {
/* 765 */             aHTxnSt = "S";
/*     */           }
/*     */ 
/* 768 */           if (StringUtils.equals("E", aHTxnSt)) {
/* 769 */             aHTxnSt = "F";
/*     */           }
/*     */ 
/* 772 */           hRspCd = root_o.getChildValue("HRSP_CD");
/* 773 */           if (hRspCd == null) {
/* 774 */             log.warn("760031,找不到节点[HRSP_CD]!");
/* 775 */             aHTxnSt = "E";
/* 776 */             break;
/*     */           }
/*     */ 
/* 779 */           hLogNo = root_o.getChildValue("HLOG_NO");
/* 780 */           if (hLogNo == null) {
/* 781 */             hLogNo = "         ";
/*     */           }
/*     */         }
/* 784 */         for (i = 0; i < 1; ++i) {
/* 785 */           if ((!(StringUtils.equalsIgnoreCase(aHTxnSt, "S"))) && (!(StringUtils.equalsIgnoreCase(aHTxnSt, "F"))))
/*     */           {
/* 788 */             synchronized (HiBatchonlineHandler.this._lock) {
/* 789 */               label590: HiBatchonlineHandler.access$508(HiBatchonlineHandler.this);
/*     */             }
/*     */           }
/*     */ 
/* 793 */           if (logNo == null) {
/*     */             break;
/*     */           }
/* 796 */           String condition = null;
/*     */ 
/* 802 */           if (StringUtils.equals(updFlg, "Y")) {
/* 803 */             condition = bpCreateCondition(etf, root_o, log, ctx);
/*     */           }
/* 805 */           if (StringUtils.isNotEmpty(condition)) {
/* 806 */             stmt1 = "UPDATE %s SET HTXN_STS = '%s', HLOG_NO = '%s', HRSP_CD = '%s', TLR_ID='%s', %s WHERE LOG_NO = '%s'";
/* 807 */             stmt1 = HiStringUtils.format(stmt1, new String[] { batTbl, aHTxnSt, hLogNo, hRspCd, tlrId, condition, logNo });
/*     */           }
/*     */           else
/*     */           {
/* 811 */             stmt1 = "UPDATE %s SET TXN_STS='%s',HTXN_STS = '%s', HLOG_NO = '%s', HRSP_CD = '%s', TLR_ID='%s' WHERE LOG_NO = '%s'";
/* 812 */             stmt1 = HiStringUtils.format(stmt1, new String[] { batTbl, aHTxnSt, aHTxnSt, hLogNo, hRspCd, tlrId, logNo });
/*     */           }
/*     */ 
/* 816 */           if (log.isInfoEnabled())
/* 817 */             log.info("batchOnline-stmt1=[" + stmt1 + "]");
/*     */           try
/*     */           {
/* 820 */             dbUtil.execUpdate(stmt1);
/* 821 */             dbUtil.commit();
/*     */           } catch (HiException e) {
/* 823 */             log.warn("batchOnline-stmt1 失败", e);
/*     */             try {
/* 825 */               dbUtil.rollback();
/*     */             } catch (Exception e1) {
/*     */             }
/*     */           }
/*     */         }
/*     */       } finally {
/* 831 */         log.close();
/* 832 */         HiMessageContext.removeCurrentContext();
/*     */       }
/* 834 */       return 0;
/*     */     }
/*     */ 
/*     */     private String bpCreateCondition(HiETF root_i, HiETF root_o, Logger log, HiMessageContext ctx)
/*     */     {
/* 839 */       String strFields = root_o.getChildValue("UPD_FLD");
/* 840 */       if (StringUtils.isBlank(strFields)) {
/* 841 */         log.warn("760031,找不到节点[UPD_FLD]!");
/* 842 */         return null;
/*     */       }
/* 844 */       String[] fields = StringUtils.split(strFields, "|");
/*     */ 
/* 846 */       StringBuffer value = new StringBuffer();
/* 847 */       for (int i = 0; i < fields.length; ++i) {
/* 848 */         if (fields[i].indexOf("=") > 0) {
/* 849 */           if (i > 0) {
/* 850 */             value.append(",");
/*     */           }
/* 852 */           value.append(fields[i]);
/*     */         } else {
/* 854 */           if (i > 0) {
/* 855 */             value.append(",");
/*     */           }
/* 857 */           String v = (String)ctx.getSpecExpre(root_o, fields[i]);
/* 858 */           if (v == null)
/* 859 */             v = "";
/* 860 */           value.append(fields[i] + "='" + v + "'");
/*     */         }
/*     */       }
/* 863 */       return value.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */   class HiStartWork
/*     */     implements Runnable
/*     */   {
/*     */     public void run()
/*     */     {
/* 628 */       HiMessageContext ctx = new HiMessageContext();
/*     */       try {
/* 630 */         ctx.pushParent(HiBatchonlineHandler.this._serverContext);
/* 631 */         HiMessage msg = new HiMessage(HiBatchonlineHandler.this._serverName, "PLTIN0");
/* 632 */         ctx.setCurrentMsg(msg);
/* 633 */         HiMessageContext.setCurrentContext(ctx);
/* 634 */         HiBatchonlineHandler.this._server.process(ctx);
/*     */       }
/*     */       catch (Throwable t) {
/* 637 */         HiBatchonlineHandler.this._serverLog.error(t, t);
/*     */       } finally {
/* 639 */         if (ctx.getDataBaseUtil() != null)
/* 640 */           ctx.getDataBaseUtil().closeAll();
/*     */       }
/*     */     }
/*     */   }
/*     */ }