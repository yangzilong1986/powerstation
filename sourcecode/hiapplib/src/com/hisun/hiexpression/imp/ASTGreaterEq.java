/*    */ package com.hisun.hiexpression.imp;
/*    */ 
/*    */ public class ASTGreaterEq extends SimpleNode
/*    */ {
/*    */   public ASTGreaterEq(int id)
/*    */   {
/*  7 */     super(id);
/*    */   }
/*    */ 
/*    */   public ASTGreaterEq(ICSExpParser p, int id) {
/* 11 */     super(p, id);
/*    */   }
/*    */ 
/*    */   public Object jjtAccept(ICSExpParserVisitor visitor, Object data)
/*    */     throws Exception
/*    */   {
/* 18 */     return visitor.visit(this, data);
/*    */   }
/*    */ }