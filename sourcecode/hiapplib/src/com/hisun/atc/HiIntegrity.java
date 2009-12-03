/*     */ package com.hisun.atc;
/*     */ 
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.engine.invoke.impl.HiAttributesHelper;
/*     */ import com.hisun.engine.invoke.impl.HiRunStatus;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.util.Stack;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiIntegrity
/*     */ {
/*  22 */   private static HiStringManager sm = HiStringManager.getManager();
/*     */   private static final String TRAN_ID_STACK = "_TRAN_ID_STACK";
/*     */ 
/*     */   public static int BeginWork(HiATLParam argsMap, HiMessageContext ctx)
/*     */   {
/*  27 */     HiRunStatus runStatus = HiRunStatus.getRunStatus(ctx);
/*  28 */     runStatus.setSCRCStart(true);
/*  29 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*  30 */     if (log.isDebugEnabled()) {
/*  31 */       log.debug("交易运行状态:[" + runStatus + "]");
/*     */     }
/*  33 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int BeginWorkNew1(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  52 */     HiTransJnl transJnl = new HiTransJnl();
/*  53 */     HiMessage msg = ctx.getCurrentMsg();
/*     */ 
/*  55 */     HiAttributesHelper attrs = HiAttributesHelper.getAttribute(ctx);
/*     */ 
/*  57 */     transJnl.setSerNam(ctx.getStrProp("trans_code"));
/*  58 */     String logNo = ctx.getStrProp("LogNo");
/*     */ 
/*  60 */     if (StringUtils.isEmpty(logNo)) {
/*  61 */       logNo = msg.getETFBody().getChildValue("LOG_NO");
/*     */     }
/*  63 */     transJnl.setLogNo(logNo);
/*     */ 
/*  65 */     if ((argsMap != null) && (argsMap.contains("Code")))
/*  66 */       transJnl.setExpSer(argsMap.get("Code"));
/*     */     else {
/*  68 */       transJnl.setExpSer(attrs.code());
/*     */     }
/*     */ 
/*  71 */     if ((argsMap != null) && (argsMap.contains("Interval")))
/*  72 */       transJnl.setItv(argsMap.getInt("Interval"));
/*     */     else {
/*  74 */       transJnl.setItv(attrs.interval());
/*     */     }
/*     */ 
/*  77 */     if ((argsMap != null) && (argsMap.contains("TimeOut")))
/*  78 */       transJnl.setTmOut(argsMap.getInt("TimeOut"));
/*     */     else {
/*  80 */       transJnl.setTmOut(attrs.timeout());
/*     */     }
/*     */ 
/*  83 */     if ((argsMap != null) && (argsMap.contains("MaxTms")))
/*  84 */       transJnl.setMaxTms(argsMap.getInt("MaxTms"));
/*     */     else {
/*  86 */       transJnl.setMaxTms(attrs.maxtimes());
/*     */     }
/*     */ 
/*  89 */     if ((argsMap != null) && (argsMap.getBoolean("Data"))) {
/*  90 */       transJnl.setData(msg.getETFBody().toString());
/*     */     }
/*  92 */     HiMessage msg1 = transJnl.invoke("BeginWork");
/*  93 */     HiETF root = msg1.getETFBody();
/*  94 */     if (root != null) {
/*  95 */       String msgType = root.getChildValue("MSG_TYP");
/*  96 */       if ("E".equalsIgnoreCase(msgType)) {
/*  97 */         throw new HiException("220064");
/*     */       }
/*     */ 
/* 100 */       Stack tranIdStack = (Stack)ctx.getProperty("_TRAN_ID_STACK");
/* 101 */       if (tranIdStack == null) {
/* 102 */         tranIdStack = new Stack();
/*     */       }
/* 104 */       tranIdStack.push(root.getChildValue("TRAN_ID"));
/* 105 */       ctx.setProperty("_TRAN_ID_STACK", tranIdStack);
/*     */     }
/* 107 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int CommitWorkNew1(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 118 */     Stack tranIdStack = (Stack)ctx.getProperty("_TRAN_ID_STACK");
/* 119 */     if ((tranIdStack == null) || (tranIdStack.isEmpty())) {
/* 120 */       return 0;
/*     */     }
/* 122 */     String transId = (String)tranIdStack.pop();
/* 123 */     ctx.setProperty("_TRAN_ID_STACK", tranIdStack);
/*     */ 
/* 125 */     HiTransJnl transJnl = new HiTransJnl();
/* 126 */     transJnl.setTranId(transId);
/* 127 */     HiMessage msg1 = transJnl.invoke("CommitWork");
/* 128 */     HiETF root = msg1.getETFBody();
/* 129 */     if (root != null) {
/* 130 */       String msgType = root.getChildValue("MSG_TYP");
/* 131 */       if ("E".equalsIgnoreCase(msgType)) {
/* 132 */         throw new HiException("220064");
/*     */       }
/*     */     }
/* 135 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int RollbackWorkNew1(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 146 */     Stack transIdStack = (Stack)ctx.getProperty("_TRAN_ID_STACK");
/* 147 */     if ((transIdStack == null) || (transIdStack.isEmpty())) {
/* 148 */       return 0;
/*     */     }
/* 150 */     String transId = (String)transIdStack.pop();
/* 151 */     ctx.setProperty("_TRAN_ID_STACK", transIdStack);
/*     */ 
/* 153 */     HiTransJnl transJnl = new HiTransJnl();
/* 154 */     transJnl.setTranId(transId);
/* 155 */     HiMessage msg1 = transJnl.invoke("RollbackWork");
/* 156 */     HiETF root = msg1.getETFBody();
/* 157 */     if (root != null) {
/* 158 */       String msgType = root.getChildValue("MSG_TYP");
/* 159 */       if ("E".equalsIgnoreCase(msgType)) {
/* 160 */         throw new HiException("220064");
/*     */       }
/*     */     }
/* 163 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int TranBeginWork(HiATLParam argsMap, HiMessageContext ctx) throws HiException
/*     */   {
/* 168 */     HiTransJnl transJnl = new HiTransJnl();
/* 169 */     HiMessage msg = ctx.getCurrentMsg();
/* 170 */     HiETF root = msg.getETFBody();
/* 171 */     HiAttributesHelper attrs = HiAttributesHelper.getAttribute(ctx);
/* 172 */     if (argsMap.contains("TxnCd"))
/* 173 */       transJnl.setSerNam(argsMap.get("TxnCd"));
/* 174 */     else if (ctx.containsProperty("trans_code"))
/* 175 */       transJnl.setSerNam(ctx.getStrProp("trans_code"));
/* 176 */     else if (root.isExist("TXN_CD")) {
/* 177 */       transJnl.setSerNam(root.getChildValue("TXN_CD"));
/*     */     }
/*     */ 
/* 180 */     String logNo = ctx.getStrProp("LogNo");
/* 181 */     if (StringUtils.isEmpty(logNo))
/* 182 */       logNo = msg.getETFBody().getChildValue("LOG_NO");
/* 183 */     transJnl.setLogNo(logNo);
/* 184 */     if ((argsMap != null) && (argsMap.contains("Code")))
/* 185 */       transJnl.setExpSer(argsMap.get("Code"));
/*     */     else {
/* 187 */       transJnl.setExpSer(attrs.code());
/*     */     }
/* 189 */     if (StringUtils.isEmpty(transJnl.getSerNam())) {
/* 190 */       transJnl.setSerNam(transJnl.getExpSer());
/*     */     }
/* 192 */     if ((argsMap != null) && (argsMap.contains("Interval")))
/* 193 */       transJnl.setItv(argsMap.getInt("Interval"));
/*     */     else
/* 195 */       transJnl.setItv(attrs.interval());
/* 196 */     if ((argsMap != null) && (argsMap.contains("TimeOut")))
/* 197 */       transJnl.setTmOut(argsMap.getInt("TimeOut"));
/*     */     else
/* 199 */       transJnl.setTmOut(attrs.timeout());
/* 200 */     transJnl.setTimOut(transJnl.getTmOut());
/* 201 */     if ((argsMap != null) && (argsMap.contains("MaxTms")))
/* 202 */       transJnl.setMaxTms(argsMap.getInt("MaxTms"));
/*     */     else
/* 204 */       transJnl.setMaxTms(attrs.maxtimes());
/* 205 */     boolean autoCommit = false;
/* 206 */     if ((argsMap != null) && (argsMap.contains("AutoCommit")) && 
/* 207 */       ("Y".equalsIgnoreCase(argsMap.get("AutoCommit")))) {
/* 208 */       autoCommit = true;
/*     */     }
/*     */ 
/* 213 */     if ((argsMap != null) && 
/* 214 */       (argsMap.contains("Data"))) {
/* 215 */       String tmp = argsMap.get("Data");
/* 216 */       if ("ETF".equalsIgnoreCase(tmp))
/* 217 */         transJnl.setData(msg.getETFBody().toString());
/*     */       else {
/* 219 */         transJnl.setData(argsMap.get("Data"));
/*     */       }
/*     */     }
/*     */ 
/* 223 */     String sqlCmd = "INSERT INTO pubigtjnl (TRAN_ID, LOG_NO, SER_NM, EXP_SER, OPR_CLK, ITV, TM_OUT, TIM_OUT, MAX_TMS, DATA) VALUES('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')";
/* 224 */     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
/* 225 */     transJnl.tranId = transJnl.logNo;
/* 226 */     transJnl.oprClk = (System.currentTimeMillis() / 1000L);
/* 227 */     int ret = dbUtil.execUpdate(sqlCmd, new String[] { transJnl.tranId, transJnl.logNo, transJnl.serNam, transJnl.expSer, String.valueOf(transJnl.oprClk), String.valueOf(transJnl.itv), String.valueOf(transJnl.tmOut), String.valueOf(transJnl.timOut), String.valueOf(transJnl.maxTms), transJnl.getData() });
/*     */ 
/* 233 */     if (ret == 0) {
/* 234 */       sqlCmd = "UPDATE pubigtjnl set SER_NM='%s', EXP_SER='%s', OPR_CLK='%s', ITV='%s', TM_OUT='%s', TIM_OUT='%s', MAX_TMS='%s', STS='0' WHERE  TRAN_ID='%s' AND LOG_NO='%s'";
/* 235 */       dbUtil.execUpdate(sqlCmd, new String[] { transJnl.serNam, transJnl.expSer, String.valueOf(transJnl.oprClk), String.valueOf(transJnl.itv), String.valueOf(transJnl.tmOut), String.valueOf(transJnl.timOut), String.valueOf(transJnl.maxTms), transJnl.tranId, transJnl.logNo });
/*     */ 
/* 241 */       if (autoCommit) {
/* 242 */         dbUtil.commit();
/*     */       }
/* 244 */       return 0;
/*     */     }
/* 246 */     if (autoCommit) {
/* 247 */       dbUtil.commit();
/*     */     }
/* 249 */     Stack tranIdStack = (Stack)ctx.getProperty("_TRAN_ID_STACK");
/* 250 */     if (tranIdStack == null)
/* 251 */       tranIdStack = new Stack();
/* 252 */     tranIdStack.push(transJnl.tranId);
/* 253 */     ctx.setProperty("_TRAN_ID_STACK", tranIdStack);
/* 254 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int TranRollbackWork(HiATLParam argsMap, HiMessageContext ctx) throws HiException
/*     */   {
/* 259 */     Stack transIdStack = (Stack)ctx.getProperty("_TRAN_ID_STACK");
/* 260 */     if ((transIdStack == null) || (transIdStack.isEmpty()))
/* 261 */       return 0;
/* 262 */     String transId = (String)transIdStack.pop();
/* 263 */     ctx.setProperty("_TRAN_ID_STACK", transIdStack);
/* 264 */     String sqlCmd = "UPDATE pubigtjnl SET STS='1' WHERE TRAN_ID='%s'";
/* 265 */     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
/* 266 */     int ret = dbUtil.execUpdate(sqlCmd, transId);
/* 267 */     boolean autoCommit = false;
/* 268 */     if ((argsMap != null) && (argsMap.contains("AutoCommit")) && 
/* 269 */       ("Y".equalsIgnoreCase(argsMap.get("AutoCommit")))) {
/* 270 */       autoCommit = true;
/*     */     }
/*     */ 
/* 273 */     if (autoCommit) {
/* 274 */       dbUtil.commit();
/*     */     }
/* 276 */     return ((ret != 0) ? 0 : 2);
/*     */   }
/*     */ 
/*     */   public static int TranCommitWork(HiATLParam argsMap, HiMessageContext ctx) throws HiException
/*     */   {
/* 281 */     Stack tranIdStack = (Stack)ctx.getProperty("_TRAN_ID_STACK");
/* 282 */     if ((tranIdStack == null) || (tranIdStack.isEmpty()))
/* 283 */       return 0;
/* 284 */     String transId = (String)tranIdStack.pop();
/* 285 */     ctx.setProperty("_TRAN_ID_STACK", tranIdStack);
/* 286 */     String sqlCmd = "UPDATE pubigtjnl SET STS='7' WHERE TRAN_ID='%s'";
/* 287 */     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
/* 288 */     int ret = dbUtil.execUpdate(sqlCmd, transId);
/* 289 */     boolean autoCommit = false;
/* 290 */     if ((argsMap != null) && (argsMap.contains("AutoCommit")) && 
/* 291 */       ("Y".equalsIgnoreCase(argsMap.get("AutoCommit")))) {
/* 292 */       autoCommit = true;
/*     */     }
/*     */ 
/* 295 */     if (autoCommit) {
/* 296 */       dbUtil.commit();
/*     */     }
/* 298 */     return ((ret != 0) ? 0 : 2);
/*     */   }
/*     */ 
/*     */   public static int QueryTransaction(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 309 */     HiTransJnl transJnl = new HiTransJnl();
/* 310 */     HiMessage msg1 = transJnl.invoke("QueryTransaction");
/* 311 */     HiETF root = msg1.getETFBody();
/* 312 */     if (root != null) {
/* 313 */       String msgType = root.getChildValue("MSG_TYP");
/* 314 */       if ("E".equalsIgnoreCase(msgType)) {
/* 315 */         throw new HiException("220064");
/*     */       }
/*     */     }
/* 318 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int CancelCorrect(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 330 */     return TranCommitWork(argsMap, ctx);
/*     */   }
/*     */ 
/*     */   public static int CancelRedo(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 342 */     return TranCommitWork(argsMap, ctx);
/*     */   }
/*     */ 
/*     */   public static int PreRegisterRedo(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 354 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 355 */     HiRunStatus runStatus = HiRunStatus.getRunStatus(ctx);
/* 356 */     HiAttributesHelper attrs = HiAttributesHelper.getAttribute(ctx);
/* 357 */     if (log.isInfoEnabled()) {
/* 358 */       log.info("Attributes:" + attrs);
/*     */     }
/* 360 */     if (!(runStatus.isRSNDNotReg())) {
/* 361 */       if (log.isInfoEnabled())
/*     */       {
/* 363 */         log.info(sm.getString("HiIntegrity.PreRegisterRedo00", String.valueOf(runStatus.getRSNDReg())));
/*     */       }
/* 365 */       return 0;
/*     */     }
/*     */ 
/* 368 */     TranBeginWork(argsMap, ctx);
/* 369 */     runStatus.setRSNDPreReg();
/* 370 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int RegisterCorrect(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 382 */     HiAttributesHelper attrs = HiAttributesHelper.getAttribute(ctx);
/* 383 */     HiRunStatus runStatus = HiRunStatus.getRunStatus(ctx);
/* 384 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 385 */     if (log.isInfoEnabled()) {
/* 386 */       log.info("Attributes:" + attrs);
/*     */     }
/* 388 */     if ((attrs.isSysCrct()) || ((attrs.isCndCrct()) && (runStatus.isSCRCStart()))) {
/* 389 */       if (runStatus.isCRCTReg()) {
/* 390 */         if (log.isInfoEnabled()) {
/* 391 */           log.info(sm.getString("HiIntegrity.RegisterCorrect00", String.valueOf(runStatus.getCRCTReg())));
/*     */         }
/* 393 */         return 0;
/*     */       }
/* 395 */       TranBeginWork(argsMap, ctx);
/* 396 */       TranRollbackWork(argsMap, ctx);
/* 397 */       runStatus.setCRCTReg();
/* 398 */       return 0;
/*     */     }
/* 400 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int RegisterRedo(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 412 */     HiAttributesHelper attrs = HiAttributesHelper.getAttribute(ctx);
/* 413 */     HiRunStatus runStatus = HiRunStatus.getRunStatus(ctx);
/* 414 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 415 */     if (log.isInfoEnabled()) {
/* 416 */       log.info("Attributes:" + attrs);
/*     */     }
/* 418 */     if ((attrs.isSysRsnd()) || ((attrs.isCndRsnd()) && (runStatus.isSCRCStart()))) {
/* 419 */       if ((runStatus.isRSNDReg()) || (runStatus.isRSNDPreReg())) {
/* 420 */         if (log.isInfoEnabled()) {
/* 421 */           log.info(sm.getString("HiIntegrity.RegisterRedo00", String.valueOf(runStatus.getRSNDReg())));
/*     */         }
/*     */ 
/* 424 */         return 0;
/*     */       }
/* 426 */       TranBeginWork(argsMap, ctx);
/* 427 */       TranRollbackWork(argsMap, ctx);
/* 428 */       runStatus.setRSNDReg();
/* 429 */       return 0;
/*     */     }
/*     */ 
/* 432 */     if (log.isInfoEnabled()) {
/* 433 */       log.info(sm.getString("HiIntegrity.RegisterRedo00", String.valueOf(attrs.integrity()), String.valueOf(runStatus.getSCRCStart())));
/*     */     }
/*     */ 
/* 437 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int SetCorrectStatus(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 449 */     HiRunStatus runStatus = HiRunStatus.getRunStatus(ctx);
/* 450 */     runStatus.setCRCTReg();
/* 451 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int SetRedoStatus(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 463 */     HiRunStatus runStatus = HiRunStatus.getRunStatus(ctx);
/* 464 */     runStatus.setRSNDReg();
/* 465 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int SafRegister(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 539 */     return 0;
/*     */   }
/*     */ }