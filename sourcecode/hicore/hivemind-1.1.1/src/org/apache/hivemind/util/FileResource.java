 package org.apache.hivemind.util;
 
 import java.io.File;
 import java.net.MalformedURLException;
 import java.net.URL;
 import java.util.Locale;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.apache.hivemind.Resource;
 
 public class FileResource extends AbstractResource
 {
   private static final Log LOG = LogFactory.getLog(FileResource.class);
 
   public FileResource(String path)
   {
     super(path);
   }
 
   public FileResource(String path, Locale locale)
   {
     super(path, locale);
   }
 
   protected Resource newResource(String path)
   {
     return new FileResource(path);
   }
 
   private File getFile()
   {
     return new File(super.getPath());
   }
 
   public URL getResourceURL()
   {
     File file = getFile();
     try
     {
       if ((file == null) || (!(file.exists()))) {
         return null;
       }
       return file.toURL();
     }
     catch (MalformedURLException ex)
     {
       LOG.error(UtilMessages.badFileURL(super.getPath(), ex), ex); }
     return null;
   }
 
   public Resource getLocalization(Locale locale)
   {
     LocalizedFileResourceFinder f = new LocalizedFileResourceFinder();
 
     String path = super.getPath();
 
     String finalPath = f.findLocalizedPath(path, locale);
 
     if (finalPath.equals(path)) {
       return this;
     }
     return newResource(finalPath);
   }
 
   public String toString()
   {
     return super.getPath();
   }
 }