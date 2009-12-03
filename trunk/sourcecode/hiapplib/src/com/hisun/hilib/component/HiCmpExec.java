/*     */ package com.hisun.hilib.component;
/*     */ 
/*     */ import com.hisun.engine.HiEngineUtilities;
/*     */ import com.hisun.engine.exception.HiErrorException;
/*     */ import com.hisun.engine.exception.HiGotoException;
/*     */ import com.hisun.exception.HiAppException;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.exception.HiPrimaryException;
/*     */ import com.hisun.exception.HiSQLException;
/*     */ import com.hisun.hiexpression.HiExpFactory;
/*     */ import com.hisun.hiexpression.HiExpression;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilib.HiLibManager;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.stat.util.HiStats;
/*     */ import com.hisun.stat.util.IStat;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiCmpExec
/*     */ {
/*     */   private String strError;
/*     */   private String strFunction;
/*  42 */   private HiATLParam argsMap = new HiATLParam();
/*     */ 
/*  44 */   public static HiStringManager sm = HiStringManager.getManager();
/*  45 */   private IStat stat = null;
/*     */ 
/*  48 */   private int libType = 0;
/*     */ 
/*  50 */   private int errLibType = 0;
/*     */   private static final String GOTO = "GOTO";
/*     */   private static final String IGNORE = "IGNORE";
/*     */ 
/*     */   public HiCmpExec(String func)
/*     */     throws HiException
/*     */   {
/*  56 */     setFunc(func);
/*  57 */     setError("IGNORE");
/*     */   }
/*     */ 
/*     */   public HiCmpExec(String func, String error) throws HiException
/*     */   {
/*  62 */     setFunc(func);
/*  63 */     setError(error);
/*     */   }
/*     */ 
/*     */   public HiCmpExec(String func, String argName, String argVal) throws HiException
/*     */   {
/*  68 */     setFunc(func);
/*  69 */     setArgs(argName, argVal);
/*  70 */     setError("IGNORE");
/*     */   }
/*     */ 
/*     */   public HiCmpExec(String func, Map args) throws HiException {
/*  74 */     setFunc(func);
/*  75 */     Iterator it = args.keySet().iterator();
/*     */ 
/*  77 */     while (it.hasNext())
/*     */     {
/*  79 */       String argsName = (String)it.next();
/*  80 */       setArgs(argsName, (String)args.get(argsName));
/*     */     }
/*  82 */     setError("IGNORE");
/*     */   }
/*     */ 
/*     */   public HiCmpExec(String func, Map args, String error) throws HiException {
/*  86 */     setFunc(func);
/*  87 */     Iterator it = args.keySet().iterator();
/*     */ 
/*  89 */     while (it.hasNext())
/*     */     {
/*  91 */       String argsName = (String)it.next();
/*  92 */       setArgs(argsName, (String)args.get(argsName));
/*     */     }
/*  94 */     setError(error);
/*     */   }
/*     */ 
/*     */   public void setFunc(String strName)
/*     */     throws HiException
/*     */   {
/* 102 */     this.strFunction = strName;
/*     */ 
/* 104 */     this.libType = HiLibManager.loadComponent(strName);
/* 105 */     this.stat = HiStats.getState("Exec_" + strName);
/*     */   }
/*     */ 
/*     */   public String getFunc()
/*     */   {
/* 112 */     return this.strFunction;
/*     */   }
/*     */ 
/*     */   public void setError(String strName)
/*     */     throws HiException
/*     */   {
/* 119 */     this.strError = strName;
/* 120 */     if ((StringUtils.equalsIgnoreCase(strName, "IGNORE")) || (strName.startsWith("GOTO:")))
/*     */       return;
/* 122 */     this.errLibType = HiLibManager.loadComponent(strName);
/*     */   }
/*     */ 
/*     */   public String getError()
/*     */   {
/* 130 */     return this.strError;
/*     */   }
/*     */ 
/*     */   public void setArgs(String strArgName, String strArgValue)
/*     */     throws HiException
/*     */   {
/* 138 */     if (StringUtils.isEmpty(strArgName)) {
/* 139 */       throw new HiException("213307");
/*     */     }
/* 141 */     String name = strArgName.toUpperCase();
/* 142 */     if (StringUtils.isEmpty(strArgValue)) {
/* 143 */       this.argsMap.put(name, null);
/*     */     } else {
/* 145 */       String[] args = strArgValue.split("\\|");
/* 146 */       HiExpression[] exps = new HiExpression[args.length];
/* 147 */       for (int i = 0; i < exps.length; ++i) {
/* 148 */         exps[i] = HiExpFactory.createExp(args[i]);
/*     */       }
/* 150 */       this.argsMap.put(name, exps);
/*     */     }
/*     */   }
/*     */ 
/*     */   public HiATLParam getArgs()
/*     */   {
/* 159 */     return this.argsMap;
/*     */   }
/*     */ 
/*     */   private HiATLParam getRealArgs(HiATLParam args, HiMessageContext context)
/*     */     throws HiException
/*     */   {
/* 169 */     Logger log = HiLog.getLogger(context.getCurrentMsg());
/* 170 */     HiATLParam newArgs = new HiATLParam();
/*     */ 
/* 173 */     HashMap componentParam = (HashMap)context.getProperty("@PARA", this.strFunction);
/*     */ 
/* 175 */     if (componentParam != null) {
/* 176 */       Iterator iter = componentParam.entrySet().iterator();
/* 177 */       while (iter.hasNext()) {
/* 178 */         Map.Entry entry = (Map.Entry)iter.next();
/* 179 */         newArgs.put((String)entry.getKey(), entry.getValue());
/*     */       }
/*     */     }
/*     */ 
/* 183 */     for (int i = 0; i < args.size(); ++i) {
/* 184 */       HiExpression[] exps = (HiExpression[])(HiExpression[])args.getValueObject(i);
/* 185 */       StringBuffer buf = new StringBuffer();
/* 186 */       String name = args.getName(i);
/* 187 */       StringBuffer exprName = new StringBuffer();
/* 188 */       int len = (exps != null) ? exps.length : 0;
/* 189 */       for (int j = 0; j < len; ++j) {
/* 190 */         HiExpression exp = exps[j];
/* 191 */         String realValue = null;
/* 192 */         if (exp != null) {
/* 193 */           if (log.isDebugEnabled()) {
/* 194 */             log.debug(sm.getString("HiExec.BeforeGetArgsValue", String.valueOf(i + 1), name, exp.getExp()));
/*     */           }
/*     */ 
/* 198 */           realValue = exp.getValue(context);
/* 199 */           if (log.isDebugEnabled()) {
/* 200 */             log.debug("name [" + name + "] value[" + realValue + "] oldValue[" + exp.getExp() + "]");
/*     */           }
/*     */ 
/* 203 */           buf.append(realValue);
/* 204 */           if (j < exps.length - 1) {
/* 205 */             buf.append("|");
/*     */           }
/*     */         }
/* 208 */         exprName.append(exp.getExp());
/* 209 */         if (j < exps.length - 1) {
/* 210 */           exprName.append("|");
/*     */         }
/*     */       }
/* 213 */       if (len > 1) {
/* 214 */         buf.append("|");
/* 215 */         exprName.append("|");
/*     */       }
/* 217 */       newArgs.put(name, buf.toString());
/* 218 */       if (!(log.isInfo2Enabled()))
/*     */         continue;
/* 220 */       log.info2(sm.getString("HiExec.AfterGetArgsValue", String.valueOf(i + 1), name, exprName.toString(), buf.toString()));
/*     */     }
/*     */ 
/* 223 */     return newArgs;
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext messContext)
/*     */     throws HiException
/*     */   {
/*     */     HiETF etfRoot;
/* 230 */     long l1 = System.currentTimeMillis();
/* 231 */     long size = Runtime.getRuntime().freeMemory();
/*     */ 
/* 233 */     HiMessage mess = messContext.getCurrentMsg();
/* 234 */     Logger log = HiLog.getLogger(mess);
/* 235 */     long tm1 = System.currentTimeMillis();
/* 236 */     if (log.isInfo2Enabled()) {
/* 237 */       log.info2(sm.getString("HiExec.process00", HiEngineUtilities.getCurFlowStep(), "Exec", getFunc()));
/*     */     }
/*     */ 
/* 241 */     Object body = mess.getBody();
/*     */ 
/* 243 */     int retcode = -1;
/*     */     try {
/* 245 */       HiATLParam params = getRealArgs(this.argsMap, messContext);
/* 246 */       Integer ret = (Integer)HiLibManager.invoke(getFunc(), params, messContext, this.libType);
/*     */ 
/* 248 */       params.clear();
/* 249 */       retcode = ret.intValue();
/*     */     } catch (HiPrimaryException e) {
/* 251 */       retcode = e.getRetCode();
/* 252 */       messContext.setBaseSource("RetCod", String.valueOf(retcode));
/*     */ 
/* 258 */       if (body instanceof HiETF)
/*     */       {
/* 260 */         etfRoot = (HiETF)body;
/* 261 */         etfRoot.setChildValue("MSG_TYP", "E");
/* 262 */         etfRoot.setChildValue("SQL_CD", String.valueOf(e.getSqlErrorCode()));
/*     */ 
/* 265 */         etfRoot.setChildValue("SQL_MSG", e.getSQLState());
/*     */       }
/* 267 */       if (log.isInfo2Enabled()) {
/* 268 */         log.info2(sm.getString("HiExec.ReturnValue", getFunc(), String.valueOf(retcode), String.valueOf(System.currentTimeMillis() - tm1)));
/*     */       }
/*     */ 
/* 273 */       errorProcess(messContext, e);
/*     */     }
/*     */     catch (HiSQLException e) {
/* 276 */       retcode = e.getRetCode();
/* 277 */       messContext.setBaseSource("RetCod", String.valueOf(retcode));
/*     */ 
/* 279 */       if (body instanceof HiETF)
/*     */       {
/* 281 */         etfRoot = (HiETF)body;
/* 282 */         etfRoot.setChildValue("MSG_TYP", "E");
/* 283 */         etfRoot.setChildValue("SQL_CD", String.valueOf(e.getSqlErrorCode()));
/*     */ 
/* 286 */         etfRoot.setChildValue("SQL_MSG", e.getSQLState());
/*     */       }
/* 288 */       if (log.isInfo2Enabled()) {
/* 289 */         log.info2(sm.getString("HiExec.ReturnValue", getFunc(), String.valueOf(retcode), String.valueOf(System.currentTimeMillis() - tm1)));
/*     */       }
/*     */ 
/* 295 */       errorProcess(messContext, e);
/*     */     } catch (HiAppException e) {
/* 297 */       retcode = e.getRetCode();
/*     */ 
/* 299 */       if (log.isInfo2Enabled()) {
/* 300 */         log.info2(sm.getString("HiExec.ReturnValue", getFunc(), String.valueOf(retcode), String.valueOf(System.currentTimeMillis() - tm1)));
/*     */       }
/*     */ 
/* 305 */       errorProcess(messContext, e);
/*     */     }
/*     */     catch (HiException e)
/*     */     {
/* 310 */       retcode = -1;
/* 311 */       messContext.setBaseSource("RetCod", String.valueOf(retcode));
/*     */ 
/* 313 */       if (log.isInfo2Enabled()) {
/* 314 */         log.info2(sm.getString("HiExec.ReturnValue", getFunc(), String.valueOf(retcode), String.valueOf(System.currentTimeMillis() - tm1)));
/*     */       }
/*     */ 
/* 319 */       errorProcess(messContext, e);
/*     */     }
/* 321 */     if (log.isInfo2Enabled()) {
/* 322 */       log.info2(sm.getString("HiExec.ReturnValue", getFunc(), String.valueOf(retcode), String.valueOf(System.currentTimeMillis() - tm1)));
/*     */     }
/*     */ 
/* 327 */     messContext.setBaseSource("RetCod", String.valueOf(retcode));
/*     */ 
/* 329 */     size -= Runtime.getRuntime().freeMemory();
/* 330 */     l1 = System.currentTimeMillis() - l1;
/* 331 */     if (this.stat != null)
/* 332 */       this.stat.once(l1, size);
/*     */   }
/*     */ 
/*     */   public void errorProcess(HiMessageContext ctx, HiException ex) throws HiErrorException, HiGotoException, HiException
/*     */   {
/* 337 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 338 */     String AppId = ctx.getCurrentMsg().getHeadItem("APP");
/*     */ 
/* 341 */     if (this.strError == null)
/*     */     {
/* 343 */       HiLog.logServiceError(ctx.getCurrentMsg(), ex);
/* 344 */       if (log.isInfoEnabled())
/* 345 */         log.info(sm.getString("HiExec.NoErrorProcess", getFunc()));
/* 346 */       throw new HiErrorException(ex);
/*     */     }
/*     */ 
/* 349 */     HiLog.logServiceWarn(ctx.getCurrentMsg(), ex);
/*     */ 
/* 351 */     if (this.strError.equals("IGNORE"))
/*     */     {
/* 353 */       if (log.isInfoEnabled()) {
/* 354 */         log.info(sm.getString("HiExec.IgnoreErrorProcess", getFunc()));
/*     */       }
/*     */ 
/* 357 */       Object body = ctx.getCurrentMsg().getBody();
/* 358 */       if (body instanceof HiETF)
/*     */       {
/* 360 */         HiETF etfRoot = (HiETF)body;
/* 361 */         etfRoot.setChildValue("MSG_TYP", "E");
/* 362 */         etfRoot.setChildValue("RSP_CD", ex.getCode());
/* 363 */         etfRoot.setChildValue("RSP_MSG", ex.getAppMessage());
/*     */       }
/* 365 */       return;
/*     */     }
/*     */ 
/* 368 */     String[] values = StringUtils.split(this.strError, ":");
/* 369 */     if (values.length != 2) {
/* 370 */       log.error(sm.getString("HiExec.InvalidErrorProcess", getError()));
/*     */ 
/* 372 */       throw new HiException("213310", this.strError);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 386 */       if (log.isInfoEnabled())
/* 387 */         log.info(sm.getString("HiExec.ErrorProcess", this.strError));
/* 388 */       HiATLParam params = getRealArgs(this.argsMap, ctx);
/* 389 */       HiLibManager.invoke(this.strError, params, ctx, this.errLibType);
/*     */     } catch (HiException e1) {
/* 391 */       HiLog.logServiceError(ctx.getCurrentMsg(), e1);
/*     */     }
/*     */ 
/* 396 */     if (ex != null)
/* 397 */       throw ex;
/*     */   }
/*     */ 
/*     */   public String getNodeName() {
/* 401 */     return "ComponentExec";
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 405 */     String strNodeName = super.toString();
/* 406 */     if (this.strError != null)
/* 407 */       strNodeName = strNodeName + ":func[" + this.strFunction + "],error[" + this.strError + "]";
/*     */     else
/* 409 */       strNodeName = strNodeName + ":func[" + this.strFunction + "]";
/* 410 */     return strNodeName;
/*     */   }
/*     */ 
/*     */   public static void invoke(HiMessageContext ctx, String cmp, HiATLParam argsMap)
/*     */     throws HiException
/*     */   {
/* 424 */     invoke(ctx, cmp, argsMap, "IGNORE");
/*     */   }
/*     */ 
/*     */   public static void invoke(HiMessageContext ctx, String cmp, HiATLParam argsMap, String error)
/*     */     throws HiException
/*     */   {
/*     */   }
/*     */ }