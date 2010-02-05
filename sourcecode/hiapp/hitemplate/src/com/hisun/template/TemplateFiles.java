 package com.hisun.template;
 
 import freemarker.cache.MruCacheStorage;
 import freemarker.template.Configuration;
 import freemarker.template.DefaultObjectWrapper;
 import freemarker.template.Template;
 import java.io.File;
 import java.io.IOException;
 import java.util.HashMap;
 
 class TemplateFiles
 {
   private static HashMap<String, TemplateFile> templates = new HashMap();
 
   public Template getTemplate(String file) throws IOException {
     TemplateFile template = (TemplateFile)templates.get(file);
     File f = new File(file);
     Configuration cfg = new Configuration();
     cfg.setDirectoryForTemplateLoading(new File(f.getParent()));
 
     cfg.setObjectWrapper(new DefaultObjectWrapper());
     cfg.setCacheStorage(new MruCacheStorage(20, 250));
     if (template == null) {
       template = new TemplateFile();
       template.file = file;
       templates.put(file, template);
     }
 
     if (template.lastTime != f.lastModified()) {
       template.lastTime = f.lastModified();
       template.template = cfg.getTemplate(f.getName());
     }
     return template.template;
   }
 }