 package com.hisun.atc.rpt.xml;
 
 import com.hisun.atc.rpt.HiReportRuntimeException;
 import com.hisun.xml.Located;
 import java.util.HashMap;
 import java.util.Map;
 
 public class HiMsgConvertNode extends Located
 {
   public Map items;
 
   public HiMsgConvertNode()
   {
     this.items = new HashMap(); }
 
   public void addItem(HiItemNode item) {
     this.items.put(item.alias, item.map);
   }
 
   public String convert(String alias, String value) {
     if (this.items.containsKey(alias)) {
       Map map = (Map)this.items.get(alias);
       if (map.containsKey(value))
         return ((String)map.get(value));
       if (map.containsKey("default")) {
         return ((String)map.get("default"));
       }
     }
 
     throw new HiReportRuntimeException(3, "MsgConvert转换失败[" + alias + ":" + value + "]");
   }
 
   public static class HiItemNode
   {
     String alias;
     Map map;
 
     public HiItemNode()
     {
       this.map = new HashMap(); }
 
     public void addConvert(String value, String msg) {
       this.map.put(value, msg);
     }
 
     public void addDefault(String msg) {
       this.map.put("default", msg);
     }
 
     public void setAlias(String alias) {
       this.alias = alias;
     }
   }
 }