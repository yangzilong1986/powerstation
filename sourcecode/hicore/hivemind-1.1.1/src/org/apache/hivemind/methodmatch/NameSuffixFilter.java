 package org.apache.hivemind.methodmatch;
 
 import org.apache.hivemind.service.MethodSignature;
 
 public class NameSuffixFilter extends MethodFilter
 {
   private String _nameSuffix;
 
   public NameSuffixFilter(String nameSuffix)
   {
     this._nameSuffix = nameSuffix;
   }
 
   public boolean matchMethod(MethodSignature sig)
   {
     return sig.getName().endsWith(this._nameSuffix);
   }
 }