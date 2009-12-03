/*     */ package com.hisun.hilib;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ class HiFunctionItem
/*     */ {
/*     */   Method method;
/*     */   Object object;
/*     */ 
/*     */   public HiFunctionItem(Object object, Method method)
/*     */   {
/* 501 */     this.object = object;
/* 502 */     this.method = method;
/*     */   }
/*     */ }