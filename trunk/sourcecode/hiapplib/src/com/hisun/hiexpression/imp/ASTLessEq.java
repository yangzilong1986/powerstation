/*    */ package com.hisun.hiexpression.imp;
/*    */ 
/*    */ public class ASTLessEq extends SimpleNode
/*    */ {
/*    */   public ASTLessEq(int id)
/*    */   {
/*  7 */     super(id);
/*    */   }
/*    */ 
/*    */   public ASTLessEq(ICSExpParser p, int id) {
/* 11 */     super(p, id);
/*    */   }
/*    */ 
/*    */   public Object jjtAccept(ICSExpParserVisitor visitor, Object data)
/*    */     throws Exception
/*    */   {
/* 18 */     return visitor.visit(this, data);
/*    */   }
/*    */ }