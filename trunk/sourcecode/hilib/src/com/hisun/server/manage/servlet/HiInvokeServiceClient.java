/*    */ package com.hisun.server.manage.servlet;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import org.apache.commons.httpclient.HttpClient;
/*    */ import org.apache.commons.httpclient.methods.PostMethod;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.commons.lang.SystemUtils;
/*    */ 
/*    */ public class HiInvokeServiceClient
/*    */ {
/*    */   public static void main(String[] args)
/*    */     throws Exception
/*    */   {
/* 11 */     HttpClient httpclient = new HttpClient();
/* 12 */     PostMethod method = new PostMethod(args[0]);
/*    */     try {
/* 14 */       for (int i = 1; i < args.length; ++i) {
/* 15 */         String[] nameValuePair = StringUtils.split(args[i], "=");
/* 16 */         if (nameValuePair.length != 2) {
/*    */           continue;
/*    */         }
/* 19 */         method.addParameter(nameValuePair[0], nameValuePair[1]);
/*    */       }
/* 21 */       httpclient.executeMethod(method);
/*    */     } catch (Exception e) {
/* 23 */       System.out.println(e);
/* 24 */       System.exit(-1);
/*    */     }
/* 26 */     int ret = 0;
/* 27 */     if (method.getStatusCode() == 200) {
/* 28 */       byte[] bs = method.getResponseBody();
/* 29 */       System.out.println(new String(bs));
/*    */     }
/*    */     else
/*    */     {
/*    */       String tmp;
/* 31 */       String response = method.getResponseBodyAsString();
/*    */ 
/* 33 */       int i = response.indexOf("<pre>");
/* 34 */       int j = response.indexOf("</pre>");
/*    */ 
/* 36 */       if ((i != -1) && (j != -1) && (i < j))
/* 37 */         tmp = response.substring(i + 5, j - 5);
/*    */       else {
/* 39 */         tmp = response;
/*    */       }
/* 41 */       int k = tmp.indexOf(SystemUtils.LINE_SEPARATOR);
/* 42 */       if (k != -1)
/* 43 */         System.out.println(tmp.substring(0, k));
/*    */       else {
/* 45 */         System.out.println(tmp);
/*    */       }
/* 47 */       System.out.println("see SYS.log file for complete information");
/* 48 */       ret = -1;
/*    */     }
/* 50 */     method.releaseConnection();
/* 51 */     System.exit(ret);
/*    */   }
/*    */ }