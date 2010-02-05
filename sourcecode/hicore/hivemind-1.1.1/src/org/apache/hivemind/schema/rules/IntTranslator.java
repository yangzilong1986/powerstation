 package org.apache.hivemind.schema.rules;
 
 import java.util.Map;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.schema.Translator;
 
 public class IntTranslator
   implements Translator
 {
   private int _minValue;
   private boolean _isMinValue;
   private int _maxValue;
   private boolean _isMaxValue;
   private int _defaultValue = 0;
 
   public IntTranslator()
   {
   }
 
   public IntTranslator(String initializer)
   {
     Map m = RuleUtils.convertInitializer(initializer);
 
     String defaultInit = (String)m.get("default");
 
     if (defaultInit != null) {
       this._defaultValue = Integer.parseInt(defaultInit);
     }
     String minInit = (String)m.get("min");
     if (minInit != null)
     {
       this._isMinValue = true;
       this._minValue = Integer.parseInt(minInit);
     }
 
     String maxInit = (String)m.get("max");
     if (maxInit == null)
       return;
     this._isMaxValue = true;
     this._maxValue = Integer.parseInt(maxInit);
   }
 
   public Object translate(Module contributingModule, Class propertyType, String inputValue, Location location)
   {
     int value;
     if (HiveMind.isBlank(inputValue)) {
       return new Integer(this._defaultValue);
     }
 
     try
     {
       value = Integer.parseInt(inputValue);
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(RulesMessages.invalidIntValue(inputValue), location, null);
     }
 
     if ((this._isMinValue) && (value < this._minValue)) {
       throw new ApplicationRuntimeException(RulesMessages.minIntValue(inputValue, this._minValue));
     }
     if ((this._isMaxValue) && (value > this._maxValue)) {
       throw new ApplicationRuntimeException(RulesMessages.maxIntValue(inputValue, this._maxValue));
     }
     return new Integer(value);
   }
 }