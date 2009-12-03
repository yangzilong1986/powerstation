/*     */ package com.hisun.mon;
/*     */ 
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import org.apache.commons.lang.time.DateUtils;
/*     */ 
/*     */ public class HiRunStatInfoPool
/*     */ {
/*  16 */   private static int MAX_LAST_RUN_TIMES = 20;
/*  17 */   private static int MAX_LAST_RUN_DAYS = 7;
/*     */   private HiRunStatInfo totalRunStatInfo;
/*     */   private HiRunStatInfo curRunStatInfo;
/*     */   private ConcurrentLinkedQueue last7DRunStatInfos;
/*     */   private ConcurrentLinkedQueue last100RunTimeInfos;
/*     */   private static HiRunStatInfoPool instance;
/*     */ 
/*     */   public HiRunStatInfoPool()
/*     */   {
/*  21 */     this.totalRunStatInfo = new HiRunStatInfo();
/*     */ 
/*  25 */     this.curRunStatInfo = new HiRunStatInfo();
/*     */ 
/*  29 */     this.last7DRunStatInfos = new ConcurrentLinkedQueue();
/*     */ 
/*  33 */     this.last100RunTimeInfos = new ConcurrentLinkedQueue();
/*     */   }
/*     */ 
/*     */   public static synchronized HiRunStatInfoPool getInstance() {
/*  37 */     if (instance == null) {
/*  38 */       instance = new HiRunStatInfoPool();
/*     */     }
/*  40 */     return instance;
/*     */   }
/*     */ 
/*     */   public HiRunStatInfo totalRunStatInfo() {
/*  44 */     return this.totalRunStatInfo;
/*     */   }
/*     */ 
/*     */   public HiRunStatInfo curRunStatInfo() {
/*  48 */     return this.curRunStatInfo;
/*     */   }
/*     */ 
/*     */   public Iterator last7DRunStatInfos() {
/*  52 */     return this.last7DRunStatInfos.iterator();
/*     */   }
/*     */ 
/*     */   public Iterator last100RunTimeInfos() {
/*  56 */     return this.last100RunTimeInfos.iterator();
/*     */   }
/*     */ 
/*     */   public void once(String msgId, String id, int elapseTm, long sysTm, String msgTyp, String msgCod, String msg)
/*     */   {
/*  69 */     once(msgId, id, elapseTm, sysTm, msgTyp, msgCod, msg, null);
/*     */   }
/*     */ 
/*     */   public void once(String msgId, String id, int elapseTm, long sysTm, String msgTyp, String msgCod, String msg, HashMap extMap)
/*     */   {
/*  82 */     if (msgTyp == null) {
/*  83 */       msgTyp = "E";
/*     */     }
/*     */ 
/*  86 */     if ("000000".equals(msgCod)) {
/*  87 */       msgTyp = "N";
/*     */     }
/*     */ 
/*  90 */     this.totalRunStatInfo.once(elapseTm, sysTm, msgTyp);
/*  91 */     if (!(DateUtils.isSameDay(new Date(sysTm), new Date(this.curRunStatInfo.lastSysTm))))
/*     */     {
/*  93 */       if (this.last7DRunStatInfos.size() >= MAX_LAST_RUN_DAYS) {
/*  94 */         this.last7DRunStatInfos.poll();
/*     */       }
/*  96 */       this.last7DRunStatInfos.add(this.curRunStatInfo);
/*  97 */       this.curRunStatInfo = new HiRunStatInfo();
/*     */     }
/*  99 */     this.curRunStatInfo.once(elapseTm, sysTm, msgTyp);
/* 100 */     if (this.last100RunTimeInfos.size() >= MAX_LAST_RUN_TIMES) {
/* 101 */       this.last100RunTimeInfos.poll();
/*     */     }
/* 103 */     this.last100RunTimeInfos.add(new HiRunTimeInfo(msgId, id, elapseTm, sysTm, msgTyp, msgCod, msg, extMap));
/*     */   }
/*     */ }