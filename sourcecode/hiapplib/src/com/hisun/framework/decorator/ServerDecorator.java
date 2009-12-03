/*    */ package com.hisun.framework.decorator;
/*    */ 
/*    */ import com.hisun.framework.HiDefaultServer;
/*    */ import com.hisun.framework.filter.LogFilter;
/*    */ import com.hisun.handler.HiInitComponentParamHandler;
/*    */ import com.hisun.handler.HiInitServerParamHandler;
/*    */ import com.hisun.handler.HiInitTransParamHandler;
/*    */ import com.hisun.service.IObjectDecorator;
/*    */ 
/*    */ public class ServerDecorator
/*    */   implements IObjectDecorator
/*    */ {
/*    */   public Object decorate(Object object)
/*    */   {
/* 20 */     return object;
/*    */   }
/*    */ 
/*    */   public Object decorate(Object object, String style) throws Exception {
/* 24 */     if (style.equals("addDeclare"))
/* 25 */       addDeclare((HiDefaultServer)object);
/* 26 */     else if (style.equals("addFilter")) {
/* 27 */       addFilter((HiDefaultServer)object);
/*    */     }
/* 29 */     return object;
/*    */   }
/*    */ 
/*    */   private void addDeclare(HiDefaultServer server) throws Exception {
/* 33 */     server.addDeclare("InitServerParam", new HiInitServerParamHandler());
/*    */   }
/*    */ 
/*    */   private void addFilter(HiDefaultServer server)
/*    */     throws Exception
/*    */   {
/* 39 */     server.addDeclare("InitComponentParam", new HiInitComponentParamHandler());
/* 40 */     server.addDeclare("InitTransParam", new HiInitTransParamHandler());
/* 41 */     server.addFilter(new LogFilter("-----server [" + server.getName() + "] process()", server.getLog()));
/*    */ 
/* 43 */     server.buildProcHandler();
/*    */   }
/*    */ }