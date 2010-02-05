 package org.apache.hivemind.methodmatch;
 
 import org.apache.hivemind.service.MethodSignature;
 
 public class MatchAllFilter extends MethodFilter
 {
   public boolean matchMethod(MethodSignature sig)
   {
     return true;
   }
 }