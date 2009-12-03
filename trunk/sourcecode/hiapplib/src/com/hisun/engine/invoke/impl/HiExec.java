/*     */ package com.hisun.engine.invoke.impl;
/*     */ 
/*     */ import com.hisun.engine.HiEngineModel;
/*     */ import com.hisun.engine.HiEngineStack;
/*     */ import com.hisun.engine.HiEngineUtilities;
/*     */ import com.hisun.engine.exception.HiErrorException;
/*     */ import com.hisun.engine.exception.HiGotoException;
/*     */ import com.hisun.exception.HiAppException;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.exception.HiPrimaryException;
/*     */ import com.hisun.exception.HiResponseException;
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
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiExec extends HiEngineModel
/*     */ {
/*     */   private String strError;
/*     */   private String strFunction;
/*     */   private HiATLParam argsMap;
/*  45 */   public static HiStringManager sm = HiStringManager.getManager();
/*     */   private IStat stat;
/*     */   private int libType;
/*     */   private int errLibType;
/*     */   private static final String GOTO = "GOTO";
/*     */   private static final String IGNORE = "IGNORE";
/*     */ 
/*     */   public HiExec()
/*     */   {
/*  43 */     this.argsMap = new HiATLParam();
/*     */ 
/*  46 */     this.stat = null;
/*     */ 
/*  49 */     this.libType = 0;
/*     */ 
/*  51 */     this.errLibType = 0;
/*     */   }
/*     */ 
/*     */   public void setFunc(String strName)
/*     */     throws HiException
/*     */   {
/*  60 */     this.strFunction = strName;
/*     */ 
/*  62 */     this.libType = HiLibManager.loadComponent(strName);
/*  63 */     this.stat = HiStats.getState("Exec_" + strName);
/*     */   }
/*     */ 
/*     */   public String getFunc()
/*     */   {
/*  70 */     return this.strFunction;
/*     */   }
/*     */ 
/*     */   public void setError(String strName)
/*     */     throws HiException
/*     */   {
/*  77 */     this.strError = strName;
/*  78 */     if ((StringUtils.equalsIgnoreCase(strName, "IGNORE")) || (strName.startsWith("GOTO:")))
/*     */       return;
/*  80 */     this.errLibType = HiLibManager.loadComponent(strName);
/*     */   }
/*     */ 
/*     */   public String getError()
/*     */   {
/*  88 */     return this.strError;
/*     */   }
/*     */ 
/*     */   public void setArgs(String strArgName, String strArgValue)
/*     */     throws HiException
/*     */   {
/*  96 */     if (StringUtils.isEmpty(strArgName)) {
/*  97 */       throw new HiException("213307");
/*     */     }
/*     */ 
/* 100 */     String name = strArgName.toUpperCase();
/*     */ 
/* 107 */     if (StringUtils.isEmpty(strArgValue)) {
/* 108 */       this.argsMap.put(name, null);
/*     */     } else {
/* 110 */       String[] args = strArgValue.split("\\|");
/* 111 */       HiExpression[] exps = new HiExpression[args.length];
/* 112 */       for (int i = 0; i < exps.length; ++i) {
/* 113 */         exps[i] = HiExpFactory.createExp(args[i]);
/*     */       }
/* 115 */       this.argsMap.put(name, exps);
/*     */     }
/*     */   }
/*     */ 
/*     */   public HiATLParam getArgs()
/*     */   {
/* 124 */     return this.argsMap;
/*     */   }
/*     */ 
/*     */   private HiATLParam getRealArgs(HiATLParam args, HiMessageContext context)
/*     */     throws HiException
/*     */   {
/* 134 */     Logger log = HiLog.getLogger(context.getCurrentMsg());
/* 135 */     HiATLParam newArgs = new HiATLParam();
/*     */ 
/* 138 */     HashMap componentParam = (HashMap)context.getProperty("@PARA", this.strFunction);
/*     */ 
/* 140 */     if (componentParam != null) {
/* 141 */       Iterator iter = componentParam.entrySet().iterator();
/* 142 */       while (iter.hasNext()) {
/* 143 */         Map.Entry entry = (Map.Entry)iter.next();
/* 144 */         newArgs.put((String)entry.getKey(), entry.getValue());
/*     */       }
/*     */     }
/*     */ 
/* 148 */     for (int i = 0; i < args.size(); ++i) {
/* 149 */       HiExpression[] exps = (HiExpression[])(HiExpression[])args.getValueObject(i);
/* 150 */       StringBuffer buf = new StringBuffer();
/* 151 */       String name = args.getName(i);
/* 152 */       StringBuffer exprName = new StringBuffer();
/* 153 */       int len = (exps != null) ? exps.length : 0;
/* 154 */       for (int j = 0; j < len; ++j) {
/* 155 */         HiExpression exp = exps[j];
/* 156 */         String realValue = null;
/* 157 */         if (exp != null) {
/* 158 */           if (log.isDebugEnabled()) {
/* 159 */             log.debug(sm.getString("HiExec.BeforeGetArgsValue", String.valueOf(i + 1), name, exp.getExp()));
/*     */           }
/*     */ 
/* 163 */           realValue = exp.getValue(context);
/* 164 */           if (log.isDebugEnabled()) {
/* 165 */             log.debug("name [" + name + "] value[" + realValue + "] oldValue[" + exp.getExp() + "]");
/*     */           }
/*     */ 
/* 168 */           buf.append(realValue);
/* 169 */           if (j < exps.length - 1) {
/* 170 */             buf.append("|");
/*     */           }
/*     */         }
/* 173 */         exprName.append(exp.getExp());
/* 174 */         if (j < exps.length - 1) {
/* 175 */           exprName.append("|");
/*     */         }
/*     */       }
/* 178 */       if (len > 1) {
/* 179 */         buf.append("|");
/* 180 */         exprName.append("|");
/*     */       }
/* 182 */       newArgs.put(name, buf.toString());
/* 183 */       if (!(log.isInfo2Enabled()))
/*     */         continue;
/* 185 */       log.info2(sm.getString("HiExec.AfterGetArgsValue", String.valueOf(i + 1), name, exprName.toString(), buf.toString()));
/*     */     }
/*     */ 
/* 188 */     return newArgs;
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext messContext)
/*     */     throws HiException
/*     */   {
/* 195 */     long l1 = System.currentTimeMillis();
/* 196 */     long size = Runtime.getRuntime().freeMemory();
/*     */ 
/* 198 */     HiMessage mess = messContext.getCurrentMsg();
/* 199 */     Logger log = HiLog.getLogger(mess);
/* 200 */     long tm1 = System.currentTimeMillis();
/* 201 */     if (log.isInfo2Enabled()) {
/* 202 */       log.info2(sm.getString("HiExec.process00", HiEngineUtilities.getCurFlowStep(), "Exec", getFunc()));
/*     */     }
/*     */ 
/* 206 */     HiETF etfRoot = mess.getETFBody();
/*     */ 
/* 208 */     int retcode = -1;
/*     */     try {
/* 210 */       HiATLParam params = getRealArgs(this.argsMap, messContext);
/* 211 */       Integer ret = (Integer)HiLibManager.invoke(getFunc(), params, messContext, this.libType);
/*     */ 
/* 213 */       params.clear();
/* 214 */       retcode = ret.intValue();
/*     */     } catch (HiResponseException e) {
/* 216 */       HiEngineStack.getEngineStack(messContext).push(this);
/* 217 */       throw e;
/*     */     } catch (HiPrimaryException e) {
/* 219 */       retcode = e.getRetCode();
/* 220 */       messContext.setBaseSource("RetCod", String.valueOf(retcode));
/*     */ 
/* 226 */       etfRoot.setChildValue("MSG_TYP", "E");
/* 227 */       etfRoot.setChildValue("SQL_CD", String.valueOf(e.getSqlErrorCode()));
/*     */ 
/* 230 */       etfRoot.setChildValue("SQL_MSG", e.getSQLState());
/* 231 */       if (log.isInfo2Enabled()) {
/* 232 */         log.info2(sm.getString("HiExec.ReturnValue", getFunc(), String.valueOf(retcode), String.valueOf(System.currentTimeMillis() - tm1)));
/*     */       }
/*     */ 
/* 237 */       errorProcess(messContext, e);
/*     */     }
/*     */     catch (HiSQLException e) {
/* 240 */       retcode = e.getRetCode();
/* 241 */       messContext.setBaseSource("RetCod", String.valueOf(retcode));
/*     */ 
/* 243 */       etfRoot.setChildValue("MSG_TYP", "E");
/* 244 */       etfRoot.setChildValue("SQL_CD", String.valueOf(e.getSqlErrorCode()));
/*     */ 
/* 247 */       etfRoot.setChildValue("SQL_MSG", e.getSQLState());
/* 248 */       if (log.isInfo2Enabled()) {
/* 249 */         log.info2(sm.getString("HiExec.ReturnValue", getFunc(), String.valueOf(retcode), String.valueOf(System.currentTimeMillis() - tm1)));
/*     */       }
/*     */ 
/* 255 */       errorProcess(messContext, e);
/*     */     } catch (HiAppException e) {
/* 257 */       retcode = e.getRetCode();
/*     */ 
/* 259 */       if (log.isInfo2Enabled()) {
/* 260 */         log.info2(sm.getString("HiExec.ReturnValue", getFunc(), String.valueOf(retcode), String.valueOf(System.currentTimeMillis() - tm1)));
/*     */       }
/*     */ 
/* 265 */       errorProcess(messContext, e);
/*     */     }
/*     */     catch (HiException e)
/*     */     {
/* 270 */       retcode = -1;
/* 271 */       messContext.setBaseSource("RetCod", String.valueOf(retcode));
/*     */ 
/* 273 */       if (log.isInfo2Enabled()) {
/* 274 */         log.info2(sm.getString("HiExec.ReturnValue", getFunc(), String.valueOf(retcode), String.valueOf(System.currentTimeMillis() - tm1)));
/*     */       }
/*     */ 
/* 279 */       errorProcess(messContext, e);
/*     */     }
/* 281 */     if (log.isInfo2Enabled()) {
/* 282 */       log.info2(sm.getString("HiExec.ReturnValue", getFunc(), String.valueOf(retcode), String.valueOf(System.currentTimeMillis() - tm1)));
/*     */     }
/*     */ 
/* 287 */     messContext.setBaseSource("RetCod", String.valueOf(retcode));
/*     */ 
/* 289 */     size -= Runtime.getRuntime().freeMemory();
/* 290 */     l1 = System.currentTimeMillis() - l1;
/* 291 */     if (this.stat != null)
/* 292 */       this.stat.once(l1, size);
/*     */   }
/*     */ 
/*     */   public void errorProcess(HiMessageContext ctx, HiException ex) throws HiErrorException, HiGotoException, HiException
/*     */   {
/* 297 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 298 */     String AppId = ctx.getCurrentMsg().getHeadItem("APP");
/*     */ 
/* 301 */     if (this.strError == null)
/*     */     {
/* 303 */       HiLog.logServiceError(ctx.getCurrentMsg(), ex);
/* 304 */       if (log.isInfoEnabled())
/* 305 */         log.info(sm.getString("HiExec.NoErrorProcess", getFunc()));
/* 306 */       throw new HiErrorException(ex);
/*     */     }
/*     */ 
/* 309 */     HiLog.logServiceWarn(ctx.getCurrentMsg(), ex);
/*     */ 
/* 311 */     if (this.strError.equals("IGNORE"))
/*     */     {
/* 313 */       HiETF etfRoot = ctx.getCurrentMsg().getETFBody();
/* 314 */       if (log.isInfoEnabled()) {
/* 315 */         log.info(sm.getString("HiExec.IgnoreErrorProcess", getFunc()));
/*     */       }
/*     */ 
/* 318 */       etfRoot.setChildValue("MSG_TYP", "E");
/* 319 */       etfRoot.setChildValue("RSP_CD", ex.getCode());
/* 320 */       etfRoot.setChildValue("RSP_MSG", ex.getAppMessage());
/* 321 */       return;
/*     */     }
/*     */ 
/* 324 */     String[] values = StringUtils.split(this.strError, ":");
/* 325 */     if (values.length != 2) {
/* 326 */       log.error(sm.getString("HiExec.InvalidErrorProcess", getError()));
/*     */ 
/* 328 */       throw new HiException("213310", this.strError);
/*     */     }
/*     */ 
/* 332 */     if (values[0].equals("GOTO")) {
/* 333 */       if (log.isInfoEnabled())
/* 334 */         log.info(sm.getString("HiExec.GoToErrorProcess", values[1]));
/* 335 */       HiGotoException e = new HiGotoException();
/* 336 */       e.seGototName(values[1]);
/* 337 */       throw e;
/*     */     }
/*     */     try
/*     */     {
/* 341 */       if (log.isInfoEnabled()) {
/* 342 */         log.info(sm.getString("HiExec.ErrorProcess", this.strError));
/*     */       }
/* 344 */       HiLibManager.invoke(this.strError, new HiATLParam(), ctx, this.errLibType);
/*     */     } catch (HiException e1) {
/* 346 */       HiLog.logServiceError(ctx.getCurrentMsg(), e1);
/*     */     }
/*     */ 
/* 351 */     if (ex != null)
/* 352 */       throw ex;
/*     */   }
/*     */ 
/*     */   public String getNodeName() {
/* 356 */     return "Exec";
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 360 */     String strNodeName = super.toString();
/* 361 */     if (this.strError != null)
/* 362 */       strNodeName = strNodeName + ":func[" + this.strFunction + "],error[" + this.strError + "]";
/*     */     else
/* 364 */       strNodeName = strNodeName + ":func[" + this.strFunction + "]";
/* 365 */     return strNodeName;
/*     */   }
/*     */ }