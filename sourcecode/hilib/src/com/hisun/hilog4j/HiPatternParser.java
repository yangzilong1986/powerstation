/*    */ package com.hisun.hilog4j;
/*    */ 
/*    */ import org.apache.log4j.helpers.FormattingInfo;
/*    */ import org.apache.log4j.helpers.PatternConverter;
/*    */ import org.apache.log4j.helpers.PatternParser;
/*    */ import org.apache.log4j.spi.LoggingEvent;
/*    */ 
/*    */ public class HiPatternParser extends PatternParser
/*    */ {
/*    */   public HiPatternParser(String pattern)
/*    */   {
/* 12 */     super(pattern);
/*    */   }
/*    */ 
/*    */   public void finalizeConverter(char c) {
/* 16 */     if (c == '#') {
/* 17 */       addConverter(new UserDirPatternConverter(this.formattingInfo));
/* 18 */       this.currentLiteral.setLength(0);
/*    */     } else {
/* 20 */       super.finalizeConverter(c);
/*    */     }
/*    */   }
/*    */ 
/*    */   private static class UserDirPatternConverter extends PatternConverter {
/*    */     UserDirPatternConverter(FormattingInfo formattingInfo) {
/* 26 */       super(formattingInfo);
/*    */     }
/*    */ 
/*    */     public String convert(LoggingEvent event) {
/* 30 */       StringBuffer sb = new StringBuffer();
/* 31 */       Runtime rt = Runtime.getRuntime();
/* 32 */       sb.append("max mem:[");
/* 33 */       sb.append(rt.maxMemory() / 1024L);
/* 34 */       sb.append("k];total mem:[");
/* 35 */       sb.append(rt.totalMemory() / 1024L);
/* 36 */       sb.append("k];free mem:[");
/* 37 */       sb.append(rt.freeMemory() / 1024L);
/* 38 */       sb.append("k]");
/* 39 */       return sb.toString();
/*    */     }
/*    */   }
/*    */ }