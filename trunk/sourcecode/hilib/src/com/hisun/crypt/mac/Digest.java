/*     */ package com.hisun.crypt.mac;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public class Digest
/*     */ {
/*     */   public static final String ENCODE = "UTF-8";
/*     */ 
/*     */   public static String hmacSign(String aValue, String aKey)
/*     */   {
/*  25 */     return hmacSign(aValue, aKey, "UTF-8");
/*     */   }
/*     */ 
/*     */   public static String hmacSign(String aValue, String aKey, String encoding)
/*     */   {
/*     */     byte[] keyb;
/*     */     byte[] value;
/*  37 */     byte[] k_ipad = new byte[64];
/*  38 */     byte[] k_opad = new byte[64];
/*     */     try
/*     */     {
/*  43 */       keyb = aKey.getBytes(encoding);
/*  44 */       value = aValue.getBytes(encoding);
/*     */     }
/*     */     catch (UnsupportedEncodingException e)
/*     */     {
/*  48 */       keyb = aKey.getBytes();
/*  49 */       value = aValue.getBytes();
/*     */     }
/*  51 */     Arrays.fill(k_ipad, keyb.length, 64, 54);
/*  52 */     Arrays.fill(k_opad, keyb.length, 64, 92);
/*  53 */     for (int i = 0; i < keyb.length; ++i)
/*     */     {
/*  55 */       k_ipad[i] = (byte)(keyb[i] ^ 0x36);
/*  56 */       k_opad[i] = (byte)(keyb[i] ^ 0x5C);
/*     */     }
/*     */ 
/*  59 */     MessageDigest md = null;
/*     */     try
/*     */     {
/*  62 */       md = MessageDigest.getInstance("MD5");
/*     */     }
/*     */     catch (NoSuchAlgorithmException e)
/*     */     {
/*  66 */       e.printStackTrace();
/*  67 */       return null;
/*     */     }
/*  69 */     md.update(k_ipad);
/*  70 */     md.update(value);
/*  71 */     byte[] dg = md.digest();
/*  72 */     md.reset();
/*  73 */     md.update(k_opad);
/*  74 */     md.update(dg, 0, 16);
/*  75 */     dg = md.digest();
/*  76 */     return ConvertUtils.toHex(dg);
/*     */   }
/*     */ 
/*     */   public static String hmacSHASign(String aValue, String aKey, String encoding)
/*     */   {
/*     */     byte[] keyb;
/*     */     byte[] value;
/*  88 */     byte[] k_ipad = new byte[64];
/*  89 */     byte[] k_opad = new byte[64];
/*     */     try
/*     */     {
/*  94 */       keyb = aKey.getBytes(encoding);
/*  95 */       value = aValue.getBytes(encoding);
/*     */     }
/*     */     catch (UnsupportedEncodingException e)
/*     */     {
/*  99 */       keyb = aKey.getBytes();
/* 100 */       value = aValue.getBytes();
/*     */     }
/* 102 */     Arrays.fill(k_ipad, keyb.length, 64, 54);
/* 103 */     Arrays.fill(k_opad, keyb.length, 64, 92);
/* 104 */     for (int i = 0; i < keyb.length; ++i)
/*     */     {
/* 106 */       k_ipad[i] = (byte)(keyb[i] ^ 0x36);
/* 107 */       k_opad[i] = (byte)(keyb[i] ^ 0x5C);
/*     */     }
/*     */ 
/* 110 */     MessageDigest md = null;
/*     */     try
/*     */     {
/* 113 */       md = MessageDigest.getInstance("SHA");
/*     */     }
/*     */     catch (NoSuchAlgorithmException e)
/*     */     {
/* 117 */       e.printStackTrace();
/* 118 */       return null;
/*     */     }
/* 120 */     md.update(k_ipad);
/* 121 */     md.update(value);
/* 122 */     byte[] dg = md.digest();
/* 123 */     md.reset();
/* 124 */     md.update(k_opad);
/* 125 */     md.update(dg, 0, 20);
/* 126 */     dg = md.digest();
/* 127 */     return ConvertUtils.toHex(dg);
/*     */   }
/*     */ 
/*     */   public static String digest(String aValue)
/*     */   {
/* 137 */     return digest(aValue, "UTF-8");
/*     */   }
/*     */ 
/*     */   public static String digest(String aValue, String encoding)
/*     */   {
/*     */     byte[] value;
/* 149 */     aValue = aValue.trim();
/*     */     try
/*     */     {
/* 153 */       value = aValue.getBytes(encoding);
/*     */     }
/*     */     catch (UnsupportedEncodingException e)
/*     */     {
/* 157 */       value = aValue.getBytes();
/*     */     }
/* 159 */     MessageDigest md = null;
/*     */     try
/*     */     {
/* 162 */       md = MessageDigest.getInstance("SHA");
/*     */     }
/*     */     catch (NoSuchAlgorithmException e)
/*     */     {
/* 166 */       e.printStackTrace();
/* 167 */       return null;
/*     */     }
/* 169 */     return ConvertUtils.toHex(md.digest(value));
/*     */   }
/*     */ 
/*     */   public static String digest(String aValue, String alg, String encoding)
/*     */   {
/*     */     byte[] value;
/* 181 */     aValue = aValue.trim();
/*     */     try
/*     */     {
/* 185 */       value = aValue.getBytes(encoding);
/*     */     }
/*     */     catch (UnsupportedEncodingException e)
/*     */     {
/* 189 */       value = aValue.getBytes();
/*     */     }
/* 191 */     MessageDigest md = null;
/*     */     try
/*     */     {
/* 194 */       md = MessageDigest.getInstance(alg);
/*     */     }
/*     */     catch (NoSuchAlgorithmException e)
/*     */     {
/* 198 */       e.printStackTrace();
/* 199 */       return null;
/*     */     }
/* 201 */     return ConvertUtils.toHex(md.digest(value));
/*     */   }
/*     */ }