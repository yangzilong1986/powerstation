 package com.hisun.atmp.tcp;
 
 import edu.emory.mathcs.backport.java.util.concurrent.ConcurrentHashMap;
 import java.net.Socket;
 import java.util.Enumeration;
 
 class HiSocketPool
   implements Runnable
 {
   ConcurrentHashMap socketPool = new ConcurrentHashMap();
 
   HiSocketPool()
   {
     new Thread(this);
   }
 
   public Socket getSocket(String ip, String port, String tm)
   {
     String key = ip + port + tm;
     return ((Socket)this.socketPool.get(key));
   }
 
   public void putSocket(String ip, String port, String tm, Socket socket) {
     String key = ip + port + tm;
     this.socketPool.put(key, socket);
   }
 
   public void run() {
     Enumeration elements = this.socketPool.elements();
     while (elements.hasMoreElements());
   }
 }