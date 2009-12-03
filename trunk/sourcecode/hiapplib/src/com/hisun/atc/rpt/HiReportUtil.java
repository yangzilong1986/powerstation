/*    */ package com.hisun.atc.rpt;
/*    */ 
/*    */ import com.hisun.util.HiICSProperty;
/*    */ 
/*    */ public class HiReportUtil
/*    */ {
/*    */   public static String getFullPath(String path)
/*    */   {
/* 14 */     if (path.startsWith("/"))
/* 15 */       return path;
/* 16 */     String file = HiICSProperty.getWorkDir();
/* 17 */     if (!(file.endsWith("/")))
/* 18 */       file = file + "/";
/* 19 */     file = file + path;
/* 20 */     return file;
/*    */   }
/*    */ }