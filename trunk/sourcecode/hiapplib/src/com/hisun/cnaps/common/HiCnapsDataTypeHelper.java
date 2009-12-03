/*    */ package com.hisun.cnaps.common;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ 
/*    */ public class HiCnapsDataTypeHelper
/*    */ {
/*    */   public static String fullIfNeed(int type, int length, String value)
/*    */   {
/* 17 */     if (length == -1)
/* 18 */       return value;
/* 19 */     if (type == -1)
/* 20 */       return value;
/* 21 */     int bufferLen = value.getBytes().length;
/* 22 */     if (bufferLen == length)
/* 23 */       return value;
/* 24 */     if (bufferLen > length)
/* 25 */       value = value.substring(0, length);
/* 26 */     switch (type)
/*    */     {
/*    */     case 110:
/* 29 */       return lFullByte(value, bufferLen, length, '0');
/*    */     case 120:
/* 32 */       return rFullByte(value, bufferLen, length, ' ');
/*    */     case 103:
/* 35 */       return rFullByte(value, value.length(), length / 2, 12288);
/*    */     }
/* 37 */     return value;
/*    */   }
/*    */ 
/*    */   public static String lFullByte(String buffer, int bufferLen, int length, char c)
/*    */   {
/* 42 */     char[] cc = new char[length - bufferLen];
/* 43 */     Arrays.fill(cc, c);
/* 44 */     StringBuffer sb = new StringBuffer(buffer);
/* 45 */     sb.insert(0, cc);
/* 46 */     return sb.toString();
/*    */   }
/*    */ 
/*    */   public static String rFullByte(String buffer, int bufferLen, int length, char c)
/*    */   {
/* 51 */     char[] cc = new char[length - bufferLen];
/* 52 */     Arrays.fill(cc, c);
/* 53 */     StringBuffer sb = new StringBuffer(buffer);
/* 54 */     sb.insert(sb.length(), cc);
/* 55 */     return sb.toString();
/*    */   }
/*    */ }