 package org.apache.hivemind.methodmatch;
 
 import org.apache.hivemind.service.MethodSignature;
 
 public class ParameterCountFilter extends MethodFilter
 {
   private int _parameterCount;
 
   public ParameterCountFilter(int parameterCount)
   {
     this._parameterCount = parameterCount;
   }
 
   public boolean matchMethod(MethodSignature method)
   {
     return (method.getParameterTypes().length == this._parameterCount);
   }
 }