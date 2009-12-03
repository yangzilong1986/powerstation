/*    */ package com.hisun.hiexpression.imp;
/*    */ 
/*    */ public class Token
/*    */ {
/*    */   public int kind;
/*    */   public int beginLine;
/*    */   public int beginColumn;
/*    */   public int endLine;
/*    */   public int endColumn;
/*    */   public String image;
/*    */   public Token next;
/*    */   public Token specialToken;
/*    */ 
/*    */   public String toString()
/*    */   {
/* 58 */     return this.image;
/*    */   }
/*    */ 
/*    */   public static final Token newToken(int ofKind)
/*    */   {
/* 75 */     switch (ofKind) {
/*    */     }
/* 77 */     return new Token();
/*    */   }
/*    */ }