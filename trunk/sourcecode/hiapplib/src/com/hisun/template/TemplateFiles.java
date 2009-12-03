/*    */ package com.hisun.template;
/*    */ 
/*    */ import freemarker.cache.MruCacheStorage;
/*    */ import freemarker.template.Configuration;
/*    */ import freemarker.template.DefaultObjectWrapper;
/*    */ import freemarker.template.Template;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ class TemplateFiles
/*    */ {
/* 59 */   private static HashMap<String, TemplateFile> templates = new HashMap();
/*    */ 
/*    */   public Template getTemplate(String file) throws IOException {
/* 62 */     TemplateFile template = (TemplateFile)templates.get(file);
/* 63 */     File f = new File(file);
/* 64 */     Configuration cfg = new Configuration();
/* 65 */     cfg.setDirectoryForTemplateLoading(new File(f.getParent()));
/*    */ 
/* 67 */     cfg.setObjectWrapper(new DefaultObjectWrapper());
/* 68 */     cfg.setCacheStorage(new MruCacheStorage(20, 250));
/* 69 */     if (template == null) {
/* 70 */       template = new TemplateFile();
/* 71 */       template.file = file;
/* 72 */       templates.put(file, template);
/*    */     }
/*    */ 
/* 75 */     if (template.lastTime != f.lastModified()) {
/* 76 */       template.lastTime = f.lastModified();
/* 77 */       template.template = cfg.getTemplate(f.getName());
/*    */     }
/* 79 */     return template.template;
/*    */   }
/*    */ }