/*    */ package com.hisun.atmp.tcp;
/*    */ 
/*    */ import edu.emory.mathcs.backport.java.util.concurrent.ConcurrentHashMap;
/*    */ import java.net.Socket;
/*    */ import java.util.Enumeration;
/*    */ 
/*    */ class HiSocketPool
/*    */   implements Runnable
/*    */ {
/* 84 */   ConcurrentHashMap socketPool = new ConcurrentHashMap();
/*    */ 
/*    */   HiSocketPool()
/*    */   {
/* 81 */     new Thread(this);
/*    */   }
/*    */ 
/*    */   public Socket getSocket(String ip, String port, String tm)
/*    */   {
/* 86 */     String key = ip + port + tm;
/* 87 */     return ((Socket)this.socketPool.get(key));
/*    */   }
/*    */ 
/*    */   public void putSocket(String ip, String port, String tm, Socket socket) {
/* 91 */     String key = ip + port + tm;
/* 92 */     this.socketPool.put(key, socket);
/*    */   }
/*    */ 
/*    */   public void run() {
/* 96 */     Enumeration elements = this.socketPool.elements();
/* 97 */     while (elements.hasMoreElements());
/*    */   }
/*    */ }