 package com.hisun.atc.common;
 
 import com.hisun.message.HiContext;
 import java.util.HashMap;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class HiCmpParam
 {
   public static String get(HiContext ctx, String cmpName, String paraName)
   {
     HashMap map = (HashMap)ctx.getProperty("@CMP", cmpName);
     if (map == null) {
       return null;
     }
     return ((String)map.get(paraName));
   }
 
   public static boolean getBoolean(HiContext ctx, String cmpName, String paraName) {
     HashMap map = (HashMap)ctx.getProperty("@CMP", cmpName);
     if (map == null) {
       return false;
     }
     String tmp = (String)map.get(paraName);
     return ((StringUtils.equalsIgnoreCase(tmp, "true")) || (StringUtils.equals(tmp, "1")));
   }
 
   public static int getInt(HiContext ctx, String cmpName, String paraName) {
     HashMap map = (HashMap)ctx.getProperty("@CMP", cmpName);
     if (map == null) {
       return -1;
     }
     return NumberUtils.toInt((String)map.get(paraName), -1);
   }
 }