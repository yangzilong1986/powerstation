/*    */ package com.hisun.filter;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.framework.event.IServerInitListener;
/*    */ import com.hisun.framework.event.ServerEvent;
/*    */ import com.hisun.message.HiContext;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.pubinterface.IHandlerFilter;
/*    */ import com.hisun.util.HiDBSemaphore;
/*    */ import com.hisun.util.HiThreadSemaphore;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class HiFlowControlFilter
/*    */   implements IHandlerFilter, IServerInitListener
/*    */ {
/*    */   private int maxThreadNum;
/*    */   private int acquireThreadTimeOut;
/*    */   private int maxDBConnNum;
/*    */   private int acquireDBConnTimeOut;
/*    */   private ArrayList msgTypeList;
/*    */   private static final String DEFAULT_MSG_TYPE = "DEFAULT";
/*    */ 
/*    */   public HiFlowControlFilter()
/*    */   {
/* 17 */     this.maxThreadNum = 0;
/*    */ 
/* 19 */     this.acquireThreadTimeOut = -1;
/*    */ 
/* 21 */     this.maxDBConnNum = 0;
/*    */ 
/* 23 */     this.acquireDBConnTimeOut = -1;
/* 24 */     this.msgTypeList = new ArrayList();
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext arg0, IHandler arg1) throws HiException
/*    */   {
/* 29 */     HiThreadSemaphore threadCtrl = (HiThreadSemaphore)arg0.getProperty("_SVR_THREAD_NUM_CTRL");
/*    */ 
/* 31 */     String msgType = arg0.getCurrentMsg().getType();
/* 32 */     if ((!(this.msgTypeList.contains(msgType.toUpperCase()))) && (!(this.msgTypeList.contains("DEFAULT"))))
/*    */     {
/* 34 */       arg1.process(arg0);
/* 35 */       return;
/*    */     }
/*    */ 
/* 38 */     if (threadCtrl != null)
/* 39 */       threadCtrl.acquire();
/*    */     try
/*    */     {
/* 42 */       arg1.process(arg0);
/*    */     } finally {
/* 44 */       if (threadCtrl != null)
/* 45 */         threadCtrl.release();
/*    */     }
/*    */   }
/*    */ 
/*    */   public void serverInit(ServerEvent event) throws HiException
/*    */   {
/* 51 */     HiContext ctx = event.getServerContext();
/* 52 */     if (this.maxDBConnNum != 0) {
/* 53 */       HiDBSemaphore dBConnCtrl = new HiDBSemaphore(this.maxDBConnNum, this.acquireDBConnTimeOut);
/*    */ 
/* 55 */       ctx.setProperty("_SVR_DB_CONN_NUM_CTRL", dBConnCtrl);
/*    */     }
/* 57 */     if (this.maxThreadNum != 0) {
/* 58 */       HiThreadSemaphore threadCtrl = new HiThreadSemaphore(this.maxThreadNum, this.acquireThreadTimeOut);
/*    */ 
/* 60 */       ctx.setProperty("_SVR_THREAD_NUM_CTRL", threadCtrl);
/*    */     }
/*    */   }
/*    */ 
/*    */   public int getAcquireDBConnTimeOut() {
/* 65 */     return this.acquireDBConnTimeOut;
/*    */   }
/*    */ 
/*    */   public void setAcquireDBConnTimeOut(int acquireDBConnTimeOut) {
/* 69 */     this.acquireDBConnTimeOut = acquireDBConnTimeOut;
/*    */   }
/*    */ 
/*    */   public int getAcquireThreadTimeOut() {
/* 73 */     return this.acquireThreadTimeOut;
/*    */   }
/*    */ 
/*    */   public void setAcquireThreadTimeOut(int acquireThreadTimeOut) {
/* 77 */     this.acquireThreadTimeOut = acquireThreadTimeOut;
/*    */   }
/*    */ 
/*    */   public int getMaxDBConnNum() {
/* 81 */     return this.maxDBConnNum;
/*    */   }
/*    */ 
/*    */   public void setMaxDBConnNum(int maxDBConnNum) {
/* 85 */     this.maxDBConnNum = maxDBConnNum;
/*    */   }
/*    */ 
/*    */   public int getMaxThreadNum() {
/* 89 */     return this.maxThreadNum;
/*    */   }
/*    */ 
/*    */   public void setMaxThreadNum(int maxThreadNum) {
/* 93 */     this.maxThreadNum = maxThreadNum;
/*    */   }
/*    */ 
/*    */   public void setMsgType(String msgType) {
/* 97 */     this.msgTypeList.add(msgType.toUpperCase());
/*    */   }
/*    */ }