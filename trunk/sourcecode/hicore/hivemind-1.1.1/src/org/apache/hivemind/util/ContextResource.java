 package org.apache.hivemind.util;
 
 import java.net.MalformedURLException;
 import java.net.URL;
 import java.util.Locale;
 import javax.servlet.ServletContext;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.apache.hivemind.Resource;
 
 public class ContextResource extends AbstractResource
 {
   private static final Log LOG = LogFactory.getLog(ContextResource.class);
   private ServletContext _context;
 
   public ContextResource(ServletContext context, String path)
   {
     this(context, path, null);
   }
 
   public ContextResource(ServletContext context, String path, Locale locale)
   {
     super(path, locale);
 
     this._context = context;
   }
 
   public Resource getLocalization(Locale locale)
   {
     LocalizedContextResourceFinder finder = new LocalizedContextResourceFinder(this._context);
 
     String path = super.getPath();
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
     return new ContextResource(this._context, localizedPath, pathLocale);
   }
 
   public URL getResourceURL()
   {
     try
     {
       return this._context.getResource(super.getPath());
     }
     catch (MalformedURLException ex)
     {
       LOG.warn(UtilMessages.unableToReferenceContextPath(super.getPath(), ex), ex);
     }
     return null;
   }
 
   public String toString()
   {
     return "context:" + super.getPath();
   }
 
   public int hashCode()
   {
     return (0x1065 & super.getPath().hashCode());
   }
 
   protected Resource newResource(String path)
   {
     return new ContextResource(this._context, path);
   }
 }