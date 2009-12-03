/*    */ package com.hisun.database;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Map.Entry;
/*    */ 
/*    */ public class LRUMapEntry
/*    */   implements Map.Entry, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -8176116317739129331L;
/*    */   private Object key;
/*    */   private Object value;
/*    */ 
/*    */   public LRUMapEntry(Object key, Object value)
/*    */   {
/* 46 */     this.key = key;
/* 47 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public Object getKey()
/*    */   {
/* 56 */     return this.key;
/*    */   }
/*    */ 
/*    */   public Object getValue()
/*    */   {
/* 65 */     return this.value;
/*    */   }
/*    */ 
/*    */   public Object setValue(Object valueArg)
/*    */   {
/* 74 */     Object old = this.value;
/* 75 */     this.value = valueArg;
/* 76 */     return old;
/*    */   }
/*    */ }