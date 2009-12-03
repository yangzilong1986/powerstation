/*     */ package com.hisun.hiexpression;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.lang.reflect.Array;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.NumberFormat;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiExpBasicHelper
/*     */ {
/*     */   static String[] addArgument(String[] array, String element)
/*     */   {
/*  16 */     String[] newArray = (String[])(String[])copyArrayGrow(array, String.class);
/*  17 */     newArray[(newArray.length - 1)] = element;
/*  18 */     return newArray;
/*     */   }
/*     */ 
/*     */   static Object copyArrayGrow(Object array, Class newArrayComponentType)
/*     */   {
/*  23 */     if (array != null) {
/*  24 */       int arrayLength = Array.getLength(array);
/*  25 */       Object newArray = Array.newInstance(array.getClass().getComponentType(), arrayLength + 1);
/*     */ 
/*  27 */       System.arraycopy(array, 0, newArray, 0, arrayLength);
/*  28 */       return newArray;
/*     */     }
/*  30 */     return Array.newInstance(newArrayComponentType, 1);
/*     */   }
/*     */ 
/*     */   static int getLength(Object array)
/*     */   {
/*  35 */     if (array == null) {
/*  36 */       return 0;
/*     */     }
/*  38 */     return Array.getLength(array);
/*     */   }
/*     */ 
/*     */   static String formatDouble(double val, int precision) throws HiException
/*     */   {
/*  43 */     if (precision == 0) {
/*  44 */       return String.valueOf(()val);
/*     */     }
/*     */ 
/*  47 */     String pattern = "0." + StringUtils.repeat("0", precision);
/*     */     try
/*     */     {
/*  50 */       DecimalFormat df = (DecimalFormat)NumberFormat.getInstance();
/*  51 */       df.applyPattern(pattern);
/*  52 */       return df.format(val);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  56 */       throw new HiException("", "", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static final String tosbc(String str)
/*     */   {
/*  62 */     StringBuffer outStr = new StringBuffer();
/*  63 */     String tStr = "";
/*  64 */     byte[] b = null;
/*     */ 
/*  66 */     for (int i = 0; i < str.length(); ++i) {
/*     */       try {
/*  68 */         tStr = str.substring(i, i + 1);
/*  69 */         b = tStr.getBytes("unicode");
/*     */       } catch (UnsupportedEncodingException e) {
/*  71 */         e.printStackTrace();
/*     */       }
/*  73 */       if (b[3] != -1) {
/*  74 */         b[2] = (byte)(b[2] - 32);
/*  75 */         b[3] = -1;
/*     */         try {
/*  77 */           outStr.append(new String(b, "unicode"));
/*     */         } catch (UnsupportedEncodingException e) {
/*  79 */           e.printStackTrace();
/*     */         }
/*     */       } else {
/*  82 */         outStr.append(tStr); }
/*     */     }
/*  84 */     return outStr.toString();
/*     */   }
/*     */ 
/*     */   public static final String todbc(String str)
/*     */   {
/*  89 */     StringBuffer outStr = new StringBuffer();
/*  90 */     String tStr = "";
/*  91 */     byte[] b = null;
/*     */ 
/*  93 */     for (int i = 0; i < str.length(); ++i) {
/*     */       try {
/*  95 */         tStr = str.substring(i, i + 1);
/*  96 */         b = tStr.getBytes("unicode");
/*     */       } catch (UnsupportedEncodingException e) {
/*  98 */         e.printStackTrace();
/*     */       }
/* 100 */       if (b[3] == -1) {
/* 101 */         b[2] = (byte)(b[2] + 32);
/* 102 */         b[3] = 0;
/*     */         try {
/* 104 */           outStr.append(new String(b, "unicode"));
/*     */         } catch (UnsupportedEncodingException e) {
/* 106 */           e.printStackTrace();
/*     */         }
/*     */       } else {
/* 109 */         outStr.append(tStr); }
/*     */     }
/* 111 */     return outStr.toString();
/*     */   }
/*     */ }