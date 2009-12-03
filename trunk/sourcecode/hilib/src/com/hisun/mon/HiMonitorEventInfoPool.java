/*    */ package com.hisun.mon;
/*    */ 
/*    */ import edu.emory.mathcs.backport.java.util.concurrent.LinkedBlockingQueue;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class HiMonitorEventInfoPool
/*    */ {
/*    */   private static final int MAX_EVENT_STACK = 20;
/*    */   private int stackSize;
/*    */   private LinkedBlockingQueue queue;
/*    */   private static HiMonitorEventInfoPool instance;
/*    */ 
/*    */   public HiMonitorEventInfoPool()
/*    */   {
/* 17 */     this.queue = new LinkedBlockingQueue(); }
/*    */ 
/*    */   public static synchronized HiMonitorEventInfoPool getInstance() {
/* 20 */     if (instance == null) {
/* 21 */       instance = new HiMonitorEventInfoPool();
/*    */ 
/* 23 */       instance.setStackSize(20);
/*    */     }
/* 25 */     return instance;
/*    */   }
/*    */ 
/*    */   public Iterator getEventInfo() {
/* 29 */     return this.queue.iterator();
/*    */   }
/*    */ 
/*    */   public synchronized void addEvent(HiMonitorEventInfo eventInfo) {
/* 33 */     if (this.queue.size() >= this.stackSize) {
/* 34 */       this.queue.poll();
/*    */     }
/* 36 */     this.queue.offer(eventInfo);
/*    */   }
/*    */ 
/*    */   public int getStackSize() {
/* 40 */     return this.stackSize;
/*    */   }
/*    */ 
/*    */   public void setStackSize(int stackSize) {
/* 44 */     this.stackSize = stackSize;
/*    */   }
/*    */ }