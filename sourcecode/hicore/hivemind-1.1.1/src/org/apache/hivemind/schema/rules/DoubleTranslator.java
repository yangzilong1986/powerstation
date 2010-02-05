 package org.apache.hivemind.schema.rules;
 
 import java.util.Map;
 import org.apache.hivemind.ApplicationRuntimeException;
 import org.apache.hivemind.HiveMind;
 import org.apache.hivemind.Location;
 import org.apache.hivemind.internal.Module;
 import org.apache.hivemind.schema.Translator;
 
 public class DoubleTranslator
   implements Translator
 {
   private double _minValue;
   private boolean _isMinValue;
   private double _maxValue;
   private boolean _isMaxValue;
   private double _defaultValue = 0.0D;
 
   public DoubleTranslator()
   {
   }
 
   public DoubleTranslator(String initializer)
   {
     Map m = RuleUtils.convertInitializer(initializer);
 
     String defaultInit = (String)m.get("default");
 
     if (defaultInit != null) {
       this._defaultValue = Double.parseDouble(defaultInit);
     }
     String minInit = (String)m.get("min");
     if (minInit != null)
     {
       this._isMinValue = true;
       this._minValue = Double.parseDouble(minInit);
     }
 
     String maxInit = (String)m.get("max");
     if (maxInit == null)
       return;
     this._isMaxValue = true;
     this._maxValue = Double.parseDouble(maxInit);
   }
 
   public Object translate(Module contributingModule, Class propertyType, String inputValue, Location location)
   {
     double value;
     if (HiveMind.isBlank(inputValue)) {
       return new Double(this._defaultValue);
     }
 
     try
     {
       value = Double.parseDouble(inputValue);
     }
     catch (Exception ex)
     {
       throw new ApplicationRuntimeException(RulesMessages.invalidDoubleValue(inputValue), location, ex);
     }
 
     if ((this._isMinValue) && (value < this._minValue)) {
       throw new ApplicationRuntimeException(RulesMessages.minDoubleValue(inputValue, this._minValue));
     }
 
     if ((this._isMaxValue) && (value > this._maxValue)) {
       throw new ApplicationRuntimeException(RulesMessages.maxDoubleValue(inputValue, this._maxValue));
     }
 
     return new Double(value);
   }
 }