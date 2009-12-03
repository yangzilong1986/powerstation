/*     */ package com.hisun.util;
/*     */ 
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class PropertyMessageResources extends MessageResources
/*     */ {
/* 136 */   protected static final Logger log = HiLog.getLogger("SYS.trc");
/*     */ 
/* 141 */   protected long lastModified = -1L;
/*     */ 
/* 145 */   protected long lastLoaded = -1L;
/*     */   private int checkInterval;
/* 154 */   protected HashMap messages = new HashMap();
/*     */ 
/*     */   public PropertyMessageResources(MessageResourcesFactory factory, String config)
/*     */   {
/* 168 */     super(factory, config);
/* 169 */     log.debug("Initializing, config='" + config + "'");
/*     */   }
/*     */ 
/*     */   public PropertyMessageResources(MessageResourcesFactory factory, String config, boolean returnNull)
/*     */   {
/* 185 */     super(factory, config, returnNull);
/* 186 */     loadLocale(localeKey(this.defaultLocale));
/* 187 */     log.debug("Initializing, config='" + config + "', returnNull=" + returnNull);
/*     */   }
/*     */ 
/*     */   public void setMode(String mode)
/*     */   {
/* 203 */     String value = (mode == null) ? null : mode.trim();
/*     */   }
/*     */ 
/*     */   public String getMessage(Locale locale, String key)
/*     */   {
/* 223 */     if (log.isDebugEnabled()) {
/* 224 */       log.debug("getMessage(" + locale + "," + key + ")");
/*     */     }
/*     */ 
/* 227 */     String localeKey = localeKey(locale);
/* 228 */     String originalKey = messageKey(localeKey, key);
/* 229 */     String message = null;
/*     */ 
/* 232 */     message = findMessage(locale, key, originalKey);
/* 233 */     if (message != null) {
/* 234 */       return message;
/*     */     }
/*     */ 
/* 238 */     if (this.returnNull) {
/* 239 */       return null;
/*     */     }
/* 241 */     return "???" + messageKey(locale, key) + "???";
/*     */   }
/*     */ 
/*     */   public synchronized void reload(Locale locale)
/*     */   {
/* 247 */     clear();
/* 248 */     loadLocale(localeKey(locale));
/*     */   }
/*     */ 
/*     */   protected synchronized void loadLocale(String localeKey)
/*     */   {
/* 264 */     if (log.isDebugEnabled()) {
/* 265 */       log.debug("loadLocale(" + localeKey + ")");
/*     */     }
/*     */ 
/* 269 */     String name = this.config.replace('.', '/');
/*     */ 
/* 271 */     if (localeKey.length() > 0) {
/* 272 */       name = name + "_" + localeKey;
/*     */     }
/*     */ 
/* 275 */     name = name + ".properties";
/*     */ 
/* 277 */     InputStream is = null;
/* 278 */     Properties props = new Properties();
/*     */ 
/* 281 */     if (log.isDebugEnabled()) {
/* 282 */       log.debug("  Loading resource '" + name + "'");
/*     */     }
/*     */ 
/* 292 */     if (log.isInfoEnabled()) {
/* 293 */       log.info("load:{" + HiICSProperty.getWorkDir() + File.separator + name + "}");
/* 294 */       log.info(this.messages);
/* 295 */       log.info(this.formats);
/*     */     }
/* 297 */     File f = new File(HiICSProperty.getWorkDir() + File.separator + name);
/*     */     try {
/* 299 */       is = new FileInputStream(f);
/*     */     } catch (FileNotFoundException e1) {
/* 301 */       e1.printStackTrace();
/*     */     }
/* 303 */     if (is != null) {
/*     */       try {
/* 305 */         props.load(is);
/*     */       } catch (IOException e) {
/* 307 */         log.error("loadLocale()", e);
/*     */       } finally {
/*     */         try {
/* 310 */           is.close();
/*     */         } catch (IOException e) {
/* 312 */           log.error("loadLocale()", e);
/*     */         }
/*     */       }
/* 315 */       if (log.isDebugEnabled()) {
/* 316 */         log.debug("  Loading resource completed");
/*     */       }
/*     */     }
/* 319 */     else if (log.isWarnEnabled()) {
/* 320 */       log.warn("  Resource " + name + " Not Found.");
/*     */     }
/*     */ 
/* 325 */     if (props.size() < 1) {
/* 326 */       return;
/*     */     }
/*     */ 
/* 329 */     synchronized (this.messages) {
/* 330 */       Iterator names = props.keySet().iterator();
/*     */ 
/* 332 */       while (names.hasNext()) {
/* 333 */         String key = (String)names.next();
/*     */ 
/* 335 */         if (log.isDebugEnabled()) {
/* 336 */           log.debug("  Saving message key '" + messageKey(localeKey, key));
/*     */         }
/*     */ 
/* 340 */         this.messages.put(messageKey(localeKey, key), props.getProperty(key));
/*     */       }
/*     */     }
/*     */ 
/* 344 */     this.lastLoaded = System.currentTimeMillis();
/* 345 */     this.lastModified = f.lastModified();
/*     */   }
/*     */ 
/*     */   private String findMessage(Locale locale, String key, String originalKey)
/*     */   {
/* 368 */     String localeKey = localeKey(locale);
/* 369 */     String message = null;
/* 370 */     message = findMessage(localeKey, key, originalKey);
/* 371 */     return message;
/*     */   }
/*     */ 
/*     */   private String findMessage(String localeKey, String key, String originalKey)
/*     */   {
/* 393 */     String messageKey = messageKey(localeKey, key);
/*     */ 
/* 396 */     boolean addIt = !(messageKey.equals(originalKey));
/*     */ 
/* 398 */     synchronized (this.messages) {
/* 399 */       String message = (String)this.messages.get(messageKey);
/* 400 */       if ((message != null) && 
/* 401 */         (addIt)) {
/* 402 */         this.messages.put(originalKey, message);
/*     */       }
/*     */ 
/* 405 */       return message;
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isModified(Locale locale)
/*     */   {
/* 411 */     String localeKey = localeKey(locale);
/*     */ 
/* 414 */     return isModified(localeKey);
/*     */   }
/*     */ 
/*     */   public void clear() {
/* 418 */     this.formats.clear();
/* 419 */     this.messages.clear();
/*     */   }
/*     */ 
/*     */   protected boolean isModified(String localeKey) {
/* 423 */     if (System.currentTimeMillis() - this.lastLoaded < this.checkInterval * 1000) {
/* 424 */       return false;
/*     */     }
/* 426 */     String name = this.config.replace('.', '/');
/* 427 */     if (localeKey.length() > 0) {
/* 428 */       name = name + "_" + localeKey;
/*     */     }
/*     */ 
/* 431 */     name = name + ".properties";
/*     */ 
/* 443 */     File f = new File(HiICSProperty.getWorkDir() + File.separator + name);
/* 444 */     if ((this.lastModified != -1L) && (f.lastModified() != -1L) && (this.lastModified != f.lastModified())) {
/* 445 */       this.lastLoaded = System.currentTimeMillis();
/* 446 */       this.lastModified = f.lastModified();
/* 447 */       log.info("file:[" + name + "] modified");
/* 448 */       return true;
/*     */     }
/* 450 */     return false;
/*     */   }
/*     */ 
/*     */   public int getCheckInterval()
/*     */   {
/* 458 */     return this.checkInterval;
/*     */   }
/*     */ 
/*     */   public void setCheckInterval(int checkInterval)
/*     */   {
/* 465 */     this.checkInterval = checkInterval;
/*     */   }
/*     */ }