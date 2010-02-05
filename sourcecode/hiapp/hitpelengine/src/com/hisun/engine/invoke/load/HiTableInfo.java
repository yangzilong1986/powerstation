 package com.hisun.engine.invoke.load;
 
 import java.util.ArrayList;
 import java.util.HashMap;
 
 public class HiTableInfo
 {
   private ArrayList _columns;
   private HashMap _infos;
   private HashMap _defaultValue;
   private int type;
 
   public HiTableInfo()
   {
     this._columns = new ArrayList();
     this._infos = new HashMap();
     this._defaultValue = new HashMap();
 
     this.type = 0;
   }
 
   public void addName(String name, String value)
   {
     ArrayList values;
     if (this._infos.containsKey(name)) {
       values = (ArrayList)this._infos.get(name);
       values.add(value);
     } else {
       values = new ArrayList();
       values.add(value);
       this._infos.put(name, values);
     }
   }
 
   public void setColumns(ArrayList columns)
   {
     this._columns = columns;
   }
 
   public void addDefault(String name, String value)
   {
     this._defaultValue.put(name, value);
   }
 
   public String getColValue(String col1_name, String col1_value)
   {
     int idx = this._columns.indexOf(col1_name);
     idx = (idx == 0) ? 1 : 0;
     return getColValue(col1_name, col1_value, (String)this._columns.get(idx));
   }
 
   public String getColValue(String col1_name, String col1_value, String col2_name)
   {
     if ((!(this._infos.containsKey(col1_name))) || (!(this._infos.containsKey(col2_name)))) {
       return NotFoundProc(col2_name, col1_value);
     }
 
     ArrayList values1 = (ArrayList)this._infos.get(col1_name);
     ArrayList values2 = (ArrayList)this._infos.get(col2_name);
     int idx = values1.indexOf(col1_value);
     if ((idx == -1) || (values2.get(idx) == null)) {
       return NotFoundProc(col2_name, col1_value);
     }
 
     return ((String)values2.get(idx));
   }
 
   public String NotFoundProc(String col_name, String col_value) {
     switch (this.type)
     {
     case 0:
       return ((String)this._defaultValue.get(col_name));
     case 1:
       return col_value;
     }
     return ((String)this._defaultValue.get(col_name));
   }
 
   public String toString()
   {
     StringBuffer result = new StringBuffer();
     result.append("_columns：");
     result.append(this._columns);
     result.append("_infos：");
     result.append(this._infos);
     result.append("_defaultValue：");
     result.append(this._defaultValue);
     return result.toString();
   }
 
   public int getType() {
     return this.type;
   }
 
   public void setType(int type) {
     this.type = type;
   }
 }