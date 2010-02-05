 package org.apache.hivemind.service.impl;
 
 import javassist.ClassPool;
 import javassist.CtClass;
 import javassist.NotFoundException;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.service.ClassFabUtils;
 
 public class CtClassSource
 {
   private HiveMindClassPool _pool;
 
   public CtClassSource(HiveMindClassPool pool)
   {
     this._pool = pool;
   }
 
   public CtClass getCtClass(Class searchClass)
   {
     ClassLoader loader = searchClass.getClassLoader();
 
     this._pool.appendClassLoader(loader);
 
     String name = ClassFabUtils.getJavaClassName(searchClass);
     try
     {
       return this._pool.get(name);
     }
     catch (NotFoundException ex)
     {
       throw new ApplicationRuntimeException(ServiceMessages.unableToLookupClass(name, ex), ex);
     }
   }
 
   public CtClass newClass(String name, Class superClass)
   {
     CtClass ctSuperClass = getCtClass(superClass);
 
     return this._pool.makeClass(name, ctSuperClass);
   }
 
   public CtClass newInterface(String name)
   {
     return this._pool.makeInterface(name);
   }
 
   public Class createClass(CtClass ctClass)
   {
     try
     {
       return this._pool.toClass(ctClass);
     }
     catch (Throwable ex)
     {
       throw new ApplicationRuntimeException(ServiceMessages.unableToWriteClass(ctClass, ex), ex);
     }
   }
 }