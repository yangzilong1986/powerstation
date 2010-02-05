 package org.apache.hivemind.util;
 
 import java.net.URL;
 import java.util.Locale;
 import org.apache.hivemind.Resource;
 
 public abstract class AbstractResource
   implements Resource
 {
   private String _path;
   private String _name;
   private String _folderPath;
   private Locale _locale;
 
   protected AbstractResource(String path)
   {
     this(path, null);
   }
 
   protected AbstractResource(String path, Locale locale)
   {
     this._path = path;
     this._locale = locale;
   }
 
   public String getName()
   {
     if (this._name == null) {
       split();
     }
     return this._name;
   }
 
   public Resource getRelativeResource(String name)
   {
     if (name.startsWith("/"))
     {
       if (name.equals(this._path)) {
         return this;
       }
       return newResource(name);
     }
 
     if (this._folderPath == null) {
       split();
     }
     if (name.equals(this._name)) {
       return this;
     }
     return newResource(this._folderPath + name);
   }
 
   public String getPath()
   {
     return this._path;
   }
 
   public Locale getLocale()
   {
     return this._locale;
   }
 
   protected abstract Resource newResource(String paramString);
 
   private void split()
   {
     int lastSlashx = this._path.lastIndexOf(47);
 
     this._folderPath = this._path.substring(0, lastSlashx + 1);
     this._name = this._path.substring(lastSlashx + 1);
   }
 
   public boolean equals(Object obj)
   {
     if (obj == null) {
       return false;
     }
     if (obj.getClass().equals(super.getClass()))
     {
       AbstractResource otherLocation = (AbstractResource)obj;
 
       return this._path.equals(otherLocation._path);
     }
 
     return false;
   }
 
   public abstract Resource getLocalization(Locale paramLocale);
 
   public abstract URL getResourceURL();
 }