/*     */ package com.hisun.atmp.crypt;
/*     */ 
/*     */ public class Shuffle
/*     */ {
/*     */   public static final int TRANKEY_LEN = 8;
/*   5 */   public static final char[] mask = "19F2C8276AD0839B".toCharArray();
/*   6 */   public static final char[] step = "35827629".toCharArray();
/*   7 */   public static final char[] keymask = "BA987D40".toCharArray();
/*     */ 
/*     */   static int t_xtoi(int x)
/*     */   {
/*  11 */     return ((x >= 65) ? x - 65 + 10 : x - 48);
/*     */   }
/*     */ 
/*     */   public static int shuffle(char[] inbuf, char[] key, char[] outbuf, int len) {
/*  15 */     int c = 0;
/*     */ 
/*  17 */     for (int i = 0; i < len; ++i)
/*     */     {
/*     */       int j;
/*     */       int k;
/*  18 */       if (i < 8) {
/*  19 */         j = inbuf[(len - i - 1)] - '0' + key[(7 - i)] - '0' + c;
/*  20 */         c = j / 10;
/*  21 */       } else if (i == 8) {
/*  22 */         j = inbuf[(len - i - 1)] - '0' + c;
/*  23 */         c = j / 10;
/*  24 */       } else if (i == 9) {
/*  25 */         j = inbuf[(len - i - 1)] - '0' + c;
/*     */       } else {
/*  27 */         j = inbuf[(len - i - 1)] - '0';
/*     */       }
/*  29 */       j %= 10;
/*  30 */       if (i < 16)
/*  31 */         k = t_xtoi(mask[(15 - i)]);
/*     */       else
/*  33 */         k = 0;
/*  34 */       outbuf[(len - i - 1)] = (char)(j ^ k + 48);
/*     */     }
/*     */ 
/*  37 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int unshuffle(char[] inbuf, char[] key, char[] outbuf, int len)
/*     */   {
/*     */     int j;
/*  41 */     int c = 0;
/*     */ 
/*  43 */     for (int i = 0; i < len; ++i)
/*     */     {
/*     */       int k;
/*  45 */       j = inbuf[(len - i - 1)] - '0';
/*  46 */       if (i < 16)
/*  47 */         k = t_xtoi(mask[(15 - i)]);
/*     */       else
/*  49 */         k = 0;
/*  50 */       inbuf[(len - i - 1)] = (char)(j ^ k + 48);
/*     */     }
/*  52 */     for (i = 0; i < len; ++i) {
/*  53 */       if (i < 8) {
/*  54 */         j = inbuf[(len - i - 1)] - '0' - (key[(7 - i)] - '0') + c;
/*  55 */         if (j < 0) {
/*  56 */           j += 10;
/*  57 */           c = -1;
/*     */         } else {
/*  59 */           c = 0; }
/*  60 */       } else if (i == 8) {
/*  61 */         j = inbuf[(len - i - 1)] - '0' + c;
/*  62 */         if (j < 0) {
/*  63 */           j += 10;
/*  64 */           c = -1;
/*     */         } else {
/*  66 */           c = 0; }
/*  67 */       } else if (i == 9) {
/*  68 */         j = inbuf[(len - i - 1)] - '0' + c;
/*  69 */         if (j < 0)
/*  70 */           j += 10;
/*     */       }
/*     */       else {
/*  73 */         j = inbuf[(len - i - 1)] - '0';
/*     */       }
/*  75 */       outbuf[(len - i - 1)] = (char)(j + 48);
/*     */     }
/*  77 */     return 0;
/*     */   }
/*     */ 
/*     */   public static void updatetrm(char[] key)
/*     */   {
/*  82 */     int c = 0;
/*     */ 
/*  84 */     for (int i = 0; i < 8; ++i) {
/*  85 */       int j = key[(8 - i - 1)] - '0' + step[(7 - i)] - '0' + c;
/*  86 */       c = j / 10;
/*  87 */       j %= 10;
/*     */ 
/*  89 */       key[(8 - i - 1)] = (char)(j + 48);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void shufkey(char[] pkey)
/*     */   {
/*  97 */     for (int i = 0; i < 8; ++i)
/*  98 */       pkey[i] = (char)((pkey[i] - '0' ^ t_xtoi(keymask[i])) + 48);
/*     */   }
/*     */ 
/*     */   public static void strToBin(char[] Outpuf, char[] Input, int num)
/*     */   {
/* 106 */     for (int i = 0; i < num; ++i)
/* 107 */       Outpuf[i] = (char)((Input[(i << 1)] - '0' << 4) + Input[((i << 1) + 1)] - '0');
/*     */   }
/*     */ 
/*     */   public static void binToStr(char[] Output, char[] Input, int num)
/*     */   {
/* 114 */     for (int i = 0; i < num; ++i) {
/* 115 */       Output[(i << 1)] = (char)(((Input[i] & 0xF0) >> '\4') + 48);
/* 116 */       Output[((i << 1) + 1)] = (char)((Input[i] & 0xF) + '0');
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void strToBCD(char[] Outpuf, char[] Input, int num)
/*     */   {
/* 124 */     for (int i = 0; i < num; ++i)
/* 125 */       Outpuf[i] = (char)((t_xtoi(Input[(i << 1)]) << 4) + t_xtoi(Input[((i << 1) + 1)]));
/*     */   }
/*     */ 
/*     */   public static void bcdToStr(char[] Output, char[] Input, int num)
/*     */   {
/* 131 */     for (int i = 0; i < num; ++i) {
/* 132 */       Output[(i << 1)] = bin2char((Input[i] & 0xF0) >> '\4');
/* 133 */       Output[((i << 1) + 1)] = bin2char(Input[i] & 0xF);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static char bin2char(int bin) {
/* 138 */     return (char)((bin < 10) ? bin + 48 : bin - 10 + 65);
/*     */   }
/*     */ }