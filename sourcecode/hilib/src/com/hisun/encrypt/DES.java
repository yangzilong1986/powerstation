/*     */ package com.hisun.encrypt;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.security.Key;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ 
/*     */ public class DES
/*     */ {
/*  16 */   private static String strDefaultKey = "hnzt";
/*     */   private Cipher encryptCipher;
/*     */   private Cipher decryptCipher;
/*     */ 
/*     */   public static String byteArr2HexStr(byte[] arrB)
/*     */     throws Exception
/*     */   {
/*  33 */     int iLen = arrB.length;
/*     */ 
/*  35 */     StringBuffer sb = new StringBuffer(iLen * 2);
/*  36 */     for (int i = 0; i < iLen; ++i) {
/*  37 */       int intTmp = arrB[i];
/*     */ 
/*  39 */       while (intTmp < 0) {
/*  40 */         intTmp += 256;
/*     */       }
/*     */ 
/*  43 */       if (intTmp < 16) {
/*  44 */         sb.append("0");
/*     */       }
/*  46 */       sb.append(Integer.toString(intTmp, 16));
/*     */     }
/*  48 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static byte[] hexStr2ByteArr(String strIn)
/*     */     throws Exception
/*     */   {
/*  63 */     byte[] arrB = strIn.getBytes();
/*  64 */     int iLen = arrB.length;
/*     */ 
/*  67 */     byte[] arrOut = new byte[iLen / 2];
/*  68 */     for (int i = 0; i < iLen; i += 2) {
/*  69 */       String strTmp = new String(arrB, i, 2);
/*  70 */       arrOut[(i / 2)] = (byte)Integer.parseInt(strTmp, 16);
/*     */     }
/*  72 */     return arrOut;
/*     */   }
/*     */ 
/*     */   public DES()
/*     */     throws Exception
/*     */   {
/*  81 */     this(strDefaultKey);
/*     */   }
/*     */ 
/*     */   public DES(String strKey)
/*     */     throws Exception
/*     */   {
/*  18 */     this.encryptCipher = null;
/*     */ 
/*  20 */     this.decryptCipher = null;
/*     */ 
/*  93 */     Key key = getKey(strKey.getBytes());
/*     */ 
/*  95 */     this.encryptCipher = Cipher.getInstance("DES");
/*  96 */     this.encryptCipher.init(1, key);
/*     */ 
/*  98 */     this.decryptCipher = Cipher.getInstance("DES");
/*  99 */     this.decryptCipher.init(2, key);
/*     */   }
/*     */ 
/*     */   public byte[] encrypt(byte[] arrB)
/*     */     throws Exception
/*     */   {
/* 111 */     return this.encryptCipher.doFinal(arrB);
/*     */   }
/*     */ 
/*     */   public byte[] encrypt(byte[] arrB, int offset, int length) throws Exception {
/* 115 */     return this.encryptCipher.doFinal(arrB, offset, length);
/*     */   }
/*     */ 
/*     */   public String encrypt(String strIn)
/*     */     throws Exception
/*     */   {
/* 127 */     return byteArr2HexStr(encrypt(strIn.getBytes()));
/*     */   }
/*     */ 
/*     */   public byte[] decrypt(byte[] arrB)
/*     */     throws Exception
/*     */   {
/* 139 */     return this.decryptCipher.doFinal(arrB);
/*     */   }
/*     */ 
/*     */   public byte[] decrypt(byte[] arrB, int offset, int length) throws Exception {
/* 143 */     return this.decryptCipher.doFinal(arrB, offset, length);
/*     */   }
/*     */ 
/*     */   public String decrypt(String strIn)
/*     */     throws Exception
/*     */   {
/* 155 */     return new String(decrypt(hexStr2ByteArr(strIn)));
/*     */   }
/*     */ 
/*     */   private Key getKey(byte[] arrBTmp)
/*     */     throws Exception
/*     */   {
/* 168 */     byte[] arrB = new byte[8];
/*     */ 
/* 171 */     for (int i = 0; (i < arrBTmp.length) && (i < arrB.length); ++i) {
/* 172 */       arrB[i] = arrBTmp[i];
/*     */     }
/*     */ 
/* 176 */     Key key = new SecretKeySpec(arrB, "DES");
/*     */ 
/* 178 */     return key;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */     DES des;
/* 187 */     String strOriginal = "1111";
/* 188 */     String strOp = "-de";
/*     */ 
/* 191 */     if (args.length == 2) {
/* 192 */       strOp = args[0];
/* 193 */       strOriginal = args[1];
/*     */     } else {
/* 195 */       System.out.println("Wrong Parameter count , try use \"java DES -de|-en  'the string you want to be Encrypted'\"");
/*     */ 
/* 197 */       System.out.println("Now do Encrypt with \"1111\"");
/*     */       try {
/* 199 */         des = new DES();
/*     */ 
/* 201 */         System.out.println("*****  加密测试 *****");
/* 202 */         des.enTest("1111");
/*     */ 
/* 204 */         System.out.println("*****  解密测试 *****");
/* 205 */         des.deTest("0fc7648b53e54cfb");
/*     */       } catch (Exception ex) {
/* 207 */         ex.printStackTrace();
/*     */       }
/*     */ 
/* 210 */       return;
/*     */     }
/*     */     try
/*     */     {
/* 214 */       if (strOp.equals("-de")) {
/* 215 */         ex = new DES();
/* 216 */         ex.deTest(strOriginal);
/* 217 */       } else if (strOp.equals("-en")) {
/* 218 */         ex = new DES();
/* 219 */         ex.enTest(strOriginal);
/*     */       } else {
/* 221 */         System.out.println("Wrong operater , try use \"java DES -de|-en  'the string you want to be Encrypted'\"");
/*     */ 
/* 223 */         System.out.println("Now do Encrypt with \"1111\"");
/*     */       }
/*     */     } catch (Exception ex) {
/* 226 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void enTest(String strOriginal)
/*     */   {
/*     */     try
/*     */     {
/* 235 */       System.out.println("Plain   String: " + strOriginal);
/*     */ 
/* 237 */       String strEncrypt = encrypt(strOriginal);
/* 238 */       System.out.println("Encrypted String: " + strEncrypt);
/*     */     }
/*     */     catch (Exception ex) {
/* 241 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void deTest(String strOriginal)
/*     */   {
/*     */     try
/*     */     {
/* 250 */       System.out.println("Encrypted String: " + strOriginal);
/* 251 */       System.out.println("Encrypted String length =  " + strOriginal.length());
/*     */ 
/* 253 */       String strPlain = decrypt(strOriginal);
/* 254 */       System.out.println("Plain  String: " + strPlain);
/*     */     } catch (Exception ex) {
/* 256 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ }