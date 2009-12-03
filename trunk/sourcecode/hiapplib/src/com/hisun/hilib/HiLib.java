/*     */ package com.hisun.hilib;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import com.hisun.util.HiResource;
/*     */ import java.io.File;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Properties;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiLib
/*     */ {
/*     */   private static final String HIPUBJAR = "hipubatc.jar";
/*     */   private static final String IMP_PATH_DEFINE = "PACKAGE";
/*     */   private static final String METHOD = "execute";
/*     */   private static final String IMP_CLASS_DEFINE = "CLASS";
/*  40 */   private static Logger log = HiLog.getLogger("lib.trc");
/*  41 */   private static Object _lock = new Object();
/*  42 */   private static HashMap entry = new HashMap();
/*     */ 
/*  44 */   private static ArrayList entryIndex = new ArrayList();
/*     */   private static final String ATCLOCALDEF = "conf/atclocal.properties";
/*     */   private static final String LOCAL = "";
/*     */ 
/*     */   public static synchronized void load(String pkgNS, String jarName)
/*     */     throws HiException
/*     */   {
/*  52 */     if (log.isDebugEnabled()) {
/*  53 */       log.debug("load:[" + pkgNS + "]:[" + jarName + "]");
/*     */     }
/*     */ 
/*  56 */     String[] packages = StringUtils.split(jarName, ", ");
/*  57 */     for (int i = 0; i < packages.length; ++i)
/*  58 */       doLoad(pkgNS, packages[i]);
/*     */   }
/*     */ 
/*     */   public static void doLoad(String pkgNS, String jarName)
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/*  77 */       if (log.isDebugEnabled()) {
/*  78 */         log.debug("doLoad:[" + pkgNS + "]:[" + jarName + "]");
/*     */       }
/*  80 */       String propertyName = getPropertyName(jarName);
/*  81 */       if (StringUtils.isEmpty(propertyName)) {
/*  82 */         propertyName = pkgNS;
/*     */       }
/*     */ 
/*  87 */       String resource = "conf" + File.separator + propertyName.toUpperCase() + ".properties";
/*     */ 
/*  89 */       Properties p = new Properties();
/*  90 */       InputStream stream = HiResource.getResourceAsStream(resource);
/*  91 */       if (stream == null) {
/*  92 */         throw new HiException("215010", resource);
/*     */       }
/*     */ 
/*  96 */       p.load(stream);
/*  97 */       stream.close();
/*  98 */       String packageName = p.getProperty("PACKAGE");
/*  99 */       String cls = p.getProperty("CLASS");
/* 100 */       p.clear();
/* 101 */       p = null;
/*     */ 
/* 103 */       if (packageName == null) {
/* 104 */         throw new HiException("215001", jarName, "PACKAGE");
/*     */       }
/*     */ 
/* 107 */       packageName = packageName.trim();
/* 108 */       for (int i = 0; i < entryIndex.size(); ++i) {
/* 109 */         HiPackageNSItem item = (HiPackageNSItem)entryIndex.get(i);
/* 110 */         if ((StringUtils.equalsIgnoreCase(item.packageNS, pkgNS)) && (StringUtils.equalsIgnoreCase(item.pkgName, packageName)))
/*     */         {
/* 113 */           return;
/*     */         }
/*     */       }
/*     */ 
/* 117 */       if (cls == null) {
/* 118 */         loadPackage(pkgNS, packageName);
/* 119 */         return;
/*     */       }
/*     */ 
/* 122 */       if (log.isDebugEnabled()) {
/* 123 */         log.debug(packageName + ":" + cls);
/*     */       }
/* 125 */       String[] names = StringUtils.split(cls, " , ");
/*     */ 
/* 128 */       for (int i = 0; i < names.length; ++i)
/*     */       {
/* 130 */         String fullClsName = packageName.trim() + "." + names[i].trim();
/* 131 */         if (log.isDebugEnabled()) {
/* 132 */           log.debug(names[i]);
/*     */         }
/*     */ 
/* 135 */         Class clazz = Class.forName(fullClsName);
/* 136 */         Method[] ms = clazz.getMethods();
/* 137 */         Object obj = clazz.newInstance();
/*     */ 
/* 140 */         for (int j = 0; j < ms.length; ++j)
/*     */         {
/* 142 */           if (ms[j].getDeclaringClass() == Object.class) {
/*     */             continue;
/*     */           }
/*     */ 
/* 146 */           String key = pkgNS + ":" + ms[j].getName();
/*     */ 
/* 148 */           if (log.isDebugEnabled()) {
/* 149 */             log.debug("key1: " + key);
/*     */           }
/* 151 */           entry.put(key.toUpperCase(), new HiFunctionItem(obj, ms[j]));
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 156 */       entryIndex.add(new HiPackageNSItem(pkgNS, packageName));
/*     */     }
/*     */     catch (Exception e) {
/* 159 */       if (e instanceof HiException) {
/* 160 */         throw ((HiException)e);
/*     */       }
/* 162 */       throw new HiException("215003", jarName, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static synchronized boolean loadComponent(String name)
/*     */     throws HiException
/*     */   {
/* 175 */     if (log.isDebugEnabled()) {
/* 176 */       log.debug("loadLocalComponent:[" + name + "]");
/*     */     }
/*     */ 
/* 180 */     String[] values = StringUtils.split(name, ":");
/* 181 */     if (values.length != 2) {
/* 182 */       throw new HiException("215004", name);
/*     */     }
/* 184 */     if (!(procPkgNS(values[0]))) {
/* 185 */       if (log.isDebugEnabled()) {
/* 186 */         log.debug("loadLocalComponent:[" + name + "], not find");
/*     */       }
/* 188 */       return false;
/*     */     }
/* 190 */     if (!(doLoadComponent(name, values[0], values[1]))) {
/* 191 */       throw new HiException("215004", name);
/*     */     }
/*     */ 
/* 194 */     return true;
/*     */   }
/*     */ 
/*     */   public static synchronized boolean loadComponent()
/*     */     throws HiException
/*     */   {
/* 203 */     if (log.isDebugEnabled()) {
/* 204 */       log.debug("loadComponent");
/*     */     }
/*     */ 
/* 207 */     String tmp = HiICSProperty.getProperty("pubatc.jar");
/* 208 */     if (StringUtils.isEmpty(tmp))
/*     */     {
/* 210 */       if (log.isDebugEnabled()) {
/* 211 */         log.debug("loadComponent,no startup: can't find item[pubatc.jar] at sys.properties");
/*     */       }
/* 213 */       return false;
/*     */     }
/*     */ 
/* 216 */     load("PUB", tmp);
/*     */ 
/* 218 */     return true;
/*     */   }
/*     */ 
/*     */   public static boolean contains(String name)
/*     */     throws HiException
/*     */   {
/* 228 */     if (log.isDebugEnabled()) {
/* 229 */       log.debug("contains:[" + name + "]");
/*     */     }
/*     */ 
/* 232 */     return entry.containsKey(name);
/*     */   }
/*     */ 
/*     */   private static boolean procPkgNS(String pkgNS)
/*     */     throws HiException
/*     */   {
/* 242 */     if (log.isDebugEnabled()) {
/* 243 */       log.debug("procPkgNS:[" + pkgNS + "]");
/*     */     }
/*     */ 
/* 246 */     boolean founded = false;
/* 247 */     for (int i = 0; i < entryIndex.size(); ++i) {
/* 248 */       HiPackageNSItem item = (HiPackageNSItem)entryIndex.get(i);
/* 249 */       if (StringUtils.equalsIgnoreCase(item.packageNS, pkgNS)) {
/* 250 */         founded = true;
/* 251 */         break;
/*     */       }
/*     */     }
/*     */ 
/* 255 */     if (founded) {
/* 256 */       return true;
/*     */     }
/*     */ 
/* 259 */     if (!("PUB".equalsIgnoreCase(pkgNS))) {
/* 260 */       return false;
/*     */     }
/*     */ 
/* 263 */     String tmp = HiICSProperty.getProperty("pubatc.jar");
/* 264 */     if (StringUtils.isEmpty(tmp)) {
/* 265 */       tmp = "hipubatc.jar";
/*     */     }
/* 267 */     load("PUB", tmp);
/* 268 */     return true;
/*     */   }
/*     */ 
/*     */   private static boolean doLoadComponent(String name, String pkgNS, String clsName)
/*     */     throws HiException
/*     */   {
/*     */     HiPackageNSItem item;
/* 273 */     if (log.isDebugEnabled()) {
/* 274 */       log.debug("doLoadComponent:[" + name + "]:[" + pkgNS + "]:[" + clsName + "]");
/*     */     }
/*     */ 
/* 278 */     if (entry.containsKey(name.toUpperCase())) {
/* 279 */       return true;
/*     */     }
/*     */ 
/* 282 */     boolean founded = false;
/*     */ 
/* 284 */     for (int i = 0; i < entryIndex.size(); ++i) {
/* 285 */       item = (HiPackageNSItem)entryIndex.get(i);
/* 286 */       if (StringUtils.equalsIgnoreCase(item.packageNS, pkgNS)) {
/* 287 */         founded = true;
/* 288 */         break;
/*     */       }
/*     */     }
/* 291 */     if (!(founded)) {
/* 292 */       throw new HiException("215004", name);
/*     */     }
/* 294 */     founded = false;
/*     */ 
/* 296 */     for (i = 0; i < entryIndex.size(); ++i) {
/* 297 */       item = (HiPackageNSItem)entryIndex.get(i);
/* 298 */       if (!(StringUtils.equalsIgnoreCase(item.packageNS, pkgNS))) {
/*     */         continue;
/*     */       }
/* 301 */       founded = true;
/* 302 */       String fullClsName = item.pkgName + "." + clsName;
/*     */       try
/*     */       {
/* 305 */         Class clazz = Class.forName(fullClsName);
/* 306 */         Object obj = clazz.newInstance();
/* 307 */         Method m = clazz.getMethod("execute", new Class[] { HiATLParam.class, HiMessageContext.class });
/*     */ 
/* 309 */         entry.put(name.toUpperCase(), new HiFunctionItem(obj, m));
/* 310 */         label404: if (log.isDebugEnabled())
/* 311 */           log.debug("found component:[" + name + "]");
/*     */       }
/*     */       catch (ClassNotFoundException e) {
/* 314 */         log.error(e, e);
/*     */ 
/* 323 */         break label404:
/*     */       }
/*     */       catch (InstantiationException e)
/*     */       {
/* 316 */         throw HiException.makeException(clsName, e);
/*     */       } catch (IllegalAccessException e) {
/* 318 */         throw HiException.makeException(clsName, e);
/*     */       } catch (SecurityException e) {
/* 320 */         throw HiException.makeException(clsName, e);
/*     */       } catch (NoSuchMethodException e) {
/* 322 */         throw HiException.makeException(clsName, e);
/*     */       }
/*     */     }
/* 325 */     return founded;
/*     */   }
/*     */ 
/*     */   public static Object invoke(String name, HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 339 */     if (log.isDebugEnabled()) {
/* 340 */       log.debug("invoke:[" + name + "]");
/*     */     }
/*     */ 
/* 344 */     String[] values = StringUtils.split(name, ":");
/* 345 */     if (values.length != 2) {
/* 346 */       throw new HiException("215004", name);
/*     */     }
/* 348 */     String packageName = getPackage(values[0], ctx);
/* 349 */     if (packageName == null) {
/* 350 */       throw new HiException("215004", name);
/*     */     }
/*     */ 
/* 353 */     return invoke(packageName, name, args, ctx);
/*     */   }
/*     */ 
/*     */   public static Object invoke(String packageName, String name, HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 369 */     if (log.isDebugEnabled()) {
/* 370 */       log.debug("invoke:[" + packageName + "]:[" + name + "]");
/*     */     }
/*     */ 
/* 373 */     HiFunctionItem function = getFunction(packageName, name);
/* 374 */     if (function == null) {
/* 375 */       loadComponent(name);
/*     */     }
/*     */ 
/* 378 */     function = getFunction(packageName, name);
/* 379 */     if (function == null) {
/* 380 */       throw new HiException("215006", name);
/*     */     }
/* 382 */     if (log.isDebugEnabled()) {
/* 383 */       log.debug(function);
/*     */     }
/* 385 */     Object[] params = new Object[2];
/* 386 */     params[0] = args;
/* 387 */     params[1] = ctx;
/*     */     try
/*     */     {
/* 391 */       return function.method.invoke(function.object, params);
/*     */     } catch (InvocationTargetException e) {
/* 393 */       Throwable t = e.getTargetException();
/* 394 */       if (t == null) {
/* 395 */         throw new HiException("215007", new String[] { name, e.getMessage() }, e);
/*     */       }
/* 397 */       if (t instanceof HiException) {
/* 398 */         throw ((HiException)t);
/*     */       }
/* 400 */       throw new HiException("215007", new String[] { name, t.getMessage() }, t);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 404 */       if (e instanceof HiException) {
/* 405 */         throw ((HiException)e);
/*     */       }
/* 407 */       throw new HiException("215007", new String[] { name, e.getMessage() }, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String getPackage(String alias, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 423 */     if (log.isDebugEnabled()) {
/* 424 */       log.debug("getPackage:[" + alias + "]");
/*     */     }
/*     */ 
/* 430 */     if (StringUtils.equalsIgnoreCase(alias, "PUB")) {
/* 431 */       String tmp = HiICSProperty.getProperty("pubatc.jar");
/* 432 */       if (StringUtils.isEmpty(tmp)) {
/* 433 */         tmp = "hipubatc.jar";
/*     */       }
/* 435 */       synchronized (_lock) {
/* 436 */         if (!(containtsEntryIndex("PUB"))) {
/* 437 */           load("PUB", tmp);
/*     */         }
/*     */       }
/* 440 */       return tmp;
/*     */     }
/* 442 */     return ctx.getStrProp("LIBDECLARE." + alias);
/*     */   }
/*     */ 
/*     */   private static HiFunctionItem getFunction(String packageName, String name)
/*     */   {
/* 459 */     return ((HiFunctionItem)entry.get(name.toUpperCase()));
/*     */   }
/*     */ 
/*     */   private static String getPropertyName(String packageName)
/*     */   {
/* 464 */     int idx1 = packageName.indexOf(47);
/* 465 */     if (idx1 == -1)
/* 466 */       idx1 = 0;
/* 467 */     int idx2 = packageName.indexOf(46);
/* 468 */     if (idx2 == -1) {
/* 469 */       idx2 = 0;
/*     */     }
/* 471 */     return packageName.substring(idx1, idx2);
/*     */   }
/*     */ 
/*     */   private static void loadPackage(String pkgNS, String packageDef) {
/* 475 */     if (log.isDebugEnabled()) {
/* 476 */       log.debug("loadPackage:[" + pkgNS + "]:[" + packageDef + "]");
/*     */     }
/* 478 */     String[] names = StringUtils.split(packageDef, " , ");
/* 479 */     for (int i = 0; i < names.length; ++i)
/* 480 */       entryIndex.add(new HiPackageNSItem(pkgNS, names[i]));
/*     */   }
/*     */ 
/*     */   private static boolean containtsEntryIndex(String pkgNS)
/*     */   {
/* 485 */     boolean founded = false;
/* 486 */     for (int i = 0; i < entryIndex.size(); ++i) {
/* 487 */       HiPackageNSItem NSItem = (HiPackageNSItem)entryIndex.get(i);
/* 488 */       if (StringUtils.equalsIgnoreCase(NSItem.packageNS, pkgNS)) {
/* 489 */         founded = true;
/* 490 */         break;
/*     */       }
/*     */     }
/* 493 */     return founded;
/*     */   }
/*     */ }