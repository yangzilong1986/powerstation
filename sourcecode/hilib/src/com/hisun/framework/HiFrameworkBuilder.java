/*    */ package com.hisun.framework;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.framework.parser.HiDigesterParserImp;
/*    */ import com.hisun.pipeline.PipelineBuilder;
/*    */ import com.hisun.pipeline.PipelineBuilderFactory;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.pubinterface.IHandlerFilter;
/*    */ import com.hisun.service.HiInfrastructure;
/*    */ import com.hisun.service.IObjectDecorator;
/*    */ import com.hisun.util.HiResource;
/*    */ import org.apache.hivemind.Registry;
/*    */ import org.apache.hivemind.lib.BeanFactory;
/*    */ 
/*    */ public class HiFrameworkBuilder
/*    */ {
/*    */   public static PipelineBuilder handlerBuilder;
/*    */ 
/*    */   public static Registry getRegistry()
/*    */   {
/* 23 */     return HiInfrastructure.getRegistry();
/*    */   }
/*    */ 
/*    */   public static HiConfigParser getParser()
/*    */   {
/* 28 */     return new HiDigesterParserImp();
/*    */   }
/*    */ 
/*    */   public static BeanFactory getHandlerFactory() {
/* 32 */     return ((BeanFactory)getRegistry().getService("icsframework.handlerFactory", BeanFactory.class));
/*    */   }
/*    */ 
/*    */   public static BeanFactory getCommFactory()
/*    */   {
/* 37 */     return ((BeanFactory)getRegistry().getService("icsframework.commFactory", BeanFactory.class));
/*    */   }
/*    */ 
/*    */   public static PipelineBuilder getPipelineBuilder()
/*    */   {
/* 43 */     if (handlerBuilder == null) {
/* 44 */       PipelineBuilderFactory factory = (PipelineBuilderFactory)getRegistry().getService("icsframework.PipelineBuilderFactory", PipelineBuilderFactory.class);
/*    */ 
/* 46 */       handlerBuilder = factory.createPipelineBuilder(IHandler.class, IHandlerFilter.class);
/*    */     }
/* 48 */     return handlerBuilder;
/*    */   }
/*    */ 
/*    */   public static IObjectDecorator getObjectDecorator() throws HiException
/*    */   {
/*    */     try {
/* 54 */       Class clazz = HiResource.loadClass("com.hisun.framework.decorator.ServerDecorator");
/*    */ 
/* 56 */       return ((IObjectDecorator)clazz.newInstance());
/*    */     } catch (Exception e) {
/* 58 */       throw HiException.makeException(e);
/*    */     }
/*    */   }
/*    */ }