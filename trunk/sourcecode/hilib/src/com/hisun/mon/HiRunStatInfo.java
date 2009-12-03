/*     */ package com.hisun.mon;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class HiRunStatInfo
/*     */ {
/*     */   HashMap typeStats;
/*     */   int maxTime;
/*     */   double avgTime;
/*     */   int minTime;
/*     */   long lastSysTm;
/*     */ 
/*     */   public HiRunStatInfo()
/*     */   {
/*  56 */     this.typeStats = new HashMap();
/*     */   }
/*     */ 
/*     */   public HiRunStatInfo cloneObject()
/*     */   {
/*  10 */     HiRunStatInfo info = new HiRunStatInfo();
/*  11 */     info.maxTime = this.maxTime;
/*  12 */     info.minTime = this.minTime;
/*  13 */     info.lastSysTm = this.lastSysTm;
/*  14 */     info.typeStats.putAll(this.typeStats);
/*  15 */     return info;
/*     */   }
/*     */ 
/*     */   public void clear() {
/*  19 */     this.maxTime = 0;
/*  20 */     this.minTime = 0;
/*  21 */     this.avgTime = 0.0D;
/*  22 */     this.typeStats.clear();
/*     */   }
/*     */ 
/*     */   public void once(int elapseTm, long sysTm, String status) {
/*  26 */     if (elapseTm > this.maxTime) {
/*  27 */       this.maxTime = elapseTm;
/*     */     }
/*     */ 
/*  30 */     if (this.minTime == 0) {
/*  31 */       this.minTime = elapseTm;
/*     */     }
/*     */ 
/*  34 */     if (elapseTm < this.minTime) {
/*  35 */       this.minTime = elapseTm;
/*     */     }
/*  37 */     if (this.typeStats.containsKey(status)) {
/*  38 */       long t = ((Long)this.typeStats.get(status)).longValue();
/*  39 */       t += 1L;
/*  40 */       this.typeStats.put(status, Long.valueOf(t));
/*     */     } else {
/*  42 */       this.typeStats.put(status, Long.valueOf(1L));
/*     */     }
/*  44 */     Iterator iter = this.typeStats.values().iterator();
/*  45 */     long sum = 0L;
/*  46 */     while (iter.hasNext()) {
/*  47 */       sum += ((Long)iter.next()).longValue();
/*     */     }
/*  49 */     this.avgTime = (((sum - 1L) * this.avgTime + elapseTm) * 1.0D / sum);
/*  50 */     this.lastSysTm = sysTm;
/*     */   }
/*     */ 
/*     */   public HashMap getProcStatMap()
/*     */   {
/*  74 */     return this.typeStats;
/*     */   }
/*     */ 
/*     */   public void setProcStatMap(HashMap procStatMap) {
/*  78 */     this.typeStats = procStatMap;
/*     */   }
/*     */ 
/*     */   public int getMaxTime() {
/*  82 */     return this.maxTime;
/*     */   }
/*     */ 
/*     */   public void setMaxTime(int maxTime) {
/*  86 */     this.maxTime = maxTime;
/*     */   }
/*     */ 
/*     */   public double getAvgTime() {
/*  90 */     return this.avgTime;
/*     */   }
/*     */ 
/*     */   public void setAvgTime(double avgTime) {
/*  94 */     this.avgTime = avgTime;
/*     */   }
/*     */ 
/*     */   public int getMinTime() {
/*  98 */     return this.minTime;
/*     */   }
/*     */ 
/*     */   public void setMinTime(int minTime) {
/* 102 */     this.minTime = minTime;
/*     */   }
/*     */ 
/*     */   public long getLastSysTm() {
/* 106 */     return this.lastSysTm;
/*     */   }
/*     */ 
/*     */   public void setLastSysTm(long lastSysTm) {
/* 110 */     this.lastSysTm = lastSysTm;
/*     */   }
/*     */ }