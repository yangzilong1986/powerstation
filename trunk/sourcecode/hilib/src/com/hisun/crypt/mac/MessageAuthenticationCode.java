/*     */ package com.hisun.crypt.mac;
/*     */ 
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import javax.crypto.BadPaddingException;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.IllegalBlockSizeException;
/*     */ import javax.crypto.NoSuchPaddingException;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.SecretKeyFactory;
/*     */ import javax.crypto.spec.DESKeySpec;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ 
/*     */ public class MessageAuthenticationCode
/*     */ {
/*     */   public static byte[] mac(byte[] key, byte[] data)
/*     */     throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IllegalStateException
/*     */   {
/*  93 */     return mac(key, data, 0, data.length);
/*     */   }
/*     */ 
/*     */   public static byte[] mac(byte[] key, byte[] data, int offset, int len)
/*     */     throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IllegalStateException
/*     */   {
/* 120 */     String Algorithm = "DES";
/*     */ 
/* 122 */     SecretKey deskey = new SecretKeySpec(key, "DES");
/*     */ 
/* 124 */     Cipher c1 = Cipher.getInstance("DES");
/* 125 */     c1.init(1, deskey);
/* 126 */     byte[] buf = { 0, 0, 0, 0, 0, 0, 0, 0 };
/* 127 */     for (int i = 0; i < len; ) {
/* 128 */       for (int j = 0; (j < 8) && (i < len); ++j)
/*     */       {
/*     */         int tmp100_98 = j;
/*     */         byte[] tmp100_96 = buf; tmp100_96[tmp100_98] = (byte)(tmp100_96[tmp100_98] ^ data[(offset + i)]);
/*     */ 
/* 128 */         ++i;
/*     */       }
/*     */ 
/* 131 */       buf = c1.update(buf);
/*     */     }
/* 133 */     c1.doFinal();
/* 134 */     return buf;
/*     */   }
/*     */ 
/*     */   public static byte[] desEncryption(byte[] key, byte[] data)
/*     */     throws NoSuchAlgorithmException, Exception
/*     */   {
/* 158 */     String Algorithm = "DES/ECB/NoPadding";
/*     */ 
/* 160 */     if ((key.length != 8) || (data.length != 8)) {
/* 161 */       throw new IllegalArgumentException("key or data's length != 8");
/*     */     }
/* 163 */     DESKeySpec desKS = new DESKeySpec(key);
/* 164 */     SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
/* 165 */     SecretKey deskey = skf.generateSecret(desKS);
/*     */ 
/* 167 */     Cipher c1 = Cipher.getInstance("DES/ECB/NoPadding");
/* 168 */     c1.init(1, deskey);
/*     */ 
/* 171 */     byte[] buf = c1.doFinal(data);
/*     */ 
/* 173 */     byte[] enc_data = new byte[8];
/* 174 */     System.arraycopy(buf, 0, enc_data, 0, 8);
/* 175 */     return enc_data;
/*     */   }
/*     */ 
/*     */   public static byte[] desDecryption(byte[] key, byte[] data)
/*     */     throws NoSuchAlgorithmException, Exception
/*     */   {
/* 198 */     String Algorithm = "DES/ECB/NoPadding";
/*     */ 
/* 200 */     if ((key.length != 8) || (data.length != 8)) {
/* 201 */       throw new IllegalArgumentException("key's len != 8 or data's length !=8");
/*     */     }
/*     */ 
/* 204 */     SecretKey deskey = new SecretKeySpec(key, "DES");
/*     */ 
/* 206 */     Cipher c1 = Cipher.getInstance("DES/ECB/NoPadding");
/* 207 */     c1.init(2, deskey);
/*     */ 
/* 210 */     byte[] decrypted = c1.doFinal(data);
/* 211 */     return decrypted;
/*     */   }
/*     */ 
/*     */   protected byte[] encryptByDES(byte[] bytP, byte[] bytKey)
/*     */     throws Exception
/*     */   {
/* 222 */     DESKeySpec desKS = new DESKeySpec(bytKey);
/* 223 */     SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
/* 224 */     SecretKey sk = skf.generateSecret(desKS);
/* 225 */     Cipher cip = Cipher.getInstance("DES");
/* 226 */     cip.init(1, sk);
/* 227 */     return cip.doFinal(bytP);
/*     */   }
/*     */ 
/*     */   protected byte[] decryptByDES(byte[] bytE, byte[] bytKey)
/*     */     throws Exception
/*     */   {
/* 238 */     DESKeySpec desKS = new DESKeySpec(bytKey);
/* 239 */     SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
/* 240 */     SecretKey sk = skf.generateSecret(desKS);
/* 241 */     Cipher cip = Cipher.getInstance("DES");
/* 242 */     cip.init(2, sk);
/* 243 */     return cip.doFinal(bytE);
/*     */   }
/*     */ 
/*     */   public static byte[] des3Encryption(byte[] key, byte[] data)
/*     */     throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IllegalStateException
/*     */   {
/* 264 */     String Algorithm = "DESede";
/*     */ 
/* 266 */     SecretKey deskey = new SecretKeySpec(key, "DESede");
/*     */ 
/* 268 */     Cipher c1 = Cipher.getInstance("DESede");
/* 269 */     c1.init(1, deskey);
/* 270 */     return c1.doFinal(data);
/*     */   }
/*     */ 
/*     */   public static byte[] des3Decryption(byte[] key, byte[] data)
/*     */     throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IllegalStateException
/*     */   {
/* 290 */     String Algorithm = "DESede";
/*     */ 
/* 292 */     SecretKey deskey = new SecretKeySpec(key, "DESede");
/*     */ 
/* 294 */     Cipher c1 = Cipher.getInstance("DESede");
/* 295 */     c1.init(2, deskey);
/* 296 */     return c1.doFinal(data);
/*     */   }
/*     */ }