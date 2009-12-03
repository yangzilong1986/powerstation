/*    */ package com.hisun.hiexpression.imp;
/*    */ 
/*    */ public class ASTNotEq extends SimpleNode
/*    */ {
/*    */   public ASTNotEq(int id)
/*    */   {
/*  7 */     super(id);
/*    */   }
/*    */ 
/*    */   public ASTNotEq(ICSExpParser p, int id) {
/* 11 */     super(p, id);
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 15 */     return this.children[0] + " = " + this.children[1];
/*    */   }
/*    */ 
/*    */   public Object jjtAccept(ICSExpParserVisitor visitor, Object data)
/*    */     throws Exception
/*    */   {
/* 21 */     return visitor.visit(this, data);
/*    */   }
/*    */ }