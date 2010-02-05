 package com.hisun.encrypt;
 
 import java.io.PrintStream;
 import java.security.Key;
 import javax.crypto.Cipher;
 import javax.crypto.spec.SecretKeySpec;
 
 public class DES
 {
   private static String strDefaultKey = "hnzt";
   private Cipher encryptCipher;
   private Cipher decryptCipher;
 
   public static String byteArr2HexStr(byte[] arrB)
     throws Exception
   {
     int iLen = arrB.length;
 
     StringBuffer sb = new StringBuffer(iLen * 2);
     for (int i = 0; i < iLen; ++i) {
       int intTmp = arrB[i];
 
       while (intTmp < 0) {
         intTmp += 256;
       }
 
       if (intTmp < 16) {
         sb.append("0");
       }
       sb.append(Integer.toString(intTmp, 16));
     }
     return sb.toString();
   }
 
   public static byte[] hexStr2ByteArr(String strIn)
     throws Exception
   {
     byte[] arrB = strIn.getBytes();
     int iLen = arrB.length;
 
     byte[] arrOut = new byte[iLen / 2];
     for (int i = 0; i < iLen; i += 2) {
       String strTmp = new String(arrB, i, 2);
       arrOut[(i / 2)] = (byte)Integer.parseInt(strTmp, 16);
     }
     return arrOut;
   }
 
   public DES()
     throws Exception
   {
     this(strDefaultKey);
   }
 
   public DES(String strKey)
     throws Exception
   {
     this.encryptCipher = null;
 
     this.decryptCipher = null;
 
     Key key = getKey(strKey.getBytes());
 
     this.encryptCipher = Cipher.getInstance("DES");
     this.encryptCipher.init(1, key);
 
     this.decryptCipher = Cipher.getInstance("DES");
     this.decryptCipher.init(2, key);
   }
 
   public byte[] encrypt(byte[] arrB)
     throws Exception
   {
     return this.encryptCipher.doFinal(arrB);
   }
 
   public byte[] encrypt(byte[] arrB, int offset, int length) throws Exception {
     return this.encryptCipher.doFinal(arrB, offset, length);
   }
 
   public String encrypt(String strIn)
     throws Exception
   {
     return byteArr2HexStr(encrypt(strIn.getBytes()));
   }
 
   public byte[] decrypt(byte[] arrB)
     throws Exception
   {
     return this.decryptCipher.doFinal(arrB);
   }
 
   public byte[] decrypt(byte[] arrB, int offset, int length) throws Exception {
     return this.decryptCipher.doFinal(arrB, offset, length);
   }
 
   public String decrypt(String strIn)
     throws Exception
   {
     return new String(decrypt(hexStr2ByteArr(strIn)));
   }
 
   private Key getKey(byte[] arrBTmp)
     throws Exception
   {
     byte[] arrB = new byte[8];
 
     for (int i = 0; (i < arrBTmp.length) && (i < arrB.length); ++i) {
       arrB[i] = arrBTmp[i];
     }
 
     Key key = new SecretKeySpec(arrB, "DES");
 
     return key;
   }
 
   public static void main(String[] args)
   {
     DES des;
     String strOriginal = "1111";
     String strOp = "-de";
 
     if (args.length == 2) {
       strOp = args[0];
       strOriginal = args[1];
     } else {
       System.out.println("Wrong Parameter count , try use \"java DES -de|-en  'the string you want to be Encrypted'\"");
 
       System.out.println("Now do Encrypt with \"1111\"");
       try {
         des = new DES();
 
         System.out.println("*****  加密测试 *****");
         des.enTest("1111");
 
         System.out.println("*****  解密测试 *****");
         des.deTest("0fc7648b53e54cfb");
       } catch (Exception ex) {
         ex.printStackTrace();
       }
 
       return;
     }
     try
     {
       if (strOp.equals("-de")) {
         ex = new DES();
         ex.deTest(strOriginal);
       } else if (strOp.equals("-en")) {
         ex = new DES();
         ex.enTest(strOriginal);
       } else {
         System.out.println("Wrong operater , try use \"java DES -de|-en  'the string you want to be Encrypted'\"");
 
         System.out.println("Now do Encrypt with \"1111\"");
       }
     } catch (Exception ex) {
       ex.printStackTrace();
     }
   }
 
   private void enTest(String strOriginal)
   {
     try
     {
       System.out.println("Plain   String: " + strOriginal);
 
       String strEncrypt = encrypt(strOriginal);
       System.out.println("Encrypted String: " + strEncrypt);
     }
     catch (Exception ex) {
       ex.printStackTrace();
     }
   }
 
   private void deTest(String strOriginal)
   {
     try
     {
       System.out.println("Encrypted String: " + strOriginal);
       System.out.println("Encrypted String length =  " + strOriginal.length());
 
       String strPlain = decrypt(strOriginal);
       System.out.println("Plain  String: " + strPlain);
     } catch (Exception ex) {
       ex.printStackTrace();
     }
   }
 }