 package org.apache.hivemind.service.impl;
 
 import java.lang.reflect.Method;
 import java.lang.reflect.Modifier;
 import java.util.ArrayList;
 import java.util.HashSet;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Set;
 import org.apache.hivemind.service.ClassFabUtils;
 import org.apache.hivemind.service.ClassFactory;
 import org.apache.hivemind.service.InterfaceFab;
 import org.apache.hivemind.service.InterfaceSynthesizer;
 import org.apache.hivemind.service.MethodSignature;
 
 public class InterfaceSynthesizerImpl
   implements InterfaceSynthesizer
 {
   private ClassFactory _classFactory;
 
   public Class synthesizeInterface(Class beanClass)
   {
     Operation op = new Operation(null);
 
     explodeClass(beanClass, op);
 
     return createInterface(beanClass, op);
   }
 
   void explodeClass(Class beanClass, Operation op)
   {
     Class current = beanClass;
 
     while (current != Object.class)
     {
       op.processClass(current);
 
       current = current.getSuperclass();
     }
 
     op.processInterfaceQueue();
   }
 
   Class createInterface(Class beanClass, Operation op)
   {
     String name = ClassFabUtils.generateClassName(beanClass);
 
     return createInterface(name, op);
   }
 
   private Class createInterface(String name, Operation op)
   {
     InterfaceFab fab = this._classFactory.newInterface(name);
 
     Iterator i = op.getInterfaces().iterator();
     while (i.hasNext())
     {
       Class interfaceClass = (Class)i.next();
 
       fab.addInterface(interfaceClass);
     }
 
     i = op.getNonInterfaceMethodSignatures().iterator();
     while (i.hasNext())
     {
       MethodSignature sig = (MethodSignature)i.next();
 
       fab.addMethod(sig);
     }
 
     return fab.createInterface();
   }
 
   public void setClassFactory(ClassFactory classFactory)
   {
     this._classFactory = classFactory;
   }
 
   private static class Operation
   {
     private Set _interfaces;
     private Set _interfaceMethods;
     private Set _allMethods;
     private List _interfaceQueue;
 
     private Operation()
     {
       this._interfaces = new HashSet();
 
       this._interfaceMethods = new HashSet();
 
       this._allMethods = new HashSet();
 
       this._interfaceQueue = new ArrayList();
     }
 
     public Set getInterfaces() {
       return this._interfaces;
     }
 
     public Set getNonInterfaceMethodSignatures()
     {
       Set result = new HashSet(this._allMethods);
 
       result.removeAll(this._interfaceMethods);
 
       return result;
     }
 
     public void processInterfaceQueue()
     {
       while (!(this._interfaceQueue.isEmpty()))
       {
         Class interfaceClass = (Class)this._interfaceQueue.remove(0);
 
         processInterface(interfaceClass);
       }
     }
 
     private void processInterface(Class interfaceClass)
     {
       Class[] interfaces = interfaceClass.getInterfaces();
 
       for (int i = 0; i < interfaces.length; ++i) {
         addInterfaceToQueue(interfaces[i]);
       }
       Method[] methods = interfaceClass.getDeclaredMethods();
 
       for (int i = 0; i < methods.length; ++i)
       {
         MethodSignature sig = new MethodSignature(methods[i]);
 
         this._interfaceMethods.add(sig);
       }
     }
 
     private void addInterfaceToQueue(Class interfaceClass)
     {
       if (this._interfaces.contains(interfaceClass)) {
         return;
       }
       this._interfaces.add(interfaceClass);
       this._interfaceQueue.add(interfaceClass);
     }
 
     public void processClass(Class beanClass)
     {
       Class[] interfaces = beanClass.getInterfaces();
 
       for (int i = 0; i < interfaces.length; ++i) {
         addInterfaceToQueue(interfaces[i]);
       }
       Method[] methods = beanClass.getDeclaredMethods();
 
       for (int i = 0; i < methods.length; ++i)
       {
         Method m = methods[i];
         int modifiers = m.getModifiers();
 
         if (Modifier.isStatic(modifiers)) continue; if (!(Modifier.isPublic(modifiers))) {
           continue;
         }
         MethodSignature sig = new MethodSignature(m);
 
         this._allMethods.add(sig);
       }
     }
 
     Operation(InterfaceSynthesizerImpl.1 x0)
     {
     }
   }
 }