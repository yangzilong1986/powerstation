 package com.hisun.engine.invoke.impl;
 
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.HiEngineStack;
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.engine.exception.HiErrorException;
 import com.hisun.engine.exception.HiGotoException;
 import com.hisun.exception.HiAppException;
 import com.hisun.exception.HiException;
 import com.hisun.exception.HiPrimaryException;
 import com.hisun.exception.HiResponseException;
 import com.hisun.exception.HiSQLException;
 import com.hisun.hiexpression.HiExpFactory;
 import com.hisun.hiexpression.HiExpression;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilib.HiLibManager;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.stat.util.HiStats;
 import com.hisun.stat.util.IStat;
 import com.hisun.util.HiStringManager;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.Map.Entry;
 import java.util.Set;
 import org.apache.commons.lang.StringUtils;
 
 public class HiExec extends HiEngineModel
 {
   private String strError;
   private String strFunction;
   private HiATLParam argsMap;
   public static HiStringManager sm = HiStringManager.getManager();
   private IStat stat;
   private int libType;
   private int errLibType;
   private static final String GOTO = "GOTO";
   private static final String IGNORE = "IGNORE";
 
   public HiExec()
   {
     this.argsMap = new HiATLParam();
 
     this.stat = null;
 
     this.libType = 0;
 
     this.errLibType = 0;
   }
 
   public void setFunc(String strName)
     throws HiException
   {
     this.strFunction = strName;
 
     this.libType = HiLibManager.loadComponent(strName);
     this.stat = HiStats.getState("Exec_" + strName);
   }
 
   public String getFunc()
   {
     return this.strFunction;
   }
 
   public void setError(String strName)
     throws HiException
   {
     this.strError = strName;
     if ((StringUtils.equalsIgnoreCase(strName, "IGNORE")) || (strName.startsWith("GOTO:")))
       return;
     this.errLibType = HiLibManager.loadComponent(strName);
   }
 
   public String getError()
   {
     return this.strError;
   }
 
   public void setArgs(String strArgName, String strArgValue)
     throws HiException
   {
     if (StringUtils.isEmpty(strArgName)) {
       throw new HiException("213307");
     }
 
     String name = strArgName.toUpperCase();
 
     if (StringUtils.isEmpty(strArgValue)) {
       this.argsMap.put(name, null);
     } else {
       String[] args = strArgValue.split("\\|");
       HiExpression[] exps = new HiExpression[args.length];
       for (int i = 0; i < exps.length; ++i) {
         exps[i] = HiExpFactory.createExp(args[i]);
       }
       this.argsMap.put(name, exps);
     }
   }
 
   public HiATLParam getArgs()
   {
     return this.argsMap;
   }
 
   private HiATLParam getRealArgs(HiATLParam args, HiMessageContext context)
     throws HiException
   {
     Logger log = HiLog.getLogger(context.getCurrentMsg());
     HiATLParam newArgs = new HiATLParam();
 
     HashMap componentParam = (HashMap)context.getProperty("@PARA", this.strFunction);
 
     if (componentParam != null) {
       Iterator iter = componentParam.entrySet().iterator();
       while (iter.hasNext()) {
         Map.Entry entry = (Map.Entry)iter.next();
         newArgs.put((String)entry.getKey(), entry.getValue());
       }
     }
 
     for (int i = 0; i < args.size(); ++i) {
       HiExpression[] exps = (HiExpression[])(HiExpression[])args.getValueObject(i);
       StringBuffer buf = new StringBuffer();
       String name = args.getName(i);
       StringBuffer exprName = new StringBuffer();
       int len = (exps != null) ? exps.length : 0;
       for (int j = 0; j < len; ++j) {
         HiExpression exp = exps[j];
         String realValue = null;
         if (exp != null) {
           if (log.isDebugEnabled()) {
             log.debug(sm.getString("HiExec.BeforeGetArgsValue", String.valueOf(i + 1), name, exp.getExp()));
           }
 
           realValue = exp.getValue(context);
           if (log.isDebugEnabled()) {
             log.debug("name [" + name + "] value[" + realValue + "] oldValue[" + exp.getExp() + "]");
           }
 
           buf.append(realValue);
           if (j < exps.length - 1) {
             buf.append("|");
           }
         }
         exprName.append(exp.getExp());
         if (j < exps.length - 1) {
           exprName.append("|");
         }
       }
       if (len > 1) {
         buf.append("|");
         exprName.append("|");
       }
       newArgs.put(name, buf.toString());
       if (!(log.isInfo2Enabled()))
         continue;
       log.info2(sm.getString("HiExec.AfterGetArgsValue", String.valueOf(i + 1), name, exprName.toString(), buf.toString()));
     }
 
     return newArgs;
   }
 
   public void process(HiMessageContext messContext)
     throws HiException
   {
     long l1 = System.currentTimeMillis();
     long size = Runtime.getRuntime().freeMemory();
 
     HiMessage mess = messContext.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     long tm1 = System.currentTimeMillis();
     if (log.isInfo2Enabled()) {
       log.info2(sm.getString("HiExec.process00", HiEngineUtilities.getCurFlowStep(), "Exec", getFunc()));
     }
 
     HiETF etfRoot = mess.getETFBody();
 
     int retcode = -1;
     try {
       HiATLParam params = getRealArgs(this.argsMap, messContext);
       Integer ret = (Integer)HiLibManager.invoke(getFunc(), params, messContext, this.libType);
 
       params.clear();
       retcode = ret.intValue();
     } catch (HiResponseException e) {
       HiEngineStack.getEngineStack(messContext).push(this);
       throw e;
     } catch (HiPrimaryException e) {
       retcode = e.getRetCode();
       messContext.setBaseSource("RetCod", String.valueOf(retcode));
 
       etfRoot.setChildValue("MSG_TYP", "E");
       etfRoot.setChildValue("SQL_CD", String.valueOf(e.getSqlErrorCode()));
 
       etfRoot.setChildValue("SQL_MSG", e.getSQLState());
       if (log.isInfo2Enabled()) {
         log.info2(sm.getString("HiExec.ReturnValue", getFunc(), String.valueOf(retcode), String.valueOf(System.currentTimeMillis() - tm1)));
       }
 
       errorProcess(messContext, e);
     }
     catch (HiSQLException e) {
       retcode = e.getRetCode();
       messContext.setBaseSource("RetCod", String.valueOf(retcode));
 
       etfRoot.setChildValue("MSG_TYP", "E");
       etfRoot.setChildValue("SQL_CD", String.valueOf(e.getSqlErrorCode()));
 
       etfRoot.setChildValue("SQL_MSG", e.getSQLState());
       if (log.isInfo2Enabled()) {
         log.info2(sm.getString("HiExec.ReturnValue", getFunc(), String.valueOf(retcode), String.valueOf(System.currentTimeMillis() - tm1)));
       }
 
       errorProcess(messContext, e);
     } catch (HiAppException e) {
       retcode = e.getRetCode();
 
       if (log.isInfo2Enabled()) {
         log.info2(sm.getString("HiExec.ReturnValue", getFunc(), String.valueOf(retcode), String.valueOf(System.currentTimeMillis() - tm1)));
       }
 
       errorProcess(messContext, e);
     }
     catch (HiException e)
     {
       retcode = -1;
       messContext.setBaseSource("RetCod", String.valueOf(retcode));
 
       if (log.isInfo2Enabled()) {
         log.info2(sm.getString("HiExec.ReturnValue", getFunc(), String.valueOf(retcode), String.valueOf(System.currentTimeMillis() - tm1)));
       }
 
       errorProcess(messContext, e);
     }
     if (log.isInfo2Enabled()) {
       log.info2(sm.getString("HiExec.ReturnValue", getFunc(), String.valueOf(retcode), String.valueOf(System.currentTimeMillis() - tm1)));
     }
 
     messContext.setBaseSource("RetCod", String.valueOf(retcode));
 
     size -= Runtime.getRuntime().freeMemory();
     l1 = System.currentTimeMillis() - l1;
     if (this.stat != null)
       this.stat.once(l1, size);
   }
 
   public void errorProcess(HiMessageContext ctx, HiException ex) throws HiErrorException, HiGotoException, HiException
   {
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     String AppId = ctx.getCurrentMsg().getHeadItem("APP");
 
     if (this.strError == null)
     {
       HiLog.logServiceError(ctx.getCurrentMsg(), ex);
       if (log.isInfoEnabled())
         log.info(sm.getString("HiExec.NoErrorProcess", getFunc()));
       throw new HiErrorException(ex);
     }
 
     HiLog.logServiceWarn(ctx.getCurrentMsg(), ex);
 
     if (this.strError.equals("IGNORE"))
     {
       HiETF etfRoot = ctx.getCurrentMsg().getETFBody();
       if (log.isInfoEnabled()) {
         log.info(sm.getString("HiExec.IgnoreErrorProcess", getFunc()));
       }
 
       etfRoot.setChildValue("MSG_TYP", "E");
       etfRoot.setChildValue("RSP_CD", ex.getCode());
       etfRoot.setChildValue("RSP_MSG", ex.getAppMessage());
       return;
     }
 
     String[] values = StringUtils.split(this.strError, ":");
     if (values.length != 2) {
       log.error(sm.getString("HiExec.InvalidErrorProcess", getError()));
 
       throw new HiException("213310", this.strError);
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
       if (log.isInfoEnabled()) {
         log.info(sm.getString("HiExec.ErrorProcess", this.strError));
       }
       HiLibManager.invoke(this.strError, new HiATLParam(), ctx, this.errLibType);
     } catch (HiException e1) {
       HiLog.logServiceError(ctx.getCurrentMsg(), e1);
     }
 
     if (ex != null)
       throw ex;
   }
 
   public String getNodeName() {
     return "Exec";
   }
 
   public String toString() {
     String strNodeName = super.toString();
     if (this.strError != null)
       strNodeName = strNodeName + ":func[" + this.strFunction + "],error[" + this.strError + "]";
     else
       strNodeName = strNodeName + ":func[" + this.strFunction + "]";
     return strNodeName;
   }
 }