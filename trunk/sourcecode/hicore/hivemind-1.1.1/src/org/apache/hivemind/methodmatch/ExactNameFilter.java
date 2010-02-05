 package org.apache.hivemind.methodmatch;
 
 import org.apache.hivemind.service.MethodSignature;
 
 public class ExactNameFilter extends MethodFilter
 {
   private String _name;
 
   public ExactNameFilter(String name)
   {
     this._name = name;
   }
 
   public boolean matchMethod(MethodSignature sig)
   {
     return sig.getName().equals(this._name);
   }
 }