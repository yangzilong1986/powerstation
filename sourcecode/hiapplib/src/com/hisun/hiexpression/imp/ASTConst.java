/*    */ package com.hisun.hiexpression.imp;
/*    */ 
/*    */ public class ASTConst extends SimpleNode
/*    */ {
/*    */   private Object value;
/*    */ 
/*    */   public ASTConst(int id)
/*    */   {
/*  9 */     super(id);
/*    */   }
/*    */ 
/*    */   public ASTConst(ICSExpParser p, int id) {
/* 13 */     super(p, id);
/*    */   }
/*    */ 
/*    */   void setValue(Object value)
/*    */   {
/* 18 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public Object getValue() {
/* 22 */     return this.value;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 26 */     String result = "ASTConst:";
/*    */ 
/* 28 */     if (this.value == null) {
/* 29 */       result = result + "null";
/*    */     }
/* 31 */     else if (this.value instanceof String)
/* 32 */       result = result + '"' + this.value.toString() + '"';
/*    */     else {
/* 34 */       result = result + '"' + this.value.getClass().toString() + '"';
/*    */     }
/*    */ 
/* 37 */     return result;
/*    */   }
/*    */ 
/*    */   public Object jjtAccept(ICSExpParserVisitor visitor, Object data)
/*    */   {
/* 42 */     return visitor.visit(this, data);
/*    */   }
/*    */ }