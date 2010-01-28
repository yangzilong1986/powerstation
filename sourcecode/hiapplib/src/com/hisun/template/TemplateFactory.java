 package com.hisun.template;
 
 import java.util.HashMap;
 import java.util.Map;
 
 public class TemplateFactory
 {
   private static TemplateFactory instance = new TemplateFactory();
   private Map objectMap;
 
   public TemplateFactory()
   {
     this.objectMap = new HashMap();
     synchronized (this) {
       this.objectMap.put("freemarker", new FreemarkerTemplateEngine());
       this.objectMap.put("velocity", new VelocityTemplateEngine());
     }
   }
 
   public static TemplateFactory getInstance() {
     return instance;
   }
 
   public Object getBean(String beanName)
   {
     return this.objectMap.get(beanName);
   }
 }