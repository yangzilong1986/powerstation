 package org.apache.hivemind.schema.rules;
 
 import java.util.Collections;
 import java.util.HashMap;
 import java.util.Map;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.Element;
 import org.apache.hivemind.ErrorHandler;
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.Locatable;
 import org.apache.hivemind.Messages;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.schema.SchemaProcessor;
 import org.apache.hivemind.schema.Translator;
 import org.apache.hivemind.util.PropertyUtils;
 
 public class RuleUtils
 {
   private static final Log LOG = LogFactory.getLog(RuleUtils.class);
 
   public static Map convertInitializer(String initializer)
   {
     if (HiveMind.isBlank(initializer)) {
       return Collections.EMPTY_MAP;
     }
     Map result = new HashMap();
 
     int lastCommax = -1;
     int inputLength = initializer.length();
 
     while (lastCommax < inputLength)
     {
       int nextCommax = initializer.indexOf(44, lastCommax + 1);
 
       if (nextCommax < 0) {
         nextCommax = inputLength;
       }
       String term = initializer.substring(lastCommax + 1, nextCommax);
 
       int equalsx = term.indexOf(61);
 
       if (equalsx <= 0) {
         throw new ApplicationRuntimeException(RulesMessages.invalidInitializer(initializer));
       }
       String key = term.substring(0, equalsx);
       String value = term.substring(equalsx + 1);
 
       result.put(key, value);
 
       lastCommax = nextCommax;
     }
 
     return result;
   }
 
   public static String processText(SchemaProcessor processor, Element element, String inputValue)
   {
     if (inputValue == null) {
       return null;
     }
     Module contributingModule = processor.getContributingModule();
 
     if (inputValue.startsWith("%"))
     {
       String key = inputValue.substring(1);
 
       return contributingModule.getMessages().getMessage(key);
     }
 
     return contributingModule.expandSymbols(inputValue, element.getLocation());
   }
 
   public static void setProperty(SchemaProcessor processor, Element element, String propertyName, Object target, Object value)
   {
     try
     {
       PropertyUtils.write(target, propertyName, value);
     }
     catch (Exception ex)
     {
       ErrorHandler errorHandler = processor.getContributingModule().getErrorHandler();
       errorHandler.error(LOG, RulesMessages.unableToSetElementProperty(propertyName, target, processor, element, ex), element.getLocation(), ex);
     }
   }
 
   public static Translator getTranslator(SchemaProcessor processor, String translator)
   {
     if (translator == null) {
       return new NullTranslator();
     }
     return processor.getContributingModule().getTranslator(translator);
   }
 }