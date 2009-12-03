/*    */ package com.hisun.protocol.tcp.parser;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class HiHostIpTable
/*    */ {
/*    */   private HashMap map;
/*    */ 
/*    */   public HiHostIpTable()
/*    */   {
/*  8 */     this.map = new HashMap(); }
/*    */ 
/*    */   public void add(String hostName, String ip, int port) { this.map.put(hostName, new HiHostIpItem(hostName, ip, port));
/*    */   }
/*    */ 
/*    */   public HiHostIpItem getHostIpItem(String hostName) {
/* 14 */     return ((HiHostIpItem)this.map.get(hostName));
/*    */   }
/*    */ }