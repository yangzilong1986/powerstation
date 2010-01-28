 package com.hisun.hiexpression.imp;
 
 public class ASTGreaterEq extends SimpleNode
 {
   public ASTGreaterEq(int id)
   {
     super(id);
   }
 
   public ASTGreaterEq(ICSExpParser p, int id) {
     super(p, id);
   }
 
   public Object jjtAccept(ICSExpParserVisitor visitor, Object data)
     throws Exception
   {
     return visitor.visit(this, data);
   }
 }