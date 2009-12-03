/*    */ package com.hisun.web.filter;
/*    */ 
/*    */ public class HiConfig
/*    */ {
/*    */   private String ip;
/*    */   private int port;
/*    */   private String serverName;
/*    */   private String logLevel;
/*    */   private String msgType;
/*    */ 
/*    */   public HiConfig()
/*    */   {
/*  4 */     this.ip = "";
/*    */ 
/*  6 */     this.serverName = "MPPTEL";
/*  7 */     this.logLevel = "1";
/*  8 */     this.msgType = "MNGTYPE"; }
/*    */ 
/*    */   public String getIp() {
/* 11 */     return this.ip; }
/*    */ 
/*    */   public void setIp(String ip) {
/* 14 */     this.ip = ip; }
/*    */ 
/*    */   public int getPort() {
/* 17 */     return this.port; }
/*    */ 
/*    */   public void setPort(int port) {
/* 20 */     this.port = port; }
/*    */ 
/*    */   public String getServerName() {
/* 23 */     return this.serverName; }
/*    */ 
/*    */   public void setServerName(String serverName) {
/* 26 */     this.serverName = serverName; }
/*    */ 
/*    */   public String getLogLevel() {
/* 29 */     return this.logLevel; }
/*    */ 
/*    */   public void setLogLevel(String logLevel) {
/* 32 */     this.logLevel = logLevel; }
/*    */ 
/*    */   public String getMsgType() {
/* 35 */     return this.msgType; }
/*    */ 
/*    */   public void setMsgType(String msgType) {
/* 38 */     this.msgType = msgType;
/*    */   }
/*    */ }