 package com.hisun.protocol.tcp.parser;
 
 public class HiHostIpItem
 {
   public String hostName;
   public String ip;
   public int port;
 
   public HiHostIpItem(String hostName, String ip, int port)
   {
     this.hostName = hostName;
     this.ip = ip;
     this.port = port;
   }
 }