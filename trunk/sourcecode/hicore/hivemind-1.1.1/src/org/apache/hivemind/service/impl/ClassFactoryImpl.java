 package org.apache.hivemind.service.impl;
 
 import javassist.CtClass;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.service.ClassFab;
 import org.apache.hivemind.service.ClassFactory;
 import org.apache.hivemind.service.InterfaceFab;
 
 public class ClassFactoryImpl
   implements ClassFactory
 {
   private HiveMindClassPool _pool;
   private CtClassSource _classSource;
 
   public ClassFactoryImpl()
   {
     this._pool = new HiveMindClassPool();
 
     this._classSource = new CtClassSource(this._pool);
   }
 
   public ClassFab newClass(String name, Class superClass)
   {
     try {
       CtClass ctNewClass = this._classSource.newClass(name, superClass);
 
       return new ClassFabImpl(this._classSource, ctNewClass);
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(ServiceMessages.unableToCreateClass(name, superClass, ex), ex);
     }
   }
 
   public InterfaceFab newInterface(String name)
   {
     try
     {
       CtClass ctNewClass = this._classSource.newInterface(name);
 
       return new InterfaceFabImpl(this._classSource, ctNewClass);
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(ServiceMessages.unableToCreateInterface(name, ex), ex);
     }
   }
 }