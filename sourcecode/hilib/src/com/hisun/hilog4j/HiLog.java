/*     */ package com.hisun.hilog4j;
/*     */ 
/*     */ import com.hisun.exception.HiAppException;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.exception.HiSysException;
/*     */ import com.hisun.message.HiMessage;
/*     */ import java.util.HashMap;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiLog
/*     */ {
/*  16 */   private static HiArrayBlockingQueue dyncLoggerCache = new HiArrayBlockingQueue(200);
/*     */ 
/*  19 */   private static HashMap loggerCache = new HashMap();
/*     */ 
/*     */   public static Logger getLogger(HiMessage mess)
/*     */   {
/*  29 */     String filename = mess.getHeadItem("FID");
/*  30 */     if (StringUtils.isEmpty(filename)) {
/*  31 */       filename = mess.getRequestId();
/*     */     }
/*  33 */     String level = mess.getHeadItem("STF");
/*  34 */     return getDynTrcLogger(filename, level);
/*     */   }
/*     */ 
/*     */   public static synchronized Logger getDynTrcLogger(String name, String level)
/*     */   {
/*  44 */     if (Logger.toLevel(level).toInt() >= 30000) {
/*  45 */       return Logger.dummyLogger;
/*     */     }
/*     */ 
/*  48 */     Logger log = null;
/*  49 */     if (dyncLoggerCache.containsKey(name)) {
/*  50 */       log = (Logger)dyncLoggerCache.get(name);
/*     */     } else {
/*  52 */       log = new Logger(new HiDynTrcFileName(name), level);
/*  53 */       dyncLoggerCache.put(name, log);
/*     */     }
/*  55 */     Level level1 = Logger.toLevel(level);
/*  56 */     if (level1.toInt() < log.level.toInt()) {
/*  57 */       log.setLevel(level1);
/*     */     }
/*  59 */     return log;
/*     */   }
/*     */ 
/*     */   public static synchronized void clear(HiMessage mess)
/*     */   {
/*     */   }
/*     */ 
/*     */   public static synchronized void close(HiMessage mess)
/*     */   {
/*     */   }
/*     */ 
/*     */   public static Logger getLogger(String filename)
/*     */   {
/*  97 */     return getTrcLogger(filename);
/*     */   }
/*     */ 
/*     */   public static synchronized Logger getErrorLogger(String filename)
/*     */   {
/* 107 */     Logger log = null;
/* 108 */     if (loggerCache.containsKey(filename)) {
/* 109 */       log = (Logger)loggerCache.get(filename);
/*     */     } else {
/* 111 */       log = new Logger(new HiLogFileName(filename));
/* 112 */       loggerCache.put(filename, log);
/*     */     }
/* 114 */     return log;
/*     */   }
/*     */ 
/*     */   public static synchronized Logger getErrorLogger(String filename, String level)
/*     */   {
/* 125 */     Logger log = null;
/* 126 */     if (loggerCache.containsKey(filename)) {
/* 127 */       log = (Logger)loggerCache.get(filename);
/*     */     } else {
/* 129 */       log = new Logger(new HiLogFileName(filename), level);
/* 130 */       loggerCache.put(filename, log);
/*     */     }
/* 132 */     return log;
/*     */   }
/*     */ 
/*     */   public static synchronized Logger getTrcLogger(String filename)
/*     */   {
/* 142 */     Logger log = null;
/* 143 */     if (loggerCache.containsKey(filename)) {
/* 144 */       log = (Logger)loggerCache.get(filename);
/*     */     } else {
/* 146 */       log = new Logger(new HiTrcFileName(filename));
/* 147 */       loggerCache.put(filename, log);
/*     */     }
/* 149 */     return log;
/*     */   }
/*     */ 
/*     */   public static synchronized Logger getTrcLogger(String filename, String level)
/*     */   {
/* 159 */     Logger log = null;
/* 160 */     if (loggerCache.containsKey(filename)) {
/* 161 */       log = (Logger)loggerCache.get(filename);
/*     */     } else {
/* 163 */       log = new Logger(new HiTrcFileName(filename), level);
/* 164 */       loggerCache.put(filename, log);
/*     */     }
/* 166 */     return log;
/*     */   }
/*     */ 
/*     */   public static void logServiceError(HiMessage mess, HiException e)
/*     */   {
/* 179 */     Logger tranlog = getLogger(mess);
/* 180 */     String AppId = mess.getHeadItem("APP");
/* 181 */     if (AppId == null) {
/* 182 */       AppId = "APP";
/*     */     }
/*     */ 
/* 185 */     Logger applog = getErrorLogger(AppId + ".log");
/* 186 */     if (e instanceof HiSysException)
/*     */     {
/* 188 */       tranlog.fatal(e.getAppMessage());
/*     */ 
/* 190 */       if (e.isLog()) {
/* 191 */         applog.fatal(mess.getRequestId() + ":" + e.getAppMessage());
/* 192 */         applog.fatal(mess.getRequestId() + "DUMP MESSAGE:" + mess);
/*     */       } else {
/* 194 */         applog.fatal(mess.getRequestId() + ":" + e.getAppStackMessage());
/*     */ 
/* 197 */         applog.fatal(mess.getRequestId() + "DUMP MESSAGE:" + mess);
/* 198 */         applog.fatal(mess.getRequestId(), e);
/* 199 */         e.setLog(true);
/*     */       }
/*     */     }
/* 202 */     else if (e instanceof HiAppException)
/*     */     {
/* 204 */       tranlog.error(e.getAppMessage());
/*     */ 
/* 206 */       if (e.isLog()) {
/* 207 */         applog.error(mess.getRequestId() + ":" + e.getAppMessage());
/* 208 */         applog.error(mess.getRequestId() + "DUMP MESSAGE:" + mess);
/*     */       } else {
/* 210 */         applog.error(mess.getRequestId() + ":" + e.getAppStackMessage());
/*     */ 
/* 213 */         applog.error(mess.getRequestId() + "DUMP MESSAGE:" + mess);
/* 214 */         applog.error(mess.getRequestId(), e);
/* 215 */         e.setLog(true);
/*     */       }
/*     */     }
/*     */     else {
/* 219 */       tranlog.error(e.getAppMessage());
/*     */ 
/* 221 */       if (e.isLog()) {
/* 222 */         applog.error(mess.getRequestId() + ":" + e.getAppMessage());
/* 223 */         applog.error(mess.getRequestId() + "DUMP MESSAGE:" + mess);
/*     */       } else {
/* 225 */         applog.error(mess.getRequestId() + ":" + e.getAppStackMessage());
/*     */ 
/* 228 */         applog.error(mess.getRequestId() + "DUMP MESSAGE:" + mess);
/* 229 */         applog.error(mess.getRequestId(), e);
/* 230 */         e.setLog(true);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void logServiceWarn(HiMessage mess, HiException e)
/*     */   {
/* 245 */     Logger tranlog = getLogger(mess);
/* 246 */     String AppId = mess.getHeadItem("APP");
/* 247 */     if (AppId == null)
/* 248 */       AppId = "APP";
/* 249 */     Logger applog = getErrorLogger(AppId + ".log");
/*     */ 
/* 251 */     if (e instanceof HiSysException)
/*     */     {
/* 253 */       tranlog.warn(e.getAppMessage());
/*     */ 
/* 255 */       if (e.isLog()) {
/* 256 */         applog.warn(mess.getRequestId() + ":" + e.getAppMessage());
/*     */       } else {
/* 258 */         applog.warn(mess.getRequestId() + ":" + e.getAppStackMessage());
/* 259 */         applog.warn(mess.getRequestId(), e);
/* 260 */         e.setLog(true);
/*     */       }
/*     */     }
/* 263 */     else if (e instanceof HiAppException)
/*     */     {
/* 265 */       tranlog.warn(e.getAppMessage());
/*     */ 
/* 267 */       if (e.isLog()) {
/* 268 */         applog.warn(mess.getRequestId() + ":" + e.getAppMessage());
/*     */       } else {
/* 270 */         applog.warn(mess.getRequestId() + ":" + e.getAppStackMessage());
/* 271 */         applog.warn(mess.getRequestId(), e);
/* 272 */         e.setLog(true);
/*     */       }
/*     */     }
/*     */     else {
/* 276 */       tranlog.warn(e.getAppMessage());
/*     */ 
/* 278 */       if (e.isLog()) {
/* 279 */         applog.warn(mess.getRequestId() + ":" + e.getAppMessage());
/*     */       } else {
/* 281 */         applog.warn(mess.getRequestId() + ":" + e.getAppStackMessage());
/* 282 */         applog.warn(mess.getRequestId(), e);
/* 283 */         e.setLog(true);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void logServerError(String ServerName, HiMessage mess, HiException e)
/*     */   {
/* 303 */     String level = "ERROR"; String requestId = ""; String seperator = "";
/* 304 */     if (mess != null) {
/* 305 */       level = mess.getHeadItem("STF");
/* 306 */       if (StringUtils.isEmpty(level)) {
/* 307 */         level = "ERROR";
/*     */       }
/* 309 */       requestId = mess.getRequestId();
/* 310 */       seperator = ":";
/*     */     }
/* 312 */     if (!(StringUtils.isEmpty(requestId))) {
/* 313 */       Logger log = getLogger(mess);
/* 314 */       log.error(e.getAppMessage());
/*     */     }
/*     */ 
/* 317 */     Logger serverlog = getErrorLogger(ServerName + ".log");
/*     */ 
/* 320 */     if (e instanceof HiSysException)
/*     */     {
/* 322 */       serverlog.fatal(requestId + seperator + e.getAppMessage());
/*     */ 
/* 324 */       if (e.isLog()) {
/* 325 */         serverlog.fatal(requestId + seperator + e.getAppMessage());
/*     */       }
/*     */       else {
/* 328 */         serverlog.fatal(requestId + seperator + e.getAppStackMessage());
/* 329 */         serverlog.fatal(requestId + "DUMP MESSAGE:" + mess);
/* 330 */         serverlog.fatal(requestId, e);
/* 331 */         e.setLog(true);
/*     */       }
/*     */ 
/*     */     }
/* 336 */     else if (e.isLog()) {
/* 337 */       serverlog.error(requestId + seperator + e.getAppMessage());
/*     */     }
/*     */     else {
/* 340 */       serverlog.error(requestId + seperator + e.getAppStackMessage());
/* 341 */       serverlog.fatal(requestId + "DUMP MESSAGE:" + mess);
/* 342 */       serverlog.error(requestId + seperator, e);
/* 343 */       e.setLog(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void logSysError(String msg, Throwable t)
/*     */   {
/* 355 */     Logger log = getErrorLogger("SYS.log");
/* 356 */     if (t instanceof HiException) {
/* 357 */       HiException e = (HiException)t;
/* 358 */       if (e.isLog()) {
/* 359 */         log.error(msg);
/* 360 */         log.error(e.getAppMessage());
/*     */       } else {
/* 362 */         log.error(e.getAppStackMessage());
/* 363 */         log.error(e, e);
/*     */       }
/*     */     } else {
/* 366 */       log.error(msg, t);
/*     */     }
/*     */   }
/*     */ }