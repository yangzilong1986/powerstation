 package com.hisun.protocol.tcp;
 
 import com.hisun.protocol.tcp.parser.HiIPXMLParser;
 
 public class HiTcpBuilder
 {
   private static HiIPXMLParser parser;
 
   public static HiIPXMLParser getXMLParser()
   {
     if (parser == null)
       parser = new HiIPXMLParser();
     return parser;
   }
 }