 package org.apache.hivemind.test;
 
 public class ArrayMatcher extends AbstractArgumentMatcher
 {
   public boolean compareArguments(Object expected, Object actual)
   {
     Object[] e = (Object[])(Object[])expected;
     Object[] a = (Object[])(Object[])actual;
 
     if (a == null) {
       return false;
     }
     if (e.length != a.length) {
       return false;
     }
     for (int i = 0; i < e.length; ++i)
     {
       if (!(e[i].equals(a[i]))) {
         return false;
       }
     }
     return true;
   }
 }