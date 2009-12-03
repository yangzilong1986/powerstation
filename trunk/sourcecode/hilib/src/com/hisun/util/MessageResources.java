/*     */ package com.hisun.util;
/*     */ 
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import java.io.Serializable;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public abstract class MessageResources
/*     */   implements Serializable
/*     */ {
/*  56 */   protected static Logger log = HiLog.getLogger("SYS.trc");
/*     */ 
/*  64 */   protected static MessageResourcesFactory defaultFactory = null;
/*     */   protected String config;
/*     */   protected Locale defaultLocale;
/*     */   protected MessageResourcesFactory factory;
/*     */   protected HashMap formats;
/*     */   protected boolean returnNull;
/*     */   private boolean escape;
/*     */ 
/*     */   public MessageResources(MessageResourcesFactory factory, String config)
/*     */   {
/* 108 */     this(factory, config, false);
/*     */   }
/*     */ 
/*     */   public MessageResources(MessageResourcesFactory factory, String config, boolean returnNull)
/*     */   {
/*  69 */     this.config = null;
/*     */ 
/*  74 */     this.defaultLocale = Locale.getDefault();
/*     */ 
/*  79 */     this.factory = null;
/*     */ 
/*  85 */     this.formats = new HashMap();
/*     */ 
/*  91 */     this.returnNull = false;
/*     */ 
/*  97 */     this.escape = true;
/*     */ 
/* 123 */     this.factory = factory;
/* 124 */     this.config = config;
/* 125 */     this.returnNull = returnNull;
/*     */   }
/*     */ 
/*     */   public String getConfig()
/*     */   {
/* 134 */     return this.config;
/*     */   }
/*     */ 
/*     */   public MessageResourcesFactory getFactory()
/*     */   {
/* 143 */     return this.factory;
/*     */   }
/*     */ 
/*     */   public boolean getReturnNull()
/*     */   {
/* 153 */     return this.returnNull;
/*     */   }
/*     */ 
/*     */   public void setReturnNull(boolean returnNull)
/*     */   {
/* 164 */     this.returnNull = returnNull;
/*     */   }
/*     */ 
/*     */   public boolean isEscape()
/*     */   {
/* 174 */     return this.escape;
/*     */   }
/*     */ 
/*     */   public void setEscape(boolean escape)
/*     */   {
/* 184 */     this.escape = escape;
/*     */   }
/*     */ 
/*     */   public String getMessage(String key)
/*     */   {
/* 195 */     return getMessage((Locale)null, key, null);
/*     */   }
/*     */ 
/*     */   public String getMessage(String key, Object[] args)
/*     */   {
/* 206 */     return getMessage((Locale)null, key, args);
/*     */   }
/*     */ 
/*     */   public String getMessage(String key, Object arg0)
/*     */   {
/* 217 */     return getMessage((Locale)null, key, arg0);
/*     */   }
/*     */ 
/*     */   public String getMessage(String key, Object arg0, Object arg1)
/*     */   {
/* 229 */     return getMessage((Locale)null, key, arg0, arg1);
/*     */   }
/*     */ 
/*     */   public String getMessage(String key, Object arg0, Object arg1, Object arg2)
/*     */   {
/* 242 */     return getMessage((Locale)null, key, arg0, arg1, arg2);
/*     */   }
/*     */ 
/*     */   public String getMessage(String key, Object arg0, Object arg1, Object arg2, Object arg3)
/*     */   {
/* 257 */     return getMessage((Locale)null, key, arg0, arg1, arg2, arg3);
/*     */   }
/*     */ 
/*     */   public String getMessage(String key, Object arg0, Object arg1, Object arg2, Object arg3, Object arg4)
/*     */   {
/* 273 */     return getMessage((Locale)null, key, new Object[] { arg0, arg1, arg2, arg3, arg4 });
/*     */   }
/*     */ 
/*     */   public String getMessage(String key, Object arg0, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5)
/*     */   {
/* 289 */     return getMessage((Locale)null, key, new Object[] { arg0, arg1, arg2, arg3, arg4, arg5 });
/*     */   }
/*     */ 
/*     */   public abstract String getMessage(Locale paramLocale, String paramString);
/*     */ 
/*     */   public abstract boolean isModified(Locale paramLocale);
/*     */ 
/*     */   public abstract void reload(Locale paramLocale);
/*     */ 
/*     */   public String getMessage(Locale locale, String key, Object[] args)
/*     */   {
/* 320 */     if (locale == null) {
/* 321 */       locale = this.defaultLocale;
/*     */     }
/* 323 */     MessageFormat format = null;
/* 324 */     String formatKey = messageKey(locale, key);
/*     */ 
/* 326 */     synchronized (this.formats) {
/* 327 */       if (isModified(locale)) {
/* 328 */         reload(locale);
/*     */       }
/* 330 */       format = (MessageFormat)this.formats.get(formatKey);
/*     */ 
/* 332 */       if (format == null) {
/* 333 */         String formatString = getMessage(locale, key);
/*     */ 
/* 335 */         if (formatString == null) {
/* 336 */           return "???" + formatKey + "???";
/*     */         }
/*     */ 
/* 339 */         format = new MessageFormat(escape(formatString));
/* 340 */         format.setLocale(locale);
/* 341 */         this.formats.put(formatKey, format);
/*     */       }
/*     */     }
/*     */ 
/* 345 */     return format.format(args);
/*     */   }
/*     */ 
/*     */   public String getMessage(Locale locale, String key, Object arg0)
/*     */   {
/* 359 */     return getMessage(locale, key, new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */   public String getMessage(Locale locale, String key, Object arg0, Object arg1)
/*     */   {
/* 374 */     return getMessage(locale, key, new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */   public String getMessage(Locale locale, String key, Object arg0, Object arg1, Object arg2)
/*     */   {
/* 391 */     return getMessage(locale, key, new Object[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */   public String getMessage(Locale locale, String key, Object arg0, Object arg1, Object arg2, Object arg3)
/*     */   {
/* 409 */     return getMessage(locale, key, new Object[] { arg0, arg1, arg2, arg3 });
/*     */   }
/*     */ 
/*     */   public boolean isPresent(String key)
/*     */   {
/* 420 */     return isPresent(null, key);
/*     */   }
/*     */ 
/*     */   public boolean isPresent(Locale locale, String key)
/*     */   {
/* 432 */     String message = getMessage(locale, key);
/*     */ 
/* 434 */     if (message == null) {
/* 435 */       return false;
/*     */     }
/* 437 */     return ((message.startsWith("???")) && (message.endsWith("???")));
/*     */   }
/*     */ 
/*     */   protected String escape(String string)
/*     */   {
/* 452 */     if (!(isEscape())) {
/* 453 */       return string;
/*     */     }
/*     */ 
/* 456 */     if ((string == null) || (string.indexOf(39) < 0)) {
/* 457 */       return string;
/*     */     }
/*     */ 
/* 460 */     int n = string.length();
/* 461 */     StringBuffer sb = new StringBuffer(n);
/*     */ 
/* 463 */     for (int i = 0; i < n; ++i) {
/* 464 */       char ch = string.charAt(i);
/*     */ 
/* 466 */       if (ch == '\'') {
/* 467 */         sb.append('\'');
/*     */       }
/*     */ 
/* 470 */       sb.append(ch);
/*     */     }
/*     */ 
/* 473 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   protected String localeKey(Locale locale)
/*     */   {
/* 484 */     return ((locale == null) ? "" : locale.toString());
/*     */   }
/*     */ 
/*     */   protected String messageKey(Locale locale, String key)
/*     */   {
/* 495 */     return localeKey(locale) + "." + key;
/*     */   }
/*     */ 
/*     */   protected String messageKey(String localeKey, String key)
/*     */   {
/* 507 */     return localeKey + "." + key;
/*     */   }
/*     */ 
/*     */   public static synchronized MessageResources getMessageResources(String config)
/*     */   {
/* 518 */     if (defaultFactory == null) {
/* 519 */       defaultFactory = MessageResourcesFactory.createFactory();
/*     */     }
/*     */ 
/* 522 */     return defaultFactory.createResources(config);
/*     */   }
/*     */ 
/*     */   public void log(String message)
/*     */   {
/* 531 */     log.debug(message);
/*     */   }
/*     */ 
/*     */   public void log(String message, Throwable throwable)
/*     */   {
/* 542 */     log.debug(message, throwable);
/*     */   }
/*     */ }