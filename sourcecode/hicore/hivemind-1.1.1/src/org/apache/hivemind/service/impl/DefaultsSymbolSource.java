 package org.apache.hivemind.service.impl;
 
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.apache.hivemind.SymbolSource;
 import org.apache.hivemind.impl.BaseLocatable;
 
 public class DefaultsSymbolSource extends BaseLocatable
   implements SymbolSource
 {
   private List _defaults;
   private Map _symbols;
 
   public DefaultsSymbolSource()
   {
     this._symbols = new HashMap();
   }
 
   public String valueForSymbol(String name) {
     return ((String)this._symbols.get(name));
   }
 
   public void initializeService()
   {
     int count = this._defaults.size();
     for (int i = 0; i < count; ++i)
     {
       FactoryDefault fd = (FactoryDefault)this._defaults.get(i);
 
       String symbol = fd.getSymbol();
       String value = fd.getValue();
 
       this._symbols.put(symbol, value);
     }
   }
 
   public void setDefaults(List list)
   {
     this._defaults = list;
   }
 }