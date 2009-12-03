/*    */ package com.hisun.hilog4j;
/*    */ 
/*    */ import org.apache.log4j.PatternLayout;
/*    */ import org.apache.log4j.helpers.PatternParser;
/*    */ 
/*    */ public class HiPatternLayout extends PatternLayout
/*    */ {
/*    */   public HiPatternLayout()
/*    */   {
/*  8 */     this("%m%n");
/*    */   }
/*    */ 
/*    */   public HiPatternLayout(String pattern) {
/* 12 */     super(pattern);
/*    */   }
/*    */ 
/*    */   public PatternParser createPatternParser(String pattern) {
/* 16 */     return new HiPatternParser((pattern == null) ? "%m%n" : pattern);
/*    */   }
/*    */ }