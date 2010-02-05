 package com.hisun.crypt.des;
 
 import com.hisun.crypt.CryptorFactory;
 import com.hisun.crypt.Decryptor;
 import com.hisun.crypt.Encryptor;
 import com.hisun.crypt.Key;
 
 public class DESCryptorFactory
   implements CryptorFactory
 {
   private DESEncryptor encryptor;
   private DESDecryptor decryptor;
   private DefaultDESKey key;
 
   public Decryptor getDecryptor()
   {
     if (this.decryptor == null)
     {
       this.decryptor = new DESDecryptor();
     }
     return this.decryptor;
   }
 
   public Encryptor getEncryptor() {
     if (this.encryptor == null)
     {
       this.encryptor = new DESEncryptor();
     }
     return this.encryptor;
   }
 
   public String getAlgorithmName()
   {
     return "DES";
   }
 
   public Key getDefaultDecryptKey() {
     if (this.key == null)
       this.key = new DefaultDESKey();
     return this.key;
   }
 
   public Key getDefaultEncryptKey() {
     if (this.key == null)
       this.key = new DefaultDESKey();
     return this.key;
   }
 }