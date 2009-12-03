/*     */ package com.hisun.startup;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.HiConfigParser;
/*     */ import com.hisun.framework.HiDefaultServer;
/*     */ import com.hisun.framework.HiFrameworkBuilder;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.loader.HiClassLoaderFactory;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.register.HiRegisterService;
/*     */ import com.hisun.register.HiServiceObject;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import com.hisun.util.HiResource;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import org.apache.commons.beanutils.PropertyUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiStartup
/*     */ {
/*  37 */   private static HashMap startupMap = new HashMap();
/*     */ 
/*  39 */   private static Logger log = HiLog.getLogger("SYS.trc");
/*     */ 
/*  41 */   private static HiStringManager sm = HiStringManager.getManager();
/*     */ 
/*  43 */   private HiDefaultServer server = null;
/*     */ 
/*  45 */   private int count = 0;
/*     */ 
/*  47 */   private boolean failured = false;
/*     */   private String serverName;
/*     */   private String appName;
/*  55 */   private boolean autoStart = false;
/*     */   private String configFile;
/*     */   private ClassLoader appLoader;
/*     */ 
/*     */   public String getServerName()
/*     */   {
/*  62 */     return this.serverName;
/*     */   }
/*     */ 
/*     */   public static HiStartup initialize(String serverName) throws HiException {
/*  66 */     if (log.isDebugEnabled()) {
/*  67 */       log.debug("HiStartup.initialize() - start");
/*     */     }
/*     */ 
/*  70 */     HiStartup startup = null;
/*     */ 
/*  72 */     synchronized (sm) {
/*     */       try {
/*  74 */         startup = getInstance(serverName);
/*  75 */         startup.init();
/*  76 */         startup.autoStart();
/*     */       } catch (HiException e) {
/*  78 */         if (startup != null) {
/*  79 */           startup.setFailured(true);
/*     */           try {
/*  81 */             startup.destory();
/*     */           } catch (HiException e1) {
/*     */           }
/*  84 */           startup = null;
/*     */         }
/*  86 */         e.addMsgStack("212009", serverName);
/*  87 */         throw e;
/*     */       }
/*     */     }
/*     */ 
/*  91 */     if (log.isDebugEnabled()) {
/*  92 */       log.debug("HiStartup.initialize(String) - end");
/*     */     }
/*  94 */     return startup;
/*     */   }
/*     */ 
/*     */   public static HiStartup initialize(String serverName, boolean start) throws HiException
/*     */   {
/*  99 */     if (log.isDebugEnabled()) {
/* 100 */       log.debug("HiStartup.initialize() - start");
/*     */     }
/* 102 */     HiStartup startup = null;
/*     */ 
/* 104 */     synchronized (sm) {
/*     */       try {
/* 106 */         startup = getInstance(serverName);
/* 107 */         startup.init();
/* 108 */         startup.autoStart = start;
/* 109 */         startup.autoStart();
/*     */       } catch (HiException e) {
/* 111 */         if (startup != null) {
/* 112 */           startup.setFailured(true);
/*     */           try {
/* 114 */             startup.destory();
/*     */           } catch (HiException e1) {
/*     */           }
/* 117 */           startup = null;
/*     */         }
/* 119 */         e.addMsgStack("212009", serverName);
/* 120 */         throw e;
/*     */       }
/*     */     }
/*     */ 
/* 124 */     if (log.isDebugEnabled()) {
/* 125 */       log.debug("HiStartup.initialize(String) - end");
/*     */     }
/* 127 */     return startup;
/*     */   }
/*     */ 
/*     */   public static HiStartup getInstance(String serverName) {
/* 131 */     if (log.isDebugEnabled()) {
/* 132 */       log.debug("HiStartup.getInstance(String) - start");
/*     */     }
/*     */ 
/* 135 */     HiStartup startup = null;
/* 136 */     if ((startup = (HiStartup)startupMap.get(serverName)) == null) {
/* 137 */       startup = new HiStartup(serverName);
/* 138 */       startupMap.put(serverName, startup);
/*     */     }
/* 140 */     startup.count += 1;
/*     */ 
/* 142 */     if (log.isDebugEnabled()) {
/* 143 */       log.debug("HiStartup.getInstance(String) - end");
/*     */     }
/* 145 */     return startup;
/*     */   }
/*     */ 
/*     */   public HiStartup(String serverName) {
/* 149 */     this.serverName = serverName;
/* 150 */     this.configFile = "etc/" + serverName + "_ATR.XML";
/*     */   }
/*     */ 
/*     */   public void setFailured(boolean failured) {
/* 154 */     this.failured = failured;
/*     */   }
/*     */ 
/*     */   public boolean isFailured() {
/* 158 */     return this.failured;
/*     */   }
/*     */ 
/*     */   public void init()
/*     */     throws HiException
/*     */   {
/* 166 */     ClassLoader oldLoader = Thread.currentThread().getContextClassLoader();
/*     */ 
/* 168 */     if (HiICSProperty.getWorkDir() == null)
/* 169 */       throw new HiException("212012", "HWORKDIR");
/*     */   }
/*     */ 
/*     */   public void autoStart()
/*     */     throws HiException
/*     */   {
/* 185 */     if (log.isDebugEnabled()) {
/* 186 */       log.debug("HiStartup.autoStart() - start");
/*     */     }
/*     */ 
/* 189 */     if (this.failured) {
/* 190 */       if (log.isDebugEnabled()) {
/* 191 */         log.debug("HiStartup.autoStart() - end");
/*     */       }
/* 193 */       return;
/*     */     }
/* 195 */     if (!(this.autoStart)) {
/* 196 */       if (log.isDebugEnabled()) {
/* 197 */         log.debug("HiStartup.autoStart() - end");
/*     */       }
/* 199 */       return;
/*     */     }
/*     */ 
/* 202 */     start();
/*     */ 
/* 204 */     if (log.isDebugEnabled())
/* 205 */       log.debug("HiStartup.autoStart() - end");
/*     */   }
/*     */ 
/*     */   public void destory()
/*     */     throws HiException
/*     */   {
/* 214 */     if (log.isDebugEnabled()) {
/* 215 */       log.debug("destory() - start");
/*     */     }
/*     */ 
/* 218 */     synchronized (sm) {
/* 219 */       if (log.isDebugEnabled()) {
/* 220 */         log.debug(sm.getString("HiStartup.destory", this.serverName, String.valueOf(this.count)));
/*     */       }
/* 222 */       if (--this.count != 0) {
/* 223 */         return;
/*     */       }
/* 225 */       stop();
/*     */     }
/*     */ 
/* 228 */     if (log.isDebugEnabled())
/* 229 */       log.debug("HiStartup.destory() - end");
/*     */   }
/*     */ 
/*     */   public void start()
/*     */     throws HiException
/*     */   {
/* 238 */     if (log.isDebugEnabled()) {
/* 239 */       log.debug("HiStartup.start() - start");
/*     */     }
/*     */ 
/* 242 */     if (log.isInfoEnabled()) {
/* 243 */       log.info(sm.getString("HiStartup.start", this.serverName));
/*     */     }
/*     */ 
/* 246 */     this.server = loadServer(this.configFile);
/* 247 */     this.server.init();
/* 248 */     this.server.start();
/*     */ 
/* 250 */     HiServiceObject obj = new HiServiceObject(this.serverName);
/* 251 */     obj.setServerName(this.serverName);
/* 252 */     obj.setServerType(this.server.getType());
/* 253 */     obj.setLogLevel(this.server.getTrace());
/* 254 */     HiRegisterService.register(obj);
/*     */ 
/* 256 */     if (log.isDebugEnabled())
/* 257 */       log.debug("HiStartup.start() - end");
/*     */   }
/*     */ 
/*     */   public void stop()
/*     */     throws HiException
/*     */   {
/* 263 */     if (log.isInfoEnabled()) {
/* 264 */       log.info("HiStartup.stop");
/*     */     }
/*     */ 
/* 267 */     if (this.server == null)
/* 268 */       return;
/*     */     try
/*     */     {
/* 271 */       HiRegisterService.unregister(this.serverName);
/*     */     }
/*     */     catch (HiException e) {
/*     */     }
/* 275 */     this.server.stop();
/* 276 */     this.server.destroy();
/* 277 */     this.server = null;
/*     */ 
/* 279 */     if (log.isDebugEnabled())
/* 280 */       log.debug("HiStartup.stop() - end");
/*     */   }
/*     */ 
/*     */   public void resume()
/*     */     throws HiException
/*     */   {
/* 289 */     if (log.isDebugEnabled()) {
/* 290 */       log.debug("HiStartup.resume() - start");
/*     */     }
/*     */ 
/* 293 */     if (this.server == null) {
/* 294 */       throw new HiException("212010");
/*     */     }
/*     */ 
/* 298 */     this.server.start();
/*     */ 
/* 300 */     if (log.isDebugEnabled())
/* 301 */       log.debug("HiStartup.resume() - end");
/*     */   }
/*     */ 
/*     */   public void pause()
/*     */     throws HiException
/*     */   {
/* 310 */     if (log.isDebugEnabled()) {
/* 311 */       log.debug("HiStartup.pause() - start");
/*     */     }
/*     */ 
/* 314 */     if (this.server == null) {
/* 315 */       return;
/*     */     }
/*     */ 
/* 318 */     this.server.stop();
/*     */ 
/* 320 */     if (log.isDebugEnabled())
/* 321 */       log.debug("HiStartup.pause() - end");
/*     */   }
/*     */ 
/*     */   public void reload()
/*     */     throws HiException
/*     */   {
/* 330 */     if (log.isDebugEnabled()) {
/* 331 */       log.debug("HiStartup.reload() - start");
/*     */     }
/*     */ 
/* 334 */     stop();
/* 335 */     start();
/*     */ 
/* 337 */     if (log.isDebugEnabled())
/* 338 */       log.debug("HiStartup.reload() - end");
/*     */   }
/*     */ 
/*     */   public HiMessage process(HiMessage message)
/*     */     throws HiException
/*     */   {
/* 349 */     if (log.isDebugEnabled()) {
/* 350 */       log.debug("HiStartup.process(HiMessage) - start");
/*     */     }
/* 352 */     if (this.server == null) {
/* 353 */       throw new HiException("212010", this.serverName);
/*     */     }
/* 355 */     ClassLoader oldLoader = Thread.currentThread().getContextClassLoader();
/*     */ 
/* 357 */     Thread.currentThread().setContextClassLoader(this.appLoader);
/*     */ 
/* 359 */     Logger log1 = HiLog.getLogger(message);
/* 360 */     if (log1.isDebugEnabled()) {
/* 361 */       log1.debug(sm.getString("HiStartup.process", this.server.getType(), this.server.getName()));
/*     */     }
/* 363 */     HiMessageContext ctx = new HiMessageContext();
/* 364 */     ctx.setCurrentMsg(message);
/*     */ 
/* 366 */     HiMessage msg = null;
/*     */     try {
/* 368 */       this.server.process(ctx);
/* 369 */       msg = ctx.getCurrentMsg();
/*     */     } finally {
/* 371 */       Thread.currentThread().setContextClassLoader(oldLoader);
/*     */ 
/* 373 */       ctx.clear();
/* 374 */       ctx = null;
/*     */     }
/* 376 */     if (log.isDebugEnabled()) {
/* 377 */       log.debug("HiStartup.process(HiMessage) - end");
/*     */     }
/* 379 */     return msg;
/*     */   }
/*     */ 
/*     */   public void load(String serverName, String configFile) throws HiException {
/* 383 */     this.serverName = serverName;
/* 384 */     this.configFile = configFile;
/* 385 */     init();
/* 386 */     start();
/*     */   }
/*     */ 
/*     */   public synchronized HiMessage manage(HiMessage message)
/*     */     throws HiException
/*     */   {
/* 396 */     if (log.isDebugEnabled()) {
/* 397 */       log.debug("HiStartup.manange(HiMessage) - start");
/*     */     }
/*     */ 
/* 400 */     String cmd = message.getHeadItem("CMD");
/* 401 */     HiETF etf = (HiETF)message.getBody();
/* 402 */     this.appName = etf.getChildValue("APP_NM");
/* 403 */     this.serverName = etf.getChildValue("SERVER");
/* 404 */     this.configFile = etf.getChildValue("CONFIG_FILE");
/* 405 */     if (log.isDebugEnabled())
/* 406 */       log.debug(sm.getString("HiStartup.manange", message));
/* 407 */     if (StringUtils.equalsIgnoreCase(cmd, "START")) {
/* 408 */       if (this.server == null)
/* 409 */         load(message);
/*     */       else
/* 411 */         reload();
/*     */     }
/* 413 */     else if (StringUtils.equalsIgnoreCase(cmd, "RESTART"))
/*     */     {
/* 417 */       if (this.server == null)
/* 418 */         load(message);
/*     */       else
/* 420 */         reload();
/*     */     }
/* 422 */     else if (StringUtils.equalsIgnoreCase(cmd, "STOP")) {
/* 423 */       if (this.server == null) {
/* 424 */         throw new HiException("212010", this.serverName);
/*     */       }
/* 426 */       stop();
/* 427 */     } else if (StringUtils.equalsIgnoreCase(cmd, "PAUSE")) {
/* 428 */       if (this.server == null) {
/* 429 */         throw new HiException("212010", this.serverName);
/*     */       }
/* 431 */       pause();
/* 432 */     } else if (StringUtils.equalsIgnoreCase(cmd, "RESUME")) {
/* 433 */       if (this.server == null) {
/* 434 */         throw new HiException("212010", this.serverName);
/*     */       }
/* 436 */       resume();
/*     */     }
/* 438 */     if (log.isDebugEnabled()) {
/* 439 */       log.debug("HiStartup.manange(HiMessage) - end");
/*     */     }
/* 441 */     return message;
/*     */   }
/*     */ 
/*     */   private void load(HiMessage message)
/*     */     throws HiException
/*     */   {
/* 450 */     if (log.isDebugEnabled()) {
/* 451 */       log.debug("HiStartup.load(HiMessage) - start");
/*     */     }
/*     */ 
/* 454 */     if (this.server != null) {
/* 455 */       throw new HiException("212011", this.server.getName());
/*     */     }
/*     */ 
/* 459 */     HiETF etf = (HiETF)message.getBody();
/* 460 */     this.serverName = etf.getChildValue("SERVER");
/* 461 */     this.configFile = etf.getChildValue("CONFIG_FILE");
/*     */ 
/* 463 */     init();
/* 464 */     start();
/*     */ 
/* 466 */     if (log.isDebugEnabled())
/* 467 */       log.debug("HiStartup.load(HiMessage) - end");
/*     */   }
/*     */ 
/*     */   private HiDefaultServer loadServer(String file)
/*     */     throws HiException
/*     */   {
/* 496 */     if (log.isDebugEnabled()) {
/* 497 */       log.debug("HiStartup.loadFile() - start");
/*     */     }
/* 499 */     HiICSProperty.reload();
/* 500 */     ClassLoader oldLoader = null;
/* 501 */     InputStream is = null;
/* 502 */     HiDefaultServer server = null;
/*     */     try {
/* 504 */       oldLoader = Thread.currentThread().getContextClassLoader();
/* 505 */       if (this.appLoader != null) {
/* 506 */         Thread.currentThread().setContextClassLoader(this.appLoader);
/* 507 */         PropertyUtils.clearDescriptors();
/*     */       }
/* 509 */       this.appLoader = HiClassLoaderFactory.createClassLoader("application", this.appName, oldLoader);
/*     */ 
/* 512 */       if (log.isDebugEnabled()) {
/* 513 */         log.debug("appLoader:" + this.appLoader);
/*     */       }
/* 515 */       Thread.currentThread().setContextClassLoader(this.appLoader);
/* 516 */       is = HiResource.getResourceAsStream(file);
/* 517 */       if (is == null) {
/* 518 */         throw new HiException("212004", file);
/*     */       }
/*     */ 
/* 521 */       HiConfigParser parser = HiFrameworkBuilder.getParser();
/* 522 */       server = parser.parseServerXML(is);
/* 523 */       parser = null;
/*     */     } catch (HiException e) {
/* 525 */       server = null;
/*     */ 
/* 527 */       throw e;
/*     */     } finally {
/*     */       try {
/* 530 */         if (is != null) {
/* 531 */           is.close();
/* 532 */           is = null;
/*     */         }
/* 534 */         Thread.currentThread().setContextClassLoader(oldLoader);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/*     */     }
/* 540 */     if (log.isDebugEnabled()) {
/* 541 */       log.debug("HiStartup.loadFile(String) - end");
/*     */     }
/* 543 */     return server;
/*     */   }
/*     */ }