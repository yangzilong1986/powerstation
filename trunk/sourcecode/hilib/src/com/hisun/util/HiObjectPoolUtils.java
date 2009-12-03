/*    */ package com.hisun.util;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import org.apache.commons.pool.impl.GenericObjectPool;
/*    */ 
/*    */ public class HiObjectPoolUtils
/*    */ {
/* 11 */   private static int MAX_ATIVED = 200;
/*    */   private GenericObjectPool stringBufferPool;
/*    */   private GenericObjectPool hashMapPool;
/*    */   private GenericObjectPool arrayListPool;
/*    */   private static HiObjectPoolUtils instance;
/*    */ 
/*    */   public HiObjectPoolUtils()
/*    */   {
/* 12 */     this.stringBufferPool = new GenericObjectPool(new StringBuidlerFactory(), MAX_ATIVED, 2, 10L);
/*    */ 
/* 15 */     this.hashMapPool = new GenericObjectPool(new HashMapFactory(), MAX_ATIVED, 2, 10L);
/*    */ 
/* 18 */     this.arrayListPool = new GenericObjectPool(new ArrayListFactory(), MAX_ATIVED, 2, 10L);
/*    */   }
/*    */ 
/*    */   public static synchronized HiObjectPoolUtils getInstance()
/*    */   {
/* 23 */     if (instance == null) {
/* 24 */       instance = new HiObjectPoolUtils();
/*    */     }
/* 26 */     return instance;
/*    */   }
/*    */ 
/*    */   public StringBuilder borrowStringBuilder() {
/*    */     try {
/* 31 */       return ((StringBuilder)this.stringBufferPool.borrowObject());
/*    */     }
/*    */     catch (Exception e) {
/* 34 */       e.printStackTrace(); }
/* 35 */     return null;
/*    */   }
/*    */ 
/*    */   public void returnStringBuilder(StringBuilder arg0)
/*    */   {
/*    */     try {
/* 41 */       this.stringBufferPool.returnObject(arg0);
/*    */     }
/*    */     catch (Exception e) {
/* 44 */       e.printStackTrace(); }
/*    */   }
/*    */ 
/*    */   public HashMap borrowHashMap() {
/*    */     try {
/* 49 */       return ((HashMap)this.hashMapPool.borrowObject());
/*    */     }
/*    */     catch (Exception e) {
/* 52 */       e.printStackTrace(); }
/* 53 */     return null;
/*    */   }
/*    */ 
/*    */   public void returnHashMap(HashMap arg0)
/*    */   {
/*    */     try {
/* 59 */       this.hashMapPool.returnObject(arg0);
/*    */     }
/*    */     catch (Exception e) {
/* 62 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public ArrayList borrowArrayList() {
/*    */     try {
/* 68 */       return ((ArrayList)this.arrayListPool.borrowObject());
/*    */     }
/*    */     catch (Exception e) {
/* 71 */       e.printStackTrace(); }
/* 72 */     return null;
/*    */   }
/*    */ 
/*    */   public void returnArrayList(ArrayList arg0)
/*    */   {
/*    */     try {
/* 78 */       this.arrayListPool.returnObject(arg0);
/*    */     }
/*    */     catch (Exception e) {
/* 81 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }