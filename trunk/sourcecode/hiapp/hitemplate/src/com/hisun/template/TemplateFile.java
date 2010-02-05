 package com.hisun.template;
 
 import freemarker.template.Template;
 
 class TemplateFile
 {
   String file;
   long lastTime;
   Template template;
 
   public TemplateFile()
   {
   }
 
   public TemplateFile(String file, Template template, long lastTime)
   {
     this.file = file;
     this.template = template;
     this.lastTime = lastTime;
   }
 }