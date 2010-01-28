 package com.hisun.template;
 
 import java.io.StringWriter;
 import java.util.Map;
 import org.apache.velocity.VelocityContext;
 import org.apache.velocity.app.Velocity;
 
 public abstract class AbstractTemplateEngine
   implements TemplateEngine
 {
   private static TemplateFiles templateFiles = new TemplateFiles();
 
   public abstract String getEngineType();
 
   public StringBuffer run(Map context, String templateFile) throws Exception { if ("ENGINE_TYPE_FREEMARKER".equals(getEngineType())) {
       return executeFreemarker(context, templateFile);
     }
     return executeVelocity(context, templateFile);
   }
 
   private StringBuffer executeFreemarker(Map context, String templateFile) throws Exception {
     freemarker.template.Template temp = null;
     synchronized (templateFiles) {
       temp = templateFiles.getTemplate(templateFile);
     }
     StringWriter sw = new StringWriter();
     temp.process(context, sw);
     sw.close();
     return sw.getBuffer();
   }
 
   private StringBuffer executeVelocity(Map root, String templateFile) throws Exception
   {
     Velocity.init();
     VelocityContext context = new VelocityContext(root);
     org.apache.velocity.Template template = null;
 
     template = Velocity.getTemplate(templateFile);
 
     StringWriter sw = new StringWriter();
     template.merge(context, sw);
     sw.close();
     return sw.getBuffer();
   }
 }