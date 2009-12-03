/*     */ package com.hisun.engine;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiMessage;
/*     */ import edu.emory.mathcs.backport.java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiMessagePool
/*     */ {
/*     */   private static final String THREAD_HEADER = "ORI_THREAD";
/*  25 */   private final ConcurrentHashMap headers = new ConcurrentHashMap();
/*  26 */   private int memPoolTmOut = 31;
/*  27 */   private int memPoolSize = 300;
/*     */ 
/*     */   public HiMessagePool()
/*     */   {
/*  31 */     String value = HiContext.getCurrentContext().getStrProp("@PARA", "_MEMPOOLTMOUT");
/*     */ 
/*  33 */     this.memPoolTmOut = NumberUtils.toInt(value, 30);
/*  34 */     value = HiContext.getCurrentContext().getStrProp("@PARA", "_MEMPOOLSIZE");
/*     */ 
/*  37 */     this.memPoolSize = NumberUtils.toInt(value, 60);
/*     */   }
/*     */ 
/*     */   public ConcurrentHashMap getPool() {
/*  41 */     return this.headers;
/*     */   }
/*     */ 
/*     */   public static synchronized void setMessagePool() {
/*  45 */     setMessagePool(HiContext.getCurrentContext());
/*     */   }
/*     */ 
/*     */   public static synchronized void setMessagePool(HiContext ctx) {
/*  49 */     if (ctx.containsProperty("ORI_THREAD"))
/*  50 */       return;
/*  51 */     HiMessagePool msgPool = new HiMessagePool();
/*  52 */     ctx.setProperty("ORI_THREAD", msgPool);
/*     */   }
/*     */ 
/*     */   public static HiMessagePool getMessagePool() {
/*  56 */     return getMessagePool(HiContext.getCurrentContext());
/*     */   }
/*     */ 
/*     */   public static HiMessagePool getMessagePool(HiContext ctx) {
/*  60 */     return ((HiMessagePool)ctx.getProperty("ORI_THREAD"));
/*     */   }
/*     */ 
/*     */   public synchronized void saveHeader(Object key, HiMessage msg)
/*     */     throws HiException
/*     */   {
/*  70 */     if (this.headers.containsKey(key)) {
/*  71 */       HiMessage msg1 = (HiMessage)this.headers.get(key);
/*  72 */       Long stm = (Long)msg.getObjectHeadItem("STM");
/*  73 */       boolean timeout = System.currentTimeMillis() - stm.longValue() > this.memPoolTmOut * 1000;
/*  74 */       if (!(timeout)) {
/*  75 */         throw new HiException("213342", "mempool duplicate key:[" + key.toString() + "]");
/*     */       }
/*     */     }
/*  78 */     else if (this.headers.size() >= this.memPoolSize) {
/*  79 */       removeTimeOut();
/*  80 */       if (this.headers.size() >= this.memPoolSize) {
/*  81 */         throw new HiException("213343", "mempool overflow size:[" + key.toString() + "]");
/*     */       }
/*     */     }
/*     */ 
/*  85 */     this.headers.put(key, msg);
/*     */   }
/*     */ 
/*     */   public void removeTimeOut() {
/*  89 */     Iterator it = this.headers.entrySet().iterator();
/*  90 */     while (it.hasNext()) {
/*  91 */       Map.Entry entry = (Map.Entry)it.next();
/*  92 */       HiMessage msg = (HiMessage)entry.getValue();
/*  93 */       Long stm = (Long)msg.getObjectHeadItem("STM");
/*  94 */       boolean timeout = System.currentTimeMillis() - stm.longValue() > this.memPoolTmOut * 1000;
/*  95 */       if (timeout)
/*  96 */         it.remove();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object restoreHeader(Object key, HiMessage msg)
/*     */   {
/* 107 */     HiMessage orimsg = (HiMessage)this.headers.get(key);
/*     */ 
/* 109 */     Logger log = HiLog.getLogger(msg);
/*     */ 
/* 111 */     if (orimsg == null) {
/* 112 */       log.warn("invalid message:" + msg);
/* 113 */       return msg;
/*     */     }
/* 115 */     this.headers.remove(key);
/* 116 */     orimsg.setBody(msg.getBody());
/* 117 */     msg.setHead(orimsg.getHead());
/*     */ 
/* 119 */     return msg;
/*     */   }
/*     */ 
/*     */   public Object getHeader(Object key) {
/* 123 */     return this.headers.get(key);
/*     */   }
/*     */ 
/*     */   public void removeHeader(Object key)
/*     */   {
/* 132 */     this.headers.remove(key);
/*     */   }
/*     */ 
/*     */   public boolean containsHeader(Object key)
/*     */   {
/* 141 */     return this.headers.containsKey(key);
/*     */   }
/*     */ }