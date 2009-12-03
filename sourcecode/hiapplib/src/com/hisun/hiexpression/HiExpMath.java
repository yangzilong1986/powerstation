/*     */ package com.hisun.hiexpression;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiExpMath
/*     */ {
/*     */   public static String OR(Object ctx, String[] args)
/*     */     throws HiException
/*     */   {
/*  25 */     if (args.length < 2) {
/*  26 */       throw new HiException("215110", "OR");
/*     */     }
/*  28 */     for (int i = 0; i < args.length; ++i) {
/*  29 */       if (StringUtils.equals(args[i], "1"))
/*  30 */         return "1";
/*     */     }
/*  32 */     return "0";
/*     */   }
/*     */ 
/*     */   public static String NOT(Object ctx, String[] args)
/*     */     throws HiException
/*     */   {
/*  49 */     if (args.length != 1)
/*  50 */       throw new HiException("215110", "NOT");
/*  51 */     if (StringUtils.equals(args[0], "0")) {
/*  52 */       return "1";
/*     */     }
/*  54 */     return "0";
/*     */   }
/*     */ 
/*     */   public static String AND(Object ctx, String[] args)
/*     */     throws HiException
/*     */   {
/*  71 */     if (args.length < 2) {
/*  72 */       throw new HiException("215110", "AND");
/*     */     }
/*  74 */     for (int i = 0; i < args.length; ++i) {
/*  75 */       if (StringUtils.equals(args[i], "0"))
/*  76 */         return "0";
/*     */     }
/*  78 */     return "1";
/*     */   }
/*     */ 
/*     */   public static String ADD(Object ctx, String[] args)
/*     */   {
/*  93 */     long result = 0L;
/*  94 */     for (int i = 0; i < args.length; ++i) {
/*  95 */       result += NumberUtils.toLong(StringUtils.trim(args[i]));
/*     */     }
/*  97 */     return String.valueOf(result);
/*     */   }
/*     */ 
/*     */   public static String SUB(Object ctx, String[] args)
/*     */     throws HiException
/*     */   {
/* 112 */     if (args.length < 2) {
/* 113 */       throw new HiException("215110", "SUB");
/*     */     }
/* 115 */     long result = NumberUtils.toLong(StringUtils.trim(args[0]));
/* 116 */     for (int i = 1; i < args.length; ++i) {
/* 117 */       result -= NumberUtils.toLong(StringUtils.trim(args[i]));
/*     */     }
/*     */ 
/* 120 */     return String.valueOf(result);
/*     */   }
/*     */ 
/*     */   public static String MUL(Object ctx, String[] args)
/*     */     throws HiException
/*     */   {
/* 135 */     if (args.length < 2) {
/* 136 */       throw new HiException("215110", "MUL");
/*     */     }
/* 138 */     long result = 1L;
/* 139 */     for (int i = 0; i < args.length; ++i) {
/* 140 */       result *= NumberUtils.toLong(StringUtils.trim(args[i]));
/*     */     }
/* 142 */     return String.valueOf(result);
/*     */   }
/*     */ 
/*     */   public static String DIV(Object ctx, String[] args)
/*     */     throws HiException
/*     */   {
/* 157 */     if (args.length < 2)
/* 158 */       throw new HiException("215110", "DIV");
/* 159 */     long result = NumberUtils.toLong(StringUtils.trim(args[0]));
/* 160 */     for (int i = 1; i < args.length; ++i) {
/* 161 */       result /= NumberUtils.toLong(StringUtils.trim(args[i]));
/*     */     }
/* 163 */     return String.valueOf(result);
/*     */   }
/*     */ 
/*     */   public static String MOD(Object ctx, String[] args)
/*     */     throws HiException
/*     */   {
/* 178 */     if (args.length < 2)
/* 179 */       throw new HiException("215110", "MOD");
/* 180 */     long result = NumberUtils.toLong(StringUtils.trim(args[0]));
/* 181 */     for (int i = 1; i < args.length; ++i) {
/* 182 */       result %= NumberUtils.toLong(StringUtils.trim(args[i]));
/*     */     }
/* 184 */     return String.valueOf(result);
/*     */   }
/*     */ }