 package com.hisun.atc.rpt;
 
 import com.hisun.util.HiICSProperty;
 
 public class HiReportUtil
 {
   public static String getFullPath(String path)
   {
     if (path.startsWith("/"))
       return path;
     String file = HiICSProperty.getWorkDir();
     if (!(file.endsWith("/")))
       file = file + "/";
     file = file + path;
     return file;
   }
 }