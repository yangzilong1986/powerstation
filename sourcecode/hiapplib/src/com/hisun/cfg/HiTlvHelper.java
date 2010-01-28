 package com.hisun.cfg;
 
 import com.hisun.util.HiConvHelper;
 
 public class HiTlvHelper
 {
   public static String getTag(byte[] bytes, int tag_type)
   {
     switch (tag_type)
     {
     case 1:
       return HiConvHelper.bcd2AscStr(bytes);
     case 2:
       return new String(bytes);
     case 0:
       return Integer.valueOf(HiConvHelper.bcd2AscStr(bytes), 16).toString();
     }
 
     return HiConvHelper.bcd2AscStr(bytes);
   }
 }