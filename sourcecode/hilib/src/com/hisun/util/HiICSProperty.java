/*     */ package com.hisun.util;
/*     */ 
/*     */ import com.hisun.message.HiContext;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Calendar;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Properties;
/*     */ import org.apache.commons.lang.BooleanUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiICSProperty
/*     */ {
/*     */   public static boolean containsProperty(String name)
/*     */   {
/*  33 */     HiContext ctx = HiContext.getRootContext();
/*  34 */     return ctx.containsProperty("@SYS." + name);
/*     */   }
/*     */ 
/*     */   public static void setProperty(String name, String value)
/*     */   {
/*  43 */     HiContext ctx = HiContext.getRootContext();
/*  44 */     ctx.setProperty("@SYS", name, StringUtils.trim(value));
/*     */   }
/*     */ 
/*     */   public static String getProperty(String name) {
/*  48 */     HiContext ctx = HiContext.getRootContext();
/*  49 */     return ctx.getStrProp("@SYS", name);
/*     */   }
/*     */ 
/*     */   public static String getProperty(String name, String defaultValue) {
/*  53 */     HiContext ctx = HiContext.getRootContext();
/*  54 */     String value = ctx.getStrProp("@SYS", name);
/*  55 */     if (value == null)
/*  56 */       return defaultValue;
/*  57 */     return value;
/*     */   }
/*     */ 
/*     */   public static int getInt(String name) {
/*  61 */     return NumberUtils.toInt(getProperty(name));
/*     */   }
/*     */ 
/*     */   public static int getInt(String name, int defaultValue) {
/*  65 */     return NumberUtils.toInt(getProperty(name), defaultValue);
/*     */   }
/*     */ 
/*     */   public static boolean getBoolean(String name) {
/*  69 */     return BooleanUtils.toBoolean(getProperty(name));
/*     */   }
/*     */ 
/*     */   public static boolean getBoolean(String name, boolean defaultValue) {
/*  73 */     String value = getProperty(name);
/*  74 */     if (value == null) {
/*  75 */       return defaultValue;
/*     */     }
/*  77 */     return BooleanUtils.toBoolean(getProperty(name));
/*     */   }
/*     */ 
/*     */   public static void reload() {
/*  81 */     loadProperties();
/*  82 */     initialize();
/*     */   }
/*     */ 
/*     */   private static void loadProperties() {
/*  86 */     InputStream is = null;
/*  87 */     Throwable error = null;
/*  88 */     Properties properties = new Properties();
/*     */     try {
/*  90 */       is = HiResource.getResourceAsStream("conf/env.properties");
/*  91 */       if (is != null)
/*  92 */         properties.load(is);
/*  93 */       is = null;
/*  94 */       Enumeration enumeration = properties.propertyNames();
/*     */ 
/*  96 */       while (enumeration.hasMoreElements()) {
/*  97 */         String name = (String)enumeration.nextElement();
/*  98 */         String value = properties.getProperty(name);
/*  99 */         System.setProperty(name, value);
/*     */       }
/* 101 */       properties.clear();
/*     */     } catch (FileNotFoundException e) {
/*     */     }
/*     */     catch (IOException e) {
/*     */     }
/* 106 */     String icsHome = System.getProperty("HWORKDIR");
/* 107 */     if (icsHome == null) {
/* 108 */       icsHome = System.getenv("HWORKDIR");
/*     */     }
/* 110 */     if (icsHome == null) {
/* 111 */       icsHome = "./";
/*     */     }
/* 113 */     setProperty("HWORKDIR", icsHome);
/*     */ 
/* 115 */     if (is == null)
/*     */     {
/*     */       try
/*     */       {
/* 119 */         is = HiResource.getResourceAsStream("conf/sys.properties");
/*     */       }
/*     */       catch (Throwable t)
/*     */       {
/*     */       }
/*     */     }
/*     */ 
/* 126 */     if (is == null) {
/*     */       try {
/* 128 */         is = HiICSProperty.class.getResourceAsStream("sys.properties");
/*     */       }
/*     */       catch (Throwable t)
/*     */       {
/*     */       }
/*     */     }
/*     */ 
/* 135 */     if (is != null) {
/*     */       try {
/* 137 */         properties.load(is);
/* 138 */         is.close();
/*     */       } catch (Throwable t) {
/* 140 */         error = t;
/*     */       }
/*     */     }
/*     */ 
/* 144 */     if ((is != null) && (error != null));
/* 149 */     Enumeration enumeration = properties.propertyNames();
/*     */ 
/* 151 */     HiSymbolExpander symbolExpander = new HiSymbolExpander(HiContext.getRootContext(), "@SYS");
/*     */ 
/* 153 */     while (enumeration.hasMoreElements()) {
/* 154 */       String name = (String)enumeration.nextElement();
/* 155 */       String value = properties.getProperty(name);
/* 156 */       if (value != null) {
/* 157 */         setProperty(name, symbolExpander.expandSymbols(value));
/*     */       }
/*     */     }
/* 160 */     properties.clear();
/* 161 */     properties = null;
/*     */   }
/*     */ 
/*     */   static void initialize()
/*     */   {
/* 166 */     String icsHome = getProperty("HWORKDIR");
/* 167 */     if (System.getProperty("dat.dir") == null) {
/* 168 */       if (icsHome.endsWith(File.separator))
/* 169 */         setProperty("dat.dir", icsHome + "dat");
/*     */       else {
/* 171 */         setProperty("dat.dir", icsHome + File.separator + "dat");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 176 */     if (System.getProperty("bak.dir") == null) {
/* 177 */       if (icsHome.endsWith(File.separator))
/* 178 */         setProperty("bak.dir", icsHome + "bak");
/*     */       else {
/* 180 */         setProperty("bak.dir", icsHome + File.separator + "bak");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 185 */     if (System.getProperty("etc.dir") == null) {
/* 186 */       if (icsHome.endsWith(File.separator))
/* 187 */         setProperty("etc.dir", icsHome + "etc");
/*     */       else {
/* 189 */         setProperty("etc.dir", icsHome + File.separator + "etc");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 194 */     if (System.getProperty("conf.dir") == null) {
/* 195 */       if (icsHome.endsWith(File.separator))
/* 196 */         setProperty("conf.dir", icsHome + "cfg");
/*     */       else {
/* 198 */         setProperty("conf.dir", icsHome + File.separator + "cfg");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 203 */     if (System.getProperty("sql.dir") == null) {
/* 204 */       if (icsHome.endsWith(File.separator))
/* 205 */         setProperty("sql.dir", icsHome + "sql");
/*     */       else {
/* 207 */         setProperty("sql.dir", icsHome + File.separator + "sql");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 212 */     if (System.getProperty("trc.dir") == null) {
/* 213 */       if (icsHome.endsWith(File.separator))
/* 214 */         setProperty("trc.dir", icsHome + "trc");
/*     */       else {
/* 216 */         setProperty("trc.dir", icsHome + File.separator + "trc");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 221 */     if (System.getProperty("log.dir") == null) {
/* 222 */       if (icsHome.endsWith(File.separator))
/* 223 */         setProperty("log.dir", icsHome + "log");
/*     */       else {
/* 225 */         setProperty("log.dir", icsHome + File.separator + "log");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 230 */     if (System.getProperty("tmp.dir") == null) {
/* 231 */       if (icsHome.endsWith(File.separator))
/* 232 */         setProperty("tmp.dir", icsHome + "tmp");
/*     */       else {
/* 234 */         setProperty("tmp.dir", icsHome + File.separator + "tmp");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 239 */     if (System.getProperty("bin.dir") == null) {
/* 240 */       if (icsHome.endsWith(File.separator))
/* 241 */         setProperty("bin.dir", icsHome + "bin");
/*     */       else {
/* 243 */         setProperty("bin.dir", icsHome + File.separator + "bin");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 248 */     if (System.getProperty("private.lib.dir") == null) {
/* 249 */       if (icsHome.endsWith(File.separator))
/* 250 */         setProperty("private.lib.dir", icsHome + "lib");
/*     */       else {
/* 252 */         setProperty("private.lib.dir", icsHome + File.separator + "lib");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 257 */     if (System.getProperty("common.lib.dir") == null)
/* 258 */       if (icsHome.endsWith(File.separator))
/* 259 */         setProperty("common.lib.dir", icsHome + "lib");
/*     */       else
/* 261 */         setProperty("common.lib.dir", icsHome + File.separator + "lib");
/*     */   }
/*     */ 
/*     */   public static String getWorkDir()
/*     */   {
/* 274 */     return getProperty("HWORKDIR");
/*     */   }
/*     */ 
/*     */   public static String getAppDir()
/*     */   {
/* 282 */     return getProperty("HWORKDIR") + File.separator + "app";
/*     */   }
/*     */ 
/*     */   public static String getDatDir()
/*     */   {
/* 290 */     return getProperty("dat.dir");
/*     */   }
/*     */ 
/*     */   public static String getDatDir(String channel)
/*     */   {
/* 298 */     return getProperty("dat.dir") + File.separator + channel.toLowerCase();
/*     */   }
/*     */ 
/*     */   public static String getDatDir(String channel, String userDefine)
/*     */   {
/* 307 */     return getDatDir(channel) + File.separator + userDefine.toLowerCase();
/*     */   }
/*     */ 
/*     */   public static String getDatDir(String channel, String subChannel, String userDefine)
/*     */   {
/* 317 */     return getDatDir(channel) + File.separator + subChannel.toLowerCase() + File.separator + userDefine.toLowerCase();
/*     */   }
/*     */ 
/*     */   public static String getBakDir()
/*     */   {
/* 327 */     return getProperty("bak.dir");
/*     */   }
/*     */ 
/*     */   public static String getEtcDir()
/*     */   {
/* 335 */     return getProperty("etc.dir");
/*     */   }
/*     */ 
/*     */   public static String getConfDir()
/*     */   {
/* 343 */     return getProperty("conf.dir");
/*     */   }
/*     */ 
/*     */   public static String getSqlDir()
/*     */   {
/* 351 */     return getProperty("sql.dir");
/*     */   }
/*     */ 
/*     */   public static String getTrcDir()
/*     */   {
/* 359 */     StringBuffer path = new StringBuffer();
/* 360 */     path.append(getProperty("trc.dir"));
/* 361 */     path.append(File.separator);
/* 362 */     Calendar cal = Calendar.getInstance();
/* 363 */     int d = cal.get(5);
/* 364 */     if (d >= 10) {
/* 365 */       path.append(d);
/*     */     } else {
/* 367 */       path.append("0");
/* 368 */       path.append(d);
/*     */     }
/* 370 */     path.append(File.separator);
/* 371 */     return path.toString();
/*     */   }
/*     */ 
/*     */   public static String getLogDir()
/*     */   {
/* 379 */     StringBuffer path = new StringBuffer();
/* 380 */     path.append(getProperty("log.dir"));
/* 381 */     path.append(File.separator);
/* 382 */     Calendar cal = Calendar.getInstance();
/* 383 */     int d = cal.get(5);
/* 384 */     if (d >= 10) {
/* 385 */       path.append(d);
/*     */     } else {
/* 387 */       path.append("0");
/* 388 */       path.append(d);
/*     */     }
/* 390 */     path.append(File.separator);
/* 391 */     return path.toString();
/*     */   }
/*     */ 
/*     */   public static String getTmpDir()
/*     */   {
/* 399 */     return getProperty("tmp.dir");
/*     */   }
/*     */ 
/*     */   public static String getBinDir()
/*     */   {
/* 407 */     return getProperty("bin.dir");
/*     */   }
/*     */ 
/*     */   public static boolean isJUnitEnv()
/*     */   {
/* 417 */     return (!(StringUtils.equals(System.getProperty("_ICS_ENV"), "_ICS_JUNIT_ENV")));
/*     */   }
/*     */ 
/*     */   public static boolean isPrdEnv()
/*     */   {
/* 429 */     return (!(StringUtils.equals(getProperty("sys.runmode"), "product")));
/*     */   }
/*     */ 
/*     */   public static boolean isDevEnv()
/*     */   {
/* 442 */     return (!(StringUtils.equals(System.getProperty("_ICS_ENV"), "_ICS_DEV_ENV")));
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  24 */     loadProperties();
/*  25 */     initialize();
/*     */   }
/*     */ }