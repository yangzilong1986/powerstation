 package com.hisun.hiexpression.imp;
 
 public class ASTConst extends SimpleNode
 {
   private Object value;
 
   public ASTConst(int id)
   {
     super(id);
   }
 
   public ASTConst(ICSExpParser p, int id) {
     super(p, id);
   }
 
   void setValue(Object value)
   {
     this.value = value;
   }
 
   public Object getValue() {
     return this.value;
   }
 
   public String toString() {
     String result = "ASTConst:";
 
     if (this.value == null) {
       result = result + "null";
     }
     else if (this.value instanceof String)
       result = result + '"' + this.value.toString() + '"';
     else {
       result = result + '"' + this.value.getClass().toString() + '"';
     }
 
     return result;
   }
 
   public Object jjtAccept(ICSExpParserVisitor visitor, Object data)
   {
     return visitor.visit(this, data);
   }
 }