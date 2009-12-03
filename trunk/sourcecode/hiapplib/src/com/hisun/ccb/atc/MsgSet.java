/*    */ package com.hisun.ccb.atc;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.locks.Lock;
/*    */ import java.util.concurrent.locks.ReentrantLock;
/*    */ 
/*    */ public class MsgSet
/*    */ {
/*    */   private List<MsgBase> msgList;
/*    */   private final Lock lock;
/*    */ 
/*    */   public MsgSet()
/*    */   {
/* 18 */     this.msgList = new ArrayList();
/*    */ 
/* 20 */     this.lock = new ReentrantLock(true);
/*    */   }
/*    */ 
/*    */   public void addMsg(MsgBase msg)
/*    */   {
/* 29 */     this.lock.lock();
/*    */     try
/*    */     {
/* 32 */       this.msgList.add(msg);
/*    */     }
/*    */     finally {
/* 35 */       this.lock.unlock();
/*    */     }
/*    */   }
/*    */ 
/*    */   public Collection<MsgBase> getAllMsg()
/*    */   {
/* 46 */     List list = new ArrayList();
/* 47 */     this.lock.lock();
/*    */     try
/*    */     {
/* 50 */       list.addAll(this.msgList);
/*    */     }
/*    */     finally {
/* 53 */       this.lock.unlock();
/*    */     }
/* 55 */     return list;
/*    */   }
/*    */ 
/*    */   public String getAllMsgByStr()
/*    */   {
/* 65 */     return "";
/*    */   }
/*    */ 
/*    */   public String getMsgError()
/*    */   {
/* 79 */     StringBuilder sb = new StringBuilder();
/* 80 */     this.lock.lock();
/*    */     try
/*    */     {
/* 83 */       for (MsgBase msg : this.msgList)
/*    */       {
/* 91 */         sb.append(msg.getMsg());
/*    */       }
/*    */     }
/*    */     finally {
/* 95 */       this.lock.unlock();
/*    */     }
/* 97 */     return sb.toString();
/*    */   }
/*    */ }