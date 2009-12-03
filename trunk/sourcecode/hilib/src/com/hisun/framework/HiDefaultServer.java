/*     */ package com.hisun.framework;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.event.IServerDestroyListener;
/*     */ import com.hisun.framework.event.IServerInitListener;
/*     */ import com.hisun.framework.event.IServerPauseListener;
/*     */ import com.hisun.framework.event.IServerResumeListener;
/*     */ import com.hisun.framework.event.IServerStartListener;
/*     */ import com.hisun.framework.event.IServerStopListener;
/*     */ import com.hisun.framework.event.ServerEvent;
/*     */ import com.hisun.framework.filter.SetContextFilter;
/*     */ import com.hisun.framework.imp.HiDefaultProcess;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.pipeline.PipelineBuilder;
/*     */ import com.hisun.pubinterface.IHandler;
/*     */ import com.hisun.pubinterface.IHandlerFilter;
/*     */ import com.hisun.register.HiRegisterService;
/*     */ import com.hisun.register.HiServiceObject;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.beanutils.PropertyUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiDefaultServer
/*     */   implements IServer
/*     */ {
/*     */   Logger log;
/*  46 */   public static final HiStringManager sm = HiStringManager.getManager();
/*     */   private Map declares;
/*     */   private Map processes;
/*     */   private Map processesbyname;
/*     */   private boolean isRunning;
/*     */   private boolean isPause;
/*     */   private String name;
/*     */   private String type;
/*     */   private String trace;
/*     */   private HiContext serverContext;
/*     */   private List filters;
/*     */   private IHandler procHandler;
/*     */ 
/*     */   public HiDefaultServer()
/*     */   {
/*  48 */     this.declares = new LinkedHashMap();
/*     */ 
/*  52 */     this.processes = new HashMap();
/*     */ 
/*  54 */     this.processesbyname = new HashMap();
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  71 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/*  75 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getType() {
/*  79 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(String type) {
/*  83 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public String getTrace() {
/*  87 */     return this.trace;
/*     */   }
/*     */ 
/*     */   public void setTrace(String trace) {
/*  91 */     this.trace = trace;
/*     */   }
/*     */ 
/*     */   public HiContext getServerContext() {
/*  95 */     return this.serverContext;
/*     */   }
/*     */ 
/*     */   public void addDeclare(String name, Object declare)
/*     */     throws Exception
/*     */   {
/* 101 */     if (this.log.isDebugEnabled()) {
/* 102 */       this.log.debug("addDeclare() - start:" + name);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 108 */       PropertyUtils.setProperty(declare, "server", this);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */     try
/*     */     {
/* 115 */       PropertyUtils.setProperty(declare, "log", this.log);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */     try
/*     */     {
/* 122 */       if (declare instanceof IServerInitListener) {
/* 123 */         IServerInitListener init = (IServerInitListener)declare;
/* 124 */         init.serverInit(new ServerEvent(this, this.log, this.serverContext));
/*     */       }
/* 126 */       if (this.log.isInfoEnabled())
/* 127 */         this.log.info(sm.getString("HiDefaultServer.init00", name));
/*     */     } catch (HiException e) {
/* 129 */       HiLog.logServerError(this.name, null, e);
/* 130 */       destroy();
/* 131 */       throw e;
/*     */     }
/*     */ 
/* 134 */     this.declares.put(name, declare);
/*     */ 
/* 137 */     if (declare instanceof IHandlerFilter) {
/* 138 */       addFilter((IHandlerFilter)declare);
/*     */     }
/*     */ 
/* 141 */     if (this.log.isDebugEnabled())
/* 142 */       this.log.debug("addDeclare() - end:" + name);
/*     */   }
/*     */ 
/*     */   public Object getDeclare(String name)
/*     */   {
/* 147 */     return this.declares.get(name);
/*     */   }
/*     */ 
/*     */   public void addProcess(HiDefaultProcess process) {
/* 151 */     if (this.log.isDebugEnabled()) {
/* 152 */       this.log.debug("addProcess() - start:" + process.getName());
/*     */     }
/*     */ 
/* 155 */     if (StringUtils.equalsIgnoreCase("default", process.getMsgtype()))
/* 156 */       this.processes.put("default", process);
/*     */     else {
/* 158 */       this.processes.put(process.getMsgtype(), process);
/*     */     }
/* 160 */     this.processesbyname.put(process.getName(), process);
/*     */ 
/* 162 */     if (this.log.isDebugEnabled())
/* 163 */       this.log.debug("addProcess() - end:" + process.getName());
/*     */   }
/*     */ 
/*     */   public HiDefaultProcess getProcess(String msgtype)
/*     */   {
/* 168 */     if (this.log.isDebugEnabled()) {
/* 169 */       this.log.debug("getProcess() - start");
/*     */     }
/*     */ 
/* 172 */     HiDefaultProcess returnHiDefaultProcess = (HiDefaultProcess)this.processes.get(msgtype);
/*     */ 
/* 174 */     if (this.log.isDebugEnabled()) {
/* 175 */       this.log.debug("getProcess() - end");
/*     */     }
/* 177 */     return returnHiDefaultProcess;
/*     */   }
/*     */ 
/*     */   public HiDefaultProcess getProcessByName(String name) {
/* 181 */     if (this.log.isDebugEnabled()) {
/* 182 */       this.log.debug("getProcessByName() - start");
/*     */     }
/*     */ 
/* 185 */     HiDefaultProcess returnHiDefaultProcess = (HiDefaultProcess)this.processesbyname.get(name);
/*     */ 
/* 187 */     if (this.log.isDebugEnabled()) {
/* 188 */       this.log.debug("getProcess() - end");
/*     */     }
/* 190 */     return returnHiDefaultProcess;
/*     */   }
/*     */ 
/*     */   public void startBuild()
/*     */   {
/* 195 */     this.log = HiLog.getLogger(this.name + ".trc");
/*     */ 
/* 197 */     if (this.trace != null) {
/* 198 */       this.log.setLevel(Logger.toLevel(this.trace));
/*     */     }
/* 200 */     this.serverContext = HiContext.createContext("SVR." + this.name, null);
/*     */ 
/* 203 */     this.serverContext.setProperty("SVR.type", this.type, true);
/* 204 */     this.serverContext.setProperty("SVR.name", this.name, true);
/* 205 */     this.serverContext.setProperty("SVR.log", this.log, true);
/* 206 */     this.serverContext.setProperty("SVR.server", this, true);
/*     */ 
/* 208 */     HiContext.pushCurrentContext(this.serverContext);
/*     */ 
/* 210 */     if (this.log.isDebugEnabled())
/* 211 */       this.log.debug("server [" + this.name + "] start build! [name:" + this.name + ",trace:" + this.trace + "]");
/*     */   }
/*     */ 
/*     */   public void endBuild()
/*     */   {
/* 217 */     if (this.log.isDebugEnabled()) {
/* 218 */       this.log.debug("server [" + this.name + "] end build!");
/*     */     }
/* 220 */     HiContext.popCurrentContext();
/* 221 */     HiContext.removeCurrentContext();
/*     */   }
/*     */ 
/*     */   public Logger getLog()
/*     */   {
/* 227 */     return this.log;
/*     */   }
/*     */ 
/*     */   public boolean isRunning() {
/* 231 */     return this.isRunning;
/*     */   }
/*     */ 
/*     */   public boolean isPaused() {
/* 235 */     return this.isPause;
/*     */   }
/*     */ 
/*     */   public void init() throws HiException
/*     */   {
/*     */   }
/*     */ 
/*     */   public void destroy() {
/* 243 */     if (this.log.isDebugEnabled()) {
/* 244 */       this.log.debug("destroy() - start");
/*     */     }
/* 246 */     if (this.declares == null) {
/* 247 */       return;
/*     */     }
/* 249 */     ServerEvent event = new ServerEvent(this, this.log, this.serverContext);
/* 250 */     for (Iterator it = this.declares.keySet().iterator(); it.hasNext(); ) {
/* 251 */       String key = (String)it.next();
/* 252 */       Object declare = this.declares.get(key);
/* 253 */       if (declare instanceof IServerDestroyListener) {
/*     */         try
/*     */         {
/* 256 */           ((IServerDestroyListener)declare).serverDestroy(event);
/* 257 */           if (this.log.isInfoEnabled())
/* 258 */             this.log.info("destroy Declare Object : " + key);
/*     */         }
/*     */         catch (HiException e) {
/* 261 */           HiLog.logServerError(this.name, null, e);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 269 */     this.declares.clear();
/* 270 */     this.declares = null;
/* 271 */     if (this.serverContext != null) {
/* 272 */       this.serverContext.clear();
/* 273 */       this.serverContext = null;
/*     */     }
/* 275 */     if (this.log.isInfoEnabled())
/* 276 */       this.log.info(sm.getString("HiDefaultServer.destroy01", this.name));
/*     */   }
/*     */ 
/*     */   public void start() throws HiException
/*     */   {
/* 281 */     if (this.log.isDebugEnabled()) {
/* 282 */       this.log.debug("start() - start");
/*     */     }
/*     */ 
/* 286 */     if (isRunning()) {
/* 287 */       return;
/*     */     }
/* 289 */     ServerEvent event = new ServerEvent(this, this.log, this.serverContext);
/* 290 */     for (Iterator it = this.declares.keySet().iterator(); it.hasNext(); ) {
/* 291 */       String key = (String)it.next();
/* 292 */       Object declare = this.declares.get(key);
/* 293 */       if (declare instanceof IServerStartListener) {
/*     */         try
/*     */         {
/* 296 */           ((IServerStartListener)declare).serverStart(event);
/* 297 */           if (this.log.isInfoEnabled())
/* 298 */             this.log.info("start Declare Object : " + key);
/*     */         }
/*     */         catch (HiException e) {
/* 301 */           HiLog.logServerError(this.name, null, e);
/* 302 */           throw e;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 307 */     this.isRunning = true;
/* 308 */     if (this.log.isInfoEnabled())
/* 309 */       this.log.info(sm.getString("HiDefaultServer.start01", this.name, this.type));
/*     */   }
/*     */ 
/*     */   public void stop() throws HiException {
/* 313 */     if (this.log.isDebugEnabled()) {
/* 314 */       this.log.debug("stop() - start");
/*     */     }
/*     */ 
/* 317 */     if (!(isRunning())) {
/* 318 */       return;
/*     */     }
/* 320 */     ServerEvent event = new ServerEvent(this, this.log, this.serverContext);
/* 321 */     for (Iterator it = this.declares.keySet().iterator(); it.hasNext(); ) {
/* 322 */       String key = (String)it.next();
/* 323 */       Object declare = this.declares.get(key);
/* 324 */       if (declare instanceof IServerStopListener) {
/*     */         try
/*     */         {
/* 327 */           ((IServerStopListener)declare).serverStop(event);
/* 328 */           if (this.log.isInfoEnabled())
/* 329 */             this.log.info("stop Declare Object : " + key);
/*     */         }
/*     */         catch (HiException e) {
/* 332 */           HiLog.logServerError(this.name, null, e);
/* 333 */           throw e;
/*     */         }
/*     */       }
/*     */     }
/* 337 */     this.isRunning = false;
/* 338 */     if (this.log.isInfoEnabled())
/* 339 */       this.log.info(sm.getString("HiDefaultServer.stop01", this.name, this.type));
/*     */   }
/*     */ 
/*     */   public void pause() {
/* 343 */     if (this.log.isDebugEnabled()) {
/* 344 */       this.log.debug("pause() - start");
/*     */     }
/*     */ 
/* 347 */     if (!(isRunning()))
/* 348 */       return;
/* 349 */     if (this.isPause) {
/* 350 */       return;
/*     */     }
/* 352 */     ServerEvent event = new ServerEvent(this, this.log, this.serverContext);
/* 353 */     for (Iterator it = this.declares.keySet().iterator(); it.hasNext(); ) {
/* 354 */       String key = (String)it.next();
/* 355 */       Object declare = this.declares.get(key);
/* 356 */       if (declare instanceof IServerPauseListener) {
/* 357 */         ((IServerPauseListener)declare).serverPause(event);
/* 358 */         if (this.log.isInfoEnabled()) {
/* 359 */           this.log.info(sm.getString("HiDefaultServer.pause00", key));
/*     */         }
/*     */       }
/*     */     }
/* 363 */     this.isPause = true;
/* 364 */     if (this.log.isInfoEnabled())
/* 365 */       this.log.info(sm.getString("HiDefaultServer.pause01", this.name, this.type));
/*     */   }
/*     */ 
/*     */   public void resume() {
/* 369 */     if (this.log.isDebugEnabled()) {
/* 370 */       this.log.debug("resume() - start");
/*     */     }
/*     */ 
/* 373 */     if (!(isRunning()))
/* 374 */       return;
/* 375 */     if (!(this.isPause)) {
/* 376 */       return;
/*     */     }
/* 378 */     ServerEvent event = new ServerEvent(this, this.log, this.serverContext);
/* 379 */     for (Iterator it = this.declares.keySet().iterator(); it.hasNext(); ) {
/* 380 */       String key = (String)it.next();
/* 381 */       Object declare = this.declares.get(key);
/* 382 */       if (declare instanceof IServerResumeListener) {
/* 383 */         ((IServerResumeListener)declare).serverResume(event);
/* 384 */         if (this.log.isInfoEnabled()) {
/* 385 */           this.log.info(sm.getString("HiDefaultServer.resume00", key));
/*     */         }
/*     */       }
/*     */     }
/* 389 */     this.isPause = false;
/* 390 */     if (this.log.isInfoEnabled())
/* 391 */       this.log.info(sm.getString("HiDefaultServer.resume01", this.name, this.type));
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx) throws HiException {
/* 395 */     this.procHandler.process(ctx);
/*     */   }
/*     */ 
/*     */   public void buildProcHandler()
/*     */   {
/* 400 */     IHandler handler = new procHandler();
/*     */ 
/* 402 */     IHandlerFilter setCtxFilter = new SetContextFilter(this.serverContext);
/* 403 */     if (this.filters == null)
/* 404 */       addFilter(setCtxFilter);
/*     */     else {
/* 406 */       ((ArrayList)this.filters).add(0, setCtxFilter);
/*     */     }
/* 408 */     this.procHandler = ((IHandler)HiFrameworkBuilder.getPipelineBuilder().buildPipeline(this.filters, handler));
/*     */   }
/*     */ 
/*     */   public void addFilter(IHandlerFilter filter)
/*     */   {
/* 413 */     if (this.filters == null)
/* 414 */       this.filters = new ArrayList();
/* 415 */     this.filters.add(filter);
/*     */   }
/*     */ 
/*     */   class procHandler
/*     */     implements IHandler
/*     */   {
/*     */     public void process(HiMessageContext ctx)
/*     */       throws HiException
/*     */     {
/*     */       String errmsg;
/* 433 */       HiMessage msg = ctx.getCurrentMsg();
/*     */ 
/* 435 */       HiServiceObject obj = HiRegisterService.getService(HiDefaultServer.this.name);
/* 436 */       if (!(obj.isRunning())) {
/* 437 */         errmsg = HiDefaultServer.sm.getString("HiDefaultServer.process01", msg.getRequestId(), HiDefaultServer.this.name, HiDefaultServer.this.type);
/*     */ 
/* 439 */         HiDefaultServer.this.log.error(errmsg);
/* 440 */         throw new HiException("211003", errmsg);
/*     */       }
/* 442 */       HiDefaultServer.this.log.setLevel(Logger.toLevel(obj.getLogLevel()));
/* 443 */       if (!(HiDefaultServer.this.isRunning()))
/*     */       {
/* 445 */         errmsg = HiDefaultServer.sm.getString("HiDefaultServer.process01", msg.getRequestId(), HiDefaultServer.this.name, HiDefaultServer.this.type);
/*     */ 
/* 447 */         HiDefaultServer.this.log.error(errmsg);
/* 448 */         throw new HiException("211003", errmsg);
/*     */       }
/*     */ 
/* 452 */       String msgtype = msg.getType();
/* 453 */       HiDefaultProcess process = (HiDefaultProcess)HiDefaultServer.this.processes.get(msgtype);
/*     */ 
/* 456 */       if (process == null) {
/* 457 */         process = (HiDefaultProcess)HiDefaultServer.this.processes.get("default");
/*     */       }
/*     */ 
/* 460 */       if (process == null) {
/* 461 */         String errmsg = HiDefaultServer.sm.getString("HiDefaultServer.process02", msg.getRequestId(), HiDefaultServer.this.name, HiDefaultServer.this.type, msgtype);
/*     */ 
/* 463 */         HiDefaultServer.this.log.error(errmsg);
/* 464 */         throw new HiException("211004", errmsg);
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 469 */         if (HiDefaultServer.this.log.isInfoEnabled()) {
/* 470 */           HiDefaultServer.this.log.info(HiDefaultServer.sm.getString("HiDefaultServer.process03", msg.getRequestId(), msgtype, process.getName()));
/*     */         }
/*     */ 
/* 474 */         process.process(ctx);
/*     */       } catch (HiException e) {
/* 476 */         HiLog.logServerError(HiDefaultServer.this.name, ctx.getCurrentMsg(), e);
/* 477 */         throw e;
/*     */       }
/*     */     }
/*     */   }
/*     */ }