/*     */ package com.hisun.hilog4j;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ public class Level extends Priority
/*     */   implements Serializable
/*     */ {
/*     */   public static final int TRACE_INT = 5000;
/*     */   public static final int INFO_INT2 = 20002;
/*     */   public static final int INFO_INT3 = 20003;
/*  51 */   public static final Level OFF = new Level(2147483647, "OFF", 0);
/*     */ 
/*  57 */   public static final Level FATAL = new Level(50000, "FATAL", 0);
/*     */ 
/*  62 */   public static final Level ERROR = new Level(40000, "ERROR", 3);
/*     */ 
/*  67 */   public static final Level WARN = new Level(30000, "WARN", 4);
/*     */ 
/*  73 */   public static final Level INFO = new Level(20000, "INFO", 6);
/*     */ 
/*  75 */   public static final Level INFO2 = new Level(20002, "INFO2", 6);
/*     */ 
/*  77 */   public static final Level INFO3 = new Level(20003, "INFO3", 6);
/*     */ 
/*  83 */   public static final Level DEBUG = new Level(10000, "DEBUG", 7);
/*     */ 
/*  90 */   public static final Level TRACE = new Level(5000, "TRACE", 7);
/*     */ 
/*  96 */   public static final Level ALL = new Level(-2147483648, "ALL", 7);
/*     */   static final long serialVersionUID = 3491141966387921974L;
/*     */ 
/*     */   protected Level(int level, String levelStr, int syslogEquivalent)
/*     */   {
/* 108 */     super(level, levelStr, syslogEquivalent);
/*     */   }
/*     */ 
/*     */   public static Level toLevel(String sArg)
/*     */   {
/* 119 */     return toLevel(sArg, DEBUG);
/*     */   }
/*     */ 
/*     */   public static Level toLevel(int val)
/*     */   {
/* 130 */     return toLevel(val, DEBUG);
/*     */   }
/*     */ 
/*     */   public static Level toLevel(int val, Level defaultLevel)
/*     */   {
/* 140 */     switch (val)
/*     */     {
/*     */     case -2147483648:
/* 141 */       return ALL;
/*     */     case 10000:
/* 142 */       return DEBUG;
/*     */     case 20000:
/* 143 */       return INFO;
/*     */     case 20002:
/* 144 */       return INFO2;
/*     */     case 20003:
/* 145 */       return INFO3;
/*     */     case 30000:
/* 146 */       return WARN;
/*     */     case 40000:
/* 147 */       return ERROR;
/*     */     case 50000:
/* 148 */       return FATAL;
/*     */     case 2147483647:
/* 149 */       return OFF;
/*     */     case 5000:
/* 150 */       return TRACE; }
/* 151 */     return defaultLevel;
/*     */   }
/*     */ 
/*     */   public static Level toLevel(String sArg, Level defaultLevel)
/*     */   {
/* 163 */     if (sArg == null) {
/* 164 */       return defaultLevel;
/*     */     }
/* 166 */     String s = sArg.toUpperCase();
/*     */ 
/* 168 */     if (s.equals("ALL")) return ALL;
/* 169 */     if (s.equals("DEBUG")) return DEBUG;
/* 170 */     if (s.equals("INFO")) return INFO;
/* 171 */     if (s.equals("INFO2")) return INFO2;
/* 172 */     if (s.equals("INFO3")) return INFO3;
/* 173 */     if (s.equals("WARN")) return WARN;
/* 174 */     if (s.equals("ERROR")) return ERROR;
/* 175 */     if (s.equals("FATAL")) return FATAL;
/* 176 */     if (s.equals("OFF")) return OFF;
/* 177 */     if (s.equals("TRACE")) return TRACE;
/* 178 */     return defaultLevel;
/*     */   }
/*     */ }