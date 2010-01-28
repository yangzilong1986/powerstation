 package com.hisun.engine.invoke.load;
 
 import com.hisun.message.HiContext;
 import java.util.HashMap;
 
 public class HiDict
 {
   private HashMap _dictMap;
   private static final String _DICT_CFG = "DictCfg";
   private String _name;
 
   public HiDict()
   {
     this._dictMap = new HashMap();
 
     this._name = null; }
 
   public static HiDictItem get(HiContext ctx, String name) {
     HiDict dictUtil = (HiDict)ctx.getProperty("CONFIGDECLARE", "DictCfg");
 
     return ((HiDictItem)dictUtil._dictMap.get(name.toUpperCase()));
   }
 
   public void addItem(HiDictItem item) {
     this._dictMap.put(item.getName(), item);
   }
 
   public String getName() {
     return this._name;
   }
 
   public void setName(String name) {
     this._name = name;
   }
 }