/*    */ package com.hisun.crypt.des;
/*    */ 
/*    */ import com.hisun.crypt.CryptorFactory;
/*    */ import com.hisun.crypt.Decryptor;
/*    */ import com.hisun.crypt.Encryptor;
/*    */ import com.hisun.crypt.Key;
/*    */ 
/*    */ public class DESCryptorFactory
/*    */   implements CryptorFactory
/*    */ {
/*    */   private DESEncryptor encryptor;
/*    */   private DESDecryptor decryptor;
/*    */   private DefaultDESKey key;
/*    */ 
/*    */   public Decryptor getDecryptor()
/*    */   {
/* 13 */     if (this.decryptor == null)
/*    */     {
/* 15 */       this.decryptor = new DESDecryptor();
/*    */     }
/* 17 */     return this.decryptor;
/*    */   }
/*    */ 
/*    */   public Encryptor getEncryptor() {
/* 21 */     if (this.encryptor == null)
/*    */     {
/* 23 */       this.encryptor = new DESEncryptor();
/*    */     }
/* 25 */     return this.encryptor;
/*    */   }
/*    */ 
/*    */   public String getAlgorithmName()
/*    */   {
/* 30 */     return "DES";
/*    */   }
/*    */ 
/*    */   public Key getDefaultDecryptKey() {
/* 34 */     if (this.key == null)
/* 35 */       this.key = new DefaultDESKey();
/* 36 */     return this.key;
/*    */   }
/*    */ 
/*    */   public Key getDefaultEncryptKey() {
/* 40 */     if (this.key == null)
/* 41 */       this.key = new DefaultDESKey();
/* 42 */     return this.key;
/*    */   }
/*    */ }