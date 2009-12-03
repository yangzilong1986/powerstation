/*    */ package com.hisun.deploy;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import java.net.HttpURLConnection;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ 
/*    */ public class HiLoadHelper
/*    */ {
/*    */   public static void execute(String requestUrl)
/*    */     throws HiException
/*    */   {
/* 22 */     HttpURLConnection conn = null;
/*    */     try
/*    */     {
/* 25 */       URL url = new URL(requestUrl);
/* 26 */       System.out.println("URL:" + requestUrl);
/* 27 */       conn = (HttpURLConnection)url.openConnection();
/*    */ 
/* 29 */       System.out.println("openConnection .....");
/* 30 */       conn.setDoOutput(true);
/*    */ 
/* 32 */       conn.setRequestMethod("POST");
/* 33 */       System.out.println("code:" + conn.getResponseCode());
/*    */     }
/*    */     catch (MalformedURLException e)
/*    */     {
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/*    */     }
/*    */     finally
/*    */     {
/* 44 */       if (conn != null)
/* 45 */         conn.disconnect();
/*    */     }
/*    */   }
/*    */ }