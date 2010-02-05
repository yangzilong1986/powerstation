 package org.apache.hivemind.methodmatch;
 
 import org.apache.hivemind.service.MethodSignature;
 
 public class NamePrefixFilter extends MethodFilter
 {
   private String _namePrefix;
 
   public NamePrefixFilter(String namePrefix)
   {
     this._namePrefix = namePrefix;
   }
 
   public boolean matchMethod(MethodSignature sig)
   {
     return sig.getName().startsWith(this._namePrefix);
   }
 }