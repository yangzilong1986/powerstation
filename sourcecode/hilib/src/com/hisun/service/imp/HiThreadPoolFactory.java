/*    */ package com.hisun.service.imp;
/*    */ 
/*    */ import com.hisun.service.IThreadPoolFactory;
/*    */ import com.hisun.util.HiThreadPool;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class HiThreadPoolFactory
/*    */   implements IThreadPoolFactory
/*    */ {
/*    */   private Map pools;
/*    */ 
/*    */   public HiThreadPoolFactory()
/*    */   {
/* 10 */     this.pools = new HashMap(); }
/*    */ 
/*    */   public synchronized HiThreadPool getThreadPool(String id) {
/* 13 */     if (this.pools.containsKey(id)) {
/* 14 */       return ((HiThreadPool)this.pools.get(id));
/*    */     }
/*    */ 
/* 17 */     HiThreadPool pool = new HiThreadPool();
/* 18 */     this.pools.put(id, pool);
/* 19 */     return pool;
/*    */   }
/*    */ }