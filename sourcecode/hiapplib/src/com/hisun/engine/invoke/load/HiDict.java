/*    */ package com.hisun.engine.invoke.load;
/*    */ 
/*    */ import com.hisun.message.HiContext;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class HiDict
/*    */ {
/*    */   private HashMap _dictMap;
/*    */   private static final String _DICT_CFG = "DictCfg";
/*    */   private String _name;
/*    */ 
/*    */   public HiDict()
/*    */   {
/* 14 */     this._dictMap = new HashMap();
/*    */ 
/* 16 */     this._name = null; }
/*    */ 
/*    */   public static HiDictItem get(HiContext ctx, String name) {
/* 19 */     HiDict dictUtil = (HiDict)ctx.getProperty("CONFIGDECLARE", "DictCfg");
/*    */ 
/* 21 */     return ((HiDictItem)dictUtil._dictMap.get(name.toUpperCase()));
/*    */   }
/*    */ 
/*    */   public void addItem(HiDictItem item) {
/* 25 */     this._dictMap.put(item.getName(), item);
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 29 */     return this._name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 33 */     this._name = name;
/*    */   }
/*    */ }