 package org.apache.hivemind.service.impl;
 
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 import javassist.CtBehavior;
 import javassist.CtClass;
 import javassist.CtMethod;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.service.InterfaceFab;
 import org.apache.hivemind.service.MethodSignature;
 
 public class InterfaceFabImpl extends AbstractFab
   implements InterfaceFab
 {
   private List _methods = new ArrayList();
 
   public InterfaceFabImpl(CtClassSource source, CtClass ctClass)
   {
     super(source, ctClass);
   }
 
   public String toString()
   {
     StringBuffer buffer = new StringBuffer("InterfaceFabImpl[\npublic interface ");
 
     CtClass ctClass = super.getCtClass();
 
     buffer.append(ctClass.getName());
     try
     {
       CtClass[] interfaces = ctClass.getInterfaces();
 
       for (int i = 0; i < interfaces.length; ++i)
       {
         buffer.append((i == 0) ? " extends " : ", ");
         buffer.append(interfaces[i].getName());
       }
 
     }
     catch (Exception ex)
     {
       buffer.append("<Exception: " + ex + ">");
     }
 
     Iterator i = this._methods.iterator();
 
     while (i.hasNext())
     {
       MethodSignature sig = (MethodSignature)i.next();
 
       buffer.append("\n\npublic ");
       buffer.append(sig);
       buffer.append(";");
     }
 
     buffer.append("\n]");
 
     return buffer.toString();
   }
 
   public void addMethod(MethodSignature ms)
   {
     CtClass ctReturnType = super.convertClass(ms.getReturnType());
     CtClass[] ctParameters = super.convertClasses(ms.getParameterTypes());
     CtClass[] ctExceptions = super.convertClasses(ms.getExceptionTypes());
 
     CtMethod method = new CtMethod(ctReturnType, ms.getName(), ctParameters, super.getCtClass());
     try
     {
       method.setModifiers(1025);
       method.setExceptionTypes(ctExceptions);
 
       super.getCtClass().addMethod(method);
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(ServiceMessages.unableToAddMethod(ms, super.getCtClass(), ex), ex);
     }
 
     this._methods.add(ms);
   }
 
   public Class createInterface()
   {
     return super.createClass();
   }
 }