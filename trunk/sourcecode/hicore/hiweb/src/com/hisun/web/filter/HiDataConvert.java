 package com.hisun.web.filter;

 import com.hisun.web.tag.HiExpression;

 import java.util.HashMap;
 
 public class HiDataConvert
 {
   private HashMap _dataConvert = new HashMap();
 
   public HiDataConvert() { this._dataConvert.put("D", "DATEPROC");
     this._dataConvert.put("A", "AMTPROC");
   }
 
   public String convert(String name, String value) throws Exception {
     String expr = (String)this._dataConvert.get(name.toUpperCase());
     if (expr == null) {
       return value;
     }
     return HiExpression.expr(expr, value);
   }
 
   public void add(String rule, String expr) {
     this._dataConvert.put(rule, expr);
   }
 }