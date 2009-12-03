/*    */ package com.hisun.hiexpression.imp;
/*    */ 
/*    */ public class ASTIcsVarRef extends SimpleNode
/*    */ {
/*    */   public static final int TYPE_BAS = 0;
/*    */   public static final int TYPE_BCFG = 1;
/*    */   public static final int TYPE_MSG = 2;
/*    */   public static final int TYPE_ETF = 3;
/*    */   public static final int TYPE_PARA = 4;
/*    */   public static final int TYPE_DS = 5;
/*    */   public static final int TYPE_SYS = 6;
/*    */   public String item;
/*    */   public int type;
/*    */ 
/*    */   public ASTIcsVarRef(int id)
/*    */   {
/* 25 */     super(id);
/*    */   }
/*    */ 
/*    */   public ASTIcsVarRef(ICSExpParser p, int id) {
/* 29 */     super(p, id);
/*    */   }
/*    */ 
/*    */   public void setItemName(int type, String image) {
/* 33 */     this.item = image;
/* 34 */     this.type = type;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 38 */     StringBuffer result = new StringBuffer();
/*    */ 
/* 40 */     switch (this.type)
/*    */     {
/*    */     case 0:
/* 42 */       result.append("BAS type:");
/* 43 */       break;
/*    */     case 1:
/* 45 */       result.append("BCFG type:");
/* 46 */       break;
/*    */     case 2:
/* 48 */       result.append("MSG type:");
/* 49 */       break;
/*    */     case 3:
/* 51 */       result.append("ETF type:");
/* 52 */       break;
/*    */     case 4:
/* 54 */       result.append("ETF type:");
/* 55 */       break;
/*    */     case 5:
/* 57 */       result.append("DS type:");
/*    */     }
/*    */ 
/* 62 */     return this.item;
/*    */   }
/*    */ 
/*    */   public Object jjtAccept(ICSExpParserVisitor visitor, Object data) throws Exception
/*    */   {
/* 67 */     return visitor.visit(this, data);
/*    */   }
/*    */ }