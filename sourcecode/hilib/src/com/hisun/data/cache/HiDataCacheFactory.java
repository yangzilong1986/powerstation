/*    */ package com.hisun.data.cache;
/*    */ 
/*    */ public class HiDataCacheFactory
/*    */ {
/*    */   public HiDataCache createDBCache()
/*    */   {
/* 10 */     return new HiDataCacheDBImpl(); }
/*    */ 
/*    */   public HiDataCache createFileCache() {
/* 13 */     return new HiDataCacheFileImpl();
/*    */   }
/*    */ }