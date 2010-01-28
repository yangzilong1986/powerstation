 package com.hisun.engine.pojo;
 
 import com.hisun.engine.invoke.impl.HiProcess;
 import com.hisun.exception.HiException;
 import com.hisun.framework.event.IServerDestroyListener;
 import com.hisun.framework.event.IServerInitListener;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 import com.hisun.register.HiRegisterService;
 import org.apache.commons.lang.StringUtils;
 
 public class HiEngineBean
   implements IServerInitListener, IServerDestroyListener, IHandler
 {
   public final Logger log = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
   private HiApplicationBean app;
 
   public HiEngineBean()
   {
     this.app = new HiApplicationBean();
   }
 
   public void setCode(String code) {
     this.app.setAppCode(code);
   }
 
   public void setApp(String appName)
   {
     this.app.setAppName(appName);
   }
 
   public void setPkg(String pkg) throws HiException
   {
     this.app.setPkg(pkg);
   }
 
   public void setLog(String logLevel)
   {
     this.app.setLog(logLevel);
   }
 
   public void setBeanConfig(String beanConfig) throws HiException
   {
     this.app.setBeanConfig(beanConfig);
   }
 
   public void setCmpConfig(String CmpConfig) throws HiException
   {
     this.app.setBeanConfig(CmpConfig);
   }
 
   public void init()
     throws HiException
   {
     this.app.init4spring();
     HiRegisterService.register(this.app.context);
   }
 
   public void destroy()
     throws HiException
   {
     this.app.destroy();
     HiRegisterService.unregister(this.app.context);
   }
 
   public void serverInit(ServerEvent arg0) throws HiException {
     init();
   }
 
   public void serverDestroy(ServerEvent arg0) throws HiException
   {
     destroy();
   }
 
   public void process(HiMessageContext ctx) throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
 
     String strCode = null;
     strCode = mess.getHeadItem("STC");
     if (StringUtils.isEmpty(strCode))
     {
       this.app.execGetCodeBean(ctx);
     }
     strCode = mess.getHeadItem("STC");
     if (StringUtils.isEmpty(strCode))
     {
       throw new HiException("213303", strCode);
     }
     if (this.log.isDebugEnabled())
     {
       this.log.debug("HiEngineBean: ready start Transaction[" + strCode + "] Process");
     }
     HiProcess.process(this.app, ctx);
   }
 }