/*    */ package com.hisun.web.filter;
/*    */ 
/*    */ import com.hisun.web.tag.HiExpression;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class HiDataConvert
/*    */ {
/* 13 */   private HashMap _dataConvert = new HashMap();
/*    */ 
/*    */   public HiDataConvert() { this._dataConvert.put("D", "DATEPROC");
/* 16 */     this._dataConvert.put("A", "AMTPROC");
/*    */   }
/*    */ 
/*    */   public String convert(String name, String value) throws Exception {
/* 20 */     String expr = (String)this._dataConvert.get(name.toUpperCase());
/* 21 */     if (expr == null) {
/* 22 */       return value;
/*    */     }
/* 24 */     return HiExpression.expr(expr, value);
/*    */   }
/*    */ 
/*    */   public void add(String rule, String expr) {
/* 28 */     this._dataConvert.put(rule, expr);
/*    */   }
/*    */ }