/*    */ package com.hisun.server.manage.servlet;
/*    */ 
/*    */ import com.hisun.register.HiRegisterService;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class HiServerLog
/*    */ {
/*  6 */   private static final String[] _modes = { "open", "close" };
/*    */ 
/*    */   public static void main(String[] args) throws Exception { if (args.length != 2) {
/*  9 */       System.out.println("{open|close} name");
/*    */ 
/* 11 */       return;
/*    */     }
/*    */ 
/* 14 */     int k = 0;
/* 15 */     for (k = 0; k < _modes.length; ++k) {
/* 16 */       if (_modes.equals(args[0])) {
/*    */         break;
/*    */       }
/*    */     }
/* 20 */     if (k > _modes.length) {
/* 21 */       throw new Exception("invalid mode:[" + args[0] + "]");
/*    */     }
/*    */     try
/*    */     {
/* 25 */       if ("open".equalsIgnoreCase(args[0]))
/* 26 */         HiRegisterService.setLogSwitch(args[1], "1");
/*    */       else
/* 28 */         HiRegisterService.setLogSwitch(args[1], "0");
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/*    */     }
/*    */   }
/*    */ }