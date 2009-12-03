/*    */ package com.hisun.cnaps.common;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class HiRepeatTagManager
/*    */ {
/*    */   public HashMap rTags;
/*    */ 
/*    */   public HiRepeatTagManager()
/*    */   {
/* 17 */     this.rTags = new HashMap(3);
/*    */   }
/*    */ 
/*    */   public synchronized String nextTagEtfName(String etfName)
/*    */   {
/* 22 */     Integer count = (Integer)this.rTags.remove(etfName);
/* 23 */     if (count == null) {
/* 24 */       count = new Integer(1);
/*    */     }
/* 26 */     else if (count != null)
/* 27 */       count = new Integer(count.intValue() + 1);
/* 28 */     this.rTags.put(etfName, count);
/* 29 */     return count.toString();
/*    */   }
/*    */ 
/*    */   public synchronized String getTagCount(String etfName)
/*    */   {
/* 34 */     return this.rTags.get(etfName).toString();
/*    */   }
/*    */ }