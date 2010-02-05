package com.hisun.handler;

import com.hisun.atc.HiTransJnl;
import com.hisun.atc.bat.HiPubBatchInfo;
import com.hisun.atc.common.HiAtcLib;
import com.hisun.atc.common.HiDBCursor;
import com.hisun.atc.common.HiDbtUtils;
import com.hisun.database.HiDataBaseUtil;
import com.hisun.dispatcher.HiRouterOut;
import com.hisun.exception.HiException;
import com.hisun.framework.IServer;
import com.hisun.framework.event.*;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.*;
import com.hisun.pubinterface.IHandler;
import com.hisun.util.HiStringUtils;
import com.hisun.util.HiThreadPool;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

public class HiBatchonlineHandler implements IHandler, IServerInitListener, IServerStartListener, IServerStopListener, IServerPauseListener, IServerDestroyListener, IServerResumeListener {
    public static int DEFAULT_MIN_THREADS = 5;
    public static int DEFAULT_MAX_THREADS = 20;
    public static int DEFAULT_MAX_SEND_COUNT = 99;
    public static final String BAT_HOST_MSGTYP = "PLTIN0";
    public static final int NON_STATUS = -1;
    public static final int RUN_STATUS = 0;
    public static final int STOP_STATUS = 1;
    public static final int PAUSE_STATUS = 2;
    private String _serverType;
    private int _level;
    private int _numOfPthread;
    private int _queueSize;
    private IServer _server;
    private int _tranNumOfOverTime;
    private HiThreadPool _pool;
    private int _currTranNumOfOverTime;
    private Logger _serverLog;
    private int _maxSendCount;
    private String _serverName;
    private Object _lock;
    private int _status;
    private boolean _registerErrorFlag;
    private HiContext _serverContext;
    private String brNo;

    public HiBatchonlineHandler() {
        this._queueSize = 1000;

        this._currTranNumOfOverTime = 0;

        this._maxSendCount = DEFAULT_MAX_SEND_COUNT;

        this._lock = new Object();

        this._status = -1;
        this._registerErrorFlag = false;

        this.brNo = "";
    }

    public void process(HiMessageContext ctx) throws HiException {
        long lastTime = 0L;
        long nowTime = 0L;

        String actDat = null;
        String sysId = null;
        HiPubBatchInfo batchInfo = new HiPubBatchInfo();
        HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
        HashMap record = null;
        if (this._status != -1) return;
        this._status = 0;
        if (this._serverLog.isInfoEnabled()) this._serverLog.info("批量发送通道处理开始");
        while (true) {
            if (this._status == 2) {
                sleep(2, this._serverLog);
            }
            if (this._status == 1) {
                break;
            }

            nowTime = System.currentTimeMillis() / 1000L;

            if ((nowTime - lastTime > 60L) || (lastTime == 0L)) {
                String strSql = "SELECT ACC_DT, SYS_ID FROM pubpltinf";
                record = dbUtil.readRecord(strSql);
                if (record.isEmpty()) {
                    this._serverLog.warn("760009,取会计日期失败,稍后再试!");
                    sleep(2, this._serverLog);
                }

                actDat = (String) record.get("ACC_DT");
                sysId = (String) record.get("SYS_ID");
                lastTime = nowTime;
            }

            if (this._serverType.equals("B")) checkServerTypeForB(dbUtil, actDat, this.brNo);
            else {
                checkServerTypeForA(dbUtil, actDat, this.brNo);
            }

            try {
                record = dbUtil.readRecord("SELECT * FROM PUBBATINF WHERE (BR_NO='%s' or '%s'='') and CMT_FLG = '%s'  AND ROWNUM=1 ORDER BY Lst_Tm ", this.brNo, this.brNo, this._serverType);
            } catch (Throwable e) {
                while (true) {
                    this._serverLog.error("760012,查找待处理的批次时SELECT pubbatinf失败", e);
                    sleep(5, this._serverLog);
                }
            }

            if (record.isEmpty()) {
                sleep(5, this._serverLog);
            }

            if (this._serverLog.isDebugEnabled()) {
                this._serverLog.debug("pubbatinf:[" + record + "]");
            }

            batchInfo.setValuesFromMap(record);
            if (!(StringUtils.equals(actDat, batchInfo.acc_dt))) {
                this._serverLog.warn("760010, 原磁盘文件编号[" + batchInfo.dsk_no + "]因主机换日[" + actDat + "]被迫中止!");
                try {
                    dbUtil.execUpdate("UPDATE pubbatinf SET CMT_FLG = 'S' WHERE BR_NO='%s' and DSK_NO = '%s'", batchInfo.br_no, batchInfo.br_no, batchInfo.dsk_no);

                    dbUtil.commit();
                } catch (Throwable e) {
                    this._serverLog.error("760011,停止因主机换日而终止的批次时UPDATE pubbatinf失败", e);

                    dbUtil.rollback();
                }
                sleep(5, this._serverLog);
            }
            if (NumberUtils.toInt(batchInfo.snd_cnt) >= this._maxSendCount) {
                this._serverLog.warn("760046,原磁盘文件编号[" + batchInfo.dsk_no + "]发送次数已经到达最大值[" + this._maxSendCount + "]被迫中止!");
                try {
                    dbUtil.execUpdate("UPDATE pubbatinf SET CMT_FLG = 'S' WHERE BR_NO='%s' and DSK_NO = '%s'", batchInfo.br_no, batchInfo.dsk_no);

                    dbUtil.commit();
                } catch (Throwable e) {
                    this._serverLog.error("760011,停止因主机换日而终止的批次时UPDATE pubbatinf失败", e);

                    dbUtil.rollback();
                }
                sleep(5, this._serverLog);
            }

            try {
                sendRecord(ctx, batchInfo, sysId);
                dbUtil.commit();
            } catch (Throwable e) {
                dbUtil.rollback();
                this._serverLog.warn("处理磁盘文件编号=[" + batchInfo.dsk_no + "]的批次失败", e);
            }
            if (this._registerErrorFlag) {
                registerError(ctx, batchInfo);
            }
            sleep(5, this._serverLog);
            this._currTranNumOfOverTime = 0;
        }
        this._status = -1;
    }

    private int checkServerTypeForB(HiDataBaseUtil dbUtil, String actDat, String brNo) throws HiException {
        int ret = 0;
        try {
            ret = dbUtil.execUpdate("UPDATE pubbatinf SET CMT_FLG = 'B' WHERE (BR_NO='%s' or '%s'='') and DSK_NO IN (SELECT  min(DSK_NO) FROM pubbatinf WHERE ACC_DT = '%s' AND CMT_FLG = '0' AND TXN_MOD = '1' AND CHK_FLG = '1' AND CAST(ORN_CNT AS INT) >= %s)", brNo, brNo, actDat, String.valueOf(this._level));

            if (ret == 0) {
                ret = dbUtil.execUpdate("UPDATE pubbatinf SET CMT_FLG = 'B' WHERE (BR_NO='%s' or '%s'='') and DSK_NO IN (SELECT  min(DSK_NO) FROM pubbatinf WHERE ACC_DT = '%s' AND CMT_FLG = '0' AND TXN_MOD = '1' AND CHK_FLG = '1' AND CAST(ORN_CNT AS INT) < %s)", brNo, brNo, actDat, String.valueOf(this._level));
            }

            dbUtil.commit();
        } catch (Throwable e) {
            this._serverLog.error("大通道正在检查符合条件的批次记录", e);
            dbUtil.rollback();
            sleep(10, this._serverLog);
        }

        if ((ret != 0) && (this._serverLog.isInfoEnabled())) {
            this._serverLog.info("找到符合条件的一个小批次,将由大通道处理!");
        }
        return ret;
    }

    private int checkServerTypeForA(HiDataBaseUtil dbUtil, String actDat, String brNo) throws HiException {
        int ret = 0;
        try {
            ret = dbUtil.execUpdate("UPDATE pubbatinf SET CMT_FLG = 'A' WHERE (BR_NO='%s' or '%s'='') and DSK_NO IN (SELECT  min(DSK_NO) FROM pubbatinf WHERE ACC_DT = '%s' AND CMT_FLG = '0' AND TXN_MOD = '1'  AND CHK_FLG = '1' AND CAST(ORN_CNT AS INT) < %s)", brNo, brNo, actDat, String.valueOf(this._level));

            dbUtil.commit();
        } catch (Throwable e) {
            this._serverLog.error("小通道正在检查符合条件的批次记录", e);
            dbUtil.rollback();
            sleep(10, this._serverLog);
        }
        if ((ret != 0) && (this._serverLog.isInfoEnabled())) {
            this._serverLog.info("找到符合条件的一个小批次,将由小通道处理!");
        }

        return ret;
    }

    private void sendRecord(HiMessageContext ctx, HiPubBatchInfo batchInfo, String sysId) throws HiException {
        label682:
        label816:
        String strSql;
        HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
        this._registerErrorFlag = true;
        if (this._serverType.equals("A")) {
            if (this._serverLog.isInfoEnabled()) {
                this._serverLog.info("小通道开始处理批次号=[" + batchInfo.dsk_no + "]的批次");
            }
        } else if (this._serverLog.isInfoEnabled()) {
            this._serverLog.info("大通道开始处理批次号=[" + batchInfo.dsk_no + "]的批次");
        }

        String sqlCmd = "SELECT SYS_ID FROM pubbchlst WHERE BR_NO='%s'";

        HashMap record = null;
        record = dbUtil.readRecord(sqlCmd, batchInfo.br_no);

        if ((record != null) && (!(record.isEmpty()))) {
            sysId = (String) record.get("SYS_ID");
        }

        String batTbl = batchInfo.trd_tbl;
        if (StringUtils.isBlank(batTbl)) {
            String strSql = "SELECT A.BAT_TBL FROM pubjnldef A, pubbatinf B WHERE B.DSK_NO = '%s'AND A.BR_NO = B.BR_NO AND A.BUS_TYP = B.BUS_TYP AND A.CRP_CD = B.CRP_CD";

            record = dbUtil.readRecord(strSql, batchInfo.dsk_no);
            batTbl = (String) record.get("BAT_TBL");
            if (StringUtils.isBlank(batTbl)) {
                this._serverLog.warn("DSK_NO=[" + batchInfo.dsk_no + "]没有在pubjnldef中配置对应的批量业务流水表名!");

                return;
            }
        }

        String stmt = HiStringUtils.format("SELECT * FROM %s WHERE DSK_NO = '%s' AND (HTXN_STS = 'U' OR HTXN_STS = 'E') AND IS_TXN = 'Y'", batTbl, batchInfo.dsk_no);

        HiMessage msg = ctx.getCurrentMsg();
        HiDBCursor cur = null;
        cur = HiDbtUtils.dbtsqlcursor(stmt, "O", null, msg.getETFBody(), ctx);
        HiETF root_rec = null;

        HiMessage newMsg = null;
        ArrayList futures = new ArrayList();
        boolean hasProcess = false;
        try {
            i = 1;
            while (this._status != 1) {
                if (this._currTranNumOfOverTime >= this._tranNumOfOverTime) {
                    this._serverLog.warn("760020,达到规定的超时,系统错误交易笔数退出");
                    break;
                }

                root_rec = HiETFFactory.createETF();
                cur = HiDbtUtils.dbtsqlcursor(stmt, "F", cur, root_rec, ctx);

                if (cur.ret == 100) {
                    break;
                }
                hasProcess = true;
                if (this._serverLog.isInfoEnabled()) {
                    this._serverLog.info("760021, 处理磁盘文件编号[" + batchInfo.dsk_no + "]批次，第[" + i + "]笔");
                }

                String brNo = root_rec.getChildValue("BR_NO");
                if (StringUtils.isBlank(brNo)) {
                    brNo = batchInfo.br_no;
                } else {
                    record = dbUtil.readRecord("SELECT SYS_ID FROM pubbchlst WHERE BR_NO = '%s'", brNo);

                    if (!(record.isEmpty())) {
                        sysId = (String) record.get("SYS_ID");
                    }
                }

                String txnCnl = root_rec.getChildValue("TXN_CNL");
                if (StringUtils.isNotBlank(txnCnl)) {
                    String cnlSub = root_rec.getChildValue("CNL_SUB");
                    String tlrId = null;

                    tlrId = HiAtcLib.sqnGetDumTlr(ctx, brNo, txnCnl, cnlSub);
                    root_rec.setChildValue("TLR_ID", tlrId);
                }

                root_rec.setChildValue("TBL_NM", batTbl);
                root_rec.setChildValue("UPD_FLG", batchInfo.upd_flg);
                root_rec.setChildValue("FSYS_ID", sysId);

                newMsg = new HiMessage(this._serverName, msg.getType());

                newMsg.setHeadItem("SCH", "rq");
                newMsg.setHeadItem("ECT", "text/etf");

                newMsg.setBody(root_rec);
                BatchOnlineProcess bo = new BatchOnlineProcess();
                bo.setCurrentMsg(newMsg);
                while (true) try {
                    futures.add(this._pool.submit(bo));
                } catch (RejectedExecutionException rje) {
                    while (this._status != 1) {
                        try {
                            Thread.sleep(200L);
                        } catch (InterruptedException e) {
                            break label682:
                        }
                    }
                }
                ++i;
            }

        } finally {
            cur.close();
        }

        if (!(hasProcess)) {
            if (this._serverLog.isInfoEnabled()) {
                this._serverLog.info("磁盘文件编号[" + batchInfo.dsk_no + "]批次没有符合条件记录");
            }
            return;
        }

        for (int i = 0; i < futures.size(); ++i) {
            try {
                ((Future) futures.get(i)).get(60L, TimeUnit.SECONDS);
            } catch (Exception e) {
                this._serverLog.error("等待线程结束异常", e);
                break label816:
            }
        }

        if (this._serverLog.isInfoEnabled()) {
            this._serverLog.info("本次处理完毕!当前主机超时,系统错误数=[" + this._currTranNumOfOverTime + "],当前已用线程数=[" + (this._pool.getActiveCount() - 1) + "]");
        }

        if (this._currTranNumOfOverTime != 0) {
            return;
        }

        this._registerErrorFlag = false;
        if (StringUtils.equals(batchInfo.sum_flg, "Y")) {
            strSql = "SELECT CAST(COUNT(*) AS CHAR(8)) as SUC_CNT, CAST(SUM(CAST(TXN_AMT AS BIGINT)) AS CHAR(15)) as SUC_AMT FROM %s WHERE DSK_NO = '%s' AND IS_TXN = 'Y' AND (HTXN_STS = 'S' OR HRSP_CD = 'SC6128') ";

            record = dbUtil.readRecord(strSql, batTbl, batchInfo.dsk_no);

            if ((record != null) && (!(record.isEmpty()))) {
                String sucCnt = (String) record.get("SUC_CNT");
                String sucAmt = (String) record.get("SUC_AMT");
                if (StringUtils.isBlank(sucCnt)) batchInfo.suc_cnt = "0";
                else {
                    batchInfo.suc_cnt = sucCnt;
                }
                if (StringUtils.isBlank(sucAmt)) batchInfo.suc_amt = "0";
                else {
                    batchInfo.suc_amt = sucAmt;
                }
                strSql = "UPDATE pubbatinf SET CMT_FLG = 'C',SUC_CNT = '%s',SUC_AMT = '%s',FAL_CNT = CAST(CAST(ORN_CNT AS INT) - CAST(%s AS INT) AS CHAR(8)),FAL_AMT = CAST(CAST(ORN_AMT AS BIGINT) - CAST(%s AS BIGINT) AS CHAR(15)) WHERE  DSK_NO  = '%s'";

                dbUtil.execUpdate(strSql, batchInfo.suc_cnt, batchInfo.suc_amt, batchInfo.suc_cnt, batchInfo.suc_amt, batchInfo.dsk_no);

                dbUtil.commit();
            }
        } else {
            strSql = "UPDATE pubbatinf SET CMT_FLG = 'C' WHERE DSK_NO  = '%s'";
            dbUtil.execUpdate(strSql, batchInfo.dsk_no);
            dbUtil.commit();
        }

        if ((StringUtils.isBlank(batchInfo.fTxn_Cd)) || (StringUtils.isBlank(batchInfo.obj_svr))) {
            return;
        }

        HiTransJnl transJnl = new HiTransJnl();

        transJnl.setTranId(batchInfo.dsk_no);
        if (this._serverType.equals("B")) transJnl.setSerNam("S.LBCSVR");
        else {
            transJnl.setSerNam("S.SBCSVR");
        }
        transJnl.setExpSer(batchInfo.fTxn_Cd);
        transJnl.setLogNo(batchInfo.dsk_no);
        transJnl.setItv(30);
        transJnl.setTmOut(30);
        transJnl.setTimOut(30);
        transJnl.setActDat(batchInfo.acc_dt);
        transJnl.setMaxTms(10);
        transJnl.register();
    }

    private void registerError(HiMessageContext ctx, HiPubBatchInfo batchInfo) throws HiException {
        HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();

        int snd_cnt = NumberUtils.toInt(batchInfo.snd_cnt) + 1;
        String sqlCmd = "UPDATE pubbatinf SET Snd_Cnt='%s', Lst_Tm='%s' WHERE BR_NO='%s' and Dsk_No='%s'";
        try {
            dbUtil.execUpdate(sqlCmd, String.valueOf(snd_cnt), String.valueOf(System.currentTimeMillis() / 1000L), batchInfo.br_no, batchInfo.dsk_no);

            dbUtil.commit();
        } catch (Throwable e) {
            dbUtil.rollback();
            this._serverLog.error("registerError", e);
        }
    }

    private void sleep(int second, Logger log) {
        try {
            Thread.currentThread();
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            log.error(e, e);
        }
    }

    public void setAttribOfServer(String AttribOfServer) throws HiException {
        if ((!(StringUtils.equals(AttribOfServer, "A"))) && (!(StringUtils.equals(AttribOfServer, "B")))) {
            throw new HiException("231414", AttribOfServer);
        }

        this._serverType = AttribOfServer;
    }

    public void setNumOfLevel(String NumOfLevel) throws HiException {
        this._level = Integer.parseInt(NumOfLevel);
        if ((this._level > 500) || (this._level < 200)) throw new HiException("231416", NumOfLevel);
    }

    public void setNumOfPthread(String NumOfPthread) throws HiException {
        this._numOfPthread = Integer.parseInt(NumOfPthread);
        if ((this._numOfPthread > 20) || (this._numOfPthread < 1)) throw new HiException("231415", NumOfPthread);
    }

    public void setTranNumOfOverTime(String TranNumOfOverTime) throws HiException {
        this._tranNumOfOverTime = Integer.parseInt(TranNumOfOverTime);
        if ((this._tranNumOfOverTime > 20) || (this._tranNumOfOverTime < 1))
            throw new HiException("231417", TranNumOfOverTime);
    }

    public void serverInit(ServerEvent arg0) throws HiException {
        this._serverContext = arg0.getServerContext();
        if (DEFAULT_MIN_THREADS > this._numOfPthread) {
            this._numOfPthread = DEFAULT_MIN_THREADS;
        }
        this._pool = HiThreadPool.createThreadPool(DEFAULT_MIN_THREADS, this._numOfPthread, this._queueSize);

        this._serverLog = arg0.getLog();
        this._serverName = arg0.getServerContext().getStrProp("SVR.name");

        this._server = arg0.getServer();
    }

    public void serverStart(ServerEvent arg0) throws HiException {
        this._pool.submit(new HiStartWork());
    }

    public void serverStop(ServerEvent arg0) throws HiException {
        if (this._status != 0) return;
        this._status = 1;
        this._pool.shutdown();
        try {
            this._pool.awaitTermination(30L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            this._serverLog.error("serverStop", e);
        }
    }

    public void serverResume(ServerEvent arg0) {
        if (this._status != 2) return;
        this._status = 0;
    }

    public void serverPause(ServerEvent arg0) {
        if (this._status != 0) return;
        this._status = 2;
    }

    public void serverDestroy(ServerEvent arg0) throws HiException {
        serverStop(arg0);
    }

    public int getQueueSize() {
        return this._queueSize;
    }

    public void setQueueSize(int size) {
        this._queueSize = size;
    }

    public String getBrNo() {
        return this.brNo;
    }

    public void setBrNo(String brNo) {
        this.brNo = brNo;
    }

    class BatchOnlineProcess implements Runnable {
        public static final String HOST_TRAN_SUCCESS = "N";
        public static final String HOST_TRAN_FAIL = "E";
        public static final String HOST_TRAN_CANCEL = "C";
        public static final String HOST_TRAN_REVERSE = "R";
        public static final String TRAN_SUCCESS = "S";
        public static final String TRAN_FAIL = "F";
        public static final String TRAN_CANCEL = "C";
        public static final String TRAN_REVERSE = "R";
        public static final String TRAN_UNKNOW = "U";
        public static final String TRAN_ERROR = "E";
        private HiMessage msg;

        void setCurrentMsg(HiMessage msg) {
            this.msg = msg;
        }

        public void run() {
            HiMessageContext ctx = new HiMessageContext();
            ctx.pushParent(HiBatchonlineHandler.this._serverContext);
            ctx.setCurrentMsg(this.msg);
            HiMessageContext.setCurrentMessageContext(ctx);
            try {
                process(ctx);
            } catch (Throwable t) {
                HiBatchonlineHandler.this._serverLog.error(t, t);
            } finally {
                if (ctx.getDataBaseUtil() != null) ctx.getDataBaseUtil().closeAll();
            }
        }

        private int process(HiMessageContext ctx) {
            String logNo = null;
            String updFlg = null;
            String stmt1 = null;
            String batTbl = null;
            String aHTxnSt = null;
            String hLogNo = null;
            String hRspCd = null;
            String tlrId = null;
            HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();

            HiETF etf = this.msg.getETFBody();
            HiETF root_o = null;
            Logger log = HiLog.getLogger(this.msg);
            try {
                for (int i = 0; i < 1; ++i) {
                    logNo = etf.getChildValue("LOG_NO");
                    if (logNo == null) {
                        log.warn("760031,找不到节点[LOG_NO]!");
                        aHTxnSt = "E";
                        break;
                    }
                    batTbl = etf.getChildValue("TBL_NM");
                    if (batTbl == null) {
                        log.warn("760031,找不到节点[TBL_NM]!");
                        aHTxnSt = "E";
                        break;
                    }

                    tlrId = etf.getChildValue("TLR_ID");
                    if (tlrId == null) {
                        log.warn("760031,找不到节点[TLR_ID]!");
                        aHTxnSt = "E";
                        break;
                    }

                    String aTxnCod = etf.getChildValue("HTXN_CD");
                    if (aTxnCod == null) {
                        log.warn("760031,找不到节点[HTXN_CD]!");
                        aHTxnSt = "E";
                        break;
                    }
                    this.msg.setHeadItem("STC", aTxnCod);
                    updFlg = etf.getChildValue("UPD_FLG");
                    if (updFlg == null) {
                        log.warn("760031,找不到节点[UPD_FLG]!");
                        aHTxnSt = "E";
                        break;
                    }
                    String aTxnObj = etf.getChildValue("TXN_OBJ");
                    if (StringUtils.isNotEmpty(aTxnObj)) {
                        this.msg.setHeadItem("SDT", aTxnObj);
                    }

                    if (log.isInfoEnabled()) {
                        log.info("LOG_NO:[" + logNo + "], TLR_ID:[" + tlrId + "], HTXN_CD[:[" + aTxnCod + "], UPD_FLG[" + updFlg + "], TXN_OBJ:[" + aTxnObj + "]");
                    }

                    try {
                        this.msg.setHeadItem("STM", new Long(System.currentTimeMillis()));

                        this.msg = HiRouterOut.syncProcess(this.msg);
                        HiMessageContext.setCurrentMessageContext(ctx);
                        ctx.setCurrentMsg(this.msg);
                    } catch (HiException e) {
                        log.warn("调主机交易错误!", e);
                        aHTxnSt = "E";
                        ctx.setCurrentMsg(this.msg);
                        break label590:
                    }
                    log = HiLog.getLogger(this.msg);
                    String aError = this.msg.getHeadItem("SSC");
                    if ((StringUtils.isNotEmpty(aError)) && (!(StringUtils.equals("000000", aError)))) {
                        log.warn("760031,前置错误,此交易失败:[" + aError + "]");
                        aHTxnSt = "E";
                        break;
                    }

                    root_o = this.msg.getETFBody();
                    aHTxnSt = root_o.getChildValue("MSG_TYP");
                    if (aHTxnSt == null) {
                        log.warn("760031,找不到节点[MSG_TYP]!");
                        aHTxnSt = "E";
                        break;
                    }

                    if (StringUtils.equals("N", aHTxnSt)) {
                        aHTxnSt = "S";
                    }

                    if (StringUtils.equals("E", aHTxnSt)) {
                        aHTxnSt = "F";
                    }

                    hRspCd = root_o.getChildValue("HRSP_CD");
                    if (hRspCd == null) {
                        log.warn("760031,找不到节点[HRSP_CD]!");
                        aHTxnSt = "E";
                        break;
                    }

                    hLogNo = root_o.getChildValue("HLOG_NO");
                    if (hLogNo == null) {
                        hLogNo = "         ";
                    }
                }
                for (i = 0; i < 1; ++i) {
                    if ((!(StringUtils.equalsIgnoreCase(aHTxnSt, "S"))) && (!(StringUtils.equalsIgnoreCase(aHTxnSt, "F")))) {
                        synchronized (HiBatchonlineHandler.this._lock) {
                            label590:
                            HiBatchonlineHandler.access$508(HiBatchonlineHandler.this);
                        }
                    }

                    if (logNo == null) {
                        break;
                    }
                    String condition = null;

                    if (StringUtils.equals(updFlg, "Y")) {
                        condition = bpCreateCondition(etf, root_o, log, ctx);
                    }
                    if (StringUtils.isNotEmpty(condition)) {
                        stmt1 = "UPDATE %s SET HTXN_STS = '%s', HLOG_NO = '%s', HRSP_CD = '%s', TLR_ID='%s', %s WHERE LOG_NO = '%s'";
                        stmt1 = HiStringUtils.format(stmt1, new String[]{batTbl, aHTxnSt, hLogNo, hRspCd, tlrId, condition, logNo});
                    } else {
                        stmt1 = "UPDATE %s SET TXN_STS='%s',HTXN_STS = '%s', HLOG_NO = '%s', HRSP_CD = '%s', TLR_ID='%s' WHERE LOG_NO = '%s'";
                        stmt1 = HiStringUtils.format(stmt1, new String[]{batTbl, aHTxnSt, aHTxnSt, hLogNo, hRspCd, tlrId, logNo});
                    }

                    if (log.isInfoEnabled()) log.info("batchOnline-stmt1=[" + stmt1 + "]");
                    try {
                        dbUtil.execUpdate(stmt1);
                        dbUtil.commit();
                    } catch (HiException e) {
                        log.warn("batchOnline-stmt1 失败", e);
                        try {
                            dbUtil.rollback();
                        } catch (Exception e1) {
                        }
                    }
                }
            } finally {
                log.close();
                HiMessageContext.removeCurrentContext();
            }
            return 0;
        }

        private String bpCreateCondition(HiETF root_i, HiETF root_o, Logger log, HiMessageContext ctx) {
            String strFields = root_o.getChildValue("UPD_FLD");
            if (StringUtils.isBlank(strFields)) {
                log.warn("760031,找不到节点[UPD_FLD]!");
                return null;
            }
            String[] fields = StringUtils.split(strFields, "|");

            StringBuffer value = new StringBuffer();
            for (int i = 0; i < fields.length; ++i) {
                if (fields[i].indexOf("=") > 0) {
                    if (i > 0) {
                        value.append(",");
                    }
                    value.append(fields[i]);
                } else {
                    if (i > 0) {
                        value.append(",");
                    }
                    String v = (String) ctx.getSpecExpre(root_o, fields[i]);
                    if (v == null) v = "";
                    value.append(fields[i] + "='" + v + "'");
                }
            }
            return value.toString();
        }
    }

    class HiStartWork implements Runnable {
        public void run() {
            HiMessageContext ctx = new HiMessageContext();
            try {
                ctx.pushParent(HiBatchonlineHandler.this._serverContext);
                HiMessage msg = new HiMessage(HiBatchonlineHandler.this._serverName, "PLTIN0");
                ctx.setCurrentMsg(msg);
                HiMessageContext.setCurrentContext(ctx);
                HiBatchonlineHandler.this._server.process(ctx);
            } catch (Throwable t) {
                HiBatchonlineHandler.this._serverLog.error(t, t);
            } finally {
                if (ctx.getDataBaseUtil() != null) ctx.getDataBaseUtil().closeAll();
            }
        }
    }
}