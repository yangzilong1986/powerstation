/*    */ package com.hisun.atc.rpt.xml;
/*    */ 
/*    */ import com.hisun.atc.rpt.HiRptContext;
/*    */ import com.hisun.xml.Located;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class HiVarDefNode extends Located
/*    */ {
/*    */   List vars;
/*    */ 
/*    */   public HiVarDefNode()
/*    */   {
/* 33 */     this.vars = new ArrayList(); }
/*    */ 
/*    */   public void addVarDef(VarDefNode v) {
/* 36 */     this.vars.add(v);
/*    */   }
/*    */ 
/*    */   public void init(HiRptContext ctx)
/*    */   {
/* 44 */     Iterator it = this.vars.iterator();
/* 45 */     while (it.hasNext()) {
/* 46 */       VarDefNode node = (VarDefNode)it.next();
/* 47 */       String alias = node.alias;
/* 48 */       String value = null;
/* 49 */       if (node.pos != -1)
/*    */       {
/* 51 */         value = ctx.getValueByPos(node.pos);
/* 52 */       } else if (node.name != null)
/*    */       {
/* 55 */         value = ctx.getValueByName(node.name.substring(1));
/*    */       }
/* 57 */       if (value != null)
/*    */       {
/* 59 */         ctx.vars.put(alias.toUpperCase(), value);
/* 60 */         ctx.info("VarDef [" + alias + ":" + value + "]");
/*    */       } else {
/* 62 */         ctx.warn("VarDef [" + alias + "," + node.pos + "," + node.name + "] has no value!");
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public static class VarDefNode
/*    */   {
/*    */     String alias;
/*    */     String name;
/*    */     int pos;
/*    */ 
/*    */     public VarDefNode()
/*    */     {
/* 18 */       this.pos = -1; }
/*    */ 
/*    */     public void setAlias(String alias) {
/* 21 */       this.alias = alias;
/*    */     }
/*    */ 
/*    */     public void setName(String name) {
/* 25 */       this.name = name;
/*    */     }
/*    */ 
/*    */     public void setPos(int pos) {
/* 29 */       this.pos = pos;
/*    */     }
/*    */   }
/*    */ }