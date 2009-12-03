/*     */ package com.hisun.term.tcp.nio;
/*     */ 
/*     */ public class HiChannelContextData
/*     */ {
/*     */   int maxFrameSequence;
/*     */   String termLogicAddr;
/*     */   String termIP;
/*     */   int channelStatus;
/*     */   int channelCloseReason;
/*     */   long recentCreateTime;
/*     */   int currentFrameSeq;
/*     */   long recentCloseTime;
/*     */   long recentCommTime;
/*     */   long totalRecvBytes;
/*     */   long totalSendBytes;
/*     */ 
/*     */   public HiChannelContextData()
/*     */   {
/*  10 */     this.maxFrameSequence = 16;
/*     */ 
/*  35 */     this.recentCreateTime = System.currentTimeMillis();
/*     */ 
/*  45 */     this.recentCloseTime = -1L;
/*     */ 
/*  50 */     this.recentCommTime = -1L;
/*     */   }
/*     */ 
/*     */   public int getChannelCloseReason()
/*     */   {
/*  63 */     return this.channelCloseReason;
/*     */   }
/*     */ 
/*     */   public void setChannelCloseReason(int channelCloseReason) {
/*  67 */     this.channelCloseReason = channelCloseReason;
/*     */   }
/*     */ 
/*     */   public int getChannelStatus() {
/*  71 */     return this.channelStatus;
/*     */   }
/*     */ 
/*     */   public void setChannelStatus(int channelStatus) {
/*  75 */     this.channelStatus = channelStatus;
/*     */   }
/*     */ 
/*     */   public synchronized int getCurrentFrameSeq() {
/*  79 */     int seq = this.currentFrameSeq;
/*  80 */     this.currentFrameSeq = ((this.currentFrameSeq + 1) % this.maxFrameSequence);
/*  81 */     return seq;
/*     */   }
/*     */ 
/*     */   public void setCurrentFrameSeq(int currentFrameSeq) {
/*  85 */     this.currentFrameSeq = currentFrameSeq;
/*     */   }
/*     */ 
/*     */   public long getRecentCloseTime() {
/*  89 */     return this.recentCloseTime;
/*     */   }
/*     */ 
/*     */   public void setRecentCloseTime(long recentCloseTime) {
/*  93 */     this.recentCloseTime = recentCloseTime;
/*     */   }
/*     */ 
/*     */   public long getRecentCommTime() {
/*  97 */     return this.recentCommTime;
/*     */   }
/*     */ 
/*     */   public void setRecentCommTime(long recentCommTime) {
/* 101 */     this.recentCommTime = recentCommTime;
/*     */   }
/*     */ 
/*     */   public long getRecentCreateTime() {
/* 105 */     return this.recentCreateTime;
/*     */   }
/*     */ 
/*     */   public void setRecentCreateTime(long recentCreateTime) {
/* 109 */     this.recentCreateTime = recentCreateTime;
/*     */   }
/*     */ 
/*     */   public String getTermIP() {
/* 113 */     return this.termIP;
/*     */   }
/*     */ 
/*     */   public void setTermIP(String termIP) {
/* 117 */     this.termIP = termIP;
/*     */   }
/*     */ 
/*     */   public String getTermLogicAddr() {
/* 121 */     return this.termLogicAddr;
/*     */   }
/*     */ 
/*     */   public void setTermLogicAddr(String termLogicAddr) {
/* 125 */     this.termLogicAddr = termLogicAddr;
/*     */   }
/*     */ 
/*     */   public long getTotalRecvBytes() {
/* 129 */     return this.totalRecvBytes;
/*     */   }
/*     */ 
/*     */   public void setTotalRecvBytes(long totalRecvBytes) {
/* 133 */     this.totalRecvBytes = totalRecvBytes;
/*     */   }
/*     */ 
/*     */   public long getTotalSendBytes() {
/* 137 */     return this.totalSendBytes;
/*     */   }
/*     */ 
/*     */   public void setTotalSendBytes(long totalSendBytes) {
/* 141 */     this.totalSendBytes = totalSendBytes;
/*     */   }
/*     */ 
/*     */   public int getMaxFrameSequence() {
/* 145 */     return this.maxFrameSequence;
/*     */   }
/*     */ 
/*     */   public void setMaxFrameSequence(int maxFrameSequence) {
/* 149 */     this.maxFrameSequence = maxFrameSequence;
/*     */   }
/*     */ }