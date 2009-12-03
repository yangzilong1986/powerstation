/*     */ package com.hisun.hiexpression;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.text.DecimalFormat;
/*     */ 
/*     */ public class COMP3Util
/*     */ {
/*     */   public static double composeDouble(String tmp, int limit)
/*     */   {
/*  14 */     double amt = 0.0D;
/*  15 */     int ch = 0;
/*  16 */     int high = 0;
/*  17 */     int low = 0;
/*     */ 
/*  19 */     for (int idx = 0; idx < tmp.length() - 1; ++idx) {
/*  20 */       ch = tmp.charAt(idx);
/*  21 */       high = (ch & 0xF0) >> 4;
/*  22 */       low = ch & 0xF;
/*  23 */       amt = amt * 100.0D + high * 10 + low;
/*     */     }
/*  25 */     ch = tmp.charAt(idx);
/*  26 */     high = (ch & 0xF0) >> 4;
/*  27 */     low = ch & 0xF;
/*  28 */     amt = amt * 10.0D + high;
/*  29 */     amt /= Math.pow(10.0D, limit);
/*  30 */     if (low == 13) {
/*  31 */       return (-1.0D * amt);
/*     */     }
/*  33 */     return amt;
/*     */   }
/*     */ 
/*     */   public static byte[] composeCOMP3(double value, int length, int limit)
/*     */   {
/*  45 */     String vstr = getFormatorStr("0.00", limit, value);
/*  46 */     StringBuffer buf = new StringBuffer();
/*  47 */     int len = (length + 1) / 2 + 1;
/*  48 */     int num_zero = len * 2 - 1 - vstr.length();
/*  49 */     for (int i = 0; i < num_zero; ++i) {
/*  50 */       buf.append("0");
/*     */     }
/*  52 */     buf.append(vstr);
/*  53 */     if ((value > 1.E-005D) && (value < 0.0D))
/*  54 */       buf.append("D");
/*     */     else {
/*  56 */       buf.append("C");
/*     */     }
/*  58 */     vstr = buf.toString();
/*  59 */     byte[] b = new byte[len];
/*  60 */     int high = 0;
/*  61 */     int low = 0;
/*  62 */     for (int idx = 0; idx < len * 2; idx += 2) {
/*  63 */       if (idx + 2 == vstr.length()) {
/*  64 */         high = vstr.charAt(idx) - '0';
/*  65 */         low = vstr.charAt(idx + 1);
/*  66 */         if (low == 68)
/*  67 */           low = 13;
/*     */         else
/*  69 */           low = 12;
/*     */       }
/*     */       else {
/*  72 */         high = vstr.charAt(idx) - '0';
/*  73 */         low = vstr.charAt(idx + 1) - '0';
/*     */       }
/*  75 */       int ch = (high << 4) + low;
/*  76 */       int chv = 0;
/*  77 */       if (ch > 127)
/*  78 */         chv = -1 * (256 - ch);
/*     */       else {
/*  80 */         chv = ch;
/*     */       }
/*  82 */       b[(idx / 2)] = (byte)chv;
/*     */     }
/*  84 */     return b;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/*  88 */     byte[] b = null;
/*  89 */     b = composeCOMP3(-99999999.989999995D, 11, 2);
/*  90 */     System.out.println(b);
/*  91 */     b = composeCOMP3(88888888.989999995D, 11, 2);
/*  92 */     System.out.println(b);
/*  93 */     b = composeCOMP3(99999999.989999995D, 11, 2);
/*  94 */     System.out.println(b);
/*  95 */     b = composeCOMP3(1.99D, 11, 2);
/*  96 */     System.out.println(b);
/*  97 */     b = composeCOMP3(0.99D, 11, 2);
/*  98 */     System.out.println(b);
/*  99 */     b = composeCOMP3(0.01D, 11, 2);
/* 100 */     System.out.println(b);
/*     */   }
/*     */ 
/*     */   private static String getFormatorStr(String pattern, int fraction, double value)
/*     */   {
/* 105 */     DecimalFormat nf = new DecimalFormat();
/* 106 */     nf.applyPattern(pattern);
/* 107 */     nf.setMaximumFractionDigits(fraction);
/* 108 */     nf.setMinimumFractionDigits(fraction);
/* 109 */     return nf.format(value);
/*     */   }
/*     */ }