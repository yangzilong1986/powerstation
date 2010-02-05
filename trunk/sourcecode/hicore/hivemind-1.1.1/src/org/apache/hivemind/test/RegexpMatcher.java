 package org.apache.hivemind.test;
 
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.oro.text.regex.Pattern;
 import org.apache.oro.text.regex.Perl5Compiler;
 import org.apache.oro.text.regex.Perl5Matcher;
 
 public class RegexpMatcher extends AbstractArgumentMatcher
 {
   private static Perl5Compiler _compiler = new Perl5Compiler();
 
   private static Perl5Matcher _matcher = new Perl5Matcher();
 
   public boolean compareArguments(Object expected, Object actual)
   {
     return matchRegexp((String)expected, (String)actual);
   }
 
   private boolean matchRegexp(String expectedRegexp, String actualString)
   {
     try
     {
       Pattern p = _compiler.compile(expectedRegexp);
 
       return _matcher.matches(actualString, p);
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(ex);
     }
   }
 }