 package com.hisun.stat.util;
 
 import org.apache.commons.lang.StringUtils;
 
 public class HiStat
   implements IStat
 {
   private String key;
   private int times;
   private float time;
   private long size;
 
   HiStat(String key)
   {
     this.key = key;
   }
 
   public synchronized void reset() {
     this.times = 0;
     this.time = 0.0F;
   }
 
   public synchronized void once(long time) {
     this.times += 1;
     this.time += (float)time;
   }
 
   public synchronized void once(long time, long size) {
     this.times += 1;
     this.time += (float)time;
     this.size += size;
   }
 
   public synchronized void multi(int times, long time) {
     this.times += times;
     this.time += (float)time;
   }
 
   public void dump(StringBuffer buf) {
     if (this.times == 0)
       return;
     String tmp = StringUtils.rightPad(this.key, 30, ' ');
     buf.append(tmp);
     buf.append(',');
     tmp = StringUtils.leftPad(String.valueOf(this.times), 10, ' ');
     buf.append(tmp);
     buf.append(',');
     tmp = StringUtils.leftPad(String.valueOf(this.time), 10, ' ');
     buf.append(tmp);
     buf.append(',');
     tmp = StringUtils.leftPad(String.valueOf(this.time / this.times), 10, ' ');
     buf.append(tmp);
     buf.append(',');
     tmp = StringUtils.leftPad(String.valueOf(this.size / this.times), 10, ' ');
     buf.append(tmp);
     buf.append('\n');
   }
 }