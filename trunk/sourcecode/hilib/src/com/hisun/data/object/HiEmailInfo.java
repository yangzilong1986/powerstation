/*    */ package com.hisun.data.object;
/*    */ 
/*    */ public class HiEmailInfo
/*    */ {
/*    */   private String host;
/*    */   private int port;
/*    */   private String user;
/*    */   private String from;
/*    */   private String name;
/*    */   private String passwd;
/*    */ 
/*    */   public String getHost()
/*    */   {
/* 36 */     return this.host;
/*    */   }
/*    */ 
/*    */   public void setHost(String host) {
/* 40 */     this.host = host;
/*    */   }
/*    */ 
/*    */   public int getPort() {
/* 44 */     return this.port;
/*    */   }
/*    */ 
/*    */   public void setPort(int port) {
/* 48 */     this.port = port;
/*    */   }
/*    */ 
/*    */   public String getUser() {
/* 52 */     return this.user;
/*    */   }
/*    */ 
/*    */   public void setUser(String user) {
/* 56 */     this.user = user;
/*    */   }
/*    */ 
/*    */   public String getFrom() {
/* 60 */     return this.from;
/*    */   }
/*    */ 
/*    */   public void setFrom(String from) {
/* 64 */     this.from = from;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 68 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 72 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public String getPasswd() {
/* 76 */     return this.passwd;
/*    */   }
/*    */ 
/*    */   public void setPasswd(String passwd) {
/* 80 */     this.passwd = passwd; }
/*    */ 
/*    */   public String toString() {
/* 83 */     return String.format("[%s][%d][%s][%s][%s][%s]", new Object[] { this.host, Integer.valueOf(this.port), this.user, this.from, this.name, this.passwd });
/*    */   }
/*    */ }