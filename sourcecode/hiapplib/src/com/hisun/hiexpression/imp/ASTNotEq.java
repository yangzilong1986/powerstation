 package com.hisun.hiexpression.imp;
 
 public class ASTNotEq extends SimpleNode
 {
   public ASTNotEq(int id)
   {
     super(id);
   }
 
   public ASTNotEq(ICSExpParser p, int id) {
     super(p, id);
   }
 
   public String toString() {
     return this.children[0] + " = " + this.children[1];
   }
 
   public Object jjtAccept(ICSExpParserVisitor visitor, Object data)
     throws Exception
   {
     return visitor.visit(this, data);
   }
 }