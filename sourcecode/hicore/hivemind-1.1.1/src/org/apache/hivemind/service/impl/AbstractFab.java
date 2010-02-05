 package org.apache.hivemind.service.impl;
 
 import java.util.HashMap;
 import java.util.Map;
 import javassist.CtClass;
 
 public class AbstractFab
 {
   private final CtClass _ctClass;
   private final CtClassSource _source;
   private Map _ctClassCache = new HashMap();
 
   public AbstractFab(CtClassSource source, CtClass ctClass)
   {
     this._ctClass = ctClass;
     this._source = source;
   }
 
   public void addInterface(Class interfaceClass)
   {
     CtClass ctInterfaceClass = this._source.getCtClass(interfaceClass);
 
     this._ctClass.addInterface(ctInterfaceClass);
   }
 
   protected CtClass[] convertClasses(Class[] inputClasses)
   {
     if ((inputClasses == null) || (inputClasses.length == 0)) {
       return null;
     }
     int count = inputClasses.length;
     CtClass[] result = new CtClass[count];
 
     for (int i = 0; i < count; ++i)
     {
       CtClass ctClass = convertClass(inputClasses[i]);
 
       result[i] = ctClass;
     }
 
     return result;
   }
 
   protected CtClass convertClass(Class inputClass)
   {
     CtClass result = (CtClass)this._ctClassCache.get(inputClass);
 
     if (result == null)
     {
       result = this._source.getCtClass(inputClass);
       this._ctClassCache.put(inputClass, result);
     }
 
     return result;
   }
 
   public Class createClass()
   {
     return this._source.createClass(this._ctClass);
   }
 
   protected CtClass getCtClass()
   {
     return this._ctClass;
   }
 
   protected CtClassSource getSource()
   {
     return this._source;
   }
 }