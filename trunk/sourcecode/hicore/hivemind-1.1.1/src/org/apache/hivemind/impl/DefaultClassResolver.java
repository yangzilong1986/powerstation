 package org.apache.hivemind.impl;
 
 import java.net.URL;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.ClassResolver;
 
 public class DefaultClassResolver
   implements ClassResolver
 {
   private ClassLoader _loader;
 
   public DefaultClassResolver()
   {
     this(Thread.currentThread().getContextClassLoader());
   }
 
   public DefaultClassResolver(ClassLoader loader)
   {
     this._loader = loader;
   }
 
   public URL getResource(String name)
   {
     String stripped = removeLeadingSlash(name);
 
     URL result = this._loader.getResource(stripped);
 
     return result;
   }
 
   private String removeLeadingSlash(String name)
   {
     if (name.startsWith("/")) {
       return name.substring(1);
     }
     return name;
   }
 
   public Class findClass(String type)
   {
     try
     {
       return lookupClass(type);
     }
     catch (Throwable t)
     {
       throw new ApplicationRuntimeException(ImplMessages.unableToLoadClass(type, this._loader, t), t);
     }
   }
 
   private Class lookupClass(String type)
     throws ClassNotFoundException
   {
     Class result = JavaTypeUtils.getPrimtiveClass(type);
 
     if (result != null) {
       return result;
     }
 
     String jvmName = JavaTypeUtils.getJVMClassName(type);
 
     return Class.forName(jvmName, true, this._loader);
   }
 
   public Class checkForClass(String type)
   {
     try
     {
       return lookupClass(type);
     }
     catch (ClassNotFoundException ex)
     {
       return null;
     }
     catch (Throwable t)
     {
       throw new ApplicationRuntimeException(ImplMessages.unableToLoadClass(type, this._loader, t), t);
     }
   }
 
   public ClassLoader getClassLoader()
   {
     return this._loader;
   }
 }