 package org.apache.hivemind.test;
 
 import org.easymock.AbstractMatcher;
 
 public abstract class AbstractArgumentMatcher extends AbstractMatcher
   implements ArgumentMatcher
 {
   protected boolean argumentMatches(Object expected, Object actual)
   {
     return super.compareArguments(expected, actual);
   }
 
   public abstract boolean compareArguments(Object paramObject1, Object paramObject2);
 }