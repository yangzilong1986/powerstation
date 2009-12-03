/*     */ package com.hisun.hilog4j;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiConvHelper;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.SystemUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class Logger
/*     */   implements HiCloseable
/*     */ {
/*  19 */   public static int DEFAULT_LIMIT_SIZE = 20971520;
/*  20 */   public static int DEFAULT_LIMIT_LINES = 20;
/*  21 */   public static int DEFAULT_QUEUE_MAX_SIZE = 1000;
/*     */   public static final String SYS_LOG = "SYS.log";
/*  23 */   public static Logger dummyLogger = new Logger(new HiLogFileName("DUMMY.log"), Level.ERROR);
/*     */ 
/*  25 */   private static SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss,SSS");
/*     */ 
/*  27 */   protected int limitsLines = DEFAULT_LIMIT_LINES;
/*  28 */   protected int limitsSize = DEFAULT_LIMIT_SIZE;
/*  29 */   protected Level level = Level.DEBUG;
/*  30 */   protected IFileName fileName = null;
/*  31 */   protected boolean hasOfHead = true;
/*     */   protected ILogCache logCache;
/*     */   protected String msgId;
/*     */ 
/*     */   public static Logger getLogger(String name)
/*     */   {
/*  36 */     return new Logger(new HiTrcFileName(name), Level.DEBUG);
/*     */   }
/*     */ 
/*     */   public Logger(String name) {
/*  40 */     construct(new HiTrcFileName(name));
/*     */   }
/*     */ 
/*     */   public Logger(String name, String level) {
/*  44 */     construct(new HiTrcFileName(name), toLevel(level));
/*     */   }
/*     */ 
/*     */   public Logger(String name, Level level) {
/*  48 */     construct(new HiTrcFileName(name), level);
/*     */   }
/*     */ 
/*     */   public Logger(IFileName name) {
/*  52 */     construct(name);
/*     */   }
/*     */ 
/*     */   public Logger(IFileName name, String level) {
/*  56 */     construct(name, toLevel(level));
/*     */   }
/*     */ 
/*     */   public Logger(IFileName name, Level level)
/*     */   {
/*  61 */     construct(name, level);
/*     */   }
/*     */ 
/*     */   protected void construct(IFileName name) {
/*  65 */     String tmp1 = HiICSProperty.getProperty("log.level", "DEBUG");
/*  66 */     construct(name, toLevel(tmp1));
/*     */   }
/*     */ 
/*     */   protected void construct(IFileName name, Level level) {
/*  70 */     String tmp2 = HiICSProperty.getProperty("log.limits_lines", "20");
/*  71 */     this.limitsLines = NumberUtils.toInt(tmp2);
/*  72 */     tmp2 = HiICSProperty.getProperty("log.limits_size", "20");
/*  73 */     this.limitsSize = (NumberUtils.toInt(tmp2) * 1024 * 1024);
/*  74 */     if (this.limitsSize <= 0) {
/*  75 */       this.limitsSize = DEFAULT_LIMIT_SIZE;
/*     */     }
/*  77 */     this.level = level;
/*  78 */     this.fileName = name;
/*  79 */     this.logCache = new HiDirectLogCache(this.limitsSize);
/*     */   }
/*     */ 
/*     */   public void setLevel(org.apache.log4j.Level level)
/*     */   {
/*  84 */     this.level = Level.toLevel(level.toInt());
/*     */   }
/*     */ 
/*     */   public void setLevel(Level level) {
/*  88 */     this.level = level;
/*     */   }
/*     */ 
/*     */   public Level getLevel() {
/*  92 */     return this.level;
/*     */   }
/*     */ 
/*     */   public static Logger getLogger(Class clazz) {
/*  96 */     String name = clazz.getName();
/*  97 */     int idx1 = name.lastIndexOf(46);
/*  98 */     if (idx1 != -1) {
/*  99 */       name = name.substring(idx1 + 1);
/*     */     }
/* 101 */     return getLogger(name);
/*     */   }
/*     */ 
/*     */   public boolean isDebugEnabled()
/*     */   {
/* 106 */     return (this.level.toInt() > 10000);
/*     */   }
/*     */ 
/*     */   public void debug(Object msg)
/*     */   {
/* 112 */     debug(new Object[] { msg });
/*     */   }
/*     */ 
/*     */   public void debug(Object msg1, Object msg2) {
/* 116 */     debug(new Object[] { msg1, msg2 });
/*     */   }
/*     */ 
/*     */   public void debug(Object msg1, Object msg2, Object msg3) {
/* 120 */     debug(new Object[] { msg1, msg2, msg3 });
/*     */   }
/*     */ 
/*     */   public void debug(Object msg1, Object msg2, Object msg3, Object msg4) {
/* 124 */     debug(new Object[] { msg1, msg2, msg3, msg4 });
/*     */   }
/*     */ 
/*     */   public void debug(Object msg, Throwable t) {
/* 128 */     debug(new Object[] { msg }, t);
/*     */   }
/*     */ 
/*     */   public void debug(Object msg1, Object msg2, Throwable t) {
/* 132 */     debug(new Object[] { msg1, msg2 }, t);
/*     */   }
/*     */ 
/*     */   public void debug(Object msg1, Object msg2, Object msg3, Throwable t) {
/* 136 */     debug(new Object[] { msg1, msg2, msg3 }, t);
/*     */   }
/*     */ 
/*     */   public void debug(Object msg1, Object msg2, Object msg3, Object msg4, Throwable t)
/*     */   {
/* 141 */     debug(new Object[] { msg1, msg2, msg3, msg4 }, t);
/*     */   }
/*     */ 
/*     */   public void debug(Object[] msgs) {
/* 145 */     if (isDebugEnabled())
/* 146 */       log(Level.DEBUG, msgs);
/*     */   }
/*     */ 
/*     */   public void debug(Object[] msgs, Throwable t)
/*     */   {
/* 151 */     if (isDebugEnabled())
/* 152 */       log(Level.DEBUG, msgs, t);
/*     */   }
/*     */ 
/*     */   public boolean isInfoEnabled()
/*     */   {
/* 158 */     return (this.level.toInt() > 20000);
/*     */   }
/*     */ 
/*     */   public void info(Object msg)
/*     */   {
/* 164 */     info(new Object[] { msg });
/*     */   }
/*     */ 
/*     */   public void info(Object msg1, Object msg2) {
/* 168 */     info(new Object[] { msg1, msg2 });
/*     */   }
/*     */ 
/*     */   public void info(Object msg1, Object msg2, Object msg3) {
/* 172 */     info(new Object[] { msg1, msg2, msg3 });
/*     */   }
/*     */ 
/*     */   public void info(Object msg1, Object msg2, Object msg3, Object msg4) {
/* 176 */     info(new Object[] { msg1, msg2, msg3, msg4 });
/*     */   }
/*     */ 
/*     */   public void info(Object msg, Throwable t) {
/* 180 */     info(new Object[] { msg }, t);
/*     */   }
/*     */ 
/*     */   public void info(Object msg1, Object msg2, Throwable t) {
/* 184 */     info(new Object[] { msg1, msg2 }, t);
/*     */   }
/*     */ 
/*     */   public void info(Object msg1, Object msg2, Object msg3, Throwable t) {
/* 188 */     info(new Object[] { msg1, msg2, msg3 }, t);
/*     */   }
/*     */ 
/*     */   public void info(Object msg1, Object msg2, Object msg3, Object msg4, Throwable t)
/*     */   {
/* 193 */     info(new Object[] { msg1, msg2, msg3, msg4 }, t);
/*     */   }
/*     */ 
/*     */   public void info(Object[] msgs, Throwable t) {
/* 197 */     if (isInfoEnabled())
/* 198 */       log(Level.INFO, msgs, t);
/*     */   }
/*     */ 
/*     */   public void info(Object[] msgs)
/*     */   {
/* 203 */     if (isInfoEnabled())
/* 204 */       log(Level.INFO, msgs);
/*     */   }
/*     */ 
/*     */   public boolean isInfo2Enabled()
/*     */   {
/* 210 */     return (this.level.toInt() > 20002);
/*     */   }
/*     */ 
/*     */   public void info2(Object msg)
/*     */   {
/* 216 */     info2(new Object[] { msg });
/*     */   }
/*     */ 
/*     */   public void info2(Object msg1, Object msg2) {
/* 220 */     info2(new Object[] { msg1, msg2 });
/*     */   }
/*     */ 
/*     */   public void info2(Object msg1, Object msg2, Object msg3) {
/* 224 */     info2(new Object[] { msg1, msg2, msg3 });
/*     */   }
/*     */ 
/*     */   public void info2(Object msg1, Object msg2, Object msg3, Object msg4) {
/* 228 */     info2(new Object[] { msg1, msg2, msg3, msg4 });
/*     */   }
/*     */ 
/*     */   public void info2(Object msg, Throwable t) {
/* 232 */     info2(new Object[] { msg }, t);
/*     */   }
/*     */ 
/*     */   public void info2(Object msg1, Object msg2, Throwable t) {
/* 236 */     info2(new Object[] { msg1, msg2 }, t);
/*     */   }
/*     */ 
/*     */   public void info2(Object msg1, Object msg2, Object msg3, Throwable t) {
/* 240 */     info2(new Object[] { msg1, msg2, msg3 }, t);
/*     */   }
/*     */ 
/*     */   public void info2(Object msg1, Object msg2, Object msg3, Object msg4, Throwable t)
/*     */   {
/* 245 */     info2(new Object[] { msg1, msg2, msg3, msg4 }, t);
/*     */   }
/*     */ 
/*     */   public void info2(Object[] msgs, Throwable t) {
/* 249 */     if (isInfo2Enabled())
/* 250 */       log(Level.INFO2, msgs, t);
/*     */   }
/*     */ 
/*     */   public void info2(Object[] msgs)
/*     */   {
/* 255 */     if (isInfo2Enabled())
/* 256 */       log(Level.INFO2, msgs);
/*     */   }
/*     */ 
/*     */   public boolean isInfo3Enabled()
/*     */   {
/* 262 */     return (this.level.toInt() > 20003);
/*     */   }
/*     */ 
/*     */   public void info3(Object msg)
/*     */   {
/* 268 */     info3(new Object[] { msg });
/*     */   }
/*     */ 
/*     */   public void info3(Object msg1, Object msg2) {
/* 272 */     info3(new Object[] { msg1, msg2 });
/*     */   }
/*     */ 
/*     */   public void info3(Object msg1, Object msg2, Object msg3) {
/* 276 */     info3(new Object[] { msg1, msg2, msg3 });
/*     */   }
/*     */ 
/*     */   public void info3(Object msg1, Object msg2, Object msg3, Object msg4) {
/* 280 */     info3(new Object[] { msg1, msg2, msg3, msg4 });
/*     */   }
/*     */ 
/*     */   public void info3(Object msg, Throwable t) {
/* 284 */     info3(new Object[] { msg }, t);
/*     */   }
/*     */ 
/*     */   public void info3(Object msg1, Object msg2, Throwable t) {
/* 288 */     info3(new Object[] { msg1, msg2 }, t);
/*     */   }
/*     */ 
/*     */   public void info3(Object msg1, Object msg2, Object msg3, Throwable t) {
/* 292 */     info3(new Object[] { msg1, msg2, msg3 }, t);
/*     */   }
/*     */ 
/*     */   public void info3(Object msg1, Object msg2, Object msg3, Object msg4, Throwable t)
/*     */   {
/* 297 */     info3(new Object[] { msg1, msg2, msg3, msg4 }, t);
/*     */   }
/*     */ 
/*     */   public void info3(Object[] msgs, Throwable t) {
/* 301 */     if (isInfo3Enabled())
/* 302 */       log(Level.INFO3, msgs, t);
/*     */   }
/*     */ 
/*     */   public void info3(Object[] msgs)
/*     */   {
/* 307 */     if (isInfo3Enabled())
/* 308 */       log(Level.INFO3, msgs);
/*     */   }
/*     */ 
/*     */   public boolean isWarnEnabled()
/*     */   {
/* 314 */     return (this.level.toInt() > 30000);
/*     */   }
/*     */ 
/*     */   public void warn(Object msg)
/*     */   {
/* 320 */     warn(new Object[] { msg });
/*     */   }
/*     */ 
/*     */   public void warn(Object msg1, Object msg2) {
/* 324 */     warn(new Object[] { msg1, msg2 });
/*     */   }
/*     */ 
/*     */   public void warn(Object msg1, Object msg2, Object msg3) {
/* 328 */     warn(new Object[] { msg1, msg2, msg3 });
/*     */   }
/*     */ 
/*     */   public void warn(Object msg1, Object msg2, Object msg3, Object msg4) {
/* 332 */     warn(new Object[] { msg1, msg2, msg3, msg4 });
/*     */   }
/*     */ 
/*     */   public void warn(Object msg, Throwable t) {
/* 336 */     warn(new Object[] { msg }, t);
/*     */   }
/*     */ 
/*     */   public void warn(Object msg1, Object msg2, Throwable t) {
/* 340 */     warn(new Object[] { msg1, msg2 }, t);
/*     */   }
/*     */ 
/*     */   public void warn(Object msg1, Object msg2, Object msg3, Throwable t) {
/* 344 */     warn(new Object[] { msg1, msg2, msg3 }, t);
/*     */   }
/*     */ 
/*     */   public void warn(Object msg1, Object msg2, Object msg3, Object msg4, Throwable t)
/*     */   {
/* 349 */     warn(new Object[] { msg1, msg2, msg3, msg4 }, t);
/*     */   }
/*     */ 
/*     */   public void warn(Object[] msgs) {
/* 353 */     if (isWarnEnabled())
/* 354 */       log(Level.WARN, msgs);
/*     */   }
/*     */ 
/*     */   public void warn(Object[] msgs, Throwable t)
/*     */   {
/* 359 */     if (isWarnEnabled())
/* 360 */       log(Level.WARN, msgs, t);
/*     */   }
/*     */ 
/*     */   public boolean isErrorEnabled()
/*     */   {
/* 366 */     return (this.level.toInt() > 40000);
/*     */   }
/*     */ 
/*     */   public void error(Object msg)
/*     */   {
/* 372 */     error(new Object[] { msg });
/*     */   }
/*     */ 
/*     */   public void error(Object msg1, Object msg2) {
/* 376 */     error(new Object[] { msg1, msg2 });
/*     */   }
/*     */ 
/*     */   public void error(Object msg1, Object msg2, Object msg3) {
/* 380 */     error(new Object[] { msg1, msg2, msg3 });
/*     */   }
/*     */ 
/*     */   public void error(Object msg1, Object msg2, Object msg3, Object msg4) {
/* 384 */     error(new Object[] { msg1, msg2, msg3, msg4 });
/*     */   }
/*     */ 
/*     */   public void error(Object msg, Throwable t) {
/* 388 */     error(new Object[] { msg }, t);
/*     */   }
/*     */ 
/*     */   public void error(Object msg1, Object msg2, Throwable t) {
/* 392 */     error(new Object[] { msg1, msg2 }, t);
/*     */   }
/*     */ 
/*     */   public void error(Object msg1, Object msg2, Object msg3, Throwable t) {
/* 396 */     error(new Object[] { msg1, msg2, msg3 }, t);
/*     */   }
/*     */ 
/*     */   public void error(Object msg1, Object msg2, Object msg3, Object msg4, Throwable t)
/*     */   {
/* 401 */     error(new Object[] { msg1, msg2, msg3, msg4 }, t);
/*     */   }
/*     */ 
/*     */   public void error(Object[] msgs) {
/* 405 */     log(Level.ERROR, msgs);
/*     */   }
/*     */ 
/*     */   public void error(Object[] msgs, Throwable t) {
/* 409 */     log(Level.ERROR, msgs, t);
/*     */   }
/*     */ 
/*     */   public boolean isFatalEnabled()
/*     */   {
/* 414 */     return (this.level.toInt() > 50000);
/*     */   }
/*     */ 
/*     */   public void fatal(Object msg)
/*     */   {
/* 420 */     fatal(new Object[] { msg });
/*     */   }
/*     */ 
/*     */   public void fatal(Object msg1, Object msg2) {
/* 424 */     fatal(new Object[] { msg1, msg2 });
/*     */   }
/*     */ 
/*     */   public void fatal(Object msg1, Object msg2, Object msg3) {
/* 428 */     fatal(new Object[] { msg1, msg2, msg3 });
/*     */   }
/*     */ 
/*     */   public void fatal(Object msg1, Object msg2, Object msg3, Object msg4) {
/* 432 */     fatal(new Object[] { msg1, msg2, msg3, msg4 });
/*     */   }
/*     */ 
/*     */   public void fatal(Object msg, Throwable t) {
/* 436 */     fatal(new Object[] { msg }, t);
/*     */   }
/*     */ 
/*     */   public void fatal(Object msg1, Object msg2, Throwable t) {
/* 440 */     fatal(new Object[] { msg1, msg2 }, t);
/*     */   }
/*     */ 
/*     */   public void fatal(Object msg1, Object msg2, Object msg3, Throwable t) {
/* 444 */     fatal(new Object[] { msg1, msg2, msg3 }, t);
/*     */   }
/*     */ 
/*     */   public void fatal(Object msg1, Object msg2, Object msg3, Object msg4, Throwable t)
/*     */   {
/* 449 */     fatal(new Object[] { msg1, msg2, msg3, msg4 }, t);
/*     */   }
/*     */ 
/*     */   public void fatal(Object[] msgs) {
/* 453 */     log(Level.FATAL, msgs);
/* 454 */     log(new HiLogFileName("SYS.log"), Level.FATAL, msgs);
/*     */   }
/*     */ 
/*     */   public void fatal(Object[] msgs, Throwable t) {
/* 458 */     log(Level.FATAL, msgs, t);
/* 459 */     log(new HiLogFileName("SYS.log"), Level.FATAL, msgs, t);
/*     */   }
/*     */ 
/*     */   protected void log(Level level, Object[] msgs, Throwable t) {
/* 463 */     log(this.fileName, level, msgs, t);
/*     */   }
/*     */ 
/*     */   protected void log(IFileName name, Level level, Object[] msgs, Throwable t)
/*     */   {
/*     */     Throwable t1;
/* 467 */     StringBuilder buffer = new StringBuilder(81);
/* 468 */     println(name, buffer, level, msgs);
/* 469 */     buffer.append(t.toString());
/* 470 */     buffer.append(SystemUtils.LINE_SEPARATOR);
/*     */ 
/* 472 */     StackTraceElement[] elements = t.getStackTrace();
/* 473 */     for (int i = 0; (elements != null) && (i < elements.length); ++i) {
/* 474 */       if (this.msgId != null) {
/* 475 */         buffer.append(this.msgId);
/* 476 */         buffer.append(' ');
/*     */       }
/* 478 */       buffer.append("        at ");
/* 479 */       buffer.append(elements[i].toString());
/* 480 */       buffer.append(SystemUtils.LINE_SEPARATOR);
/* 481 */       if ((i == this.limitsLines) && (i < elements.length - 1)) {
/* 482 */         if (this.msgId != null) {
/* 483 */           buffer.append(this.msgId);
/* 484 */           buffer.append(' ');
/*     */         }
/* 486 */         buffer.append("        Truncated...........");
/* 487 */         buffer.append(SystemUtils.LINE_SEPARATOR);
/* 488 */         break;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 493 */     if (t instanceof HiException)
/* 494 */       t1 = ((HiException)t).getNestedException();
/*     */     else {
/* 496 */       t1 = t.getCause();
/*     */     }
/* 498 */     while (t1 != null) {
/* 499 */       buffer.append("Nested Exception:");
/* 500 */       buffer.append(SystemUtils.LINE_SEPARATOR);
/* 501 */       buffer.append(t1.toString());
/* 502 */       buffer.append(SystemUtils.LINE_SEPARATOR);
/*     */ 
/* 504 */       elements = t1.getStackTrace();
/* 505 */       for (int i = 0; (elements != null) && (i < elements.length); ++i) {
/* 506 */         if (this.msgId != null) {
/* 507 */           buffer.append(this.msgId);
/* 508 */           buffer.append(' ');
/*     */         }
/* 510 */         buffer.append("           ");
/* 511 */         buffer.append(elements[i].toString());
/* 512 */         buffer.append(SystemUtils.LINE_SEPARATOR);
/* 513 */         if ((i == this.limitsLines) && (i < elements.length - 1)) {
/* 514 */           if (this.msgId != null) {
/* 515 */             buffer.append(this.msgId);
/* 516 */             buffer.append(' ');
/*     */           }
/* 518 */           buffer.append("        Truncated..........");
/* 519 */           buffer.append(SystemUtils.LINE_SEPARATOR);
/* 520 */           break;
/*     */         }
/*     */       }
/* 523 */       if (t1 instanceof HiException) {
/* 524 */         t1 = ((HiException)t1).getNestedException();
/*     */       }
/* 526 */       t1 = t1.getCause();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 531 */       this.logCache.put(new HiLogInfo(name, buffer));
/*     */     } catch (IOException e) {
/* 533 */       System.out.println(name.get());
/* 534 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void log(IFileName name, Level level, Object[] msgs)
/*     */   {
/* 541 */     StringBuilder buffer = new StringBuilder(81);
/* 542 */     println(name, buffer, level, msgs);
/*     */     try {
/* 544 */       this.logCache.put(new HiLogInfo(name, buffer));
/*     */     } catch (IOException e) {
/* 546 */       System.out.println(name.get());
/* 547 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void log(Level level, Object[] msgs) {
/* 552 */     log(this.fileName, level, msgs);
/*     */   }
/*     */ 
/*     */   protected void println(IFileName name, StringBuilder buffer, Level level, Object[] msgs)
/*     */   {
/* 557 */     if (this.msgId != null) {
/* 558 */       String tmpMsgId = "";
/* 559 */       HiContext ctx = HiContext.getCurrentContext();
/* 560 */       if (ctx instanceof HiMessageContext) {
/* 561 */         tmpMsgId = ((HiMessageContext)ctx).getCurrentMsg().getRequestId();
/* 562 */         buffer.append(tmpMsgId);
/* 563 */         buffer.append(' ');
/*     */       }
/*     */     }
/*     */ 
/* 567 */     if (this.hasOfHead) {
/* 568 */       buffer.append(df.format(new Date()));
/* 569 */       buffer.append(' ');
/* 570 */       buffer.append(level.toString());
/* 571 */       buffer.append(' ');
/* 572 */       buffer.append('-');
/* 573 */       buffer.append(' ');
/*     */     }
/*     */ 
/* 576 */     for (int i = 0; i < msgs.length; ++i) {
/* 577 */       if (msgs[i] instanceof byte[])
/* 578 */         buffer.append(HiConvHelper.binToAscStr((byte[])(byte[])msgs[i]));
/*     */       else {
/* 580 */         buffer.append(String.valueOf(msgs[i]));
/*     */       }
/* 582 */       if (i != msgs.length - 1) {
/* 583 */         buffer.append(':');
/*     */       }
/*     */     }
/*     */ 
/* 587 */     if (name.getLineLength() != -1) {
/* 588 */       i = buffer.toString().getBytes().length;
/* 589 */       for (; i < name.getLineLength(); ++i) {
/* 590 */         buffer.append(' ');
/*     */       }
/*     */     }
/* 593 */     buffer.append(SystemUtils.LINE_SEPARATOR);
/*     */   }
/*     */ 
/*     */   public static Level toLevel(String strLev) {
/* 597 */     Level level = null;
/* 598 */     if ((StringUtils.equals(strLev, "0")) || (StringUtils.equalsIgnoreCase(strLev, "no")))
/*     */     {
/* 600 */       level = Level.ERROR;
/* 601 */     } else if ((StringUtils.equals(strLev, "1")) || (StringUtils.equalsIgnoreCase(strLev, "yes")))
/*     */     {
/* 603 */       level = Level.INFO;
/* 604 */     } else if (StringUtils.equals(strLev, "2"))
/* 605 */       level = Level.INFO2;
/* 606 */     else if (StringUtils.equals(strLev, "3"))
/* 607 */       level = Level.INFO3;
/* 608 */     else if (StringUtils.equals(strLev, "4"))
/* 609 */       level = Level.WARN;
/* 610 */     else if (StringUtils.equals(strLev, "5"))
/* 611 */       level = Level.ERROR;
/*     */     else {
/* 613 */       level = Level.toLevel(strLev, Level.ERROR);
/*     */     }
/* 615 */     return level;
/*     */   }
/*     */ 
/*     */   public void setHasOfHead(boolean hasOfHead) {
/* 619 */     this.hasOfHead = hasOfHead;
/*     */   }
/*     */ 
/*     */   public void clear() {
/* 623 */     this.logCache.clear();
/*     */   }
/*     */ 
/*     */   public void flush() {
/*     */     try {
/* 628 */       this.logCache.flush();
/*     */     } catch (IOException e) {
/* 630 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void close() {
/*     */     try {
/* 636 */       this.logCache.close();
/*     */     } catch (IOException e) {
/* 638 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setFixSizeable(boolean fixsizeable) {
/* 643 */     this.fileName.setFixedSizeable(fixsizeable);
/*     */   }
/*     */ 
/*     */   public void setLineLength(int lineLength) {
/* 647 */     this.fileName.setLineLength(lineLength);
/*     */   }
/*     */ 
/*     */   public String getMsgId()
/*     */   {
/* 654 */     return this.msgId;
/*     */   }
/*     */ 
/*     */   public void setMsgId(String msgId)
/*     */   {
/* 661 */     this.msgId = msgId;
/*     */   }
/*     */ }