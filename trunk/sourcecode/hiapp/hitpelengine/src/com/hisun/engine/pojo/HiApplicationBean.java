 package com.hisun.engine.pojo;
 
 import com.hisun.bean.HiBeanManager;
 import com.hisun.engine.invoke.impl.HiProcess;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiICSProperty;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.Set;
 import org.apache.commons.lang.StringUtils;
 
 public class HiApplicationBean extends HiPojoEngineModel
 {
   private final Logger log = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
 
   private String serverName = HiContext.getCurrentContext().getStrProp("SVR.name");
   private static final String TRANSPRE = "TRANS:";
   private static final String GETCODETS = "GetCode";
   private String appCode = null;
 
   private String appName = "app";
 
   private String pkg = null;
 
   private String beanConfig = null;
 
   private String cmpConfig = null;
 
   private String log_level = "error";
   private String trace;
   private HashMap tranMap = new HashMap();
 
   private HiTransactionBean getTxnCodeBean = null;
 
   public HiApplicationBean()
   {
     this.context = HiContext.createAndPushContext();
   }
 
   public void setAppName(String appName) {
     if (this.log.isDebugEnabled())
     {
       this.log.debug("setAppName(String) - " + appName);
     }
 
     this.appName = appName;
 
     this.context.setId(HiContext.APP_PRE + this.appName);
     this.context.setProperty("app_name", appName);
   }
 
   public String getAppName()
   {
     return this.appName;
   }
 
   public void setAppCode(String code) {
     if (this.log.isDebugEnabled())
     {
       this.log.debug("setAppCode(String) - " + code);
     }
     this.appCode = code;
     if (this.context.getId() == null)
     {
       this.context.setId(HiContext.APP_PRE + this.appCode);
     }
     this.context.setProperty("app_code", code);
   }
 
   public String getAppCode() {
     return this.appCode;
   }
 
   public void setPkg(String pkg) throws HiException
   {
     if (this.log.isDebugEnabled())
     {
       this.log.debug("setPkg(String) - " + pkg);
     }
 
     this.pkg = pkg;
   }
 
   public void setBeanConfig(String beanConfig) {
     if (this.log.isDebugEnabled())
     {
       this.log.debug("setBeanConfig(String) - " + beanConfig);
     }
     if (this.beanConfig != null)
     {
       this.beanConfig = this.beanConfig + ", " + beanConfig;
     }
     else
     {
       this.beanConfig = beanConfig; }
   }
 
   public String getBeanConfig() {
     return this.beanConfig; }
 
   public void setCmpConfig(String cmpConfig) {
     if (this.log.isDebugEnabled())
     {
       this.log.debug("setCmpConfig(String) - " + cmpConfig);
     }
     if (this.cmpConfig != null)
     {
       this.cmpConfig = this.cmpConfig + ", " + cmpConfig;
     }
     else
     {
       this.cmpConfig = cmpConfig; }
   }
 
   public String getCmpConfig() {
     return this.cmpConfig;
   }
 
   public void setLog(String logLevel) {
     this.log_level = logLevel;
   }
 
   public boolean isTrace()
   {
     return (!("yes".equalsIgnoreCase(this.trace)));
   }
 
   public void setTrace(String trace)
   {
     if (this.log.isDebugEnabled()) {
       this.log.debug("setTrace(String) - start");
     }
 
     this.trace = trace;
     this.context.setProperty("app_log_level", trace);
 
     if (this.log.isDebugEnabled())
       this.log.debug("setTrace(String) - end");
   }
 
   public HashMap getTranMap()
   {
     return this.tranMap;
   }
 
   public String getNodeName() {
     return "ApplicationBean";
   }
 
   public HiTransactionBean getTransaction(String code)
   {
     return ((HiTransactionBean)this.tranMap.get(code));
   }
 
   public void init4spring() throws HiException
   {
     if (this.beanConfig == null)
     {
       throw new HiException("215027", "Load Trans Java Bean Failure:Bean Config is null");
     }
     if (this.cmpConfig == null)
     {
       this.cmpConfig = HiICSProperty.getProperty("spring.config");
     }
     if (this.cmpConfig != null)
     {
       this.beanConfig = this.beanConfig + ", " + this.cmpConfig;
     }
     Map transMap = HiBeanManager.getTransBeanMap(this.beanConfig);
     if (transMap == null)
     {
       this.log.info("Load Trans Java Bean: not any bean!");
       return;
     }
 
     Iterator it = transMap.entrySet().iterator();
 
     while (it.hasNext())
     {
       Map.Entry transEntry = (Map.Entry)it.next();
 
       Object obj = transEntry.getValue();
       if (!(obj instanceof HiTransactionBean))
         continue;
       if (this.log.isDebugEnabled())
       {
         this.log.debug("find trans:" + transEntry.getKey());
       }
       HiTransactionBean transBean = (HiTransactionBean)obj;
       transBean.init();
       String transCode = transBean.getCode();
       if (transCode == null)
       {
         transCode = StringUtils.substringAfter((String)transEntry.getKey(), "TRANS:");
         transBean.setCode(transCode);
       }
       if (!(StringUtils.isNotBlank(transCode)))
         continue;
       this.tranMap.put(transCode, transBean);
 
       if (StringUtils.equalsIgnoreCase(transCode, "GetCode"))
       {
         this.getTxnCodeBean = transBean;
       }
       if (!(this.log.isInfoEnabled()))
         continue;
       this.log.info("code=[" + transCode + "]");
     }
 
     transMap.clear();
 
     loadAfter();
   }
 
   public void loadAfter()
     throws HiException
   {
     popOwnerContext();
   }
 
   public void destroy()
     throws HiException
   {
     HiBeanManager.destroy(this.context);
   }
 
   public void execGetCodeBean(HiMessageContext msgCtx) throws HiException {
     if (this.getTxnCodeBean == null)
       return;
     HiProcess.process(this.getTxnCodeBean, msgCtx);
   }
 
   public void process(HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
 
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled())
     {
       log.debug("HiApplicationBean:process(HiMessageContext) - start");
     }
 
     String strCode = msg.getHeadItem("STC");
     msg.setHeadItem("APP", this.appName);
     ctx.setProperty("TXN_CD", strCode);
     msg.getETFBody().setChildValue("TXN_CD", strCode);
     if (this.appCode != null)
     {
       msg.getETFBody().setChildValue("APP_CLS", getAppCode());
     }
 
     HiTransactionBean transBean = getTransaction(strCode);
     if (transBean == null)
     {
       throw new HiException("213322", strCode);
     }
 
     HiProcess.process(transBean, ctx);
 
     if (!(log.isDebugEnabled()))
       return;
     log.debug("HiApplicationBean:process(HiMessageContext) - end");
   }
 }