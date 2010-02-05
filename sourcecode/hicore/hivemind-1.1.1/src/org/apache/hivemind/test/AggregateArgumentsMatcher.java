 package org.apache.hivemind.test;
 
 import org.easymock.AbstractMatcher;
 
 public class AggregateArgumentsMatcher extends AbstractMatcher
 {
   private ArgumentMatcher[] _matchers;
   private ArgumentMatcher _defaultMatcher;
 
   public AggregateArgumentsMatcher(ArgumentMatcher[] matchers)
   {
     this._defaultMatcher = new EqualsMatcher();
 
     this._matchers = matchers;
   }
 
   public AggregateArgumentsMatcher(ArgumentMatcher matcher)
   {
     this(new ArgumentMatcher[] { matcher });
   }
 
   public boolean matches(Object[] expected, Object[] actual)
   {
     for (int i = 0; i < expected.length; ++i)
     {
       if (!(matches(i, expected[i], actual[i]))) {
         return false;
       }
     }
     return true;
   }
 
   private boolean matches(int argumentIndex, Object expected, Object actual)
   {
     if (expected == actual) {
       return true;
     }
 
     if ((expected == null) || (actual == null)) {
       return false;
     }
     ArgumentMatcher am = getArgumentMatcher(argumentIndex);
 
     return am.compareArguments(expected, actual);
   }
 
   private ArgumentMatcher getArgumentMatcher(int argumentIndex)
   {
     if (argumentIndex >= this._matchers.length) {
       return this._defaultMatcher;
     }
     ArgumentMatcher result = this._matchers[argumentIndex];
 
     if (result == null) {
       result = this._defaultMatcher;
     }
     return result;
   }
 }