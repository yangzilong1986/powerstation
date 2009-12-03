/*     */ package com.hisun.framework.imp;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.HiDefaultServer;
/*     */ import com.hisun.framework.HiFrameworkBuilder;
/*     */ import com.hisun.framework.filter.LogFilter;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.pipeline.PipelineBuilder;
/*     */ import com.hisun.pubinterface.IHandler;
/*     */ import com.hisun.stat.util.HiStats;
/*     */ import com.hisun.stat.util.IStat;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.beanutils.PropertyUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.hivemind.lib.BeanFactory;
/*     */ 
/*     */ public class HiDefaultProcess
/*     */   implements IHandler
/*     */ {
/*     */   private final Logger log;
/*  32 */   private final HiStringManager sm = HiStringManager.getManager();
/*     */   private String name;
/*     */   private String msgtype;
/*  39 */   private List handlers = new ArrayList();
/*     */   private HiDefaultServer server;
/*     */   private String errprocess;
/*     */   private HiDefaultProcess errprocessObject;
/*     */ 
/*     */   public HiDefaultProcess()
/*     */   {
/*  48 */     HiContext ctx = HiContext.getCurrentContext();
/*  49 */     this.server = ((HiDefaultServer)ctx.getProperty("SVR.server"));
/*  50 */     this.log = ((Logger)ctx.getProperty("SVR.log"));
/*     */   }
/*     */ 
/*     */   public HiDefaultServer getServer()
/*     */   {
/*  55 */     return this.server;
/*     */   }
/*     */ 
/*     */   public void setServer(HiDefaultServer server) {
/*  59 */     this.server = server;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/*  63 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  67 */     return this.name;
/*     */   }
/*     */ 
/*     */   public String getMsgtype() {
/*  71 */     return this.msgtype;
/*     */   }
/*     */ 
/*     */   public void setMsgtype(String msgType) {
/*  75 */     this.msgtype = msgType;
/*     */   }
/*     */ 
/*     */   public String getErrorprocess() {
/*  79 */     return this.errprocess;
/*     */   }
/*     */ 
/*     */   public void setError(String errprocess) throws HiException {
/*  83 */     if (this.log.isDebugEnabled()) {
/*  84 */       this.log.debug("HiDefaultProcess.setErrhandler() - start" + errprocess);
/*     */     }
/*     */ 
/*  87 */     this.errprocess = errprocess;
/*     */ 
/*  91 */     if (this.log.isInfoEnabled()) {
/*  92 */       this.log.info(this.sm.getString("HiDefaultProcess.ErrHandler00", errprocess));
/*     */     }
/*     */ 
/*  95 */     if (this.log.isDebugEnabled())
/*  96 */       this.log.debug("HiDefaultProcess.setErrhandler() - end");
/*     */   }
/*     */ 
/*     */   public void addHandler(String handler, String method)
/*     */     throws HiException
/*     */   {
/* 102 */     if (this.log.isDebugEnabled()) {
/* 103 */       this.log.debug("HiDefaultProcess.addHandler() - start");
/*     */     }
/*     */ 
/* 106 */     Object objHandler = this.server.getDeclare(handler);
/* 107 */     if (StringUtils.isBlank(method)) {
/* 108 */       if (objHandler instanceof IHandler) {
/* 109 */         IStat stat = HiStats.getState(this.server.getName() + ":" + handler);
/* 110 */         LogFilter filter = new LogFilter("handler " + handler, this.log, stat);
/* 111 */         IHandler proxyHandler = (IHandler)HiFrameworkBuilder.getPipelineBuilder().buildPipeline(filter, (IHandler)objHandler);
/*     */ 
/* 114 */         this.handlers.add(proxyHandler);
/* 115 */         break label289:
/*     */       }
/* 117 */       throw new HiException("Declare Object: " + handler + " is not a handler");
/*     */     }
/*     */ 
/* 120 */     HiHandlerWapper wp = new HiHandlerWapper(handler, objHandler, method, this.log);
/* 121 */     IStat stat = HiStats.getState(this.server.getName() + ":" + handler);
/* 122 */     LogFilter filter = new LogFilter("handler " + handler, this.log, stat);
/* 123 */     IHandler proxyHandler = (IHandler)HiFrameworkBuilder.getPipelineBuilder().buildPipeline(filter, wp);
/*     */ 
/* 126 */     this.handlers.add(proxyHandler);
/*     */ 
/* 129 */     if (this.log.isDebugEnabled())
/* 130 */       label289: this.log.debug("HiDefaultProcess.addHandler() - end");
/*     */   }
/*     */ 
/*     */   public int getHandlerNum()
/*     */   {
/* 135 */     int returnint = this.handlers.size();
/* 136 */     return returnint;
/*     */   }
/*     */ 
/*     */   public void addSystemHandler(String handler, String param) throws HiException
/*     */   {
/* 141 */     if (this.log.isDebugEnabled()) {
/* 142 */       this.log.debug("HiDefaultProcess.addSystemHandler() - start" + handler + ":" + param);
/*     */     }
/*     */ 
/* 145 */     IHandler objHandler = createSystemHandler(handler, param);
/* 146 */     if (objHandler instanceof IHandler) {
/* 147 */       IStat stat = null;
/* 148 */       stat = HiStats.getState(this.server.getName() + ":" + handler);
/* 149 */       LogFilter filter = new LogFilter("system handler " + handler, this.log, stat);
/* 150 */       IHandler proxyHandler = (IHandler)HiFrameworkBuilder.getPipelineBuilder().buildPipeline(filter, objHandler);
/*     */ 
/* 153 */       this.handlers.add(proxyHandler);
/*     */     }
/*     */     else {
/* 156 */       throw new HiException("System handler: " + handler + " is not a handler");
/*     */     }
/*     */ 
/* 160 */     if (this.log.isDebugEnabled())
/* 161 */       this.log.debug("HiDefaultProcess.addSystemHandler() - end");
/*     */   }
/*     */ 
/*     */   protected IHandler createSystemHandler(String name, String param)
/*     */     throws HiException
/*     */   {
/* 167 */     if (!(HiFrameworkBuilder.getHandlerFactory().contains(name))) {
/* 168 */       throw new HiException("211002", "can't create systemhandler :" + name);
/*     */     }
/*     */ 
/* 172 */     IHandler handler = (IHandler)HiFrameworkBuilder.getHandlerFactory().get(name);
/*     */ 
/* 175 */     if (param != null) {
/*     */       try {
/* 177 */         PropertyUtils.setProperty(handler, "param", param);
/*     */       } catch (Exception e) {
/* 179 */         throw new HiException("211002", "handler '" + name + "':" + e, e);
/*     */       }
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 185 */       PropertyUtils.setProperty(handler, "log", this.server.getLog());
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/* 190 */     return handler;
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     Iterator i;
/* 196 */     if (this.log.isDebugEnabled()) {
/* 197 */       this.log.debug("HiDefaultProcess.process() - start:" + this.name);
/*     */     }
/*     */ 
/* 200 */     HiMessage msg = ctx.getCurrentMsg();
/*     */     try
/*     */     {
/* 204 */       for (i = this.handlers.iterator(); i.hasNext(); ) {
/* 205 */         msg = ctx.getCurrentMsg();
/* 206 */         if (StringUtils.equals(msg.getHeadItem("RSP"), "0")) {
/*     */           break;
/*     */         }
/* 209 */         IHandler handler = (IHandler)i.next();
/* 210 */         handler.process(ctx);
/*     */       }
/*     */     } catch (Throwable e) {
/* 213 */       if (this.errprocess != null) {
/*     */         try {
/* 215 */           this.log.warn(e, e);
/*     */ 
/* 217 */           if (!(msg.hasHeadItem("SSC"))) {
/* 218 */             msg.setHeadItem("SSC", "211007");
/* 219 */             if (e instanceof HiException) {
/* 220 */               HiException te = (HiException)e;
/* 221 */               msg.setHeadItem("SSC", te.getCode());
/*     */             }
/*     */           }
/* 224 */           HiDefaultProcess process = this.server.getProcessByName(this.errprocess);
/* 225 */           if (process != null)
/* 226 */             process.process(ctx);
/*     */         } catch (HiException ee) {
/* 228 */           ee.addMsgStack("211007", this.name);
/* 229 */           HiLog.logServerError(getServer().getName(), ctx.getCurrentMsg(), ee);
/*     */ 
/* 231 */           throw ee;
/*     */         }
/*     */       }
/* 234 */       throw HiException.makeException("211007", this.name, e);
/*     */     }
/*     */ 
/* 242 */     if (this.log.isDebugEnabled())
/* 243 */       this.log.debug("HiDefaultProcess.process() - end:" + this.name);
/*     */   }
/*     */ 
/*     */   public void init()
/*     */     throws HiException
/*     */   {
/*     */   }
/*     */ }