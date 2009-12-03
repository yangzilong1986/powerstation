/*    */ package com.hisun.common;
/*    */ 
/*    */ import com.hisun.message.HiETF;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class HiETF2HashMapList
/*    */ {
/* 16 */   private HiIgnoreHashMap _map = new HiIgnoreHashMap();
/*    */ 
/*    */   public String toString() { return this._map.toString(); }
/*    */ 
/*    */   public HiIgnoreHashMap map() {
/* 21 */     return this._map; }
/*    */ 
/*    */   public HiETF2HashMapList(HiETF etf) {
/* 24 */     toHashMapList(etf.getChildNodes(), this._map, this._map);
/*    */   }
/*    */ 
/*    */   private void toHashMapList(List list, HiIgnoreHashMap map, HiIgnoreHashMap parentMap) {
/* 28 */     for (int i = 0; i < list.size(); ++i) {
/* 29 */       HiETF node = (HiETF)list.get(i);
/* 30 */       List list1 = node.getChildNodes();
/* 31 */       if (list1.size() != 0)
/*    */       {
/*    */         ArrayList tmpList;
/* 32 */         parentMap = map;
/* 33 */         String tmp = node.getName();
/* 34 */         int idx = tmp.lastIndexOf(95);
/* 35 */         if (idx == -1) {
/* 36 */           idx = tmp.length();
/*    */         }
/* 38 */         tmp = tmp.substring(0, idx);
/*    */ 
/* 40 */         if ((tmpList = (ArrayList)parentMap.get(tmp)) == null) {
/* 41 */           tmpList = new ArrayList();
/* 42 */           parentMap.put(tmp, tmpList);
/*    */         }
/* 44 */         HiIgnoreHashMap tmpMap = new HiIgnoreHashMap();
/* 45 */         tmpList.add(tmpMap);
/* 46 */         toHashMapList(list1, tmpMap, parentMap);
/*    */       } else {
/* 48 */         String value = node.getValue();
/* 49 */         if ("null".equals(value))
/* 50 */           map.put(node.getName(), "");
/*    */         else
/* 52 */           map.put(node.getName(), value);
/*    */       }
/*    */     }
/*    */   }
/*    */ }