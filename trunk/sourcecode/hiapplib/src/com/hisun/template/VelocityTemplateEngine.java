 package com.hisun.template;
 
 import java.util.Map;
 
 public class VelocityTemplateEngine extends AbstractTemplateEngine
 {
   public StringBuffer run(Map map, String templateFile)
     throws Exception
   {
     return super.run(map, templateFile);
   }
 
   public String getEngineType()
   {
     return "ENGINE_TYPE_VELOCITY";
   }
 }