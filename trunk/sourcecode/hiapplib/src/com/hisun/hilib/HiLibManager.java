/*     */ package com.hisun.hilib;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ 
/*     */ public class HiLibManager
/*     */ {
/*  11 */   private static Logger log = HiLog.getLogger("lib.trc");
/*     */   private static final int LOCALLIB = 1;
/*     */   private static final int SPRINGLIB = 2;
/*     */   public static final String SPRINGCONF = "spring.config";
/*     */ 
/*     */   public static synchronized int loadComponent()
/*     */     throws HiException
/*     */   {
/*  26 */     if (log.isDebugEnabled()) {
/*  27 */       log.debug("libmanager->loadComponent");
/*     */     }
/*     */ 
/*  31 */     boolean founded = HiLib.loadComponent();
/*     */ 
/*  34 */     boolean founded2 = HiSpringLibManager.loadComponent();
/*     */ 
/*  36 */     if ((!(founded)) && (!(founded2)) && (log.isWarnEnabled())) {
/*  37 */       log.warn("libmanager->loadComponent, not any lib Manager startup!");
/*     */     }
/*  39 */     return 0;
/*     */   }
/*     */ 
/*     */   public static synchronized int loadComponent(String name)
/*     */     throws HiException
/*     */   {
/*  50 */     if (log.isDebugEnabled()) {
/*  51 */       log.debug("libmanager->loadComponent:[" + name + "]");
/*     */     }
/*     */ 
/*  55 */     boolean founded = HiLib.loadComponent(name);
/*     */ 
/*  57 */     if (founded) {
/*  58 */       return 1;
/*     */     }
/*     */ 
/*  61 */     founded = HiSpringLibManager.loadComponent(name);
/*     */ 
/*  63 */     if (!(founded)) {
/*  64 */       throw new HiException("215004", name);
/*     */     }
/*  66 */     return 2;
/*     */   }
/*     */ 
/*     */   public static Object invoke(String name, HiATLParam args, HiMessageContext ctx, int libType)
/*     */     throws HiException
/*     */   {
/*  84 */     if (libType == 1)
/*  85 */       return HiLib.invoke(name, args, ctx);
/*  86 */     if (libType == 2) {
/*  87 */       return HiSpringLibManager.invoke(name, args, ctx);
/*     */     }
/*     */ 
/*  90 */     return HiLib.invoke(name, args, ctx);
/*     */   }
/*     */ 
/*     */   public static Object invoke(String name, HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 108 */     if (HiLib.contains(name))
/*     */     {
/* 110 */       return HiLib.invoke(name, args, ctx);
/*     */     }
/* 112 */     if (HiSpringLibManager.contains(name))
/*     */     {
/* 114 */       return HiSpringLibManager.invoke(name, args, ctx);
/*     */     }
/*     */ 
/* 118 */     throw new HiException("215006", name);
/*     */   }
/*     */ }