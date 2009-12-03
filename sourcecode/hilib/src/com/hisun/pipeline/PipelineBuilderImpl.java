/*    */ package com.hisun.pipeline;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class PipelineBuilderImpl
/*    */   implements PipelineBuilder
/*    */ {
/*    */   private final BridgeBuilder builder;
/*    */ 
/*    */   public PipelineBuilderImpl(BridgeBuilder builder)
/*    */   {
/* 10 */     this.builder = builder;
/*    */   }
/*    */ 
/*    */   public Object buildPipeline(Object filter, Object terminator) {
/* 14 */     ArrayList list = new ArrayList();
/* 15 */     list.add(filter);
/* 16 */     return buildPipeline(list, terminator);
/*    */   }
/*    */ 
/*    */   public Object buildPipeline(List filters, Object terminator) {
/* 20 */     if (filters.isEmpty()) {
/* 21 */       return terminator;
/*    */     }
/* 23 */     Object next = terminator;
/* 24 */     int count = filters.size();
/*    */ 
/* 26 */     for (int i = count - 1; i >= 0; --i)
/*    */     {
/* 28 */       Object filter = filters.get(i);
/* 29 */       next = this.builder.instantiateBridge(next, filter);
/*    */     }
/*    */ 
/* 32 */     return next;
/*    */   }
/*    */ }