 package org.apache.hivemind.impl;
 
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.apache.hivemind.ErrorHandler;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.SymbolSource;
 
 public class SymbolExpander
 {
   private ErrorHandler _errorHandler;
   private SymbolSource _source;
   private static final Log LOG = LogFactory.getLog(SymbolExpander.class);
   private static final int STATE_START = 0;
   private static final int STATE_DOLLAR = 1;
   private static final int STATE_COLLECT_SYMBOL_NAME = 2;
 
   public SymbolExpander(ErrorHandler handler, SymbolSource source)
   {
     this._errorHandler = handler;
     this._source = source;
   }
 
   public String expandSymbols(String text, Location location)
   {
     StringBuffer result = new StringBuffer(text.length());
     char[] buffer = text.toCharArray();
     int state = 0;
     int blockStart = 0;
     int blockLength = 0;
     int symbolStart = -1;
     int symbolLength = 0;
     int i = 0;
     int braceDepth = 0;
     boolean anySymbols = false;
 
     while (i < buffer.length)
     {
       char ch = buffer[i];
 
       switch (state)
       {
       case 0:
         if (ch == '$')
         {
           state = 1;
           ++i;
         }
 
         ++blockLength;
         ++i;
         break;
       case 1:
         if (ch == '{')
         {
           state = 2;
           ++i;
 
           symbolStart = i;
           symbolLength = 0;
           braceDepth = 1;
         }
 
         if (ch == '$')
         {
           anySymbols = true;
 
           if (blockLength > 0) {
             result.append(buffer, blockStart, blockLength);
           }
           result.append(ch);
 
           ++i;
           blockStart = i;
           blockLength = 0;
           state = 0;
         }
 
         ++blockLength;
 
         state = 0;
         break;
       case 2:
         if (ch != '}')
         {
           if (ch == '{') {
             ++braceDepth;
           }
           ++i;
           ++symbolLength;
         }
 
         --braceDepth;
 
         if (braceDepth > 0)
         {
           ++i;
           ++symbolLength;
         }
 
         if (symbolLength == 0) {
           blockLength += 3;
         }
 
         if (blockLength > 0) {
           result.append(buffer, blockStart, blockLength);
         }
         if (symbolLength > 0)
         {
           String variableName = text.substring(symbolStart, symbolStart + symbolLength);
 
           result.append(expandSymbol(variableName, location));
 
           anySymbols = true;
         }
 
         ++i;
         blockStart = i;
         blockLength = 0;
 
         state = 0;
 
         continue;
       }
 
     }
 
     if (!(anySymbols)) {
       return text;
     }
 
     if (state == 1) {
       ++blockLength;
     }
     if (state == 2) {
       blockLength += symbolLength + 2;
     }
     if (blockLength > 0) {
       result.append(buffer, blockStart, blockLength);
     }
     return result.toString();
   }
 
   private String expandSymbol(String name, Location location)
   {
     String value = this._source.valueForSymbol(name);
 
     if (value != null) {
       return value;
     }
     this._errorHandler.error(LOG, ImplMessages.noSuchSymbol(name), location, null);
 
     return "${" + name + "}";
   }
 }