 package com.hisun.web.filter;
 
 public class HiConfig
 {
   private String ip;
   private int port;
   private String serverName;
   private String logLevel;
   private String msgType;
 
   public HiConfig()
   {
     this.ip = "";
 
     this.serverName = "MPPTEL";
     this.logLevel = "1";
     this.msgType = "MNGTYPE"; }
 
   public String getIp() {
     return this.ip; }
 
   public void setIp(String ip) {
     this.ip = ip; }
 
   public int getPort() {
     return this.port; }
 
   public void setPort(int port) {
     this.port = port; }
 
   public String getServerName() {
     return this.serverName; }
 
   public void setServerName(String serverName) {
     this.serverName = serverName; }
 
   public String getLogLevel() {
     return this.logLevel; }
 
   public void setLogLevel(String logLevel) {
     this.logLevel = logLevel; }
 
   public String getMsgType() {
     return this.msgType; }
 
   public void setMsgType(String msgType) {
     this.msgType = msgType;
   }
 }