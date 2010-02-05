 package org.apache.hivemind.methodmatch;
 
 import java.util.ArrayList;
 import java.util.List;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.util.StringUtils;
 
 public class MethodPatternParser
 {
   private List _filters;
 
   public MethodFilter parseMethodPattern(String pattern)
   {
     this._filters = new ArrayList();
 
     int parenx = pattern.indexOf(40);
 
     String namePattern = (parenx < 0) ? pattern : pattern.substring(0, parenx);
 
     parseNamePattern(pattern, namePattern);
 
     if (parenx >= 0) {
       parseParametersPattern(pattern, pattern.substring(parenx));
     }
     switch (this._filters.size())
     {
     case 0:
       return new MatchAllFilter();
     case 1:
       return ((MethodFilter)this._filters.get(0));
     }
 
     return new CompositeFilter(this._filters);
   }
 
   private void parseNamePattern(String methodPattern, String namePattern)
   {
     if (namePattern.equals("*")) {
       return;
     }
     if (namePattern.length() == 0) {
       throw new ApplicationRuntimeException(MethodMatchMessages.missingNamePattern(methodPattern));
     }
 
     if ((namePattern.startsWith("*")) && (namePattern.endsWith("*")))
     {
       String substring = namePattern.substring(1, namePattern.length() - 1);
 
       validateNamePattern(methodPattern, substring);
 
       this._filters.add(new InfixNameFilter(substring));
       return;
     }
 
     if (namePattern.startsWith("*"))
     {
       String suffix = namePattern.substring(1);
 
       validateNamePattern(methodPattern, suffix);
 
       this._filters.add(new NameSuffixFilter(suffix));
       return;
     }
 
     if (namePattern.endsWith("*"))
     {
       String prefix = namePattern.substring(0, namePattern.length() - 1);
 
       validateNamePattern(methodPattern, prefix);
 
       this._filters.add(new NamePrefixFilter(prefix));
       return;
     }
 
     validateNamePattern(methodPattern, namePattern);
 
     this._filters.add(new ExactNameFilter(namePattern));
   }
 
   private void parseParametersPattern(String methodPattern, String pattern)
   {
     if (pattern.equals("()"))
     {
       addParameterCountFilter(0);
       return;
     }
 
     if (!(pattern.endsWith(")"))) {
       throw new ApplicationRuntimeException(MethodMatchMessages.invalidParametersPattern(methodPattern));
     }
 
     pattern = pattern.substring(1, pattern.length() - 1);
 
     char ch = pattern.charAt(0);
 
     if (Character.isDigit(ch))
     {
       addParameterCountFilter(methodPattern, pattern);
       return;
     }
 
     String[] names = StringUtils.split(pattern);
 
     addParameterCountFilter(names.length);
     for (int i = 0; i < names.length; ++i)
       this._filters.add(new ParameterFilter(i, names[i].trim()));
   }
 
   private void addParameterCountFilter(String methodPattern, String pattern)
   {
     try
     {
       int count = Integer.parseInt(pattern);
       addParameterCountFilter(count);
     }
     catch (NumberFormatException ex)
     {
       throw new ApplicationRuntimeException(MethodMatchMessages.invalidParametersPattern(methodPattern));
     }
   }
 
   private void addParameterCountFilter(int count)
   {
     this._filters.add(0, new ParameterCountFilter(count));
   }
 
   private void validateNamePattern(String methodPattern, String nameSubstring)
   {
     if (nameSubstring.indexOf(42) >= 0)
       throw new ApplicationRuntimeException(MethodMatchMessages.invalidNamePattern(methodPattern));
   }
 }