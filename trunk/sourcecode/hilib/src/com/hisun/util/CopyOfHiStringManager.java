/*     */ package com.hisun.util;
/*     */ 
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Locale;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
/*     */ 
/*     */ public final class CopyOfHiStringManager
/*     */ {
/*     */   private ResourceBundle bundle;
/*     */   private Locale locale;
/* 281 */   private static Hashtable managers = new Hashtable();
/*     */   private final String bundleName;
/*     */ 
/*     */   private CopyOfHiStringManager(String packageName)
/*     */   {
/*  79 */     this(packageName, Locale.getDefault());
/*     */   }
/*     */ 
/*     */   private CopyOfHiStringManager(String packageName, Locale loc)
/*     */   {
/*  84 */     this.bundleName = packageName + ".message";
/*     */ 
/*  86 */     this.bundle = ReloadablePropertyResourceBundle.getResourceBundle(this.bundleName, loc);
/*     */ 
/*  93 */     this.locale = loc;
/*     */   }
/*     */ 
/*     */   public String getString(String key)
/*     */   {
/* 114 */     if (key == null) {
/* 115 */       String msg = "key may not have a null value";
/*     */ 
/* 117 */       throw new IllegalArgumentException(msg);
/*     */     }
/*     */ 
/* 121 */     String str = null;
/*     */     try
/*     */     {
/* 124 */       str = this.bundle.getString(key);
/*     */     }
/*     */     catch (MissingResourceException mre)
/*     */     {
/* 137 */       str = null;
/*     */     }
/*     */ 
/* 140 */     return str;
/*     */   }
/*     */ 
/*     */   public String getString(String key, Object[] args)
/*     */   {
/* 152 */     String iString = null;
/* 153 */     String value = getString(key);
/*     */     try
/*     */     {
/* 161 */       if (args == null) {
/* 162 */         args = new Object[1];
/*     */       }
/*     */ 
/* 165 */       Object[] nonNullArgs = args;
/* 166 */       for (int i = 0; i < args.length; ++i) {
/* 167 */         if (args[i] == null) {
/* 168 */           if (nonNullArgs == args) {
/* 169 */             nonNullArgs = (Object[])(Object[])args.clone();
/*     */           }
/* 171 */           nonNullArgs[i] = "null";
/*     */         }
/*     */       }
/* 174 */       if (value == null) {
/* 175 */         value = key;
/*     */       }
/* 177 */       MessageFormat mf = new MessageFormat(value);
/* 178 */       mf.setLocale(this.locale);
/* 179 */       iString = mf.format(nonNullArgs, new StringBuffer(), null).toString();
/*     */ 
/* 181 */       return iString;
/*     */     } catch (IllegalArgumentException iae) {
/* 183 */       StringBuffer buf = new StringBuffer();
/* 184 */       buf.append(value);
/* 185 */       for (int i = 0; i < args.length; ++i) {
/* 186 */         buf.append(" arg[" + i + "]=" + args[i]);
/*     */       }
/* 188 */       iString = buf.toString();
/*     */     }
/* 190 */     return iString;
/*     */   }
/*     */ 
/*     */   public String getString(String key, Object arg)
/*     */   {
/* 203 */     Object[] args = { arg };
/* 204 */     return getString(key, args);
/*     */   }
/*     */ 
/*     */   public String getString(String key, Object arg1, Object arg2)
/*     */   {
/* 217 */     Object[] args = { arg1, arg2 };
/* 218 */     return getString(key, args);
/*     */   }
/*     */ 
/*     */   public String getString(String key, Object arg1, Object arg2, Object arg3)
/*     */   {
/* 232 */     Object[] args = { arg1, arg2, arg3 };
/* 233 */     return getString(key, args);
/*     */   }
/*     */ 
/*     */   public String getString(String key, Object arg1, Object arg2, Object arg3, Object arg4)
/*     */   {
/* 249 */     Object[] args = { arg1, arg2, arg3, arg4 };
/* 250 */     return getString(key, args);
/*     */   }
/*     */ 
/*     */   public String getString(String key, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5)
/*     */   {
/* 267 */     Object[] args = { arg1, arg2, arg3, arg4, arg5 };
/* 268 */     return getString(key, args);
/*     */   }
/*     */ 
/*     */   public String getString(String key, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6)
/*     */   {
/* 273 */     Object[] args = { arg1, arg2, arg3, arg4, arg5, arg6 };
/* 274 */     return getString(key, args);
/*     */   }
/*     */ 
/*     */   private static synchronized CopyOfHiStringManager getManager(String packageName)
/*     */   {
/* 294 */     CopyOfHiStringManager mgr = (CopyOfHiStringManager)managers.get(packageName);
/* 295 */     if (mgr == null) {
/* 296 */       mgr = new CopyOfHiStringManager(packageName);
/* 297 */       managers.put(packageName, mgr);
/*     */     }
/*     */ 
/* 301 */     return mgr;
/*     */   }
/*     */ 
/*     */   public static synchronized CopyOfHiStringManager getManager()
/*     */   {
/* 308 */     return getManager("conf");
/*     */   }
/*     */ 
/*     */   public static synchronized CopyOfHiStringManager getManager(String packageName, Locale loc)
/*     */   {
/* 336 */     CopyOfHiStringManager mgr = (CopyOfHiStringManager)managers.get(packageName + "_" + loc.toString());
/*     */ 
/* 338 */     if (mgr == null) {
/* 339 */       mgr = new CopyOfHiStringManager(packageName, loc);
/* 340 */       managers.put(packageName + "_" + loc.toString(), mgr);
/*     */     }
/* 342 */     return mgr;
/*     */   }
/*     */ 
/*     */   public Locale getLocale()
/*     */   {
/* 347 */     return this.locale;
/*     */   }
/*     */ 
/*     */   public void checkForUpdates()
/*     */   {
/* 353 */     if (this.bundle == null)
/* 354 */       return;
/* 355 */     this.bundle = ReloadablePropertyResourceBundle.getResourceBundle(this.bundleName, this.locale);
/*     */   }
/*     */ }