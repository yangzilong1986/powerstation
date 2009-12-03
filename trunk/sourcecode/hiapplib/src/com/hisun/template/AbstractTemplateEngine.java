/*    */ package com.hisun.template;
/*    */ 
/*    */ import java.io.StringWriter;
/*    */ import java.util.Map;
/*    */ import org.apache.velocity.VelocityContext;
/*    */ import org.apache.velocity.app.Velocity;
/*    */ 
/*    */ public abstract class AbstractTemplateEngine
/*    */   implements TemplateEngine
/*    */ {
/* 23 */   private static TemplateFiles templateFiles = new TemplateFiles();
/*    */ 
/*    */   public abstract String getEngineType();
/*    */ 
/*    */   public StringBuffer run(Map context, String templateFile) throws Exception { if ("ENGINE_TYPE_FREEMARKER".equals(getEngineType())) {
/* 27 */       return executeFreemarker(context, templateFile);
/*    */     }
/* 29 */     return executeVelocity(context, templateFile);
/*    */   }
/*    */ 
/*    */   private StringBuffer executeFreemarker(Map context, String templateFile) throws Exception {
/* 33 */     freemarker.template.Template temp = null;
/* 34 */     synchronized (templateFiles) {
/* 35 */       temp = templateFiles.getTemplate(templateFile);
/*    */     }
/* 37 */     StringWriter sw = new StringWriter();
/* 38 */     temp.process(context, sw);
/* 39 */     sw.close();
/* 40 */     return sw.getBuffer();
/*    */   }
/*    */ 
/*    */   private StringBuffer executeVelocity(Map root, String templateFile) throws Exception
/*    */   {
/* 45 */     Velocity.init();
/* 46 */     VelocityContext context = new VelocityContext(root);
/* 47 */     org.apache.velocity.Template template = null;
/*    */ 
/* 49 */     template = Velocity.getTemplate(templateFile);
/*    */ 
/* 51 */     StringWriter sw = new StringWriter();
/* 52 */     template.merge(context, sw);
/* 53 */     sw.close();
/* 54 */     return sw.getBuffer();
/*    */   }
/*    */ }