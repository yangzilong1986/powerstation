 package com.hisun.protocol.tcp;
 
 import com.hisun.hilog4j.Logger;
 import java.io.IOException;
 import java.net.InetSocketAddress;
 import java.net.Socket;
 import java.net.SocketException;
 import java.rmi.server.RMIClientSocketFactory;
 import java.util.HashMap;
 import java.util.HashSet;
 import java.util.Iterator;
 import java.util.Map;
 
 public class HiConnectionPool
 {
   public static final int DEFAULT_EXPIRATION_TIMEOUT = 60000;
   public static final int DEFAULT_CAPACITY = 200;
   protected final HashSet connections = new HashSet();
   protected String host;
   protected int port;
   protected Logger log;
   protected RMIClientSocketFactory socketFactory;
   int expirationTimeout = 60000;
 
   private int socketTimeout = 30;
 
   private int capacity = 200;
 
   private final Map ipconnections = new HashMap();
 
   public HiConnectionPool()
   {
   }
 
   public HiConnectionPool(String hostName, int port, RMIClientSocketFactory socketFactory, int expirationTimeout, int capacity)
   {
     if ((capacity <= 0) || (expirationTimeout < 0)) {
       throw new IllegalArgumentException();
     }
     this.host = hostName;
     this.port = port;
     this.socketFactory = socketFactory;
     this.expirationTimeout = expirationTimeout;
     this.capacity = capacity;
   }
 
   private HiConnection findConnection() {
     HiConnection result = null;
     for (Iterator iter = this.connections.iterator(); iter.hasNext(); ) {
       HiConnection conn = (HiConnection)iter.next();
       byte connStatus = conn.acquire();
       if (connStatus == 1) {
         result = conn;
         break; }
       if (connStatus == 0) {
         iter.remove();
       }
     }
     return result;
   }
 
   synchronized void notifyConnectionStateChanged() {
     super.notify();
   }
 
   public synchronized HiConnection getConnection()
     throws IOException, InterruptedException
   {
     checkConnectPermission();
 
     HiConnection conn = findConnection();
     while (conn == null) {
       if (this.connections.size() == this.capacity) {
         super.wait();
         conn = findConnection();
       }
 
       Socket socket = new Socket();
       if (this.socketTimeout > 0)
         socket.connect(new InetSocketAddress(this.host, this.port), this.socketTimeout * 1000);
       else {
         socket.connect(new InetSocketAddress(this.host, this.port));
       }
       setSocketOptions(socket);
       conn = new HiConnection(socket, this);
       this.connections.add(conn);
     }
 
     return conn;
   }
 
   void setSocketOptions(Socket socket) throws SocketException {
     socket.setTcpNoDelay(true);
     if (this.socketTimeout > 0)
       socket.setSoTimeout(this.socketTimeout * 1000);
   }
 
   protected void checkConnectPermission() {
     SecurityManager security = System.getSecurityManager();
     if (security != null)
       security.checkConnect(this.host, this.port);
   }
 
   public int getCapacity()
   {
     return this.capacity;
   }
 
   public void setCapacity(int capacity) {
     if (capacity <= 0) {
       throw new IllegalArgumentException();
     }
     this.capacity = capacity;
   }
 
   public int getExpirationTimeout() {
     return this.expirationTimeout;
   }
 
   public void setExpirationTimeout(int expirationTimeout) {
     if (expirationTimeout < 0) {
       throw new IllegalArgumentException();
     }
     this.expirationTimeout = expirationTimeout;
   }
 
   public String getHost() {
     return this.host;
   }
 
   public void setHost(String hostName) {
     this.host = hostName;
   }
 
   public int getPort() {
     return this.port;
   }
 
   public void setPort(int port) {
     this.port = port;
   }
 
   private HiConnection findConnection(String ip, int port)
   {
     HiConnection result = null;
 
     if (this.ipconnections.containsKey(ip)) {
       HiConnection conn = (HiConnection)this.ipconnections.get(ip);
 
       byte connStatus = conn.acquire();
       if (connStatus == 1) {
         if (port == conn.getSocket().getPort())
           result = conn;
         else
           try {
             conn.getSocket().close();
           } catch (IOException e) {
             e.printStackTrace();
           }
       }
       else if (connStatus == 0) {
         this.ipconnections.remove(ip);
       }
     }
     return result;
   }
 
   public HiConnection getConnection(String ip, int port)
     throws IOException, InterruptedException
   {
     checkConnectPermission();
 
     HiConnection conn = findConnection(ip, port);
     while (conn == null) {
       if (this.ipconnections.containsKey(ip)) {
         Object ob = this.ipconnections.get(ip);
         synchronized (ob) {
           ob.wait();
           conn = findConnection(ip, port);
         }
 
       }
 
       Socket socket = new Socket();
       if (this.socketTimeout > 0)
         socket.connect(new InetSocketAddress(ip, port), this.socketTimeout * 1000);
       else {
         socket.connect(new InetSocketAddress(ip, port));
       }
       setSocketOptions(socket);
 
       conn = new HiConnection(socket, this);
       conn.setMultIP(true);
       this.ipconnections.put(ip, conn);
     }
 
     return conn;
   }
 
   public void setLog(Logger log) {
     this.log = log;
   }
 
   public int getTimeOut() {
     return this.socketTimeout;
   }
 
   public void setTimeOut(int socketTimeout) {
     this.socketTimeout = socketTimeout;
   }
 }