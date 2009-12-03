/*     */ package com.hisun.hilib;
/*     */ 
/*     */ import com.hisun.bean.HiBeanManager;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.component.HiIComponent;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import org.springframework.context.ApplicationContext;
/*     */ 
/*     */ public class HiSpringLibManager
/*     */ {
/*  16 */   private static Logger log = HiLog.getLogger("lib.trc");
/*     */ 
/*     */   public static synchronized boolean loadComponent(String name)
/*     */     throws HiException
/*     */   {
/*  30 */     if (log.isDebugEnabled()) {
/*  31 */       log.debug("springlibmanager->loadComponent:[" + name + "]");
/*     */     }
/*     */ 
/*  34 */     ApplicationContext springCtx = getSpringCtx();
/*  35 */     if (springCtx == null)
/*     */     {
/*  37 */       if (log.isDebugEnabled()) {
/*  38 */         log.debug("springlibmanager->loadComponent, can't startup");
/*     */       }
/*  40 */       return false;
/*     */     }
/*     */ 
/*  43 */     if (!(springCtx.containsBean(name)))
/*     */     {
/*  45 */       if (log.isDebugEnabled()) {
/*  46 */         log.debug("springlibmanager->loadComponent: can't find [" + name + "]");
/*     */       }
/*  48 */       return false;
/*     */     }
/*  50 */     return true;
/*     */   }
/*     */ 
/*     */   public static synchronized boolean loadComponent()
/*     */     throws HiException
/*     */   {
/*  61 */     if (log.isDebugEnabled()) {
/*  62 */       log.debug("springlibmanager->loadComponent");
/*     */     }
/*     */ 
/*  65 */     ApplicationContext springCtx = getSpringCtx();
/*  66 */     if (springCtx == null)
/*     */     {
/*  68 */       if (log.isDebugEnabled()) {
/*  69 */         log.debug("springlibmanager->loadComponent, applicationcontext is null");
/*     */       }
/*  71 */       return false;
/*     */     }
/*     */ 
/*  74 */     return true;
/*     */   }
/*     */ 
/*     */   public static boolean contains(String name)
/*     */     throws HiException
/*     */   {
/*  84 */     if (log.isDebugEnabled()) {
/*  85 */       log.debug("springlibmanager->contains:[" + name + "]");
/*     */     }
/*     */ 
/*  88 */     ApplicationContext springCtx = getSpringCtx();
/*  89 */     if (springCtx == null)
/*     */     {
/*  91 */       return false;
/*     */     }
/*  93 */     return springCtx.containsBean(name);
/*     */   }
/*     */ 
/*     */   public static ApplicationContext getSpringCtx()
/*     */   {
/* 103 */     ApplicationContext springCtx = HiBeanManager.getServerSpringCtx();
/* 104 */     if (springCtx == null)
/*     */     {
/* 106 */       String config = HiICSProperty.getProperty("spring.config");
/* 107 */       if (config == null)
/*     */       {
/* 109 */         log.warn("springlibmanager->Read File sys.properties: spring.config is null!");
/* 110 */         return null;
/*     */       }
/*     */ 
/* 113 */       springCtx = HiBeanManager.loadSpringCtxAndBindServer(config);
/*     */     }
/* 115 */     return springCtx;
/*     */   }
/*     */ 
/*     */   public static Object invoke(String name, HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 129 */     ApplicationContext springCtx = HiBeanManager.getServerSpringCtx(ctx);
/* 130 */     return invoke(springCtx, name, args, ctx);
/*     */   }
/*     */ 
/*     */   public static Object invoke(ApplicationContext springCtx, String name, HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 148 */     if (springCtx != null)
/*     */     {
/* 150 */       HiIComponent cmp = (HiIComponent)springCtx.getBean(name);
/* 151 */       if (cmp == null)
/*     */       {
/* 153 */         throw new HiException("215006", name);
/*     */       }
/* 155 */       return Integer.valueOf(cmp.execute(args, ctx));
/*     */     }
/*     */ 
/* 159 */     throw new HiException("215006", name);
/*     */   }
/*     */ }