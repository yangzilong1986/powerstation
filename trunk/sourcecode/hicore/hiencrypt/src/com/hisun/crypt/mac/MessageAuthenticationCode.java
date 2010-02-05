 package com.hisun.crypt.mac;
 
 import java.security.InvalidKeyException;
 import java.security.NoSuchAlgorithmException;
 import javax.crypto.BadPaddingException;
 import javax.crypto.Cipher;
 import javax.crypto.IllegalBlockSizeException;
 import javax.crypto.NoSuchPaddingException;
 import javax.crypto.SecretKey;
 import javax.crypto.SecretKeyFactory;
 import javax.crypto.spec.DESKeySpec;
 import javax.crypto.spec.SecretKeySpec;
 
 public class MessageAuthenticationCode
 {
   public static byte[] mac(byte[] key, byte[] data)
     throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IllegalStateException
   {
     return mac(key, data, 0, data.length);
   }
 
   public static byte[] mac(byte[] key, byte[] data, int offset, int len)
     throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IllegalStateException
   {
     String Algorithm = "DES";
 
     SecretKey deskey = new SecretKeySpec(key, "DES");
 
     Cipher c1 = Cipher.getInstance("DES");
     c1.init(1, deskey);
     byte[] buf = { 0, 0, 0, 0, 0, 0, 0, 0 };
     for (int i = 0; i < len; ) {
       for (int j = 0; (j < 8) && (i < len); ++j)
       {
         int tmp100_98 = j;
         byte[] tmp100_96 = buf; tmp100_96[tmp100_98] = (byte)(tmp100_96[tmp100_98] ^ data[(offset + i)]);
 
         ++i;
       }
 
       buf = c1.update(buf);
     }
     c1.doFinal();
     return buf;
   }
 
   public static byte[] desEncryption(byte[] key, byte[] data)
     throws NoSuchAlgorithmException, Exception
   {
     String Algorithm = "DES/ECB/NoPadding";
 
     if ((key.length != 8) || (data.length != 8)) {
       throw new IllegalArgumentException("key or data's length != 8");
     }
     DESKeySpec desKS = new DESKeySpec(key);
     SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
     SecretKey deskey = skf.generateSecret(desKS);
 
     Cipher c1 = Cipher.getInstance("DES/ECB/NoPadding");
     c1.init(1, deskey);
 
     byte[] buf = c1.doFinal(data);
 
     byte[] enc_data = new byte[8];
     System.arraycopy(buf, 0, enc_data, 0, 8);
     return enc_data;
   }
 
   public static byte[] desDecryption(byte[] key, byte[] data)
     throws NoSuchAlgorithmException, Exception
   {
     String Algorithm = "DES/ECB/NoPadding";
 
     if ((key.length != 8) || (data.length != 8)) {
       throw new IllegalArgumentException("key's len != 8 or data's length !=8");
     }
 
     SecretKey deskey = new SecretKeySpec(key, "DES");
 
     Cipher c1 = Cipher.getInstance("DES/ECB/NoPadding");
     c1.init(2, deskey);
 
     byte[] decrypted = c1.doFinal(data);
     return decrypted;
   }
 
   protected byte[] encryptByDES(byte[] bytP, byte[] bytKey)
     throws Exception
   {
     DESKeySpec desKS = new DESKeySpec(bytKey);
     SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
     SecretKey sk = skf.generateSecret(desKS);
     Cipher cip = Cipher.getInstance("DES");
     cip.init(1, sk);
     return cip.doFinal(bytP);
   }
 
   protected byte[] decryptByDES(byte[] bytE, byte[] bytKey)
     throws Exception
   {
     DESKeySpec desKS = new DESKeySpec(bytKey);
     SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
     SecretKey sk = skf.generateSecret(desKS);
     Cipher cip = Cipher.getInstance("DES");
     cip.init(2, sk);
     return cip.doFinal(bytE);
   }
 
   public static byte[] des3Encryption(byte[] key, byte[] data)
     throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IllegalStateException
   {
     String Algorithm = "DESede";
 
     SecretKey deskey = new SecretKeySpec(key, "DESede");
 
     Cipher c1 = Cipher.getInstance("DESede");
     c1.init(1, deskey);
     return c1.doFinal(data);
   }
 
   public static byte[] des3Decryption(byte[] key, byte[] data)
     throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IllegalStateException
   {
     String Algorithm = "DESede";
 
     SecretKey deskey = new SecretKeySpec(key, "DESede");
 
     Cipher c1 = Cipher.getInstance("DESede");
     c1.init(2, deskey);
     return c1.doFinal(data);
   }
 }