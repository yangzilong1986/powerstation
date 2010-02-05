 package org.apache.hivemind.util;
 
 import java.util.Locale;
 import org.apache.hivemind.ClassResolver;
 
 public class LocalizedResourceFinder
 {
   private ClassResolver _resolver;
 
   public LocalizedResourceFinder(ClassResolver resolver)
   {
     this._resolver = resolver;
   }
 
   public LocalizedResource resolve(String resourcePath, Locale locale)
   {
     String basePath;
     String suffix;
     int dotx = resourcePath.lastIndexOf(46);
 
     if (dotx >= 0) {
       basePath = resourcePath.substring(0, dotx);
       suffix = resourcePath.substring(dotx);
     }
     else
     {
       basePath = resourcePath;
       suffix = "";
     }
 
     LocalizedNameGenerator generator = new LocalizedNameGenerator(basePath, locale, suffix);
 
     while (generator.more())
     {
       String candidatePath = generator.next();
 
       if (this._resolver.getResource(candidatePath) != null) {
         return new LocalizedResource(candidatePath, generator.getCurrentLocale());
       }
     }
     return null;
   }
 }