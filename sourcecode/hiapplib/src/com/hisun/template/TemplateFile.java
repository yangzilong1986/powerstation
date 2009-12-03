/*    */ package com.hisun.template;
/*    */ 
/*    */ import freemarker.template.Template;
/*    */ 
/*    */ class TemplateFile
/*    */ {
/*    */   String file;
/*    */   long lastTime;
/*    */   Template template;
/*    */ 
/*    */   public TemplateFile()
/*    */   {
/*    */   }
/*    */ 
/*    */   public TemplateFile(String file, Template template, long lastTime)
/*    */   {
/* 90 */     this.file = file;
/* 91 */     this.template = template;
/* 92 */     this.lastTime = lastTime;
/*    */   }
/*    */ }