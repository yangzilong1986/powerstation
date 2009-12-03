/*      */ package com.hisun.message;
/*      */ 
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ 
/*      */ class HiMsgIdManager
/*      */ {
/* 1019 */   private static Map requestIdMap = new HashMap(100);
/*      */ 
/*      */   public static synchronized String getRequestId(String server)
/*      */   {
/* 1028 */     int pos = 0;
/* 1029 */     if (requestIdMap.containsKey(server))
/*      */     {
/* 1031 */       pos = ((Integer)requestIdMap.get(server)).intValue();
/*      */     }
/* 1033 */     requestIdMap.put(server, new Integer(++pos));
/*      */ 
/* 1035 */     return server + StringUtils.leftPad(String.valueOf(pos), 12, '0');
/*      */   }
/*      */ }