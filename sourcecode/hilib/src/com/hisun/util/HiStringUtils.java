/*     */ package com.hisun.util;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiStringUtils
/*     */ {
/*     */   public static String format(String format, String arg0)
/*     */   {
/*  19 */     return format(format, new String[] { arg0 });
/*     */   }
/*     */ 
/*     */   public static String format(String format, String arg0, String arg1)
/*     */   {
/*  25 */     return format(format, new String[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */   public static String format(String format, String arg0, String arg1, String arg2)
/*     */   {
/*  32 */     return format(format, new String[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */   public static String format(String format, String arg0, String arg1, String arg2, String arg3)
/*     */   {
/*  39 */     return format(format, new String[] { arg0, arg1, arg2, arg3 });
/*     */   }
/*     */ 
/*     */   public static String format(String format, String arg0, String arg1, String arg2, String arg3, String arg4)
/*     */   {
/*  46 */     return format(format, new String[] { arg0, arg1, arg2, arg3, arg4 });
/*     */   }
/*     */ 
/*     */   public static String format(String fmt, String[] args)
/*     */   {
/*  52 */     StringBuffer buffer = new StringBuffer(fmt);
/*  53 */     return format(buffer, args).toString();
/*     */   }
/*     */ 
/*     */   public static String format(String fmt, List args)
/*     */   {
/*  58 */     StringBuffer buffer = new StringBuffer(fmt);
/*  59 */     return format(buffer, args).toString();
/*     */   }
/*     */ 
/*     */   public static StringBuffer format(StringBuffer fmt, List args)
/*     */   {
/*  72 */     int idx = 0;
/*  73 */     if (args.size() == 0)
/*  74 */       return fmt;
/*  75 */     for (int i = 0; (i < fmt.length()) && (idx < args.size()); ++i)
/*     */     {
/*  77 */       if ((fmt.charAt(i) == '%') && (fmt.charAt(i + 1) == 's'))
/*     */       {
/*  79 */         fmt.delete(i, i + 1 + 1);
/*  80 */         if (args.get(idx) != null) {
/*  81 */           fmt.insert(i, args.get(idx));
/*     */         }
/*  83 */         ++idx;
/*     */       } else {
/*  85 */         if ((fmt.charAt(i) != '%') || (fmt.charAt(i + 1) != '%'))
/*     */           continue;
/*  87 */         fmt.deleteCharAt(i);
/*     */       }
/*     */     }
/*  90 */     return fmt;
/*     */   }
/*     */ 
/*     */   public static StringBuffer format(StringBuffer fmt, String[] args)
/*     */   {
/*  95 */     int idx = 0;
/*  96 */     if (args.length == 0)
/*  97 */       return fmt;
/*  98 */     for (int i = 0; (i < fmt.length()) && (idx < args.length); ++i)
/*     */     {
/* 100 */       if ((fmt.charAt(i) == '%') && (fmt.charAt(i + 1) == 's'))
/*     */       {
/* 102 */         fmt.delete(i, i + 1 + 1);
/* 103 */         if (args[idx] != null)
/* 104 */           fmt.insert(i, args[idx]);
/* 105 */         ++idx;
/*     */       } else {
/* 107 */         if ((fmt.charAt(i) != '%') || (fmt.charAt(i + 1) != '%'))
/*     */           continue;
/* 109 */         fmt.deleteCharAt(i);
/*     */       }
/*     */     }
/* 112 */     return fmt;
/*     */   }
/*     */ 
/*     */   public static String leftPad(long l, int len) {
/* 116 */     return StringUtils.leftPad(String.valueOf(l), len, '0');
/*     */   }
/*     */ 
/*     */   public static String leftPad(int i, int len) {
/* 120 */     return StringUtils.leftPad(String.valueOf(i), len, '0');
/*     */   }
/*     */ 
/*     */   public static String leftPad(short s, int len) {
/* 124 */     return StringUtils.leftPad(String.valueOf(s), len, '0');
/*     */   }
/*     */ }