/*     */ package com.hisun.protocol.tcp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.Socket;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class HiConnection
/*     */ {
/*     */   static final byte USED = -1;
/*     */   static final byte CLOSED = 0;
/*     */   static final byte READY = 1;
/*  99 */   static final Reaper reaper = new Reaper();
/*     */   final HiConnectionPool pool;
/*     */   final Socket socket;
/* 105 */   long expires = -1L;
/*     */ 
/* 199 */   private boolean isMultIP = false;
/*     */ 
/*     */   HiConnection(Socket socket, HiConnectionPool pool)
/*     */   {
/* 108 */     this.socket = socket;
/* 109 */     this.pool = pool;
/*     */   }
/*     */ 
/*     */   public void returnToPool()
/*     */   {
/* 125 */     release(System.currentTimeMillis() + this.pool.expirationTimeout);
/*     */ 
/* 127 */     reaper.registerConnection(this, this.pool.expirationTimeout);
/*     */ 
/* 131 */     if (this.isMultIP)
/* 132 */       synchronized (this) {
/* 133 */         super.notify();
/*     */       }
/*     */     else
/* 136 */       this.pool.notifyConnectionStateChanged();
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/*     */     try
/*     */     {
/* 144 */       this.socket.close();
/*     */     } catch (IOException e) {
/*     */     }
/* 147 */     this.pool.notifyConnectionStateChanged();
/*     */   }
/*     */ 
/*     */   public synchronized Socket getSocket()
/*     */   {
/* 156 */     return this.socket;
/*     */   }
/*     */ 
/*     */   synchronized byte acquire()
/*     */   {
/* 166 */     if (this.socket.isClosed())
/* 167 */       return 0;
/* 168 */     if (this.expires == -1L) {
/* 169 */       return -1;
/*     */     }
/* 171 */     this.expires = -1L;
/* 172 */     return 1;
/*     */   }
/*     */ 
/*     */   synchronized void release(long closeTime) {
/* 176 */     if (this.socket.isClosed())
/*     */     {
/* 178 */       return; }
/* 179 */     if (this.expires == -1L)
/* 180 */       this.expires = closeTime;
/*     */     else
/* 182 */       throw new IllegalStateException("Not currently used");
/*     */   }
/*     */ 
/*     */   synchronized long getCloseTime()
/*     */   {
/* 187 */     return this.expires;
/*     */   }
/*     */ 
/*     */   synchronized boolean isClosed() {
/* 191 */     return this.socket.isClosed();
/*     */   }
/*     */ 
/*     */   synchronized boolean isUsed() {
/* 195 */     return (this.expires == -1L);
/*     */   }
/*     */ 
/*     */   public boolean isMultIP()
/*     */   {
/* 202 */     return this.isMultIP;
/*     */   }
/*     */ 
/*     */   public void setMultIP(boolean isMultIP) {
/* 206 */     this.isMultIP = isMultIP;
/*     */   }
/*     */ 
/*     */   static class Reaper
/*     */   {
/*     */     private final HashSet connections;
/*     */     private final Reap reap;
/*     */     private volatile long sleepTime;
/*     */     private Thread thread;
/*     */ 
/*     */     Reaper()
/*     */     {
/*  24 */       this.connections = new HashSet();
/*     */ 
/*  26 */       this.reap = new Reap();
/*     */ 
/*  28 */       this.sleepTime = 9223372036854775807L;
/*     */     }
/*     */ 
/*     */     void registerConnection(HiConnection conn, long closeDelay)
/*     */     {
/*  74 */       if (conn.isClosed()) {
/*  75 */         return;
/*     */       }
/*  77 */       synchronized (this) {
/*  78 */         this.connections.add(conn);
/*  79 */         if (closeDelay < this.sleepTime) {
/*  80 */           this.sleepTime = closeDelay;
/*     */         }
/*  82 */         if (this.thread == null)
/*  83 */           this.thread = ((Thread)AccessController.doPrivileged(new PrivilegedAction()
/*     */           {
/*     */             public Object run() {
/*  86 */               Thread thread = new Thread(HiConnection.Reaper.this.reap, "Reaper");
/*  87 */               thread.setDaemon(true);
/*  88 */               thread.start();
/*  89 */               return thread;
/*     */             }
/*     */           }));
/*     */       }
/*     */     }
/*     */ 
/*     */     class Reap
/*     */       implements Runnable
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         do
/*     */           try
/*     */           {
/*  36 */             Thread.sleep(HiConnection.Reaper.this.sleepTime);
/*     */           } catch (InterruptedException e) {
/*     */           }
/*  39 */         while (doReap());
/*     */       }
/*     */ 
/*     */       private boolean doReap() {
/*  43 */         boolean result = true;
/*  44 */         long currentTime = System.currentTimeMillis();
/*  45 */         List connToClose = new ArrayList();
/*  46 */         synchronized (HiConnection.Reaper.this) {
/*  47 */           for (Iterator iter = HiConnection.Reaper.this.connections.iterator(); iter.hasNext(); ) {
/*  48 */             HiConnection conn = (HiConnection)iter.next();
/*  49 */             if (conn.getCloseTime() > currentTime)
/*     */               continue;
/*  51 */             switch (conn.acquire())
/*     */             {
/*     */             case -1:
/*     */             case 0:
/*  54 */               break;
/*     */             case 1:
/*  56 */               connToClose.add(conn);
/*     */             }
/*  58 */             iter.remove();
/*     */           }
/*  60 */           if (HiConnection.Reaper.this.connections.isEmpty()) {
/*  61 */             HiConnection.Reaper.access$202(HiConnection.Reaper.this, null);
/*  62 */             result = false;
/*     */           }
/*     */         }
/*  65 */         for (Iterator iter = connToClose.iterator(); iter.hasNext(); ) {
/*  66 */           HiConnection conn = (HiConnection)iter.next();
/*  67 */           conn.close();
/*     */         }
/*  69 */         return result;
/*     */       }
/*     */     }
/*     */   }
/*     */ }