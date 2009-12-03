/*    */ package com.hisun.stat.util;
/*    */ 
/*    */ import com.hisun.util.HiICSProperty;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class HiStats
/*    */ {
/* 13 */   public static Map stats = new HashMap();
/* 14 */   public static IStat dummyStat = new HiDummyStat();
/*    */ 
/*    */   public static IStat getState(String key) { String flag = HiICSProperty.getProperty("sys.stat");
/* 17 */     if (!(StringUtils.equals(flag, "true"))) {
/* 18 */       return dummyStat;
/*    */     }
/*    */ 
/* 21 */     if (stats.containsKey(key)) {
/* 22 */       stat = (HiStat)stats.get(key);
/*    */ 
/* 24 */       return stat;
/*    */     }
/* 26 */     HiStat stat = new HiStat(key);
/* 27 */     stats.put(key, stat);
/* 28 */     return stat;
/*    */   }
/*    */ 
/*    */   public static void removeState(String key) {
/* 32 */     stats.remove(key);
/*    */   }
/*    */ 
/*    */   public static void clearState(String key) {
/* 36 */     stats.remove(key);
/*    */   }
/*    */ 
/*    */   public static void clearAllStat() {
/* 40 */     Collection coll = stats.values();
/* 41 */     Iterator iter = coll.iterator();
/* 42 */     while (iter.hasNext())
/* 43 */       ((HiStat)iter.next()).reset();
/*    */   }
/*    */ 
/*    */   public static String dumpAllStat()
/*    */   {
/* 48 */     Iterator it = stats.values().iterator();
/* 49 */     StringBuffer ret = new StringBuffer();
/* 50 */     while (it.hasNext()) {
/* 51 */       ((HiStat)(HiStat)it.next()).dump(ret);
/*    */     }
/* 53 */     return ret.toString();
/*    */   }
/*    */ }