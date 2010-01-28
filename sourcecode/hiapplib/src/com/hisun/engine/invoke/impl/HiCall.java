 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.HiEngineStack;
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.engine.exception.HiErrorException;
 import com.hisun.engine.exception.HiExitException;
 import com.hisun.engine.exception.HiGotoException;
 import com.hisun.engine.exception.HiReturnException;
 import com.hisun.exception.HiAppException;
 import com.hisun.exception.HiException;
 import com.hisun.exception.HiPrimaryException;
 import com.hisun.exception.HiResponseException;
 import com.hisun.exception.HiSQLException;
 import com.hisun.hiexpression.HiExpFactory;
 import com.hisun.hiexpression.HiExpression;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilib.HiLib;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 import java.util.HashMap;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class HiCall extends HiEngineModel
 {
   private String strPackageName;
   private HiExpression packageNameExpr;
   private String strFunction;
   private HiExpression funcExpr;
   private HiAbstractApplication app;
   private String strTxnCod;
   private String[] _inParams;
   private String[] _outParams;
   private String _error;
 
   public HiCall()
   {
     this.strPackageName = null;
     this.packageNameExpr = null;
 
     this.strFunction = null;
 
     this.funcExpr = null;
 
     this.app = null;
 
     this.strTxnCod = null;
 
     this._inParams = null;
 
     this._outParams = null;
 
     this._error = null; }
 
   public void setPackage(String strPackageName) {
     this.strPackageName = strPackageName;
     this.packageNameExpr = HiExpFactory.createExp(strPackageName);
   }
 
   public String getNodeName() {
     return "Call";
   }
 
   public void setFunction(String strFunction) {
     this.strFunction = strFunction;
     this.funcExpr = HiExpFactory.createExp(strFunction);
   }
 
   public void setApplication(HiAbstractApplication obj)
   {
     this.app = obj;
   }
 
   public void setTransaction(String txncod) {
     this.strTxnCod = txncod;
   }
 
   public void setInput(String in) {
     this._inParams = StringUtils.split(in, " |");
   }
 
   public void setOutput(String out) {
     this._outParams = StringUtils.split(out, " |");
   }
 
   public void process(HiMessageContext ctx)
     throws HiException
   {
     int retcode = 0;
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     HiETF etfRoot = mess.getETFBody();
     long tm1 = System.currentTimeMillis();
     String tmp1 = null;
     if (this.strTxnCod != null) {
       tmp1 = this.strTxnCod;
     }
 
     if (this.funcExpr != null) {
       tmp1 = this.funcExpr.getValue(ctx);
     }
     try
     {
       doProcess(ctx);
     } catch (HiResponseException e) {
       HiEngineStack.getEngineStack(ctx).push(this);
       throw e;
     } catch (HiPrimaryException e) {
       retcode = e.getRetCode();
       ctx.setBaseSource("RetCod", String.valueOf(retcode));
       etfRoot.setChildValue("MSG_TYP", "E");
       etfRoot.setChildValue("SQL_CD", String.valueOf(e.getSqlErrorCode()));
 
       etfRoot.setChildValue("SQL_MSG", e.getSQLState());
       if (log.isInfoEnabled()) {
         log.info(sm.getString("HiExec.ReturnValue", tmp1, String.valueOf(retcode), String.valueOf(System.currentTimeMillis() - tm1)));
       }
 
       errorProcess(ctx, e);
     }
     catch (HiSQLException e) {
       retcode = e.getRetCode();
       ctx.setBaseSource("RetCod", String.valueOf(retcode));
       etfRoot.setChildValue("MSG_TYP", "E");
       etfRoot.setChildValue("SQL_CD", String.valueOf(e.getSqlErrorCode()));
 
       etfRoot.setChildValue("SQL_MSG", e.getSQLState());
       if (log.isInfo2Enabled()) {
         log.info2(sm.getString("HiExec.ReturnValue", tmp1, String.valueOf(retcode), String.valueOf(System.currentTimeMillis() - tm1)));
       }
 
       errorProcess(ctx, e);
     } catch (HiAppException e) {
       retcode = e.getRetCode();
       ctx.setBaseSource("RetCod", String.valueOf(retcode));
 
       if (log.isInfo2Enabled()) {
         log.info2(sm.getString("HiExec.ReturnValue", tmp1, String.valueOf(retcode), String.valueOf(System.currentTimeMillis() - tm1)));
       }
 
       errorProcess(ctx, e);
     } catch (HiReturnException e) {
     }
     catch (HiExitException e) {
       throw e;
     } catch (HiException e) {
       retcode = -1;
       ctx.setBaseSource("RetCod", String.valueOf(retcode));
       if (log.isInfo2Enabled()) {
         log.info2(sm.getString("HiExec.ReturnValue", tmp1, String.valueOf(retcode), String.valueOf(System.currentTimeMillis() - tm1)));
       }
 
       errorProcess(ctx, e);
     }
     retcode = NumberUtils.toInt(ctx.getStrProp("RetCod"));
 
     if (log.isInfo2Enabled()) {
       log.info2(sm.getString("HiExec.ReturnValue", tmp1, String.valueOf(retcode), String.valueOf(System.currentTimeMillis() - tm1)));
     }
 
     ctx.setBaseSource("RetCod", String.valueOf(retcode));
   }
 
   public void doProcess(HiMessageContext ctx) throws HiException {
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
 
     if (log.isDebugEnabled()) {
       log.debug("HiCall.process(HiMessageContext) - start");
     }
 
     if ((this.strTxnCod != null) && (this.app instanceof HiApplicationCTLImpl)) {
       if (log.isInfo2Enabled()) {
         log.info2(sm.getString("HiCall.process00", HiEngineUtilities.getCurFlowStep(), this.strTxnCod));
       }
 
       HiTransactionCTLImpl txn = ((HiApplicationCTLImpl)this.app).getTransaction(this.strTxnCod);
 
       HiEngineStack stack = HiEngineStack.getCurrentStack(ctx);
       txn.doProcess(ctx, stack);
     } else {
       String funcName = null;
       String packageName = null;
       if (this.funcExpr != null) {
         funcName = this.funcExpr.getValue(ctx);
       }
       if (this.packageNameExpr != null) {
         packageName = this.packageNameExpr.getValue(ctx);
       }
 
       if ((packageName == null) && (funcName == null)) {
         throw new HiException("213337");
       }
 
       if (packageName == null) {
         if (log.isInfo2Enabled()) {
           log.info2(sm.getString("HiCall.process01", HiEngineUtilities.getCurFlowStep(), funcName));
         }
 
         HiFunction func = (HiFunction)ctx.getProperty("GLOBALFUNCTION", funcName);
 
         doFunction(func, ctx);
       } else {
         if (log.isInfo2Enabled()) {
           log.info2(sm.getString("HiCall.process02", HiEngineUtilities.getCurFlowStep(), packageName, funcName));
         }
 
         HashMap funcMap = (HashMap)ctx.getProperty("PACKAGEDECLARE", packageName);
 
         HiFunction func = (HiFunction)funcMap.get(funcName);
         if (func == null) {
           throw new HiException("213338", packageName, funcName);
         }
 
         doFunction(func, ctx);
       }
     }
 
     if (log.isDebugEnabled())
       log.debug("HiCall.process(HiMessageContext) - end");
   }
 
   private HiETF createNewETF(HiETF root, String[] lstInput, String[] lstInput1)
   {
     HiETF newETF = HiETFFactory.createETF();
 
     String value = null;
     for (int i = 0; i < lstInput.length; ++i) {
       value = root.getChildValue(lstInput[i]);
       if (lstInput1 != null)
         newETF.setChildValue(lstInput1[i], value);
       else
         newETF.setChildValue(lstInput[i], value);
     }
     return newETF;
   }
 
   private HiETF addETFtoOld(HiETF oldRoot, HiETF newRoot, String[] lstOutput, String[] lstOutput1)
   {
     String value = null;
     if ((lstOutput.length == 1) && (StringUtils.equalsIgnoreCase(lstOutput[0], "ETF"))) {
       oldRoot.combine(newRoot, true);
       return oldRoot;
     }
     for (int i = 0; i < lstOutput.length; ++i) {
       value = newRoot.getChildValue(lstOutput1[i]);
       oldRoot.setChildValue(lstOutput[i], value);
     }
 
     return oldRoot;
   }
 
   private void doFunction(HiFunction func, HiMessageContext messContext) throws HiException
   {
     HiMessage mess = messContext.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
 
     if (log.isDebugEnabled()) {
       log.debug("HiCall.doFunction - start");
     }
 
     HiMessage newMess = null;
 
     if (this._inParams == null) {
       this._inParams = func.getInParams();
     }
     if ((func.getInParams() != null) && (this._inParams != null) && (this._inParams.length != func.getInParams().length))
     {
       throw new HiException("215122", func.getName() + ":input");
     }
 
     if (this._outParams == null) {
       this._outParams = func.getOutParams();
     }
 
     if ((func.getOutParams() != null) && (this._outParams != null) && (this._outParams.length != func.getOutParams().length))
     {
       throw new HiException("215122", func.getName() + ":output");
     }
 
     if (this._inParams == null) {
       newMess = new HiMessage(mess);
       newMess.setBody(mess.getETFBody().cloneNode());
     } else {
       HiETF etfInput = createNewETF(mess.getETFBody(), this._inParams, func.getInParams());
 
       newMess = mess.cloneNoBody();
       newMess.setBody(etfInput);
     }
     messContext.setCurrentMsg(newMess);
     try {
       HiProcess.process(func, messContext);
     } finally {
       messContext.setCurrentMsg(mess);
     }
     HiMessage outMsg = messContext.getResponseMsg();
     if ((this._outParams != null) && (func.getOutParams() != null)) {
       HiETF etfOutput = addETFtoOld((HiETF)outMsg.getBody(), (HiETF)newMess.getBody(), this._outParams, func.getOutParams());
 
       outMsg.setBody(etfOutput);
     }
 
     if (log.isDebugEnabled())
       log.debug("HiCall.doFunction() - end");
   }
 
   public void errorProcess(HiMessageContext ctx, HiException ex)
     throws HiErrorException, HiGotoException, HiException
   {
     String tmp1 = null;
     if (this.strTxnCod != null) {
       tmp1 = this.strTxnCod;
     }
 
     if (this.funcExpr != null) {
       tmp1 = this.funcExpr.getValue(ctx);
     }
 
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (this._error == null)
     {
       HiLog.logServiceError(ctx.getCurrentMsg(), ex);
       if (log.isInfoEnabled())
         log.info(sm.getString("HiExec.NoErrorProcess", tmp1));
       throw new HiErrorException(ex);
     }
     HiLog.logServiceWarn(ctx.getCurrentMsg(), ex);
     if (this._error.equals("IGNORE"))
     {
       HiETF etfRoot = ctx.getCurrentMsg().getETFBody();
       if (log.isInfoEnabled()) {
         log.info(sm.getString("HiExec.IgnoreErrorProcess", tmp1));
       }
       etfRoot.setChildValue("MSG_TYP", "E");
       etfRoot.setChildValue("RSP_CD", ex.getCode());
       etfRoot.setChildValue("RSP_MSG", ex.getAppMessage());
       return;
     }
 
     String[] values = StringUtils.split(this._error, ":");
     if (values.length != 2) {
       log.error(sm.getString("HiExec.InvalidErrorProcess", getError()));
 
       throw new HiException("213310", this._error);
     }
 
     if (values[0].equals("GOTO")) {
       if (log.isInfoEnabled())
         log.info(sm.getString("HiExec.GoToErrorProcess", values[1]));
       HiGotoException e = new HiGotoException();
       e.seGototName(values[1]);
       throw e;
     }
     try
     {
       if (log.isInfoEnabled())
         log.info(sm.getString("HiExec.ErrorProcess", this._error));
       HiLib.invoke(this._error, new HiATLParam(), ctx);
     } catch (HiException e1) {
       HiLog.logServiceError(ctx.getCurrentMsg(), e1);
     }
 
     if (ex != null)
       throw ex;
   }
 
   public String getError() {
     return this._error;
   }
 
   public void setError(String error) {
     this._error = error;
   }
 }