 package com.hisun.protocol.tcp.parser;
 
 import java.util.HashMap;
 
 public class HiHostIpTable
 {
   private HashMap map;
 
   public HiHostIpTable()
   {
     this.map = new HashMap(); }
 
   public void add(String hostName, String ip, int port) { this.map.put(hostName, new HiHostIpItem(hostName, ip, port));
   }
 
   public HiHostIpItem getHostIpItem(String hostName) {
     return ((HiHostIpItem)this.map.get(hostName));
   }
 }