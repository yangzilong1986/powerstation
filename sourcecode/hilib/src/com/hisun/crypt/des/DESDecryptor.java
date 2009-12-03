/*     */ package com.hisun.crypt.des;
/*     */ 
/*     */ import com.hisun.crypt.Decryptor;
/*     */ import com.hisun.crypt.Key;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.SecureRandom;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.SecretKeyFactory;
/*     */ import javax.crypto.spec.DESKeySpec;
/*     */ 
/*     */ public class DESDecryptor
/*     */   implements Decryptor
/*     */ {
/*     */   private Key key;
/*     */   private Cipher dcipher;
/*     */ 
/*     */   public DESDecryptor()
/*     */   {
/*     */   }
/*     */ 
/*     */   public DESDecryptor(Key key)
/*     */   {
/*  22 */     this.key = key;
/*  23 */     createCipher();
/*     */   }
/*     */ 
/*     */   public byte[] decrypt(byte[] data, int offset, int length) throws Exception
/*     */   {
/*  28 */     byte[] deCryptedData = null;
/*     */     try
/*     */     {
/*  31 */       deCryptedData = this.dcipher.doFinal(data, offset, length);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  35 */       e.printStackTrace();
/*     */     }
/*  37 */     return deCryptedData;
/*     */   }
/*     */ 
/*     */   public byte[] decrypt(byte[] data) throws Exception
/*     */   {
/*  42 */     byte[] deCryptedData = null;
/*     */     try
/*     */     {
/*  45 */       deCryptedData = this.dcipher.doFinal(data);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  49 */       e.printStackTrace();
/*     */     }
/*  51 */     return deCryptedData;
/*     */   }
/*     */ 
/*     */   public void decrypt(InputStream in, OutputStream out)
/*     */     throws Exception
/*     */   {
/*     */     try
/*     */     {
/*  60 */       int blockSize = this.dcipher.getBlockSize();
/*  61 */       int outputSize = this.dcipher.getOutputSize(blockSize);
/*  62 */       byte[] inBytes = new byte[blockSize];
/*  63 */       byte[] outBytes = new byte[outputSize];
/*  64 */       int inLength = 0;
/*  65 */       boolean more = true;
/*  66 */       while (more)
/*     */       {
/*  68 */         inLength = in.read(inBytes);
/*  69 */         if (inLength == blockSize)
/*     */         {
/*  71 */           int outLength = this.dcipher.update(inBytes, 0, blockSize, outBytes);
/*     */ 
/*  75 */           out.write(outBytes, 0, outLength);
/*     */         }
/*     */ 
/*  78 */         more = false;
/*     */       }
/*  80 */       if (inLength > 0)
/*     */       {
/*  82 */         outBytes = this.dcipher.doFinal(inBytes, 0, inLength);
/*     */       }
/*     */       else
/*  85 */         outBytes = this.dcipher.doFinal();
/*  86 */       out.write(outBytes);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  91 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setKey(Key key)
/*     */   {
/*  97 */     this.key = key;
/*  98 */     createCipher();
/*     */   }
/*     */ 
/*     */   private void createCipher()
/*     */   {
/* 103 */     if (this.key == null)
/* 104 */       return;
/*     */     try
/*     */     {
/* 107 */       DESKeySpec dks = new DESKeySpec(this.key.getKey());
/* 108 */       SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
/* 109 */       SecretKey sk = keyFactory.generateSecret(dks);
/*     */ 
/* 111 */       SecureRandom sr = new SecureRandom();
/* 112 */       this.dcipher = Cipher.getInstance("DES");
/* 113 */       this.dcipher.init(2, sk, sr);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 117 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }