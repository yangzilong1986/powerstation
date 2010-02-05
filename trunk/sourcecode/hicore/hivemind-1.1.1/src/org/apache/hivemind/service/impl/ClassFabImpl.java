 package org.apache.hivemind.service.impl;
 
 import java.lang.reflect.Modifier;
 import java.util.ArrayList;
 import java.util.Collection;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import javassist.CannotCompileException;
 import javassist.CtBehavior;
 import javassist.CtClass;
 import javassist.CtConstructor;
 import javassist.CtField;
 import javassist.CtMethod;
 import javassist.NotFoundException;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.service.ClassFab;
 import org.apache.hivemind.service.MethodFab;
 import org.apache.hivemind.service.MethodSignature;
 
 public class ClassFabImpl extends AbstractFab
   implements ClassFab
 {
   private Map _methods = new HashMap();
 
   private List _constructors = new ArrayList();
 
   public ClassFabImpl(CtClassSource source, CtClass ctClass)
   {
     super(source, ctClass);
   }
 
   public String toString()
   {
     StringBuffer buffer = new StringBuffer("ClassFab[\n");
     try
     {
       buildClassAndInheritance(buffer);
 
       buildFields(buffer);
 
       buildConstructors(buffer);
 
       buildMethods(buffer);
     }
     catch (Exception ex)
     {
       buffer.append(" *** ");
       buffer.append(ex);
     }
 
     buffer.append("\n]");
 
     return buffer.toString();
   }
 
   private void buildMethods(StringBuffer buffer)
   {
     Iterator i = this._methods.values().iterator();
     while (i.hasNext())
     {
       MethodFab mf = (MethodFab)i.next();
 
       buffer.append("\n");
       buffer.append(mf);
       buffer.append("\n");
     }
   }
 
   private void buildConstructors(StringBuffer buffer)
   {
     Iterator i = this._constructors.iterator();
 
     while (i.hasNext())
     {
       buffer.append("\n");
       buffer.append(i.next());
     }
   }
 
   private void buildFields(StringBuffer buffer)
     throws NotFoundException
   {
     CtField[] fields = super.getCtClass().getDeclaredFields();
 
     for (int i = 0; i < fields.length; ++i)
     {
       buffer.append("\n");
       buffer.append(modifiers(fields[i].getModifiers()));
       buffer.append(" ");
       buffer.append(fields[i].getType().getName());
       buffer.append(" ");
       buffer.append(fields[i].getName());
       buffer.append(";\n");
     }
   }
 
   private void buildClassAndInheritance(StringBuffer buffer)
     throws NotFoundException
   {
     buffer.append(modifiers(super.getCtClass().getModifiers()));
     buffer.append(" class ");
     buffer.append(super.getCtClass().getName());
     buffer.append(" extends ");
     buffer.append(super.getCtClass().getSuperclass().getName());
     buffer.append("\n");
 
     CtClass[] interfaces = super.getCtClass().getInterfaces();
 
     if (interfaces.length <= 0)
       return;
     buffer.append("  implements ");
 
     for (int i = 0; i < interfaces.length; ++i)
     {
       if (i > 0) {
         buffer.append(", ");
       }
       buffer.append(interfaces[i].getName());
     }
 
     buffer.append("\n");
   }
 
   private String modifiers(int modifiers)
   {
     return Modifier.toString(modifiers);
   }
 
   String getName()
   {
     return super.getCtClass().getName();
   }
 
   public void addField(String name, Class type)
   {
     CtClass ctType = super.convertClass(type);
     try
     {
       CtField field = new CtField(ctType, name, super.getCtClass());
       field.setModifiers(2);
 
       super.getCtClass().addField(field);
     }
     catch (CannotCompileException ex)
     {
       throw new ApplicationRuntimeException(ServiceMessages.unableToAddField(name, super.getCtClass(), ex), ex);
     }
   }
 
   public boolean containsMethod(MethodSignature ms)
   {
     return (this._methods.get(ms) != null);
   }
 
   public MethodFab addMethod(int modifiers, MethodSignature ms, String body)
   {
     if (this._methods.get(ms) != null) {
       throw new ApplicationRuntimeException(ServiceMessages.duplicateMethodInClass(ms, this));
     }
     CtClass ctReturnType = super.convertClass(ms.getReturnType());
 
     CtClass[] ctParameters = super.convertClasses(ms.getParameterTypes());
     CtClass[] ctExceptions = super.convertClasses(ms.getExceptionTypes());
 
     CtMethod method = new CtMethod(ctReturnType, ms.getName(), ctParameters, super.getCtClass());
     try
     {
       method.setModifiers(modifiers);
       method.setBody(body);
       method.setExceptionTypes(ctExceptions);
 
       super.getCtClass().addMethod(method);
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(ServiceMessages.unableToAddMethod(ms, super.getCtClass(), ex), ex);
     }
 
     MethodFab result = new MethodFabImpl(super.getSource(), ms, method, body);
 
     this._methods.put(ms, result);
 
     return result;
   }
 
   public MethodFab getMethodFab(MethodSignature ms)
   {
     return ((MethodFab)this._methods.get(ms));
   }
 
   public void addConstructor(Class[] parameterTypes, Class[] exceptions, String body)
   {
     CtClass[] ctParameters = super.convertClasses(parameterTypes);
     CtClass[] ctExceptions = super.convertClasses(exceptions);
     try
     {
       CtConstructor constructor = new CtConstructor(ctParameters, super.getCtClass());
       constructor.setExceptionTypes(ctExceptions);
       constructor.setBody(body);
 
       super.getCtClass().addConstructor(constructor);
 
       this._constructors.add(new AddedConstructor(parameterTypes, exceptions, body));
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(ServiceMessages.unableToAddConstructor(super.getCtClass(), ex), ex);
     }
   }
 
   private class AddedConstructor
   {
     private Class[] _parameterTypes;
     private Class[] _exceptionTypes;
     private String _body;
 
     AddedConstructor(Class[] paramArrayOfClass1, Class[] paramArrayOfClass2, String paramString)
     {
       this._parameterTypes = paramArrayOfClass1;
       this._exceptionTypes = paramArrayOfClass2;
       this._body = paramString;
     }
 
     public String toString()
     {
       StringBuffer buffer = new StringBuffer();
 
       buffer.append("public ");
       buffer.append(ClassFabImpl.this.getCtClass().getName());
 
       buffer.append("(");
 
       int count = size(this._parameterTypes);
       for (int i = 0; i < count; ++i)
       {
         if (i > 0) {
           buffer.append(", ");
         }
         buffer.append(this._parameterTypes[i].getName());
 
         buffer.append(" $");
         buffer.append(i + 1);
       }
 
       buffer.append(")");
 
       count = size(this._exceptionTypes);
       for (i = 0; i < count; ++i)
       {
         if (i == 0)
           buffer.append("\n  throws ");
         else {
           buffer.append(", ");
         }
         buffer.append(this._exceptionTypes[i].getName());
       }
 
       buffer.append("\n");
       buffer.append(this._body);
 
       buffer.append("\n");
 
       return buffer.toString();
     }
 
     private int size(Object[] array)
     {
       return ((array == null) ? 0 : array.length);
     }
   }
 }