/*    */ package com.hisun.util;
/*    */ 
/*    */ public final class HiStringManager
/*    */ {
/*    */   private MessageResources resources;
/*    */   private static HiStringManager instance;
/*    */ 
/*    */   public static synchronized HiStringManager getManager()
/*    */   {
/*  8 */     if (instance == null) {
/*  9 */       instance = new HiStringManager();
/*    */     }
/* 11 */     return instance;
/*    */   }
/*    */ 
/*    */   public HiStringManager() {
/* 15 */     MessageResourcesFactory factory = PropertyMessageResourcesFactory.createFactory();
/* 16 */     this.resources = factory.createResources("conf/message");
/*    */   }
/*    */ 
/*    */   public String getString(String key) {
/* 20 */     return this.resources.getMessage(key);
/*    */   }
/*    */ 
/*    */   public String getString(String key, Object[] args) {
/* 24 */     return this.resources.getMessage(key, args);
/*    */   }
/*    */ 
/*    */   public String getString(String key, Object arg) {
/* 28 */     Object[] args = { arg };
/* 29 */     return getString(key, args);
/*    */   }
/*    */ 
/*    */   public String getString(String key, Object arg1, Object arg2) {
/* 33 */     Object[] args = { arg1, arg2 };
/* 34 */     return getString(key, args);
/*    */   }
/*    */ 
/*    */   public String getString(String key, Object arg1, Object arg2, Object arg3) {
/* 38 */     Object[] args = { arg1, arg2, arg3 };
/* 39 */     return getString(key, args);
/*    */   }
/*    */ 
/*    */   public String getString(String key, Object arg1, Object arg2, Object arg3, Object arg4)
/*    */   {
/* 44 */     Object[] args = { arg1, arg2, arg3, arg4 };
/* 45 */     return getString(key, args);
/*    */   }
/*    */ 
/*    */   public String getString(String key, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5)
/*    */   {
/* 50 */     Object[] args = { arg1, arg2, arg3, arg4, arg5 };
/* 51 */     return getString(key, args);
/*    */   }
/*    */ 
/*    */   public String getString(String key, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6)
/*    */   {
/* 56 */     Object[] args = { arg1, arg2, arg3, arg4, arg5, arg6 };
/* 57 */     return getString(key, args);
/*    */   }
/*    */ }