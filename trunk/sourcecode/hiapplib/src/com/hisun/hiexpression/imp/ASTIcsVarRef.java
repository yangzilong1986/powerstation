 package com.hisun.hiexpression.imp;
 
 public class ASTIcsVarRef extends SimpleNode
 {
   public static final int TYPE_BAS = 0;
   public static final int TYPE_BCFG = 1;
   public static final int TYPE_MSG = 2;
   public static final int TYPE_ETF = 3;
   public static final int TYPE_PARA = 4;
   public static final int TYPE_DS = 5;
   public static final int TYPE_SYS = 6;
   public String item;
   public int type;
 
   public ASTIcsVarRef(int id)
   {
     super(id);
   }
 
   public ASTIcsVarRef(ICSExpParser p, int id) {
     super(p, id);
   }
 
   public void setItemName(int type, String image) {
     this.item = image;
     this.type = type;
   }
 
   public String toString() {
     StringBuffer result = new StringBuffer();
 
     switch (this.type)
     {
     case 0:
       result.append("BAS type:");
       break;
     case 1:
       result.append("BCFG type:");
       break;
     case 2:
       result.append("MSG type:");
       break;
     case 3:
       result.append("ETF type:");
       break;
     case 4:
       result.append("ETF type:");
       break;
     case 5:
       result.append("DS type:");
     }
 
     return this.item;
   }
 
   public Object jjtAccept(ICSExpParserVisitor visitor, Object data) throws Exception
   {
     return visitor.visit(this, data);
   }
 }