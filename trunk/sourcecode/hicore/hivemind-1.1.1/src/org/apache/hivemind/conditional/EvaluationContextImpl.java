 package org.apache.hivemind.conditional;
 
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.ClassResolver;
 import org.apache.hivemind.util.Defense;
 
 public class EvaluationContextImpl
   implements EvaluationContext
 {
   private ClassResolver _resolver;
 
   public EvaluationContextImpl(ClassResolver resolver)
   {
     Defense.notNull(resolver, "resolver");
 
     this._resolver = resolver;
   }
 
   public boolean isPropertySet(String propertyName)
   {
     return Boolean.getBoolean(propertyName);
   }
 
   public boolean doesClassExist(String className)
   {
     try
     {
       this._resolver.findClass(className);
 
       return true;
     }
     catch (ApplicationRuntimeException ex) {
     }
     return false;
   }
 }