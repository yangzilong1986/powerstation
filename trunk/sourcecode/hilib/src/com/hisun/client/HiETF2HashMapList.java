/*    */ package com.hisun.client;
/*    */ 
/*    */ import com.hisun.message.HiETF;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class HiETF2HashMapList
/*    */ {
/* 15 */   private HiIgnoreHashMap _map = new HiIgnoreHashMap();
/*    */ 
/*    */   public String toString() { return this._map.toString(); }
/*    */ 
/*    */   public HiIgnoreHashMap map() {
/* 20 */     return this._map; }
/*    */ 
/*    */   public HiETF2HashMapList(HiETF etf) {
/* 23 */     toHashMapList(etf.getChildNodes(), this._map, this._map);
/*    */   }
/*    */ 
/*    */   private void toHashMapList(List list, HiIgnoreHashMap map, HiIgnoreHashMap parentMap) {
/* 27 */     for (int i = 0; i < list.size(); ++i) {
/* 28 */       HiETF node = (HiETF)list.get(i);
/* 29 */       List list1 = node.getChildNodes();
/* 30 */       if (list1.size() != 0)
/*    */       {
/*    */         ArrayList tmpList;
/* 31 */         parentMap = map;
/* 32 */         String tmp = node.getName();
/* 33 */         int idx = tmp.lastIndexOf(95);
/* 34 */         if (idx == -1) {
/* 35 */           idx = tmp.length();
/*    */         }
/* 37 */         tmp = tmp.substring(0, idx);
/*    */ 
/* 39 */         if ((tmpList = (ArrayList)parentMap.get(tmp)) == null) {
/* 40 */           tmpList = new ArrayList();
/* 41 */           parentMap.put(tmp, tmpList);
/*    */         }
/* 43 */         HiIgnoreHashMap tmpMap = new HiIgnoreHashMap();
/* 44 */         tmpList.add(tmpMap);
/* 45 */         toHashMapList(list1, tmpMap, parentMap);
/*    */       } else {
/* 47 */         String value = node.getValue();
/* 48 */         if ("null".equals(value))
/* 49 */           map.put(node.getName(), "");
/*    */         else
/* 51 */           map.put(node.getName(), value);
/*    */       }
/*    */     }
/*    */   }
/*    */ }