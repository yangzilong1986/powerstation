 package org.apache.hivemind.service.impl;
 
 import java.util.HashSet;
 import java.util.Set;
 import javassist.CannotCompileException;
 import javassist.ClassPath;
 import javassist.ClassPool;
 import javassist.CtClass;
 import javassist.LoaderClassPath;
 
 public class HiveMindClassPool extends ClassPool
 {
   private ClassFactoryClassLoader _loader = new ClassFactoryClassLoader();
 
   private Set _loaders = new HashSet();
 
   public HiveMindClassPool()
   {
     super(null);
 
     appendClassLoader(Thread.currentThread().getContextClassLoader());
   }
 
   public synchronized void appendClassLoader(ClassLoader loader)
   {
     if ((loader == null) || (loader == this._loader) || (this._loaders.contains(loader))) {
       return;
     }
     this._loader.addDelegateLoader(loader);
 
     ClassPath path = new LoaderClassPath(loader);
 
     super.appendClassPath(path);
 
     this._loaders.add(loader);
   }
 
   public Class toClass(CtClass ctClass)
     throws CannotCompileException
   {
     return ctClass.toClass(this._loader);
   }
 }