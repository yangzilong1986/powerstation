/*    */ package com.hisun.web.service;
/*    */ 
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ 
/*    */ public class HiLogFactoryImpl
/*    */   implements HiLogFactory
/*    */ {
/*    */   private String logFile;
/*    */   private String logLevel;
/*    */ 
/*    */   public HiLogFactoryImpl()
/*    */   {
/*  7 */     this.logFile = "mngmon.trc";
/*  8 */     this.logLevel = "DEBUG"; }
/*    */ 
/*    */   public Logger getLogger(String userid) {
/* 11 */     Logger log = HiLog.getTrcLogger(this.logFile, this.logLevel);
/* 12 */     return log;
/*    */   }
/*    */ 
/*    */   public Logger getLogger()
/*    */   {
/* 20 */     return getLogger("");
/*    */   }
/*    */ 
/*    */   public void setLogFile(String logFile) {
/* 24 */     this.logFile = logFile;
/*    */   }
/*    */ 
/*    */   public void setLogLevel(String logLevel) {
/* 28 */     this.logLevel = logLevel;
/*    */   }
/*    */ }