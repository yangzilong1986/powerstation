/*     */ package com.hisun.crypt.mac;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public class CryptUtilImpl
/*     */ {
/*     */   public String cryptDes(String source, String key)
/*     */   {
/*  17 */     return Des3Encryption.encode(key, source);
/*     */   }
/*     */ 
/*     */   public String decryptDes(String des, String key)
/*     */   {
/*  22 */     return Des3Encryption.decode(key, des);
/*     */   }
/*     */ 
/*     */   public String cryptDes(String source)
/*     */   {
/*  27 */     return Des3Encryption.encode(source);
/*     */   }
/*     */ 
/*     */   public String decryptDes(String des)
/*     */   {
/*  32 */     return Des3Encryption.decode(des);
/*     */   }
/*     */ 
/*     */   public String cryptMd5(String source, String key)
/*     */   {
/*     */     byte[] keyb;
/*     */     byte[] value;
/*  36 */     byte[] k_ipad = new byte[64];
/*  37 */     byte[] k_opad = new byte[64];
/*     */     try
/*     */     {
/*  41 */       keyb = key.getBytes("UTF-8");
/*  42 */       value = source.getBytes("UTF-8");
/*     */     } catch (UnsupportedEncodingException e) {
/*  44 */       keyb = key.getBytes();
/*  45 */       value = source.getBytes();
/*     */     }
/*  47 */     Arrays.fill(k_ipad, keyb.length, 64, 54);
/*  48 */     Arrays.fill(k_opad, keyb.length, 64, 92);
/*  49 */     for (int i = 0; i < keyb.length; ++i)
/*     */     {
/*  51 */       k_ipad[i] = (byte)(keyb[i] ^ 0x36);
/*  52 */       k_opad[i] = (byte)(keyb[i] ^ 0x5C);
/*     */     }
/*  54 */     MessageDigest md = null;
/*     */     try
/*     */     {
/*  57 */       md = MessageDigest.getInstance("MD5");
/*     */     }
/*     */     catch (NoSuchAlgorithmException e)
/*     */     {
/*  63 */       return null;
/*     */     }
/*  65 */     md.update(k_ipad);
/*  66 */     md.update(value);
/*  67 */     byte[] dg = md.digest();
/*  68 */     md.reset();
/*  69 */     md.update(k_opad);
/*  70 */     md.update(dg, 0, 16);
/*  71 */     dg = md.digest();
/*  72 */     return toHex(dg);
/*     */   }
/*     */ 
/*     */   public String cryptMd5(String source) {
/*     */     byte[] value;
/*     */     try {
/*  78 */       value = source.getBytes("UTF-8");
/*     */     } catch (UnsupportedEncodingException e) {
/*  80 */       value = source.getBytes();
/*     */     }
/*  82 */     MessageDigest md = null;
/*     */     try
/*     */     {
/*  85 */       md = MessageDigest.getInstance("MD5");
/*     */     }
/*     */     catch (NoSuchAlgorithmException e)
/*     */     {
/*  91 */       return null;
/*     */     }
/*  93 */     md.update(value);
/*  94 */     byte[] dg = md.digest();
/*  95 */     return toHex(dg);
/*     */   }
/*     */ 
/*     */   public static String toHex(byte[] input)
/*     */   {
/* 100 */     if (input == null) {
/* 101 */       return null;
/*     */     }
/* 103 */     StringBuffer output = new StringBuffer(input.length * 2);
/* 104 */     for (int i = 0; i < input.length; ++i)
/*     */     {
/* 106 */       int current = input[i] & 0xFF;
/* 107 */       if (current < 16)
/* 108 */         output.append("0");
/* 109 */       output.append(Integer.toString(current, 16));
/*     */     }
/*     */ 
/* 112 */     return output.toString();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */     String s1;
/* 122 */     StringBuffer source = new StringBuffer();
/* 123 */     for (int i = 0; i < 1; ++i) {
/* 124 */       source.append("GenericServerServiceCallbackHandler Callback class, Users can extend this class and implement");
/*     */     }
/* 126 */     String key = "123456781234567812345678";
/* 127 */     CryptUtilImpl impl = new CryptUtilImpl();
/* 128 */     String des = null;
/*     */ 
/* 130 */     long l1 = System.currentTimeMillis();
/* 131 */     for (int i = 0; i < 1000; ++i) {
/* 132 */       des = impl.cryptDes(source.toString(), key);
/* 133 */       s1 = impl.decryptDes(des, key);
/*     */     }
/* 135 */     long l2 = System.currentTimeMillis();
/* 136 */     System.out.println(l2 - l1);
/*     */   }
/*     */ }