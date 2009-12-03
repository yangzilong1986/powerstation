/*    */ package com.hisun.server.manage.servlet;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import org.apache.commons.httpclient.HttpClient;
/*    */ import org.apache.commons.httpclient.methods.PostMethod;
/*    */ import org.apache.commons.lang.SystemUtils;
/*    */ 
/*    */ public class HiServerManagerClient
/*    */ {
/*    */   public static void main(String[] args)
/*    */     throws Exception
/*    */   {
/* 23 */     if (args.length < 3) {
/* 24 */       System.out.println("url {start|restart|stop|pause|resume|list} {-s|-a|-g} [name]");
/*    */ 
/* 26 */       System.exit(-1);
/*    */     }
/*    */ 
/* 29 */     HttpClient httpclient = new HttpClient();
/* 30 */     PostMethod method = new PostMethod(args[0]);
/* 31 */     method.addParameter("arg_num", String.valueOf(args.length - 1));
/* 32 */     for (int i = 1; i < args.length; ++i)
/* 33 */       method.addParameter("arg_" + i, args[i]);
/*    */     try
/*    */     {
/* 36 */       httpclient.executeMethod(method);
/*    */     } catch (Exception e) {
/* 38 */       System.out.println(e.getMessage());
/* 39 */       System.exit(-1);
/*    */     }
/* 41 */     int ret = 0;
/* 42 */     if (method.getStatusCode() == 200) {
/* 43 */       byte[] bs = method.getResponseBody();
/* 44 */       System.out.println(new String(bs));
/*    */     }
/*    */     else
/*    */     {
/*    */       String tmp;
/* 46 */       String response = method.getResponseBodyAsString();
/*    */ 
/* 48 */       int i = response.indexOf("<pre>");
/* 49 */       int j = response.indexOf("</pre>");
/*    */ 
/* 51 */       if ((i != -1) && (j != -1) && (i < j))
/* 52 */         tmp = response.substring(i + 5, j - 5);
/*    */       else {
/* 54 */         tmp = response;
/*    */       }
/* 56 */       int k = tmp.indexOf(SystemUtils.LINE_SEPARATOR);
/* 57 */       if (k != -1)
/* 58 */         System.out.println(tmp.substring(0, k));
/*    */       else {
/* 60 */         System.out.println(tmp);
/*    */       }
/* 62 */       System.out.println("see SYS.log file for complete information");
/* 63 */       ret = -1;
/*    */     }
/* 65 */     method.releaseConnection();
/* 66 */     System.exit(ret);
/*    */   }
/*    */ 
/*    */   public static void printHex(byte[] bs) {
/* 70 */     for (int i = 0; i < bs.length; ++i) {
/* 71 */       if (i % 8 == 0)
/* 72 */         System.out.println();
/* 73 */       System.out.print(Integer.toHexString(bs[i] & 0xFF) + " ");
/*    */     }
/*    */   }
/*    */ }