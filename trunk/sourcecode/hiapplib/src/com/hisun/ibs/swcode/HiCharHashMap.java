/*    */ package com.hisun.ibs.swcode;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class HiCharHashMap extends HashMap
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public void put(int arg0, int arg1)
/*    */   {
/* 12 */     put(new HiChar(arg0), new HiChar(arg1));
/*    */   }
/*    */ 
/*    */   public HiChar get(int arg0) {
/* 16 */     return get(new HiChar(arg0));
/*    */   }
/*    */ 
/*    */   public HiChar get(HiChar arg0) {
/* 20 */     HiChar tmp = get(arg0);
/* 21 */     if (tmp == null) {
/* 22 */       return new HiChar(65535);
/*    */     }
/* 24 */     return tmp;
/*    */   }
/*    */ }