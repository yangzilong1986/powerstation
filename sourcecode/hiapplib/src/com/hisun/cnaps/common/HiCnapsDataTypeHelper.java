 package com.hisun.cnaps.common;
 
 import java.util.Arrays;
 
 public class HiCnapsDataTypeHelper
 {
   public static String fullIfNeed(int type, int length, String value)
   {
     if (length == -1)
       return value;
     if (type == -1)
       return value;
     int bufferLen = value.getBytes().length;
     if (bufferLen == length)
       return value;
     if (bufferLen > length)
       value = value.substring(0, length);
     switch (type)
     {
     case 110:
       return lFullByte(value, bufferLen, length, '0');
     case 120:
       return rFullByte(value, bufferLen, length, ' ');
     case 103:
       return rFullByte(value, value.length(), length / 2, 12288);
     }
     return value;
   }
 
   public static String lFullByte(String buffer, int bufferLen, int length, char c)
   {
     char[] cc = new char[length - bufferLen];
     Arrays.fill(cc, c);
     StringBuffer sb = new StringBuffer(buffer);
     sb.insert(0, cc);
     return sb.toString();
   }
 
   public static String rFullByte(String buffer, int bufferLen, int length, char c)
   {
     char[] cc = new char[length - bufferLen];
     Arrays.fill(cc, c);
     StringBuffer sb = new StringBuffer(buffer);
     sb.insert(sb.length(), cc);
     return sb.toString();
   }
 }