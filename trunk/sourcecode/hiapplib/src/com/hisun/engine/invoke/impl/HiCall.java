/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.engine.HiEngineModel;
/*     */ import com.hisun.engine.HiEngineStack;
/*     */ import com.hisun.engine.HiEngineUtilities;
/*     */ import com.hisun.engine.exception.HiErrorException;
/*     */ import com.hisun.engine.exception.HiExitException;
/*     */ import com.hisun.engine.exception.HiGotoException;
/*     */ import com.hisun.engine.exception.HiReturnException;
/*     */ import com.hisun.exception.HiAppException;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.exception.HiPrimaryException;
/*     */ import com.hisun.exception.HiResponseException;
/*     */ import com.hisun.exception.HiSQLException;
/*     */ import com.hisun.hiexpression.HiExpFactory;
/*     */ import com.hisun.hiexpression.HiExpression;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilib.HiLib;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.util.HashMap;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiCall extends HiEngineModel
/*     */ {
/*     */   private String strPackageName;
/*     */   private HiExpression packageNameExpr;
/*     */   private String strFunction;
/*     */   private HiExpression funcExpr;
/*     */   private HiAbstractApplication app;
/*     */   private String strTxnCod;
/*     */   private String[] _inParams;
/*     */   private String[] _outParams;
/*     */   private String _error;
/*     */ 
/*     */   public HiCall()
/*     */   {
/*  40 */     this.strPackageName = null;
/*  41 */     this.packageNameExpr = null;
/*     */ 
/*  43 */     this.strFunction = null;
/*     */ 
/*  45 */     this.funcExpr = null;
/*     */ 
/*  48 */     this.app = null;
/*     */ 
/*  51 */     this.strTxnCod = null;
/*     */ 
/*  54 */     this._inParams = null;
/*     */ 
/*  57 */     this._outParams = null;
/*     */ 
/*  59 */     this._error = null; }
/*     */ 
/*     */   public void setPackage(String strPackageName) {
/*  62 */     this.strPackageName = strPackageName;
/*  63 */     this.packageNameExpr = HiExpFactory.createExp(strPackageName);
/*     */   }
/*     */ 
/*     */   public String getNodeName() {
/*  67 */     return "Call";
/*     */   }
/*     */ 
/*     */   public void setFunction(String strFunction) {
/*  71 */     this.strFunction = strFunction;
/*  72 */     this.funcExpr = HiExpFactory.createExp(strFunction);
/*     */   }
/*     */ 
/*     */   public void setApplication(HiAbstractApplication obj)
/*     */   {
/*  77 */     this.app = obj;
/*     */   }
/*     */ 
/*     */   public void setTransaction(String txncod) {
/*  81 */     this.strTxnCod = txncod;
/*     */   }
/*     */ 
/*     */   public void setInput(String in) {
/*  85 */     this._inParams = StringUtils.split(in, " |");
/*     */   }
/*     */ 
/*     */   public void setOutput(String out) {
/*  89 */     this._outParams = StringUtils.split(out, " |");
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  96 */     int retcode = 0;
/*  97 */     HiMessage mess = ctx.getCurrentMsg();
/*  98 */     Logger log = HiLog.getLogger(mess);
/*  99 */     HiETF etfRoot = mess.getETFBody();
/* 100 */     long tm1 = System.currentTimeMillis();
/* 101 */     String tmp1 = null;
/* 102 */     if (this.strTxnCod != null) {
/* 103 */       tmp1 = this.strTxnCod;
/*     */     }
/*     */ 
/* 106 */     if (this.funcExpr != null) {
/* 107 */       tmp1 = this.funcExpr.getValue(ctx);
/*     */     }
/*     */     try
/*     */     {
/* 111 */       doProcess(ctx);
/*     */     } catch (HiResponseException e) {
/* 113 */       HiEngineStack.getEngineStack(ctx).push(this);
/* 114 */       throw e;
/*     */     } catch (HiPrimaryException e) {
/* 116 */       retcode = e.getRetCode();
/* 117 */       ctx.setBaseSource("RetCod", String.valueOf(retcode));
/* 118 */       etfRoot.setChildValue("MSG_TYP", "E");
/* 119 */       etfRoot.setChildValue("SQL_CD", String.valueOf(e.getSqlErrorCode()));
/*     */ 
/* 122 */       etfRoot.setChildValue("SQL_MSG", e.getSQLState());
/* 123 */       if (log.isInfoEnabled()) {
/* 124 */         log.info(sm.getString("HiExec.ReturnValue", tmp1, String.valueOf(retcode), String.valueOf(System.currentTimeMillis() - tm1)));
/*     */       }
/*     */ 
/* 129 */       errorProcess(ctx, e);
/*     */     }
/*     */     catch (HiSQLException e) {
/* 132 */       retcode = e.getRetCode();
/* 133 */       ctx.setBaseSource("RetCod", String.valueOf(retcode));
/* 134 */       etfRoot.setChildValue("MSG_TYP", "E");
/* 135 */       etfRoot.setChildValue("SQL_CD", String.valueOf(e.getSqlErrorCode()));
/*     */ 
/* 138 */       etfRoot.setChildValue("SQL_MSG", e.getSQLState());
/* 139 */       if (log.isInfo2Enabled()) {
/* 140 */         log.info2(sm.getString("HiExec.ReturnValue", tmp1, String.valueOf(retcode), String.valueOf(System.currentTimeMillis() - tm1)));
/*     */       }
/*     */ 
/* 146 */       errorProcess(ctx, e);
/*     */     } catch (HiAppException e) {
/* 148 */       retcode = e.getRetCode();
/* 149 */       ctx.setBaseSource("RetCod", String.valueOf(retcode));
/*     */ 
/* 151 */       if (log.isInfo2Enabled()) {
/* 152 */         log.info2(sm.getString("HiExec.ReturnValue", tmp1, String.valueOf(retcode), String.valueOf(System.currentTimeMillis() - tm1)));
/*     */       }
/*     */ 
/* 157 */       errorProcess(ctx, e);
/*     */     } catch (HiReturnException e) {
/*     */     }
/*     */     catch (HiExitException e) {
/* 161 */       throw e;
/*     */     } catch (HiException e) {
/* 163 */       retcode = -1;
/* 164 */       ctx.setBaseSource("RetCod", String.valueOf(retcode));
/* 165 */       if (log.isInfo2Enabled()) {
/* 166 */         log.info2(sm.getString("HiExec.ReturnValue", tmp1, String.valueOf(retcode), String.valueOf(System.currentTimeMillis() - tm1)));
/*     */       }
/*     */ 
/* 172 */       errorProcess(ctx, e);
/*     */     }
/* 174 */     retcode = NumberUtils.toInt(ctx.getStrProp("RetCod"));
/*     */ 
/* 176 */     if (log.isInfo2Enabled()) {
/* 177 */       log.info2(sm.getString("HiExec.ReturnValue", tmp1, String.valueOf(retcode), String.valueOf(System.currentTimeMillis() - tm1)));
/*     */     }
/*     */ 
/* 183 */     ctx.setBaseSource("RetCod", String.valueOf(retcode));
/*     */   }
/*     */ 
/*     */   public void doProcess(HiMessageContext ctx) throws HiException {
/* 187 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*     */ 
/* 189 */     if (log.isDebugEnabled()) {
/* 190 */       log.debug("HiCall.process(HiMessageContext) - start");
/*     */     }
/*     */ 
/* 193 */     if ((this.strTxnCod != null) && (this.app instanceof HiApplicationCTLImpl)) {
/* 194 */       if (log.isInfo2Enabled()) {
/* 195 */         log.info2(sm.getString("HiCall.process00", HiEngineUtilities.getCurFlowStep(), this.strTxnCod));
/*     */       }
/*     */ 
/* 198 */       HiTransactionCTLImpl txn = ((HiApplicationCTLImpl)this.app).getTransaction(this.strTxnCod);
/*     */ 
/* 201 */       HiEngineStack stack = HiEngineStack.getCurrentStack(ctx);
/* 202 */       txn.doProcess(ctx, stack);
/*     */     } else {
/* 204 */       String funcName = null;
/* 205 */       String packageName = null;
/* 206 */       if (this.funcExpr != null) {
/* 207 */         funcName = this.funcExpr.getValue(ctx);
/*     */       }
/* 209 */       if (this.packageNameExpr != null) {
/* 210 */         packageName = this.packageNameExpr.getValue(ctx);
/*     */       }
/*     */ 
/* 213 */       if ((packageName == null) && (funcName == null)) {
/* 214 */         throw new HiException("213337");
/*     */       }
/*     */ 
/* 217 */       if (packageName == null) {
/* 218 */         if (log.isInfo2Enabled()) {
/* 219 */           log.info2(sm.getString("HiCall.process01", HiEngineUtilities.getCurFlowStep(), funcName));
/*     */         }
/*     */ 
/* 222 */         HiFunction func = (HiFunction)ctx.getProperty("GLOBALFUNCTION", funcName);
/*     */ 
/* 224 */         doFunction(func, ctx);
/*     */       } else {
/* 226 */         if (log.isInfo2Enabled()) {
/* 227 */           log.info2(sm.getString("HiCall.process02", HiEngineUtilities.getCurFlowStep(), packageName, funcName));
/*     */         }
/*     */ 
/* 231 */         HashMap funcMap = (HashMap)ctx.getProperty("PACKAGEDECLARE", packageName);
/*     */ 
/* 234 */         HiFunction func = (HiFunction)funcMap.get(funcName);
/* 235 */         if (func == null) {
/* 236 */           throw new HiException("213338", packageName, funcName);
/*     */         }
/*     */ 
/* 240 */         doFunction(func, ctx);
/*     */       }
/*     */     }
/*     */ 
/* 244 */     if (log.isDebugEnabled())
/* 245 */       log.debug("HiCall.process(HiMessageContext) - end");
/*     */   }
/*     */ 
/*     */   private HiETF createNewETF(HiETF root, String[] lstInput, String[] lstInput1)
/*     */   {
/* 250 */     HiETF newETF = HiETFFactory.createETF();
/*     */ 
/* 252 */     String value = null;
/* 253 */     for (int i = 0; i < lstInput.length; ++i) {
/* 254 */       value = root.getChildValue(lstInput[i]);
/* 255 */       if (lstInput1 != null)
/* 256 */         newETF.setChildValue(lstInput1[i], value);
/*     */       else
/* 258 */         newETF.setChildValue(lstInput[i], value);
/*     */     }
/* 260 */     return newETF;
/*     */   }
/*     */ 
/*     */   private HiETF addETFtoOld(HiETF oldRoot, HiETF newRoot, String[] lstOutput, String[] lstOutput1)
/*     */   {
/* 265 */     String value = null;
/* 266 */     if ((lstOutput.length == 1) && (StringUtils.equalsIgnoreCase(lstOutput[0], "ETF"))) {
/* 267 */       oldRoot.combine(newRoot, true);
/* 268 */       return oldRoot;
/*     */     }
/* 270 */     for (int i = 0; i < lstOutput.length; ++i) {
/* 271 */       value = newRoot.getChildValue(lstOutput1[i]);
/* 272 */       oldRoot.setChildValue(lstOutput[i], value);
/*     */     }
/*     */ 
/* 275 */     return oldRoot;
/*     */   }
/*     */ 
/*     */   private void doFunction(HiFunction func, HiMessageContext messContext) throws HiException
/*     */   {
/* 280 */     HiMessage mess = messContext.getCurrentMsg();
/* 281 */     Logger log = HiLog.getLogger(mess);
/*     */ 
/* 283 */     if (log.isDebugEnabled()) {
/* 284 */       log.debug("HiCall.doFunction - start");
/*     */     }
/*     */ 
/* 287 */     HiMessage newMess = null;
/*     */ 
/* 293 */     if (this._inParams == null) {
/* 294 */       this._inParams = func.getInParams();
/*     */     }
/* 296 */     if ((func.getInParams() != null) && (this._inParams != null) && (this._inParams.length != func.getInParams().length))
/*     */     {
/* 298 */       throw new HiException("215122", func.getName() + ":input");
/*     */     }
/*     */ 
/* 302 */     if (this._outParams == null) {
/* 303 */       this._outParams = func.getOutParams();
/*     */     }
/*     */ 
/* 306 */     if ((func.getOutParams() != null) && (this._outParams != null) && (this._outParams.length != func.getOutParams().length))
/*     */     {
/* 308 */       throw new HiException("215122", func.getName() + ":output");
/*     */     }
/*     */ 
/* 312 */     if (this._inParams == null) {
/* 313 */       newMess = new HiMessage(mess);
/* 314 */       newMess.setBody(mess.getETFBody().cloneNode());
/*     */     } else {
/* 316 */       HiETF etfInput = createNewETF(mess.getETFBody(), this._inParams, func.getInParams());
/*     */ 
/* 318 */       newMess = mess.cloneNoBody();
/* 319 */       newMess.setBody(etfInput);
/*     */     }
/* 321 */     messContext.setCurrentMsg(newMess);
/*     */     try {
/* 323 */       HiProcess.process(func, messContext);
/*     */     } finally {
/* 325 */       messContext.setCurrentMsg(mess);
/*     */     }
/* 327 */     HiMessage outMsg = messContext.getResponseMsg();
/* 328 */     if ((this._outParams != null) && (func.getOutParams() != null)) {
/* 329 */       HiETF etfOutput = addETFtoOld((HiETF)outMsg.getBody(), (HiETF)newMess.getBody(), this._outParams, func.getOutParams());
/*     */ 
/* 331 */       outMsg.setBody(etfOutput);
/*     */     }
/*     */ 
/* 334 */     if (log.isDebugEnabled())
/* 335 */       log.debug("HiCall.doFunction() - end");
/*     */   }
/*     */ 
/*     */   public void errorProcess(HiMessageContext ctx, HiException ex)
/*     */     throws HiErrorException, HiGotoException, HiException
/*     */   {
/* 341 */     String tmp1 = null;
/* 342 */     if (this.strTxnCod != null) {
/* 343 */       tmp1 = this.strTxnCod;
/*     */     }
/*     */ 
/* 346 */     if (this.funcExpr != null) {
/* 347 */       tmp1 = this.funcExpr.getValue(ctx);
/*     */     }
/*     */ 
/* 350 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 351 */     if (this._error == null)
/*     */     {
/* 353 */       HiLog.logServiceError(ctx.getCurrentMsg(), ex);
/* 354 */       if (log.isInfoEnabled())
/* 355 */         log.info(sm.getString("HiExec.NoErrorProcess", tmp1));
/* 356 */       throw new HiErrorException(ex);
/*     */     }
/* 358 */     HiLog.logServiceWarn(ctx.getCurrentMsg(), ex);
/* 359 */     if (this._error.equals("IGNORE"))
/*     */     {
/* 361 */       HiETF etfRoot = ctx.getCurrentMsg().getETFBody();
/* 362 */       if (log.isInfoEnabled()) {
/* 363 */         log.info(sm.getString("HiExec.IgnoreErrorProcess", tmp1));
/*     */       }
/* 365 */       etfRoot.setChildValue("MSG_TYP", "E");
/* 366 */       etfRoot.setChildValue("RSP_CD", ex.getCode());
/* 367 */       etfRoot.setChildValue("RSP_MSG", ex.getAppMessage());
/* 368 */       return;
/*     */     }
/*     */ 
/* 371 */     String[] values = StringUtils.split(this._error, ":");
/* 372 */     if (values.length != 2) {
/* 373 */       log.error(sm.getString("HiExec.InvalidErrorProcess", getError()));
/*     */ 
/* 375 */       throw new HiException("213310", this._error);
/*     */     }
/*     */ 
/* 378 */     if (values[0].equals("GOTO")) {
/* 379 */       if (log.isInfoEnabled())
/* 380 */         log.info(sm.getString("HiExec.GoToErrorProcess", values[1]));
/* 381 */       HiGotoException e = new HiGotoException();
/* 382 */       e.seGototName(values[1]);
/* 383 */       throw e;
/*     */     }
/*     */     try
/*     */     {
/* 387 */       if (log.isInfoEnabled())
/* 388 */         log.info(sm.getString("HiExec.ErrorProcess", this._error));
/* 389 */       HiLib.invoke(this._error, new HiATLParam(), ctx);
/*     */     } catch (HiException e1) {
/* 391 */       HiLog.logServiceError(ctx.getCurrentMsg(), e1);
/*     */     }
/*     */ 
/* 394 */     if (ex != null)
/* 395 */       throw ex;
/*     */   }
/*     */ 
/*     */   public String getError() {
/* 399 */     return this._error;
/*     */   }
/*     */ 
/*     */   public void setError(String error) {
/* 403 */     this._error = error;
/*     */   }
/*     */ }