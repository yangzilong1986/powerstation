/*    */ package com.hisun.common;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class HiIgnoreHashMap extends HashMap
/*    */ {
/*    */   public Object put(Object key, Object value)
/*    */   {
/* 12 */     return super.put(((String)key).toUpperCase(), value);
/*    */   }
/*    */ 
/*    */   public Object get(Object key) {
/* 16 */     return super.get(((String)key).toUpperCase());
/*    */   }
/*    */ }