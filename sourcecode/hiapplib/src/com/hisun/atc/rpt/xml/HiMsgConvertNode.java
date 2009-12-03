/*    */ package com.hisun.atc.rpt.xml;
/*    */ 
/*    */ import com.hisun.atc.rpt.HiReportRuntimeException;
/*    */ import com.hisun.xml.Located;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class HiMsgConvertNode extends Located
/*    */ {
/*    */   public Map items;
/*    */ 
/*    */   public HiMsgConvertNode()
/*    */   {
/* 33 */     this.items = new HashMap(); }
/*    */ 
/*    */   public void addItem(HiItemNode item) {
/* 36 */     this.items.put(item.alias, item.map);
/*    */   }
/*    */ 
/*    */   public String convert(String alias, String value) {
/* 40 */     if (this.items.containsKey(alias)) {
/* 41 */       Map map = (Map)this.items.get(alias);
/* 42 */       if (map.containsKey(value))
/* 43 */         return ((String)map.get(value));
/* 44 */       if (map.containsKey("default")) {
/* 45 */         return ((String)map.get("default"));
/*    */       }
/*    */     }
/*    */ 
/* 49 */     throw new HiReportRuntimeException(3, "MsgConvert转换失败[" + alias + ":" + value + "]");
/*    */   }
/*    */ 
/*    */   public static class HiItemNode
/*    */   {
/*    */     String alias;
/*    */     Map map;
/*    */ 
/*    */     public HiItemNode()
/*    */     {
/* 18 */       this.map = new HashMap(); }
/*    */ 
/*    */     public void addConvert(String value, String msg) {
/* 21 */       this.map.put(value, msg);
/*    */     }
/*    */ 
/*    */     public void addDefault(String msg) {
/* 25 */       this.map.put("default", msg);
/*    */     }
/*    */ 
/*    */     public void setAlias(String alias) {
/* 29 */       this.alias = alias;
/*    */     }
/*    */   }
/*    */ }