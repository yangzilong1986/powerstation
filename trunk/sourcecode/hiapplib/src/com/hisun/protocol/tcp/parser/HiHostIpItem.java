/*   */ package com.hisun.protocol.tcp.parser;
/*   */ 
/*   */ public class HiHostIpItem
/*   */ {
/*   */   public String hostName;
/*   */   public String ip;
/*   */   public int port;
/*   */ 
/*   */   public HiHostIpItem(String hostName, String ip, int port)
/*   */   {
/* 5 */     this.hostName = hostName;
/* 6 */     this.ip = ip;
/* 7 */     this.port = port;
/*   */   }
/*   */ }