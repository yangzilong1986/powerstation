 package org.apache.hivemind.schema.rules;
 
 import java.util.Map;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.schema.Translator;
 
 public class LongTranslator
   implements Translator
 {
   private long _minValue;
   private boolean _isMinValue;
   private long _maxValue;
   private boolean _isMaxValue;
   private long _defaultValue = 0L;
 
   public LongTranslator()
   {
   }
 
   public LongTranslator(String initializer)
   {
     Map m = RuleUtils.convertInitializer(initializer);
 
     String defaultInit = (String)m.get("default");
 
     if (defaultInit != null) {
       this._defaultValue = Long.parseLong(defaultInit);
     }
     String minInit = (String)m.get("min");
     if (minInit != null)
     {
       this._isMinValue = true;
       this._minValue = Long.parseLong(minInit);
     }
 
     String maxInit = (String)m.get("max");
     if (maxInit == null)
       return;
     this._isMaxValue = true;
     this._maxValue = Long.parseLong(maxInit);
   }
 
   public Object translate(Module contributingModule, Class propertyType, String inputValue, Location location)
   {
     long value;
     if (HiveMind.isBlank(inputValue)) {
       return new Long(this._defaultValue);
     }
 
     try
     {
       value = Long.parseLong(inputValue);
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(RulesMessages.invalidLongValue(inputValue), location, ex);
     }
 
     if ((this._isMinValue) && (value < this._minValue)) {
       throw new ApplicationRuntimeException(RulesMessages.minLongValue(inputValue, this._minValue));
     }
 
     if ((this._isMaxValue) && (value > this._maxValue)) {
       throw new ApplicationRuntimeException(RulesMessages.maxLongValue(inputValue, this._maxValue));
     }
 
     return new Long(value);
   }
 }