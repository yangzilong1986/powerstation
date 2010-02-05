 package org.apache.hivemind.methodmatch;
 
 import org.apache.hivemind.service.MethodSignature;
 
 public class InfixNameFilter extends MethodFilter
 {
   private String _nameSubstring;
 
   public InfixNameFilter(String nameSubstring)
   {
     this._nameSubstring = nameSubstring;
   }
 
   public boolean matchMethod(MethodSignature sig)
   {
     return (sig.getName().indexOf(this._nameSubstring) >= 0);
   }
 }