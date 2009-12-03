/*     */ package com.hisun.protocol.tcp;
/*     */ 
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketException;
/*     */ import java.rmi.server.RMIClientSocketFactory;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class HiConnectionPool
/*     */ {
/*     */   public static final int DEFAULT_EXPIRATION_TIMEOUT = 60000;
/*     */   public static final int DEFAULT_CAPACITY = 200;
/*  62 */   protected final HashSet connections = new HashSet();
/*     */   protected String host;
/*     */   protected int port;
/*     */   protected Logger log;
/*     */   protected RMIClientSocketFactory socketFactory;
/*  75 */   int expirationTimeout = 60000;
/*     */ 
/*  80 */   private int socketTimeout = 30;
/*     */ 
/*  82 */   private int capacity = 200;
/*     */ 
/* 212 */   private final Map ipconnections = new HashMap();
/*     */ 
/*     */   public HiConnectionPool()
/*     */   {
/*     */   }
/*     */ 
/*     */   public HiConnectionPool(String hostName, int port, RMIClientSocketFactory socketFactory, int expirationTimeout, int capacity)
/*     */   {
/*  90 */     if ((capacity <= 0) || (expirationTimeout < 0)) {
/*  91 */       throw new IllegalArgumentException();
/*     */     }
/*  93 */     this.host = hostName;
/*  94 */     this.port = port;
/*  95 */     this.socketFactory = socketFactory;
/*  96 */     this.expirationTimeout = expirationTimeout;
/*  97 */     this.capacity = capacity;
/*     */   }
/*     */ 
/*     */   private HiConnection findConnection() {
/* 101 */     HiConnection result = null;
/* 102 */     for (Iterator iter = this.connections.iterator(); iter.hasNext(); ) {
/* 103 */       HiConnection conn = (HiConnection)iter.next();
/* 104 */       byte connStatus = conn.acquire();
/* 105 */       if (connStatus == 1) {
/* 106 */         result = conn;
/* 107 */         break; }
/* 108 */       if (connStatus == 0) {
/* 109 */         iter.remove();
/*     */       }
/*     */     }
/* 112 */     return result;
/*     */   }
/*     */ 
/*     */   synchronized void notifyConnectionStateChanged() {
/* 116 */     super.notify();
/*     */   }
/*     */ 
/*     */   public synchronized HiConnection getConnection()
/*     */     throws IOException, InterruptedException
/*     */   {
/* 135 */     checkConnectPermission();
/*     */ 
/* 137 */     HiConnection conn = findConnection();
/* 138 */     while (conn == null) {
/* 139 */       if (this.connections.size() == this.capacity) {
/* 140 */         super.wait();
/* 141 */         conn = findConnection();
/*     */       }
/*     */ 
/* 145 */       Socket socket = new Socket();
/* 146 */       if (this.socketTimeout > 0)
/* 147 */         socket.connect(new InetSocketAddress(this.host, this.port), this.socketTimeout * 1000);
/*     */       else {
/* 149 */         socket.connect(new InetSocketAddress(this.host, this.port));
/*     */       }
/* 151 */       setSocketOptions(socket);
/* 152 */       conn = new HiConnection(socket, this);
/* 153 */       this.connections.add(conn);
/*     */     }
/*     */ 
/* 156 */     return conn;
/*     */   }
/*     */ 
/*     */   void setSocketOptions(Socket socket) throws SocketException {
/* 160 */     socket.setTcpNoDelay(true);
/* 161 */     if (this.socketTimeout > 0)
/* 162 */       socket.setSoTimeout(this.socketTimeout * 1000);
/*     */   }
/*     */ 
/*     */   protected void checkConnectPermission() {
/* 166 */     SecurityManager security = System.getSecurityManager();
/* 167 */     if (security != null)
/* 168 */       security.checkConnect(this.host, this.port);
/*     */   }
/*     */ 
/*     */   public int getCapacity()
/*     */   {
/* 173 */     return this.capacity;
/*     */   }
/*     */ 
/*     */   public void setCapacity(int capacity) {
/* 177 */     if (capacity <= 0) {
/* 178 */       throw new IllegalArgumentException();
/*     */     }
/* 180 */     this.capacity = capacity;
/*     */   }
/*     */ 
/*     */   public int getExpirationTimeout() {
/* 184 */     return this.expirationTimeout;
/*     */   }
/*     */ 
/*     */   public void setExpirationTimeout(int expirationTimeout) {
/* 188 */     if (expirationTimeout < 0) {
/* 189 */       throw new IllegalArgumentException();
/*     */     }
/* 191 */     this.expirationTimeout = expirationTimeout;
/*     */   }
/*     */ 
/*     */   public String getHost() {
/* 195 */     return this.host;
/*     */   }
/*     */ 
/*     */   public void setHost(String hostName) {
/* 199 */     this.host = hostName;
/*     */   }
/*     */ 
/*     */   public int getPort() {
/* 203 */     return this.port;
/*     */   }
/*     */ 
/*     */   public void setPort(int port) {
/* 207 */     this.port = port;
/*     */   }
/*     */ 
/*     */   private HiConnection findConnection(String ip, int port)
/*     */   {
/* 215 */     HiConnection result = null;
/*     */ 
/* 217 */     if (this.ipconnections.containsKey(ip)) {
/* 218 */       HiConnection conn = (HiConnection)this.ipconnections.get(ip);
/*     */ 
/* 220 */       byte connStatus = conn.acquire();
/* 221 */       if (connStatus == 1) {
/* 222 */         if (port == conn.getSocket().getPort())
/* 223 */           result = conn;
/*     */         else
/*     */           try {
/* 226 */             conn.getSocket().close();
/*     */           } catch (IOException e) {
/* 228 */             e.printStackTrace();
/*     */           }
/*     */       }
/* 231 */       else if (connStatus == 0) {
/* 232 */         this.ipconnections.remove(ip);
/*     */       }
/*     */     }
/* 235 */     return result;
/*     */   }
/*     */ 
/*     */   public HiConnection getConnection(String ip, int port)
/*     */     throws IOException, InterruptedException
/*     */   {
/* 242 */     checkConnectPermission();
/*     */ 
/* 244 */     HiConnection conn = findConnection(ip, port);
/* 245 */     while (conn == null) {
/* 246 */       if (this.ipconnections.containsKey(ip)) {
/* 247 */         Object ob = this.ipconnections.get(ip);
/* 248 */         synchronized (ob) {
/* 249 */           ob.wait();
/* 250 */           conn = findConnection(ip, port);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 255 */       Socket socket = new Socket();
/* 256 */       if (this.socketTimeout > 0)
/* 257 */         socket.connect(new InetSocketAddress(ip, port), this.socketTimeout * 1000);
/*     */       else {
/* 259 */         socket.connect(new InetSocketAddress(ip, port));
/*     */       }
/* 261 */       setSocketOptions(socket);
/*     */ 
/* 263 */       conn = new HiConnection(socket, this);
/* 264 */       conn.setMultIP(true);
/* 265 */       this.ipconnections.put(ip, conn);
/*     */     }
/*     */ 
/* 268 */     return conn;
/*     */   }
/*     */ 
/*     */   public void setLog(Logger log) {
/* 272 */     this.log = log;
/*     */   }
/*     */ 
/*     */   public int getTimeOut() {
/* 276 */     return this.socketTimeout;
/*     */   }
/*     */ 
/*     */   public void setTimeOut(int socketTimeout) {
/* 280 */     this.socketTimeout = socketTimeout;
/*     */   }
/*     */ }