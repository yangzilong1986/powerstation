 package org.apache.hivemind.util;
 
 import java.net.MalformedURLException;
 import java.util.Locale;
 import javax.servlet.ServletContext;
 
 public class LocalizedContextResourceFinder
 {
   private ServletContext _context;
 
   public LocalizedContextResourceFinder(ServletContext context)
   {
     this._context = context;
   }
 
   public LocalizedResource resolve(String contextPath, Locale locale)
   {
     String basePath;
     String suffix;
     int dotx = contextPath.lastIndexOf(46);
 
     if (dotx >= 0) {
       basePath = contextPath.substring(0, dotx);
       suffix = contextPath.substring(dotx);
     }
     else
     {
       basePath = contextPath;
       suffix = "";
     }
 
     LocalizedNameGenerator generator = new LocalizedNameGenerator(basePath, locale, suffix);
 
     while (generator.more())
     {
       String candidatePath = generator.next();
 
       if (isExistingResource(candidatePath)) {
         return new LocalizedResource(candidatePath, generator.getCurrentLocale());
       }
     }
     return null;
   }
 
   private boolean isExistingResource(String path)
   {
     try
     {
       return (this._context.getResource(path) != null);
     }
     catch (MalformedURLException ex) {
     }
     return false;
   }
 }