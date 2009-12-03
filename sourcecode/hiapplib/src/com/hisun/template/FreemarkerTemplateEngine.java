/*    */ package com.hisun.template;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public class FreemarkerTemplateEngine extends AbstractTemplateEngine
/*    */ {
/*    */   public StringBuffer run(Map root, String templateFile)
/*    */     throws Exception
/*    */   {
/*  8 */     return super.run(root, templateFile);
/*    */   }
/*    */ 
/*    */   public String getEngineType()
/*    */   {
/* 13 */     return "ENGINE_TYPE_FREEMARKER";
/*    */   }
/*    */ }