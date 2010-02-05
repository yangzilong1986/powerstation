 package org.apache.hivemind.methodmatch;
 
 import java.util.Iterator;
 import java.util.List;
 import org.apache.hivemind.service.MethodSignature;
 
 public class CompositeFilter extends MethodFilter
 {
   private List _filters;
 
   public CompositeFilter(List filters)
   {
     this._filters = filters;
   }
 
   public boolean matchMethod(MethodSignature sig)
   {
     Iterator i = this._filters.iterator();
     while (i.hasNext())
     {
       MethodFilter mf = (MethodFilter)i.next();
 
       if (!(mf.matchMethod(sig))) {
         return false;
       }
 
     }
 
     return true;
   }
 }