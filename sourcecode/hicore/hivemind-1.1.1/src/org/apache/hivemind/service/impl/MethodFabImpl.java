 package org.apache.hivemind.service.impl;
 
 import java.lang.reflect.Modifier;
 import javassist.CtBehavior;
 import javassist.CtClass;
 import javassist.CtMember;
 import javassist.CtMethod;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.service.MethodFab;
 import org.apache.hivemind.service.MethodSignature;
 
 class MethodFabImpl
   implements MethodFab
 {
   private CtClassSource _source;
   private MethodSignature _signature;
   private CtMethod _method;
   private StringBuffer _descriptionBody = new StringBuffer();
 
   public MethodFabImpl(CtClassSource source, MethodSignature signature, CtMethod method, String body)
   {
     this._source = source;
     this._signature = signature;
     this._method = method;
 
     this._descriptionBody.append(body);
   }
 
   public String toString()
   {
     StringBuffer buffer = new StringBuffer();
     try
     {
       buffer.append(Modifier.toString(this._method.getModifiers()));
 
       buffer.append(" ");
       buffer.append(this._method.getReturnType().getName());
 
       buffer.append(" ");
       buffer.append(this._method.getName());
       buffer.append("(");
 
       CtClass[] params = this._method.getParameterTypes();
 
       for (int i = 0; i < params.length; ++i)
       {
         if (i > 0) {
           buffer.append(", ");
         }
         buffer.append(params[i].getName());
 
         buffer.append(" $");
         buffer.append(i + 1);
       }
       buffer.append(")");
 
       CtClass[] exceptions = this._method.getExceptionTypes();
 
       for (int i = 0; i < exceptions.length; ++i)
       {
         if (i == 0)
           buffer.append("\n  throws ");
         else {
           buffer.append(", ");
         }
         buffer.append(exceptions[i].getName());
       }
 
       buffer.append("\n");
       buffer.append(this._descriptionBody);
     }
     catch (Exception ex)
     {
       buffer.append(" *** ");
       buffer.append(ex);
     }
 
     return buffer.toString();
   }
 
   public void addCatch(Class exceptionClass, String catchBody)
   {
     CtClass ctException = this._source.getCtClass(exceptionClass);
     try
     {
       this._method.addCatch(catchBody, ctException);
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(ServiceMessages.unableToAddCatch(exceptionClass, this._method, ex), ex);
     }
 
     this._descriptionBody.append("\ncatch(");
     this._descriptionBody.append(exceptionClass.getName());
     this._descriptionBody.append(" $e)\n");
     this._descriptionBody.append(catchBody);
   }
 
   public void extend(String body, boolean asFinally)
   {
     try
     {
       this._method.insertAfter(body, asFinally);
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(ServiceMessages.unableToExtendMethod(this._signature, this._method.getDeclaringClass().getName(), ex), ex);
     }
 
     this._descriptionBody.append("\n");
 
     if (asFinally) {
       this._descriptionBody.append("finally\n");
     }
     this._descriptionBody.append(body);
   }
 }