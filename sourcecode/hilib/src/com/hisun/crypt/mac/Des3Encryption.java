/*    */ package com.hisun.crypt.mac;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class Des3Encryption
/*    */ {
/*    */   public static final String CHAR_ENCODING = "UTF-8";
/*    */ 
/*    */   public static byte[] encode(byte[] key, byte[] data)
/*    */     throws Exception
/*    */   {
/*  7 */     return MessageAuthenticationCode.des3Encryption(key, data);
/*    */   }
/*    */ 
/*    */   public static byte[] decode(byte[] key, byte[] value) throws Exception {
/* 11 */     return MessageAuthenticationCode.des3Decryption(key, value);
/*    */   }
/*    */ 
/*    */   public static String encode(String key, String data) {
/*    */     try {
/* 16 */       byte[] keyByte = key.getBytes("UTF-8");
/* 17 */       byte[] dataByte = data.getBytes("UTF-8");
/* 18 */       byte[] valueByte = MessageAuthenticationCode.des3Encryption(keyByte, dataByte);
/*    */ 
/* 20 */       String value = new String(Base64.encode(valueByte), "UTF-8");
/* 21 */       return value;
/*    */     } catch (Exception e) {
/* 23 */       e.printStackTrace(); }
/* 24 */     return null;
/*    */   }
/*    */ 
/*    */   public static String decode(String key, String value)
/*    */   {
/*    */     try {
/* 30 */       byte[] keyByte = key.getBytes("UTF-8");
/* 31 */       byte[] valueByte = Base64.decode(value.getBytes("UTF-8"));
/* 32 */       byte[] dataByte = MessageAuthenticationCode.des3Decryption(keyByte, valueByte);
/*    */ 
/* 34 */       String data = new String(dataByte, "UTF-8");
/* 35 */       return data;
/*    */     } catch (Exception e) {
/* 37 */       e.printStackTrace(); }
/* 38 */     return null;
/*    */   }
/*    */ 
/*    */   public static String encode(String value)
/*    */   {
/* 44 */     return encode("a1b2c3d4e5f6g7h8i9j0klmn", value);
/*    */   }
/*    */ 
/*    */   public static String decode(String value)
/*    */   {
/* 49 */     return decode("a1b2c3d4e5f6g7h8i9j0klmn", value);
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) {
/* 53 */     String value = "test111";
/* 54 */     String key = "123456781234567812345678";
/* 55 */     System.out.println(key);
/* 56 */     System.out.println(encode(key, value));
/* 57 */     System.out.println(decode(key, encode(key, value)));
/*    */   }
/*    */ }