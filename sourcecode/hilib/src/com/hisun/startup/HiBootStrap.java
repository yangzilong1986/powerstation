/*    */ package com.hisun.startup;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class HiBootStrap
/*    */ {
/*    */   public static void main(String[] args)
/*    */     throws HiException
/*    */   {
/* 18 */     if (args.length != 2) {
/* 19 */       System.out.println("USAGE: jbserv SERVER_NAME ATR_FILE");
/* 20 */       return;
/*    */     }
/*    */ 
/* 23 */     HiStartup startup = HiStartup.getInstance(args[0]);
/* 24 */     startup.load(args[0], args[1]);
/* 25 */     await();
/*    */   }
/*    */ 
/*    */   public static void await()
/*    */   {
/*    */     try
/*    */     {
/* 36 */       Thread.sleep(100000L);
/*    */     }
/*    */     catch (InterruptedException ex)
/*    */     {
/*    */     }
/*    */   }
/*    */ }