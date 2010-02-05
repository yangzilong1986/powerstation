 package org.apache.hivemind.util;
 
 import java.io.IOException;
 import java.io.InputStream;
 import java.net.MalformedURLException;
 import java.net.URL;
 import java.util.Locale;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.Resource;
 
 public class URLResource extends AbstractResource
 {
   private URL _url;
 
   public URLResource(URL url)
   {
     super(url.toString());
     this._url = url;
   }
 
   public URLResource(String path)
   {
     super(path);
   }
 
   public String toString()
   {
     return super.getPath();
   }
 
   protected Resource newResource(String path)
   {
     return new URLResource(path);
   }
 
   public URL getResourceURL()
   {
     if (this._url == null)
     {
       try
       {
         URL test = new URL(super.getPath());
         InputStream stream = test.openStream();
         if (stream != null)
         {
           stream.close();
           this._url = test;
         }
       }
       catch (MalformedURLException ex)
       {
         throw new ApplicationRuntimeException(ex);
       }
       catch (IOException ex)
       {
       }
 
     }
 
     return this._url;
   }
 
   public Resource getLocalization(Locale locale)
   {
     return this;
   }
 }