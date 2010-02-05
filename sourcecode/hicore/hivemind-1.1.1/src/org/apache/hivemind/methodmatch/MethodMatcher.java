 package org.apache.hivemind.methodmatch;
 
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.service.MethodSignature;
 
 public class MethodMatcher
 {
   private MethodPatternParser _parser;
   private List _methodInfos;
   private Object _defaultValue;
 
   public MethodMatcher(Object defaultValue)
   {
     this._parser = new MethodPatternParser();
 
     this._defaultValue = defaultValue;
   }
 
   public MethodMatcher()
   {
     this(null);
   }
 
   private MethodFilter parseMethodPattern(String pattern)
   {
     return this._parser.parseMethodPattern(pattern);
   }
 
   public synchronized void put(String methodPattern, Object patternValue)
   {
     if (this._methodInfos == null) {
       this._methodInfos = new ArrayList();
     }
     StoredPattern sp = new StoredPattern(methodPattern, patternValue);
 
     this._methodInfos.add(sp);
   }
 
   public synchronized Object get(MethodSignature sig)
   {
     if (this._methodInfos == null) {
       return this._defaultValue;
     }
     Iterator i = this._methodInfos.iterator();
     while (i.hasNext())
     {
       StoredPattern sp = (StoredPattern)i.next();
 
       if (sp.match(sig)) {
         return sp._patternValue;
       }
 
     }
 
     return this._defaultValue;
   }
 
   private class StoredPattern
   {
     String _methodPattern;
     MethodFilter _filter;
     Object _patternValue;
 
     StoredPattern(String paramString, Object paramObject)
     {
       this._methodPattern = paramString;
       this._patternValue = value;
     }
 
     boolean match(MethodSignature sig)
     {
       if (this._filter == null)
       {
         try
         {
           this._filter = MethodMatcher.this.parseMethodPattern(this._methodPattern);
         }
         catch (RuntimeException ex)
         {
           Location l = HiveMind.findLocation(new Object[] { this._patternValue, ex });
 
           if (l == null) {
             throw ex;
           }
           throw new ApplicationRuntimeException(MethodMatchMessages.exceptionAtLocation(l, ex), ex);
         }
 
       }
 
       return this._filter.matchMethod(sig);
     }
   }
 }