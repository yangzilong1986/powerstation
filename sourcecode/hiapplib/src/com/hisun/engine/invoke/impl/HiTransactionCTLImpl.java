 package com.hisun.engine.invoke.impl;
 
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.HiEngineStack;
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.engine.exception.HiErrorException;
 import com.hisun.engine.exception.HiExitException;
 import com.hisun.engine.exception.HiGotoException;
 import com.hisun.engine.exception.HiReturnException;
 import com.hisun.engine.invoke.HiIAction;
 import com.hisun.engine.invoke.HiIEngineModel;
 import com.hisun.exception.HiException;
 import com.hisun.exception.HiResponseException;
 import com.hisun.hiexpression.HiExpFactory;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilib.HiLib;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.service.HiInfrastructure;
 import com.hisun.util.HiStringManager;
 import com.hisun.util.HiStringUtils;
 import com.hisun.util.HiThreadPool;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 
 public class HiTransactionCTLImpl extends HiAbstractTransaction
 {
   private String strError;
   protected boolean isFunc = false;
 
   public HiTransactionCTLImpl() {
     this.context = HiContext.createAndPushContext();
     this.log_id = this.context.getStrProp("__LOG_ID");
     if (this.log_id != null)
       this.log_idExpr = HiExpFactory.createExp(this.log_id);
   }
 
   public void setError(String strError)
   {
     this.strError = strError;
   }
 
   public String getError()
   {
     return this.strError;
   }
 
   public void process(HiMessageContext ctx)
     throws HiException
   {
     HiFunction func;
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
 
     HiEngineStack stack = HiEngineStack.getCurrentStack(ctx);
     try {
       func = (HiFunction)this.context.getProperty("_before");
       if (func == null) {
         func = (HiFunction)this.context.getProperty("GLOBALFUNCTION", "_before");
       }
 
       if (func != null) {
         HiProcess.process(func, ctx);
       }
 
       doProcess(ctx, stack);
     } catch (Throwable e) {
       if (!(e instanceof HiExitException))
         HiLog.logServiceError(msg, HiException.makeException(e));
     }
     finally {
       try {
         HiFunction func = (HiFunction)this.context.getProperty("_after");
         if (func == null) {
           func = (HiFunction)this.context.getProperty("GLOBALFUNCTION", "_after");
         }
 
         if (func != null)
           HiProcess.process(func, ctx);
       }
       finally {
         if (ctx.getDataBaseUtil() != null)
           ctx.getDataBaseUtil().closeAll();
       }
     }
     HiRunStatus runStatus = HiRunStatus.getRunStatus(ctx);
     if (runStatus.isResponse())
       return;
     msg.setHeadItem("RSP", "0");
   }
 
   public void doProcess(HiMessageContext ctx, HiEngineStack stack)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
 
     HiETF etfRoot = (HiETF)msg.getBody();
 
     List childs = super.getChilds();
     String strGotoName = null;
     HiEngineModel rsEn = null;
     if (stack != null) {
       rsEn = (HiEngineModel)stack.pop();
     }
     if (childs == null) {
       log.warn("no execute step");
       return;
     }
 
     for (int i = 0; i < childs.size(); ++i) {
       HiIAction child = (HiIAction)childs.get(i);
 
       if (rsEn != null) {
         if (child != rsEn) {
           continue;
         }
         rsEn = null;
         if (stack.size() == 0) {
           continue;
         }
       }
 
       HiEngineUtilities.setCurFlowStep(i);
 
       if (strGotoName != null) {
         if (!(child instanceof HiLabel))
           continue;
         HiLabel lable = (HiLabel)child;
         label469: if (strGotoName.equalsIgnoreCase(lable.getName()))
           strGotoName = null;
       }
       else
       {
         try
         {
           HiProcess.process(child, ctx);
         } catch (HiResponseException e) {
           if (log.isInfoEnabled()) {
             log.info("PutResponse is start");
           }
           HiEngineStack.getEngineStack(ctx).push(this);
           HiMessageContext newCtx = new HiMessageContext(ctx);
           HiThreadPool pool = HiInfrastructure.getThreadPoolService("RESPONSE_THREAD");
 
           newCtx.setDataBaseUtil(ctx.getDataBaseUtil());
           ctx.setDataBaseUtil(null);
           HiEngineStack es = HiEngineStack.getEngineStack(ctx);
           es.setMessContext(newCtx);
           pool.execute(es);
           return;
         } catch (HiGotoException goEx) {
           if (strGotoName == null)
             strGotoName = goEx.getGotoName();
         } catch (HiReturnException e) {
           return;
         } catch (HiExitException e) {
           if (this.isFunc) {
             throw e;
           }
           return;
         }
         catch (HiErrorException e) {
           if (this.strError == null) {
             errorProcess(ctx, "PUB:DefaultErrorProc", e);
             return;
           }
 
           if (this.strError.equals("IGNORE")) {
             if (log.isInfoEnabled()) {
               log.info(this.sm.getString("HiTransactionCTLImpl.IgnoreErrorProcess"));
             }
 
             return;
           }
 
           String[] values = StringUtils.split(this.strError, ":");
           if (values.length != 2) {
             log.error(this.sm.getString("HiTransactionCTLImpl.InvalidErrorProcess", this.strError));
 
             throw new HiException("213311", String.valueOf(values.length));
           }
 
           if (values[0].equals("GOTO")) {
             strGotoName = values[1];
             break label469:
           }
           errorProcess(ctx, this.strError, e);
           return;
         } catch (HiException e) {
           etfRoot.setChildValue("MSG_TYP", "E");
           etfRoot.setChildValue("RSP_CD", e.getCode());
           etfRoot.setChildValue("RSP_MSG", e.getAppMessage());
           HiLog.logServiceError(msg, e);
           return;
         }
       }
     }
   }
 
   public void errorProcess(HiMessageContext ctx, String func, HiErrorException e) {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     HiETF etfRoot = msg.getETFBody();
     try
     {
       if (log.isInfoEnabled()) {
         log.info(this.sm.getString("HiTransactionCTLImpl.DefaultErrorProcess", "PUB:DefaultErrorProc"));
       }
 
       etfRoot.setChildValue("MSG_TYP", "E");
       etfRoot.setChildValue("RSP_CD", e.getCode());
       etfRoot.setChildValue("RSP_MSG", e.getAppMessage());
       HiLib.invoke(func, new HiATLParam(), ctx);
     } catch (Exception e1) {
       etfRoot.setChildValue("MSG_TYP", "E");
       etfRoot.setChildValue("RSP_CD", "213336");
       etfRoot.setChildValue("RSP_MSG", this.sm.getString("213336"));
 
       HiLog.logServiceError(msg, HiException.makeException("213336", e1));
 
       return;
     }
   }
 
   public void initProcess(HiMessageContext ctx)
     throws HiException
   {
     setInitData(ctx);
   }
 
   public void setBussinessData(HiMessageContext ctx) throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     HiETF etfRoot = (HiETF)msg.getBody();
     String strActDat = null;
 
     String sqlCmd = "SELECT ACTDAT,SYSID FROM PUBPLTINF";
     List lRec = ctx.getDataBaseUtil().execQuery(sqlCmd);
     if (log.isDebugEnabled()) {
       log.debug(this.sm.getString("HiTransactionCTLImpl.GetSysInfoSql", sqlCmd));
     }
 
     if ((lRec == null) || (lRec.size() == 0)) {
       log.fatal(this.sm.getString("HiTransactionCTLImpl.GetSysInfoError"));
       throw new HiException("211007");
     }
 
     HashMap tmp = (HashMap)lRec.get(0);
 
     etfRoot.setChildValue("FSysId", (String)tmp.get("SYSID"));
 
     strActDat = (String)tmp.get("ACTDAT");
     etfRoot.setChildValue("ActDat", strActDat);
     ctx.setBaseSource("FSysId", (String)tmp.get("SYSID"));
     ctx.setBaseSource("ActDat", strActDat);
 
     if (log.isInfoEnabled()) {
       log.info(this.sm.getString("HiTransactionCTLImpl.GetActDat", strActDat));
       log.info(this.sm.getString("HiTransactionCTLImpl.GetAppCode", ctx.getStrProp("app_code")));
     }
 
     String AplSub = etfRoot.getChildValue("AplSub");
     if (log.isInfoEnabled()) {
       log.info("取AplSub,值为:[" + AplSub + "]");
     }
 
     String AplCls = etfRoot.getChildValue("AplCls");
     if (AplCls == null) {
       throw new HiException("220058", "AplCls");
     }
 
     if (log.isInfoEnabled()) {
       log.info("取AplCls,值为:[" + AplCls + "]");
     }
 
     String busTyp = null;
     sqlCmd = HiStringUtils.format("SELECT BUSTYP FROM PUBAPLBUS WHERE APLCLS='%s' AND (APLSUB='%s' OR APLSUB=' ')", AplCls, AplSub);
 
     lRec = ctx.getDataBaseUtil().execQuery(sqlCmd);
 
     if (lRec.size() != 0) {
       tmp = (HashMap)lRec.get(0);
       busTyp = (String)tmp.get("BUSTYP");
       ctx.setBaseSource("BusTyp", busTyp);
       if (log.isInfoEnabled()) {
         log.info("--->业务类型 = " + busTyp);
       }
     }
 
     if (StringUtils.isEmpty(busTyp)) {
       return;
     }
     etfRoot.setChildValue("BusTyp", busTyp);
 
     lRec.clear();
     sqlCmd = HiStringUtils.format("SELECT ONLTBL FROM PUBJNLDEF WHERE BUSTYP = '%s' FETCH FIRST ROWS ONLY", busTyp);
 
     lRec = ctx.getDataBaseUtil().execQuery(sqlCmd);
     if (lRec.size() == 0) {
       return;
     }
     tmp = (HashMap)lRec.get(0);
     String onlTbl = (String)tmp.get("ONLTBL");
 
     if (log.isInfoEnabled()) {
       log.info("--->流水表 = " + onlTbl);
     }
     ctx.setJnlTable(onlTbl);
     ctx.setBaseSource("LstTab", onlTbl);
 
     HiAttributesHelper attr = HiAttributesHelper.getAttribute(ctx);
 
     if (attr.isNoChk())
       etfRoot.setChildValue("MstChk", "0");
     else {
       etfRoot.setChildValue("MstChk", "1");
     }
 
     etfRoot.setChildValue("ItgTyp", attr.integtype());
     etfRoot.setChildValue("TxnTyp", attr.getTxnTyp());
     etfRoot.setChildValue("FRspCd", "211007");
   }
 
   public void setInitData(HiMessageContext ctx) throws HiException {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     HiRunStatus runStatus = new HiRunStatus();
     HiAttributesHelper attr = HiAttributesHelper.getAttribute(ctx);
     runStatus.setResponse(!(attr.isNoResponse()));
     HiRunStatus.setRunStatus(ctx, runStatus);
   }
 
   public void beforeProcess(HiMessageContext ctx)
     throws HiException
   {
     super.beforeProcess(ctx);
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (log.isInfo3Enabled()) {
       log.info3(this.sm.getString("HiTransactionCTLImpl.process00", ctx.getStrProp("SVR.name")));
 
       log.info3(this.sm.getString("HiTransactionCTLImpl.process01", this.strCode));
     }
     initProcess(ctx);
   }
 
   public void afterProcess(HiMessageContext ctx) throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
 
     if (log.isDebugEnabled()) {
       log.debug(this.sm.getString("HiTransactionCTLImpl.AfterProcess"));
     }
     super.afterProcess(ctx);
 
     msg.setHeadItem("SCH", "rp");
 
     if (msg.getHeadItemValSize("STC") > 1) {
       msg.delHeadItemVal("STC");
     }
     if (log.isInfo3Enabled())
       log.info3(this.sm.getString("HiTransactionCTLImpl.EndOfProcess"));
   }
 
   public void loadAfter() throws HiException
   {
     super.popOwnerContext();
   }
 
   public void addChilds(HiIEngineModel child) throws HiException {
     if (this.childs == null) {
       this.childs = new ArrayList();
     }
     if ((child instanceof HiElseIfProcess) || (child instanceof HiElseProcess)) {
       int nSize = this.childs.size();
       if (nSize <= 0) {
         throw new HiException("213304");
       }
       HiIfProcess ifChild = (HiIfProcess)this.childs.get(nSize - 1);
       ifChild.addControlNodes(child);
     } else if (child instanceof HiFunction) {
       HiFunction func = (HiFunction)child;
       this.context.setProperty(func.getName(), func);
     } else {
       this.childs.add(child);
     }
   }
 }