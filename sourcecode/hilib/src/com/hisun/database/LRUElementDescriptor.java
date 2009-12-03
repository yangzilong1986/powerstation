/*    */ package com.hisun.database;
/*    */ 
/*    */ public class LRUElementDescriptor extends DoubleLinkedListNode
/*    */ {
/*    */   private static final long serialVersionUID = 8249555756363020156L;
/*    */   private Object key;
/*    */ 
/*    */   public LRUElementDescriptor(Object key, Object payloadP)
/*    */   {
/* 44 */     super(payloadP);
/* 45 */     setKey(key);
/*    */   }
/*    */ 
/*    */   public void setKey(Object key)
/*    */   {
/* 54 */     this.key = key;
/*    */   }
/*    */ 
/*    */   public Object getKey()
/*    */   {
/* 62 */     return this.key;
/*    */   }
/*    */ }