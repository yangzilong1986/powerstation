 package org.apache.hivemind.service.impl;
 
 import java.util.ArrayList;
 import java.util.List;
 
 class ClassFactoryClassLoader extends ClassLoader
 {
   private List _loaders;
 
   ClassFactoryClassLoader()
   {
     this._loaders = new ArrayList();
   }
 
   public synchronized void addDelegateLoader(ClassLoader loader)
   {
     this._loaders.add(loader);
   }
 
   protected synchronized Class findClass(String name)
     throws ClassNotFoundException
   {
     ClassNotFoundException cnfex = null;
     try
     {
       return super.findClass(name);
     }
     catch (ClassNotFoundException count)
     {
       cnfex = ex;
 
       int count = this._loaders.size();
       for (int i = 0; i < count; )
       {
         ClassLoader l = (ClassLoader)this._loaders.get(i);
         try
         {
           return l.loadClass(name);
         }
         catch (ClassNotFoundException ex)
         {
           ++i;
         }
 
       }
 
       throw cnfex;
     }
   }
 }