/*     */ package com.hisun.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class MessageResourcesFactory
/*     */   implements Serializable
/*     */ {
/*  51 */   protected static transient Class clazz = null;
/*     */ 
/*  62 */   protected static String factoryClass = "com.hisun.util.PropertyMessageResourcesFactory";
/*     */   protected boolean returnNull;
/*     */ 
/*     */   public MessageResourcesFactory()
/*     */   {
/*  76 */     this.returnNull = false;
/*     */   }
/*     */ 
/*     */   public boolean getReturnNull()
/*     */   {
/* 104 */     return this.returnNull;
/*     */   }
/*     */ 
/*     */   public void setReturnNull(boolean returnNull)
/*     */   {
/* 115 */     this.returnNull = returnNull;
/*     */   }
/*     */ 
/*     */   public abstract MessageResources createResources(String paramString);
/*     */ 
/*     */   public static String getFactoryClass()
/*     */   {
/* 136 */     return factoryClass;
/*     */   }
/*     */ 
/*     */   public static void setFactoryClass(String factoryClass)
/*     */   {
/* 147 */     factoryClass = factoryClass;
/* 148 */     clazz = null;
/*     */   }
/*     */ 
/*     */   public static MessageResourcesFactory createFactory()
/*     */   {
/*     */     try
/*     */     {
/* 162 */       if (clazz == null) {
/* 163 */         clazz = Class.forName(factoryClass);
/*     */       }
/*     */ 
/* 166 */       MessageResourcesFactory factory = (MessageResourcesFactory)clazz.newInstance();
/*     */ 
/* 169 */       return factory;
/*     */     }
/*     */     catch (Throwable t) {
/*     */     }
/* 173 */     return null;
/*     */   }
/*     */ }