/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.engine.HiEngineModel;
/*     */ import com.hisun.engine.HiEngineStack;
/*     */ import com.hisun.engine.HiEngineUtilities;
/*     */ import com.hisun.engine.exception.HiErrorException;
/*     */ import com.hisun.engine.exception.HiExitException;
/*     */ import com.hisun.engine.exception.HiGotoException;
/*     */ import com.hisun.engine.exception.HiReturnException;
/*     */ import com.hisun.engine.invoke.HiIAction;
/*     */ import com.hisun.engine.invoke.HiIEngineModel;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.exception.HiResponseException;
/*     */ import com.hisun.hiexpression.HiExpFactory;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilib.HiLib;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.service.HiInfrastructure;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import com.hisun.util.HiStringUtils;
/*     */ import com.hisun.util.HiThreadPool;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiTransactionCTLImpl extends HiAbstractTransaction
/*     */ {
/*     */   private String strError;
/*  42 */   protected boolean isFunc = false;
/*     */ 
/*     */   public HiTransactionCTLImpl() {
/*  45 */     this.context = HiContext.createAndPushContext();
/*  46 */     this.log_id = this.context.getStrProp("__LOG_ID");
/*  47 */     if (this.log_id != null)
/*  48 */       this.log_idExpr = HiExpFactory.createExp(this.log_id);
/*     */   }
/*     */ 
/*     */   public void setError(String strError)
/*     */   {
/*  56 */     this.strError = strError;
/*     */   }
/*     */ 
/*     */   public String getError()
/*     */   {
/*  63 */     return this.strError;
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     HiFunction func;
/*  67 */     HiMessage msg = ctx.getCurrentMsg();
/*  68 */     Logger log = HiLog.getLogger(msg);
/*     */ 
/*  70 */     HiEngineStack stack = HiEngineStack.getCurrentStack(ctx);
/*     */     try {
/*  72 */       func = (HiFunction)this.context.getProperty("_before");
/*  73 */       if (func == null) {
/*  74 */         func = (HiFunction)this.context.getProperty("GLOBALFUNCTION", "_before");
/*     */       }
/*     */ 
/*  77 */       if (func != null) {
/*  78 */         HiProcess.process(func, ctx);
/*     */       }
/*     */ 
/*  81 */       doProcess(ctx, stack);
/*     */     } catch (Throwable e) {
/*  83 */       if (!(e instanceof HiExitException))
/*  84 */         HiLog.logServiceError(msg, HiException.makeException(e));
/*     */     }
/*     */     finally {
/*     */       try {
/*  88 */         HiFunction func = (HiFunction)this.context.getProperty("_after");
/*  89 */         if (func == null) {
/*  90 */           func = (HiFunction)this.context.getProperty("GLOBALFUNCTION", "_after");
/*     */         }
/*     */ 
/*  93 */         if (func != null)
/*  94 */           HiProcess.process(func, ctx);
/*     */       }
/*     */       finally {
/*  97 */         if (ctx.getDataBaseUtil() != null)
/*  98 */           ctx.getDataBaseUtil().closeAll();
/*     */       }
/*     */     }
/* 101 */     HiRunStatus runStatus = HiRunStatus.getRunStatus(ctx);
/* 102 */     if (runStatus.isResponse())
/*     */       return;
/* 104 */     msg.setHeadItem("RSP", "0");
/*     */   }
/*     */ 
/*     */   public void doProcess(HiMessageContext ctx, HiEngineStack stack)
/*     */     throws HiException
/*     */   {
/* 110 */     HiMessage msg = ctx.getCurrentMsg();
/* 111 */     Logger log = HiLog.getLogger(msg);
/*     */ 
/* 113 */     HiETF etfRoot = (HiETF)msg.getBody();
/*     */ 
/* 116 */     List childs = super.getChilds();
/* 117 */     String strGotoName = null;
/* 118 */     HiEngineModel rsEn = null;
/* 119 */     if (stack != null) {
/* 120 */       rsEn = (HiEngineModel)stack.pop();
/*     */     }
/* 122 */     if (childs == null) {
/* 123 */       log.warn("no execute step");
/* 124 */       return;
/*     */     }
/*     */ 
/* 127 */     for (int i = 0; i < childs.size(); ++i) {
/* 128 */       HiIAction child = (HiIAction)childs.get(i);
/*     */ 
/* 130 */       if (rsEn != null) {
/* 131 */         if (child != rsEn) {
/*     */           continue;
/*     */         }
/* 134 */         rsEn = null;
/* 135 */         if (stack.size() == 0) {
/*     */           continue;
/*     */         }
/*     */       }
/*     */ 
/* 140 */       HiEngineUtilities.setCurFlowStep(i);
/*     */ 
/* 142 */       if (strGotoName != null) {
/* 143 */         if (!(child instanceof HiLabel))
/*     */           continue;
/* 145 */         HiLabel lable = (HiLabel)child;
/* 146 */         label469: if (strGotoName.equalsIgnoreCase(lable.getName()))
/* 147 */           strGotoName = null;
/*     */       }
/*     */       else
/*     */       {
/*     */         try
/*     */         {
/* 153 */           HiProcess.process(child, ctx);
/*     */         } catch (HiResponseException e) {
/* 155 */           if (log.isInfoEnabled()) {
/* 156 */             log.info("PutResponse is start");
/*     */           }
/* 158 */           HiEngineStack.getEngineStack(ctx).push(this);
/* 159 */           HiMessageContext newCtx = new HiMessageContext(ctx);
/* 160 */           HiThreadPool pool = HiInfrastructure.getThreadPoolService("RESPONSE_THREAD");
/*     */ 
/* 164 */           newCtx.setDataBaseUtil(ctx.getDataBaseUtil());
/* 165 */           ctx.setDataBaseUtil(null);
/* 166 */           HiEngineStack es = HiEngineStack.getEngineStack(ctx);
/* 167 */           es.setMessContext(newCtx);
/* 168 */           pool.execute(es);
/* 169 */           return;
/*     */         } catch (HiGotoException goEx) {
/* 171 */           if (strGotoName == null)
/* 172 */             strGotoName = goEx.getGotoName();
/*     */         } catch (HiReturnException e) {
/* 174 */           return;
/*     */         } catch (HiExitException e) {
/* 176 */           if (this.isFunc) {
/* 177 */             throw e;
/*     */           }
/* 179 */           return;
/*     */         }
/*     */         catch (HiErrorException e) {
/* 182 */           if (this.strError == null) {
/* 183 */             errorProcess(ctx, "PUB:DefaultErrorProc", e);
/* 184 */             return;
/*     */           }
/*     */ 
/* 187 */           if (this.strError.equals("IGNORE")) {
/* 188 */             if (log.isInfoEnabled()) {
/* 189 */               log.info(this.sm.getString("HiTransactionCTLImpl.IgnoreErrorProcess"));
/*     */             }
/*     */ 
/* 192 */             return;
/*     */           }
/*     */ 
/* 195 */           String[] values = StringUtils.split(this.strError, ":");
/* 196 */           if (values.length != 2) {
/* 197 */             log.error(this.sm.getString("HiTransactionCTLImpl.InvalidErrorProcess", this.strError));
/*     */ 
/* 200 */             throw new HiException("213311", String.valueOf(values.length));
/*     */           }
/*     */ 
/* 204 */           if (values[0].equals("GOTO")) {
/* 205 */             strGotoName = values[1];
/* 206 */             break label469:
/*     */           }
/* 208 */           errorProcess(ctx, this.strError, e);
/* 209 */           return;
/*     */         } catch (HiException e) {
/* 211 */           etfRoot.setChildValue("MSG_TYP", "E");
/* 212 */           etfRoot.setChildValue("RSP_CD", e.getCode());
/* 213 */           etfRoot.setChildValue("RSP_MSG", e.getAppMessage());
/* 214 */           HiLog.logServiceError(msg, e);
/* 215 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void errorProcess(HiMessageContext ctx, String func, HiErrorException e) {
/* 222 */     HiMessage msg = ctx.getCurrentMsg();
/* 223 */     Logger log = HiLog.getLogger(msg);
/* 224 */     HiETF etfRoot = msg.getETFBody();
/*     */     try
/*     */     {
/* 227 */       if (log.isInfoEnabled()) {
/* 228 */         log.info(this.sm.getString("HiTransactionCTLImpl.DefaultErrorProcess", "PUB:DefaultErrorProc"));
/*     */       }
/*     */ 
/* 232 */       etfRoot.setChildValue("MSG_TYP", "E");
/* 233 */       etfRoot.setChildValue("RSP_CD", e.getCode());
/* 234 */       etfRoot.setChildValue("RSP_MSG", e.getAppMessage());
/* 235 */       HiLib.invoke(func, new HiATLParam(), ctx);
/*     */     } catch (Exception e1) {
/* 237 */       etfRoot.setChildValue("MSG_TYP", "E");
/* 238 */       etfRoot.setChildValue("RSP_CD", "213336");
/* 239 */       etfRoot.setChildValue("RSP_MSG", this.sm.getString("213336"));
/*     */ 
/* 241 */       HiLog.logServiceError(msg, HiException.makeException("213336", e1));
/*     */ 
/* 243 */       return;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void initProcess(HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 250 */     setInitData(ctx);
/*     */   }
/*     */ 
/*     */   public void setBussinessData(HiMessageContext ctx) throws HiException
/*     */   {
/* 255 */     HiMessage msg = ctx.getCurrentMsg();
/* 256 */     Logger log = HiLog.getLogger(msg);
/* 257 */     HiETF etfRoot = (HiETF)msg.getBody();
/* 258 */     String strActDat = null;
/*     */ 
/* 260 */     String sqlCmd = "SELECT ACTDAT,SYSID FROM PUBPLTINF";
/* 261 */     List lRec = ctx.getDataBaseUtil().execQuery(sqlCmd);
/* 262 */     if (log.isDebugEnabled()) {
/* 263 */       log.debug(this.sm.getString("HiTransactionCTLImpl.GetSysInfoSql", sqlCmd));
/*     */     }
/*     */ 
/* 267 */     if ((lRec == null) || (lRec.size() == 0)) {
/* 268 */       log.fatal(this.sm.getString("HiTransactionCTLImpl.GetSysInfoError"));
/* 269 */       throw new HiException("211007");
/*     */     }
/*     */ 
/* 272 */     HashMap tmp = (HashMap)lRec.get(0);
/*     */ 
/* 274 */     etfRoot.setChildValue("FSysId", (String)tmp.get("SYSID"));
/*     */ 
/* 276 */     strActDat = (String)tmp.get("ACTDAT");
/* 277 */     etfRoot.setChildValue("ActDat", strActDat);
/* 278 */     ctx.setBaseSource("FSysId", (String)tmp.get("SYSID"));
/* 279 */     ctx.setBaseSource("ActDat", strActDat);
/*     */ 
/* 281 */     if (log.isInfoEnabled()) {
/* 282 */       log.info(this.sm.getString("HiTransactionCTLImpl.GetActDat", strActDat));
/* 283 */       log.info(this.sm.getString("HiTransactionCTLImpl.GetAppCode", ctx.getStrProp("app_code")));
/*     */     }
/*     */ 
/* 291 */     String AplSub = etfRoot.getChildValue("AplSub");
/* 292 */     if (log.isInfoEnabled()) {
/* 293 */       log.info("取AplSub,值为:[" + AplSub + "]");
/*     */     }
/*     */ 
/* 296 */     String AplCls = etfRoot.getChildValue("AplCls");
/* 297 */     if (AplCls == null) {
/* 298 */       throw new HiException("220058", "AplCls");
/*     */     }
/*     */ 
/* 301 */     if (log.isInfoEnabled()) {
/* 302 */       log.info("取AplCls,值为:[" + AplCls + "]");
/*     */     }
/*     */ 
/* 305 */     String busTyp = null;
/* 306 */     sqlCmd = HiStringUtils.format("SELECT BUSTYP FROM PUBAPLBUS WHERE APLCLS='%s' AND (APLSUB='%s' OR APLSUB=' ')", AplCls, AplSub);
/*     */ 
/* 310 */     lRec = ctx.getDataBaseUtil().execQuery(sqlCmd);
/*     */ 
/* 312 */     if (lRec.size() != 0) {
/* 313 */       tmp = (HashMap)lRec.get(0);
/* 314 */       busTyp = (String)tmp.get("BUSTYP");
/* 315 */       ctx.setBaseSource("BusTyp", busTyp);
/* 316 */       if (log.isInfoEnabled()) {
/* 317 */         log.info("--->业务类型 = " + busTyp);
/*     */       }
/*     */     }
/*     */ 
/* 321 */     if (StringUtils.isEmpty(busTyp)) {
/* 322 */       return;
/*     */     }
/* 324 */     etfRoot.setChildValue("BusTyp", busTyp);
/*     */ 
/* 326 */     lRec.clear();
/* 327 */     sqlCmd = HiStringUtils.format("SELECT ONLTBL FROM PUBJNLDEF WHERE BUSTYP = '%s' FETCH FIRST ROWS ONLY", busTyp);
/*     */ 
/* 331 */     lRec = ctx.getDataBaseUtil().execQuery(sqlCmd);
/* 332 */     if (lRec.size() == 0) {
/* 333 */       return;
/*     */     }
/* 335 */     tmp = (HashMap)lRec.get(0);
/* 336 */     String onlTbl = (String)tmp.get("ONLTBL");
/*     */ 
/* 338 */     if (log.isInfoEnabled()) {
/* 339 */       log.info("--->流水表 = " + onlTbl);
/*     */     }
/* 341 */     ctx.setJnlTable(onlTbl);
/* 342 */     ctx.setBaseSource("LstTab", onlTbl);
/*     */ 
/* 344 */     HiAttributesHelper attr = HiAttributesHelper.getAttribute(ctx);
/*     */ 
/* 346 */     if (attr.isNoChk())
/* 347 */       etfRoot.setChildValue("MstChk", "0");
/*     */     else {
/* 349 */       etfRoot.setChildValue("MstChk", "1");
/*     */     }
/*     */ 
/* 352 */     etfRoot.setChildValue("ItgTyp", attr.integtype());
/* 353 */     etfRoot.setChildValue("TxnTyp", attr.getTxnTyp());
/* 354 */     etfRoot.setChildValue("FRspCd", "211007");
/*     */   }
/*     */ 
/*     */   public void setInitData(HiMessageContext ctx) throws HiException {
/* 358 */     HiMessage msg = ctx.getCurrentMsg();
/* 359 */     Logger log = HiLog.getLogger(msg);
/* 360 */     HiRunStatus runStatus = new HiRunStatus();
/* 361 */     HiAttributesHelper attr = HiAttributesHelper.getAttribute(ctx);
/* 362 */     runStatus.setResponse(!(attr.isNoResponse()));
/* 363 */     HiRunStatus.setRunStatus(ctx, runStatus);
/*     */   }
/*     */ 
/*     */   public void beforeProcess(HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 380 */     super.beforeProcess(ctx);
/* 381 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 382 */     if (log.isInfo3Enabled()) {
/* 383 */       log.info3(this.sm.getString("HiTransactionCTLImpl.process00", ctx.getStrProp("SVR.name")));
/*     */ 
/* 385 */       log.info3(this.sm.getString("HiTransactionCTLImpl.process01", this.strCode));
/*     */     }
/* 387 */     initProcess(ctx);
/*     */   }
/*     */ 
/*     */   public void afterProcess(HiMessageContext ctx) throws HiException
/*     */   {
/* 392 */     HiMessage msg = ctx.getCurrentMsg();
/* 393 */     Logger log = HiLog.getLogger(msg);
/*     */ 
/* 395 */     if (log.isDebugEnabled()) {
/* 396 */       log.debug(this.sm.getString("HiTransactionCTLImpl.AfterProcess"));
/*     */     }
/* 398 */     super.afterProcess(ctx);
/*     */ 
/* 447 */     msg.setHeadItem("SCH", "rp");
/*     */ 
/* 450 */     if (msg.getHeadItemValSize("STC") > 1) {
/* 451 */       msg.delHeadItemVal("STC");
/*     */     }
/* 453 */     if (log.isInfo3Enabled())
/* 454 */       log.info3(this.sm.getString("HiTransactionCTLImpl.EndOfProcess"));
/*     */   }
/*     */ 
/*     */   public void loadAfter() throws HiException
/*     */   {
/* 459 */     super.popOwnerContext();
/*     */   }
/*     */ 
/*     */   public void addChilds(HiIEngineModel child) throws HiException {
/* 463 */     if (this.childs == null) {
/* 464 */       this.childs = new ArrayList();
/*     */     }
/* 466 */     if ((child instanceof HiElseIfProcess) || (child instanceof HiElseProcess)) {
/* 467 */       int nSize = this.childs.size();
/* 468 */       if (nSize <= 0) {
/* 469 */         throw new HiException("213304");
/*     */       }
/* 471 */       HiIfProcess ifChild = (HiIfProcess)this.childs.get(nSize - 1);
/* 472 */       ifChild.addControlNodes(child);
/* 473 */     } else if (child instanceof HiFunction) {
/* 474 */       HiFunction func = (HiFunction)child;
/* 475 */       this.context.setProperty(func.getName(), func);
/*     */     } else {
/* 477 */       this.childs.add(child);
/*     */     }
/*     */   }
/*     */ }