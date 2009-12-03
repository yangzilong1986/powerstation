/*    */ package com.hisun.framework.imp;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.util.HiStringManager;
/*    */ import java.lang.reflect.Method;
/*    */ import org.apache.commons.beanutils.MethodUtils;
/*    */ 
/*    */ public class HiHandlerWapper
/*    */   implements IHandler
/*    */ {
/* 15 */   protected final HiStringManager sm = HiStringManager.getManager();
/*    */   protected final String name;
/*    */   protected Object handler;
/*    */   protected final String method;
/*    */   private final Logger log;
/*    */ 
/*    */   public HiHandlerWapper(String name, Object handler, String method, Logger log)
/*    */     throws HiException
/*    */   {
/* 32 */     this.name = name;
/* 33 */     this.handler = handler;
/* 34 */     this.method = method;
/* 35 */     this.log = log;
/*    */ 
/* 38 */     Method m = MethodUtils.getAccessibleMethod(handler.getClass(), method, HiMessageContext.class);
/*    */ 
/* 40 */     if (m == null)
/* 41 */       throw new HiException("211002", "handler has no method '" + method + "' :" + name);
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 47 */     if (this.log.isDebugEnabled()) {
/* 48 */       this.log.debug("Handler " + this.name + " " + this.method + "() - start");
/*    */     }
/*    */     try
/*    */     {
/* 52 */       MethodUtils.invokeMethod(this.handler, this.method, ctx);
/*    */     } catch (Throwable t) {
/* 54 */       throw HiException.makeException("211005", this.method, t);
/*    */     }
/*    */ 
/* 58 */     if (this.log.isDebugEnabled())
/* 59 */       this.log.debug("Handler " + this.name + " " + this.method + "() - end");
/*    */   }
/*    */ }