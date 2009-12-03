/*    */ package com.hisun.stat.util;
/*    */ 
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class HiStat
/*    */   implements IStat
/*    */ {
/*    */   private String key;
/*    */   private int times;
/*    */   private float time;
/*    */   private long size;
/*    */ 
/*    */   HiStat(String key)
/*    */   {
/* 16 */     this.key = key;
/*    */   }
/*    */ 
/*    */   public synchronized void reset() {
/* 20 */     this.times = 0;
/* 21 */     this.time = 0.0F;
/*    */   }
/*    */ 
/*    */   public synchronized void once(long time) {
/* 25 */     this.times += 1;
/* 26 */     this.time += (float)time;
/*    */   }
/*    */ 
/*    */   public synchronized void once(long time, long size) {
/* 30 */     this.times += 1;
/* 31 */     this.time += (float)time;
/* 32 */     this.size += size;
/*    */   }
/*    */ 
/*    */   public synchronized void multi(int times, long time) {
/* 36 */     this.times += times;
/* 37 */     this.time += (float)time;
/*    */   }
/*    */ 
/*    */   public void dump(StringBuffer buf) {
/* 41 */     if (this.times == 0)
/* 42 */       return;
/* 43 */     String tmp = StringUtils.rightPad(this.key, 30, ' ');
/* 44 */     buf.append(tmp);
/* 45 */     buf.append(',');
/* 46 */     tmp = StringUtils.leftPad(String.valueOf(this.times), 10, ' ');
/* 47 */     buf.append(tmp);
/* 48 */     buf.append(',');
/* 49 */     tmp = StringUtils.leftPad(String.valueOf(this.time), 10, ' ');
/* 50 */     buf.append(tmp);
/* 51 */     buf.append(',');
/* 52 */     tmp = StringUtils.leftPad(String.valueOf(this.time / this.times), 10, ' ');
/* 53 */     buf.append(tmp);
/* 54 */     buf.append(',');
/* 55 */     tmp = StringUtils.leftPad(String.valueOf(this.size / this.times), 10, ' ');
/* 56 */     buf.append(tmp);
/* 57 */     buf.append('\n');
/*    */   }
/*    */ }