 package com.hisun.template;
 
 import java.util.Map;
 
 public class FreemarkerTemplateEngine extends AbstractTemplateEngine
 {
   public StringBuffer run(Map root, String templateFile)
     throws Exception
   {
     return super.run(root, templateFile);
   }
 
   public String getEngineType()
   {
     return "ENGINE_TYPE_FREEMARKER";
   }
 }