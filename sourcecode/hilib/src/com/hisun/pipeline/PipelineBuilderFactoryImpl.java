/*    */ package com.hisun.pipeline;
/*    */ 
/*    */ import org.apache.hivemind.service.ClassFactory;
/*    */ import org.apache.hivemind.service.impl.ClassFactoryImpl;
/*    */ 
/*    */ public class PipelineBuilderFactoryImpl
/*    */   implements PipelineBuilderFactory
/*    */ {
/*    */   public PipelineBuilder createPipelineBuilder(Class serviceInterface, Class filterInterface)
/*    */   {
/* 24 */     ClassFactory _classFactory = new ClassFactoryImpl();
/* 25 */     BridgeBuilder builder = new BridgeBuilder(null, "pipeline", serviceInterface, filterInterface, _classFactory);
/*    */ 
/* 27 */     return new PipelineBuilderImpl(builder);
/*    */   }
/*    */ }