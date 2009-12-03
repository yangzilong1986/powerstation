/*    */ package com.hisun.framework.decorator;
/*    */ 
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.service.IObjectDecorator;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class HandlerDecorator
/*    */   implements IObjectDecorator
/*    */ {
/*    */   public Object decorate(Object object)
/*    */   {
/* 16 */     System.out.println("decorate handler");
/* 17 */     return object;
/*    */   }
/*    */ 
/*    */   public Object decorate(Object object, String style) {
/* 21 */     System.out.println("decorate handler with style:" + style);
/* 22 */     if (style.equals("Handler")) {
/* 23 */       decorateHandler((IHandler)object);
/*    */     }
/* 25 */     else if (style.equals("SystemHandler")) {
/* 26 */       decorateSystemHandler((IHandler)object);
/*    */     }
/* 28 */     return object;
/*    */   }
/*    */ 
/*    */   private void decorateHandler(IHandler handler)
/*    */   {
/*    */   }
/*    */ 
/*    */   private void decorateSystemHandler(IHandler handler)
/*    */   {
/*    */   }
/*    */ }