/*     */ package com.hisun.crypt.des;
/*     */ 
/*     */ import com.hisun.crypt.Encryptor;
/*     */ import com.hisun.crypt.Key;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.SecureRandom;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.SecretKeyFactory;
/*     */ import javax.crypto.spec.DESKeySpec;
/*     */ 
/*     */ public class DESEncryptor
/*     */   implements Encryptor
/*     */ {
/*     */   private Key key;
/*     */   Cipher ecipher;
/*     */ 
/*     */   public DESEncryptor()
/*     */   {
/*     */   }
/*     */ 
/*     */   public DESEncryptor(Key key)
/*     */   {
/*  22 */     this.key = key;
/*  23 */     createCipher();
/*     */   }
/*     */ 
/*     */   public byte[] encrypt(byte[] data, int offset, int len)
/*     */     throws Exception
/*     */   {
/*  29 */     byte[] encryptedData = null;
/*     */     try
/*     */     {
/*  32 */       encryptedData = this.ecipher.doFinal(data, offset, len);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  37 */       e.printStackTrace();
/*     */     }
/*  39 */     return encryptedData;
/*     */   }
/*     */ 
/*     */   public byte[] encrypt(byte[] data)
/*     */     throws Exception
/*     */   {
/*  47 */     byte[] encryptedData = null;
/*     */     try
/*     */     {
/*  50 */       encryptedData = this.ecipher.doFinal(data);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  55 */       e.printStackTrace();
/*     */     }
/*  57 */     return encryptedData;
/*     */   }
/*     */ 
/*     */   public void encrypt(InputStream in, OutputStream out)
/*     */     throws Exception
/*     */   {
/*     */     try
/*     */     {
/*  65 */       int blockSize = this.ecipher.getBlockSize();
/*  66 */       int outputSize = this.ecipher.getOutputSize(blockSize);
/*  67 */       byte[] inBytes = new byte[blockSize];
/*  68 */       byte[] outBytes = new byte[outputSize];
/*  69 */       int inLength = 0;
/*  70 */       boolean more = true;
/*  71 */       while (more)
/*     */       {
/*  73 */         inLength = in.read(inBytes);
/*  74 */         if (inLength == blockSize)
/*     */         {
/*  76 */           int outLength = this.ecipher.update(inBytes, 0, blockSize, outBytes);
/*  77 */           out.write(outBytes, 0, outLength);
/*     */         }
/*     */ 
/*  80 */         more = false;
/*     */       }
/*  82 */       if (inLength > 0)
/*     */       {
/*  84 */         outBytes = this.ecipher.doFinal(inBytes, 0, inLength);
/*     */       }
/*     */       else
/*  87 */         outBytes = this.ecipher.doFinal();
/*  88 */       out.write(outBytes);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  93 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setKey(Key key) {
/*  98 */     this.key = key;
/*  99 */     createCipher();
/*     */   }
/*     */ 
/*     */   private void createCipher()
/*     */   {
/* 104 */     if (this.key == null) {
/* 105 */       return;
/*     */     }
/*     */     try
/*     */     {
/* 109 */       SecureRandom sr = new SecureRandom();
/* 110 */       DESKeySpec dks = new DESKeySpec(this.key.getKey());
/* 111 */       SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
/* 112 */       SecretKey sk = keyFactory.generateSecret(dks);
/*     */ 
/* 115 */       this.ecipher = Cipher.getInstance("DES");
/* 116 */       this.ecipher.init(1, sk, sr);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 120 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }