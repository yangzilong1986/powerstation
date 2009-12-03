/*     */ package com.hisun.engine.pojo;
/*     */ 
/*     */ import com.hisun.engine.invoke.impl.HiProcess;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.event.IServerDestroyListener;
/*     */ import com.hisun.framework.event.IServerInitListener;
/*     */ import com.hisun.framework.event.ServerEvent;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.pubinterface.IHandler;
/*     */ import com.hisun.register.HiRegisterService;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiEngineBean
/*     */   implements IServerInitListener, IServerDestroyListener, IHandler
/*     */ {
/*  27 */   public final Logger log = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
/*     */   private HiApplicationBean app;
/*     */ 
/*     */   public HiEngineBean()
/*     */   {
/*  33 */     this.app = new HiApplicationBean();
/*     */   }
/*     */ 
/*     */   public void setCode(String code) {
/*  37 */     this.app.setAppCode(code);
/*     */   }
/*     */ 
/*     */   public void setApp(String appName)
/*     */   {
/*  42 */     this.app.setAppName(appName);
/*     */   }
/*     */ 
/*     */   public void setPkg(String pkg) throws HiException
/*     */   {
/*  47 */     this.app.setPkg(pkg);
/*     */   }
/*     */ 
/*     */   public void setLog(String logLevel)
/*     */   {
/*  52 */     this.app.setLog(logLevel);
/*     */   }
/*     */ 
/*     */   public void setBeanConfig(String beanConfig) throws HiException
/*     */   {
/*  57 */     this.app.setBeanConfig(beanConfig);
/*     */   }
/*     */ 
/*     */   public void setCmpConfig(String CmpConfig) throws HiException
/*     */   {
/*  62 */     this.app.setBeanConfig(CmpConfig);
/*     */   }
/*     */ 
/*     */   public void init()
/*     */     throws HiException
/*     */   {
/*  71 */     this.app.init4spring();
/*  72 */     HiRegisterService.register(this.app.context);
/*     */   }
/*     */ 
/*     */   public void destroy()
/*     */     throws HiException
/*     */   {
/*  82 */     this.app.destroy();
/*  83 */     HiRegisterService.unregister(this.app.context);
/*     */   }
/*     */ 
/*     */   public void serverInit(ServerEvent arg0) throws HiException {
/*  87 */     init();
/*     */   }
/*     */ 
/*     */   public void serverDestroy(ServerEvent arg0) throws HiException
/*     */   {
/*  92 */     destroy();
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx) throws HiException
/*     */   {
/*  97 */     HiMessage mess = ctx.getCurrentMsg();
/*     */ 
/*  99 */     String strCode = null;
/* 100 */     strCode = mess.getHeadItem("STC");
/* 101 */     if (StringUtils.isEmpty(strCode))
/*     */     {
/* 104 */       this.app.execGetCodeBean(ctx);
/*     */     }
/* 106 */     strCode = mess.getHeadItem("STC");
/* 107 */     if (StringUtils.isEmpty(strCode))
/*     */     {
/* 109 */       throw new HiException("213303", strCode);
/*     */     }
/* 111 */     if (this.log.isDebugEnabled())
/*     */     {
/* 113 */       this.log.debug("HiEngineBean: ready start Transaction[" + strCode + "] Process");
/*     */     }
/* 115 */     HiProcess.process(this.app, ctx);
/*     */   }
/*     */ }