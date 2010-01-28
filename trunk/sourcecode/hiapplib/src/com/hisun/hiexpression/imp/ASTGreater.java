 package com.hisun.hiexpression.imp;
 
 public class ASTGreater extends SimpleNode
 {
   public ASTGreater(int id)
   {
     super(id);
   }
 
   public ASTGreater(ICSExpParser p, int id) {
     super(p, id);
   }
 
   public Object jjtAccept(ICSExpParserVisitor visitor, Object data)
     throws Exception
   {
     return visitor.visit(this, data);
   }
 }