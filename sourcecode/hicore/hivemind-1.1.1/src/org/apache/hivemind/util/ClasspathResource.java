 package org.apache.hivemind.util;
 
 import java.net.URL;
 import java.util.Locale;
 import org.apache.hivemind.ClassResolver;
 import org.apache.hivemind.Resource;
 
 public class ClasspathResource extends AbstractResource
 {
   private ClassResolver _resolver;
 
   public ClasspathResource(ClassResolver resolver, String path)
   {
     this(resolver, path, null);
   }
 
   public ClasspathResource(ClassResolver resolver, String path, Locale locale)
   {
     super(path, locale);
 
     this._resolver = resolver;
   }
 
   public Resource getLocalization(Locale locale)
   {
     String path = super.getPath();
     LocalizedResourceFinder finder = new LocalizedResourceFinder(this._resolver);
 
     LocalizedResource localizedResource = finder.resolve(path, locale);
 
     if (localizedResource == null) {
       return null;
     }
     String localizedPath = localizedResource.getResourcePath();
     Locale pathLocale = localizedResource.getResourceLocale();
 
     if (localizedPath == null) {
       return null;
     }
     if (path.equals(localizedPath)) {
       return this;
     }
     return new ClasspathResource(this._resolver, localizedPath, pathLocale);
   }
 
   public URL getResourceURL()
   {
     return this._resolver.getResource(super.getPath());
   }
 
   public String toString()
   {
     return "classpath:" + super.getPath();
   }
 
   public int hashCode()
   {
     return (0x12AF & super.getPath().hashCode());
   }
 
   protected Resource newResource(String path)
   {
     return new ClasspathResource(this._resolver, path);
   }
 }