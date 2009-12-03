/*    */ package com.hisun.parse;
/*    */ 
/*    */ import com.hisun.message.HiContext;
/*    */ import com.hisun.util.HiSymbolExpander;
/*    */ import org.apache.commons.digester.substitution.VariableExpander;
/*    */ 
/*    */ public class HiVariableExpander
/*    */   implements VariableExpander
/*    */ {
/*    */   public String expand(String param)
/*    */   {
/* 12 */     HiContext ctx = HiContext.getCurrentContext();
/* 13 */     HiSymbolExpander symbolExpander = new HiSymbolExpander(ctx, "@PARA");
/* 14 */     return symbolExpander.expandSymbols(param);
/*    */   }
/*    */ }