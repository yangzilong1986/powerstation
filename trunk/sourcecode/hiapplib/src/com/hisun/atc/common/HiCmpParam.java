/*    */ package com.hisun.atc.common;
/*    */ 
/*    */ import com.hisun.message.HiContext;
/*    */ import java.util.HashMap;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.commons.lang.math.NumberUtils;
/*    */ 
/*    */ public class HiCmpParam
/*    */ {
/*    */   public static String get(HiContext ctx, String cmpName, String paraName)
/*    */   {
/* 13 */     HashMap map = (HashMap)ctx.getProperty("@CMP", cmpName);
/* 14 */     if (map == null) {
/* 15 */       return null;
/*    */     }
/* 17 */     return ((String)map.get(paraName));
/*    */   }
/*    */ 
/*    */   public static boolean getBoolean(HiContext ctx, String cmpName, String paraName) {
/* 21 */     HashMap map = (HashMap)ctx.getProperty("@CMP", cmpName);
/* 22 */     if (map == null) {
/* 23 */       return false;
/*    */     }
/* 25 */     String tmp = (String)map.get(paraName);
/* 26 */     return ((StringUtils.equalsIgnoreCase(tmp, "true")) || (StringUtils.equals(tmp, "1")));
/*    */   }
/*    */ 
/*    */   public static int getInt(HiContext ctx, String cmpName, String paraName) {
/* 30 */     HashMap map = (HashMap)ctx.getProperty("@CMP", cmpName);
/* 31 */     if (map == null) {
/* 32 */       return -1;
/*    */     }
/* 34 */     return NumberUtils.toInt((String)map.get(paraName), -1);
/*    */   }
/*    */ }