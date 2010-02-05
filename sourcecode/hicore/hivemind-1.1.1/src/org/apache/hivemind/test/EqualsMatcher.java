 package org.apache.hivemind.test;
 
 public class EqualsMatcher extends AbstractArgumentMatcher
 {
   public boolean compareArguments(Object expected, Object actual)
   {
     return expected.equals(actual);
   }
 }