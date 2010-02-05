 package org.apache.hivemind.test;
 
 public class TypeMatcher extends AbstractArgumentMatcher
 {
   public boolean compareArguments(Object expected, Object actual)
   {
     return expected.getClass().equals(actual.getClass());
   }
 }