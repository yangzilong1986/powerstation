/*    */ package com.hisun.hiexpression.imp;
/*    */ 
/*    */ public abstract interface ICSExpParserConstants
/*    */ {
/*    */   public static final int EOF = 0;
/*    */   public static final int IDENT = 29;
/*    */   public static final int LETTER = 30;
/*    */   public static final int DIGIT = 31;
/*    */   public static final int ESC = 35;
/*    */   public static final int CHAR_LITERAL = 37;
/*    */   public static final int BACK_CHAR_ESC = 38;
/*    */   public static final int BACK_CHAR_LITERAL = 40;
/*    */   public static final int STRING_ESC = 41;
/*    */   public static final int STRING_LITERAL = 43;
/*    */   public static final int INT_LITERAL = 44;
/*    */   public static final int FLT_LITERAL = 45;
/*    */   public static final int DEC_FLT = 46;
/*    */   public static final int DEC_DIGITS = 47;
/*    */   public static final int EXPONENT = 48;
/*    */   public static final int FLT_SUFF = 49;
/*    */   public static final int DEFAULT = 0;
/*    */   public static final int WithinCharLiteral = 1;
/*    */   public static final int WithinBackCharLiteral = 2;
/*    */   public static final int WithinStringLiteral = 3;
/* 28 */   public static final String[] tokenImage = { "<EOF>", "\"==\"", "\"!=\"", "\"<\"", "\">\"", "\"<=\"", "\">=\"", "\"@BAS.\"", "\"@BCFG.\"", "\"@MSG.\"", "\"@ETF.\"", "\"@PARA.\"", "\"@SYS.\"", "\"$\"", "\"~\"", "\"%\"", "\"#\"", "\"@\"", "\"true\"", "\"false\"", "\"null\"", "\"(\"", "\")\"", "\",\"", "\" \"", "\"\\t\"", "\"\\f\"", "\"\\r\"", "\"\\n\"", "<IDENT>", "<LETTER>", "<DIGIT>", "\"`\"", "\"\\'\"", "\"\\\"\"", "<ESC>", "<token of kind 36>", "\"\\'\"", "<BACK_CHAR_ESC>", "<token of kind 39>", "\"`\"", "<STRING_ESC>", "<token of kind 42>", "\"\\\"\"", "<INT_LITERAL>", "<FLT_LITERAL>", "<DEC_FLT>", "<DEC_DIGITS>", "<EXPONENT>", "<FLT_SUFF>" };
/*    */ }