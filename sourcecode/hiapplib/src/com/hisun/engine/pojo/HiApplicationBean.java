/*     */ package com.hisun.engine.pojo;
/*     */ 
/*     */ import com.hisun.bean.HiBeanManager;
/*     */ import com.hisun.engine.invoke.impl.HiProcess;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiApplicationBean extends HiPojoEngineModel
/*     */ {
/*  26 */   private final Logger log = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
/*     */ 
/*  28 */   private String serverName = HiContext.getCurrentContext().getStrProp("SVR.name");
/*     */   private static final String TRANSPRE = "TRANS:";
/*     */   private static final String GETCODETS = "GetCode";
/*  33 */   private String appCode = null;
/*     */ 
/*  35 */   private String appName = "app";
/*     */ 
/*  38 */   private String pkg = null;
/*     */ 
/*  41 */   private String beanConfig = null;
/*     */ 
/*  43 */   private String cmpConfig = null;
/*     */ 
/*  46 */   private String log_level = "error";
/*     */   private String trace;
/*  53 */   private HashMap tranMap = new HashMap();
/*     */ 
/*  55 */   private HiTransactionBean getTxnCodeBean = null;
/*     */ 
/*     */   public HiApplicationBean()
/*     */   {
/*  59 */     this.context = HiContext.createAndPushContext();
/*     */   }
/*     */ 
/*     */   public void setAppName(String appName) {
/*  63 */     if (this.log.isDebugEnabled())
/*     */     {
/*  65 */       this.log.debug("setAppName(String) - " + appName);
/*     */     }
/*     */ 
/*  68 */     this.appName = appName;
/*     */ 
/*  70 */     this.context.setId(HiContext.APP_PRE + this.appName);
/*  71 */     this.context.setProperty("app_name", appName);
/*     */   }
/*     */ 
/*     */   public String getAppName()
/*     */   {
/*  76 */     return this.appName;
/*     */   }
/*     */ 
/*     */   public void setAppCode(String code) {
/*  80 */     if (this.log.isDebugEnabled())
/*     */     {
/*  82 */       this.log.debug("setAppCode(String) - " + code);
/*     */     }
/*  84 */     this.appCode = code;
/*  85 */     if (this.context.getId() == null)
/*     */     {
/*  87 */       this.context.setId(HiContext.APP_PRE + this.appCode);
/*     */     }
/*  89 */     this.context.setProperty("app_code", code);
/*     */   }
/*     */ 
/*     */   public String getAppCode() {
/*  93 */     return this.appCode;
/*     */   }
/*     */ 
/*     */   public void setPkg(String pkg) throws HiException
/*     */   {
/*  98 */     if (this.log.isDebugEnabled())
/*     */     {
/* 100 */       this.log.debug("setPkg(String) - " + pkg);
/*     */     }
/*     */ 
/* 103 */     this.pkg = pkg;
/*     */   }
/*     */ 
/*     */   public void setBeanConfig(String beanConfig) {
/* 107 */     if (this.log.isDebugEnabled())
/*     */     {
/* 109 */       this.log.debug("setBeanConfig(String) - " + beanConfig);
/*     */     }
/* 111 */     if (this.beanConfig != null)
/*     */     {
/* 113 */       this.beanConfig = this.beanConfig + ", " + beanConfig;
/*     */     }
/*     */     else
/*     */     {
/* 117 */       this.beanConfig = beanConfig; }
/*     */   }
/*     */ 
/*     */   public String getBeanConfig() {
/* 121 */     return this.beanConfig; }
/*     */ 
/*     */   public void setCmpConfig(String cmpConfig) {
/* 124 */     if (this.log.isDebugEnabled())
/*     */     {
/* 126 */       this.log.debug("setCmpConfig(String) - " + cmpConfig);
/*     */     }
/* 128 */     if (this.cmpConfig != null)
/*     */     {
/* 130 */       this.cmpConfig = this.cmpConfig + ", " + cmpConfig;
/*     */     }
/*     */     else
/*     */     {
/* 134 */       this.cmpConfig = cmpConfig; }
/*     */   }
/*     */ 
/*     */   public String getCmpConfig() {
/* 138 */     return this.cmpConfig;
/*     */   }
/*     */ 
/*     */   public void setLog(String logLevel) {
/* 142 */     this.log_level = logLevel;
/*     */   }
/*     */ 
/*     */   public boolean isTrace()
/*     */   {
/* 152 */     return (!("yes".equalsIgnoreCase(this.trace)));
/*     */   }
/*     */ 
/*     */   public void setTrace(String trace)
/*     */   {
/* 158 */     if (this.log.isDebugEnabled()) {
/* 159 */       this.log.debug("setTrace(String) - start");
/*     */     }
/*     */ 
/* 162 */     this.trace = trace;
/* 163 */     this.context.setProperty("app_log_level", trace);
/*     */ 
/* 165 */     if (this.log.isDebugEnabled())
/* 166 */       this.log.debug("setTrace(String) - end");
/*     */   }
/*     */ 
/*     */   public HashMap getTranMap()
/*     */   {
/* 171 */     return this.tranMap;
/*     */   }
/*     */ 
/*     */   public String getNodeName() {
/* 175 */     return "ApplicationBean";
/*     */   }
/*     */ 
/*     */   public HiTransactionBean getTransaction(String code)
/*     */   {
/* 180 */     return ((HiTransactionBean)this.tranMap.get(code));
/*     */   }
/*     */ 
/*     */   public void init4spring() throws HiException
/*     */   {
/* 185 */     if (this.beanConfig == null)
/*     */     {
/* 187 */       throw new HiException("215027", "Load Trans Java Bean Failure:Bean Config is null");
/*     */     }
/* 189 */     if (this.cmpConfig == null)
/*     */     {
/* 191 */       this.cmpConfig = HiICSProperty.getProperty("spring.config");
/*     */     }
/* 193 */     if (this.cmpConfig != null)
/*     */     {
/* 195 */       this.beanConfig = this.beanConfig + ", " + this.cmpConfig;
/*     */     }
/* 197 */     Map transMap = HiBeanManager.getTransBeanMap(this.beanConfig);
/* 198 */     if (transMap == null)
/*     */     {
/* 200 */       this.log.info("Load Trans Java Bean: not any bean!");
/* 201 */       return;
/*     */     }
/*     */ 
/* 205 */     Iterator it = transMap.entrySet().iterator();
/*     */ 
/* 208 */     while (it.hasNext())
/*     */     {
/* 210 */       Map.Entry transEntry = (Map.Entry)it.next();
/*     */ 
/* 212 */       Object obj = transEntry.getValue();
/* 213 */       if (!(obj instanceof HiTransactionBean))
/*     */         continue;
/* 215 */       if (this.log.isDebugEnabled())
/*     */       {
/* 217 */         this.log.debug("find trans:" + transEntry.getKey());
/*     */       }
/* 219 */       HiTransactionBean transBean = (HiTransactionBean)obj;
/* 220 */       transBean.init();
/* 221 */       String transCode = transBean.getCode();
/* 222 */       if (transCode == null)
/*     */       {
/* 225 */         transCode = StringUtils.substringAfter((String)transEntry.getKey(), "TRANS:");
/* 226 */         transBean.setCode(transCode);
/*     */       }
/* 228 */       if (!(StringUtils.isNotBlank(transCode)))
/*     */         continue;
/* 230 */       this.tranMap.put(transCode, transBean);
/*     */ 
/* 232 */       if (StringUtils.equalsIgnoreCase(transCode, "GetCode"))
/*     */       {
/* 234 */         this.getTxnCodeBean = transBean;
/*     */       }
/* 236 */       if (!(this.log.isInfoEnabled()))
/*     */         continue;
/* 238 */       this.log.info("code=[" + transCode + "]");
/*     */     }
/*     */ 
/* 243 */     transMap.clear();
/*     */ 
/* 245 */     loadAfter();
/*     */   }
/*     */ 
/*     */   public void loadAfter()
/*     */     throws HiException
/*     */   {
/* 317 */     popOwnerContext();
/*     */   }
/*     */ 
/*     */   public void destroy()
/*     */     throws HiException
/*     */   {
/* 323 */     HiBeanManager.destroy(this.context);
/*     */   }
/*     */ 
/*     */   public void execGetCodeBean(HiMessageContext msgCtx) throws HiException {
/* 327 */     if (this.getTxnCodeBean == null)
/*     */       return;
/* 329 */     HiProcess.process(this.getTxnCodeBean, msgCtx);
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 335 */     HiMessage msg = ctx.getCurrentMsg();
/*     */ 
/* 337 */     Logger log = HiLog.getLogger(msg);
/* 338 */     if (log.isDebugEnabled())
/*     */     {
/* 340 */       log.debug("HiApplicationBean:process(HiMessageContext) - start");
/*     */     }
/*     */ 
/* 343 */     String strCode = msg.getHeadItem("STC");
/* 344 */     msg.setHeadItem("APP", this.appName);
/* 345 */     ctx.setProperty("TXN_CD", strCode);
/* 346 */     msg.getETFBody().setChildValue("TXN_CD", strCode);
/* 347 */     if (this.appCode != null)
/*     */     {
/* 349 */       msg.getETFBody().setChildValue("APP_CLS", getAppCode());
/*     */     }
/*     */ 
/* 352 */     HiTransactionBean transBean = getTransaction(strCode);
/* 353 */     if (transBean == null)
/*     */     {
/* 355 */       throw new HiException("213322", strCode);
/*     */     }
/*     */ 
/* 358 */     HiProcess.process(transBean, ctx);
/*     */ 
/* 360 */     if (!(log.isDebugEnabled()))
/*     */       return;
/* 362 */     log.debug("HiApplicationBean:process(HiMessageContext) - end");
/*     */   }
/*     */ }