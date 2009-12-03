/*    */ package com.hisun.client;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class HiIgnoreHashMap extends HashMap
/*    */ {
/*    */   public Object put(Object key, Object value)
/*    */   {
/*  8 */     return super.put(((String)key).toUpperCase(), value);
/*    */   }
/*    */ 
/*    */   public Object get(Object key) {
/* 12 */     return super.get(((String)key).toUpperCase());
/*    */   }
/*    */ }