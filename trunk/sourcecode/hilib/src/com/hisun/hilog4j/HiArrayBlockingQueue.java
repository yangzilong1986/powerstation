/*     */ package com.hisun.hilog4j;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.concurrent.ArrayBlockingQueue;
/*     */ 
/*     */ class HiArrayBlockingQueue
/*     */ {
/* 372 */   private HashMap map = new HashMap();
/* 373 */   private ArrayBlockingQueue list = null;
/*     */   private int capacity;
/* 375 */   private Object lock = new Object();
/*     */ 
/*     */   public HiArrayBlockingQueue(int capacity) {
/* 378 */     this.list = new ArrayBlockingQueue(capacity);
/* 379 */     this.capacity = capacity;
/*     */   }
/*     */ 
/*     */   public void put(Object name, Object value) {
/* 383 */     HiNameValuePair nameValuePair = new HiNameValuePair(name, value);
/* 384 */     put(nameValuePair);
/*     */   }
/*     */ 
/*     */   public Object get(Object name) {
/* 388 */     if (!(this.map.containsKey(name))) {
/* 389 */       return null;
/*     */     }
/* 391 */     HiNameValuePair nameValuePair = (HiNameValuePair)this.map.get(name);
/* 392 */     return nameValuePair.value;
/*     */   }
/*     */ 
/*     */   public void remove(Object name) {
/* 396 */     if (!(this.map.containsKey(name))) {
/* 397 */       return;
/*     */     }
/* 399 */     synchronized (this.lock)
/*     */     {
/* 401 */       HiNameValuePair nameValuePair = (HiNameValuePair)this.map.remove(name);
/* 402 */       this.list.remove(nameValuePair);
/* 403 */       if (nameValuePair.value instanceof HiCloseable) {
/* 404 */         HiCloseable closeable = (HiCloseable)nameValuePair.value;
/* 405 */         closeable.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void put(HiNameValuePair nameValuePair) {
/* 411 */     synchronized (this.lock) {
/* 412 */       if (this.list.offer(nameValuePair)) {
/* 413 */         this.map.put(nameValuePair.name, nameValuePair);
/* 414 */         return;
/*     */       }
/* 416 */       for (int i = 0; i < this.capacity / 10; ++i) {
/* 417 */         nameValuePair = (HiNameValuePair)this.list.poll();
/* 418 */         this.map.remove(nameValuePair.name);
/* 419 */         if (nameValuePair.value instanceof HiCloseable) {
/* 420 */           HiCloseable closeable = (HiCloseable)nameValuePair.value;
/* 421 */           closeable.close();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean containsKey(Object name) {
/* 428 */     return this.map.containsKey(name);
/*     */   }
/*     */ 
/*     */   class HiNameValuePair {
/*     */     Object name;
/*     */     Object value;
/*     */ 
/*     */     public HiNameValuePair(Object paramObject1, Object paramObject2) {
/* 436 */       this.name = paramObject1;
/* 437 */       this.value = value;
/*     */     }
/*     */ 
/*     */     public String toString() {
/* 441 */       return this.name + ":" + this.value;
/*     */     }
/*     */   }
/*     */ }