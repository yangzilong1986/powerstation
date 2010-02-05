 package org.apache.hivemind.methodmatch;
 
 import org.apache.hivemind.service.ClassFabUtils;
 import org.apache.hivemind.service.MethodSignature;
 
 public class ParameterFilter extends MethodFilter
 {
   private int _parameterIndex;
   private String _parameterType;
 
   public ParameterFilter(int index, String type)
   {
     this._parameterIndex = index;
     this._parameterType = type;
   }
 
   public boolean matchMethod(MethodSignature sig)
   {
     Class actualType = sig.getParameterTypes()[this._parameterIndex];
     String actualTypeName = ClassFabUtils.getJavaClassName(actualType);
 
     return actualTypeName.equals(this._parameterType);
   }
 }