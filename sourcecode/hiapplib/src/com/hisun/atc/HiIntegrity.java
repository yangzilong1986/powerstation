 package com.hisun.atc;
 
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.engine.invoke.impl.HiAttributesHelper;
 import com.hisun.engine.invoke.impl.HiRunStatus;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 import java.util.Stack;
 import org.apache.commons.lang.StringUtils;
 
 public class HiIntegrity
 {
   private static HiStringManager sm = HiStringManager.getManager();
   private static final String TRAN_ID_STACK = "_TRAN_ID_STACK";
 
   public static int BeginWork(HiATLParam argsMap, HiMessageContext ctx)
   {
     HiRunStatus runStatus = HiRunStatus.getRunStatus(ctx);
     runStatus.setSCRCStart(true);
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (log.isDebugEnabled()) {
       log.debug("交易运行状态:[" + runStatus + "]");
     }
     return 0;
   }
 
   public static int BeginWorkNew1(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiTransJnl transJnl = new HiTransJnl();
     HiMessage msg = ctx.getCurrentMsg();
 
     HiAttributesHelper attrs = HiAttributesHelper.getAttribute(ctx);
 
     transJnl.setSerNam(ctx.getStrProp("trans_code"));
     String logNo = ctx.getStrProp("LogNo");
 
     if (StringUtils.isEmpty(logNo)) {
       logNo = msg.getETFBody().getChildValue("LOG_NO");
     }
     transJnl.setLogNo(logNo);
 
     if ((argsMap != null) && (argsMap.contains("Code")))
       transJnl.setExpSer(argsMap.get("Code"));
     else {
       transJnl.setExpSer(attrs.code());
     }
 
     if ((argsMap != null) && (argsMap.contains("Interval")))
       transJnl.setItv(argsMap.getInt("Interval"));
     else {
       transJnl.setItv(attrs.interval());
     }
 
     if ((argsMap != null) && (argsMap.contains("TimeOut")))
       transJnl.setTmOut(argsMap.getInt("TimeOut"));
     else {
       transJnl.setTmOut(attrs.timeout());
     }
 
     if ((argsMap != null) && (argsMap.contains("MaxTms")))
       transJnl.setMaxTms(argsMap.getInt("MaxTms"));
     else {
       transJnl.setMaxTms(attrs.maxtimes());
     }
 
     if ((argsMap != null) && (argsMap.getBoolean("Data"))) {
       transJnl.setData(msg.getETFBody().toString());
     }
     HiMessage msg1 = transJnl.invoke("BeginWork");
     HiETF root = msg1.getETFBody();
     if (root != null) {
       String msgType = root.getChildValue("MSG_TYP");
       if ("E".equalsIgnoreCase(msgType)) {
         throw new HiException("220064");
       }
 
       Stack tranIdStack = (Stack)ctx.getProperty("_TRAN_ID_STACK");
       if (tranIdStack == null) {
         tranIdStack = new Stack();
       }
       tranIdStack.push(root.getChildValue("TRAN_ID"));
       ctx.setProperty("_TRAN_ID_STACK", tranIdStack);
     }
     return 0;
   }
 
   public static int CommitWorkNew1(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     Stack tranIdStack = (Stack)ctx.getProperty("_TRAN_ID_STACK");
     if ((tranIdStack == null) || (tranIdStack.isEmpty())) {
       return 0;
     }
     String transId = (String)tranIdStack.pop();
     ctx.setProperty("_TRAN_ID_STACK", tranIdStack);
 
     HiTransJnl transJnl = new HiTransJnl();
     transJnl.setTranId(transId);
     HiMessage msg1 = transJnl.invoke("CommitWork");
     HiETF root = msg1.getETFBody();
     if (root != null) {
       String msgType = root.getChildValue("MSG_TYP");
       if ("E".equalsIgnoreCase(msgType)) {
         throw new HiException("220064");
       }
     }
     return 0;
   }
 
   public static int RollbackWorkNew1(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     Stack transIdStack = (Stack)ctx.getProperty("_TRAN_ID_STACK");
     if ((transIdStack == null) || (transIdStack.isEmpty())) {
       return 0;
     }
     String transId = (String)transIdStack.pop();
     ctx.setProperty("_TRAN_ID_STACK", transIdStack);
 
     HiTransJnl transJnl = new HiTransJnl();
     transJnl.setTranId(transId);
     HiMessage msg1 = transJnl.invoke("RollbackWork");
     HiETF root = msg1.getETFBody();
     if (root != null) {
       String msgType = root.getChildValue("MSG_TYP");
       if ("E".equalsIgnoreCase(msgType)) {
         throw new HiException("220064");
       }
     }
     return 0;
   }
 
   public static int TranBeginWork(HiATLParam argsMap, HiMessageContext ctx) throws HiException
   {
     HiTransJnl transJnl = new HiTransJnl();
     HiMessage msg = ctx.getCurrentMsg();
     HiETF root = msg.getETFBody();
     HiAttributesHelper attrs = HiAttributesHelper.getAttribute(ctx);
     if (argsMap.contains("TxnCd"))
       transJnl.setSerNam(argsMap.get("TxnCd"));
     else if (ctx.containsProperty("trans_code"))
       transJnl.setSerNam(ctx.getStrProp("trans_code"));
     else if (root.isExist("TXN_CD")) {
       transJnl.setSerNam(root.getChildValue("TXN_CD"));
     }
 
     String logNo = ctx.getStrProp("LogNo");
     if (StringUtils.isEmpty(logNo))
       logNo = msg.getETFBody().getChildValue("LOG_NO");
     transJnl.setLogNo(logNo);
     if ((argsMap != null) && (argsMap.contains("Code")))
       transJnl.setExpSer(argsMap.get("Code"));
     else {
       transJnl.setExpSer(attrs.code());
     }
     if (StringUtils.isEmpty(transJnl.getSerNam())) {
       transJnl.setSerNam(transJnl.getExpSer());
     }
     if ((argsMap != null) && (argsMap.contains("Interval")))
       transJnl.setItv(argsMap.getInt("Interval"));
     else
       transJnl.setItv(attrs.interval());
     if ((argsMap != null) && (argsMap.contains("TimeOut")))
       transJnl.setTmOut(argsMap.getInt("TimeOut"));
     else
       transJnl.setTmOut(attrs.timeout());
     transJnl.setTimOut(transJnl.getTmOut());
     if ((argsMap != null) && (argsMap.contains("MaxTms")))
       transJnl.setMaxTms(argsMap.getInt("MaxTms"));
     else
       transJnl.setMaxTms(attrs.maxtimes());
     boolean autoCommit = false;
     if ((argsMap != null) && (argsMap.contains("AutoCommit")) && 
       ("Y".equalsIgnoreCase(argsMap.get("AutoCommit")))) {
       autoCommit = true;
     }
 
     if ((argsMap != null) && 
       (argsMap.contains("Data"))) {
       String tmp = argsMap.get("Data");
       if ("ETF".equalsIgnoreCase(tmp))
         transJnl.setData(msg.getETFBody().toString());
       else {
         transJnl.setData(argsMap.get("Data"));
       }
     }
 
     String sqlCmd = "INSERT INTO pubigtjnl (TRAN_ID, LOG_NO, SER_NM, EXP_SER, OPR_CLK, ITV, TM_OUT, TIM_OUT, MAX_TMS, DATA) VALUES('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')";
     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
     transJnl.tranId = transJnl.logNo;
     transJnl.oprClk = (System.currentTimeMillis() / 1000L);
     int ret = dbUtil.execUpdate(sqlCmd, new String[] { transJnl.tranId, transJnl.logNo, transJnl.serNam, transJnl.expSer, String.valueOf(transJnl.oprClk), String.valueOf(transJnl.itv), String.valueOf(transJnl.tmOut), String.valueOf(transJnl.timOut), String.valueOf(transJnl.maxTms), transJnl.getData() });
 
     if (ret == 0) {
       sqlCmd = "UPDATE pubigtjnl set SER_NM='%s', EXP_SER='%s', OPR_CLK='%s', ITV='%s', TM_OUT='%s', TIM_OUT='%s', MAX_TMS='%s', STS='0' WHERE  TRAN_ID='%s' AND LOG_NO='%s'";
       dbUtil.execUpdate(sqlCmd, new String[] { transJnl.serNam, transJnl.expSer, String.valueOf(transJnl.oprClk), String.valueOf(transJnl.itv), String.valueOf(transJnl.tmOut), String.valueOf(transJnl.timOut), String.valueOf(transJnl.maxTms), transJnl.tranId, transJnl.logNo });
 
       if (autoCommit) {
         dbUtil.commit();
       }
       return 0;
     }
     if (autoCommit) {
       dbUtil.commit();
     }
     Stack tranIdStack = (Stack)ctx.getProperty("_TRAN_ID_STACK");
     if (tranIdStack == null)
       tranIdStack = new Stack();
     tranIdStack.push(transJnl.tranId);
     ctx.setProperty("_TRAN_ID_STACK", tranIdStack);
     return 0;
   }
 
   public static int TranRollbackWork(HiATLParam argsMap, HiMessageContext ctx) throws HiException
   {
     Stack transIdStack = (Stack)ctx.getProperty("_TRAN_ID_STACK");
     if ((transIdStack == null) || (transIdStack.isEmpty()))
       return 0;
     String transId = (String)transIdStack.pop();
     ctx.setProperty("_TRAN_ID_STACK", transIdStack);
     String sqlCmd = "UPDATE pubigtjnl SET STS='1' WHERE TRAN_ID='%s'";
     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
     int ret = dbUtil.execUpdate(sqlCmd, transId);
     boolean autoCommit = false;
     if ((argsMap != null) && (argsMap.contains("AutoCommit")) && 
       ("Y".equalsIgnoreCase(argsMap.get("AutoCommit")))) {
       autoCommit = true;
     }
 
     if (autoCommit) {
       dbUtil.commit();
     }
     return ((ret != 0) ? 0 : 2);
   }
 
   public static int TranCommitWork(HiATLParam argsMap, HiMessageContext ctx) throws HiException
   {
     Stack tranIdStack = (Stack)ctx.getProperty("_TRAN_ID_STACK");
     if ((tranIdStack == null) || (tranIdStack.isEmpty()))
       return 0;
     String transId = (String)tranIdStack.pop();
     ctx.setProperty("_TRAN_ID_STACK", tranIdStack);
     String sqlCmd = "UPDATE pubigtjnl SET STS='7' WHERE TRAN_ID='%s'";
     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
     int ret = dbUtil.execUpdate(sqlCmd, transId);
     boolean autoCommit = false;
     if ((argsMap != null) && (argsMap.contains("AutoCommit")) && 
       ("Y".equalsIgnoreCase(argsMap.get("AutoCommit")))) {
       autoCommit = true;
     }
 
     if (autoCommit) {
       dbUtil.commit();
     }
     return ((ret != 0) ? 0 : 2);
   }
 
   public static int QueryTransaction(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiTransJnl transJnl = new HiTransJnl();
     HiMessage msg1 = transJnl.invoke("QueryTransaction");
     HiETF root = msg1.getETFBody();
     if (root != null) {
       String msgType = root.getChildValue("MSG_TYP");
       if ("E".equalsIgnoreCase(msgType)) {
         throw new HiException("220064");
       }
     }
     return 0;
   }
 
   public static int CancelCorrect(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     return TranCommitWork(argsMap, ctx);
   }
 
   public static int CancelRedo(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     return TranCommitWork(argsMap, ctx);
   }
 
   public static int PreRegisterRedo(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     HiRunStatus runStatus = HiRunStatus.getRunStatus(ctx);
     HiAttributesHelper attrs = HiAttributesHelper.getAttribute(ctx);
     if (log.isInfoEnabled()) {
       log.info("Attributes:" + attrs);
     }
     if (!(runStatus.isRSNDNotReg())) {
       if (log.isInfoEnabled())
       {
         log.info(sm.getString("HiIntegrity.PreRegisterRedo00", String.valueOf(runStatus.getRSNDReg())));
       }
       return 0;
     }
 
     TranBeginWork(argsMap, ctx);
     runStatus.setRSNDPreReg();
     return 0;
   }
 
   public static int RegisterCorrect(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiAttributesHelper attrs = HiAttributesHelper.getAttribute(ctx);
     HiRunStatus runStatus = HiRunStatus.getRunStatus(ctx);
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (log.isInfoEnabled()) {
       log.info("Attributes:" + attrs);
     }
     if ((attrs.isSysCrct()) || ((attrs.isCndCrct()) && (runStatus.isSCRCStart()))) {
       if (runStatus.isCRCTReg()) {
         if (log.isInfoEnabled()) {
           log.info(sm.getString("HiIntegrity.RegisterCorrect00", String.valueOf(runStatus.getCRCTReg())));
         }
         return 0;
       }
       TranBeginWork(argsMap, ctx);
       TranRollbackWork(argsMap, ctx);
       runStatus.setCRCTReg();
       return 0;
     }
     return 0;
   }
 
   public static int RegisterRedo(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiAttributesHelper attrs = HiAttributesHelper.getAttribute(ctx);
     HiRunStatus runStatus = HiRunStatus.getRunStatus(ctx);
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (log.isInfoEnabled()) {
       log.info("Attributes:" + attrs);
     }
     if ((attrs.isSysRsnd()) || ((attrs.isCndRsnd()) && (runStatus.isSCRCStart()))) {
       if ((runStatus.isRSNDReg()) || (runStatus.isRSNDPreReg())) {
         if (log.isInfoEnabled()) {
           log.info(sm.getString("HiIntegrity.RegisterRedo00", String.valueOf(runStatus.getRSNDReg())));
         }
 
         return 0;
       }
       TranBeginWork(argsMap, ctx);
       TranRollbackWork(argsMap, ctx);
       runStatus.setRSNDReg();
       return 0;
     }
 
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiIntegrity.RegisterRedo00", String.valueOf(attrs.integrity()), String.valueOf(runStatus.getSCRCStart())));
     }
 
     return 0;
   }
 
   public static int SetCorrectStatus(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiRunStatus runStatus = HiRunStatus.getRunStatus(ctx);
     runStatus.setCRCTReg();
     return 0;
   }
 
   public static int SetRedoStatus(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiRunStatus runStatus = HiRunStatus.getRunStatus(ctx);
     runStatus.setRSNDReg();
     return 0;
   }
 
   public static int SafRegister(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     return 0;
   }
 }