 package org.apache.hivemind.util;
 
 import java.io.File;
 import java.util.Locale;
 
 public class LocalizedFileResourceFinder
 {
   public String findLocalizedPath(String path, Locale locale)
   {
     String basePath;
     String suffix;
     int dotx = path.lastIndexOf(46);
 
     if (dotx >= 0)
     {
       basePath = path.substring(0, dotx);
       suffix = path.substring(dotx);
     }
     else
     {
       basePath = path;
       suffix = "";
     }
     LocalizedNameGenerator g = new LocalizedNameGenerator(basePath, locale, suffix);
 
     while (g.more())
     {
       String candidate = g.next();
 
       File f = new File(candidate);
 
       if (f.exists()) {
         return candidate;
       }
     }
     return path;
   }
 }