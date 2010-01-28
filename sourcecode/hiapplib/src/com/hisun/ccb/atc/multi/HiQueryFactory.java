 package com.hisun.ccb.atc.multi;
 
 public class HiQueryFactory
 {
   public static HiIMultiQuery getProcessor(HiMultiDTO md)
   {
     if (md.getDataSourceType().equalsIgnoreCase("fileSource"))
     {
       return new HiMultiFileQueryImp(md); }
     if (md.getDataSourceType().equalsIgnoreCase("dbSource"));
     return null;
   }
 }