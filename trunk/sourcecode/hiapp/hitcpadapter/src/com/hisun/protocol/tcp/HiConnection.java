 package com.hisun.protocol.tcp;
 
 import java.io.IOException;
 import java.net.Socket;
 import java.security.AccessController;
 import java.security.PrivilegedAction;
 import java.util.ArrayList;
 import java.util.HashSet;
 import java.util.Iterator;
 import java.util.List;
 
 public class HiConnection
 {
   static final byte USED = -1;
   static final byte CLOSED = 0;
   static final byte READY = 1;
   static final Reaper reaper = new Reaper();
   final HiConnectionPool pool;
   final Socket socket;
   long expires = -1L;
 
   private boolean isMultIP = false;
 
   HiConnection(Socket socket, HiConnectionPool pool)
   {
     this.socket = socket;
     this.pool = pool;
   }
 
   public void returnToPool()
   {
     release(System.currentTimeMillis() + this.pool.expirationTimeout);
 
     reaper.registerConnection(this, this.pool.expirationTimeout);
 
     if (this.isMultIP)
       synchronized (this) {
         super.notify();
       }
     else
       this.pool.notifyConnectionStateChanged();
   }
 
   public void close()
   {
     try
     {
       this.socket.close();
     } catch (IOException e) {
     }
     this.pool.notifyConnectionStateChanged();
   }
 
   public synchronized Socket getSocket()
   {
     return this.socket;
   }
 
   synchronized byte acquire()
   {
     if (this.socket.isClosed())
       return 0;
     if (this.expires == -1L) {
       return -1;
     }
     this.expires = -1L;
     return 1;
   }
 
   synchronized void release(long closeTime) {
     if (this.socket.isClosed())
     {
       return; }
     if (this.expires == -1L)
       this.expires = closeTime;
     else
       throw new IllegalStateException("Not currently used");
   }
 
   synchronized long getCloseTime()
   {
     return this.expires;
   }
 
   synchronized boolean isClosed() {
     return this.socket.isClosed();
   }
 
   synchronized boolean isUsed() {
     return (this.expires == -1L);
   }
 
   public boolean isMultIP()
   {
     return this.isMultIP;
   }
 
   public void setMultIP(boolean isMultIP) {
     this.isMultIP = isMultIP;
   }
 
   static class Reaper
   {
     private final HashSet connections;
     private final Reap reap;
     private volatile long sleepTime;
     private Thread thread;
 
     Reaper()
     {
       this.connections = new HashSet();
 
       this.reap = new Reap();
 
       this.sleepTime = 9223372036854775807L;
     }
 
     void registerConnection(HiConnection conn, long closeDelay)
     {
       if (conn.isClosed()) {
         return;
       }
       synchronized (this) {
         this.connections.add(conn);
         if (closeDelay < this.sleepTime) {
           this.sleepTime = closeDelay;
         }
         if (this.thread == null)
           this.thread = ((Thread)AccessController.doPrivileged(new PrivilegedAction()
           {
             public Object run() {
               Thread thread = new Thread(HiConnection.Reaper.this.reap, "Reaper");
               thread.setDaemon(true);
               thread.start();
               return thread;
             }
           }));
       }
     }
 
     class Reap
       implements Runnable
     {
       public void run()
       {
         do
           try
           {
             Thread.sleep(HiConnection.Reaper.this.sleepTime);
           } catch (InterruptedException e) {
           }
         while (doReap());
       }
 
       private boolean doReap() {
         boolean result = true;
         long currentTime = System.currentTimeMillis();
         List connToClose = new ArrayList();
         synchronized (HiConnection.Reaper.this) {
           for (Iterator iter = HiConnection.Reaper.this.connections.iterator(); iter.hasNext(); ) {
             HiConnection conn = (HiConnection)iter.next();
             if (conn.getCloseTime() > currentTime)
               continue;
             switch (conn.acquire())
             {
             case -1:
             case 0:
               break;
             case 1:
               connToClose.add(conn);
             }
             iter.remove();
           }
           if (HiConnection.Reaper.this.connections.isEmpty()) {
             HiConnection.Reaper.access$202(HiConnection.Reaper.this, null);
             result = false;
           }
         }
         for (Iterator iter = connToClose.iterator(); iter.hasNext(); ) {
           HiConnection conn = (HiConnection)iter.next();
           conn.close();
         }
         return result;
       }
     }
   }
 }