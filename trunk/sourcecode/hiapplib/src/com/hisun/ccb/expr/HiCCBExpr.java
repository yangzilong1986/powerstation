/*     */ package com.hisun.ccb.expr;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import org.apache.commons.lang.RandomStringUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiCCBExpr
/*     */ {
/*     */   private static boolean isHaveInCharSet(String str, String char_set)
/*     */   {
/*  19 */     byte[] bytes1 = str.getBytes();
/*  20 */     byte[] bytes2 = char_set.getBytes();
/*  21 */     for (int i = 0; i < bytes1.length; ++i)
/*     */     {
/*  23 */       for (int j = 0; j < bytes2.length; ++j)
/*     */       {
/*  25 */         if (bytes1[i] == bytes2[j])
/*     */         {
/*  27 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*  31 */     return false;
/*     */   }
/*     */ 
/*     */   private static boolean isSeriesSameChar(String str, int num)
/*     */   {
/*  41 */     byte[] bytes = str.getBytes();
/*  42 */     byte c = bytes[0];
/*     */ 
/*  44 */     if (num < 2)
/*     */     {
/*  46 */       return false;
/*     */     }
/*     */ 
/*  49 */     int i = 1; for (int j = 1; i < bytes.length; ++i)
/*     */     {
/*  51 */       if (bytes[i] == c)
/*     */       {
/*  53 */         ++j;
/*  54 */         if (j < num)
/*     */           continue;
/*  56 */         return true;
/*     */       }
/*     */ 
/*  61 */       j = 1;
/*  62 */       c = bytes[i];
/*     */     }
/*     */ 
/*  65 */     return false;
/*     */   }
/*     */ 
/*     */   public static String BINNOT(Object ctx, String[] args)
/*     */     throws HiException
/*     */   {
/*  78 */     if (args.length != 1)
/*  79 */       throw new HiException("215110", "BINNOT");
/*  80 */     byte[] bytes = args[0].getBytes();
/*  81 */     StringBuffer result = new StringBuffer();
/*  82 */     for (int i = 0; i < bytes.length; ++i)
/*     */     {
/*  84 */       if (bytes[i] == 48)
/*  85 */         result.append('1');
/*     */       else {
/*  87 */         result.append('0');
/*     */       }
/*     */     }
/*  90 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public static String BINAND(Object ctx, String[] args)
/*     */     throws HiException
/*     */   {
/* 105 */     if (args.length != 2) {
/* 106 */       throw new HiException("215110", "BINAND");
/*     */     }
/* 108 */     byte[] bytes1 = args[0].getBytes();
/* 109 */     byte[] bytes2 = args[1].getBytes();
/* 110 */     int length = Math.min(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
/*     */ 
/* 112 */     StringBuffer result = new StringBuffer();
/* 113 */     for (int i = 0; i < length; ++i)
/*     */     {
/* 115 */       if ((bytes1[i] == 48) && (bytes2[i] == 48))
/* 116 */         result.append('0');
/*     */       else {
/* 118 */         result.append('1');
/*     */       }
/*     */     }
/* 121 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public static String CPMAND(Object ctx, String[] args)
/*     */     throws HiException
/*     */   {
/* 136 */     if (args.length != 2) {
/* 137 */       throw new HiException("215110", "BINAND");
/*     */     }
/* 139 */     byte[] bytes1 = args[0].getBytes();
/* 140 */     byte[] bytes2 = args[1].getBytes();
/* 141 */     int length = Math.min(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
/*     */ 
/* 143 */     for (int i = 0; i < length; ++i)
/*     */     {
/* 145 */       if ((bytes1[i] != 48) && (bytes2[i] != 48)) {
/* 146 */         return new String("1");
/*     */       }
/*     */     }
/* 149 */     return new String("0");
/*     */   }
/*     */ 
/*     */   public static String ISSIMPLEPASSWD(Object ctx, String[] args)
/*     */     throws HiException
/*     */   {
/* 169 */     if (args.length != 1) {
/* 170 */       throw new HiException("215110", "ISSIMPLEPASSWD");
/*     */     }
/*     */ 
/* 173 */     if (args[0].length() != 6)
/*     */     {
/* 175 */       return new String("1");
/*     */     }
/*     */ 
/* 179 */     if (!(isHaveInCharSet(args[0], "0123456789")))
/*     */     {
/* 181 */       return new String("2");
/*     */     }
/*     */ 
/* 185 */     if (!(isHaveInCharSet(args[0], "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")))
/*     */     {
/* 187 */       return new String("3");
/*     */     }
/*     */ 
/* 191 */     if (!(isHaveInCharSet(args[0], "!@#$%^&*()")))
/*     */     {
/* 193 */       return new String("4");
/*     */     }
/*     */ 
/* 196 */     if (isSeriesSameChar(args[0], 3))
/*     */     {
/* 198 */       return new String("5");
/*     */     }
/*     */ 
/* 201 */     return new String("0");
/*     */   }
/*     */ 
/*     */   public static String GETRANDOM(Object ctx, String[] args)
/*     */     throws HiException
/*     */   {
/* 214 */     if (args.length < 1) {
/* 215 */       throw new HiException("215110", "GETRANDOM");
/*     */     }
/* 217 */     int len = NumberUtils.toInt(StringUtils.trim(args[0]));
/*     */ 
/* 219 */     return RandomStringUtils.randomNumeric(len);
/*     */   }
/*     */ 
/*     */   public static String DESENCRYPT(Object ctx, String[] args)
/*     */     throws HiException
/*     */   {
/* 232 */     if (args.length < 1) {
/* 233 */       throw new HiException("215110", "DESENCRYPT");
/*     */     }
/*     */ 
/* 238 */     return args[0];
/*     */   }
/*     */ }